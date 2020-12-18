package id.calocallo.sicape.ui.main.lhp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ListBukti
import kotlinx.android.synthetic.main.item_1_text.view.*

class DetailBuktiAdapter(val context: Context, val list: ArrayList<ListBukti>?) :
    RecyclerView.Adapter<DetailBuktiAdapter.DtlBuktiHolder>() {
    inner class DtlBuktiHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(list: ListBukti?) {
            with(itemView) {
                txt_item_detail_lhp.text = list?.bukti
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DtlBuktiHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_1_text, parent, false)
        return DtlBuktiHolder(view)
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }

    override fun onBindViewHolder(holder: DtlBuktiHolder, position: Int) {
        holder.bind(list?.get(position))
    }
}