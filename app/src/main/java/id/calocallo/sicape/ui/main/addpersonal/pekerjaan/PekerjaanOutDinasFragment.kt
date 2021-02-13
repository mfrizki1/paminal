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
    private lateinit var parent: ParentListPekerjaanODinas
    private lateinit var adapter: PekerjaanODinasAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pekerjaan_out_dinas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager1 = activity?.let { SessionManager1(it) }!!
        list = ArrayList()
//        parent = ParentListPekerjaanODinas(list)

        initRecycler()
        btn_next_pekerjaan_oDinas.setOnClickListener {
            if (list.size == 0) {
                list.clear()
            }
            sessionManager1.setPekerjaanDiluar(list)
            Log.e("pekerjaanLuar", "${sessionManager1.getPekerjaanDiluar()}")
//            doPekerjaan()
            //berhasil -> GO
            //gagal -> TOAST

//            Log.e("dataODINAS1", parent.parentList[0].nama_pkrjan.toString())
//            Log.e("dataODINAS2", parent.parentList[1].instansi.toString())
//            Log.e("dataODINAS2", parent.parentList[2].nama_pkrjan.toString())
            startActivity(Intent(activity, AddAlamatActivity::class.java))
            activity!!.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun doPekerjaan() {
        addPekerjaanReq.riwayat_pekerjaan = sessionManager1.getPekerjaan()
        addPekerjaanReq.riwayat_pekerjaan_luar_dinas = list
        NetworkConfig().getService().addPekerjaanMany(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            Constants.ID_PERSONEL,
            addPekerjaanReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(activity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    if (response.body()?.message == "Data riwayat pekerjaan saved succesfully") {
                        startActivity(Intent(activity, AddAlamatActivity::class.java))
                    } else {
                        Toast.makeText(activity, "Error Tambah Data Pekerjaan", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(activity, "Error Tambah Data Pekerjaan", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    private fun initRecycler() {
        rv_pekerjaan_ODinas.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val diluar = sessionManager1.getPekerjaanDiluar()
        if (diluar.size == 0) {
            list.add(
                PekerjaanODinasReq(
                    "",
                    "",
                    "",
                    "",
                    "",
                    ""
                )
            )
        }
//        else {
//            list.add(
//                PekerjaanODinasReq("", "", "", "", "", "")
//            )
//        }

        adapter = activity?.let {
            PekerjaanODinasAdapter(it, list, object : PekerjaanODinasAdapter.OnClickODinas {
                override fun onDelete(position: Int) {
                    list.removeAt(position)
                    adapter.notifyDataSetChanged()
                }
            })
        }!!
        rv_pekerjaan_ODinas.adapter = adapter

        btn_add_pekerjaan_oDinas.setOnClickListener {
            val position = if (list.isEmpty()) 0 else list.size - 1
            list.add(
                PekerjaanODinasReq(
                    "",
                    "",
                    "",
                    "",
                    "",
                    ""
                )
            )
            adapter.notifyItemInserted(position)
            adapter.notifyDataSetChanged()
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