package id.calocallo.sicape.ui.main.addpersonal.pekerjaan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ParentListPekerjaanODinas
import id.calocallo.sicape.network.request.PekerjaanODinasReq
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.AddPekerjaanReq
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.ui.main.addpersonal.alamat.AddAlamatActivity
import id.calocallo.sicape.utils.Constants
import id.calocallo.sicape.utils.SessionManager1
import kotlinx.android.synthetic.main.fragment_pekerjaan_out_dinas.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PekerjaanOutDinasFragment : Fragment() {
    private var addPekerjaanReq = AddPekerjaanReq()
    private lateinit var sessionManager1: SessionManager1
    private lateinit var list: ArrayList<PekerjaanODinasReq>
    private lateinit var adapter: PekerjaanODinasAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pekerjaan_out_dinas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager1 = activity?.let { SessionManager1(it) }!!
        list = ArrayList()

        initRecycler()
        btn_next_pekerjaan_oDinas.setOnClickListener {
            if (list.size == 0) {
                list.clear()
            }
            sessionManager1.setPekerjaanDiluar(list)
            Log.e("pekerjaanLuar", "${sessionManager1.getPekerjaanDiluar()}")
            startActivity(Intent(activity, AddAlamatActivity::class.java))
            activity!!.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun initRecycler() {
        rv_pekerjaan_ODinas.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val diluar = sessionManager1.getPekerjaanDiluar()
        if (diluar.size == 0) {
            list.add(PekerjaanODinasReq())
        }

        adapter = activity?.let {
            PekerjaanODinasAdapter(it, list, object : PekerjaanODinasAdapter.OnClickODinas {
                override fun onDelete(position: Int) {
                    list.removeAt(position)
                    adapter.notifyItemRemoved(position)

                }
            })
        }!!
        rv_pekerjaan_ODinas.adapter = adapter

        btn_add_pekerjaan_oDinas.setOnClickListener {
            val position = if (list.isEmpty()) 0 else list.size - 1
            list.add(PekerjaanODinasReq())
            adapter.notifyItemChanged(position)
            adapter.notifyItemInserted(position)
        }
    }

    override fun onResume() {
        super.onResume()
        val pkrjn_luar_dinas = sessionManager1.getPekerjaanDiluar()
        for (i in 0 until pkrjn_luar_dinas.size) {
            list.add(
                i, PekerjaanODinasReq(
                    pkrjn_luar_dinas[i].pekerjaan,
                    pkrjn_luar_dinas[i].instansi,
                    pkrjn_luar_dinas[i].tahun_awal,
                    pkrjn_luar_dinas[i].tahun_akhir,
                    pkrjn_luar_dinas[i].dalam_rangka,
                    pkrjn_luar_dinas[i].keterangan
                )
            )
        }
    }
}