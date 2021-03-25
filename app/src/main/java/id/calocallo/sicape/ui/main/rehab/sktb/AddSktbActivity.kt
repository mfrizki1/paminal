package id.calocallo.sicape.ui.main.rehab.sktb

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.SktbReq
import id.calocallo.sicape.network.response.AddSktbResp
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
import kotlinx.android.synthetic.main.activity_add_sktb.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddSktbActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var idLp: Int? = null
    private var sktbReq = SktbReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_sktb)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data SKTB"
        sessionManager1 = SessionManager1(this)

        btn_pick_lp_sktb_add.setOnClickListener {
            val intent = Intent(this, LpChooseActivity::class.java)
            intent.putExtra(IS_SKTB, true)
            startActivityForResult(intent, REQ_LP_SKTB)
        }
        btn_save_sktb_add.attachTextChangeAnimator()
        bindProgressButton(btn_save_sktb_add)
        btn_save_sktb_add.setOnClickListener {
            addSktb()
        }
    }

    private fun addSktb() {
        sktbReq.id_lp = idLp
        sktbReq.kota_penetapan = edt_kota_penetapan_sktb_add.text.toString()
        sktbReq.tanggal_penetapan = edt_tanggal_penetapan_sktb_add.text.toString()
        sktbReq.nama_kabid_propam = edt_nama_pimpinan_sktb_add.text.toString()
        sktbReq.pangkat_kabid_propam =
            edt_pangkat_pimpinan_sktb_add.text.toString().toUpperCase()
        sktbReq.nrp_kabid_propam = edt_nrp_pimpinan_sktb_add.text.toString()
        sktbReq.tembusan = edt_tembusan_sktb_add.text.toString()

        btn_save_sktb_add.showProgress {
            progressColor = Color.WHITE
        }
        apiAddSktb()
        Log.e("Add SKTB", "$sktbReq")
    }

    private fun apiAddSktb() {
        NetworkConfig().getServSktb().addSktb("Bearer ${sessionManager1.fetchAuthToken()}", sktbReq)
            .enqueue(
                object : Callback<Base1Resp<AddSktbResp>> {
                    override fun onResponse(
                        call: Call<Base1Resp<AddSktbResp>>,
                        response: Response<Base1Resp<AddSktbResp>>
                    ) {
                        if (response.body()?.message == "Data sktb saved succesfully") {
                            btn_save_sktb_add.hideDrawable(R.string.data_saved)
                            btn_save_sktb_add.showSnackbar(R.string.data_saved) {
                                action(R.string.next) {
                                    finish()
                                }
                            }
                        } else {
                            toast("${response.body()?.message}")
                            btn_save_sktb_add.hideDrawable(R.string.not_save)
                        }
                    }

                    override fun onFailure(call: Call<Base1Resp<AddSktbResp>>, t: Throwable) {
                        Toast.makeText(this@AddSktbActivity, "$t", Toast.LENGTH_SHORT).show()
                        btn_save_sktb_add.hideDrawable(R.string.not_save)
                    }

                })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_LP_SKTB) {
            if (resultCode == PickJenisLpActivity.RES_LP_CHOOSE) {
                val dataLp = data?.getParcelableExtra<LpMinResp>(AddLhpActivity.DATA_LP)
                txt_lp_sktb_add.text = dataLp?.no_lp
                idLp = dataLp?.id
            }
        }
    }

    companion object {
        const val REQ_LP_SKTB = 1
        const val REQ_PUTKKE_ON_SKTB = 2
        const val RES_SKHD_ON_SKTB = 3
        const val RES_PUTKKE_ON_SKTB = 4
        const val IS_SKTB = "IS_SKTB"
    }
}