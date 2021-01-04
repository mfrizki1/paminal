package id.calocallo.sicape.ui.main.lp.pidana.tes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.LpSaksiResp

class SelectedSaksiAdapter internal constructor(
    private val context: Context,
    private val saksiItem: ArrayList<LpSaksiResp>
) : RecyclerView.Adapter<SelectedSaksiAdapter.SelectedSaksiHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    var tracker: SelectionTracker<LpSaksiResp>? = null

    inner class SelectedSaksiHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtSaksi = itemView.findViewById<TextView>(R.id.txt_detail_1)
        private val txtIsKorban = itemView.findViewById<TextView>(R.id.txt_detail_2)
        fun getItemDetails(): ItemDetailsLookup.ItemDetails<LpSaksiResp> =
            object : ItemDetailsLookup.ItemDetails<LpSaksiResp>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): LpSaksiResp? = saksiItem[position]
            }

        fun setSaksi(saksiItem: LpSaksiResp, selected: Boolean) {
            txtSaksi.text = saksiItem.nama
            txtIsKorban.text = saksiItem.is_korban
            itemView.isActivated = selected

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedSaksiHolder {
        val itemView = inflater.inflate(R.layout.item_2_text, parent, false)
        return SelectedSaksiHolder(itemView)
    }

    override fun getItemCount(): Int {
        return saksiItem.size
    }

    override fun onBindViewHolder(holder: SelectedSaksiHolder, position: Int) {
        tracker?.let {
            holder.setSaksi(saksiItem[position], it.isSelected(saksiItem[position]))
        }
    }

    fun getItem(position: Int): LpSaksiResp = saksiItem[position]

    fun getPosition(name: String) = saksiItem.indexOfFirst { it.nama == name }
}