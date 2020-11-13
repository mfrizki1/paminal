package id.calocallo.sicape.ui.main.addpersonal.misc.organisasi

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.OrganisasiModel
import kotlinx.android.synthetic.main.layout_organisasi.view.*

class OrganisasiAdapter(
    val context: Context,
    val list: ArrayList<OrganisasiModel>,
    val onClickOrg: OnCLickOrg
) : RecyclerView.Adapter<OrganisasiAdapter.OrgHolder>() {
    inner class OrgHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val etNama = itemView.edt_organisasi
        val etThnAwal = itemView.edt_thn_awal_organisasi
        val etThnAkhir = itemView.edt_thn_akhir_organisasi
        val etKedudukan = itemView.edt_kedudukan_organisasi
        val etThnIkut = itemView.edt_thn_ikut_organisasi
        val etAlamat = itemView.edt_alamat_organisasi
        val etKet = itemView.edt_ket_organisasi
        val btnDelete = itemView.btn_delete_organisasi

        fun setListener() {
            etNama.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    list[adapterPosition].nama = s.toString()
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
                    list[adapterPosition].thnAwal = s.toString()
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
                    list[adapterPosition].thnAkhir = s.toString()
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
            etKedudukan.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    list[adapterPosition].kedudukan = s.toString()
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
            etThnIkut.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    list[adapterPosition].thnIkut = s.toString()
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
            etAlamat.addTextChangedListener(object : TextWatcher {
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
                    onClickOrg.onDelete(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrgHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_organisasi, parent, false)
        var orgViews = OrgHolder(view)
        orgViews.setListener()
        return orgViews
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: OrgHolder, position: Int) {
        val data = list[position]
        holder.etNama.setText(data.nama)
        holder.etKedudukan.setText(data.kedudukan)
        holder.etThnIkut.setText(data.thnIkut)
        holder.etAlamat.setText(data.alamat)
        holder.etThnAkhir.setText(data.thnAkhir)
        holder.etThnAwal.setText(data.thnAwal)
        holder.etNama.setText(data.nama)
        holder.etKet.setText(data.ket)

    }

    interface OnCLickOrg {
        fun onDelete(position: Int)
    }
}