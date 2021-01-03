package id.calocallo.sicape.ui.main.lp.pasal.tes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.zanyastudios.test.PasalItem
import id.calocallo.sicape.R

class PasalTesAdapter internal constructor(
    private val context: Context,
    private val pasalItem: ArrayList<PasalItem>
) : RecyclerView.Adapter<PasalTesAdapter.PasalHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    var tracker: SelectionTracker<PasalItem>? = null

    inner class PasalHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtPasal = itemView.findViewById<TextView>(R.id.txt_1_clickable)
        fun getItemDetails(): ItemDetailsLookup.ItemDetails<PasalItem> =
            object : ItemDetailsLookup.ItemDetails<PasalItem>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): PasalItem? = pasalItem[position]
            }

        fun setPasal(pasalItem: PasalItem, selected: Boolean) {
            txtPasal.text = pasalItem.nama_pasal
            itemView.isActivated = selected

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasalHolder {
        val itemView = inflater.inflate(R.layout.layout_1_text_clickable, parent, false)
        return PasalHolder(itemView)
    }

    override fun getItemCount(): Int {
        return pasalItem.size
    }

    override fun onBindViewHolder(holder: PasalHolder, position: Int) {
        tracker?.let {
            holder.setPasal(pasalItem[position], it.isSelected(pasalItem[position]))
        }
    }

    fun getItem(position: Int): PasalItem = pasalItem[position]

    fun getPosition(name: String) = pasalItem.indexOfFirst { it.nama_pasal == name }
}