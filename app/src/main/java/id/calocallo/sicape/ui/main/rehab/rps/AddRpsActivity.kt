package id.calocallo.sicape.ui.main.rehab.rps

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.RpsReq
import id.calocallo.sicape.network.response.AddRpsResp
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.LpMinResp
import id.calocallo.sicape.ui.main.choose.lp.LpChooseActivity
import id.calocallo.sicape.ui.main.choose.lp.PickJenisLpActivity
import id.calocallo.sicape.ui.main.lhp.add.AddLhpActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.action
import id.calocallo.sicape.utils.ext.showSnackbar
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.utils.ext.toast
import kotlinx.android.synthetic.main.activity_add_rpph.*
import kotlinx.android.synthetic.main.activity_add_rps.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddRpsActivity : BaseActivity() {
    private var idLp: Int? = null
    private var rpsReq = RpsReq()
    private lateinit var sessionManager1: SessionManager1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_rps)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data RPS"
        sessionManager1 = SessionManager1(this)
        btn_save_rps_add.attachTextChangeAnimator()
        bindProgressButton(btn_save_rps_add)
        btn_save_rps_add.setOnClickListener {
            addRps()
        }
        btn_pick_lp_rps_add.attachTextChangeAnimator()
        bindProgressButton(btn_pick_lp_rps_add)
        btn_pick_lp_rps_add.setOnClickListener {
            val intent = Intent(this, LpChooseActivity::class.java)
            intent.putExtra(IS_RPS, true)
            startActivityForResult(intent, REQ_LP_RPS)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun addRps() {
        rpsReq.id_lp = idLp
        rpsReq.nama_dinas = edt_nama_dinas_rps_add.text.toString()
        rpsReq.no_nota_dinas = edt_no_nota_dinas_rps_add.text.toString()
        rpsReq.tanggal_nota_dinas = edt_tanggal_nota_dinas_rps_add.text.toString()
        rpsReq.kota_penetapan = edt_kota_penetapan_rps_add.text.toString()
        rpsReq.tanggal_penetapan = edt_tanggal_penetapan_rps_add.text.toString()
        rpsReq.nama_kabid_propam = edt_nama_pimpinan_rps_add.text.toString()
        rpsReq.pangkat_kabid_propam = edt_pangkat_pimpinan_rps_add.text.toString()
        rpsReq.nrp_kabid_propam = edt_nrp_pimpinan_rps_add.text.toString()
        rpsReq.tembusan = edt_tembusan_rps_add.text.toString()
        Log.e("add RPS", "$rpsReq")
        btn_save_rps_add.showProgress {
            progressColor = Color.WHITE
        }
        apiAddRps()
    }

    private fun apiAddRps() {
        NetworkConfig().getServRps().addRps("Bearer ${sessionManager1.fetchAuthToken()}", rpsReq)
            .enqueue(
                object :
                    Callback<Base1Resp<AddRpsResp>> {
                    override fun onResponse(
                        call: Call<Base1Resp<AddRpsResp>>,
                        response: Response<Base1Resp<AddRpsResp>>
                    ) {
                        if (response.body()?.message == "Data rps saved succesfully") {
                            btn_pick_lp_rps_add.hideProgress(R.string.data_saved)
                            btn_pick_lp_rps_add.showSnackbar(R.string.data_saved) {
                                action(R.string.next) {
                                    finish()
                                }
                            }
                        } else {
                            toast("${response.body()?.message}")
                            btn_pick_lp_rps_add.hideProgress(R.string.not_save)
                        }
                    }

                    override fun onFailure(call: Call<Base1Resp<AddRpsResp>>, t: Throwable) {
                        Toast.makeText(this@AddRpsActivity, "$t", Toast.LENGTH_SHORT).show()
                        btn_pick_lp_rps_add.hideProgress(R.string.not_save)
                    }
                })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_LP_RPS) {
            if (resultCode == PickJenisLpActivity.RES_LP_CHOOSE) {
                val dataLp = data?.getParcelableExtra<LpMinResp>(AddLhpActivity.DATA_LP)
//                rpphReq.id_putkke = putKkeModel?.id
                txt_lp_rps_add.text = dataLp?.no_lp
                idLp = dataLp?.id
            }
        }
    }

    companion object {
        const val REQ_LP_RPS = 1
        const val GET_SKHD = "GET_SKHD"
        const val RES_ID_SKHD = 2
        const val IS_RPS = "IS_RPS"
    }


}