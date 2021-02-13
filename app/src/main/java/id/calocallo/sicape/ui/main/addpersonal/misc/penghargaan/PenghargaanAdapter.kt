package id.calocallo.sicape.ui.main.addpersonal.misc.penghargaan

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.PenghargaanReq
import kotlinx.android.synthetic.main.layout_penghargaan.view.*

class PenghargaanAdapter(
    val context: Context,
    val list: ArrayList<PenghargaanReq>,
    val onClick: OnClickPenghargaan
) : RecyclerView.Adapter<PenghargaanAdapter.PenghargaanHolder>() {
    inner class PenghargaanHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(penghargaanReq: PenghargaanReq) {
            with(itemView) {
                edt_nama_penghargaan.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        penghargaanReq.penghargaan = s.toString()
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
                edt_diterima_penghargaan.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        penghargaanReq.diterima_dari = s.toString()
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
                edt_rangka_penghargaan.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        penghargaanReq.dalam_rangka = s.toString()
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
                edt_tahun_penghargaan.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        penghargaanReq.tahun = s.toString()
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
                edt_ket_penghargaan.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        penghargaanReq.keterangan = s.toString()
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
                btn_delete_penghargaan.visibility =
                    if (adapterPosition == 0) View.GONE else View.VISIBLE
                btn_delete_penghargaan.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        onClick.onDelete(adapterPosition)
                    }
                }
            }
        }

    }

    interface OnClickPenghargaan {
        fun onDelete(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PenghargaanHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_penghargaan, parent, false)
        return PenghargaanHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PenghargaanHolder, position: Int) {
        holder.bind(list[position])
        val data = list[position]
        holder.itemView.edt_nama_penghargaan.setText(data.penghargaan)
        holder.itemView.edt_diterima_penghargaan.setText(data.diterima_dari)
        holder.itemView.edt_rangka_penghargaan.setText(data.dalam_rangka)
        holder.itemView.edt_tahun_penghargaan.setText(data.tahun)
        holder.itemView.edt_ket_penghargaan.setText(data.keterangan)
    }
}