package id.calocallo.sicape.ui.main.addpersonal.pendidikan.lainnya

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
import kotlinx.android.synthetic.main.layout_pendidikan_others.view.*

class PendOtherAdapter(
    val context: Context,
    val list: ArrayList<AddPendidikanModel>,
    val onClickOther: OnCLickOther
) : RecyclerView.Adapter<PendOtherAdapter.OtherHolder>() {

    inner class OtherHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(addPendidikanModel: AddPendidikanModel) {
            with(itemView) {
                addPendidikanModel.jenis ="lain_lain"
                edt_nama_others_custom.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
//                    Log.e("nameAfter", s.toString())
                        addPendidikanModel.pendidikan = s.toString()


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
//                    onCLick.onName(list[adapterPosition], s.toString(), adapterPosition)
                    }
                })

                edt_thn_awal_others_custom.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
//                    Log.e("thnAwalAfter", s.toString())
                        addPendidikanModel.tahun_awal = s.toString()

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
//                    Log.e("thnAwal", "thnAwal")
//                    onCLick.onThnAwal(list[adapterPosition], s.toString(), adapterPosition)
                    }
                })

                edt_thn_akhir_others_custom.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
//                    Log.e("thnAkhir", s.toString())
                        addPendidikanModel.tahun_akhir = s.toString()


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
//                    Log.e("thnAkhir", "thnAkhir")
//                    onCLick.onThnAkhir(list[adapterPosition], s.toString(), adapterPosition)
                    }
                })

                edt_tempat_others_custom.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
//                    Log.e("tmpt", s.toString())
                        addPendidikanModel.kota = s.toString()


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

                edt_membiayai_others_custom.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
//                    Log.e("org", s.toString())
                        addPendidikanModel.yang_membiayai = s.toString()


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

                edt_ket_others_custom.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        Log.e("ket", s.toString())
                        addPendidikanModel.keterangan = s.toString()

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

                btn_delete_others_custom.visibility = if (adapterPosition == 0) View.GONE
                else View.VISIBLE

                btn_delete_others_custom.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        onClickOther.onDelete(adapterPosition)
                    }
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OtherHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_pendidikan_others, parent, false)
        return OtherHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: OtherHolder, position: Int) {
        holder.bind(list[position])
        val data = list[position]
        holder.itemView.edt_nama_others_custom.setText(data.pendidikan)
        holder.itemView.edt_thn_awal_others_custom.setText(data.tahun_awal)
        holder.itemView.edt_thn_akhir_others_custom.setText(data.tahun_akhir)
        holder.itemView.edt_tempat_others_custom.setText(data.kota)
        holder.itemView.edt_membiayai_others_custom.setText(data.yang_membiayai)
        holder.itemView.edt_ket_others_custom.setText(data.keterangan)
    }

    interface OnCLickOther {
        fun onDelete(position: Int)
    }
}