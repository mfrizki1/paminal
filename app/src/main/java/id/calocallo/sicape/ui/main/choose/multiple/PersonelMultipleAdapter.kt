package id.calocallo.sicape.ui.main.choose.multiple

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
import id.calocallo.sicape.model.AllPersonelModel
import id.calocallo.sicape.network.response.PasalResp
import id.calocallo.sicape.network.response.PersonelMinResp
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import kotlinx.android.synthetic.main.item_choose_personel.view.*
import java.util.*
import kotlin.collections.ArrayList

class PersonelMultipleAdapter internal constructor(
    private val context: Context,
    private val personel: ArrayList<PersonelMinResp>
) : RecyclerView.Adapter<PersonelMultipleAdapter.PersMultipHolder>(), Filterable {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var filterable: Boolean = false
    var tracker: SelectionTracker<PersonelMinResp>? = null
    var filterList = ArrayList<PersonelMinResp>()
    init {
        filterList = personel
    }

    inner class PersMultipHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun getItemDetails(): ItemDetailsLookup.ItemDetails<PersonelMinResp> =
            object : ItemDetailsLookup.ItemDetails<PersonelMinResp>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): PersonelMinResp? = filterList[position]
            }

        fun setPersonel(allPersonelModel: PersonelMinResp, selected: Boolean) {
            with(itemView) {

                txt_personel_nama_choose.text = allPersonelModel.nama
                txt_personel_pangkat_choose.text = allPersonelModel.pangkat.toString().toUpperCase()
                txt_personel_nrp_choose.text = allPersonelModel.nrp
                txt_personel_jabatan_choose.text = allPersonelModel.jabatan
                txt_personel_kesatuan_choose.text = allPersonelModel.satuan_kerja?.kesatuan
                Log.e("selected", "$selected")
                this.isActivated = selected
                if(selected){
                    img_choose_personel.visible()
                }else{
                    img_choose_personel.gone()
                }
                Log.e("selected2", "$selected")
            }

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersMultipHolder {
        val view = inflater.inflate(R.layout.item_choose_personel, parent, false)
        return PersMultipHolder(view)
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    override fun onBindViewHolder(holder: PersMultipHolder, position: Int) {
        tracker?.isSelected(filterList[position])?.let { holder.setPersonel(filterList[position], it) }
    }

    fun getItem(position: Int): PersonelMinResp? = filterList[position]

    fun getPosition(nama: String) = filterList.indexOfFirst { it.nama == nama }
    override fun getFilter(): Filter {
        return object :Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filterList = personel
                } else {
                    val resultList = ArrayList<PersonelMinResp>()
                    for (row in personel) {
                        if (row.toString().toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
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
                filterList = results?.values as ArrayList<PersonelMinResp>
                notifyDataSetChanged()
            }

        }
    }
}