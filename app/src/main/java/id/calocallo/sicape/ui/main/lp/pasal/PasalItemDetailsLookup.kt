package id.calocallo.sicape.ui.main.lp.pasal

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.network.response.LpPasalResp

class PasalItemDetailsLookup(private val rv: RecyclerView): ItemDetailsLookup<LpPasalResp>() {
    override fun getItemDetails(e: MotionEvent): ItemDetails<LpPasalResp>? {
        val view = rv.findChildViewUnder(e.x, e.y)
        if(view!= null){
            return (rv.getChildViewHolder(view) as PasalAdapter1.PasalHolder)
                .getItemDetails()
        }
        return null
    }

}