package id.calocallo.sicape.ui.main.addpersonal.saudara

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.SaudaraReq
import kotlinx.android.synthetic.main.layout_saudara.view.*

class SaudaraAdapter(
    val context: Context,
    val list: ArrayList<SaudaraReq>,
    val onClickSaudara: OnClickSaudara
) : RecyclerView.Adapter<SaudaraAdapter.SaudaraHolder>() {
    inner class SaudaraHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val etNama = itemView.edt_nama_lengkap_saudara
        val spJK = itemView.spinner_jk_saudara
        val etTmptLhr = itemView.edt_tmpt_ttl_saudara
        val etTglLhr = itemView.edt_tgl_ttl_saudara
        val etPekerjaan = itemView.edt_pekerjaan_saudara
        val etOrganisasi = itemView.edt_organisasi_saudara
        val etKet = itemView.edt_ket_saudara
        val btnDelete = itemView.btn_delete_saudara
        val spJenis = itemView.sp_ikatan_saudara


        fun bind(saudaraReq: SaudaraReq) {
            with(itemView) {
                val itemIkatan = listOf("Kandung", "Tiri", "Angkat")
                val adapterIkatan = ArrayAdapter(context, R.layout.item_spinner, itemIkatan)
                spJenis.setAdapter(adapterIkatan)
                spJenis.setOnItemClickListener { parent, view, position, id ->
                    if (position == 0) {
                        saudaraReq.status_ikatan = "kandung"
                    } else if (position == 1) {
                        saudaraReq.status_ikatan = "tiri"
                    } else {
                        saudaraReq.status_ikatan = "angkat"
                    }
                }

                etNama.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        saudaraReq.nama = s.toString()
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
                etTmptLhr.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        saudaraReq.tempat_lahir = s.toString()
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
                etTglLhr.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        saudaraReq.tanggal_lahir = s.toString()
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
                etPekerjaan.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        saudaraReq.pekerjaan_atau_sekolah = s.toString()
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
                etOrganisasi.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        saudaraReq.organisasi_yang_diikuti = s.toString()
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
                etKet.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        saudaraReq.keterangan = s.toString()
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

                val item = listOf("Laki-Laki", "Perempuan")
                val adapter = ArrayAdapter(context, R.layout.item_spinner, item)
                spJK.setAdapter(adapter)
                spJK.setOnItemClickListener { parent, view, position, id ->
                    saudaraReq.jenis_kelamin =
                        parent.getItemAtPosition(position).toString()
                }
                btnDelete.visibility = if(adapterPosition == 0)View.GONE else View.VISIBLE
                btnDelete.setOnClickListener {
                    onClickSaudara.onDelete(adapterPosition)
                }
            }
        }

    }

    interface OnClickSaudara {
        fun onDelete(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaudaraHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_saudara, parent, false)
        return SaudaraHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SaudaraHolder, position: Int) {
        holder.bind(list[position])


    }
}