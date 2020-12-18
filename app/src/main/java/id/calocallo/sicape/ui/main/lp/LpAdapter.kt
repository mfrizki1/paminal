package id.calocallo.sicape.ui.main.lp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LpDisiplinModel
import kotlinx.android.synthetic.main.item_lp.view.*

class LpAdapter(val context: Context, val list: List<LpDisiplinModel>, val onCLickLp: OnCLickLp ): RecyclerView.Adapter<LpAdapter.LpHolder>() {
    inner class LpHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(lpDisiplinModel: LpDisiplinModel) {
            with(itemView){
                txt_no_lp.text = lpDisiplinModel.no_lp
                txt_nama_lp.text = lpDisiplinModel.nama_personel
                txt_nrp_pangkat_lp.text = "Pangkat: ${lpDisiplinModel.pangkat_personel}    NRP: ${lpDisiplinModel.nrp_personel}"
                txt_hukuman_lp.text = lpDisiplinModel.hukuman
                txt_pasal_lp.text= lpDisiplinModel.pasal
                txt_jabatan_lp.text = lpDisiplinModel.jabatan
                txt_kesatuan_lp.text = lpDisiplinModel.kesatuan

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
    interface OnCLickLp{
        fun onClick(position: Int)
    }
}