package id.calocallo.sicape.ui.main.lp.saksi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.LpSaksiResp
import kotlinx.android.synthetic.main.layout_1_text_clickable.view.*

class SaksiLpAdapter internal constructor(
    private val context: Context,
    private val saksiItems: ArrayList<LpSaksiResp>
):RecyclerView.Adapter<SaksiLpAdapter.SaksiLpHolder>() {
    var tracker: SelectionTracker<LpSaksiResp>? = null
    inner class SaksiLpHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun getItemDetails():ItemDetailsLookup.ItemDetails<LpSaksiResp> =
            object: ItemDetailsLookup.ItemDetails<LpSaksiResp>(){
                override fun getSelectionKey(): LpSaksiResp? =saksiItems[position]
                override fun getPosition(): Int= adapterPosition
            }
        fun setSaksi(
            lpSaksiResp: LpSaksiResp,
            isActivated: Boolean = false
        ) {
            with(itemView){
                if(isActivated){
                    itemView.isActivated = isActivated
                    txt_1_clickable.setTextColor(resources.getColor(R.color.white))
//                    itemView.img_clickable.toggleVisibility()
                }else{
                    itemView.isActivated = false
                    txt_1_clickable.setTextColor(resources.getColor(R.color.onyx))
                }
                txt_1_clickable.text= lpSaksiResp.nama
            }
        }

    }

    fun getItem(position: Int)= saksiItems[position]
    fun getPosition(name: String) = saksiItems.indexOfFirst{it.nama == name}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaksiLpHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_1_text_clickable, parent, false)
        return SaksiLpHolder(view)
    }

    override fun getItemCount(): Int  = saksiItems.size

    override fun onBindViewHolder(holder: SaksiLpHolder, position: Int) {
        tracker?.isSelected(saksiItems[position])?.let { holder.setSaksi(saksiItems[position], it) }
    }
}