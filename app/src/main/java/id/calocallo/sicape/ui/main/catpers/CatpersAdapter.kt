package id.calocallo.sicape.ui.main.catpers

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.CatpersModel
import kotlinx.android.synthetic.main.activity_add_catpers.view.*
import kotlinx.android.synthetic.main.item_catpers.view.*
import kotlinx.android.synthetic.main.item_catpers.view.txt_pasal

class CatpersAdapter(val context:Context, val listCatpers: ArrayList<CatpersModel>, val listener: CatpersListener): RecyclerView.Adapter<CatpersAdapter.CatpersHolder>() {
    inner class CatpersHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(catpersModel: CatpersModel) {
            with(itemView){
                txt_no_lp.text = catpersModel.no_lp
                txt_name.text = catpersModel.name
                txt_nrp.text = catpersModel.pangkat_nrp
                txt_jns_pelanggaran.text = catpersModel.jenis_pelanggaran
                txt_pasal.text = catpersModel.pasal
                txt_putusan.text = catpersModel.putusan
                txt_keterangan.text = catpersModel.ket

                itemView.setOnClickListener {
                    listener.onClick(adapterPosition)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatpersHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_catpers, parent, false)
        return CatpersHolder(view)

    }

    override fun getItemCount(): Int {
        Log.e("size", listCatpers.size.toString())
        return listCatpers.size
    }

    override fun onBindViewHolder(holder: CatpersHolder, position: Int) {
        holder.bind(listCatpers[position])
    }
    interface CatpersListener{
        fun onClick(position: Int)
    }
}