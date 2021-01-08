package id.calocallo.sicape.ui.main.rehab.rps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import id.calocallo.sicape.R
import id.calocallo.sicape.model.SkhdOnRpsModel
import id.calocallo.sicape.network.request.RpsReq
import id.calocallo.sicape.network.response.RpsResp
import id.calocallo.sicape.ui.main.choose.skhd.ChooseSkhdActivity
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_rps.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditRpsActivity : BaseActivity() {
    private var idSkhd: Int? = null
    private var rpsReq = RpsReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_rps)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data RPS"
        val getDataRps = intent.extras?.getParcelable<RpsResp>(EDIT_RPS)
        getViewDataRps(getDataRps)

        btn_save_rps_edit.setOnClickListener {
            updateRps(getDataRps)
        }
        btn_pick_skhd_rps_edit.setOnClickListener {
            val intent = Intent(this, ChooseSkhdActivity::class.java)
            startActivityForResult(intent, REQ_ID_SKHD_EDIT_RPS)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_ID_SKHD_EDIT_RPS) {
            if (resultCode == AddRpsActivity.RES_ID_SKHD) {
                val getSkhd = data?.getParcelableExtra<SkhdOnRpsModel>(AddRpsActivity.GET_SKHD)
                txt_skhd_rps_edit.text = getSkhd?.no_skhd
                idSkhd = getSkhd?.id
            }
        }
    }

    private fun updateRps(dataRps: RpsResp?) {
        rpsReq.no_rps = edt_no_rps_edit.text.toString()
        rpsReq.dasar_pe = edt_nota_dinas_rps_edit.text.toString()
        rpsReq.isi_rekomendasi = edt_isi_rekomendasi_rps_edit.text.toString()
        rpsReq.kota_penetapan = edt_kota_penetapan_rps_edit.text.toString()
        rpsReq.tanggal_penetapan = edt_tanggal_penetapan_rps_edit.text.toString()
        rpsReq.nama_yang_menetapkan = edt_nama_pimpinan_rps_edit.text.toString()
        rpsReq.pangkat_yang_menetapkan = edt_pangkat_pimpinan_rps_edit.text.toString()
        rpsReq.nrp_yang_menetapkan = edt_nrp_pimpinan_rps_edit.text.toString()
        rpsReq.jabatan_yang_menetapkan = edt_jabatan_pimpinan_rps_edit.text.toString()
        rpsReq.tembusan = edt_tembusan_rps_edit.text.toString()
        rpsReq.id_skhd = idSkhd
        Log.e("edit RPs", "$rpsReq")
    }

    private fun getViewDataRps(dataRps: RpsResp?) {
        idSkhd = dataRps?.skhd?.id
        txt_skhd_rps_edit.text = dataRps?.skhd?.no_skhd
        edt_no_rps_edit.setText(dataRps?.no_rps)
        edt_nota_dinas_rps_edit.setText(dataRps?.dasar_pe)
        edt_isi_rekomendasi_rps_edit.setText(dataRps?.isi_rekomendasi)
        edt_kota_penetapan_rps_edit.setText(dataRps?.kota_penetapan)
        edt_tanggal_penetapan_rps_edit.setText(dataRps?.tanggal_penetapan)
        edt_nama_pimpinan_rps_edit.setText(dataRps?.nama_yang_menetapkan)
        edt_pangkat_pimpinan_rps_edit.setText(
            dataRps?.pangkat_yang_menetapkan.toString().toUpperCase()
        )
        edt_nrp_pimpinan_rps_edit.setText(dataRps?.nrp_yang_menetapkan)
        edt_jabatan_pimpinan_rps_edit.setText(dataRps?.jabatan_yang_menetapkan)
        edt_tembusan_rps_edit.setText(dataRps?.tembusan)
    }

    companion object {
        const val EDIT_RPS = "EDIT_RPS"
        const val REQ_ID_SKHD_EDIT_RPS = 2
    }
}