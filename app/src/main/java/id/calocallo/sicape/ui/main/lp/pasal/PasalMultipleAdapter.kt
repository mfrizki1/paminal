package id.calocallo.sicape.ui.main.lp.pasal

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.PasalResp
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import kotlinx.android.synthetic.main.activity_detail_lhp.view.*
import kotlinx.android.synthetic.main.layout_1_text_clickable.view.*
import java.util.*
import kotlin.collections.ArrayList

/*
class PasalMultipleAdapter internal constructor(
    private val context: Context,
    private val pasalResp: ArrayList<PasalResp>
) : RecyclerView.Adapter<PasalMultipleAdapter.PasalHolder>(), Filterable {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    var tracker: SelectionTracker<PasalResp>? = null
    var filterList = ArrayList<PasalResp>()

    init {
        filterList = pasalResp
    }

    inner class PasalHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtPasal = itemView.findViewById<TextView>(R.id.txt_1_clickable)
        fun getItemDetails(): ItemDetailsLookup.ItemDetails<PasalResp> =
            object : ItemDetailsLookup.ItemDetails<PasalResp>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): PasalResp = filterList[position]
            }

        fun setPasal(pasalResp: PasalResp, selected: Boolean) {
            with(itemView) {
                this.txt_1_clickable.text = pasalResp.nama_pasal
//                txtPasal.text = pasalResp.nama_pasal
                this.isActivated = selected
                if (selected) {
                    this.txt_1_clickable.setTextColor(Color.WHITE)
//                    this.img_clickable.visible()
//                    selected == false
                } else {
//                    this.img_clickable.gone()
                    this.txt_1_clickable.setTextColor(Color.BLACK)
                }
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

    fun getItem(position: Int): PasalResp = filterList[position]

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
                        if (row.toString().toLowerCase(Locale.ROOT).contains(
                                charSearch.toString().toLowerCase(
                                    Locale.ROOT
                                )
                            )
                        ) {
                            resultList.add(row)
                        }
                    }
                    filterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterList = results?.values as ArrayList<PasalResp>
                notifyDataSetChanged()
            }

        }
    }
}*/

class PasalMultipleAdapter internal constructor(
    private val context: Context,
    private val pasal: ArrayList<PasalResp>
) : RecyclerView.Adapter<PasalMultipleAdapter.PasalMultipleHolder>(), Filterable {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var filterable: Boolean = false
    var tracker: SelectionTracker<PasalResp>? = null
    var filterList = ArrayList<PasalResp>()

    init {
        filterList = pasal
    }

    inner class PasalMultipleHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun getItemDetails(): ItemDetailsLookup.ItemDetails<PasalResp> =
            object : ItemDetailsLookup.ItemDetails<PasalResp>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): PasalResp = filterList[position]
            }

        fun setPasal(pasalResp: PasalResp, selected: Boolean) {
            with(itemView) {

                /*txt_personel_nama_choose.text = allPersonelModel.nama
                txt_personel_pangkat_choose.text = allPersonelModel.pangkat.toString().toUpperCase()
                txt_personel_nrp_choose.text = allPersonelModel.nrp
                txt_personel_jabatan_choose.text = allPersonelModel.jabatan
                txt_personel_kesatuan_choose.text = allPersonelModel.satuan_kerja?.kesatuan
                Log.e("selected", "$selected")*/
                this.isActivated = selected
                txt_1_clickable.text = pasalResp.nama_pasal
                if (this.isActivated) {
                    /* img_choose_personel.visible()*/
                    this.img_clickable.visible()
                } else {
                    this.img_clickable.gone()
                    /* img_choose_personel.gone()*/
                }
                Log.e("selected2", "$selected")
            }

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasalMultipleHolder {
        val view = inflater.inflate(R.layout.layout_1_text_clickable, parent, false)
        return PasalMultipleHolder(view)
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    override fun onBindViewHolder(holder: PasalMultipleHolder, position: Int) {
        tracker?.isSelected(filterList[position])?.let { holder.setPasal(filterList[position], it) }
    }

    fun getItem(position: Int): PasalResp = filterList[position]

    fun getPosition(nama: String) = filterList.indexOfFirst { it.nama_pasal == nama }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filterList = pasal
                } else {
                    val resultList = ArrayList<PasalResp>()
                    for (row in pasal) {
                        if (row.toString().toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    filterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filterList
                return filterResults
            }
            /*override fun performFiltering(constraint: CharSequence?): FilterResults {
                val search = constraint?.toString()
                if(search?.isEmpty()!!){
                    filterList = personel
                }else{
                    val resultList = ArrayList<AllPersonelModel>()
                    for(row in personel){
                        if(row.toString().toLowerCase(Locale.ROOT)
                                .contains(search.toLowerCase(Locale.ROOT))){
                            resultList.add(row)
                        }
                        filterList = resultList
                    }
                    val filterResult = FilterResults()
                    filterResult.values = filterList
                    return filterResult
                }
            }*/

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterList = results?.values as ArrayList<PasalResp>
                notifyDataSetChanged()
            }

        }
    }
}