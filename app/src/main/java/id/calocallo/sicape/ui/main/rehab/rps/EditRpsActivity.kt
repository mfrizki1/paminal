package id.calocallo.sicape.ui.main.rehab.rps

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.model.SkhdOnRpsModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.RpsReq
import id.calocallo.sicape.network.response.AddRpsResp
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.RpsResp
import id.calocallo.sicape.ui.main.choose.skhd.ChooseSkhdActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.utils.ext.toast
import kotlinx.android.synthetic.main.activity_edit_rps.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditRpsActivity : BaseActivity() {
    private var idSkhd: Int? = null
    private var rpsReq = RpsReq()
    private lateinit var sessionManager1: SessionManager1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_rps)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data RPS"
        sessionManager1 = SessionManager1(this)
        val getDataRps = intent.extras?.getParcelable<RpsResp>(EDIT_RPS)
        getViewDataRps(getDataRps)
        btn_save_rps_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_rps_edit)
        btn_save_rps_edit.setOnClickListener {
            btn_save_rps_edit.showProgress {
                progressColor = Color.WHITE
            }
            updateRps(getDataRps)
        }
        btn_pick_lp_rps_edit.setOnClickListener {
            val intent = Intent(this, ChooseSkhdActivity::class.java)
            startActivityForResult(intent, REQ_ID_SKHD_EDIT_RPS)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_ID_SKHD_EDIT_RPS) {
            if (resultCode == AddRpsActivity.RES_ID_SKHD) {
                val getSkhd = data?.getParcelableExtra<SkhdOnRpsModel>(AddRpsActivity.GET_SKHD)
                txt_lp_rps_edit.text = getSkhd?.no_skhd
                idSkhd = getSkhd?.id
            }
        }
    }

    private fun updateRps(dataRps: RpsResp?) {
        rpsReq.nama_dinas = edt_nama_dinas_rps_edit.text.toString()
        rpsReq.no_nota_dinas = edt_no_nota_dinas_rps_edit.text.toString()
        rpsReq.tanggal_nota_dinas = edt_tanggal_nota_dinas_rps_edit.text.toString()
        rpsReq.kota_penetapan = edt_kota_penetapan_rps_edit.text.toString()
        rpsReq.tanggal_penetapan = edt_tanggal_penetapan_rps_edit.text.toString()
        rpsReq.nama_kabid_propam = edt_nama_pimpinan_rps_edit.text.toString()
        rpsReq.pangkat_kabid_propam = edt_pangkat_pimpinan_rps_edit.text.toString()
        rpsReq.nrp_kabid_propam = edt_nrp_pimpinan_rps_edit.text.toString()
        rpsReq.tembusan = edt_tembusan_rps_edit.text.toString()
        Log.e("edit RPs", "$rpsReq")
        apiEditRps(dataRps)
    }

    private fun apiEditRps(dataRps: RpsResp?) {
        NetworkConfig().getServRps().updRps("Bearer ${sessionManager1.fetchAuthToken()}", dataRps?.id, rpsReq).enqueue(
            object :
                Callback<Base1Resp<AddRpsResp>> {
                override fun onResponse(
                    call: Call<Base1Resp<AddRpsResp>>,
                    response: Response<Base1Resp<AddRpsResp>>
                ) {
                    if (response.body()?.message == "Data rps updated succesfully"){
                        btn_save_rps_edit.hideProgress(R.string.data_updated)
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        },750)
                    }else{
                        toast("${response.body()?.message}")
                        btn_save_rps_edit.hideProgress(R.string.not_update)
                    }
                }

                override fun onFailure(call: Call<Base1Resp<AddRpsResp>>, t: Throwable) {
                    btn_save_rps_edit.hideProgress(R.string.not_update)
                    Toast.makeText(this@EditRpsActivity, "$t", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun getViewDataRps(dataRps: RpsResp?) {
        edt_no_rps_edit.setText(dataRps?.no_rps)
        txt_lp_rps_edit.text = dataRps?.lp?.no_lp
        edt_no_rps_edit.setText(dataRps?.no_rps)
        edt_nama_dinas_rps_edit.setText(dataRps?.nama_dinas)
        edt_no_nota_dinas_rps_edit.setText(dataRps?.no_nota_dinas)
        edt_tanggal_nota_dinas_rps_edit.setText(dataRps?.tanggal_nota_dinas)
        edt_kota_penetapan_rps_edit.setText(dataRps?.kota_penetapan)
        edt_tanggal_penetapan_rps_edit.setText(dataRps?.tanggal_penetapan)
        edt_nama_pimpinan_rps_edit.setText(dataRps?.nama_kabid_propam)
        edt_pangkat_pimpinan_rps_edit.setText(
            dataRps?.pangkat_kabid_propam.toString().toUpperCase()
        )
        edt_nrp_pimpinan_rps_edit.setText(dataRps?.nrp_kabid_propam)
        edt_tembusan_rps_edit.setText(dataRps?.tembusan)
    }

    companion object {
        const val EDIT_RPS = "EDIT_RPS"
        const val REQ_ID_SKHD_EDIT_RPS = 2
    }
}