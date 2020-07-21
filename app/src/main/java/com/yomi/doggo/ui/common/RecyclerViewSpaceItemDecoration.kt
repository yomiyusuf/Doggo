package com.yomi.doggo.ui.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class RecyclerViewSpaceItemDecoration(
    private val horizontalSpaceWidth: Int = 0, private val verticalSpaceHeight: Int = 0) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.right = horizontalSpaceWidth
        outRect.bottom = verticalSpaceHeight
    }

}