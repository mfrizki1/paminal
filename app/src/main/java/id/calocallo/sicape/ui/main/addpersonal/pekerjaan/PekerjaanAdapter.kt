package id.calocallo.sicape.ui.main.addpersonal.pekerjaan

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.AddSinglePekerjaanReq
import kotlinx.android.synthetic.main.layout_pekerjaan.view.*
import kotlinx.android.synthetic.main.layout_pendidikan_umum.view.*

class PekerjaanAdapter(
    val context: Context,
    val list: ArrayList<AddSinglePekerjaanReq>,
    var onClickPkrjaan: OnCLickPekerjaan
) : RecyclerView.Adapter<PekerjaanAdapter.PekerjaanHolder>() {
    inner class PekerjaanHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val etName = itemView.edt_nama_pekerjaan_custom
        val etPangkat = itemView.edt_pangkat_pekerjaan_custom
        val etKesatuan = itemView.edt_kesatuan_pekerjaan_custom
        val etThnLama = itemView.edt_lama_thn_pekerjaan_custom
        val etKet = itemView.edt_ket_pekerjaan_custom
        val btnDelete = itemView.btn_delete_pekerjaan_custom
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
            etPangkat.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    list[adapterPosition].golongan = s.toString()
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
            etKesatuan.addTextChangedListener(object : TextWatcher {
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
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    list[adapterPosition].berapa_tahun = s.toString()

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
            btnDelete.visibility = if (adapterPosition == 0) View.GONE else View.VISIBLE
            btnDelete.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onClickPkrjaan.onDelete(adapterPosition)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PekerjaanHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_pekerjaan, parent, false)
        val pkrjaanVIews = PekerjaanHolder(view)
        pkrjaanVIews.setListener()
        return pkrjaanVIews
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PekerjaanHolder, position: Int) {
        val data = list[position]
        holder.etName.setText(data.pekerjaan)
        holder.etThnLama.setText(data.berapa_tahun.toString())
        holder.etKesatuan.setText(data.instansi)
        holder.etPangkat.setText(data.golongan)
        holder.etKet.setText(data.keterangan)
    }

    interface OnCLickPekerjaan {
        fun onDelete(position: Int)
    }
}