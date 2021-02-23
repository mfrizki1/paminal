package id.calocallo.sicape.ui.main.lp.pasal.tes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.network.response.PasalResp
import id.calocallo.sicape.R

class PasalTesAdapter internal constructor(
    private val context: Context,
    private val pasalResp: ArrayList<PasalResp>
) : RecyclerView.Adapter<PasalTesAdapter.PasalHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    var tracker: SelectionTracker<PasalResp>? = null

    inner class PasalHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtPasal = itemView.findViewById<TextView>(R.id.txt_1_clickable)
        fun getItemDetails(): ItemDetailsLookup.ItemDetails<PasalResp> =
            object : ItemDetailsLookup.ItemDetails<PasalResp>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): PasalResp? = pasalResp[position]
            }

        fun setPasal(pasalResp: PasalResp, selected: Boolean) {
            txtPasal.text = pasalResp.nama_pasal
            itemView.isActivated = selected

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasalHolder {
        val itemView = inflater.inflate(R.layout.layout_1_text_clickable, parent, false)
        return PasalHolder(itemView)
    }

    override fun getItemCount(): Int {
        return pasalResp.size
    }

    override fun onBindViewHolder(holder: PasalHolder, position: Int) {
        tracker?.let {
            holder.setPasal(pasalResp[position], it.isSelected(pasalResp[position]))
        }
    }

    fun getItem(position: Int): PasalResp = pasalResp[position]

    fun getPosition(name: String) = pasalResp.indexOfFirst { it.nama_pasal == name }
}