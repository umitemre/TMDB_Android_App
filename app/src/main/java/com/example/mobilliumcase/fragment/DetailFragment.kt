package com.example.mobilliumcase.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mobilliumcase.databinding.FragmentDetailBinding
import com.example.mobilliumcase.di.DaggerDetailFragmentViewModelComponent
import com.example.mobilliumcase.viewmodel.DetailFragmentViewModel
import dagger.android.support.DaggerFragment

private const val TAG = "DetailFragment"

class DetailFragment : DaggerFragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelComponent = DaggerDetailFragmentViewModelComponent.create()
        viewModelComponent.inject(viewModel)

        val movieId = arguments?.getInt("Result") ?: return

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.getDetailObservable().observe(viewLifecycleOwner, {
            if (it.id == 0)
                return@observe

            Log.d(TAG, "getDetailObservable: Data is returned. Movie ID: ${it.id}")
        })

        viewModel.fetchMovieDetail(movieId)

        binding.ibImdb.setOnClickListener {
            val imdbId = binding.ibImdb.tag as String
            if (imdbId.isNotEmpty())
                openWebPage("https://www.imdb.com/title/${binding.ibImdb.tag}/")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun openWebPage(url: String) {
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)

        if (requireContext().packageManager.resolveActivity(intent, 0) != null) {
            startActivity(intent)
        }
    }
}