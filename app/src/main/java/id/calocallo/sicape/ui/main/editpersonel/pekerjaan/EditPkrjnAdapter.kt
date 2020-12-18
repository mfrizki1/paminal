package id.calocallo.sicape.ui.main.editpersonel.pekerjaan

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PekerjaanModel
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*

class EditPkrjnAdapter(
    val context: Context,
    val list: ArrayList<PekerjaanModel>?,
    val onClick: OnClickEditPekerjaan
) : RecyclerView.Adapter<EditPkrjnAdapter.PekerjaanHolder>() {
    inner class PekerjaanHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(pekerjaanModel: PekerjaanModel?) {
            itemView.txt_edit_pendidikan.text = pekerjaanModel?.pekerjaan
            itemView.setOnClickListener {
                onClick.onClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PekerjaanHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_edit_1_text, parent, false)
        return PekerjaanHolder(view)
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }

    override fun onBindViewHolder(holder: PekerjaanHolder, position: Int) {
        holder.bind(list?.get(position))
    }

    interface OnClickEditPekerjaan {
        fun onClick(position: Int)
    }
}