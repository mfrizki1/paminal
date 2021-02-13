package id.calocallo.sicape.ui.main.addpersonal.misc.perjuangan

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.PerjuanganCitaReq
import kotlinx.android.synthetic.main.layout_perjuangan_cita.view.*

class PerjuanganAdapter(
    val context: Context,
    val list: ArrayList<PerjuanganCitaReq>,
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

        }

        fun bind(perjuanganCitaReq: PerjuanganCitaReq) {
            with(itemView) {
                edt_peristiwa.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        perjuanganCitaReq.peristiwa = s.toString()
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
                edt_tempat_peristiwa.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        perjuanganCitaReq.lokasi = s.toString()
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
                edt_thn_awal_perjuangan.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        perjuanganCitaReq.tahun_awal = s.toString()
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
                edt_thn_akhir_perjuangan.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        perjuanganCitaReq.tahun_akhir = s.toString()
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
                edt_rangka_perjuangan.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        perjuanganCitaReq.dalam_rangka = s.toString()
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
                edt_ket_perjuangan.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        perjuanganCitaReq.keterangan = s.toString()
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
                btn_delete_perjuangan.visibility = if (adapterPosition == 0) View.GONE
                else View.VISIBLE
                btn_delete_perjuangan.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        onClickPerjuangan.onDelete(adapterPosition)
                    }
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
        return PerjuanganHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PerjuanganAdapter.PerjuanganHolder, position: Int) {
        holder.bind(list[position])
        val data = list[position]
        holder.etPeristiwa.setText(data.peristiwa)
        holder.etThnAwal.setText(data.tahun_awal)
        holder.etThnAkhir.setText(data.tahun_akhir)
        holder.etTmptPeristiwa.setText(data.lokasi)
        holder.etRangka.setText(data.dalam_rangka)
        holder.etKet.setText(data.keterangan)
    }
}