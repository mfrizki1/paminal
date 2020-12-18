package id.calocallo.sicape.ui.main.lhp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ListTerlapor
import kotlinx.android.synthetic.main.item_2_text.view.*
import kotlinx.android.synthetic.main.item_1_text.view.*

class DetailTerlaporAdapter(val context: Context, val list: ArrayList<ListTerlapor>?) :
    RecyclerView.Adapter<DetailTerlaporAdapter.DtlTerlaporHolder>() {
    inner class DtlTerlaporHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(listTerlapor: ListTerlapor?) {
            with(itemView){
                txt_detail_1.text = listTerlapor?.nama_terlapor
                txt_detail_2.text = listTerlapor?.uraian_terlapor
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DtlTerlaporHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_2_text, parent, false)
        return DtlTerlaporHolder(view)
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }

    override fun onBindViewHolder(holder: DtlTerlaporHolder, position: Int) {
        holder.bind(list?.get(position))
    }
}