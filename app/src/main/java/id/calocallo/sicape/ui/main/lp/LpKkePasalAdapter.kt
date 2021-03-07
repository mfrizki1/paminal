package id.calocallo.sicape.ui.main.lp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.LpKkeResp
import kotlinx.android.synthetic.main.item_pasal_lp.view.*

class LpKkePasalAdapter(
    val pasalKkeItem: LpKkeResp
) : RecyclerView.Adapter<LpKkePasalAdapter.LpKkePasalHolder>() {
    inner class LpKkePasalHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtItem1: TextView = itemView.txt_item_1

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LpKkePasalHolder {
        return LpKkePasalHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_pasal_lp, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return pasalKkeItem.pasal_dilanggar!!.size
    }

    override fun onBindViewHolder(holder: LpKkePasalHolder, position: Int) {
        holder.txtItem1.text = pasalKkeItem.pasal_dilanggar!![position].pasal?.nama_pasal

    }
}