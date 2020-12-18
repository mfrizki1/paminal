package id.calocallo.sicape.ui.main.editpersonel.pekerjaan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.AddSinglePekerjaanReq
import id.calocallo.sicape.network.request.PekerjaanODinasReq
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.toggleVisibility
import id.calocallo.sicape.utils.ext.visible
import kotlinx.android.synthetic.main.fragment_add_single_pekerjaan.*
import kotlinx.android.synthetic.main.fragment_add_single_pekerjaan.view.*
import kotlinx.android.synthetic.main.fragment_add_single_pend.*
import kotlinx.android.synthetic.main.fragment_edit_pekerjaan_luar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddSinglePekerjaanFragment : Fragment() {
    var pekerjaan = ""
    private lateinit var sessionManager: SessionManager
    private val singlePekerjaanReq = AddSinglePekerjaanReq()
    private val pekerjaanLuarReq = PekerjaanODinasReq("","","","","","")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_single_pekerjaan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = activity?.let { SessionManager(it) }!!
        val item = listOf("Pekerjaan", "Pekerjaan Diluar Dinas")
        val adapter = activity?.let { ArrayAdapter(it, R.layout.item_spinner, item) }
        view.sp_pekerjaan_add_single.setAdapter(adapter)
        view.sp_pekerjaan_add_single.setOnItemClickListener { parent, _view, position, id ->
            if (position == 0) {
                pekerjaan = "pekerjaan"
                ll_pekerjaan_luar.gone()
                ll_pekerjaan.visible()

            } else {
                pekerjaan = "pekerjaan_luar_dinas"
                ll_pekerjaan.gone()
                ll_pekerjaan_luar.visible()

            }
        }

        view.btn_save_add_add_single.setOnClickListener {
            doAddPekerjaan(pekerjaan)
        }

    }

    private fun doAddPekerjaan(pekerjaan: String) {
        if (pekerjaan == "pekerjaan") {

            singlePekerjaanReq.keterangan = edt_ket_pekerjaan_add_single.text.toString()
            singlePekerjaanReq.instansi = edt_kesatuan_pekerjaan_add_single.text.toString()
            singlePekerjaanReq.golongan = edt_pangkat_pekerjaan_add_single.text.toString()
            singlePekerjaanReq.pekerjaan = edt_nama_pekerjaan_add_single.text.toString()
            singlePekerjaanReq.berapa_tahun = edt_lama_thn_pekerjaan_add_single.text.toString()

            NetworkConfig().getService().addPekerjaanSingle(
                "Bearer ${sessionManager.fetchAuthToken()}",
                "4",
//            sessionManager.fetchID().toString(),
                singlePekerjaanReq
            ).enqueue(object : Callback<BaseResp> {
                override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                    Toast.makeText(activity, "Error Koneksi", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                    if (response.isSuccessful) {
                        Toast.makeText(activity, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT)
                            .show()
//                    activity?.finish()
                        fragmentManager?.popBackStack()
                    } else {
                        Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                    }

                }
            })

        }else{
            pekerjaanLuarReq.pekerjaan = edt_nama_pekerjaan_luar.text.toString()
            pekerjaanLuarReq.tahun_awal = edt_thn_awal_pekerjaan_luar.text.toString()
            pekerjaanLuarReq.tahun_akhir = edt_thn_akhir_pekerjaan_luar.text.toString()
            pekerjaanLuarReq.instansi = edt_instansi_pekerjaan_luar.text.toString()
            pekerjaanLuarReq.dalam_rangka = edt_rangka_pekerjaan_luar.text.toString()
            pekerjaanLuarReq.keterangan = edt_ket_pekerjaan_luar.text.toString()
            NetworkConfig().getService().addPekerjaanLuar(
                "Bearer ${sessionManager.fetchAuthToken()}",
                "4",
                pekerjaanLuarReq
                //            sessionManager.fetchID().toString(),
            ).enqueue(object: Callback<BaseResp>{
                override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                    Toast.makeText(activity, "Error Koneksi", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                    if (response.isSuccessful) {
                        Toast.makeText(activity, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT)
                            .show()
//                     activity?.finish()
                        fragmentManager?.popBackStack()
                    } else {
                        Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }

    }
}