package id.calocallo.sicape.ui.main.skhd

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.SkhdModel
import kotlinx.android.synthetic.main.item_skhd.view.*

class SkhdAdapter(
    val context: Context,
    val list: ArrayList<SkhdModel>,
    val onClickSkhd: OnClickSkhd
) : RecyclerView.Adapter<SkhdAdapter.SkhdHolder>() {
    inner class SkhdHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var adapterHukSkhdAdapter: HukumanSkhdAdapter
        fun bind(skhdModel: SkhdModel) {
            with(itemView) {
                txt_no_skhd.text = skhdModel.no_skhd
                txt_nama_skhd.text = skhdModel.nama_personel
                txt_nrp_pangkat_skhd.text =
                    "Pangkat : ${skhdModel.pangkat_bidang}    NRP : ${skhdModel.nrp_personel}"
                txt_jabatan_skhd.text = skhdModel.jabatan
                txt_kesatuan_skhd.text = skhdModel.kesatuan
                txt_kepala_bidang_skhd.text = skhdModel.kepala_bidang
                txt_nama_bidang_skhd.text = skhdModel.nama_bidang
                txt_pangkat_nrp_bidang.text =
                    "Pangkat : ${skhdModel.pangkat_bidang}    NRP : ${skhdModel.nrp_bidang}"

                rv_hukuman_skhd.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapterHukSkhdAdapter = HukumanSkhdAdapter(context, skhdModel.listHukuman)
                rv_hukuman_skhd.adapter = adapterHukSkhdAdapter

                itemView.setOnClickListener {
                    onClickSkhd.onCLick(adapterPosition)
                }

            }
        }

    }

    interface  OnClickSkhd{
        fun onCLick(position: Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkhdHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_skhd, parent, false)
        return SkhdHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: SkhdHolder, position: Int) {
        holder.bind(list[position])
    }
}