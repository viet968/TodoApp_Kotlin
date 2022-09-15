package com.example.studentmanakotlin.Adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class StackItem(var mSpace:Int): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        var position:Int = parent.getChildAdapterPosition(view)
        outRect.right = -mSpace
    }
}