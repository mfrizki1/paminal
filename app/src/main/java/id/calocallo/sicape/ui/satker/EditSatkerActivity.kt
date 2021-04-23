package id.calocallo.sicape.ui.satker

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModelProvider
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.SatkerReq
import id.calocallo.sicape.network.request.SettingReq
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.network.response.SatKerResp
import id.calocallo.sicape.network.response.SettingResp
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.toast
import kotlinx.android.synthetic.main.activity_add_satker.*
import kotlinx.android.synthetic.main.activity_edit_satker.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditSatkerActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var satkerReq = SatkerReq()
    private var settingReq = SettingReq()
    private var dataSatker: SatKerResp? = null
    private var jenis: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_satker)
        setupActionBarWithBackButton(toolbar)
        sessionManager1 = SessionManager1(this)
        jenis = intent.getStringExtra(AddSatkerActivity.JENIS_SATKERN)

        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_save_satker_edit.gone()
            btn_del_satker_edit.gone()
        }

        if (jenis == "polres") {
            supportActionBar?.title = "Edit Data Polres"
            txt_judul_satker_edit.text = "Edit Data"
            edt_nama_satker_edit.hint = "Edit Nama Polres"
            edt_alamat_satker_edit.hint = "Edit Alamat Polres"
        } else if (jenis == "polda") {
            supportActionBar?.title = "Edit Data Satker"
            txt_judul_satker_edit.text = "Edit Data"
            edt_nama_satker_edit.hint = "Edit Nama Satker"
            edt_alamat_satker_edit.hint = "Edit Alamat Satker"
            edt_no_telp_satker_edit.hint = "Edit Telp Satker"
        } else {
            supportActionBar?.title = "Edit Data Polsek"
            txt_judul_satker_edit.text = "Edit Data"
            edt_nama_satker_edit.hint = "Edit Nama Polsek"
            edt_alamat_satker_edit.hint = "Edit Alamat Polsek"
            edt_no_telp_satker_edit.hint = "Edit Telp Polsek"
        }


        dataSatker = intent.getParcelableExtra<SatKerResp>(ListSatkerActivity.DATA_SATKER)
        edt_nama_satker_edit.editText?.setText(dataSatker?.kesatuan)
        edt_alamat_satker_edit.editText?.setText(dataSatker?.alamat_kantor)
        edt_no_telp_satker_edit.editText?.setText(dataSatker?.no_telp_kantor)

        btn_save_satker_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_satker_edit)
        btn_save_satker_edit.setOnClickListener {
            btn_save_satker_edit.showProgress {
                progressColor = Color.WHITE
            }
            satkerReq.kesatuan = edt_nama_satker_edit.editText?.text.toString()
            satkerReq.alamat_kantor = edt_alamat_satker_edit.editText?.text.toString()
            satkerReq.no_telp_kantor = edt_no_telp_satker_edit.editText?.text.toString()
//           viewModel.updSatker(sessionManager1.fetchAuthToken(), satkerReq, jenis)
            settingReq.alamat_kantor_polda = edt_alamat_satker_edit.editText?.text.toString()
            settingReq.no_telp_kantor_polda = edt_no_telp_satker_edit.editText?.text.toString()

            if (jenis == "polda") {
                apiUpdPolda()
            } else {
                apiUpdSatker()
            }
        }

        btn_del_satker_edit.setOnClickListener {
            delSatker(dataSatker)
        }
    }

    private fun delSatker(dataSatker: SatKerResp?) {
        NetworkConfig().getSatker().delSatker("Bearer ${sessionManager1.fetchAuthToken()}", dataSatker?.id)
            .enqueue(object : Callback<BaseResp> {
                override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                    if (response.body()?.message == "Data satuan kerja removed succesfully") {
                        toast(R.string.data_deleted)
                        Handler(Looper.getMainLooper()).postDelayed({ finish() }, 750)
                    } else if (response.body()?.message == "Data lp has been used as reference in another data") {
                        toast(R.string.used_on_references_lp)
                    } else {
                        toast(R.string.failed_deleted)
                    }
                }

                override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                    toast(R.string.failed_deleted)
                }
            })
    }

    private fun apiUpdSatker() {
        dataSatker?.id?.let {
            jenis?.let { it1 ->
                NetworkConfig().getSatker().updSatker(
                    "Bearer ${sessionManager1.fetchAuthToken()}",
                    it, it1, satkerReq
                ).enqueue(object : Callback<Base1Resp<SatKerResp>> {
                    override fun onResponse(
                        call: Call<Base1Resp<SatKerResp>>,
                        response: Response<Base1Resp<SatKerResp>>
                    ) {
                        if (response.body()?.message == "Data satuan kerja saved succesfully") {
                            btn_save_satker_edit.hideProgress(R.string.data_updated)

                        } else {
                            btn_save_satker_edit.hideProgress(R.string.not_update)
                            toast("${response.body()?.message}")
                        }
                    }

                    override fun onFailure(call: Call<Base1Resp<SatKerResp>>, t: Throwable) {
                        btn_save_satker_edit.hideProgress(R.string.not_update)
                        toast("$t")
                    }
                })
            }
        }
    }

    private fun apiUpdPolda() {
        NetworkConfig().getSatker()
            .updPolda("Bearer ${sessionManager1.fetchAuthToken()}", settingReq).enqueue(object :
                Callback<Base1Resp<SettingResp>> {
                override fun onResponse(
                    call: Call<Base1Resp<SettingResp>>,
                    response: Response<Base1Resp<SettingResp>>
                ) {
                    if (response.body()?.message == "Data setting updated succesfully") {
                        btn_save_satker_edit.hideProgress(R.string.data_updated)
                    } else {
                        btn_save_satker_edit.hideProgress(R.string.not_update)
                        toast("${response.body()?.message}")
                    }
                }

                override fun onFailure(call: Call<Base1Resp<SettingResp>>, t: Throwable) {
                    toast("$t")
                    btn_save_satker_edit.hideProgress(R.string.not_update)
                }
            })
    }
}