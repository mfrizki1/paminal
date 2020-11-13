package id.calocallo.sicape.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.calocallo.sicape.R
import id.calocallo.sicape.model.FiturModel
import kotlinx.android.synthetic.main.item_fitur.view.*

class FiturAdapter(
    val context: Context,
    val listFitur: ArrayList<FiturModel>,
    val listener: FiturListener
) : RecyclerView.Adapter<FiturAdapter.FiturHolder>() {
    inner class FiturHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(fiturModel: FiturModel) {
            with(itemView) {
                Glide.with(this)
                    .load(fiturModel.imgFitur)
                    .into(img_fitur)
                txt_fitur.text = fiturModel.nameFitur

                itemView.setOnClickListener {
                    listener.onClick(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FiturAdapter.FiturHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fitur, parent, false)
        return FiturHolder(view)
    }

    override fun getItemCount(): Int {
        return listFitur.size
    }

    override fun onBindViewHolder(holder: FiturAdapter.FiturHolder, position: Int) {
        holder.bind(listFitur[position])
    }

    interface FiturListener {
        fun onClick(position: Int)
    }
}