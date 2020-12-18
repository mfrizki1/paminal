package id.calocallo.sicape.ui.main.editpersonel.pendidikan

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PendidikanModel
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*

class EditPendAdapter(
    val context: Context,
    val list: ArrayList<PendidikanModel>?,
    val onClick: OnClickEditPend
) : RecyclerView.Adapter<EditPendAdapter.EditPendHolder>() {
    inner class EditPendHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(pendidikanModel: PendidikanModel?) {
            itemView.txt_edit_pendidikan.text = pendidikanModel?.pendidikan

            itemView.setOnClickListener {
                onClick.onClick(adapterPosition)
            }
        }

    }

    interface OnClickEditPend {
        fun onClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditPendHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_edit_1_text, parent, false)
        return EditPendHolder(view)
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }

    override fun onBindViewHolder(holder: EditPendHolder, position: Int) {
        holder.bind(list?.get(position))
    }
}