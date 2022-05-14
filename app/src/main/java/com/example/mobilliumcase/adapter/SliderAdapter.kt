package com.example.mobilliumcase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilliumcase.databinding.SliderItemBinding
import com.example.mobilliumcase.listener.AdapterItemClickCallback
import com.example.mobilliumcase.model.Result
import javax.inject.Inject

class SliderAdapter @Inject constructor(private var listener: AdapterItemClickCallback<Result?>) :
    ListAdapter<Result, SliderAdapter.SliderViewHolder>(SliderItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SliderItemBinding.inflate(inflater, parent, false)

        return SliderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class SliderViewHolder(private var binding: SliderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            itemView.isClickable = true
            itemView.isFocusable = true

            val result = getItem(position)

            itemView.setOnClickListener {
                listener.onItemClick(result)
            }

            binding.sliderItem = result
        }
    }
}

private class SliderItemCallback : DiffUtil.ItemCallback<Result>() {
    override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem.id == newItem.id
    }
}