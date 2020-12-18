package id.calocallo.sicape.ui.main.lhp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ListSaksi
import kotlinx.android.synthetic.main.item_2_text.view.*

class DetailSaksiAdapter(val context: Context, val list: ArrayList<ListSaksi>?): RecyclerView.Adapter<DetailSaksiAdapter.DtlSaksiHolder>() {
    inner class DtlSaksiHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(listSaksi: ListSaksi?) {
            with(itemView){
                txt_detail_1.text = listSaksi?.nama_saksi
                txt_detail_2.text = listSaksi?.uraian_saksi
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DtlSaksiHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_2_text, parent, false)
        return DtlSaksiHolder(view)
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }

    override fun onBindViewHolder(holder: DtlSaksiHolder, position: Int) {
        holder.bind(list?.get(position))
    }
}