package id.calocallo.sicape.ui.main.lp.saksi.select_saksi

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.network.response.LpSaksiResp

class SelectedSaksiDetailsLookup(private val recyclerView: RecyclerView) :
    ItemDetailsLookup<LpSaksiResp>() {
    override fun getItemDetails(event: MotionEvent): ItemDetails<LpSaksiResp>? {
        val view = recyclerView.findChildViewUnder(event.x, event.y)
        if (view != null) {
            return (recyclerView.getChildViewHolder(view) as SelectedSaksiAdapter.SelectedSaksiHolder)
                .getItemDetails()
        }
        return null
    }
}