package id.calocallo.sicape.ui.main.addpersonal.pendidikan.lainnya

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ParentListPendOther
import id.calocallo.sicape.model.AddPendidikanModel
import id.calocallo.sicape.network.request.AddPendReq
import id.calocallo.sicape.ui.main.addpersonal.pekerjaan.PekerjaanPersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.rizmaulana.sheenvalidator.lib.SheenValidator
import kotlinx.android.synthetic.main.fragment_pendidikan_lain.*
import kotlinx.android.synthetic.main.fragment_pendidikan_lain.view.*

class PendidikanLainFragment : Fragment() {
    private lateinit var sessionManager1: SessionManager1
    private lateinit var sheenValidator: SheenValidator

    var addPendResp = AddPendReq()
    private lateinit var listOther: ArrayList<AddPendidikanModel>
    private lateinit var adapter: PendOtherAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pendidikan_lain, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sheenValidator = activity?.let { SheenValidator(it) }!!
        sessionManager1 = activity?.let { SessionManager1(it) }!!

        //TODO get All List Pend
        val listUmum = sessionManager1.getPendUmum()
        val listDinas = sessionManager1.getPendDinas()
        listOther = ArrayList()

        addPendResp.riwayat_Add_pendidikan_umum = listUmum
        addPendResp.riwayat_Add_pendidikan_dinas = listDinas
        addPendResp.riwayat_Add_pendidikan_lain_lain = listOther
        setAdapter()

        //buttonNext
        view.btn_next_pend_other.setOnClickListener {
            //validasi list kosong apa tidak
            if (listOther.size == 1 && listOther[0].pendidikan == "") {
                listOther.clear()
            }
            sessionManager1.setPendOther(listOther)
            Log.e("pendOther", "${sessionManager1.getPendOther()}")
            val intent = Intent(activity, PekerjaanPersonelActivity::class.java)
            startActivity(intent)
            activity!!.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            sheenValidator.validate()

        }
    }


    private fun setAdapter() {
        rv_pend_other.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val lainnyaCreated = sessionManager1.getPendOther()
        if (lainnyaCreated.size == 0) {
            listOther.add(AddPendidikanModel())
        }
        adapter = activity?.let {
            PendOtherAdapter(it, listOther, object : PendOtherAdapter.OnCLickOther {
                override fun onDelete(position: Int) {
                    listOther.removeAt(position)
                    adapter.notifyItemRemoved(position)
                }
            })
        }!!
        rv_pend_other.adapter = adapter
        btn_add_pend_other.setOnClickListener {
            val position = if (listOther.isEmpty()) 0 else listOther.size - 1
            listOther.add(AddPendidikanModel())
            adapter.notifyItemChanged(position)
            adapter.notifyItemInserted(position)

        }
    }

    override fun onResume() {
        super.onResume()

        val lainnya = sessionManager1.getPendOther()
        for (i in 0 until lainnya.size) {
            listOther.add(
                i, AddPendidikanModel(
                    lainnya[i].pendidikan,
                    lainnya[i].jenis,
                    lainnya[i].tahun_awal,
                    lainnya[i].tahun_akhir,
                    lainnya[i].kota,
                    lainnya[i].yang_membiayai,
                    lainnya[i].keterangan
                )
            )

        }
    }
}