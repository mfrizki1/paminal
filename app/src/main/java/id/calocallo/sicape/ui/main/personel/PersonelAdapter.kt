package id.calocallo.sicape.ui.main.personel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel
import kotlinx.android.synthetic.main.item_personel.view.*

class PersonelAdapter(val context: Context, val listPersonel: ArrayList<PersonelModel>, val listener: PersonelListener): RecyclerView.Adapter<PersonelAdapter.PersonelHolder>() {
    inner class PersonelHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(personelModel: PersonelModel) {
            with(itemView){
                txt_personel_nama.text = personelModel.nama
                txt_personel_jabatan.text = personelModel.jabatan
                txt_personel_kesatuan.text = personelModel.kesatuan
                txt_personel_nrp.text = personelModel.nrp

                itemView.setOnClickListener {
                    listener.onCLick(adapterPosition)
                }
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PersonelAdapter.PersonelHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_personel, parent, false)
        return PersonelHolder(view)
    }

    override fun getItemCount(): Int {
        return listPersonel.size
    }

    override fun onBindViewHolder(holder: PersonelAdapter.PersonelHolder, position: Int) {
        holder.bind(listPersonel[position])
    }

    interface PersonelListener{
        fun onCLick(position: Int)
    }
}