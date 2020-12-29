package id.calocallo.sicape.ui.main.addpersonal.tokoh

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.TokohReq
import kotlinx.android.synthetic.main.layout_tokoh_dikagumi.view.*

class TokohAdapter(
    val context: Context,
    val list: ArrayList<TokohReq>,
    val onClickTokoh: OnClickTokoh
) : RecyclerView.Adapter<TokohAdapter.TokohHolder>() {
    inner class TokohHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val etNama = itemView.edt_nama_tokoh_dikagumi
        val etAsalNegara = itemView.edt_alasan_tokoh_dikagumi
        val etAlasan = itemView.edt_alasan_tokoh_dikagumi
        val etKet = itemView.edt_ket_tokoh_dikagumi
        val btnDelete = itemView.btn_delete_tokoh_dikagumi

      

        fun bind(tokohReq: TokohReq) {
            with(itemView) {
                etNama.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        tokohReq.nama = s.toString()
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
                etAsalNegara.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        tokohReq.asal_negara = s.toString()
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
                etAlasan.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        tokohReq.alasan = s.toString()
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
                etKet.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        tokohReq.keterangan = s.toString()
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
                btnDelete.visibility = if (adapterPosition == 0) View.GONE else View.VISIBLE
                btnDelete.setOnClickListener {
                    onClickTokoh.onDelete(adapterPosition)
                }
            }
        }

    }

    interface OnClickTokoh {
        fun onDelete(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TokohHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_tokoh_dikagumi, parent, false)
        return TokohHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TokohHolder, position: Int) {
        holder.bind(list[position])

    }
}