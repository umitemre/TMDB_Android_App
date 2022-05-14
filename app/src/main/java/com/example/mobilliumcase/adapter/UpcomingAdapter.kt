package com.example.mobilliumcase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilliumcase.databinding.UpcomingItemBinding
import com.example.mobilliumcase.listener.AdapterItemClickCallback
import com.example.mobilliumcase.model.Result
import javax.inject.Inject

class UpcomingAdapter @Inject constructor(private var listener: AdapterItemClickCallback<Result?>) :
    PagingDataAdapter<Result, UpcomingAdapter.UpcomingViewHolder>(UpcomingItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UpcomingItemBinding.inflate(inflater, parent, false)

        return UpcomingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UpcomingViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class UpcomingViewHolder(private var binding: UpcomingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            itemView.isClickable = true
            itemView.isFocusable = true

            val result = getItem(position)

            itemView.setOnClickListener {
                listener.onItemClick(result)
            }

            binding.upcomingItem = result
        }
    }
}

private class UpcomingItemCallback : DiffUtil.ItemCallback<Result>() {
    override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem.id == newItem.id
    }
}