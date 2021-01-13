package id.calocallo.sicape.ui.main.rehab.rps

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.RpsResp
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.formatterTanggal
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_rps.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class DetailRpsActivity : BaseActivity() {
    private var idSkhd: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_rps)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Data RPS"
        val getDataRPS = intent.extras?.getParcelable<RpsResp>(DETAIL_RPS)
        btn_edit_rps.setOnClickListener {
            val intent = Intent(this, EditRpsActivity::class.java)
            intent.putExtra(EditRpsActivity.EDIT_RPS, getDataRPS)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        getViewRps(getDataRPS)

        bindProgressButton(btn_generate_rps_detail)
        btn_generate_rps_detail.attachTextChangeAnimator()
        btn_generate_rps_detail.setOnClickListener {
            btn_generate_rps_detail.showProgress {
                progressColor = Color.WHITE
            }
            Handler(Looper.getMainLooper()).postDelayed({
                btn_generate_rps_detail.hideProgress("Berhasil Generate Dokumen")
//                btn_download_rps_detail.visible()
                alert ("Download"){
                    positiveButton("Iya"){
                        btn_generate_rps_detail.hideProgress(R.string.generate_dokumen)

                    }
                    negativeButton("Tidak"){
                        btn_generate_rps_detail.hideProgress(R.string.generate_dokumen)

                    }
                }.show()
            }, 2000)
        }

    }

    private fun getViewRps(dataRPS: RpsResp?) {
        idSkhd = dataRPS?.skhd?.id
        txt_no_rps_detail.text = dataRPS?.no_rps
        txt_no_skhd_rps_detail.text = dataRPS?.skhd?.no_skhd
        txt_dasar_pe_rps_detail.text = dataRPS?.dasar_pe
        txt_isi_rekomedasi_detail.text = dataRPS?.isi_rekomendasi
        txt_kota_penetapan_rps_detail.text = "Kota : ${dataRPS?.kota_penetapan}"
        txt_tanggal_penetapan_rps_detail.text =
            "Tanggal : ${formatterTanggal(dataRPS?.tanggal_penetapan)}"
        txt_nama_pimpinan_rps_detail.text = "Nama : ${dataRPS?.nama_yang_menetapkan}"
        txt_pangkat_nrp_pimpinan_rps_detail.text =
            "Pangkat : ${dataRPS?.pangkat_yang_menetapkan.toString()
                .toUpperCase()}, NRP : ${dataRPS?.nrp_yang_menetapkan}"
        txt_jabatan_pimpinan_rps_detail.text = "Jabatan : ${dataRPS?.jabatan_yang_menetapkan}"
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