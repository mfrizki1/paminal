package id.calocallo.sicape.ui.main.lp.saksi

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.SaksiReq
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_saksi_lp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddSaksiLpActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var saksiLpReq = SaksiReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_saksi_lp)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Saksi"

        btn_save_add_saksi.attachTextChangeAnimator()
        bindProgressButton(btn_save_add_saksi)
        btn_save_add_saksi.setOnClickListener {
            addSaksiLp()
        }
    }

    private fun addSaksiLp() {
        saksiLpReq.nama_saksi = edt_nama_saksi_single.text.toString()
        saksiLpReq.tempat_lahir = edt_tempat_lahir_saksi_single.text.toString()
        saksiLpReq.tanggal_lahir = edt_tanggal_lahir_saksi_single.text.toString()
        saksiLpReq.pekerjaan = edt_pekerjaan_saksi_single.text.toString()
        saksiLpReq.alamat = edt_alamat_saksi_single.text.toString()
        val id: Int = rg_korban_saksi.checkedRadioButtonId
        if (id != -1) {
            val radio: RadioButton = findViewById(id)
            if (radio.text == "Korban") {
                saksiLpReq.isKorban = 1
            } else {
                saksiLpReq.isKorban = 0

            }
        }

        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, size, size)
        btn_save_add_saksi.showProgress {
            progressColor = Color.WHITE
        }
        btn_save_add_saksi.showDrawable(animatedDrawable) {
            buttonTextRes = R.string.data_saved
            textMarginRes = R.dimen.space_10dp
        }

        Handler(Looper.getMainLooper()).postDelayed({
            btn_save_add_saksi.hideDrawable(R.string.save)
            Log.e("add_saksi", "$saksiLpReq")
        }, 3000)
    }
}