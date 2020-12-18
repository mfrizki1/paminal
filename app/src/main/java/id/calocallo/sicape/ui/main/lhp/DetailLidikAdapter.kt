package id.calocallo.sicape.ui.main.lhp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ListLidik
import kotlinx.android.synthetic.main.item_1_text.view.*

class DetailLidikAdapter(val context: Context, val list: ArrayList<ListLidik>?) :
    RecyclerView.Adapter<DetailLidikAdapter.DtlLidikHolder>() {


    inner class DtlLidikHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(listLidik: ListLidik?) {
            with(itemView) {
                for (i in 1..list?.size!!) {
//                    var no = i.toString()
                    txt_item_detail_lhp.text =
                        "${listLidik?.nama_lidik} \t ${listLidik?.pangkat_lidik} \t NRP.${listLidik?.nrp_lidik}"
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DtlLidikHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_1_text, parent, false)
        return DtlLidikHolder(view)
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }

    override fun onBindViewHolder(holder: DtlLidikHolder, position: Int) {
        holder.bind(list?.get(position))
    }


}