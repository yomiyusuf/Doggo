package com.yomi.doggo.ui.common

import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.yomi.doggo.R
import com.yomi.doggo.util.show

/**
 * Created by Yomi Joseph on 2020-07-26.
 */
abstract class ProgressFragment: Fragment() {
    abstract fun onRetry()
    abstract fun onLoadingStateChange(isLoading: Boolean)

    fun handleErrorState(isError: Boolean) {
        view?.findViewById<View>(R.id.error_layout)?.show(isError)
        view?.findViewById<Button>(R.id.btn_retry)?.setOnClickListener {
            onRetry()
        }
    }

    fun handleLoadingState(isLoading: Boolean) {
        view?.findViewById<ProgressBar>(R.id.progress_bar)?.show(isLoading)
        onLoadingStateChange(isLoading)
    }
}