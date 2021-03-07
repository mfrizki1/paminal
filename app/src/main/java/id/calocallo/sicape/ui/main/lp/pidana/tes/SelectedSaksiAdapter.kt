package id.calocallo.sicape.ui.main.lp.pidana.tes

import java.util.*
import kotlin.collections.ArrayList
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.LpSaksiResp

class SelectedSaksiAdapter internal constructor(
    private val context: Context,
    private val saksiItem: ArrayList<LpSaksiResp>
) : RecyclerView.Adapter<SelectedSaksiAdapter.SelectedSaksiHolder>(), Filterable {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    var tracker: SelectionTracker<LpSaksiResp>? = null
    var filterList = ArrayList<LpSaksiResp>()

    init {
        filterList = saksiItem
    }

    inner class SelectedSaksiHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtSaksi = itemView.findViewById<TextView>(R.id.txt_detail_1)
        private val txtIsKorban = itemView.findViewById<TextView>(R.id.txt_detail_2)
        fun getItemDetails(): ItemDetailsLookup.ItemDetails<LpSaksiResp> =
            object : ItemDetailsLookup.ItemDetails<LpSaksiResp>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): LpSaksiResp? = filterList[position]
            }

        fun setSaksi(saksiItem: LpSaksiResp, selected: Boolean) {
            txtSaksi.text = saksiItem.nama
            if (saksiItem.is_korban == 1) {
                txtIsKorban.text = "Korban"
            } else {
                txtIsKorban.text = "Saksi"
            }
//            txtIsKorban.text = saksiItem.is_korban
            itemView.isActivated = selected

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedSaksiHolder {
        val itemView = inflater.inflate(R.layout.item_2_text, parent, false)
        return SelectedSaksiHolder(itemView)
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    override fun onBindViewHolder(holder: SelectedSaksiHolder, position: Int) {
        tracker?.let {
            holder.setSaksi(filterList[position], it.isSelected(filterList[position]))
        }
    }

    fun getItem(position: Int): LpSaksiResp = filterList[position]

    fun getPosition(name: String) = filterList.indexOfFirst { it.nama == name }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filterList = saksiItem
                } else {
                    val resultList = ArrayList<LpSaksiResp>()
                    for (row in saksiItem) {
                        if (row.toString().toLowerCase(Locale.ROOT).contains(
                                charSearch.toLowerCase(Locale.ROOT)
                            )
                        ) {
                            resultList.add(row)
                        }
                    }
                    filterList = resultList
                }
                val filterResult = FilterResults()
                filterResult.values = filterResult
                return filterResult
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterList = results?.values as ArrayList<LpSaksiResp>
                notifyDataSetChanged()
            }
        }
    }
}