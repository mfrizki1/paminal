package id.calocallo.sicape.ui.main.lp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.LpResp
import kotlinx.android.synthetic.main.item_lp.view.*

class LpAdapter(val context: Context, val list: List<LpResp>, val onCLickLp: OnCLickLp) :
    RecyclerView.Adapter<LpAdapter.LpHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()

    inner class LpHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(lpResp: LpResp) {
            with(itemView) {
                txt_no_lp.text = lpResp.no_lp
                txt_nama_lp_dilapor.text = lpResp.id_personel_dilapor.toString()
                txt_nama_lp_terlapor.text = lpResp.id_personel_terlapor.toString()
//                txt_nrp_pangkat_lp_dilapor.text = "Pangkat: ${lpDisiplinModel.pangkat_personel}    NRP: ${lpDisiplinModel.nrp_personel}"
//                txt_jabatan_lp_dilapor.text = lpDisiplinModel.jabatan
//                txt_kesatuan_lp_dilapor.text = lpDisiplinModel.kesatuan


//                txt_hukuman_lp.text = lpDisiplinModel.hukuman
//                txt_pasal_lp.text= lpDisiplinModel.pasal

                val pasalLayout = LinearLayoutManager(
                    itemView.rv_pasal_lp.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                itemView.rv_pasal_lp.apply {
                    layoutManager = pasalLayout
//                    adapter = LpPasalAdapter(list[adapterPosition], adapterPosition)
                    setRecycledViewPool(viewPool)
                }

                itemView.rv_saksi_lp.apply {
                    layoutManager = LinearLayoutManager(
                        itemView.rv_saksi_lp.context,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    adapter = LpSaksiAdapter(list[adapterPosition])
                    setRecycledViewPool(viewPool)
                }

                itemView.setOnClickListener {
                    onCLickLp.onClick(adapterPosition)
                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LpHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lp, parent, false)
        return LpHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: LpHolder, position: Int) {
        holder.bind(list[position])
    }

    interface OnCLickLp {
        fun onClick(position: Int)
    }
}