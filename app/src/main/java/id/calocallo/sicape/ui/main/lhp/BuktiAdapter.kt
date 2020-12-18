package id.calocallo.sicape.ui.main.lhp

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ListBukti
import kotlinx.android.synthetic.main.item_bukti.view.*

class BuktiAdapter(
    val context: Context,
    val list: ArrayList<ListBukti>,
    val onClickBukti: OnClickBukti
) : RecyclerView.Adapter<BuktiAdapter.BuktiHolder>() {
    inner class BuktiHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(listBukti: ListBukti) {
            with(itemView) {
                edt_bukti.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        listBukti.bukti = s.toString()
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

                btn_delete_bukti.visibility = if (adapterPosition == 0) View.GONE
                else View.VISIBLE
                btn_delete_bukti.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        onClickBukti.onDelete(adapterPosition)
                    }
                }
                btn_add_bukti.setOnClickListener {
                    onClickBukti.onAdd()
                }
            }
        }

    }

    interface OnClickBukti {
        fun onAdd()
        fun onDelete(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuktiHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bukti, parent, false)
        return BuktiHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BuktiHolder, position: Int) {
        holder.bind(list[position])
    }
}