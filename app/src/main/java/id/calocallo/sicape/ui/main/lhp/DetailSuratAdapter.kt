package id.calocallo.sicape.ui.main.lhp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ListSurat
import kotlinx.android.synthetic.main.item_1_text.view.*

class DetailSuratAdapter(val context: Context, val list: ArrayList<ListSurat>?) :
    RecyclerView.Adapter<DetailSuratAdapter.DtlSuratHolder>() {
    inner class DtlSuratHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(listSurat: ListSurat?) {
            with(itemView){
                txt_item_detail_lhp.text = listSurat?.surat
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DtlSuratHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_1_text, parent, false)
        return DtlSuratHolder(view)
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }

    override fun onBindViewHolder(holder: DtlSuratHolder, position: Int) {
        holder.bind(list?.get(position))
    }
}