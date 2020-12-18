package id.calocallo.sicape.ui.main.lhp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ListAnalisa
import kotlinx.android.synthetic.main.item_1_text.view.*

class DetailAnalisaAdapter(val context: Context, val list: ArrayList<ListAnalisa>?) :
    RecyclerView.Adapter<DetailAnalisaAdapter.DtlAnalisaHolder>() {
    inner class DtlAnalisaHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(listAnalisa: ListAnalisa?) {
            with(itemView){
                txt_item_detail_lhp.text = listAnalisa?.analisa
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DtlAnalisaHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_1_text, parent, false)
        return DtlAnalisaHolder(view)
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }

    override fun onBindViewHolder(holder: DtlAnalisaHolder, position: Int) {
        holder.bind(list?.get(position))
    }
}