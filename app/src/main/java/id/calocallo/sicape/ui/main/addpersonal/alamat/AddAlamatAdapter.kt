package id.calocallo.sicape.ui.main.addpersonal.alamat

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.AlamatReq
import kotlinx.android.synthetic.main.layout_alamat.view.*

class AddAlamatAdapter(
    val context: Context,
    val list: ArrayList<AlamatReq>,
    val onClick: OnClickAlamat
) : RecyclerView.Adapter<AddAlamatAdapter.AlamatHolder>() {
    inner class AlamatHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val etNameAlamat = itemView.edt_alamat
        val etThnAwal = itemView.edt_thn_awal_alamat
        val etThnAkhir = itemView.edt_thn_akhir_alamat
        val etRangka = itemView.edt_rangka_alamat
        val etKet = itemView.edt_ket_alamat
        val btnDelete = itemView.btn_delete_alamat
        fun setListener() {
            etNameAlamat.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    list[adapterPosition].alamat = s.toString()
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
            etThnAkhir.addTextChangedListener(object : TextWatcher {
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
                    onClick.onDelete(adapterPosition)
                }
            }
        }

    }

    interface OnClickAlamat {
        fun onDelete(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlamatHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_alamat, parent, false)
        val alamatVies = AlamatHolder(view)
        alamatVies.setListener()
        return alamatVies
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AlamatHolder, position: Int) {
        val data = list[position]
        holder.etRangka.setText(data.dalam_rangka)
        holder.etKet.setText(data.keterangan)
        holder.etThnAkhir.setText(data.tahun_akhir)
        holder.etThnAwal.setText(data.tahun_awal)
        holder.etNameAlamat.setText(data.alamat)
    }
}