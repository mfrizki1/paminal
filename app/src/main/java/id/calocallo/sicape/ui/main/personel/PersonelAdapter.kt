package id.calocallo.sicape.ui.main.personel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AllPersonelModel
import kotlinx.android.synthetic.main.item_personel.view.*

class PersonelAdapter(val context: Context, val listAllPersonel: ArrayList<AllPersonelModel>, val listener: PersonelListener): RecyclerView.Adapter<PersonelAdapter.PersonelHolder>() {
    inner class PersonelHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(allPersonelModel: AllPersonelModel) {
            with(itemView){
                txt_personel_nama.text = allPersonelModel.nama
                txt_personel_jabatan.text = allPersonelModel.jabatan
//                txt_personel_kesatuan.text = personelModel.id_satuan_kerja
                txt_personel_nrp.text = allPersonelModel.nrp

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
        return listAllPersonel.size
    }

    override fun onBindViewHolder(holder: PersonelAdapter.PersonelHolder, position: Int) {
        holder.bind(listAllPersonel[position])
    }

    interface PersonelListener{
        fun onCLick(position: Int)
    }
}