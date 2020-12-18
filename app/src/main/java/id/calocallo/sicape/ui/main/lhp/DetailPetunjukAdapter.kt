package id.calocallo.sicape.ui.main.lhp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ListPetunjuk
import kotlinx.android.synthetic.main.item_1_text.view.*

class DetailPetunjukAdapter(val context: Context, val list: ArrayList<ListPetunjuk>?) :
    RecyclerView.Adapter<DetailPetunjukAdapter.DtlPetunjukHolder>() {
    inner class DtlPetunjukHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(list: ListPetunjuk?) {
            with(itemView){
                txt_item_detail_lhp.text = list?.petunjuk
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DtlPetunjukHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_1_text, parent, false)
        return DtlPetunjukHolder(view)
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }

    override fun onBindViewHolder(holder: DtlPetunjukHolder, position: Int) {
        holder.bind(list?.get(position))
    }
}