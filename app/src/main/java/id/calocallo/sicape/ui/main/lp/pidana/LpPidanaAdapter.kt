package id.calocallo.sicape.ui.main.lp.pidana

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.LpPidanaResp
import id.calocallo.sicape.ui.main.lp.LpPidanaPasalAdapter
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import kotlinx.android.synthetic.main.item_lp_pidana.view.*

class LpPidanaAdapter(
    val context: Context,
    val list: List<LpPidanaResp>,
    val onClickLpPidana: OnClickLpPidana
) : RecyclerView.Adapter<LpPidanaAdapter.LpPidanaHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()
    interface OnClickLpPidana {
        fun onClick(position: Int)

    }

    inner class LpPidanaHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(lpPidanaResp: LpPidanaResp) {
            with(itemView) {
                if (lpPidanaResp.status_pelapor == "sipil") {
                    itemView.ll_sipil_pidana.visible()
                    itemView.ll_personel_pelapor.gone()
                } else if(lpPidanaResp.status_pelapor == "polisi") {
                    itemView.ll_sipil_pidana.gone()
                    itemView.ll_personel_pelapor.visible()
                }
                //general
                txt_no_lp_pidana.text = lpPidanaResp.no_lp

                //sipil
                txt_agama_lp_sipil_pidana.text = lpPidanaResp.agama_pelapor
                txt_alamat_lp_sipil_pidana.text = lpPidanaResp.alamat_pelapor
                txt_kwg_lp_sipil_pidana.text = lpPidanaResp.kewarganegaraan_pelapor
                txt_nama_lp_sipil_pidana.text = lpPidanaResp.nama_pelapor
                txt_nik_lp_sipil_pidana.text = lpPidanaResp.nik_ktp_pelapor
                txt_no_telp_lp_sipil_pidana.text = lpPidanaResp.no_telp_pelapor
                txt_pekerjaan_lp_sipil_pidana.text = lpPidanaResp.pekerjaan_pelapor

                //personel pelapor
                txt_nama_lp_pidana_pelapor.text =
                    "Nama : ${lpPidanaResp.id_personel_pelapor.toString()}"
                txt_nrp_pangkat_lp_pidana_pelapor.text =
                    "Pangkat : ${lpPidanaResp.id_personel_pelapor.toString()}, NRP : ${lpPidanaResp.id_personel_pelapor.toString()}"
                txt_jabatan_lp_pidana_terlapor.text =
                    "Jabatan : ${lpPidanaResp.id_personel_pelapor.toString()}"
                txt_kesatuan_lp_pidana_terlapor.text =
                    "Kesatuan : ${lpPidanaResp.id_personel_pelapor.toString()}"

                //personel terlapor
                txt_nama_lp_pidana_terlapor.text =
                    "Nama : ${lpPidanaResp.id_personel_terlapor.toString()}"
                txt_nrp_pangkat_lp_pidana_terlapor.text =
                    "Pangkat : ${lpPidanaResp.id_personel_terlapor.toString()}, NRP : ${lpPidanaResp.id_personel_terlapor.toString()}"
                txt_jabatan_lp_pidana_terlapor.text =
                    "Jabatan : ${lpPidanaResp.id_personel_terlapor.toString()}"
                txt_kesatuan_lp_pidana_terlapor.text =
                    "Kesatuan : ${lpPidanaResp.id_personel_terlapor.toString()}"

                //set pasal Layout and adapter
                itemView.rv_pasal_lp_pidana.apply {
                    layoutManager = LinearLayoutManager(
                        itemView.rv_pasal_lp_pidana.context,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    adapter = LpPidanaPasalAdapter(list[adapterPosition])
                    setRecycledViewPool(viewPool)

                    itemView.setOnClickListener{
                        onClickLpPidana.onClick(adapterPosition)
                    }
                }

            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LpPidanaAdapter.LpPidanaHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_lp_pidana, parent, false)
        return LpPidanaHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: LpPidanaAdapter.LpPidanaHolder, position: Int) {
        holder.bind(list[position])
    }

}