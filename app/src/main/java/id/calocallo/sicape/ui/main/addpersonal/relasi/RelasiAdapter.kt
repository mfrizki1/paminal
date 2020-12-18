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
): RecyclerView.Adapter<RelasiAdapter.RelasiHolder>() {
    inner class RelasiHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val etNama = itemView.edt_nama_relasi
        val spJenis = itemView.sp_jenis_relasi
        val btnDelete = itemView.btn_delete_relasi
        val btnAdd = itemView.btn_add_relasi

        fun setListener() {
            etNama.addTextChangedListener(object: TextWatcher{
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

            val item = listOf("Dalam Negeri", "Luar Negeri")
            val adapterJenis = ArrayAdapter(context, R.layout.item_spinner, item)
            spJenis.setAdapter(adapterJenis)
            spJenis.setOnItemClickListener { parent, view, position, id ->
                if(position == 0){
                    list[adapterPosition].lokasi = "dalam_negeri"
                }else{
                    list[adapterPosition].lokasi = "luar_negeri"
                }
            }
            btnDelete.setOnClickListener {
                onClickrelasi.onDelete(adapterPosition)
            }
            btnAdd.setOnClickListener {
                onClickrelasi.onAdd()
            }


        }

    }
    interface OnClickRelasi {
        fun onDelete(position: Int)
        fun onAdd()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelasiHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_relasi, parent, false)
        val relasiViews = RelasiHolder(view)
        relasiViews.setListener()
        return relasiViews
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RelasiHolder, position: Int) {
        val data = list[position]
        holder.etNama.setText(data.nama)
        holder.spJenis.setText(data.lokasi)
    }
}