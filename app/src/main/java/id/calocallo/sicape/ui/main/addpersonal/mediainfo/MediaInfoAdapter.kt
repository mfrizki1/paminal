package id.calocallo.sicape.ui.main.addpersonal.mediainfo

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.MedInfoReq
import kotlinx.android.synthetic.main.layout_med_info.view.*

class MediaInfoAdapter(
    val context: Context,
    val list: ArrayList<MedInfoReq>,
    val onClickMedInfo: OnClickMedInfo
) : RecyclerView.Adapter<MediaInfoAdapter.MedInfoHolder>() {
    inner class MedInfoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val etNama = itemView.edt_nama_med_info
        val etTopik = itemView.edt_topik_med_info
        val etAlasan = itemView.edt_alasan_med_info
        val etKet = itemView.edt_ket_med_info
        val btnDelete = itemView.btn_delete_med_info


        fun bind(medInfoReq: MedInfoReq) {
            with(itemView) {
                etNama.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        medInfoReq.sumber = s.toString()
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
                etTopik.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        medInfoReq.topik = s.toString()
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
                        medInfoReq.alasan = s.toString()
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
                        medInfoReq.keterangan = s.toString()
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
                btnDelete.visibility = if (adapterPosition == 0) View.GONE else View.VISIBLE
                btnDelete.setOnClickListener {
                    onClickMedInfo.onDelete(adapterPosition)
                }
            }
        }

    }

    interface OnClickMedInfo {
        fun onDelete(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedInfoHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_med_info, parent, false)
        return MedInfoHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MedInfoHolder, position: Int) {
        holder.bind(list[position])
    }
}