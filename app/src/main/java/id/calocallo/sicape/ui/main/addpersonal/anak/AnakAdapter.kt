package id.calocallo.sicape.ui.main.addpersonal.anak

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.AnakReq
import kotlinx.android.synthetic.main.layout_anak.view.*

class AnakAdapter(
    val context: Context,
    val list: ArrayList<AnakReq>,
    val onClickAnak: OnClickAnak
) : RecyclerView.Adapter<AnakAdapter.AnakHolder>() {
    inner class AnakHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val etNama = itemView.edt_nama_lengkap_anak
        val spJK = itemView.spinner_jk_anak
        val etTmptLhr = itemView.edt_tmpt_ttl_anak
        val etTglLhr = itemView.edt_tgl_ttl_anak
        val etPekerjaan = itemView.edt_pekerjaan_anak
        val etOrganisasi = itemView.edt_organisasi_anak
        val etKet = itemView.edt_ket_anak
        val btnDelete = itemView.btn_delete_anak
        val spJenis = itemView.sp_status_ikatan


        fun bind(anakReq: AnakReq) {
            with(itemView) {
                btnDelete.visibility = if (adapterPosition == 0) View.GONE
                else View.VISIBLE

                val itemIkatan = listOf("Kandung", "Tiri", "Angkat")
                val adapterIkatan = ArrayAdapter(context, R.layout.item_spinner, itemIkatan)
                spJenis.setAdapter(adapterIkatan)
                spJenis.setOnItemClickListener { parent, view, position, id ->
                    if (position == 0) {
                        anakReq.status_ikatan = "kandung"
                    } else if (position == 1) {
                        anakReq.status_ikatan = "tiri"
                    } else {
                        anakReq.status_ikatan = "angkat"
                    }
                }

                etNama.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        anakReq.nama = s.toString()
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
                        anakReq.tempat_lahir = s.toString()
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
                        anakReq.tanggal_lahir = s.toString()
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
                        anakReq.pekerjaan_atau_sekolah = s.toString()
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
                        anakReq.organisasi_yang_diikuti = s.toString()
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
                        anakReq.keterangan = s.toString()
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
                    when (position) {
                        0 -> anakReq.jenis_kelamin = "laki_laki"
                        1 -> anakReq.jenis_kelamin = "perempuan"
                    }
//                    anakReq.jenis_kelamin =
//                        parent.getItemAtPosition(position).toString()
                }
                btnDelete.visibility = if (adapterPosition == 0) View.GONE else View.VISIBLE
                btnDelete.setOnClickListener {
                    onClickAnak.onDelete(adapterPosition)
                }
            }
        }

    }

    interface OnClickAnak {
        fun onDelete(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnakHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_anak, parent, false)
        return AnakHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AnakHolder, position: Int) {
        holder.bind(list[position])
        val data = list[position]
        holder.etKet.setText(data.keterangan)
        holder.etNama.setText(data.nama)
        holder.spJK.setText(data.jenis_kelamin)
        holder.etTmptLhr.setText(data.tempat_lahir)
        holder.etTglLhr.setText(data.tanggal_lahir)
        holder.etPekerjaan.setText(data.pekerjaan_atau_sekolah)
        holder.etOrganisasi.setText(data.organisasi_yang_diikuti)
        holder.spJenis.setText(data.status_ikatan)
        val itemIkatan = listOf("Kandung", "Tiri", "Angkat")
        val adapterIkatan = ArrayAdapter(context, R.layout.item_spinner, itemIkatan)
        holder.spJenis.setAdapter(adapterIkatan)
        holder.spJenis.setOnItemClickListener { parent, view, position, id ->
            if (position == 0) {
                data.status_ikatan = "kandung"
            } else if (position == 1) {
                data.status_ikatan = "tiri"
            } else {
                data.status_ikatan = "angkat"
            }
        }

        val item = listOf("Laki-Laki", "Perempuan")
        val adapter = ArrayAdapter(context, R.layout.item_spinner, item)
        holder.spJK.setAdapter(adapter)
        holder.spJK.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> data.jenis_kelamin = "laki_laki"
                1 -> data.jenis_kelamin = "perempuan"
            }
        }
    }


}