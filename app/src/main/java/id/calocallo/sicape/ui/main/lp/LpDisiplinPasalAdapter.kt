package id.calocallo.sicape.ui.main.lp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.LpDisiplinResp
import kotlinx.android.synthetic.main.item_pasal_lp.view.*

class LpDisiplinPasalAdapter(
    val pasalDisiplinItem: LpDisiplinResp

) : RecyclerView.Adapter<LpDisiplinPasalAdapter.LpDisiplinHolder>() {
    inner class LpDisiplinHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtItem1: TextView = itemView.txt_item_1

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LpDisiplinHolder {
        return LpDisiplinHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_pasal_lp, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return pasalDisiplinItem.pasal_dilanggar!!.size

    }

    override fun onBindViewHolder(holder: LpDisiplinHolder, position: Int) {
        holder.txtItem1.text = pasalDisiplinItem.pasal_dilanggar!![position].nama_pasal

    }
}