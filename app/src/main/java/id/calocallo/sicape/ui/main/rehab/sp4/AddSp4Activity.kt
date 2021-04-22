package id.calocallo.sicape.ui.main.rehab.sp4

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.Sp4Req
import id.calocallo.sicape.network.response.AddSp4Resp
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
import kotlinx.android.synthetic.main.activity_add_sp4.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class AddSp4Activity : BaseActivity() {
    companion object {
        const val IS_SP4 = "IS_SP4"
        const val REQ_LP_SP4 = 2
    }

    private lateinit var sessionManager1: SessionManager1
    private var idLp: Int? = null
    private var sp4Req = Sp4Req()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_sp4)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data SP3"
        sessionManager1 = SessionManager1(this)

        btn_pick_lp_sp4_add.setOnClickListener {
            val intent = Intent(this, LpChooseActivity::class.java)
            intent.putExtra(IS_SP4, true)
            startActivityForResult(intent, REQ_LP_SP4)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_save_sp4_add.attachTextChangeAnimator()
        bindProgressButton(btn_save_sp4_add)
        btn_save_sp4_add.setOnClickListener {
            sp4Req.no_sp4 = edt_no_sp4_add.text.toString()
            sp4Req.id_lp = idLp
            sp4Req.no_surat_perintah_kapolri = edt_surat_perinth_kapolri_sp4_add.text.toString()
            sp4Req.tanggal_surat_perintah_kapolri =
                edt_tgl_surat_perinth_kapolri_sp4_add.text.toString()
            sp4Req.no_hasil_audit = edt_no_hasil_audit_sp4_add.text.toString()
            sp4Req.tanggal_hasil_audit = edt_tgl_hasil_audit_sp4_add.text.toString()
            sp4Req.tanggal_gelar_audit = edt_tgl_gelar_audit_sp4_add.text.toString()
            sp4Req.kota_penetapan = edt_kota_penetapan_sp4_add.text.toString()
            sp4Req.tanggal_penetapan = edt_tanggal_penetapan_sp4_add.text.toString()
            sp4Req.nama_akreditor = edt_nama_akreditor_sp4_add.text.toString()
            sp4Req.pangkat_akreditor = edt_pangkat_akreditor_sp4_add.text.toString()
                .toUpperCase(Locale.ROOT)
            sp4Req.nrp_akreditor = edt_nrp_akreditor_sp4_add.text.toString()
            Log.e("add Sp3", "$sp4Req")

            btn_save_sp4_add.showProgress {
                progressColor = Color.WHITE
            }

            apiAddSp4()
        }
    }

    private fun apiAddSp4() {
        NetworkConfig().getServSp4().addSp4("Bearer ${sessionManager1.fetchAuthToken()}", sp4Req)
            .enqueue(object :
                Callback<Base1Resp<AddSp4Resp>> {
                override fun onResponse(
                    call: Call<Base1Resp<AddSp4Resp>>,
                    response: Response<Base1Resp<AddSp4Resp>>
                ) {
                    if (response.body()?.message == "Data sp4 saved succesfully") {
                        btn_save_sp4_add.hideProgress(R.string.data_saved)
                        btn_save_sp4_add.showSnackbar(R.string.data_saved) {
                            action(R.string.next) {
                                finish()
                            }
                        }
                    } else {
                        toast("${response.body()?.message}")
                        btn_save_sp4_add.hideProgress(R.string.not_save)
                    }
                }

                override fun onFailure(call: Call<Base1Resp<AddSp4Resp>>, t: Throwable) {
                    Toast.makeText(this@AddSp4Activity, "$t", Toast.LENGTH_SHORT).show()
                    btn_save_sp4_add.hideProgress(R.string.not_save)
                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_LP_SP4) {
            if (resultCode == PickJenisLpActivity.RES_LP_CHOOSE) {
                val dataLp = data?.getParcelableExtra<LpMinResp>(AddLhpActivity.DATA_LP)
                txt_lp_sp4_add.text = dataLp?.no_lp
                idLp = dataLp?.id
            }
        }
    }
}