package id.calocallo.sicape.ui.gelar

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.PesertaLhgResp
import kotlinx.android.synthetic.main.item_tanggapan_peserta_gelar.view.*

class AddTanggPesertaAdapter(
    val context: Context,
    val list: MutableList<PesertaLhgResp>,
    var onClick: OnClickTanggPeserta
) : RecyclerView.Adapter<AddTanggPesertaAdapter.Gelar3Holder>() {
    interface OnClickTanggPeserta {
        fun onDelete(position: Int)
    }

    inner class Gelar3Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(pesertaModel: PesertaLhgResp) {
            with(itemView) {
                edt_nama_peserta_gelar_item.editText?.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        pesertaModel.nama_peserta = s.toString()
                    }

                    override fun beforeTextChanged(
                        s: CharSequence?, start: Int,
                        count: Int, after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?, start: Int,
                        before: Int, count: Int
                    ) {
                    }
                })
                edt_pendapat_peserta_gelar_item.editText?.addTextChangedListener(object :
                    TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        pesertaModel.pendapat = s.toString()
                    }

                    override fun beforeTextChanged(
                        s: CharSequence?, start: Int,
                        count: Int, after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?, start: Int,
                        before: Int, count: Int
                    ) {
                    }
                })
                edt_nama_peserta_gelar_item.editText?.setText(pesertaModel.nama_peserta)
                edt_pendapat_peserta_gelar_item.editText?.setText(pesertaModel.pendapat)
                btn_delete_tanggapan_peserta.visibility =
                    if (adapterPosition == 0) View.GONE else View.VISIBLE
                btn_delete_tanggapan_peserta.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION) onClick.onDelete(
                        adapterPosition
                    )
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Gelar3Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tanggapan_peserta_gelar, parent, false)
        return Gelar3Holder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Gelar3Holder, position: Int) {
        holder.bind(list[position])
    }
}