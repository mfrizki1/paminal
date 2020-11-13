package id.calocallo.sicape.ui.main.addpersonal.medsos

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.MedsosModel
import kotlinx.android.synthetic.main.layout_medsos.view.*

class MedSosAdapter(
    val context: Context,
    val list: ArrayList<MedsosModel>,
    val onClickMedsos: OnClickMedsos
) : RecyclerView.Adapter<MedSosAdapter.MedsosHolder>() {
    inner class MedsosHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val etNama = itemView.edt_nama_medsos
        val etNamaAcc = itemView.edt_acc_medsos
        val etAlasan = itemView.edt_alasan_medsos
        val etKet = itemView.edt_ket_medsos
        val btnDelete = itemView.btn_delete_medsos


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
            etNamaAcc.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    list[adapterPosition].nama_acc = s.toString()
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
            etAlasan.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    list[adapterPosition].alasan = s.toString()
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

            btnDelete.setOnClickListener {
                onClickMedsos.onDelete(adapterPosition)
            }
        }

    }

    interface OnClickMedsos {
        fun onDelete(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedsosHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_medsos, parent, false)
        var medsosViews = MedsosHolder(view)
        medsosViews.setListener()
        return medsosViews
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MedsosHolder, position: Int) {
        val data = list[position]
        holder.etNama.setText(data.nama)
        holder.etKet.setText(data.ket)
        holder.etAlasan.setText(data.alasan)
        holder.etNamaAcc.setText(data.nama_acc)
    }
}