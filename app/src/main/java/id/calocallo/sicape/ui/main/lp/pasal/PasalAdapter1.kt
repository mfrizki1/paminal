package id.calocallo.sicape.ui.main.lp.pasal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.PasalDilanggarResp

class PasalAdapter1 internal constructor(
    private val context: Context,
    private val pasalDilanggarItems: List<PasalDilanggarResp>
) :
    RecyclerView.Adapter<PasalAdapter1.ListViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    var tracker: SelectionTracker<PasalDilanggarResp>? = null

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtPasal: TextView = itemView.findViewById(R.id.txt_1_clickable)
//        private val container: LinearLayout = itemView.findViewById(R.id.linear_layout_container)

        fun setPasalItems(pasalDilanggar: PasalDilanggarResp, isActivated: Boolean = false) {
//            with(itemView){
            txtPasal.text = pasalDilanggar.pasal?.nama_pasal
            itemView.isActivated = isActivated

//            }
//            postImageView.setImageResource(postItem.image)
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<PasalDilanggarResp> =
            object : ItemDetailsLookup.ItemDetails<PasalDilanggarResp>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): PasalDilanggarResp? = pasalDilanggarItems[position]
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemView = inflater.inflate(R.layout.layout_1_text_clickable, parent, false)
        return ListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        tracker?.let {
            holder.setPasalItems(pasalDilanggarItems[position], it.isSelected(pasalDilanggarItems[position]))
        }

    }

    override fun getItemCount(): Int {
        return pasalDilanggarItems.size
    }

    fun getItem(position: Int) = pasalDilanggarItems[position]
    fun getPosition(name: String) = pasalDilanggarItems.indexOfFirst { it.pasal?.nama_pasal == name }
}

