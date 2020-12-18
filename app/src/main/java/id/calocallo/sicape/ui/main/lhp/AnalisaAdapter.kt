package id.calocallo.sicape.ui.main.lhp

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ListAnalisa
import kotlinx.android.synthetic.main.item_analisa.view.*
import kotlinx.android.synthetic.main.item_bukti.view.*

class AnalisaAdapter(
    val context: Context,
    val list: ArrayList<ListAnalisa>,
    val onClickAnalisa: OnClickAnalisa
) : RecyclerView.Adapter<AnalisaAdapter.AnalisaHolder>() {
    inner class AnalisaHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(listAnalisa: ListAnalisa) {
            with(itemView) {
                edt_analisa.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        listAnalisa.analisa = s.toString()
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

                btn_delete_analisa.visibility = if (adapterPosition == 0) View.GONE
                else View.VISIBLE
                btn_delete_analisa.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        onClickAnalisa.onDelete(adapterPosition)
                    }
                }
                btn_add_analisa.setOnClickListener {
                    onClickAnalisa.onAdd()
                }
            }
        }

    }

    interface OnClickAnalisa {
        fun onAdd()
        fun onDelete(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnalisaHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_analisa, parent, false)
        return AnalisaHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AnalisaHolder, position: Int) {
        holder.bind(list[position])
    }
}
