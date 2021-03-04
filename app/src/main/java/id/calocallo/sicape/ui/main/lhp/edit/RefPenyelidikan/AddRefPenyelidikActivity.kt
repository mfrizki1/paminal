package id.calocallo.sicape.ui.main.lhp.edit.RefPenyelidikan

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
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.RefPenyelidikanReq
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.ui.main.choose.lp.PickJenisLpActivity
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_ref_penyelidik.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddRefPenyelidikActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var idLp: Int? = null
    private var refPenyelidikanReq = RefPenyelidikanReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ref_penyelidik)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Referensi Penyelidikan"
        val isSingleAdd = intent.getBooleanExtra(SINGLE_ADD, false)
        val dataLhp = intent.getParcelableExtra<LhpMinResp>(DATA_LHP)

        sessionManager1 = SessionManager1(this)
        btn_add_lp_ref_penyelidikan.setOnClickListener {
            val intent = Intent(this, PickJenisLpActivity::class.java)
            startActivityForResult(intent, REQ_LP)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        btn_save_ref_penyelidik_add.attachTextChangeAnimator()
        bindProgressButton(btn_save_ref_penyelidik_add)
        btn_save_ref_penyelidik_add.setOnClickListener {
            refPenyelidikanReq.id_lp = idLp
            refPenyelidikanReq.isi_keterangan_terlapor = edt_ket_terlapor_ref_add.text.toString()
            Log.e("refPenyelidikanReq", "$refPenyelidikanReq")
            btn_save_ref_penyelidik_add.showProgress { progressColor = Color.WHITE }
            addRefPenyelidikSingle(dataLhp)
        }
    }

    private fun addRefPenyelidikSingle(dataLhp: LhpMinResp?) {
        NetworkConfig().getServLhp()
            .addRefPenyelidikan("Bearer ${sessionManager1.fetchAuthToken()}", dataLhp?.id, refPenyelidikanReq)
            .enqueue(object : Callback<Base1Resp<AddRefPenyelidikanResp>> {
                override fun onResponse(
                    call: Call<Base1Resp<AddRefPenyelidikanResp>>,
                    response: Response<Base1Resp<AddRefPenyelidikanResp>>
                ) {
                    if (response.body()?.message == "Data referensi penyelidikan saved succesfully") {
                        btn_save_ref_penyelidik_add.hideProgress(R.string.data_saved)
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        },1000)
                    } else {
                        btn_save_ref_penyelidik_add.hideProgress(R.string.not_save)
                    }
                }

                override fun onFailure(
                    call: Call<Base1Resp<AddRefPenyelidikanResp>>,
                    t: Throwable
                ) {
                    Toast.makeText(this@AddRefPenyelidikActivity, "$t", Toast.LENGTH_SHORT).show()
                    btn_save_ref_penyelidik_add.hideProgress(R.string.not_save)
                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_LP && resultCode == RES_LP_ON_REF) {
            val dataLp = data?.getParcelableExtra<LpMinResp>(GET_LP_FROM_CHOOSE_LP)
            Log.e("dataLp", "$dataLp")
            idLp = dataLp?.id
            txt_no_lp_ref_add.text = dataLp?.no_lp
        }
    }

    companion object {
        const val DATA_LHP = "DATA_REF_PENYELIDIK"
        const val SINGLE_ADD = "SINGLE_ADD"
        const val REQ_LP = 234
        const val RES_LP_ON_REF = 124
        const val GET_LP_FROM_CHOOSE_LP = "GET_LP_FROM_CHOOSE_LP"

    }
}