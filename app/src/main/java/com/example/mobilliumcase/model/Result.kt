package com.example.mobilliumcase.model

import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

data class Result(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
) {
    companion object {
        @JvmStatic
        @BindingAdapter("app:w500Image", requireAll = false)
        fun setImage(imageView: AppCompatImageView, path: String?) {
            Glide.with(imageView.context)
                .load("https://image.tmdb.org/t/p/w500$path")
                .into(imageView)
        }

        @JvmStatic
        @BindingAdapter("app:date", requireAll = false)
        fun setDate(textView: TextView, fromDate: String) {
            if (fromDate.isEmpty())
                return

            val fromDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val toDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

            val parsedDate = fromDateFormat.parse(fromDate) ?: return

            textView.text = toDateFormat.format(parsedDate)
        }
    }

    fun getTitleWithReleaseYear(): String {
        val fromDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val parsedDate = fromDateFormat.parse(release_date) ?: return title

        val instance = Calendar.getInstance()
        instance.time = parsedDate

        return "$title (${instance.get(Calendar.YEAR)})"
    }
}