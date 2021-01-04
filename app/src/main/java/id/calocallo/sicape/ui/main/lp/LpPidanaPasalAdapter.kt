package id.calocallo.sicape.ui.main.lp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.LpPidanaResp
import kotlinx.android.synthetic.main.item_pasal_lp.view.*

class LpPidanaPasalAdapter(
//    val jenis: String,
//    val position: Int
    val pasalLpItem: LpPidanaResp
) : RecyclerView.Adapter<LpPidanaPasalAdapter.LpPidanaPasalHolder>() {

    //    private var pasalLpItemPidana= ArrayList<LpPidanaResp>()
//    private var pasalLpItemKKe = ArrayList<LpKodeEtikResp>()
//    private var pasalLpItemDisiplin = ArrayList<LpDisiplinResp>()
    inner class LpPidanaPasalHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtItem1: TextView = itemView.txt_item_1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LpPidanaPasalHolder {
        return LpPidanaPasalHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_pasal_lp, parent, false)
        )
    }

    override fun getItemCount(): Int {
//        if(jenis == "pidana"){
//            return pasalLpItemPidana[position].listPasal!!.size
//        }else if(jenis == "kode_etik"){
//            return pasalLpItemKKe[position].listPasal!!.size
//        }else{
//            return pasalLpItemDisiplin[position].listPasal!!.size
        return pasalLpItem.pasal_dilanggar!!.size
    }


    override fun onBindViewHolder(holder: LpPidanaPasalAdapter.LpPidanaPasalHolder, position: Int) {
//        if(jenis == "pidana"){
//            holder.txtItem1.text = pasalLpItemPidana[position].listPasal!![position].nama_pasal
//        }else if(jenis =="kode_etik"){
//            holder.txtItem1.text = pasalLpItemKKe[position].listPasal!![position].nama_pasal
//        }else{
//            holder.txtItem1.text = pasalLpItemDisiplin[position].listPasal!![position].nama_pasal
//        }
            holder.txtItem1.text = pasalLpItem.pasal_dilanggar!![position].nama_pasal

    }
}