package id.calocallo.sicape.ui.main.lp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.LpResp
import kotlinx.android.synthetic.main.item_pasal_lp.view.*

class LpPasalAdapter(
    val pasalLpItem: LpResp
) : RecyclerView.Adapter<LpPasalAdapter.LpPasalHolder>() {
    inner class LpPasalHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtItem1: TextView = itemView.txt_item_1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LpPasalHolder {
        return LpPasalHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_pasal_lp, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return pasalLpItem.listPasal!!.size
    }

    override fun onBindViewHolder(holder: LpPasalHolder, position: Int) {
        holder.txtItem1.text = pasalLpItem.listPasal!![position].nama_pasal
    }
}