package id.calocallo.sicape.ui.main.lp.pidana.tes

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.network.response.LpSaksiResp
import id.calocallo.sicape.ui.main.lp.pasal.tes.PasalTesAdapter

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