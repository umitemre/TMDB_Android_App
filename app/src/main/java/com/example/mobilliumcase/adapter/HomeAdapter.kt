package com.example.mobilliumcase.adapter

import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.mobilliumcase.R
import com.example.mobilliumcase.databinding.SliderContainerBinding
import com.example.mobilliumcase.databinding.UpcomingItemBinding
import com.example.mobilliumcase.listener.OnAdapterItemClick
import com.example.mobilliumcase.model.Result
import java.util.*
import javax.inject.Inject

const val SLIDER_ROW = 1
const val UPCOMING_ROW = 2

class HomeAdapter @Inject constructor() :
    RecyclerView.Adapter<HomeAdapter.BaseViewHolder>() {

    private var sliderItems: List<Result>? = null
    private var upcomingItems: ArrayList<Result> = ArrayList()

    @Inject
    lateinit var sliderAdapter: SliderAdapter

    lateinit var onAdapterItemClickListener: OnAdapterItemClick<Result>

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
        return upcomingItems.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0)
            return SLIDER_ROW

        return UPCOMING_ROW
    }

    fun setSliderItems(results: List<Result>) {
        this.sliderItems = results
    }

    fun appendUpcomingItems(results: ArrayList<Result>) {
        this.upcomingItems.addAll(results)
    }

    fun clearUpcomingItems() {
        this.upcomingItems.clear()
    }

    abstract inner class BaseViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        abstract fun bind(position: Int)
    }

    inner class SliderViewHolder(private var binding: SliderContainerBinding) :
        HomeAdapter.BaseViewHolder(binding.root) {

        var dots: ArrayList<TextView> = ArrayList()

        override fun bind(position: Int) {
            sliderAdapter.onAdapterItemClickListener = onAdapterItemClickListener
            sliderAdapter.setResults(sliderItems)

            binding.vpSlider.adapter = sliderAdapter

            binding.vpSlider.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    setSelectedDot(position)
                }
            })


            sliderItems?.size?.let {
                createDots(it)
            }
        }

        private fun setSelectedDot(position: Int) {
            for (i in 0 until dots.size) {
                if (i == position) {
                    dots[i].setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.active_dot_color
                        )
                    )
                } else {
                    dots[i].setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.inactive_dot_color
                        )
                    )
                }
            }
        }

        private fun createDots(count: Int) {
            // Clear dots
            dots.clear()

            binding.dotsContainer.removeAllViews()

            for (i in 0 until count) {
                val t = TextView(itemView.context)
                t.text = Html.fromHtml("&#9679;")
                t.textSize = 18.0f
                t.includeFontPadding = false
                t.setPadding(0, 0, 8, 8)
                dots.add(t)

                binding.dotsContainer.addView(t)
            }
        }
    }

    inner class UpcomingViewHolder(private var binding: UpcomingItemBinding) :
        HomeAdapter.BaseViewHolder(binding.root) {

        override fun bind(position: Int) {
            itemView.isClickable = true
            itemView.isFocusable = true

            val result = upcomingItems[position - 1]

            Log.d("TAG", "bind: $result")

            itemView.setOnClickListener {
                onAdapterItemClickListener.onItemClick(result)
            }

            binding.upcomingItem = result
        }
    }
}