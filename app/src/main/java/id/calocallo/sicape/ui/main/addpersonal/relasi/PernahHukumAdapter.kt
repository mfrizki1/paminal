package id.calocallo.sicape.ui.main.addpersonal.relasi

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.HukumanReq
import kotlinx.android.synthetic.main.activity_add_catpers.view.*
import kotlinx.android.synthetic.main.layout_hukuman.view.*

class PernahHukumAdapter(
    val context: Context,
    val list: ArrayList<HukumanReq>,
    val onClickHukum: OnClickHukum
) : RecyclerView.Adapter<PernahHukumAdapter.HukumHolder>() {
    inner class HukumHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val etNama = itemView.edt_perkara_hukum
        val btnDelete = itemView.btn_delete_hukuman
        val btnAdd = itemView.btn_add_pernah_dihukum

        fun setListener() {
            etNama.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    list[adapterPosition].perkara = s.toString()
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
                onClickHukum.onDelete(adapterPosition)
            }
            btnAdd.setOnClickListener{
                onClickHukum.onAdd()
            }

        }

    }

    interface OnClickHukum {
        fun onDelete(position: Int)
        fun onAdd()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HukumHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_hukuman, parent, false)
        val hukumViews = HukumHolder(view)
        hukumViews.setListener()
        return hukumViews
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HukumHolder, position: Int) {
        val data = list[position]
        holder.etNama.setText(data.perkara)
    }
}