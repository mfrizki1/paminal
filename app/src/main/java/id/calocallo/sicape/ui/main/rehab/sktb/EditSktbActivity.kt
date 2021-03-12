package id.calocallo.sicape.ui.main.rehab.sktb

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.SktbReq
import id.calocallo.sicape.network.response.AddSktbResp
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.SktbResp
import id.calocallo.sicape.ui.main.rehab.rpph.EditRpphActivity
import id.calocallo.sicape.ui.main.rehab.rps.EditRpsActivity
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_sktb.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditSktbActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var idLp: Int? = null
    private var sktbReq = SktbReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_sktb)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data SKTB"
        sessionManager1 = SessionManager1(this)
        val dataSktb = intent.extras?.getParcelable<SktbResp>(EDIT_SKTB)
        getViewSktb(dataSktb)

        btn_save_sktb_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_sktb_edit)
        btn_save_sktb_edit.setOnClickListener {
            updateSktb(dataSktb)
        }
    }

    private fun updateSktb(dataSktb: SktbResp?) {

        sktbReq.kota_penetapan = edt_kota_penetapan_sktb_edit.text.toString()
        sktbReq.tanggal_penetapan = edt_tanggal_penetapan_sktb_edit.text.toString()
        sktbReq.nama_kabid_propam = edt_nama_pimpinan_sktb_edit.text.toString()
        sktbReq.pangkat_kabid_propam =
            edt_pangkat_pimpinan_sktb_edit.text.toString().toUpperCase()
        sktbReq.nrp_kabid_propam = edt_nrp_pimpinan_sktb_edit.text.toString()
        sktbReq.tembusan = edt_tembusan_sktb_edit.text.toString()

        btn_save_sktb_edit.showProgress {
            progressColor = Color.WHITE
        }
        Handler(Looper.getMainLooper()).postDelayed({
            btn_save_sktb_edit.hideDrawable(R.string.save)
        }, 2000)

        apiUpdSktb(dataSktb)
        Log.e("Edit SKTB", "$sktbReq")
    }

    private fun apiUpdSktb(dataSktb: SktbResp?) {
        NetworkConfig().getServSktb().updSktb("Bearer ${sessionManager1.fetchAuthToken()}", dataSktb?.id, sktbReq).enqueue(
            object :
                Callback<Base1Resp<AddSktbResp>> {
                override fun onResponse(
                    call: Call<Base1Resp<AddSktbResp>>,
                    response: Response<Base1Resp<AddSktbResp>>
                ) {
                    if (response.body()?.message == "Data sktb updated succesfully") {
                        btn_save_sktb_edit.hideDrawable(R.string.data_updated)
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        },750)

                    } else {
                        btn_save_sktb_edit.hideDrawable(R.string.not_update)
                    }
                }

                override fun onFailure(call: Call<Base1Resp<AddSktbResp>>, t: Throwable) {
                    Toast.makeText(this@EditSktbActivity, "$t", Toast.LENGTH_SHORT).show()
                    btn_save_sktb_edit.hideDrawable(R.string.not_update)
                }
            })
    }

    private fun getViewSktb(dataSktb: SktbResp?) {
        txt_lp_sktb_edit.text = dataSktb?.lp?.no_lp
        edt_no_sktb_edit.setText(dataSktb?.lp?.no_lp)
        edt_kota_penetapan_sktb_edit.setText(dataSktb?.kota_penetapan)
        edt_tanggal_penetapan_sktb_edit.setText(dataSktb?.tanggal_penetapan)
        edt_nama_pimpinan_sktb_edit.setText(dataSktb?.nama_kabid_propam)
        edt_pangkat_pimpinan_sktb_edit.setText(dataSktb?.pangkat_kabid_propam)
        edt_nrp_pimpinan_sktb_edit.setText(dataSktb?.nrp_kabid_propam)
        edt_tembusan_sktb_edit.setText(dataSktb?.tembusan)
    }

    companion object {
        const val EDIT_SKTB = "EDIT_SKTB"
    }
}