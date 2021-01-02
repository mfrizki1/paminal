package id.calocallo.sicape.ui.main.lp.pasal

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.network.response.LpPasalResp

class PasalItemDetailsLookup(private val recyclerView: RecyclerView) :
    ItemDetailsLookup<LpPasalResp>() {
    override fun getItemDetails(event: MotionEvent): ItemDetails<LpPasalResp>? {
        val view = recyclerView.findChildViewUnder(event.x, event.y)
        if (view != null) {
            return (recyclerView.getChildViewHolder(view) as PasalAdapter1.ListViewHolder)
                .getItemDetails()
        }
        return null
    }
}