package com.example.mobilliumcase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilliumcase.databinding.SliderContainerBinding
import com.example.mobilliumcase.databinding.UpcomingItemBinding
import com.example.mobilliumcase.model.Result
import javax.inject.Inject

const val SLIDER_ROW = 1
const val UPCOMING_ROW = 2

class HomeAdapter @Inject constructor() :
    RecyclerView.Adapter<HomeAdapter.BaseViewHolder>() {

    private var sliderItems: List<Result>? = null
    private var upcomingItems: List<Result>? = null

    @Inject
    lateinit var sliderAdapter: SliderAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        if (SLIDER_ROW == viewType) {
            val binding = SliderContainerBinding.inflate(inflater, parent, false)
            return SliderViewHolder(binding)
        }

        val binding = UpcomingItemBinding.inflate(inflater, parent, false)
        return UpcomingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        if (upcomingItems == null)
            return 1

        return upcomingItems!!.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0)
            return SLIDER_ROW

        return UPCOMING_ROW
    }

    fun setSliderItems(results: List<Result>) {
        this.sliderItems = results
    }

    fun setUpcomingItems(results: List<Result>) {
        this.upcomingItems = results
    }

    abstract inner class BaseViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        abstract fun bind(position: Int)
    }

    inner class SliderViewHolder(private var binding: SliderContainerBinding) :
        HomeAdapter.BaseViewHolder(binding.root) {

        override fun bind(position: Int) {
            sliderAdapter.setResults(sliderItems)

            binding.vpSlider.adapter = sliderAdapter
        }
    }

    inner class UpcomingViewHolder(private var binding: UpcomingItemBinding) :
        HomeAdapter.BaseViewHolder(binding.root) {

        override fun bind(position: Int) {
            if (upcomingItems == null) {
                return
            }

            binding.upcomingItem = upcomingItems!![position - 1]
        }
    }
}