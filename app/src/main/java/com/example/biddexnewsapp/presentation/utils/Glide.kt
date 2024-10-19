package com.example.biddexnewsapp.presentation.utils

import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide


fun ImageView.loadImageFromUrl(url: String?) {
    val circularProgressDrawable = context?.let { CircularProgressDrawable(it) }
    circularProgressDrawable?.strokeWidth = 5f
    circularProgressDrawable?.centerRadius = 30f
    circularProgressDrawable?.start()
    Glide.with(this.context)
        .load(url)
        .placeholder(circularProgressDrawable)
        .into(this)
}