package id.calocallo.sicape.ui.main.rehab.rpph

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.hideDrawable
import com.github.razir.progressbutton.showDrawable
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PutKkeOnRpphModel
import id.calocallo.sicape.network.request.RpphReq
import id.calocallo.sicape.network.response.RpphResp
import id.calocallo.sicape.ui.main.choose.skhd.ChooseSkhdActivity
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_rpph.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddRpphActivity : BaseActivity() {
    private var rpphReq = RpphReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_rpph)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data RPPH"


        btn_pick_put_kke_rps_add.setOnClickListener {
            val intent = Intent(this, ChooseSkhdActivity::class.java)
            intent.putExtra(ChooseSkhdActivity.PUT_KKE, ChooseSkhdActivity.PUT_KKE)
            startActivityForResult(intent, REQ_PUT_KKE_ON_RPPH)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        btn_save_rpph_add.setOnClickListener {
            rpphReq.no_rpph = edt_no_rpph_add.text.toString()
            rpphReq.dasar_ph = edt_dasar_rpph_add.text.toString()
            rpphReq.isi_rekomendasi = edt_isi_rekomendasi_rpph_add.text.toString()
            rpphReq.kota_penetapan = edt_kota_penetapan_rpph_add.text.toString()
            rpphReq.tanggal_penetapan = edt_tanggal_penetapan_rpph_add.text.toString()
            rpphReq.nama_yang_menetapkan = edt_nama_pimpinan_rpph_add.text.toString()
            rpphReq.pangkat_yang_menetapkan = edt_pangkat_pimpinan_rpph_add.text.toString().toUpperCase()
            rpphReq.nrp_yang_menetapkan = edt_nrp_pimpinan_rpph_add.text.toString()
            rpphReq.tembusan = edt_tembusan_rpph_add.text.toString()
            rpphReq.jabatan_yang_menetapkan = edt_jabatan_pimpinan_rpph_add.text.toString()
            val animated = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
            val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
            animated.setBounds(0,0,size,size)

            btn_save_rpph_add.showProgress{
                    progressColor = Color.WHITE
                }

            btn_save_rpph_add.showDrawable(animated){
                    textMarginRes = R.dimen.space_10dp
                    buttonTextRes = R.string.data_saved
                }

            btn_save_rpph_add.hideDrawable(R.string.save)
            Log.e("add Data RPPH","$rpphReq")
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_PUT_KKE_ON_RPPH) {
            if (resultCode == RES_PUT_KKE_ON_RPPH) {
                val putKkeModel = data?.getParcelableExtra<PutKkeOnRpphModel>(GET_RPPH)
                rpphReq.id_putkke = putKkeModel?.id
                txt_put_kke_rps_add.text = putKkeModel?.no_putkke
            }
        }
    }

    companion object {
        const val GET_RPPH = "GET_RPPH"
        const val REQ_PUT_KKE_ON_RPPH = 1
        const val RES_PUT_KKE_ON_RPPH = 2
    }
}