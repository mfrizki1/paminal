package id.calocallo.sicape.ui.main.lp.pasal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LpPasalModel

class PasalAdapter2(
    private val context: Context,
    private var listPasal: ArrayList<LpPasalModel>
) : RecyclerView.Adapter<PasalAdapter2.PasalHolder>() {
    fun setListPasal(listPasal: ArrayList<LpPasalModel>) {
        this.listPasal = ArrayList()
        this.listPasal = listPasal
        notifyDataSetChanged()
    }

       fun getAll(): ArrayList<LpPasalModel> {
        return listPasal
    }

    fun getSelected(): ArrayList<LpPasalModel>? {
        val selected: ArrayList<LpPasalModel> = ArrayList()
        for (i in 0 until listPasal.size) {
            if (listPasal.get(i).isChecked) {
                selected.add(listPasal[i])
            }
        }
        return selected
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasalHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.layout_1_text_clickable, parent, false)
        return PasalHolder(view)
    }

    override fun onBindViewHolder(
        holder: PasalHolder,
        position: Int
    ) {
        holder.bind(listPasal[position])
    }

    override fun getItemCount(): Int {
        return listPasal.size
    }

    inner class PasalHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val txtPasal: TextView
        private val checkedPasal: ImageView
        fun bind(lpPasalResp: LpPasalModel) {
            checkedPasal.visibility = if (lpPasalResp.isChecked) View.VISIBLE else View.GONE
            txtPasal.text = lpPasalResp.pasalModel[adapterPosition].nama_pasal
            itemView.setOnClickListener {
                lpPasalResp.isChecked = !lpPasalResp.isChecked
                checkedPasal.visibility = if(lpPasalResp.isChecked) View.VISIBLE else View.GONE
            }
        }

        init {
            txtPasal = itemView.findViewById(R.id.txt_1_clickable)
            checkedPasal = itemView.findViewById(R.id.img_clickable)
        }
    }

}