package com.example.mobilliumcase.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilliumcase.adapter.HomeAdapter
import com.example.mobilliumcase.databinding.FragmentHomeBinding
import com.example.mobilliumcase.decorator.SimpleItemDecorator
import com.example.mobilliumcase.di.DaggerHomeFragmentViewModelComponent
import com.example.mobilliumcase.viewmodel.HomeFragmentViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

private const val TAG = "HomeFragment"
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

        viewModel.apply {
            getNowPlayingObservable().observe(viewLifecycleOwner) {
                Log.d(TAG, "getNowPlayingObservable: Data has returned! Size: " + it.results.size)

                homeAdapter.setSliderItems(it.results)
                homeAdapter.notifyItemChanged(0)

                binding.swipeRefreshLayout.isRefreshing = false
            }

            getUpcomingObservable().observe(viewLifecycleOwner) {
                Log.d(TAG, "getUpcomingObservable: Data has returned! Size: " + it.results.size)

                homeAdapter.setUpcomingItems(it.results)
                homeAdapter.notifyItemRangeChanged(1, it.results.size)

                binding.swipeRefreshLayout.isRefreshing = false
            }

            fetchNowPlaying()
            fetchUpcoming()
        }

        binding.apply {
            rvGeneral.apply {
                val itemDecorator = SimpleItemDecorator(requireContext(), RecyclerView.VERTICAL)
                addItemDecoration(itemDecorator)

                adapter = homeAdapter
            }

            swipeRefreshLayout.setOnRefreshListener {
                viewModel.fetchUpcoming()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}