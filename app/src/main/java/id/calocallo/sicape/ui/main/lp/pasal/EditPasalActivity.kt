package id.calocallo.sicape.ui.main.lp.pasal

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.PasalReq
import id.calocallo.sicape.network.response.AddPasalResp
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.PasalResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.utils.ext.toast
import kotlinx.android.synthetic.main.activity_edit_pasal.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditPasalActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var pasalReq = PasalReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pasal)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Pasal"
        sessionManager1 = SessionManager1(this)

        val detailPasal = intent.getParcelableExtra<PasalResp>(DetailPasalActivity.DETAIL_PASAL)
        viewEditPasal(detailPasal)
        btn_save_edit_pasal.attachTextChangeAnimator()
        bindProgressButton(btn_save_edit_pasal)
        btn_save_edit_pasal.setOnClickListener {
            btn_save_edit_pasal.showProgress {
                progressColor = Color.WHITE
            }
            pasalReq.nama_pasal = edt_pasal_edit.text.toString()
            pasalReq.isi_pasal = edt_isi_pasal_edit.text.toString()
            pasalReq.tentang_pasal = edt_tentang_pasal_edit.text.toString()

            editPasal(detailPasal)
        }
    }

    private fun viewEditPasal(pasal: PasalResp?) {
        edt_pasal_edit.setText(pasal?.nama_pasal)
        edt_tentang_pasal_edit.setText(pasal?.tentang_pasal)
        edt_isi_pasal_edit.setText(pasal?.isi_pasal)
    }

    private fun editPasal(detailPasal: PasalResp?) {
        detailPasal?.id?.let {
            NetworkConfig().getServLp().updPasal(
                "Bearer ${sessionManager1.fetchAuthToken()}",
                it, pasalReq
            ).enqueue(object : Callback<Base1Resp<AddPasalResp>> {
                override fun onFailure(call: Call<Base1Resp<AddPasalResp>>, t: Throwable) {
                    Toast.makeText(this@EditPasalActivity, R.string.error_conn, Toast.LENGTH_SHORT)
                        .show()
                    btn_save_edit_pasal.hideProgress(R.string.not_update)
                }

                override fun onResponse(
                    call: Call<Base1Resp<AddPasalResp>>,
                    response: Response<Base1Resp<AddPasalResp>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.message == "Data pasal updated succesfully") {
                            btn_save_edit_pasal.hideProgress(R.string.data_updated)
                            finish()
                        }else{
                            btn_save_edit_pasal.hideProgress(R.string.not_update)
                        }
                    } else {
                        toast("${response.body()?.message}")
                        btn_save_edit_pasal.hideProgress(R.string.not_update)
                    }
                }
            })
        }
    }
}