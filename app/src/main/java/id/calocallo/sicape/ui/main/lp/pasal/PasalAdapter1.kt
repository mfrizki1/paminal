package id.calocallo.sicape.ui.main.lp.pasal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.LpPasalResp
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.toggleVisibility
import id.calocallo.sicape.utils.ext.visible
import kotlinx.android.synthetic.main.layout_1_text_clickable.view.*

class PasalAdapter1 internal constructor(
    private val context: Context,
    private val pasalItems: ArrayList<LpPasalResp>
) : RecyclerView.Adapter<PasalAdapter1.PasalHolder>() {

    var tracker: SelectionTracker<LpPasalResp>? = null

    inner class PasalHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<LpPasalResp> =
            object : ItemDetailsLookup.ItemDetails<LpPasalResp>() {
                override fun getSelectionKey(): LpPasalResp? = pasalItems[position]

                override fun getPosition(): Int = adapterPosition

            }

        fun setPasal(lpPasalResp: LpPasalResp, selected: Boolean = false) {
            with(itemView) {
                if(selected){
                    itemView.isActivated = selected
                    txt_1_clickable.setTextColor(resources.getColor(R.color.white))
//                    itemView.img_clickable.toggleVisibility()
                }else{
                    itemView.isActivated = false
                    txt_1_clickable.setTextColor(resources.getColor(R.color.onyx))
                }
//                itemView.setOnClickListener {

//                }
                txt_1_clickable.text = lpPasalResp.nama_pasal

            }
        }

    }

    fun getItem(position: Int) = pasalItems[position]
    fun getPosition(name: String) = pasalItems.indexOfFirst { it.nama_pasal == name }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasalHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_1_text_clickable, parent, false)
        return PasalHolder(view)
    }

    override fun getItemCount(): Int {
        return pasalItems.size
    }

    override fun onBindViewHolder(holder: PasalHolder, position: Int) {
        holder.setPasal(pasalItems[position], tracker!!.isSelected(pasalItems[position]))
    }

}
