package id.calocallo.sicape.ui.main.addpersonal.relasi

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.HukumanReq
import kotlinx.android.synthetic.main.activity_add_catpers.view.*
import kotlinx.android.synthetic.main.layout_hukuman.view.*

class PernahHukumAdapter(
    val context: Context,
    val list: ArrayList<HukumanReq>,
    val onClickHukum: OnClickHukum
) : RecyclerView.Adapter<PernahHukumAdapter.PernahHukumHolder>() {

    interface OnClickHukum {
        fun onDelete(position: Int)
        fun onAdd()
    }

    inner class PernahHukumHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(hukumanReq: HukumanReq) {
            with(itemView) {
                edt_perkara_hukum.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        hukumanReq.perkara = s.toString()
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
                btn_delete_hukuman.visibility = if (adapterPosition == 0) View.GONE
                else View.VISIBLE
                btn_delete_hukuman.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION)
                        onClickHukum.onDelete(adapterPosition)
                }
                btn_add_pernah_dihukum.setOnClickListener {
                    onClickHukum.onAdd()
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PernahHukumHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_hukuman, parent, false)
        return PernahHukumHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PernahHukumHolder, position: Int) {
        holder.bind(list[position])
        val data = list[position]
        holder.itemView.edt_perkara_hukum.setText(data.perkara)
    }
}

