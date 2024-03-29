package id.calocallo.sicape.ui.main.addpersonal.orangs

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.OrangsReq
import kotlinx.android.synthetic.main.layout_orang_sama.view.*

class OrangsAdapter(
    val txt_judul: String,
    val context: Context,
    val list: ArrayList<OrangsReq>,
    val onClickOrangs: OnClickOrangs
) : RecyclerView.Adapter<OrangsAdapter.OrangHolder>() {
    inner class OrangHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtJudul = itemView.txt_org_sama
        val etNama = itemView.edt_nama_lengkap_org_sama
        val spJK = itemView.spinner_jk_org_sama
        val etUmur = itemView.edt_umur_org_sama
        val etPekerjaan = itemView.edt_pekerjaan_org_sama
        val etKet = itemView.edt_ket_org_sama
        val etAlamat = itemView.edt_alamat_org_sama
        val btnDelete = itemView.btn_delete_org_sama

        fun setListener() {
            txtJudul.text = txt_judul
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

            val item = listOf("Laki-Laki", "Perempuan")
            val adapter = ArrayAdapter(context, R.layout.item_spinner, item)
            spJK.setAdapter(adapter)
            spJK.setOnItemClickListener { parent, view, position, id ->
                list[adapterPosition].jenis_kelamin = parent.getItemAtPosition(position).toString()
            }

            btnDelete.setOnClickListener {
                onClickOrangs.onDelete(adapterPosition)
            }
        }

    }

    interface OnClickOrangs {
        fun onDelete(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrangHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_orang_sama, parent, false)
        val orangsViews = OrangHolder(view)
        orangsViews.setListener()
        return orangsViews
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: OrangHolder, position: Int) {
        val data = list[position]
        holder.etNama.setText(data.nama)
        holder.etKet.setText(data.keterangan)
        holder.spJK.setText(data.jenis_kelamin)
        holder.etPekerjaan.setText(data.pekerjaan)
        holder.etUmur.setText(data.umur)
        holder.etAlamat.setText(data.alamat)
    }
}