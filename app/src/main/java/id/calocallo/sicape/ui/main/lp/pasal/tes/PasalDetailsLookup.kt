package com.zanyastudios.test

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.ui.main.lp.pasal.tes.PasalAdapter

class PasalDetailsLookup(private val recyclerView: RecyclerView) :
    ItemDetailsLookup<PasalItem>() {
    override fun getItemDetails(event: MotionEvent): ItemDetails<PasalItem>? {
        val view = recyclerView.findChildViewUnder(event.x, event.y)
        if (view != null) {
            return (recyclerView.getChildViewHolder(view) as PasalAdapter.PasalHolder)
                .getItemDetails()
        }
        return null
    }
}