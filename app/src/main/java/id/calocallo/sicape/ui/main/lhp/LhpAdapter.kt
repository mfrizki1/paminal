package id.calocallo.sicape.ui.main.lhp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LhpResp
import kotlinx.android.synthetic.main.item_lhp.view.*

class LhpAdapter(
    val context: Context,
    val list: ArrayList<LhpResp>,
    val onCLickLhp: OnClickLhp
) : RecyclerView.Adapter<LhpAdapter.LhpHolder>() {
    inner class LhpHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(lhpModel: LhpResp) {
            with(itemView) {
                var terbukti = ""
                txt_no_lhp.text = lhpModel.no_lhp
//                txt_ketua_tim.text = lhpModel.nama_ketua_tim
                if(lhpModel.isTerbukti == 0){
                    terbukti = "Terbukti"
                }else{
                    terbukti = "TIdak Terbukti"
                }
                txt_isTerbukti.text = terbukti
                itemView.setOnClickListener {
                    onCLickLhp.onClick(adapterPosition)
                }

            }
        }

    }

    interface OnClickLhp {
        fun onClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LhpHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lhp, parent, false)
        return LhpHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: LhpHolder, position: Int) {
        holder.bind(list[position])
    }
}