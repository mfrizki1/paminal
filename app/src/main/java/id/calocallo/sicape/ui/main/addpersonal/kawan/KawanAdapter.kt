package id.calocallo.sicape.ui.main.addpersonal.kawan

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.SahabatReq
import kotlinx.android.synthetic.main.layout_kawan_dekat.view.*

class KawanAdapter(
    val context: Context,
    val list: ArrayList<SahabatReq>,
    val onClickKawan: OnClickKawan
) : RecyclerView.Adapter<KawanAdapter.KawanHolder>() {
    inner class KawanHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val etNama = itemView.edt_nama_kawan
        val spJk = itemView.spinner_jk_kawan
        val etAlamat = itemView.edt_alamat_kawan
        val etUmur = itemView.edt_umur_kawan
        val etPekerjaan = itemView.edt_pekerjaan_kawan
        val etAlasan = itemView.edt_alasan_kawan
        val btnDelete = itemView.btn_delete_kawan
        
        fun bind(sahabatReq: SahabatReq) {
            with(itemView) {
                etNama.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        sahabatReq.nama = s.toString()
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
                etAlamat.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        sahabatReq.alamat = s.toString()
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
                etUmur.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        sahabatReq.umur = s.toString()
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
                etAlasan.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        sahabatReq.alasan = s.toString()
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
                        sahabatReq.pekerjaan = s.toString()
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
                spJk.setAdapter(adapter)
                spJk.setOnItemClickListener { _, _, position, _ ->
                    when(position){
                        0->sahabatReq.jenis_kelamin = "laki_laki"
                        1->sahabatReq.jenis_kelamin = "perempuan"
                    }
//                    sahabatReq.jenis_kelamin =
//                        parent.getItemAtPosition(position).toString()
                }
                btnDelete.visibility = if (adapterPosition == 0) View.GONE else View.VISIBLE
                btnDelete.setOnClickListener {
                    onClickKawan.onDelete(adapterPosition)
                }
            }
        }

    }

    interface OnClickKawan {
        fun onDelete(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KawanHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_kawan_dekat, parent, false)
        return KawanHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: KawanHolder, position: Int) {
        holder.bind(list[position])
        val data = list[position]
        holder.etNama.setText(data.nama)
        holder.spJk.setText(data.jenis_kelamin)
        holder.etUmur.setText(data.umur)
        holder.etPekerjaan.setText(data.pekerjaan)
        holder.etAlamat.setText(data.alamat)
        holder.etAlasan.setText(data.alamat)

                val item = listOf("Laki-Laki", "Perempuan")
        val adapter = ArrayAdapter(context, R.layout.item_spinner, item)
        holder.spJk.setAdapter(adapter)
        holder.spJk.setOnItemClickListener { _, _, position, _ ->
            when(position){
                0->data.jenis_kelamin = "laki_laki"
                1->data.jenis_kelamin = "perempuan"
            }
        }
    }
}