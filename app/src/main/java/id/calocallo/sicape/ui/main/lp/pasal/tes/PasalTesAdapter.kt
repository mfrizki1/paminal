package id.calocallo.sicape.ui.main.lp.pasal.tes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.network.response.PasalResp
import id.calocallo.sicape.R
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import java.util.*
import kotlin.collections.ArrayList

class PasalTesAdapter internal constructor(
    private val context: Context,
    private val pasalResp: ArrayList<PasalResp>
) : RecyclerView.Adapter<PasalTesAdapter.PasalHolder>(), Filterable {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    var tracker: SelectionTracker<PasalResp>? = null
    var filterList = ArrayList<PasalResp>()

    init {
        filterList = pasalResp
    }

    inner class PasalHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtPasal = itemView.findViewById<TextView>(R.id.txt_1_clickable)
        private val imgClick = itemView.findViewById<ImageView>(R.id.img_clickable)
        fun getItemDetails(): ItemDetailsLookup.ItemDetails<PasalResp> =
            object : ItemDetailsLookup.ItemDetails<PasalResp>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): PasalResp? = filterList[position]
            }

        fun setPasal(pasalResp: PasalResp, selected: Boolean) {
            txtPasal.text = pasalResp.nama_pasal
            itemView.isActivated = selected
            if (itemView.isActivated) {
                imgClick.visible()
            } else {
                imgClick.gone()
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasalHolder {
        val itemView = inflater.inflate(R.layout.layout_1_text_clickable, parent, false)
        return PasalHolder(itemView)
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    override fun onBindViewHolder(holder: PasalHolder, position: Int) {
        tracker?.let {
            holder.setPasal(filterList[position], it.isSelected(filterList[position]))
        }
    }

    fun getItem(position: Int): PasalResp = pasalResp[position]

    fun getPosition(name: String) = filterList.indexOfFirst { it.nama_pasal == name }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filterList = pasalResp
                } else {
                    val resultList = ArrayList<PasalResp>()
                    for (row in pasalResp) {
                        if (row.toString().toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    filterList = resultList
                }
                val filterResult = FilterResults()
                filterResult.values = filterList
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterList = results?.values as ArrayList<PasalResp>
                notifyDataSetChanged()
            }

        }
    }
}