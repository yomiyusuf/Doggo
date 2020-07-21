package com.yomi.doggo.util

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.yomi.doggo.R
import com.yomi.doggo.ui.model.Option

fun getProgressDrawable(context: Context): CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher_round)

    Glide.with(context)
        .load(uri)
        .apply(options)
        .into(this)
}

fun View.visibleOrGone(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun String.capitalizeWords(): String = split(" ").joinToString(" ") { it.capitalize() }

fun Button.updateState(option: Option) {
    this.isEnabled = !option.isSelected
    if (option.isSelected && !option.isCorrect) {
        this.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.ic_wrong,0)
    }

    if (option.isSelected && option.isCorrect) {
        this.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0, R.drawable.ic_check,0)
    }
}

fun Button.updateState(options: List<Option>) {
    val myOption = options.find { it.name.toLowerCase() == this.text.toString().toLowerCase() }
    myOption?.let { this.updateState(it) }
}

fun View.show(boolean: Boolean) {
    if (boolean) { this.visibility =View.VISIBLE } else { this.visibility = View.GONE }
}