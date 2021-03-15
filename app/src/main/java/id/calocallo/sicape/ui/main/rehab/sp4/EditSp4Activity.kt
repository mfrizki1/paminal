package id.calocallo.sicape.ui.main.rehab.sp4

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.github.razir.progressbutton.hideProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.Sp4Req
import id.calocallo.sicape.network.response.AddSp4Resp
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.Sp4Resp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_sp4.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class EditSp4Activity : BaseActivity() {
    companion object {
        const val EDT_SP4 = "EDT_SP3"
        const val REQ_SKTB_ON_SP3_EDIT = 1
        const val REQ_SKTT_ON_SP3_EDIT = 2
    }

    private lateinit var sessionManager1: SessionManager1
    private var idLp: Int? = null
    private var idSktt: Int? = null
    private var sp4Req = Sp4Req()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_sp4)
        sessionManager1 = SessionManager1(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data SP3"
        val getDetailSp3 = intent.extras?.getParcelable<Sp4Resp>(EDT_SP4)
        getViewSp3Edit(getDetailSp3)

        btn_save_sp4_edit.setOnClickListener {
            updateSp3(getDetailSp3)
        }
    }

    private fun updateSp3(detailSp3: Sp4Resp?) {
        sp4Req.id_lp = idLp
        sp4Req.no_surat_perintah_kapolri = edt_surat_perinth_kapolri_sp4_edit.text.toString()
        sp4Req.tanggal_surat_perintah_kapolri = edt_tgl_surat_perinth_kapolri_sp4_edit.text.toString()
        sp4Req.no_hasil_audit = edt_no_hasil_audit_sp4_edit.text.toString()
        sp4Req.tanggal_hasil_audit = edt_tgl_hasil_audit_sp4_edit.text.toString()
        sp4Req.tanggal_gelar_audit = edt_tgl_gelar_audit_sp4_edit.text.toString()
        sp4Req.kota_penetapan = edt_kota_penetapan_sp4_edit.text.toString()
        sp4Req.tanggal_penetapan = edt_tanggal_penetapan_sp4_edit.text.toString()
        sp4Req.nama_akreditor = edt_nama_akreditor_sp4_edit.text.toString()
        sp4Req.pangkat_akreditor = edt_pangkat_akreditor_sp4_edit.text.toString()
            .toUpperCase(Locale.ROOT)
        sp4Req.nrp_akreditor = edt_nrp_akreditor_sp4_edit.text.toString()
        Log.e("edti SP3", "$sp4Req")
        apiUpdSp4(detailSp3)
    }

    private fun apiUpdSp4(detailSp3: Sp4Resp?) {
        NetworkConfig().getServSp4()
            .updSp4("Bearer ${sessionManager1.fetchAuthToken()}", detailSp3?.id, sp4Req)
            .enqueue(object : Callback<Base1Resp<AddSp4Resp>> {
                override fun onResponse(
                    call: Call<Base1Resp<AddSp4Resp>>,
                    response: Response<Base1Resp<AddSp4Resp>>
                ) {
                    if (response.body()?.message == "Data sp4 updated succesfully") {
                        btn_save_sp4_edit.hideProgress(R.string.data_updated)
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        }, 750)
                    } else {
                        btn_save_sp4_edit.hideProgress(R.string.not_update)

                    }
                }

                override fun onFailure(call: Call<Base1Resp<AddSp4Resp>>, t: Throwable) {
                    btn_save_sp4_edit.hideProgress(R.string.not_update)
                    Toast.makeText(this@EditSp4Activity, "$t", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun getViewSp3Edit(detailSp3: Sp4Resp?) {
        edt_no_sp4_edit.setText(detailSp3?.no_sp4)
        txt_lp_sp4_edit.text = detailSp3?.lp?.no_lp
        edt_surat_perinth_kapolri_sp4_edit.setText(detailSp3?.no_surat_perintah_kapolri)
        edt_tgl_surat_perinth_kapolri_sp4_edit.setText(detailSp3?.tanggal_surat_perintah_kapolri)
        edt_no_hasil_audit_sp4_edit.setText(detailSp3?.no_hasil_audit)
        edt_tgl_hasil_audit_sp4_edit.setText(detailSp3?.tanggal_hasil_audit)
        edt_tgl_gelar_audit_sp4_edit.setText(detailSp3?.tanggal_gelar_audit)
        edt_kota_penetapan_sp4_edit.setText(detailSp3?.kota_penetapan)
        edt_tanggal_penetapan_sp4_edit.setText(detailSp3?.tanggal_penetapan)
        edt_nama_akreditor_sp4_edit.setText(detailSp3?.nama_akreditor)
        edt_pangkat_akreditor_sp4_edit.setText(
            detailSp3?.pangkat_akreditor.toString()
                .toUpperCase(Locale.ROOT)
        )
        edt_nrp_akreditor_sp4_edit.setText(detailSp3?.nrp_akreditor)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        /*  if (resultCode == Activity.RESULT_OK) {
              val sktb = data?.getParcelableExtra<SktbOnSp3>(ChooseSktbSkttActivity.GET_SKTB)
//                Log.e("skhd", "$sktb")
              if (sktb == null) {
                  txt_sktb_sp4_edit.text = "No SKTB"
              } else {
                  txt_sktb_sp4_edit.text = sktb.no_sktb
                  idSktb = sktb.id
                  idSktt = null
                  txt_sktt_sp4_edit.text = "No SKTT"
              }
          }*/

        /* if (resultCode == Activity.RESULT_OK) {
             val sktt =
                 data?.getParcelableExtra<SkttOnSp3>(ChooseSktbSkttActivity.GET_SKTT)
             Log.e("putKKe", "$sktt")
             if (sktt == null) {
                 txt_sktt_sp4_edit.text = "No SKTT"
             } else {
                 idSktt = sktt.id
                 txt_sktt_sp4_edit.text = sktt.no_sktt
                 idSktb = null
                 txt_sktb_sp4_edit.text = "No SKTB"
             }
         }*/
    }


}
