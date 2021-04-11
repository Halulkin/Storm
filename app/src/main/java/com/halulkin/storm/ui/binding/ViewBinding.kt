package com.halulkin.storm.ui.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.storm.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

object ViewBinding {

    @JvmStatic
    @BindingAdapter("bindFloat")
    fun bindFloat(view: TextView, float: Float) {
        view.text = float.roundToInt().toString()
    }

    @JvmStatic
    @BindingAdapter("bindDate")
    fun bindDate(view: TextView, long: Long?) {
        if (long != null) view.text =
            SimpleDateFormat("d MMM yyyy", Locale("en")).format(long * 1000L)
    }

    @JvmStatic
    @BindingAdapter("bindTime")
    fun bindTime(view: TextView, long: Long) {
        view.text = SimpleDateFormat("hh:mm a", Locale("en")).format(long * 1000L)
    }

    @JvmStatic
    @BindingAdapter("bindImage")
    fun bindImage(view: ImageView, urlImage: String?) {
        if (urlImage != null) {
            val iconUrl = "http://openweathermap.org/img/wn/$urlImage@2x.png"
            Glide.with(view.context).load(iconUrl).error(R.drawable.ic_01d).into(view)
        }
    }
}

