package com.example.mobilliumcase.listener

fun interface AdapterItemClickCallback<T> {
    fun onItemClick(item: T)
}