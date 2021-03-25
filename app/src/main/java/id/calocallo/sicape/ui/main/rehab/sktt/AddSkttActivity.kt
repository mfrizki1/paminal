package id.calocallo.sicape.ui.main.rehab.sktt

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LpOnSkhd
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.SkttReq
import id.calocallo.sicape.network.response.AddSkttResp
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.ui.main.choose.lhp.ChooseLhpActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.action
import id.calocallo.sicape.utils.ext.showSnackbar
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.utils.ext.toast
import kotlinx.android.synthetic.main.activity_add_sktt.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddSkttActivity : BaseActivity() {
    private var idLhp: Int? = null
    private var idLp: Int? = null
    private var skttReq = SkttReq()
    private lateinit var sessionManager1: SessionManager1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_sktt)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data SKTT"
        sessionManager1 = SessionManager1(this)

        btn_save_sktt_add.attachTextChangeAnimator()
        bindProgressButton(btn_save_sktt_add)
        btn_save_sktt_add.setOnClickListener {
            addSktt()
        }

        btn_pick_lp_sktt_add.setOnClickListener {
            val intent = Intent(this, ChooseLhpActivity::class.java)
            intent.putExtra(LP_SKTT, true)
            startActivityForResult(intent, REQ_LP_ON_SKTT)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun addSktt() {
        skttReq.id_lp = idLp
        skttReq.no_laporan_hasil_audit_investigasi =
            edt_no_lap_hasil_audit_inves_sktt_add.text.toString()
        skttReq.kota_penetapan = edt_kota_penetapan_sktt_add.text.toString()
        skttReq.tanggal_penetapan = edt_tanggal_penetapan_sktt_add.text.toString()
        skttReq.nama_kabid_propam = edt_nama_pimpinan_sktt_add.text.toString()
        skttReq.pangkat_kabid_propam =
            edt_pangkat_pimpinan_sktt_add.text.toString().toUpperCase()
        skttReq.nrp_kabid_propam = edt_nrp_pimpinan_sktt_add.text.toString()
        skttReq.tembusan = edt_tembusan_sktt_add.text.toString()
        Log.e("add SKTT", "$skttReq")

        btn_save_sktt_add.showProgress {
            progressColor = Color.WHITE
        }
        apiAddSktt()
    }

    private fun apiAddSktt() {
        NetworkConfig().getServSktt().addSktt("Bearer ${sessionManager1.fetchAuthToken()}", skttReq)
            .enqueue(object :
                Callback<Base1Resp<AddSkttResp>> {
                override fun onResponse(
                    call: Call<Base1Resp<AddSkttResp>>,
                    response: Response<Base1Resp<AddSkttResp>>
                ) {
                    if (response.body()?.message == "Data sktt saved succesfully") {
                        btn_save_sktt_add.hideProgress(R.string.data_saved)
                        btn_save_sktt_add.showSnackbar(R.string.data_saved) {
                            action(R.string.next) {
                                finish()
                            }
                        }
                    } else {
                        toast("${response.body()?.message}")
                        btn_save_sktt_add.hideProgress(R.string.not_save)

                    }
                }

                override fun onFailure(call: Call<Base1Resp<AddSkttResp>>, t: Throwable) {
                    Toast.makeText(this@AddSkttActivity, "$t", Toast.LENGTH_SHORT).show()
                    btn_save_sktt_add.hideProgress(R.string.not_save)
                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_LP_ON_SKTT) {
            when (resultCode) {
                ChooseLhpActivity.RES_LP_CHOSE_LHP -> {
                    val dataLp = data?.getParcelableExtra<LpOnSkhd>(ChooseLhpActivity.DATA_LP)
                    idLp = dataLp?.lp?.id
                    txt_no_lp_sktt_add.text = dataLp?.lp?.no_lp
                }
            }
        }
    }

    companion object {
        const val REQ_LHP_ON_SKTT = 1
        const val REQ_LP_ON_SKTT = 2
        const val GET_LP_ON_SKTT = "GET_LP_ON_SKTT"
        const val LP_SKTT = "LP_SKTT"
    }
}