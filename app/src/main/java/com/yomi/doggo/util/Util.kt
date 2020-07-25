package com.yomi.doggo.util

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.yomi.doggo.R
import com.yomi.doggo.ui.model.Option
import org.json.JSONArray

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

fun Fragment.vibratePhone() {
    val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= 26) {
        vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        vibrator.vibrate(200)
    }
}

fun String.capitalizeWords(): String = split(" ").joinToString(" ") { it.toLowerCase().capitalize() }

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

fun View.show(boolean: Boolean = true) {
    if (boolean) { this.visibility = View.VISIBLE } else { this.visibility = View.GONE }
}

/**
 * get the breed name from the image url of this format: https://images.dog.ceo/breeds/husky/n02110185_9855.jpg
 * https://images.dog.ceo/breeds/husky/n02110185_9855.jpg should return "husky"
 * https://images.dog.ceo/breeds/terrier-australian/n02096294_8025.jpg" should return "australian husky"
 */
fun String.getBreedNameFromUrl(): String {
    val name = this.substringAfter("breeds/").substringBeforeLast("/")
    return if (name.contains("-")) {
        val list = name.split("-")
        "${list[1]} ${list[0]}"
    } else {
        name
    }
}

fun JSONArray.toList(): List<String> {
    val str = this.toString().replace("[", "").replace("]", "")
    if (str.toList().isEmpty()) return listOf()

    return str.split(",").map { it.replace("\"", "") }
}