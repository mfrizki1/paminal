package id.calocallo.sicape.ui.main.addpersonal.pendidikan.umum

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
import kotlinx.android.synthetic.main.layout_pendidikan_umum.view.*

class PendUmumAdapter(
    val context: Context,
    val list: ArrayList<AddPendidikanModel>,
    var onCLick: OnClick
) : RecyclerView.Adapter<PendUmumAdapter.UmumHolder>() {
    inner class UmumHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(addPendidikanModel: AddPendidikanModel) {
            with(itemView) {
                edt_nama_umum_custom.addTextChangedListener(object :TextWatcher{
                    override fun afterTextChanged(s: Editable?) {
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
                    }
                })
                edt_thn_awal_umum_custom.addTextChangedListener(object:TextWatcher{
                    override fun afterTextChanged(s: Editable?) {
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
                    }
                })
                edt_thn_akhir_umum_custom.addTextChangedListener(object:TextWatcher{
                    override fun afterTextChanged(s: Editable?) {
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
                    }
                })
                edt_tempat_umum_custom.addTextChangedListener(object:TextWatcher{
                    override fun afterTextChanged(s: Editable?) {
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
                edt_membiayai_umum_custom.addTextChangedListener(object:TextWatcher{
                    override fun afterTextChanged(s: Editable?) {
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
                btn_delete_umum_custom.visibility = if(adapterPosition == 0)View.GONE
                else View.VISIBLE
                btn_delete_umum_custom.setOnClickListener {
                    if(adapterPosition != RecyclerView.NO_POSITION) onCLick.onDelete(adapterPosition)
                }
            }
        }

    }

    interface OnClick {
        fun onDelete(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UmumHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_pendidikan_umum, parent, false)
        return UmumHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: UmumHolder, position: Int) {
        holder.bind(list[position])
    }

}
