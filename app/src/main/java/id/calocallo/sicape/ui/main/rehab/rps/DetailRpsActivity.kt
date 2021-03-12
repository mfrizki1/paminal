package id.calocallo.sicape.ui.main.rehab.rps

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.RpsMinResp
import id.calocallo.sicape.network.response.RpsResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.formatterTanggal
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_rps.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailRpsActivity : BaseActivity() {
    private var idSkhd: Int? = null
    private lateinit var sessionManager1: SessionManager1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_rps)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Data RPS"
        sessionManager1 = SessionManager1(this)
        val dataMinRps = intent.extras?.getParcelable<RpsMinResp>(DETAIL_RPS)

        apiDetailRps(dataMinRps)
//        getViewRps(dataMinRps)

        bindProgressButton(btn_generate_rps_detail)
        btn_generate_rps_detail.attachTextChangeAnimator()
        btn_generate_rps_detail.setOnClickListener {
            btn_generate_rps_detail.showProgress {
                progressColor = Color.WHITE
            }
            Handler(Looper.getMainLooper()).postDelayed({
                btn_generate_rps_detail.hideProgress("Berhasil Generate Dokumen")
//                btn_download_rps_detail.visible()
                alert("Download") {
                    positiveButton("Iya") {
                        btn_generate_rps_detail.hideProgress(R.string.generate_dokumen)

                    }
                    negativeButton("Tidak") {
                        btn_generate_rps_detail.hideProgress(R.string.generate_dokumen)

                    }
                }.show()
            }, 2000)
        }

    }

    private fun apiDetailRps(dataMinRps: RpsMinResp?) {
        NetworkConfig().getServRps()
            .detailRps("Bearer ${sessionManager1.fetchAuthToken()}", dataMinRps?.id).enqueue(
                object :
                    Callback<RpsResp> {
                    override fun onResponse(call: Call<RpsResp>, response: Response<RpsResp>) {
                        if (response.isSuccessful) {
                            getViewRps(response.body())
                        } else {
                            Toast.makeText(
                                this@DetailRpsActivity, R.string.error, Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<RpsResp>, t: Throwable) {
                        Toast.makeText(this@DetailRpsActivity, "$t", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    @SuppressLint("SetTextI18n")
    private fun getViewRps(dataRPS: RpsResp?) {
        btn_edit_rps.setOnClickListener {
            val intent = Intent(this, EditRpsActivity::class.java)
            intent.putExtra(EditRpsActivity.EDIT_RPS, dataRPS)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

//        idSkhd = dataRPS?.skhd?.id
        txt_no_rps_detail.text = "No: ${dataRPS?.no_rps}"
        txt_no_lp_rps_detail.text = "No: ${dataRPS?.lp?.no_lp}"
        txt_nama_dinas_rps_detail.text = "Nama Dinas: ${dataRPS?.nama_dinas}"
        txt_no_nota_dinas_rps_detail.text =
            "No Dinas: ${dataRPS?.no_nota_dinas}, Tanggal: ${formatterTanggal(dataRPS?.tanggal_nota_dinas)}"
        txt_kota_penetapan_rps_detail.text = "Kota: ${dataRPS?.kota_penetapan}"
        txt_tanggal_penetapan_rps_detail.text =
            "Tanggal: ${formatterTanggal(dataRPS?.tanggal_penetapan)}"
        txt_nama_pimpinan_rps_detail.text = "Nama : ${dataRPS?.nama_kabid_propam}"
        txt_pangkat_nrp_pimpinan_rps_detail.text =
            "Pangkat: ${
                dataRPS?.pangkat_kabid_propam.toString()
                    .toUpperCase()
            }, NRP : ${dataRPS?.nrp_kabid_propam}"
        txt_tembusan_rps_detail.text = dataRPS?.tembusan
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_delete, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_delete_item -> {
                alertDialogDelete()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun alertDialogDelete() {
        this.alert("Yakin Hapus Data?") {
            positiveButton("Iya") {
//                ApiDelete()
                finish()
            }
            negativeButton("Tidak") {
            }
        }.show()
    }

    companion object {
        const val DETAIL_RPS = "DETAIL_RPS"
    }
}