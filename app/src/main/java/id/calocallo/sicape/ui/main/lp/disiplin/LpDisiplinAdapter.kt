package id.calocallo.sicape.ui.main.lp.disiplin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.LpDisiplinResp
import id.calocallo.sicape.ui.main.lp.LpDisiplinPasalAdapter
import id.calocallo.sicape.utils.ext.visible
import kotlinx.android.synthetic.main.item_lp_kke.view.*

class LpDisiplinAdapter(
    val context: Context,
    val list: List<LpDisiplinResp>,
    val onCLickDisiplin: OnClickDisiplin
): RecyclerView.Adapter<LpDisiplinAdapter.LpDisiplinHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()

    interface OnClickDisiplin {
        fun onClick(position: Int)

    }

    inner class LpDisiplinHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(lpDisiplinResp: LpDisiplinResp) {
            with(itemView){
                itemView.ll_personel_pelapor.visible()

                //personel pelapor
                txt_nama_lp_kke_pelapor.text =
                    "Nama : ${lpDisiplinResp.id_personel_pelapor.toString()}"
                txt_nrp_pangkat_lp_kke_pelapor.text =
                    "Pangkat : ${lpDisiplinResp.id_personel_pelapor.toString()}, NRP : ${lpDisiplinResp.id_personel_pelapor.toString()}"
                txt_jabatan_lp_kke_terlapor.text =
                    "Jabatan : ${lpDisiplinResp.id_personel_pelapor.toString()}"
                txt_kesatuan_lp_kke_terlapor.text =
                    "Kesatuan : ${lpDisiplinResp.id_personel_pelapor.toString()}"

                //personel terlapor
                txt_nama_lp_kke_terlapor.text =
                    "Nama : ${lpDisiplinResp.id_personel_terlapor.toString()}"
                txt_nrp_pangkat_lp_kke_terlapor.text =
                    "Pangkat : ${lpDisiplinResp.id_personel_terlapor.toString()}, NRP : ${lpDisiplinResp.id_personel_terlapor.toString()}"
                txt_jabatan_lp_kke_terlapor.text =
                    "Jabatan : ${lpDisiplinResp.id_personel_terlapor.toString()}"
                txt_kesatuan_lp_kke_terlapor.text =
                    "Kesatuan : ${lpDisiplinResp.id_personel_terlapor.toString()}"

                //set pasal layout and adapter
                itemView.rv_pasal_lp_kke.apply {
                    layoutManager = LinearLayoutManager(
                        itemView.rv_pasal_lp_kke.context,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    adapter = LpDisiplinPasalAdapter(list[adapterPosition])
                    setRecycledViewPool(viewPool)

                }
                itemView.setOnClickListener{
                    onCLickDisiplin.onClick(adapterPosition)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LpDisiplinHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_lp_kke, parent, false)
        return LpDisiplinHolder(view)
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: LpDisiplinHolder, position: Int) {
        holder.bind(list[position])

    }
}