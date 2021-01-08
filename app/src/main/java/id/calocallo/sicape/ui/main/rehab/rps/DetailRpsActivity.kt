package id.calocallo.sicape.ui.main.rehab.rps

import android.content.Intent
import android.os.Bundle
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.RpsResp
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
    }

    private fun getViewRps(dataRPS: RpsResp?) {
        idSkhd = dataRPS?.skhd?.id
        txt_no_rps_detail.text = dataRPS?.no_rps
        txt_no_skhd_rps_detail.text = dataRPS?.skhd?.no_skhd
        txt_dasar_pe_rps_detail.text = dataRPS?.dasar_pe
        txt_isi_rekomedasi_detail.text = dataRPS?.isi_rekomendasi
        txt_kota_penetapan_rps_detail.text = "Kota : ${dataRPS?.kota_penetapan}"
        txt_tanggal_penetapan_rps_detail.text = "Tanggal : ${dataRPS?.tanggal_penetapan}"
        txt_nama_pimpinan_rps_detail.text = "Nama : ${dataRPS?.nama_yang_menetapkan}"
        txt_pangkat_nrp_pimpinan_rps_detail.text =
            "Pangkat : ${dataRPS?.pangkat_yang_menetapkan.toString()
                .toUpperCase()}, NRP : ${dataRPS?.nrp_yang_menetapkan}"
        txt_jabatan_pimpinan_rps_detail.text = "Jabatan : ${dataRPS?.jabatan_yang_menetapkan}"
        txt_tembusan_rps_detail.text = dataRPS?.tembusan
    }

    companion object {
        const val DETAIL_RPS = "DETAIL_RPS"
    }
}