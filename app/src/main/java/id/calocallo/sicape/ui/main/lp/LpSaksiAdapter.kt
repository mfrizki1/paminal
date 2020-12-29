package id.calocallo.sicape.ui.main.lp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.LpResp
import id.calocallo.sicape.network.response.LpSaksiResp
import kotlinx.android.synthetic.main.item_pasal_lp.view.*

class LpSaksiAdapter(val saksiLpItems: LpResp): RecyclerView.Adapter<LpSaksiAdapter.LpSaksiHolder>() {
    inner class LpSaksiHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(lpSaksiResp: LpSaksiResp) {
            itemView.txt_item_1.text = saksiLpItems.listSaksi!![adapterPosition].nama_saksi
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LpSaksiHolder {
        return LpSaksiHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_pasal_lp, parent, false))
    }

    override fun getItemCount(): Int {
        return saksiLpItems.listSaksi?.size!!
    }

    override fun onBindViewHolder(holder: LpSaksiHolder, position: Int) {
        saksiLpItems.listSaksi?.get(position)?.let { holder.bind(it) }
    }
}