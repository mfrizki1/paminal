package id.calocallo.sicape.ui.main.choose.multiple

import android.app.Person
import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.model.AllPersonelModel
import id.calocallo.sicape.network.response.PersonelMinResp
import id.calocallo.sicape.ui.main.lp.pasal.tes.PasalTesAdapter

class PersonelDetailsLookup(private val recyclerView: RecyclerView) :
    ItemDetailsLookup<PersonelMinResp>() {
    override fun getItemDetails(e: MotionEvent): ItemDetails<PersonelMinResp>? {
        val view= recyclerView.findChildViewUnder(e.x, e.y)
        if(view != null){
            return (recyclerView.getChildViewHolder(view) as PersonelMultipleAdapter.PersMultipHolder)
                .getItemDetails()
        }
        return null
    }

}