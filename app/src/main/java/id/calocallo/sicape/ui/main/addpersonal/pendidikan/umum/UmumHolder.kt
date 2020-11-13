package id.calocallo.sicape.ui.main.addpersonal.pendidikan.umum

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PendUmumModel
import kotlinx.android.synthetic.main.layout_pendidikan_umum.view.*

class UmumAdapter(
    val context: Context,
    val list: ArrayList<PendUmumModel>,
    var onCLick: OnClick
) : RecyclerView.Adapter<UmumAdapter.UmumHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UmumHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_pendidikan_umum, parent, false)
        var umumViews = UmumHolder(view)
        umumViews.setListener()
        return umumViews
    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class UmumHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val etName = itemView.edt_nama_umum_custom
        val etKet = itemView.edt_ket_umum_custom
        val etThnAwal = itemView.edt_thn_awal_umum_custom
        val etThnAkhir = itemView.edt_thn_akhir_umum_custom
        val etTmpt = itemView.edt_tempat_umum_custom
        val etOrgMmbiayai = itemView.edt_membiayai_umum_custom
        val btnDelete = itemView.btn_delete_umum_custom

        var name = ""
        var thnAwal = ""
        var thnAkhir = ""
        var tmpt = ""
        var org = ""
        var ket = ""
        fun setListener() {

//            val tempList = ArrayList<PendUmumModel>()
//
//            val pendUmumModel = PendUmumModel()
//            pendUmumModel.nama_pend = name
//            pendUmumModel.thn_awal_pend = thnAwal
//            pendUmumModel.thn_akhir_pend = thnAkhir
//            pendUmumModel.tmpt_pend = tmpt
//            pendUmumModel.org_membiayai = org
//            pendUmumModel.ket_pend = ket
//            tempList.add(pendUmumModel)
//
//            val parentListPendUmum = ParentListPendUmum(tempList)
//            parentListPendUmum.pendUmumList = tempList

//            Log.e("parent", parentListPendUmum.pendUmumList[0].nama_pend)
//            Log.e("parent", parentListPendUmum.pendUmumList[1].nama_pend)
//            Log.e("parent", parentListPendUmum.pendUmumList[2].nama_pend)



            etName.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
//                    Log.e("nameAfter", s.toString())
                    list[adapterPosition].nama_pend = s.toString()
                    name = list[adapterPosition].nama_pend
                    Log.e("name", name)


                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                    onCLick.onName(list[adapterPosition], s.toString(), adapterPosition)
                }
            })

            etThnAwal.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
//                    Log.e("thnAwalAfter", s.toString())
                    list[adapterPosition].thn_awal_pend = s.toString()
                    thnAwal = list[adapterPosition].thn_awal_pend
                    Log.e("awal", thnAwal)

                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                    Log.e("thnAwal", "thnAwal")
//                    onCLick.onThnAwal(list[adapterPosition], s.toString(), adapterPosition)
                }
            })

            etThnAkhir.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
//                    Log.e("thnAkhir", s.toString())
                    list[adapterPosition].thn_akhir_pend = s.toString()
                    thnAkhir = list[adapterPosition].thn_akhir_pend
                    Log.e("akhir", thnAkhir)


                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                    Log.e("thnAkhir", "thnAkhir")
//                    onCLick.onThnAkhir(list[adapterPosition], s.toString(), adapterPosition)
                }
            })

            etTmpt.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
//                    Log.e("tmpt", s.toString())
                    list[adapterPosition].tmpt_pend = s.toString()
                    tmpt = list[adapterPosition].tmpt_pend
                    Log.e("tmp", tmpt)


                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

            etOrgMmbiayai.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
//                    Log.e("org", s.toString())
                    list[adapterPosition].org_membiayai = s.toString()
                    org = list[adapterPosition].org_membiayai
                    Log.e("org", org)


                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

            etKet.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    Log.e("ket", s.toString())
                    list[adapterPosition].ket_pend = s.toString()
                    ket = list[adapterPosition].ket_pend.toString()
                    Log.e("ket", ket)

                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }
            })

            btnDelete.visibility = if (adapterPosition == 0) View.GONE
            else View.VISIBLE

            btnDelete.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onCLick.onDelete(adapterPosition)
                }
            }
        }

    }

    override fun onBindViewHolder(holder: UmumHolder, position: Int) {
        bindViews(holder, position)
    }

    private fun bindViews(holder: UmumHolder, position: Int) {
        val data = list[position]
        holder.etName.setText(data.nama_pend)
        holder.etThnAwal.setText(data.thn_awal_pend)
        holder.etTmpt.setText(data.tmpt_pend)
        holder.etOrgMmbiayai.setText(data.org_membiayai)
        holder.etThnAkhir.setText(data.thn_akhir_pend)
        holder.etKet.setText(data.ket_pend)
//        onCLick.saveData(list)

//        Log.e("holder_name", holder.etName.text.toString())
//        Log.e("holder_thnawal", holder.etThnAwal.text.toString())
//        Log.e("holder_thnAkhir", holder.etThnAkhir.text.toString())
//        Log.e("holder_tmpt", holder.etTmpt.text.toString())
//        Log.e("holder_org", holder.etOrgMmbiayai.text.toString())
//        Log.e("holder_ket", holder.etKet.text.toString())
    }

    interface OnClick {
//        fun saveData(allList: ArrayList<PendUmumModel>)

        //        fun onName(item: PendUmumModel, name: String, index: Int)
//        fun onThnAwal(item: PendUmumModel, thnAwal: String, position: Int)
//        fun onThnAkhir(item: PendUmumModel, thnAkhir: String, position: Int)
//        fun onKet(item: PendUmumModel, ket: String, position: Int)
//        fun onOrg(item: PendUmumModel, org: String, position: Int)
        fun onDelete(position: Int)
    }


}
