package id.calocallo.sicape.ui.main.editpersonel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PendUmumModel
import kotlinx.android.synthetic.main.layout_edit_pendidikan.view.*

class EditPendAdapter(
    val context: Context,
    val list: ArrayList<PendUmumModel>,
    val onClick: OnClickEditPend
) : RecyclerView.Adapter<EditPendAdapter.EditPendHolder>() {
    inner class EditPendHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(pendUmumModel: PendUmumModel) {
            itemView.txt_edit_pendidikan.text = pendUmumModel.pendidikan

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
            .inflate(R.layout.layout_edit_pendidikan, parent, false)
        return EditPendHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: EditPendHolder, position: Int) {
        holder.bind(list[position])
    }
}