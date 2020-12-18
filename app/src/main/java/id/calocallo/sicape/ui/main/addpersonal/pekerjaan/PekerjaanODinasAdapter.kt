package id.calocallo.sicape.ui.main.addpersonal.pekerjaan

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.PekerjaanODinasReq
import kotlinx.android.synthetic.main.layout_pekerjaan_luar_dinas.view.*

class PekerjaanODinasAdapter(
    val context: Context,
    val list: ArrayList<PekerjaanODinasReq>,
    val onClickODinas: OnClickODinas
) : RecyclerView.Adapter<PekerjaanODinasAdapter.OutDinasHolder>() {
    inner class OutDinasHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val etInstansi = itemView.edt_instansi_pekerjaan_outdinas_custom
        val etName = itemView.edt_nama_pekerjaan_outdinas_custom
        val etTahunAwal = itemView.edt_thn_awal_pekerjaan_outdinas_custom
        val etTahunAkhir = itemView.edt_thn_akhir_pekerjaan_outdinas_custom
        val etRangka = itemView.edt_rangka_pekerjaan_outdinas_custom
        val etKet = itemView.edt_ket_pekerjaan_outdinas_custom
        val btnDelete = itemView.btn_delete_pekerjaan_outdinas_custom
        fun setListener() {
            etName.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    list[adapterPosition].pekerjaan = s.toString()
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
            etInstansi.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    list[adapterPosition].instansi = s.toString()
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
            etTahunAwal.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    list[adapterPosition].tahun_awal = s.toString()
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
            etTahunAkhir.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    list[adapterPosition].tahun_akhir = s.toString()
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
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onClickODinas.onDelete(adapterPosition)
                }
            }
        }

    }

    interface OnClickODinas {
        fun onDelete(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OutDinasHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_pekerjaan_luar_dinas, parent, false)
        val ODinas = OutDinasHolder(view)
        ODinas.setListener()
        return ODinas
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: OutDinasHolder, position: Int) {
        val data = list[position]
        holder.etInstansi.setText(data.instansi)
        holder.etKet.setText(data.keterangan)
        holder.etName.setText(data.pekerjaan)
        holder.etRangka.setText(data.dalam_rangka)
        holder.etTahunAwal.setText(data.tahun_awal)
        holder.etTahunAkhir.setText(data.tahun_akhir)
    }
}