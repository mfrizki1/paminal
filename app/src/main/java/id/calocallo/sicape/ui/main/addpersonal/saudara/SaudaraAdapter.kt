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
import id.calocallo.sicape.model.SaudaraModel
import kotlinx.android.synthetic.main.layout_saudara.view.*

class SaudaraAdapter(
    val context: Context,
    val list: ArrayList<SaudaraModel>,
    val onClickSaudara: OnClickSaudara
) : RecyclerView.Adapter<SaudaraAdapter.SaudaraHolder>() {
    inner class SaudaraHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val etNama = itemView.edt_nama_lengkap_saudara
        val spJK = itemView.spinner_jk_saudara
        val etTmptLhr = itemView.edt_tmpt_ttl_saudara
        val etTglLhr = itemView.edt_tgl_ttl_saudara
        val etUmur = itemView.edt_umur_saudara
        val etPekerjaan = itemView.edt_pekerjaan_saudara
        val etOrganisasi = itemView.edt_organisasi_saudara
        val etKet = itemView.edt_ket_saudara
        val btnDelete = itemView.btn_delete_saudara


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
            etTmptLhr.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    list[adapterPosition].tmpt_lhr = s.toString()
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
            etTglLhr.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    list[adapterPosition].tgl_lhr = s.toString()
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
            etUmur.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    list[adapterPosition].umur = s.toString()
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
            etPekerjaan.addTextChangedListener(object : TextWatcher {
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
            etOrganisasi.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    list[adapterPosition].organisasi = s.toString()
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

            val item = listOf("Laki-Laki", "Perempuan")
            val adapter = ArrayAdapter(context, R.layout.item_spinner, item)
            spJK.setAdapter(adapter)
            spJK.setOnItemClickListener { parent, view, position, id ->
                list[adapterPosition].jk = parent.getItemAtPosition(position).toString()
            }

            btnDelete.setOnClickListener {
                onClickSaudara.onDelete(adapterPosition)
            }
        }

    }

    interface OnClickSaudara {
        fun onDelete(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaudaraHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_saudara, parent, false)
        var saudaraViews = SaudaraHolder(view)
        saudaraViews.setListener()
        return saudaraViews
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SaudaraHolder, position: Int) {
        val data = list[position]
        holder.etNama.setText(data.nama)
        holder.etKet.setText(data.ket)
        holder.spJK.setText(data.jk)
        holder.etOrganisasi.setText(data.organisasi)
        holder.etTglLhr.setText(data.tgl_lhr)
        holder.etTmptLhr.setText(data.tmpt_lhr)
        holder.etPekerjaan.setText(data.pekerjaan)
        holder.etUmur.setText(data.umur)

    }
}