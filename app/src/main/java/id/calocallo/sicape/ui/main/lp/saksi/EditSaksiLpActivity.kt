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
import id.calocallo.sicape.network.response.LpSaksiResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_saksi_lp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditSaksiLpActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var saksiReq = SaksiReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_saksi_lp)
        sessionManager1 = SessionManager1(this)
        val namaJenis = intent.extras?.getString("NAMA_JENIS")
        val saksi = intent.extras?.getParcelable<LpSaksiResp>("SAKSI_EDIT")
        setupActionBarWithBackButton(toolbar)
        when(namaJenis){
            "pidana" -> supportActionBar?.title = "Edit Data Laporan Pidana"
            "disiplin" -> supportActionBar?.title = "Edit Data Laporan Disiplin"
            "kode_etik" -> supportActionBar?.title = "Edit Data Laporan Kode Etik"
        }

        getViewSaksi(saksi)
        btn_save_edit_saksi_lp.attachTextChangeAnimator()
        bindProgressButton(btn_save_edit_saksi_lp)
        btn_save_edit_saksi_lp.setOnClickListener {
            saksiReq.nama_saksi = edt_saksi_edit.text.toString()
            saksiReq.tempat_lahir = edt_tempat_lahir_saksi_edit.text.toString()
            saksiReq.tanggal_lahir = edt_tanggal_lahir_saksi_edit.text.toString()
            saksiReq.pekerjaan = edt_pekerjaan_saksi_edit.text.toString()
            saksiReq.alamat = edt_alamat_saksi_edit.text.toString()
            val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
            val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
            animatedDrawable.setBounds(0, 0, size, size)

            btn_save_edit_saksi_lp.showProgress {
                progressColor = Color.WHITE
            }
            btn_save_edit_saksi_lp.showDrawable(animatedDrawable) {
                buttonTextRes = R.string.data_updated
                textMarginRes = R.dimen.space_10dp
            }
            Handler(Looper.getMainLooper()).postDelayed({
                btn_save_edit_saksi_lp.hideDrawable(R.string.save)
                Log.e("edit_saksi", "$saksiReq")
            }, 3000)
        }

        btn_delete_edit_saksi_lp.setOnClickListener {
            alert ("Yakin Hapus Data?"){
                positiveButton("Iya"){
                    deleteSaksiSingle()
                }
                negativeButton("Tidak"){}
            }.show()
        }
    }

    private fun deleteSaksiSingle() {

    }

    private fun getViewSaksi(saksi: LpSaksiResp?) {
        edt_saksi_edit.setText(saksi?.nama)
        edt_tempat_lahir_saksi_edit.setText(saksi?.tempat_lahir)
        edt_tanggal_lahir_saksi_edit.setText(saksi?.tanggal_lahir)
        edt_pekerjaan_saksi_edit.setText(saksi?.pekerjaan)
        edt_alamat_saksi_edit.setText(saksi?.alamat)
        if(saksi?.is_korban ==1){
            rb_korban_saksi.isChecked =true
            saksiReq.isKorban=1
        }else if(saksi?.is_korban == 0){
            rb_saksi.isChecked =true
            saksiReq.isKorban = 0
        }

        rg_korban_saksi_edit.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            if(radio.text.toString().toLowerCase() == "korban"){
                radio.isChecked = true
                saksiReq.isKorban = 1
                //set pelapor =>saksi
            }else{
                radio.isChecked = true
                saksiReq.isKorban = 0
                //set pelapor =>korban
            }
        }
    }
}