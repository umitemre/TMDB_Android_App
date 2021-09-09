package com.example.mobilliumcase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilliumcase.databinding.SliderItemBinding
import com.example.mobilliumcase.listener.OnAdapterItemClick
import com.example.mobilliumcase.model.Result
import javax.inject.Inject

class SliderAdapter @Inject constructor() :
    RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {

    private var sliderItems: List<Result>? = null

    lateinit var onAdapterItemClickListener: OnAdapterItemClick<Result>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SliderItemBinding.inflate(inflater, parent, false)

        return SliderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        if (sliderItems == null)
            return 0

        return sliderItems!!.size
    }

    fun setResults(results: List<Result>?) {
        this.sliderItems = results
    }

    inner class SliderViewHolder(private var binding: SliderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            if (sliderItems == null) {
                return
            }

            itemView.isClickable = true
            itemView.isFocusable = true

            val result = sliderItems!![position]

            itemView.setOnClickListener {
                onAdapterItemClickListener.onItemClick(result)
            }

            binding.sliderItem = sliderItems!![position]
        }
    }
}