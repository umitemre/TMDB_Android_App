package com.example.mobilliumcase.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilliumcase.R
import com.example.mobilliumcase.adapter.HomeAdapter
import com.example.mobilliumcase.databinding.FragmentHomeBinding
import com.example.mobilliumcase.decorator.SimpleItemDecorator
import com.example.mobilliumcase.di.DaggerHomeFragmentViewModelComponent
import com.example.mobilliumcase.listener.OnAdapterItemClick
import com.example.mobilliumcase.model.Result
import com.example.mobilliumcase.viewmodel.HomeFragmentViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class HomeFragment : DaggerFragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeFragmentViewModel by viewModels()

    @Inject
    lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelComponent = DaggerHomeFragmentViewModelComponent.create()
        viewModelComponent.inject(viewModel)

        homeAdapter.onAdapterItemClickListener = onAdapterItemClickListener

        viewModel.apply {
            getNowPlayingObservable().observe(viewLifecycleOwner) {
                if (viewModel.nowPlayingAck)
                    return@observe

                homeAdapter.setSliderItems(it.results)
                homeAdapter.notifyItemChanged(0)

                viewModel.nowPlayingAck = true
                binding.swipeRefreshLayout.isRefreshing = false
            }

            getUpcomingObservable().observe(viewLifecycleOwner) {
                if (viewModel.upComingAck)
                    return@observe

                homeAdapter.setUpcomingItems(it.results)
                homeAdapter.notifyItemRangeChanged(1, it.results.size)

                viewModel.upComingAck = true
                binding.swipeRefreshLayout.isRefreshing = false
            }

            if (!viewModel.nowPlayingAck)
                fetchNowPlaying()

            if (!viewModel.upComingAck)
                fetchUpcoming()
        }

        binding.apply {
            rvGeneral.apply {
                val itemDecorator = SimpleItemDecorator(requireContext(), RecyclerView.VERTICAL)
                addItemDecoration(itemDecorator)

                adapter = homeAdapter
            }

            swipeRefreshLayout.setOnRefreshListener {
                viewModel.nowPlayingAck = false
                viewModel.upComingAck = false

                viewModel.fetchNowPlaying()
                viewModel.fetchUpcoming()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val onAdapterItemClickListener: OnAdapterItemClick<Result> = object: OnAdapterItemClick<Result> {
        override fun onItemClick(item: Result) {
            val bundle = Bundle()
            bundle.putInt("Result", item.id)
            findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
        }
    }
}