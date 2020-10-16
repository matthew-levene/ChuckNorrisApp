package com.ml.chucknorrisapp.view.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.ml.chucknorrisapp.model.network.NetworkConstants.CHUCK_NORRIS_IMAGE

@BindingAdapter("loadImage")
fun loadChuckNorrisImage(imageView: ImageView, url: String?){
    Glide
        .with(imageView.context)
        .load(CHUCK_NORRIS_IMAGE)
        .centerCrop()
        .into(imageView)
}