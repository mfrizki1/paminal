package id.calocallo.sicape.ui.main.lp.pasal.tes

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.network.response.PasalResp
import id.calocallo.sicape.R
import kotlinx.android.synthetic.main.layout_1_text_clickable.view.*
import java.util.*
import kotlin.collections.ArrayList

class PasalTesAdapter internal constructor(
    private val context: Context,
    private val pasalResp: ArrayList<PasalResp>
) : RecyclerView.Adapter<PasalTesAdapter.PasalHolder>(), Filterable {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    var tracker: SelectionTracker<PasalResp>? = null
    var filterPasalLisit = ArrayList<PasalResp>()

    init {
        filterPasalLisit = pasalResp
    }

    inner class PasalHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtPasal = itemView.findViewById<TextView>(R.id.txt_1_clickable)
        fun getItemDetails(): ItemDetailsLookup.ItemDetails<PasalResp> =
            object : ItemDetailsLookup.ItemDetails<PasalResp>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): PasalResp? = filterPasalLisit[position]
            }

        fun setPasal(pasalResp: PasalResp, selected: Boolean) {
            with(itemView){
//                txtPasal.text = pasalResp.nama_pasal
                txt_1_clickable.text = pasalResp.nama_pasal
//                itemView.isActivated = selected
                this.isActivated = selected
                if(selected){
                    txt_1_clickable.setTextColor(Color.WHITE)
                }else{
                    txt_1_clickable.setTextColor(Color.BLACK)
                }
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasalHolder {
        val itemView = inflater.inflate(R.layout.layout_1_text_clickable, parent, false)
        return PasalHolder(itemView)
    }

    override fun getItemCount(): Int {
        return filterPasalLisit.size
    }

    override fun onBindViewHolder(holder: PasalHolder, position: Int) {
        tracker?.let {
            holder.setPasal(filterPasalLisit[position], it.isSelected(filterPasalLisit[position]))
        }
    }

    fun getItem(position: Int): PasalResp = filterPasalLisit[position]

    fun getPosition(name: String) = filterPasalLisit.indexOfFirst { it.nama_pasal == name }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val pasalSearch = constraint.toString()
                if (pasalSearch.isEmpty()) {
                    filterPasalLisit = pasalResp
                } else {
                    val resultList = ArrayList<PasalResp>()
                    for (row in pasalResp) {
                        if (row.toString().toLowerCase(Locale.ROOT)
                                .contains(pasalSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    filterPasalLisit = resultList
                }
                val filterResult = FilterResults()
                filterResult.values = filterPasalLisit
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterPasalLisit = results?.values as ArrayList<PasalResp>
                notifyDataSetChanged()
            }
        }
    }
}