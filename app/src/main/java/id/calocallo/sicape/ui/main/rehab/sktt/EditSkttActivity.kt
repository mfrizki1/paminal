package id.calocallo.sicape.ui.main.rehab.sktt

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.SkttReq
import id.calocallo.sicape.network.response.AddSkttResp
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.LpCustomResp
import id.calocallo.sicape.network.response.SkttResp
import id.calocallo.sicape.ui.main.choose.lp.PickJenisLpActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.utils.ext.toast
import kotlinx.android.synthetic.main.activity_edit_sktt.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditSkttActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var idLp: Int? = null
    private var skttReq = SkttReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_sktt)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data SKTT"
        sessionManager1 = SessionManager1(this)

        val getSktt = intent.extras?.getParcelable<SkttResp>(EDIT_SKTT)
        getViewEditSktt(getSktt)

        btn_pick_lp_sktt_edit.setOnClickListener {
            val intent = Intent(this, PickJenisLpActivity::class.java)
            intent.putExtra(AddSkttActivity.LP_SKTT, AddSkttActivity.LP_SKTT)
            startActivityForResult(intent, AddSkttActivity.REQ_LP_ON_SKTT)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        btn_save_sktt_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_sktt_edit)
        btn_save_sktt_edit.setOnClickListener {
            updateSktt(getSktt)
        }
    }

    private fun updateSktt(sktt: SkttResp?) {
        skttReq.id_lp = idLp
        skttReq.no_laporan_hasil_audit_investigasi =
            edt_no_lap_hasil_audit_inves_sktt_edit.text.toString()
        skttReq.kota_penetapan = edt_kota_penetapan_sktt_edit.text.toString()
        skttReq.tanggal_penetapan = edt_tanggal_penetapan_sktt_edit.text.toString()
        skttReq.nama_kabid_propam = edt_nama_pimpinan_sktt_edit.text.toString()
        skttReq.pangkat_kabid_propam = edt_pangkat_pimpinan_sktt_edit.text.toString()
        skttReq.nrp_kabid_propam = edt_nrp_pimpinan_sktt_edit.text.toString()
        skttReq.tembusan = edt_tembusan_sktt_edit.text.toString()
        Log.e("edit SKTT", "$skttReq")

        btn_save_sktt_edit.showProgress {
            progressColor = Color.WHITE
        }
      apiUpdSktt(sktt)
    }

    private fun apiUpdSktt(sktt: SkttResp?) {
        NetworkConfig().getServSktt().updSktt("Bearer ${sessionManager1.fetchAuthToken()}", sktt?.id, skttReq).enqueue(
            object :
                Callback<Base1Resp<AddSkttResp>> {
                override fun onResponse(
                    call: Call<Base1Resp<AddSkttResp>>,
                    response: Response<Base1Resp<AddSkttResp>>
                ) {
                    if (response.body()?.message == "Data sktt updated succesfully") {
                        btn_save_sktt_edit.hideProgress(R.string.data_updated)
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        },750)
                    } else {
                        toast("${response.body()?.message}")
                        btn_save_sktt_edit.hideProgress(R.string.not_update)
                    }
                }

                override fun onFailure(call: Call<Base1Resp<AddSkttResp>>, t: Throwable) {
                    Toast.makeText(this@EditSkttActivity, "$t", Toast.LENGTH_SHORT).show()
                    btn_save_sktt_edit.hideProgress(R.string.not_update)

                }
            })
    }

    private fun getViewEditSktt(sktt: SkttResp?) {
        idLp = sktt?.lp?.id
        edt_no_sktt_edit.setText(sktt?.no_sktt)
        txt_no_lp_sktt_edit.text = sktt?.lp?.no_lp
        edt_no_lap_hasil_audit_inves_sktt_edit.setText(sktt?.no_laporan_hasil_audit_investigasi)
        edt_kota_penetapan_sktt_edit.setText(sktt?.kota_penetapan)
        edt_tanggal_penetapan_sktt_edit.setText(sktt?.tanggal_penetapan)
        edt_nama_pimpinan_sktt_edit.setText(sktt?.nama_kabid_propam)
        edt_pangkat_pimpinan_sktt_edit.setText(sktt?.pangkat_kabid_propam)
        edt_nrp_pimpinan_sktt_edit.setText(sktt?.nrp_kabid_propam)
        edt_tembusan_sktt_edit.setText(sktt?.tembusan)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AddSkttActivity.REQ_LHP_ON_SKTT) {
            /*  when (resultCode) {
                  Activity.RESULT_OK -> {
                      val lhpResp = data?.getParcelableExtra<LhpResp>("CHOOSE_LHP")
                      idLhp = lhpResp?.id
                      txt_no_lhp_sktt_edit.text = lhpResp?.no_lhp
                  }
              }*/
        }
        if (requestCode == AddSkttActivity.REQ_LP_ON_SKTT) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val getLpWithoutSktt =
                        data?.getParcelableExtra<LpCustomResp>(AddSkttActivity.GET_LP_ON_SKTT)
                    idLp = getLpWithoutSktt?.id
                    txt_no_lp_sktt_edit.text = getLpWithoutSktt?.no_lp
                }
            }
        }
    }

    companion object {
        const val EDIT_SKTT = "EDIT_SKTT"
    }
}