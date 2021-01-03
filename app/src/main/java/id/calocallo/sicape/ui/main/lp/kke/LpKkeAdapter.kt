package id.calocallo.sicape.ui.main.lp.kke

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.LpKkeResp
import id.calocallo.sicape.ui.main.lp.LpKkePasalAdapter
import id.calocallo.sicape.utils.ext.visible
import kotlinx.android.synthetic.main.item_lp_kke.view.*
import kotlinx.android.synthetic.main.item_lp_kke.view.ll_personel_pelapor
import kotlinx.android.synthetic.main.item_lp_kke.view.*

class LpKkeAdapter(
    val context: Context,
    val list: List<LpKkeResp>,
    val onCLickKke: OnCLickKke
) : RecyclerView.Adapter<LpKkeAdapter.LpKkeHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()

    interface OnCLickKke {
        fun onClick(position: Int)
    }

    inner class LpKkeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(lpKkeResp: LpKkeResp) {
            with(itemView) {
                itemView.ll_personel_pelapor.visible()

                //general
                txt_no_lp_kke.text = lpKkeResp.no_lp

                //personel pelapor
                txt_nama_lp_kke_pelapor.text =
                    "Nama : ${lpKkeResp.id_personel_pelapor.toString()}"
                txt_nrp_pangkat_lp_kke_pelapor.text =
                    "Pangkat : ${lpKkeResp.id_personel_pelapor.toString()}, NRP : ${lpKkeResp.id_personel_pelapor.toString()}"
                txt_jabatan_lp_kke_terlapor.text =
                    "Jabatan : ${lpKkeResp.id_personel_pelapor.toString()}"
                txt_kesatuan_lp_kke_terlapor.text =
                    "Kesatuan : ${lpKkeResp.id_personel_pelapor.toString()}"

                //personel terlapor
                txt_nama_lp_kke_terlapor.text =
                    "Nama : ${lpKkeResp.id_personel_terlapor.toString()}"
                txt_nrp_pangkat_lp_kke_terlapor.text =
                    "Pangkat : ${lpKkeResp.id_personel_terlapor.toString()}, NRP : ${lpKkeResp.id_personel_terlapor.toString()}"
                txt_jabatan_lp_kke_terlapor.text =
                    "Jabatan : ${lpKkeResp.id_personel_terlapor.toString()}"
                txt_kesatuan_lp_kke_terlapor.text =
                    "Kesatuan : ${lpKkeResp.id_personel_terlapor.toString()}"

                //set pasal layout and adapter
                itemView.rv_pasal_lp_kke.apply {
                    layoutManager = LinearLayoutManager(
                        itemView.rv_pasal_lp_kke.context,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    adapter = LpKkePasalAdapter(list[adapterPosition])
                    setRecycledViewPool(viewPool)

                }
                itemView.setOnClickListener{
                    onCLickKke.onClick(adapterPosition)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LpKkeHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_lp_kke, parent, false)
        return LpKkeHolder(view)
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: LpKkeHolder, position: Int) {
        holder.bind(list[position])
    }
}