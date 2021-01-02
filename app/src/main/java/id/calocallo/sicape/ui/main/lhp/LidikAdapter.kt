package id.calocallo.sicape.ui.main.lhp

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ListLidik
import kotlinx.android.synthetic.main.item_input_lidik.view.*

class LidikAdapter(
    val context: Context,
    val list: ArrayList<ListLidik>,
    val onClickLidik: OnClickLidik
) : RecyclerView.Adapter<LidikAdapter.LidikHolder>() {
    private var statusPenyelidik: String? = null
    private var status = ""
    private var checkedRadioButton: RadioButton? = null

    inner class LidikHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(listLidik: ListLidik) {
            with(itemView) {
                edt_nama_lidik.setText(listLidik.nama_lidik)
                edt_nrp_lidik.setText(listLidik.nrp_lidik)
                edt_pangkat_lidik.setText(listLidik.pangkat_lidik)
                val item = listOf("Ketua Tim", "Anggota")
                val adapter = ArrayAdapter(context, R.layout.item_spinner, item)
                spinner_status_lidik.setAdapter(adapter)
                spinner_status_lidik.setOnItemClickListener { parent, view, position, id ->
                    when (position) {
                        0 -> status = "ketua_tim"
                        1 -> status = "anggota"
                    }
                }

                listLidik.status_penyelidik = status
                Log.e("status", status)

                /*

rb_ketua_penyelidik.setOnCheckedChangeListener(checkedChangeListener)
if(rb_ketua_penyelidik.isChecked) checkedRadioButton = rb_ketua_penyelidik
rb_anggota_penyelidik.setOnCheckedChangeListener(checkedChangeListener)
if(rb_anggota_penyelidik.isChecked) checkedRadioButton = rb_anggota_penyelidik

Log.e("rb","ANGGOTA ${rb_anggota_penyelidik.text} KETUA ${rb_ketua_penyelidik.text}, ${checkedRadioButton?.text}")
val id: Int = rg_status_penyelidik.checkedRadioButtonId
if (id != -1) {
    val radio: RadioButton = findViewById(id)
    when (radio.text) {
        "Terbukti" -> {
            statusPenyelidik = "ketua_tim"
        }
        "Tidak Terbukti" -> {
            statusPenyelidik = "anggota"
        }
    }
//                    Log.e("isTerbutki", "${isTerbukti}")
}
 */
                edt_nama_lidik.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        listLidik.nama_lidik = s.toString()
                    }

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                    }
                })
                edt_nrp_lidik.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        listLidik.nrp_lidik = s.toString()
                    }

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                    }
                })
                edt_pangkat_lidik.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        listLidik.pangkat_lidik = s.toString()
                    }

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                    }
                })
                btn_delete_lidik.visibility = if (adapterPosition == 0) View.GONE
                else View.VISIBLE

                btn_delete_lidik.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION)
                        onClickLidik.onDelete(adapterPosition)
                }
                btn_add_lidik.setOnClickListener {
                    onClickLidik.onAdd()
                }
            }
        }

//        private val checkedChangeListener =
//            CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
//                checkedRadioButton?.apply { setChecked(!isChecked) }
//                checkedRadioButton = buttonView.apply {
//                    setChecked(isChecked)
//                }
//            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LidikHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_input_lidik, parent, false)
        return LidikHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: LidikHolder, position: Int) {
        holder.bind(list[position])
    }

    interface OnClickLidik {
        fun onAdd()
        fun onDelete(position: Int)
    }
}