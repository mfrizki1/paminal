package id.calocallo.sicape.ui.main.lp.pasal.tes

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.network.response.PasalResp
import id.calocallo.sicape.ui.main.lp.pasal.tes.PasalTesAdapter

class PasalTesDetailsLookup(private val recyclerView: RecyclerView) :
    ItemDetailsLookup<PasalResp>() {
    override fun getItemDetails(event: MotionEvent): ItemDetails<PasalResp>? {
        val view = recyclerView.findChildViewUnder(event.x, event.y)
        if (view != null) {
            return (recyclerView.getChildViewHolder(view) as PasalTesAdapter.PasalHolder)
                .getItemDetails()
        }
        return null
    }
}