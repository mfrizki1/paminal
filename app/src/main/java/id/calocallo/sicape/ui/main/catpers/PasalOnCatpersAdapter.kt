package id.calocallo.sicape.ui.main.catpers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.CatpersResp
import kotlinx.android.synthetic.main.item_pasal_lp.view.*

class PasalOnCatpersAdapter(
    val listPasalOnCatpers: CatpersResp
): RecyclerView.Adapter<PasalOnCatpersAdapter.PasalOnCatpersHolder>() {
    inner class PasalOnCatpersHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtItem1: TextView = itemView.txt_item_1

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasalOnCatpersHolder {
        return PasalOnCatpersHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_pasal_lp, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listPasalOnCatpers.pasal_dilanggar!!.size

    }

    override fun onBindViewHolder(holder: PasalOnCatpersHolder, position: Int) {
        holder.txtItem1.text = listPasalOnCatpers.pasal_dilanggar!![position].nama_pasal

    }
}