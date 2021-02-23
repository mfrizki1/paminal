package id.calocallo.sicape.ui.main.lp.pasal.tes

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.network.response.PasalResp
import id.calocallo.sicape.network.response.PersonelMinResp
import id.calocallo.sicape.ui.main.choose.multiple.PersonelMultipleAdapter
import id.calocallo.sicape.ui.main.lp.pasal.PasalMultipleAdapter
import id.calocallo.sicape.ui.main.lp.pasal.tes.PasalTesAdapter


class PasalTesDetailsLookup(private val recyclerView: RecyclerView) :
    ItemDetailsLookup<PasalResp>() {
    override fun getItemDetails(e: MotionEvent): ItemDetails<PasalResp>? {
        val view = recyclerView.findChildViewUnder(e.x, e.y)
        if (view != null) {
            return (recyclerView.getChildViewHolder(view) as PasalTesAdapter.PasalHolder)
                .getItemDetails()
        }
        return null
    }

}