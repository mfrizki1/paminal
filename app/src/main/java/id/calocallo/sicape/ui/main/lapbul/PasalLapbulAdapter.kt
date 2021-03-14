package id.calocallo.sicape.ui.main.lapbul

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.CatpersLapbulResp
import kotlinx.android.synthetic.main.item_pasal_lp.view.*

class PasalLapbulAdapter(
    val listPasal: CatpersLapbulResp
) : RecyclerView.Adapter<PasalLapbulAdapter.PasalLapbulHolder>() {
    inner class PasalLapbulHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtItem1: TextView = itemView.txt_item_1

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasalLapbulHolder {
        return PasalLapbulHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_pasal_lp, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listPasal.pasal_dilanggar!!.size
    }

    override fun onBindViewHolder(holder: PasalLapbulHolder, position: Int) {
        holder.txtItem1.text = listPasal.pasal_dilanggar?.get(position)

    }
}