package id.calocallo.sicape.ui.main.lhp.edit.terlapor

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ListTerlapor
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.request.KetTerlaporReq
import id.calocallo.sicape.network.response.KetTerlaporLhpResp
import id.calocallo.sicape.ui.main.choose.ChoosePersonelActivity
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_terlapor_lhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditTerlaporLhpActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var terlaporReq = KetTerlaporReq()
    private var idPersonelTerlapor: Int? = null
    private var namaPersonelTerlapor: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_terlapor_lhp)
        sessionManager = SessionManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Terlapor"
        val hak = sessionManager.fetchHakAkses()
        if (hak == "operator") {
            btn_save_terlapor_edit.gone()
            btn_delete_terlapor_edit.gone()
        }
        val terlapor = intent.extras?.getParcelable<KetTerlaporLhpResp>(EDIT_TERLAPOR)

        getViewTerlapor(terlapor)
        btn_save_terlapor_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_terlapor_edit)
        btn_save_terlapor_edit.setOnClickListener {
            updateTerlapor(terlapor)

        }

        btn_delete_terlapor_edit.setOnClickListener {
            alert("Yakin Hapus Data?") {
                positiveButton("Iya") {
                    deleteTerlapor(terlapor)
                }
                negativeButton("Tidak") {}
            }.show()
        }

/*set button for add personel terlapor*/
        bt_choose_personel_terlapor_edit.setOnClickListener {
            val intent = Intent(this, ChoosePersonelActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivityForResult(intent, REQ_EDIT_TERLAPOR)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val personelTerlapor = data?.getParcelableExtra<PersonelModel>("ID_PERSONEL")
        if (resultCode == Activity.RESULT_OK && requestCode == REQ_EDIT_TERLAPOR) {
            idPersonelTerlapor = personelTerlapor?.id
            namaPersonelTerlapor = personelTerlapor?.nama
            txt_nama_terlapor_edit.text = personelTerlapor?.nama
            txt_pangkat_terlapor_edit.text = personelTerlapor?.pangkat
            txt_nrp_terlapor_edit.text = personelTerlapor?.nrp
            txt_jabatan_terlapor_edit.text = personelTerlapor?.jabatan
            txt_kesatuan_terlapor_edit.text = personelTerlapor?.satuan_kerja?.kesatuan
        }
    }

    private fun getViewTerlapor(terlapor: KetTerlaporLhpResp?) {
        edt_ket_terlapor_edit.setText(terlapor?.isi_keterangan_terlapor)
        txt_nama_terlapor_edit.text = terlapor?.nama
        txt_pangkat_terlapor_edit.text = terlapor?.pangkat
        txt_nrp_terlapor_edit.text = terlapor?.nrp
        txt_jabatan_terlapor_edit.text = terlapor?.jabatan
        txt_kesatuan_terlapor_edit.text = terlapor?.kesatuan
    }

    private fun updateTerlapor(terlapor: KetTerlaporLhpResp?) {
        terlaporReq.nama_pesonel = namaPersonelTerlapor
        if(idPersonelTerlapor == null){
            terlaporReq.id_personel = terlapor?.id_personel
        }else{
            terlaporReq.id_personel = idPersonelTerlapor
        }
        terlaporReq.isi_keterangan_terlapor = edt_ket_terlapor_edit.text.toString()
        Log.e("editTlrepo", "$terlaporReq")
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        btn_save_terlapor_edit.showProgress {
            progressColor = Color.WHITE
        }
        btn_save_terlapor_edit.showDrawable(animatedDrawable) {
            buttonTextRes = R.string.data_updated
            textMarginRes = R.dimen.space_10dp

        }
        Handler(Looper.getMainLooper()).postDelayed({
            btn_save_terlapor_edit.hideDrawable(R.string.save)
        }, 3000)
    }

    private fun deleteTerlapor(terlapor: KetTerlaporLhpResp?) {

    }

    companion object {
        const val EDIT_TERLAPOR = "EDIT_TERLAPOR"
        const val REQ_EDIT_TERLAPOR = 111
    }
}