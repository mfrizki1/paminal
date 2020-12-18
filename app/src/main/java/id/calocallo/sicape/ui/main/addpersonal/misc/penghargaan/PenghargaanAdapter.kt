package id.calocallo.sicape.ui.main.addpersonal.misc.penghargaan

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PenghargaanReq
import kotlinx.android.synthetic.main.layout_penghargaan.view.*

class PenghargaanAdapter(
    val context: Context,
    val list: ArrayList<PenghargaanReq>,
    val onClick: OnClickPenghargaan
) : RecyclerView.Adapter<PenghargaanAdapter.PenghargaanHolder>() {
    inner class PenghargaanHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btnDelete = itemView.btn_delete_penghargaan
        val etJenis = itemView.edt_jenis_penghargaan
        val etDiterima = itemView.edt_diterima_penghargaan
        val etRangka = itemView.edt_rangka_penghargaan
        val etTgl = itemView.edt_tgl_penghargaan
        val etKet = itemView.edt_ket_penghargaan
        fun setListener() {
            etJenis.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    list[adapterPosition].penghargaan = s.toString()
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })
            etDiterima.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    list[adapterPosition].diterima_dari = s.toString()
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })
            etRangka.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    list[adapterPosition].dalam_rangka = s.toString()
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })
            etTgl.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    list[adapterPosition].tahun = s.toString()
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })
            etKet.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    list[adapterPosition].keterangan = s.toString()
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })
            btnDelete.setOnClickListener {
                if(adapterPosition!= RecyclerView.NO_POSITION){
                    onClick.onDelete(adapterPosition)
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
        val penghargaanViews = PenghargaanHolder(view)
        penghargaanViews.setListener()
        return penghargaanViews
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PenghargaanHolder, position: Int) {
        val data = list[position]
        holder.etJenis.setText(data.penghargaan)
        holder.etDiterima.setText(data.diterima_dari)
        holder.etRangka.setText(data.dalam_rangka)
        holder.etTgl.setText(data.tahun)
        holder.etKet.setText(data.keterangan)
    }
}