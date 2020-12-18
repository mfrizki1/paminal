package id.calocallo.sicape.ui.main.skhd

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ListHukumanSkhd
import kotlinx.android.synthetic.main.item_1_text.view.*

class HukumanSkhdAdapter(
    val context: Context,
    val list: ArrayList<ListHukumanSkhd>?
) : RecyclerView.Adapter<HukumanSkhdAdapter.HukSkhdHolder>() {
    inner class HukSkhdHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(hukSkhd: ListHukumanSkhd?) {
            with(itemView){
                txt_item_detail_lhp.text = hukSkhd?.hukuman
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HukSkhdHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_1_text, parent, false)
        return HukSkhdHolder(view)
    }

    override fun getItemCount(): Int = list?.size!!

    override fun onBindViewHolder(holderHuk: HukSkhdHolder, position: Int) {
        holderHuk.bind(list?.get(position))
    }
}