package id.calocallo.sicape.ui.main.rehab.rpph

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PutKkeOnRpphModel
import id.calocallo.sicape.network.request.RpphReq
import id.calocallo.sicape.network.response.RpphResp
import id.calocallo.sicape.ui.main.choose.skhd.ChooseSkhdActivity
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_rpph.*
import kotlinx.android.synthetic.main.activity_edit_rpph.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditRpphActivity : BaseActivity() {
    private var rpphReq = RpphReq()
    private var idPutKke: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_rpph)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data RPPH"

        val dataRpph = intent.extras?.getParcelable<RpphResp>(EDIT_RPPH_EDIT)
        getDataRpph(dataRpph)

        btn_save_rpph_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_rpph_edit)
        btn_save_rpph_edit.setOnClickListener {
            updateRpph(dataRpph)
        }

        btn_pick_put_kke_rps_edit.setOnClickListener {
            val intent = Intent(this, ChooseSkhdActivity::class.java)
            intent.putExtra(ChooseSkhdActivity.PUT_KKE, ChooseSkhdActivity.PUT_KKE)
            startActivityForResult(intent, REQ_PUT_KKE_ON_RPPH_EDIT)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_PUT_KKE_ON_RPPH_EDIT) {
            if (resultCode == RES_PUT_KKE_ON_RPPH_EDIT) {
                val putKkeModel = data?.getParcelableExtra<PutKkeOnRpphModel>(GET_RPPH_EDIT)
                idPutKke = putKkeModel?.id
                txt_put_kke_rps_edit.text = putKkeModel?.no_putkke
            }
        }
    }

    private fun updateRpph(dataRpph: RpphResp?) {
        rpphReq.no_rpph = edt_no_rpph_edit.text.toString()
        rpphReq.dasar_ph = edt_dasar_rpph_edit.text.toString()
        rpphReq.isi_rekomendasi = edt_isi_rekomendasi_rpph_edit.text.toString()
        rpphReq.kota_penetapan = edt_kota_penetapan_rpph_edit.text.toString()
        rpphReq.tanggal_penetapan = edt_tanggal_penetapan_rpph_edit.text.toString()
        rpphReq.nama_yang_menetapkan = edt_nama_pimpinan_rpph_edit.text.toString()
        rpphReq.pangkat_yang_menetapkan =
            edt_pangkat_pimpinan_rpph_edit.text.toString().toUpperCase()
        rpphReq.nrp_yang_menetapkan = edt_nrp_pimpinan_rpph_edit.text.toString()
        rpphReq.tembusan = edt_tembusan_rpph_edit.text.toString()
        rpphReq.jabatan_yang_menetapkan = edt_jabatan_pimpinan_rpph_edit.text.toString()
        rpphReq.id_putkke = idPutKke
        Log.e("edit RPPH", "$rpphReq")

        val animated = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animated.setBounds(0, 0, size, size)
        btn_save_rpph_edit.showProgress {
            progressColor = Color.WHITE
        }

        btn_save_rpph_edit.showDrawable(animated) {
            textMarginRes = R.dimen.space_10dp
            buttonTextRes = R.string.data_updated
        }

        Handler(Looper.getMainLooper()).postDelayed({
            btn_save_rpph_edit.hideDrawable(R.string.save)

        }, 2000)
    }

    private fun getDataRpph(dataRpph: RpphResp?) {
        txt_put_kke_rps_edit.text = dataRpph?.putkke?.no_putkke
        idPutKke = dataRpph?.putkke?.id
        edt_no_rpph_edit.setText(dataRpph?.no_rpph)
        edt_dasar_rpph_edit.setText(dataRpph?.dasar_ph)
        edt_isi_rekomendasi_rpph_edit.setText(dataRpph?.isi_rekomendasi)
        edt_kota_penetapan_rpph_edit.setText(dataRpph?.kota_penetapan)
        edt_tanggal_penetapan_rpph_edit.setText(dataRpph?.tanggal_penetapan)
        edt_nama_pimpinan_rpph_edit.setText(dataRpph?.nama_yang_menetapkan)
        edt_pangkat_pimpinan_rpph_edit.setText(
            dataRpph?.pangkat_yang_menetapkan.toString().toUpperCase()
        )
        edt_nrp_pimpinan_rpph_edit.setText(dataRpph?.nrp_yang_menetapkan)
        edt_jabatan_pimpinan_rpph_edit.setText(dataRpph?.jabatan_yang_menetapkan)
        edt_tembusan_rpph_edit.setText(dataRpph?.tembusan)
    }

    companion object {
        const val EDIT_RPPH_EDIT = "EDIT_RPPH"
        const val GET_RPPH_EDIT = "GET_RPPH"
        const val REQ_PUT_KKE_ON_RPPH_EDIT = 1
        const val RES_PUT_KKE_ON_RPPH_EDIT = 2
    }
}