package id.calocallo.sicape.ui.main.editpersonel.pekerjaan

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.AddSinglePekerjaanReq
import id.calocallo.sicape.network.request.PekerjaanODinasReq
import id.calocallo.sicape.network.response.AddPekerjaanResp
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import kotlinx.android.synthetic.main.fragment_add_single_pekerjaan.*
import kotlinx.android.synthetic.main.fragment_add_single_pekerjaan.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddSinglePekerjaanFragment : Fragment() {
    var pekerjaan = ""
    private lateinit var sessionManager1: SessionManager1
    private val singlePekerjaanReq = AddSinglePekerjaanReq()
    private val pekerjaanLuarReq = PekerjaanODinasReq("", "", "", "", "", "")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_single_pekerjaan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager1 = activity?.let { SessionManager1(it) }!!
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
        view.btn_save_add_add_single.attachTextChangeAnimator()
        bindProgressButton(view.btn_save_add_add_single)
        view.btn_save_add_add_single.setOnClickListener {
            doAddPekerjaan(pekerjaan)
        }

    }

    private fun doAddPekerjaan(pekerjaan: String) {
        val animatedDrawable =
            activity?.let { ContextCompat.getDrawable(it, R.drawable.animated_check) }!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        if (pekerjaan == "pekerjaan") {
            edt_nama_pekerjaan_luar.text = null
            edt_thn_awal_pekerjaan_luar.text = null
            edt_thn_akhir_pekerjaan_luar.text = null
            edt_instansi_pekerjaan_luar.text = null
            edt_rangka_pekerjaan_luar.text = null
            edt_ket_pekerjaan_luar.text = null
            singlePekerjaanReq.keterangan = edt_ket_pekerjaan_add_single.text.toString()
            singlePekerjaanReq.instansi = edt_kesatuan_pekerjaan_add_single.text.toString().toUpperCase()
            singlePekerjaanReq.golongan = edt_pangkat_pekerjaan_add_single.text.toString().toUpperCase()
            singlePekerjaanReq.pekerjaan = edt_nama_pekerjaan_add_single.text.toString()
            singlePekerjaanReq.berapa_tahun = edt_lama_thn_pekerjaan_add_single.text.toString()

            view?.btn_save_add_add_single?.showProgress {
                progressColor = Color.WHITE
            }

            NetworkConfig().getService().addPekerjaanSingle(
                "Bearer ${sessionManager1.fetchAuthToken()}",
//                "4",
                sessionManager1.fetchID().toString(),
                singlePekerjaanReq
            ).enqueue(object : Callback<Base1Resp<AddPekerjaanResp>> {
                override fun onFailure(call: Call<Base1Resp<AddPekerjaanResp>>, t: Throwable) {
                    Toast.makeText(activity, "Error Koneksi", Toast.LENGTH_SHORT).show()
                    btn_save_add_add_single.hideDrawable(R.string.save)
                }

                override fun onResponse(call: Call<Base1Resp<AddPekerjaanResp>>, response: Response<Base1Resp<AddPekerjaanResp>>) {
                    if (response.isSuccessful) {
                        Toast.makeText(activity, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT)
                            .show()
                        view?.btn_save_add_add_single?.showDrawable(animatedDrawable) {
                            buttonTextRes = R.string.data_saved
                            textMarginRes = R.dimen.space_10dp
                        }
//                    activity?.finish()
                        Handler(Looper.getMainLooper()).postDelayed({
                            fragmentManager?.popBackStack()
                        }, 500)
                    } else {
                        Handler(Looper.getMainLooper()).postDelayed({
                            btn_save_add_add_single.hideDrawable(R.string.save)
                        },3000)
                        btn_save_add_add_single.hideDrawable(R.string.not_save)

                    }

                }
            })

        } else {
            edt_ket_pekerjaan_add_single.text=  null
            edt_kesatuan_pekerjaan_add_single.text=  null
            edt_pangkat_pekerjaan_add_single.text=  null
            edt_nama_pekerjaan_add_single.text=  null
            edt_lama_thn_pekerjaan_add_single.text=  null
            pekerjaanLuarReq.pekerjaan = edt_nama_pekerjaan_luar.text.toString()
            pekerjaanLuarReq.tahun_awal = edt_thn_awal_pekerjaan_luar.text.toString()
            pekerjaanLuarReq.tahun_akhir = edt_thn_akhir_pekerjaan_luar.text.toString()
            pekerjaanLuarReq.instansi = edt_instansi_pekerjaan_luar.text.toString()
            pekerjaanLuarReq.dalam_rangka = edt_rangka_pekerjaan_luar.text.toString()
            pekerjaanLuarReq.keterangan = edt_ket_pekerjaan_luar.text.toString()

            btn_save_add_add_single.showProgress {
                progressColor = Color.WHITE
            }

            NetworkConfig().getService().addPekerjaanLuar(
                "Bearer ${sessionManager1.fetchAuthToken()}",
//                "4",
                sessionManager1.fetchID().toString(),
                pekerjaanLuarReq
            ).enqueue(object : Callback<BaseResp> {
                override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                    Toast.makeText(activity, "Error Koneksi", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                    if (response.isSuccessful) {
                        btn_save_add_add_single.showDrawable(animatedDrawable) {
                            buttonTextRes = R.string.data_saved
                            textMarginRes = R.dimen.space_10dp
                        }
//                        Toast.makeText(activity, R.string.data_saved, Toast.LENGTH_SHORT)
//                            .show()
//                     activity?.finish()
                        Handler(Looper.getMainLooper()).postDelayed({
                            fragmentManager?.popBackStack()
                        },500)
                    } else {
                        Handler(Looper.getMainLooper()).postDelayed({
                            btn_save_add_add_single.hideDrawable(R.string.save)
                        },3000)
                        btn_save_add_add_single.hideDrawable(R.string.not_save)
                    }
                }
            })
        }

    }
}