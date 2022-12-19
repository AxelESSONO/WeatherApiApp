package com.axel.weatherapiapp.binding

import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.axel.weatherapiapp.utils.UnixTimeUtils
import com.axel.weatherapplibrary.model.Current
import com.squareup.picasso.Picasso


object BindingAdapters {

        @JvmStatic
        @BindingAdapter("visibleGone")
        fun showHide(view: View, show: Boolean) {
            view.visibility = if (show) View.VISIBLE else View.GONE
        }

        @JvmStatic
        @BindingAdapter("android:imageUrl")
        fun setImageUrl(view: ImageView, imageUrl: Int?) {
            // imageUrl != null && imageUrl.isNotEmpty()
            if (imageUrl != null) {
                Picasso.get()
                    .load(imageUrl)
                    .fit()
                    .centerCrop()
                    .into(view)
            }
        }

    @JvmStatic
    @BindingAdapter("text")
    fun setText(view: TextView, text: Double?) {
        view.text = "$text"
    }

}