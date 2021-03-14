package id.calocallo.sicape.ui.main.lapbul

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.CatpersLapbulResp
import id.calocallo.sicape.utils.ext.formatterTanggal
import kotlinx.android.synthetic.main.item_1_text.view.*

class SuratRehabAdapter(
    val listSuratRehab: CatpersLapbulResp
) : RecyclerView.Adapter<SuratRehabAdapter.SuratRehabHolder>() {
    inner class SuratRehabHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtItem1: TextView = itemView.txt_item_detail_lhp

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuratRehabHolder {
        return SuratRehabHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_1_text, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listSuratRehab.surat_rehab!!.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SuratRehabHolder, position: Int) {
        val jenisLap = listSuratRehab.surat_rehab?.get(position)?.surat
        holder.txtItem1.text =
            "No ${jenisLap}: ${listSuratRehab.surat_rehab?.get(position)?.no_surat}\n" +
                    "Tanggal: ${
                        formatterTanggal(listSuratRehab.tanggal_surat_rehab)
                    }"

    }
}