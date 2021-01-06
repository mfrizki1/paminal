package id.calocallo.sicape.ui.main.addpersonal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import id.calocallo.sicape.R
import id.calocallo.sicape.model.SignalementModel
import id.calocallo.sicape.ui.main.addpersonal.relasi.AddRelasiActivity
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_signalement.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddSignalementActivity : BaseActivity() {
    private lateinit var signalementReq: SignalementModel
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_signalement)
        sessionManager = SessionManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Signalement"
        signalementReq = SignalementModel()

        btn_next_signalement.setOnClickListener {
            signalementReq.cacat = edt_cacat_ciri.text.toString()
            signalementReq.kelemahan = edt_kelemahan.text.toString()
            signalementReq.keluarga_dekat = edt_keluarga_dekat.text.toString()
            signalementReq.kesenangan = edt_kesenangan.text.toString()
            signalementReq.lain_lainnya = edt_lainnya.text.toString()
            signalementReq.mata = edt_mata.text.toString()
            signalementReq.muka = edt_muka.text.toString()
            signalementReq.rambut = edt_rambut.text.toString()
            signalementReq.sidik_jari = edt_sidik_jari.text.toString()
            signalementReq.tinggi = edt_tinggi.text.toString()
            signalementReq.yang_mempengaruhi =edt_dapat_dipengaruhi.text.toString()

            sessionManager.setSignalement(signalementReq)
            Log.e("signalement Size", "${sessionManager.getSignalement()}")
            startActivity(Intent(this, AddRelasiActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }
}