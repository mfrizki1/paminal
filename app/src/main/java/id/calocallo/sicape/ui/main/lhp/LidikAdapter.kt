package id.calocallo.sicape.ui.main.lhp

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ListLidik
import kotlinx.android.synthetic.main.item_lidik.view.*

class LidikAdapter(
    val context: Context,
    val list: ArrayList<ListLidik>,
    val onClickLidik: OnClickLidik
) : RecyclerView.Adapter<LidikAdapter.LidikHolder>() {
    inner class LidikHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(listLidik: ListLidik) {
            with(itemView) {
                edt_nama_lidik.setText(listLidik.nama_lidik)
                edt_nrp_lidik.setText(listLidik.nrp_lidik)
                edt_pangkat_lidik.setText(listLidik.pangkat_lidik)

                edt_nama_lidik.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        listLidik.nama_lidik = s.toString()
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
                edt_nrp_lidik.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        listLidik.nrp_lidik = s.toString()
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
                edt_pangkat_lidik.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        listLidik.pangkat_lidik = s.toString()
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
                btn_delete_lidik.visibility = if (adapterPosition == 0) View.GONE
                else View.VISIBLE

                btn_delete_lidik.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION)
                        onClickLidik.onDelete(adapterPosition)
                }
                btn_add_lidik.setOnClickListener {
                    onClickLidik.onAdd()
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LidikHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lidik, parent, false)
        return LidikHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: LidikHolder, position: Int) {
        holder.bind(list[position])
    }

    interface OnClickLidik {
        fun onAdd()
        fun onDelete(position: Int)
    }
}