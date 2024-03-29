package id.calocallo.sicape.ui.main.editpersonel.pekerjaan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PekerjaanModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.AddSinglePekerjaanReq
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import kotlinx.android.synthetic.main.fragment_edit_pekerjaan.*
import kotlinx.android.synthetic.main.fragment_edit_pekerjaan.view.*
import kotlinx.android.synthetic.main.fragment_edit_pend.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditPekerjaanFragment : Fragment() {
    private val singlePekerjaanReq = AddSinglePekerjaanReq()
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_pekerjaan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = activity?.let { SessionManager(it) }!!
        if (sessionManager.fetchHakAkses() == "operator") {
            view.btn_save_edit_pekerjaan.gone()
            view.btn_delete_edit_pekerjaan.gone()
        }
        val pekerjaan = arguments?.getParcelable<PekerjaanModel>("PEKERJAAN")
        edt_nama_pekerjaan_edit.setText(pekerjaan?.pekerjaan)
        edt_lama_thn_pekerjaan_edit.setText(pekerjaan?.berapa_tahun.toString())
        edt_pangkat_pekerjaan_edit.setText(pekerjaan?.golongan)
        edt_kesatuan_pekerjaan_edit.setText(pekerjaan?.instansi)
        edt_ket_pekerjaan_edit.setText(pekerjaan?.keterangan)

        btn_save_edit_pekerjaan.setOnClickListener {
            if (pekerjaan != null) {
                doUpdatePekerjaan(pekerjaan)
            }
        }

        btn_delete_edit_pekerjaan.setOnClickListener {
            this.activity!!.alert("Hapus Data", "Yakin Hapus?") {
                positiveButton("Iya") {
                    doDeletePekerjaan(pekerjaan)
                }
                negativeButton("Tidak") {
                }
            }.show()
        }
    }

    private fun doUpdatePekerjaan(pekerjaan: PekerjaanModel?) {
        singlePekerjaanReq.berapa_tahun = edt_lama_thn_pekerjaan_edit.text.toString()
        singlePekerjaanReq.pekerjaan = edt_nama_pekerjaan_edit.text.toString()
        singlePekerjaanReq.golongan = edt_pangkat_pekerjaan_edit.text.toString()
        singlePekerjaanReq.instansi = edt_kesatuan_pekerjaan_edit.text.toString()
        singlePekerjaanReq.keterangan = edt_ket_pekerjaan_edit.text.toString()

        NetworkConfig().getService().updatePekerjaan(
            "Bearer ${sessionManager.fetchAuthToken()}",
            pekerjaan?.id.toString(),
            singlePekerjaanReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(activity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if(response.isSuccessful){
                    Toast.makeText(activity, "Data Berhasil Di Update", Toast.LENGTH_SHORT).show()
                    activity?.finish()
                }else{
                    Toast.makeText(activity, "Error Update", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun doDeletePekerjaan(pekerjaan: PekerjaanModel?) {
        NetworkConfig().getService().deletePekerjaan(
            "Bearer ${sessionManager.fetchAuthToken()}",
            pekerjaan?.id.toString()
        ).enqueue(object :Callback<BaseResp>{
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(activity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if(response.isSuccessful){
                    Toast.makeText(activity, "Berhasil Hapus", Toast.LENGTH_SHORT).show()
//                    activity?.finish()
                    fragmentManager?.popBackStack()
                }else{
                    Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}