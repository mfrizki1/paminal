package id.calocallo.sicape.ui.main.addpersonal.misc.perjuangan

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PerjuanganCitaModel
import kotlinx.android.synthetic.main.layout_perjuangan_cita.view.*

class PerjuanganAdapter(
    val context: Context,
    val list: ArrayList<PerjuanganCitaModel>,
    val onClickPerjuangan: OnClickPerjuangan
) : RecyclerView.Adapter<PerjuanganAdapter.PerjuanganHolder>() {
    interface OnClickPerjuangan {
        fun onDelete(position: Int)
    }

    inner class PerjuanganHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val etPeristiwa = itemView.edt_peristiwa
        val etTmptPeristiwa = itemView.edt_tempat_peristiwa
        val etThnAwal = itemView.edt_thn_awal_perjuangan
        val etThnAkhir = itemView.edt_thn_akhir_perjuangan
        val etRangka = itemView.edt_rangka_perjuangan
        val etKet = itemView.edt_ket_perjuangan
        val btnDelete = itemView.btn_delete_perjuangan
        fun setListener() {
            etPeristiwa.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    list[adapterPosition].peristiwa = s.toString()
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
            etTmptPeristiwa.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    list[adapterPosition].tmpt_peristiwa = s.toString()
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
            etThnAwal.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    list[adapterPosition].thn_awal = s.toString()
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
            etThnAkhir.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    list[adapterPosition].thn_akhir = s.toString()
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
                    list[adapterPosition].rangka = s.toString()
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
                    list[adapterPosition].ket = s.toString()
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
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onClickPerjuangan.onDelete(adapterPosition)
                }
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PerjuanganAdapter.PerjuanganHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_perjuangan_cita, parent, false)
        var perjuanganViews = PerjuanganHolder(view)
        perjuanganViews.setListener()
        return perjuanganViews
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PerjuanganAdapter.PerjuanganHolder, position: Int) {
        val data = list[position]
        holder.etRangka.setText(data.rangka)
        holder.etPeristiwa.setText(data.peristiwa)
        holder.etTmptPeristiwa.setText(data.tmpt_peristiwa)
        holder.etThnAwal.setText(data.thn_awal)
        holder.etThnAkhir.setText(data.thn_akhir)
        holder.etKet.setText(data.ket)
    }
}