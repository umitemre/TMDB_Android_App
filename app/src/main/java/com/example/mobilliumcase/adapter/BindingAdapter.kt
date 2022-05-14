package com.example.mobilliumcase.adapter

import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.mobilliumcase.model.MovieDetail
import com.example.mobilliumcase.model.Result
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("app:w500Image", requireAll = false)
fun setImage(imageView: AppCompatImageView, path: String?) {
    path?.let {
        Glide.with(imageView.context)
            .load("https://image.tmdb.org/t/p/w500$it")
            .into(imageView)
    }
}

@BindingAdapter("app:date", requireAll = false)
fun setDate(textView: TextView, fromDate: String?) {
    if (fromDate.isNullOrEmpty())
        return

    val fromDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val toDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

    val parsedDate = fromDateFormat.parse(fromDate) ?: return

    textView.text = toDateFormat.format(parsedDate)
}

@BindingAdapter("app:titleOf", requireAll = false)
fun getTitleWithReleaseYear(textView: TextView, movieDetail: MovieDetail?) {
    val fromDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

    if (movieDetail == null)
        return

    try {
        val parsedDate: Date? = fromDateFormat.parse(movieDetail.release_date)
        parsedDate?.let {
            val instance = Calendar.getInstance()
            instance.time = it

            val title = "${movieDetail.title} (${instance.get(Calendar.YEAR)})"
            textView.text = title
        }
    } catch (exception: Exception) {
        textView.text = movieDetail.title
        return
    }
}

@BindingAdapter("app:titleOf", requireAll = false)
fun getTitleWithReleaseYear(textView: TextView, result: Result) {
    val fromDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

    try {
        val parsedDate: Date? = fromDateFormat.parse(result.release_date)
        parsedDate?.let {
            val instance = Calendar.getInstance()
            instance.time = it

            val title = "${result.title} (${instance.get(Calendar.YEAR)})"
            textView.text = title
        }
    } catch (exception: Exception) {
        textView.text = result.title
        return
    }
}