package id.calocallo.sicape.ui.main.addpersonal.relasi

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.RelasiReq
import kotlinx.android.synthetic.main.layout_relasi.view.*

class RelasiAdapter(
    val context: Context,
    val list: ArrayList<RelasiReq>,
    val onClickrelasi: OnClickRelasi
) : RecyclerView.Adapter<RelasiAdapter.RelasiHolder>() {
    interface OnClickRelasi {
        fun onDelete(position: Int)
        fun onAdd()
    }

    inner class RelasiHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(relasiReq: RelasiReq) {
            with(itemView) {
                edt_nama_relasi.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        relasiReq.nama = s.toString()
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
                val item = listOf("Dalam Negeri", "Luar Negeri")
                val adapterJenis = ArrayAdapter(context, R.layout.item_spinner, item)
                sp_jenis_relasi.setAdapter(adapterJenis)
                sp_jenis_relasi.setOnItemClickListener { parent, view, position, id ->
                    if (position == 0) {
                        relasiReq.lokasi = "dalam_negeri"
                    } else {
                        relasiReq.lokasi = "luar_negeri"
                    }
                }
                btn_delete_relasi.visibility = if (adapterPosition == 0) View.GONE
                else View.VISIBLE
                btn_delete_relasi.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        onClickrelasi.onDelete(adapterPosition)
                    }
                }
                btn_add_relasi.setOnClickListener{
                    onClickrelasi.onAdd()
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelasiHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_relasi, parent, false)
        return RelasiHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RelasiHolder, position: Int) {
        holder.bind(list[position])
    }

}