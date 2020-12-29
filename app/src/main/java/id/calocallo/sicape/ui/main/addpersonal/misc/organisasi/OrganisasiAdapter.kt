package id.calocallo.sicape.ui.main.addpersonal.misc.organisasi

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.OrganisasiReq
import kotlinx.android.synthetic.main.layout_organisasi.view.*

class OrganisasiAdapter(
    val context: Context,
    val list: ArrayList<OrganisasiReq>,
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

        }

        fun bind(organisasiReq: OrganisasiReq) {
            with(itemView) {
                edt_organisasi.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        organisasiReq.organisasi = s.toString()
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
                edt_thn_awal_organisasi.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        organisasiReq.tahun_awal = s.toString()
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
                edt_thn_akhir_organisasi.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        organisasiReq.tahun_akhir = s.toString()
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
                edt_kedudukan_organisasi.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        organisasiReq.jabatan = s.toString()
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
                edt_thn_ikut_organisasi.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        organisasiReq.tahun_bergabung = s.toString()
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
                edt_alamat_organisasi.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        organisasiReq.alamat = s.toString()
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
                edt_ket_organisasi.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        organisasiReq.keterangan = s.toString()
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
                btn_delete_organisasi.visibility = if(adapterPosition == 0)View.GONE
                else View.VISIBLE
                btn_delete_organisasi.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        onClickOrg.onDelete(adapterPosition)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrgHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_organisasi, parent, false)
        return OrgHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: OrgHolder, position: Int) {
        holder.bind(list[position])
    }

    interface OnCLickOrg {
        fun onDelete(position: Int)
    }
}