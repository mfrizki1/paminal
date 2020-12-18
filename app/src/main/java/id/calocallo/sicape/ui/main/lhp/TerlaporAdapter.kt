package id.calocallo.sicape.ui.main.lhp

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ListTerlapor
import kotlinx.android.synthetic.main.item_saksi.view.*
import kotlinx.android.synthetic.main.item_terlapor.view.*

class TerlaporAdapter(
    val context: Context,
    val list: ArrayList<ListTerlapor>,
    val onClickTerlapor: OnClickTerlapor
) : RecyclerView.Adapter<TerlaporAdapter.TerlaporHolder>() {
    inner class TerlaporHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(listTerlapor: ListTerlapor) {
            with(itemView){
                edt_nama_terlapor.addTextChangedListener(object: TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        listTerlapor.nama_terlapor = s.toString()
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
                edt_penjelasan_terlapor.addTextChangedListener(object: TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        listTerlapor.uraian_terlapor = s.toString()
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

                btn_delete_terlapor.visibility =if (adapterPosition == 0) View.GONE
                else View.VISIBLE
                btn_delete_terlapor.setOnClickListener {
                    if(adapterPosition != RecyclerView.NO_POSITION){
                        onClickTerlapor.onDelete(adapterPosition)
                    }
                }
                btn_add_terlapor.setOnClickListener {
                    onClickTerlapor.onAdd()
                }
            }
        }

    }
    interface OnClickTerlapor{
        fun onAdd()
        fun onDelete(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TerlaporHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_terlapor, parent, false)
        return TerlaporHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TerlaporHolder, position: Int) {
        holder.bind(list[position])
    }
}