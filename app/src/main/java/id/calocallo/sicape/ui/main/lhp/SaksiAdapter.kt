package id.calocallo.sicape.ui.main.lhp

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ListSaksi
import kotlinx.android.synthetic.main.item_saksi.view.*

class SaksiAdapter(
    val context: Context,
    val list: ArrayList<ListSaksi>,
    val onClickSaksi: OnClickSaksi
) : RecyclerView.Adapter<SaksiAdapter.SaksiHolder>() {
    inner class SaksiHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(listSaksi: ListSaksi) {
            with(itemView){
                edt_nama_saksi.addTextChangedListener(object: TextWatcher{
                    override fun afterTextChanged(s: Editable?) {
                        listSaksi.nama_saksi = s.toString()
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
                edt_uraian_saksi.addTextChangedListener(object: TextWatcher{
                    override fun afterTextChanged(s: Editable?) {
                        listSaksi.uraian_saksi = s.toString()
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

                btn_delete_saksi.visibility =if (adapterPosition == 0) View.GONE
                else View.VISIBLE
                btn_delete_saksi.setOnClickListener {
                    if(adapterPosition != RecyclerView.NO_POSITION){
                        onClickSaksi.onDelete(adapterPosition)
                    }
                }
                btn_add_saksi.setOnClickListener {
                    onClickSaksi.onAdd()
                }
            }
        }

    }
    interface OnClickSaksi{
        fun onAdd()
        fun onDelete(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaksiHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_saksi, parent, false)
        return SaksiHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SaksiHolder, position: Int) {
        holder.bind(list[position])
    }
}