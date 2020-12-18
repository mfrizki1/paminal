package id.calocallo.sicape.ui.main.addpersonal.pendidikan.dinas

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AddPendidikanModel
import kotlinx.android.synthetic.main.layout_pendidikan_kedinasan.view.*

class PendDinasAdapter(
    val context: Context,
    val list: ArrayList<AddPendidikanModel>,
    var onClick: OnClickDinas
) : RecyclerView.Adapter<PendDinasAdapter.DinasHolder>() {
    inner class DinasHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val etName = itemView.edt_nama_kedinasan_custom
        val etKet = itemView.edt_ket_kedinasan_custom
        val etThnAwal = itemView.edt_thn_awal_kedinasan_custom
        val etThnAkhir = itemView.edt_thn_akhir_kedinasan_custom
        val etTmpt = itemView.edt_tempat_kedinasan_custom
        val etOrgMmbiayai = itemView.edt_membiayai_kedinasan_custom
        val btnDelete = itemView.btn_delete_dinas_custom

        var name = ""
        var thnAwal = ""
        var thnAkhir = ""
        var tmpt = ""
        var org = ""
        var ket = ""
        fun setListener() {
            etName.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    list[adapterPosition].pendidikan = s.toString()
                    name = list[adapterPosition].pendidikan
                    Log.e("name", name)
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
//                    Log.e("thnAwalAfter", s.toString())
                    list[adapterPosition].tahun_awal = s.toString()
                    thnAwal = list[adapterPosition].tahun_awal
                    Log.e("awal", thnAwal)

                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                    Log.e("thnAwal", "thnAwal")
//                    onCLick.onThnAwal(list[adapterPosition], s.toString(), adapterPosition)
                }
            })

            etThnAkhir.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
//                    Log.e("thnAkhir", s.toString())
                    list[adapterPosition].tahun_akhir = s.toString()
                    thnAkhir = list[adapterPosition].tahun_akhir
                    Log.e("akhir", thnAkhir)


                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                    Log.e("thnAkhir", "thnAkhir")
//                    onCLick.onThnAkhir(list[adapterPosition], s.toString(), adapterPosition)
                }
            })

            etTmpt.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
//                    Log.e("tmpt", s.toString())
                    list[adapterPosition].kota = s.toString()
                    tmpt = list[adapterPosition].kota
                    Log.e("tmp", tmpt)


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

            etOrgMmbiayai.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
//                    Log.e("org", s.toString())
                    list[adapterPosition].yang_membiayai = s.toString()
                    org = list[adapterPosition].yang_membiayai
                    Log.e("org", org)


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
                    Log.e("ket", s.toString())
                    list[adapterPosition].keterangan = s.toString()
                    ket = list[adapterPosition].keterangan.toString()
                    Log.e("ket", ket)

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

            btnDelete.visibility = if (adapterPosition == 0) View.GONE
            else View.VISIBLE

            btnDelete.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onClick.onDelete(adapterPosition)
                }
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PendDinasAdapter.DinasHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_pendidikan_kedinasan, parent, false)
        val dinasViews = DinasHolder(view)
        dinasViews.setListener()
        return dinasViews
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PendDinasAdapter.DinasHolder, position: Int) {
        bindViews(holder, position)
    }

    private fun bindViews(holder: DinasHolder, position: Int) {
        val data = list[position]
        holder.etName.setText(data.pendidikan)
        holder.etThnAwal.setText(data.tahun_awal)
        holder.etTmpt.setText(data.kota)
        holder.etOrgMmbiayai.setText(data.yang_membiayai)
        holder.etThnAkhir.setText(data.tahun_akhir)
        holder.etKet.setText(data.keterangan)
    }

    interface OnClickDinas {
        fun onDelete(position: Int)
    }
}