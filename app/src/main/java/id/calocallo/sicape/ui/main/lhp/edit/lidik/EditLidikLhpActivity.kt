package id.calocallo.sicape.ui.main.lhp.edit.lidik

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ListLidik
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.request.PersonelPenyelidikReq
import id.calocallo.sicape.network.response.PersonelPenyelidikResp
import id.calocallo.sicape.ui.main.choose.ChoosePersonelActivity
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_lidik_lhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditLidikLhpActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var lidikReq = PersonelPenyelidikReq()
    private var statusLidik: String? = null
    private var idPersonelLidik: Int? = null
    private var currIsKetua: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_lidik_lhp)
        sessionManager = SessionManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Personel Penyelidik"
        val lhp = intent.extras?.getParcelable<PersonelPenyelidikResp>(EDIT_LIDIK)

        val hak = sessionManager.fetchHakAkses()
        if (hak == "operator") {
            btn_delete_lidik_edit.gone()
            btn_save_lidik_edit.gone()
        }
        btn_save_lidik_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_lidik_edit)
        btn_save_lidik_edit.setOnClickListener {
            updateLidik(lhp)
        }
        btn_delete_lidik_edit.setOnClickListener {
            alert("Yakin Hapus Data?") {
                positiveButton("Iya") {
                    deleteLidik(lhp)
                }
                negativeButton("Tidak")
            }.show()
        }
        getViewLidik(lhp)
        btn_choose_personel_lidik_edit.setOnClickListener {
            val intent = Intent(this, ChoosePersonelActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivityForResult(intent, REQ_PERSONEL_LIDIK)
        }
    }

    companion object {

        const val EDIT_LIDIK = "EDIT_LIDIK"
        const val REQ_PERSONEL_LIDIK = 123
    }

    private fun getViewLidik(lhp: PersonelPenyelidikResp?) {
        when (lhp?.is_ketua) {
            1 -> {
                rb_ketua_lidik_edit.isChecked = true
                currIsKetua = 1
            }
//                spinner_status_lidik_edit.setText("Ketua Tim")
            0 -> {
                rb_anggota_lidik_edit.isChecked = true
                currIsKetua = 0
            }
//            spinner_status_lidik_edit.setText("Anggota")
        }

        txt_nama_lidik_edit.text = lhp?.nama
        txt_pangkat_lidik_edit.text = lhp?.pangkat
        txt_nrp_lidik_edit.text = lhp?.nrp
        txt_jabatan_lidik_edit.text = lhp?.jabatan
        txt_kesatuan_lidik_edit.text = lhp?.kesatuan

//        edt_nama_lidik_edit.setText(lhp?.nama)
//        edt_pangkat_lidik_edit.setText(lhp?.pangkat)
//        edt_nrp_lidik_edit.setText(lhp?.nrp)

//        val item = listOf("Ketua Tim", "Anggota")
//        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
//        spinner_status_lidik_edit.setAdapter(adapter)
//        spinner_status_lidik_edit.setOnItemClickListener { parent, view, position, id ->
//            when (position) {
//                0 -> statusLidik = "ketua_tim"
//                1 -> statusLidik = "anggota"
//            }
//        }
    }

    private fun updateLidik(lhp: PersonelPenyelidikResp?) {
        btn_save_lidik_edit.showProgress {
            progressColor = Color.WHITE
        }
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, size, size)
//        lidikReq.nama = edt_nama_lidik_edit.text.toString()
//        lidik/Req.pangkat_lidik = edt_pangkat_lidik_edit.text.toString()
//        lidikReq.nrp_lidik = edt_nrp_lidik_edit.text.toString()
//        lidikReq.status_penyelidik = statusLidik
        btn_save_lidik_edit.showDrawable(animatedDrawable) {
            buttonTextRes = R.string.data_updated
            textMarginRes = R.dimen.space_10dp
        }
        Handler(Looper.getMainLooper()).postDelayed({
            btn_save_lidik_edit.hideDrawable(R.string.save)
        }, 3000)
        lidikReq.id_personel = idPersonelLidik
        lidikReq.is_ketua = currIsKetua
        Log.e("editLidik", "$lidikReq")
    }

    private fun deleteLidik(lhp: PersonelPenyelidikResp?) {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val personel = data?.getParcelableExtra<PersonelModel>("ID_PERSONEL")
        if (resultCode == Activity.RESULT_OK && requestCode == REQ_PERSONEL_LIDIK) {
            idPersonelLidik = personel?.id
            txt_nama_lidik_edit.text = personel?.nama
            txt_pangkat_lidik_edit.text = personel?.pangkat
            txt_nrp_lidik_edit.text = personel?.nrp
            txt_jabatan_lidik_edit.text = personel?.jabatan
            txt_kesatuan_lidik_edit.text = personel?.satuan_kerja?.kesatuan
        }
    }


}
