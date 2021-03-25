package id.calocallo.sicape.ui.satker

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.SatkerReq
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.SatKerResp
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.toast
import kotlinx.android.synthetic.main.activity_add_satker.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddSatkerActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var satkerReq = SatkerReq()
    private var jenis :String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_satker)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Satker"
         jenis = intent.getStringExtra(JENIS_SATKERN)
        if (jenis == "polda") {
            edt_alamat_satker_add.gone()
            edt_no_telp_satker_add.gone()
        } else if (jenis == "polres") {
            txt_judul_satker_add.text = "Tambah Data Polres"
            edt_nama_satker_add.hint = "Tambah Nama Polres"
            edt_alamat_satker_add.hint = "Tambah Alamat Polres"
            edt_no_telp_satker_add.hint = "Tambah No Telp Polres"
        } else {
            txt_judul_satker_add.text = "Tambah Data Polsek"
            edt_nama_satker_add.hint = "Tambah Nama Polsek"
            edt_alamat_satker_add.hint = "Tambah Alamat Polsek"
            edt_no_telp_satker_add.hint = "Tambah Telp Polsek"
        }
        sessionManager1 = SessionManager1(this)
        btn_save_satker_add.attachTextChangeAnimator()
        bindProgressButton(btn_save_satker_add)
        btn_save_satker_add.setOnClickListener {
            btn_save_satker_add.showProgress { progressColor = Color.WHITE }
            satkerReq.kesatuan = edt_nama_satker_add.editText?.text.toString()
            satkerReq.alamat_kantor = edt_alamat_satker_add.editText?.text.toString()
            satkerReq.no_telp_kantor = edt_no_telp_satker_add.editText?.text.toString()
            apiAddSatker()
        }
    }

    private fun apiAddSatker() {
        jenis?.let {
            NetworkConfig().getSatker().addSatker(
                ListSatkerActivity.token,
                it, satkerReq
            ).enqueue(object : Callback<Base1Resp<SatKerResp>> {
                override fun onResponse(
                    call: Call<Base1Resp<SatKerResp>>,
                    response: Response<Base1Resp<SatKerResp>>
                ) {
                    if (response.body()?.message == "Data satuan kerja saved succesfully") {
                        btn_save_satker_add.hideProgress(R.string.data_saved)
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        }, 750)
                    } else {
                        btn_save_satker_add.hideProgress(R.string.not_save)
                        toast("${response.body()?.message}")
                    }
                }

                override fun onFailure(call: Call<Base1Resp<SatKerResp>>, t: Throwable) {
                    btn_save_satker_add.hideProgress(R.string.not_save)
                    toast("$t")
                }
            })
        }

    }

    companion object {
        const val JENIS_SATKERN = "JENIS"

    }
}