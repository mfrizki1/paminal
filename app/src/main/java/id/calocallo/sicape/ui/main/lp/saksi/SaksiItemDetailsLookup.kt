package id.calocallo.sicape.ui.main.lp.saksi

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.network.response.LpSaksiResp
import id.calocallo.sicape.ui.main.lhp.SaksiAdapter
import id.calocallo.sicape.ui.main.lp.pasal.PasalAdapter1

class SaksiItemDetailsLookup(private val rv: RecyclerView):
    ItemDetailsLookup<LpSaksiResp>() {
    override fun getItemDetails(e: MotionEvent): ItemDetails<LpSaksiResp>? {
        val view = rv.findChildViewUnder(e.x, e.y)
        if(view!= null){
            return (rv.getChildViewHolder(view) as SaksiLpAdapter.SaksiLpHolder).getItemDetails()
        }
        return null
    }
}