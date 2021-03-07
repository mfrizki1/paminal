package id.calocallo.sicape.ui.main.lp.pasal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.PasalDilanggarResp
import id.calocallo.sicape.utils.ext.toggleVisibility
import kotlinx.android.synthetic.main.layout_1_text_clickable.view.*

class PasalAdapter(
    val context: Context,
    val list: ArrayList<PasalDilanggarResp>,
    val pasalClick: PasalClick
) : RecyclerView.Adapter<PasalAdapter.PasalHolder>() {
    private var multiSelect = false
    private val selectedIdPasal = arrayListOf<PasalDilanggarResp>()
    interface PasalClick {
        fun onClick(position: Int, list: PasalDilanggarResp)
        fun onLongClick(position: Int)
    }

    inner class PasalHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(pasalDilanggarResp: PasalDilanggarResp) {
            val currentItem = pasalDilanggarResp.id
            with(itemView) {
                txt_1_clickable.text = pasalDilanggarResp.pasal?.nama_pasal
                itemView.setOnClickListener {
                    if(!multiSelect){
                        multiSelect = true
                        itemView.img_clickable.toggleVisibility()
                        if(itemView.img_clickable.visibility == View.VISIBLE) {
                            pasalClick.onClick(adapterPosition, pasalDilanggarResp)
//                            selectItem(holder, currentItem)
                        }
                    }


                }

            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasalHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_1_text_clickable, parent, false)
        return PasalHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PasalHolder, position: Int) {
        holder.bind(list[position])
    }
}