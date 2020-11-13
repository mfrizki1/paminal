package id.calocallo.sicape.ui.main.addpersonal.pekerjaan

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PekerjaanODinasModel
import kotlinx.android.synthetic.main.layout_pekerjaan_luar_dinas.view.*

class PekerjaanODinasAdapter(
    val context: Context,
    val list: ArrayList<PekerjaanODinasModel>,
    val onClickODinas: OnClickODinas
) : RecyclerView.Adapter<PekerjaanODinasAdapter.OutDinasHolder>() {
    inner class OutDinasHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val etInstansi = itemView.edt_instansi_pekerjaan_outdinas_custom
        val etName = itemView.edt_nama_pekerjaan_outdinas_custom
        val etThnLama = itemView.edt_lama_thn_pekerjaan_outdinas_custom
        val etRangka = itemView.edt_rangka_pekerjaan_outdinas_custom
        val etKet = itemView.edt_ket_pekerjaan_outdinas_custom
        val btnDelete = itemView.btn_delete_pekerjaan_outdinas_custom
        fun setListener() {
            etName.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    list[adapterPosition].nama_pkrjan = s.toString()
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
            etThnLama.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    list[adapterPosition].thnLama = s.toString()
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
        var ODinas = OutDinasHolder(view)
        ODinas.setListener()
        return ODinas
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: OutDinasHolder, position: Int) {
        val data = list[position]
        holder.etInstansi.setText(data.instansi)
        holder.etKet.setText(data.ket)
        holder.etName.setText(data.nama_pkrjan)
        holder.etThnLama.setText(data.thnLama)
    }
}