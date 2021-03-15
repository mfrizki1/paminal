package id.calocallo.sicape.ui.main.lp.pasal

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
import id.calocallo.sicape.network.request.ListIdPasalReq
import id.calocallo.sicape.network.response.AddPasalDilanggarResp
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.LpMinResp
import id.calocallo.sicape.network.response.PasalResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_pasal_dilanggar.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddPasalDilanggarActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1

    companion object {
        const val REQ_PASAL_DILANGGAR = 1
        const val PICK_PASAL = "PICK_PASAL"
    }

    private var pasalDilanggarReq = ListIdPasalReq()
    private var idPasal: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pasal_dilanggar)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Pasal Yang Dilanggar"

        sessionManager1 = SessionManager1(this)
        val detailLp =
            intent.getParcelableExtra<LpMinResp>(ListPasalDilanggarActivity.ADD_PASAL_DILANGGAR)
        btn_choose_pasal.setOnClickListener {
            val intent = Intent(this, ListPasalActivity::class.java).apply {
                this.putExtra("PICK_PASAL", true)
            }
            startActivityForResult(intent, REQ_PASAL_DILANGGAR)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        bindProgressButton(btn_save_pasal_dilanggar_add)
        btn_save_pasal_dilanggar_add.attachTextChangeAnimator()
        btn_save_pasal_dilanggar_add.setOnClickListener {
            pasalDilanggarReq.id_pasal = idPasal
            btn_save_pasal_dilanggar_add.showProgress {
                progressColor = Color.WHITE
            }
            addPasalDilanggar(detailLp)
            Log.e("pasalDIlanggar", "$pasalDilanggarReq")
        }
    }

    private fun addPasalDilanggar(detailLp: LpMinResp?) {
        detailLp?.id?.let {
            NetworkConfig().getServLp().addPasalDilanggar(
                "Bearer ${sessionManager1.fetchAuthToken()}", it, pasalDilanggarReq
            ).enqueue(object : Callback<Base1Resp<AddPasalDilanggarResp>> {
                override fun onFailure(call: Call<Base1Resp<AddPasalDilanggarResp>>, t: Throwable) {
                    Toast.makeText(
                        this@AddPasalDilanggarActivity,
                        R.string.error_conn,
                        Toast.LENGTH_SHORT
                    ).show()
                    btn_save_pasal_dilanggar_add.hideProgress(R.string.not_save)
                }

                override fun onResponse(
                    call: Call<Base1Resp<AddPasalDilanggarResp>>,
                    response: Response<Base1Resp<AddPasalDilanggarResp>>
                ) {
                    if (response.body()?.message == "Data pasal dilanggar saved succesfully") {
                        btn_save_pasal_dilanggar_add.hideProgress(R.string.data_saved)
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        }, 500)
                    } else {
                        btn_save_pasal_dilanggar_add.hideProgress(R.string.not_save)
                    }
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_PASAL_DILANGGAR) {
            if (resultCode == ListPasalActivity.RES_PASAL_DILANGGAR) {
                val dataPasal = data?.getParcelableExtra<PasalResp>("DATA_PASAL")
                Log.e("dataPasal", "$dataPasal")
                idPasal = dataPasal?.id
                txt_pasal_on_pasal_dilanggar.text = dataPasal?.nama_pasal
                txt_tentang_on_pasal_dilanggar.text = dataPasal?.tentang_pasal
                txt_isi_on_pasal_dilanggar.text = dataPasal?.isi_pasal
            }
        }
    }
}