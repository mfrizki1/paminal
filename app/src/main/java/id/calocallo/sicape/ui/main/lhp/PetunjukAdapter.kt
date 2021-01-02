package id.calocallo.sicape.ui.main.lhp

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ListPetunjuk
import kotlinx.android.synthetic.main.item_petunjuk.view.*
import kotlinx.android.synthetic.main.item_input_saksi.view.*

class PetunjukAdapter(
    val context: Context,
    val list: ArrayList<ListPetunjuk>,
    val onClickPetunjuk: OnClickPetunjuk
) : RecyclerView.Adapter<PetunjukAdapter.PetunjukHolder>() {
    inner class PetunjukHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(listPetunjuk: ListPetunjuk) {
            with(itemView) {
                edt_petunjuk.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        listPetunjuk.petunjuk = s.toString()
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

                btn_delete_petunjuk.visibility = if (adapterPosition == 0) View.GONE
                else View.VISIBLE
                btn_delete_petunjuk.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        onClickPetunjuk.onDelete(adapterPosition)
                    }
                }
                btn_add_petunjuk.setOnClickListener {
                    onClickPetunjuk.onAdd()
                }
            }
        }

    }

    interface OnClickPetunjuk {
        fun onAdd()
        fun onDelete(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetunjukHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_petunjuk, parent, false)
        return PetunjukHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: PetunjukHolder, position: Int) {
        holder.bind(list[position])
    }
}