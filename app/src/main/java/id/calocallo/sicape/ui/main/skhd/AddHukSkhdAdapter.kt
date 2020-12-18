package id.calocallo.sicape.ui.main.skhd

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ListHukumanSkhd
import kotlinx.android.synthetic.main.layout_hukuman_skhd.view.*

class AddHukSkhdAdapter(
    val context: Context,
    val list: ArrayList<ListHukumanSkhd>,
    val onClickHukSkhd: OnClickHukSKHD
) : RecyclerView.Adapter<AddHukSkhdAdapter.HukHolder>() {
    inner class HukHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(HukSkhd: ListHukumanSkhd) {
            with(itemView) {
                edt_hukuman_skhd.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        HukSkhd.hukuman = s.toString()
                    }

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                    }
                })

                btn_delete_hukuman_skhd.visibility = if (adapterPosition == 0) View.GONE
                else View.VISIBLE
                btn_add_hukuman_skhd.setOnClickListener {
                    onClickHukSkhd.onAdd()
                }

                btn_delete_hukuman_skhd.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        onClickHukSkhd.onDelete(adapterPosition)
                    }
                }
            }
        }

    }

    interface OnClickHukSKHD {
        fun onAdd()
        fun onDelete(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HukHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_hukuman_skhd, parent, false)
        return HukHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HukHolder, position: Int) {
        holder.bind(list[position])
    }
}