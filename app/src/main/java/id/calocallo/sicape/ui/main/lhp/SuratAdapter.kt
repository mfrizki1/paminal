package id.calocallo.sicape.ui.main.lhp

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ListSaksi
import id.calocallo.sicape.model.ListSurat
import kotlinx.android.synthetic.main.item_saksi.view.*
import kotlinx.android.synthetic.main.item_surat.view.*

class SuratAdapter(
    val context: Context,
    val list: ArrayList<ListSurat>,
    val onClickSurat: OnClickSurat
) : RecyclerView.Adapter<SuratAdapter.SuratHolder>() {
    inner class SuratHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(listSurat: ListSurat) {
            with(itemView) {
                edt_surat.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        listSurat.surat = s.toString()
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
                btn_delete_surat.visibility = if (adapterPosition == 0) View.GONE
                else View.VISIBLE
                btn_delete_surat.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        onClickSurat.onDelete(adapterPosition)
                    }
                }
                btn_add_surat.setOnClickListener {
                    onClickSurat.onAdd()
                }
            }
        }

    }

    interface OnClickSurat {
        fun onAdd()
        fun onDelete(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuratHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_surat, parent, false)
        return SuratHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SuratHolder, position: Int) {
        holder.bind(list[position])
    }
}
