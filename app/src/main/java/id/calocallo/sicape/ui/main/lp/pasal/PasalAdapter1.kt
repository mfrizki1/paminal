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
import id.calocallo.sicape.network.response.LpPasalResp
import kotlinx.android.synthetic.main.layout_1_text_clickable.view.*

class PasalAdapter1 internal constructor(
    private val context: Context,
    private val pasalItems: List<LpPasalResp>
) :
    RecyclerView.Adapter<PasalAdapter1.ListViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    var tracker: SelectionTracker<LpPasalResp>? = null

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtPasal: TextView = itemView.findViewById(R.id.txt_1_clickable)
//        private val container: LinearLayout = itemView.findViewById(R.id.linear_layout_container)

        fun setPasalItems(pasal: LpPasalResp, isActivated: Boolean = false) {
//            with(itemView){
            txtPasal.text = pasal.nama_pasal
            itemView.isActivated = isActivated

//            }
//            postImageView.setImageResource(postItem.image)
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<LpPasalResp> =
            object : ItemDetailsLookup.ItemDetails<LpPasalResp>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): LpPasalResp? = pasalItems[position]
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemView = inflater.inflate(R.layout.layout_1_text_clickable, parent, false)
        return ListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        tracker?.let {
            holder.setPasalItems(pasalItems[position], it.isSelected(pasalItems[position]))
        }

    }

    override fun getItemCount(): Int {
        return pasalItems.size
    }

    fun getItem(position: Int) = pasalItems[position]
    fun getPosition(name: String) = pasalItems.indexOfFirst { it.nama_pasal == name }
}
