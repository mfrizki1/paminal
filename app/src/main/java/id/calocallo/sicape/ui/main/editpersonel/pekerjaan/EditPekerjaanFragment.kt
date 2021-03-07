package id.calocallo.sicape.ui.main.editpersonel.pekerjaan

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PekerjaanModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.AddSinglePekerjaanReq
import id.calocallo.sicape.network.response.AddPekerjaanResp
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import kotlinx.android.synthetic.main.fragment_edit_pekerjaan.*
import kotlinx.android.synthetic.main.fragment_edit_pekerjaan.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditPekerjaanFragment : Fragment() {
    private val singlePekerjaanReq = AddSinglePekerjaanReq()
    private lateinit var sessionManager1: SessionManager1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_pekerjaan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager1 = activity?.let { SessionManager1(it) }!!
        if (sessionManager1.fetchHakAkses() == "operator") {
            view.btn_save_edit_pekerjaan.gone()
            view.btn_delete_edit_pekerjaan.gone()
        }
        val pekerjaan = arguments?.getParcelable<PekerjaanModel>("PEKERJAAN")
        viewDetailPekerjaan(pekerjaan)


        btn_save_edit_pekerjaan.attachTextChangeAnimator()
        bindProgressButton(btn_save_edit_pekerjaan)
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

    private fun viewDetailPekerjaan(pekerjaan: PekerjaanModel?) {
        NetworkConfig().getServPers().getDetailPekerjaan(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            pekerjaan?.id.toString()
        ).enqueue(object :Callback<PekerjaanModel>{
            override fun onFailure(call: Call<PekerjaanModel>, t: Throwable) {
                Toast.makeText(activity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<PekerjaanModel>,
                response: Response<PekerjaanModel>
            ) {
               if(response.isSuccessful){
                   val data= response.body()
                   edt_nama_pekerjaan_edit.setText(data?.pekerjaan)
                   edt_lama_thn_pekerjaan_edit.setText(data?.berapa_tahun.toString())
                   edt_pangkat_pekerjaan_edit.setText(data?.golongan.toString().toUpperCase())
                   edt_kesatuan_pekerjaan_edit.setText(data?.instansi.toString().toUpperCase())
                   edt_ket_pekerjaan_edit.setText(data?.keterangan)
               }else{
                   Toast.makeText(activity, "Error Koneksi", Toast.LENGTH_SHORT).show()
               }
            }
        })

    }

    private fun doUpdatePekerjaan(pekerjaan: PekerjaanModel?) {
        val animatedDrawable =
            activity?.let { ContextCompat.getDrawable(it, R.drawable.animated_check) }!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        singlePekerjaanReq.berapa_tahun = edt_lama_thn_pekerjaan_edit.text.toString()
        singlePekerjaanReq.pekerjaan = edt_nama_pekerjaan_edit.text.toString()
        singlePekerjaanReq.golongan = edt_pangkat_pekerjaan_edit.text.toString()
        singlePekerjaanReq.instansi = edt_kesatuan_pekerjaan_edit.text.toString()
        singlePekerjaanReq.keterangan = edt_ket_pekerjaan_edit.text.toString()

        btn_save_edit_pekerjaan.showProgress {
            progressColor = Color.WHITE
        }

        NetworkConfig().getServPers().updatePekerjaan(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            pekerjaan?.id.toString(),
            singlePekerjaanReq
        ).enqueue(object : Callback<Base1Resp<AddPekerjaanResp>> {
            override fun onFailure(call: Call<Base1Resp<AddPekerjaanResp>>, t: Throwable) {
                Toast.makeText(activity, "Error Koneksi", Toast.LENGTH_SHORT).show()
                btn_save_edit_pekerjaan.hideDrawable(R.string.save)
            }

            override fun onResponse(call: Call<Base1Resp<AddPekerjaanResp>>, response: Response<Base1Resp<AddPekerjaanResp>>) {
                if (response.isSuccessful) {
                    Toast.makeText(activity, R.string.data_updated, Toast.LENGTH_SHORT).show()
                    btn_save_edit_pekerjaan.showDrawable(animatedDrawable) {
                        buttonTextRes = R.string.data_updated
                        textMarginRes = R.dimen.space_10dp
                    }
                    Handler(Looper.getMainLooper()).postDelayed({
                        fragmentManager?.popBackStack()
                    }, 500)
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_edit_pekerjaan.hideDrawable(R.string.save)
                    }, 3000)
                    btn_save_edit_pekerjaan.hideDrawable(R.string.not_update)
                }
            }
        })
    }

    private fun doDeletePekerjaan(pekerjaan: PekerjaanModel?) {
        NetworkConfig().getServPers().deletePekerjaan(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            pekerjaan?.id.toString()
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(activity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    Toast.makeText(activity, "Berhasil Hapus", Toast.LENGTH_SHORT).show()
//                    activity?.finish()
                    fragmentManager?.popBackStack()
                } else {
                    Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}