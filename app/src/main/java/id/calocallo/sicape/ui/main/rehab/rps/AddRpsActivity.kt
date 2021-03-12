package id.calocallo.sicape.ui.main.rehab.rps

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.SkhdOnRpsModel
import id.calocallo.sicape.network.request.RpsReq
import id.calocallo.sicape.ui.main.choose.skhd.ChooseSkhdActivity
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_rps.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddRpsActivity : BaseActivity() {
    private var idLp: Int? = null
    private var rpsReq = RpsReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_rps)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data RPS"

        btn_save_rps_add.attachTextChangeAnimator()
        bindProgressButton(btn_save_rps_add)
        btn_save_rps_add.setOnClickListener {
            addRps()
        }

        btn_pick_lp_rps_add.setOnClickListener {
            val intent = Intent(this, ChooseSkhdActivity::class.java)
            startActivityForResult(intent, REQ_ID_SKHD)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun addRps() {
        val animated = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animated.setBounds(0, 0, size, size)
        rpsReq.id_lp = idLp
        rpsReq.nama_dinas = edt_nama_dinas_rps_add.text.toString()
        rpsReq.no_nota_dinas = edt_no_nota_dinas_rps_add.text.toString()
        rpsReq.tanggal_nota_dinas = edt_tanggal_nota_dinas_rps_add.text.toString()
        rpsReq.kota_penetapan = edt_kota_penetapan_rps_add.text.toString()
        rpsReq.tanggal_penetapan = edt_tanggal_penetapan_rps_add.text.toString()
        rpsReq.nama_kabid_propam = edt_nama_pimpinan_rps_add.text.toString()
        rpsReq.pangkat_kabid_propam = edt_pangkat_pimpinan_rps_add.text.toString()
        rpsReq.nrp_kabid_propam = edt_nrp_pimpinan_rps_add.text.toString()
        rpsReq.tembusan = edt_tembusan_rps_add.text.toString()
        Log.e("add RPS", "$rpsReq")
        btn_save_rps_add.showProgress {
            progressColor = Color.WHITE
        }
        btn_save_rps_add.showDrawable(animated) {
            textMarginRes = R.dimen.space_10dp
            buttonTextRes = R.string.data_saved
        }

        btn_save_rps_add.hideDrawable(R.string.save)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_ID_SKHD) {
          /*  if (resultCode == RES_ID_SKHD) {
                val getSkhd = data?.getParcelableExtra<SkhdOnRpsModel>(GET_SKHD)
                txt_lp_rps_add.text = getSkhd?.no_skhd
                idLp = getSkhd?.id
            }*/
        }
    }

    companion object {
        const val REQ_ID_SKHD = 1
        const val GET_SKHD = "GET_SKHD"
        const val RES_ID_SKHD = 2
    }


}