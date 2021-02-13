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

        fun bind(alamatReq: AlamatReq) {
            with(itemView) {
                edt_alamat.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        alamatReq.alamat = s.toString()
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
                edt_thn_awal_alamat.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        alamatReq.tahun_awal = s.toString()
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
                edt_thn_akhir_alamat.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        alamatReq.tahun_akhir = s.toString()
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
                edt_rangka_alamat.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        alamatReq.dalam_rangka = s.toString()
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
                edt_ket_alamat.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        alamatReq.keterangan = s.toString()
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
                btn_delete_alamat.visibility = if (adapterPosition == 0) View.GONE
                else View.VISIBLE
                btn_delete_alamat.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        onClick.onDelete(adapterPosition)
                    }
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
        return AlamatHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AlamatHolder, position: Int) {
        holder.bind(list[position])
        val data =list[position]
        holder.itemView.edt_alamat.setText(data.alamat)
        holder.itemView.edt_thn_awal_alamat.setText(data.tahun_awal)
        holder.itemView.edt_thn_akhir_alamat.setText(data.tahun_akhir)
        holder.itemView.edt_rangka_alamat.setText(data.dalam_rangka)
        holder.itemView.edt_ket_alamat.setText(data.keterangan)
    }
}