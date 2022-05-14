package com.example.mobilliumcase.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobilliumcase.R
import com.example.mobilliumcase.adapter.SliderAdapter
import com.example.mobilliumcase.adapter.UpcomingAdapter
import com.example.mobilliumcase.databinding.FragmentHomeBinding
import com.example.mobilliumcase.listener.AdapterItemClickCallback
import com.example.mobilliumcase.model.Result
import com.example.mobilliumcase.viewmodel.HomeFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var upcomingAdapter: UpcomingAdapter

    private val viewModel: HomeFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.swipeToRefresh.setOnRefreshListener {
            upcomingAdapter.refresh()
        }

        setAdapters()
        setObservers()

        return binding.root
    }

    private fun setAdapters() {
        sliderAdapter = SliderAdapter(listener = listener)
        upcomingAdapter = UpcomingAdapter(listener = listener).apply {
            addLoadStateListener {
                if (it.refresh is LoadState.NotLoading || it.refresh is LoadState.Error) {
                    binding.swipeToRefresh.isRefreshing = false
                }
            }
        }

        binding.vpSlider.adapter = sliderAdapter
        binding.rvUpcoming.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = upcomingAdapter
        }
    }

    private fun setObservers() {
        lifecycleScope.launch {
            launch {
                viewModel.fetchNowPlaying().collectLatest {
                    sliderAdapter.submitList(it.results)
                }
            }

            launch {
                viewModel.fetchUpcoming().collectLatest {
                    upcomingAdapter.submitData(it)
                }
            }
        }
    }

    private val listener = AdapterItemClickCallback<Result?> { item ->
        item?.let {
            findNavController().navigate(R.id.action_homeFragment_to_detailFragment, Bundle().apply {
                putInt("Result", it.id)
            })
        }
    }
}