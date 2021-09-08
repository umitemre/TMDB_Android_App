package com.example.mobilliumcase.provider

abstract class Factory<T> {
    private var mListener: OnProvidedListener<T>? = null

    protected abstract fun provide(mListener: OnProvidedListener<T>)

    fun produce() {
        if (mListener == null) {
            return
        }

        provide(mListener!!)
    }

    fun setOnProvidedListener(listener: OnProvidedListener<T>) {
        this.mListener = listener
    }
}

interface OnProvidedListener<T> {
    fun onProvided(result: T)
}
