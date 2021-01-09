package id.calocallo.sicape.ui.main.lhp.edit.saksi

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.request.SaksiLhpReq
import id.calocallo.sicape.network.response.SaksiLhpResp
import id.calocallo.sicape.ui.main.choose.ChoosePersonelActivity
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_saksi_lhp.*
import kotlinx.android.synthetic.main.activity_edit_saksi_lhp.txt_alamat_sipil_saksi
import kotlinx.android.synthetic.main.activity_edit_saksi_lhp.txt_jabatan_saksi_edit
import kotlinx.android.synthetic.main.activity_edit_saksi_lhp.txt_kesatuan_saksi_edit
import kotlinx.android.synthetic.main.activity_edit_saksi_lhp.txt_nama_saksi_edit
import kotlinx.android.synthetic.main.activity_edit_saksi_lhp.txt_nama_sipil_saksi
import kotlinx.android.synthetic.main.activity_edit_saksi_lhp.txt_nrp_saksi_edit
import kotlinx.android.synthetic.main.activity_edit_saksi_lhp.txt_pangkat_saksi_edit
import kotlinx.android.synthetic.main.activity_edit_saksi_lhp.txt_pekerjaan_sipil_saksi
import kotlinx.android.synthetic.main.activity_edit_saksi_lhp.txt_tanggal_lahir_sipil_saksi
import kotlinx.android.synthetic.main.activity_edit_saksi_lhp.txt_tempat_lahir_sipil_saksi
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditSaksiLhpActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var mAlerDBuilder: MaterialAlertDialogBuilder
    private lateinit var sipilAlertD: View
    private var saksiLhpReq = SaksiLhpReq()
    private var idPersonelSaksi: Int? = null
    private var tempStatusSaksi: String? = null
    private var tempNamaSipil: String? = null
    private var tempPekerjananSipil: String? = null
    private var tempTmptLhrSipil: String? = null
    private var tempTglLhrSipil: String? = null
    private var tempAlamatSipil: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_saksi_lhp)
        sessionManager = SessionManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Saksi LHP"

        val hak = sessionManager.fetchHakAkses()
        if (hak == "operator") {
            btn_save_saksi_lhp_edit.gone()
            btn_delete_saksi_lhp_edit.gone()
        }

        val saksi = intent.extras?.getParcelable<SaksiLhpResp>(EDIT_SAKSI_LHP)
        Log.e("model", "$saksi")
        getViewSaksiLhp(saksi)

        btn_save_saksi_lhp_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_saksi_lhp_edit)
        btn_save_saksi_lhp_edit.setOnClickListener {
            updateSaksiLhp(saksi)
        }

        btn_delete_saksi_lhp_edit.setOnClickListener {
            alert("Yakin Hapus Data?") {
                positiveButton("Iya") {
                    deleteSaksiLhp(saksi)
                }
                negativeButton("Tidak") {}
            }.show()
        }

        btn_choose_personel_saksi_edit.setOnClickListener {
            val intent = Intent(this, ChoosePersonelActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivityForResult(intent, REQ_SAKSI_LHP)
        }
        mAlerDBuilder = MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
        btn_sipil_saksi_edit.setOnClickListener {
            sipilAlertD = LayoutInflater.from(this)
                .inflate(R.layout.item_sipil_saksi, null, false)
            launchSipilView(saksi)
        }
    }

    private fun launchSipilView(saksi: SaksiLhpResp?) {
        val namaSipilView = sipilAlertD.findViewById<TextInputEditText>(R.id.edt_nama_sipil_saksi)
        val pekerjaanSipilView =
            sipilAlertD.findViewById<TextInputEditText>(R.id.edt_pekerjaan_sipil_saksi)
        val tempatSipilView =
            sipilAlertD.findViewById<TextInputEditText>(R.id.edt_tempat_lahir_sipil_saksi)
        val tanggalSipilView =
            sipilAlertD.findViewById<TextInputEditText>(R.id.edt_tanggal_lahir_sipil_saksi)
        val alamatSipilView =
            sipilAlertD.findViewById<TextInputEditText>(R.id.edt_alamat_sipil_saksi)
        namaSipilView.setText(saksi?.nama)
        pekerjaanSipilView.setText(saksi?.pekerjaan)
        tempatSipilView.setText(saksi?.tempat_lahir)
        tanggalSipilView.setText(saksi?.tanggal_lahir)
        alamatSipilView.setText(saksi?.alamat)
        mAlerDBuilder.setView(sipilAlertD)
            .setTitle("Tambah Data Sipil Saksi")
            .setPositiveButton("Tambah") { dialog, _ ->
                tempAlamatSipil = alamatSipilView.text.toString()
                tempTmptLhrSipil = tanggalSipilView.text.toString()
                tempTglLhrSipil = tempatSipilView.text.toString()
                tempPekerjananSipil = pekerjaanSipilView.text.toString()
                tempNamaSipil = namaSipilView.text.toString()
                txt_nama_sipil_saksi.text = "Nama : ${tempNamaSipil}"
                txt_alamat_sipil_saksi.text = "Alamat : ${tempAlamatSipil}"
                txt_pekerjaan_sipil_saksi.text = "Pekerjaan : ${tempPekerjananSipil}"
                txt_tanggal_lahir_sipil_saksi.text = "Tanggal Lahir : ${tempTglLhrSipil}"
                txt_tempat_lahir_sipil_saksi.text = "Tempat Lahir : ${tempTmptLhrSipil}"
            }
            .setNegativeButton("Batal") { dialog, _ -> }
            .show()
    }

    private fun getViewSaksiLhp(saksi: SaksiLhpResp?) {
        idPersonelSaksi = saksi?.id_personel
        if (saksi?.status_saksi == "polisi") {
            rb_polisi_saksi_edit.isChecked = true
            ll_sipil_saksi_edit.gone()
            ll_personel_saksi_edit.visible()
            txt_nama_saksi_edit.text = saksi.nama
            txt_pangkat_saksi_edit.text = saksi.pangkat.toString().toUpperCase()
            txt_nrp_saksi_edit.text = saksi.nrp
            txt_jabatan_saksi_edit.text = saksi.jabatan
            txt_kesatuan_saksi_edit.text = saksi.kesatuan.toString().toUpperCase()
            tempStatusSaksi = "polisi"
//            idPersonelSaksi = saksi.id_personel

        } else {
            rb_sipil_saksi_edit.isChecked = true
            ll_sipil_saksi_edit.visible()
            ll_personel_saksi_edit.gone()
            txt_nama_sipil_saksi.text = saksi?.nama
            txt_tempat_lahir_sipil_saksi.text = saksi?.tempat_lahir
            txt_tanggal_lahir_sipil_saksi.text = saksi?.tanggal_lahir
            txt_pekerjaan_sipil_saksi.text = saksi?.pekerjaan
            txt_alamat_sipil_saksi.text = saksi?.alamat
            tempNamaSipil = saksi?.nama
            tempTmptLhrSipil = saksi?.tempat_lahir
            tempTglLhrSipil = saksi?.tanggal_lahir
            tempPekerjananSipil = saksi?.pekerjaan
            tempAlamatSipil = saksi?.alamat
            tempStatusSaksi = "sipil"
        }

        edt_ket_saksi_edit.setText(saksi?.isi_keterangan_saksi)

        /*set for radio group and other stuff*/
        rg_saksi_edit.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            if (radio.isChecked && radio.text == "Sipil") {
                ll_sipil_saksi_edit.visible()
                ll_personel_saksi_edit.gone()
                tempStatusSaksi = "sipil"
                idPersonelSaksi = null

            } else {
                tempStatusSaksi = "polisi"
                ll_sipil_saksi_edit.gone()
                ll_personel_saksi_edit.visible()
                tempNamaSipil = null
                tempPekerjananSipil = null
                tempTmptLhrSipil = null
                tempTglLhrSipil = null
                tempAlamatSipil = null
            }
        }

    }

    private fun updateSaksiLhp(saksi: SaksiLhpResp?) {
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, size, size)
        btn_save_saksi_lhp_edit.showProgress {
            progressColor = Color.WHITE
        }


        btn_save_saksi_lhp_edit.showDrawable(animatedDrawable) {
            buttonTextRes = R.string.data_updated
            textMarginRes = R.dimen.space_10dp
        }
        Handler(Looper.getMainLooper()).postDelayed({
            btn_save_saksi_lhp_edit.hideDrawable(R.string.save)
        }, 3000)
        saksiLhpReq.status_saksi = tempStatusSaksi
        saksiLhpReq.id_personel = idPersonelSaksi
        saksiLhpReq.nama = tempNamaSipil
        saksiLhpReq.tempat_lahir = tempTmptLhrSipil
        saksiLhpReq.tanggal_lahir = tempTglLhrSipil
        saksiLhpReq.pekerjaan = tempTglLhrSipil
        saksiLhpReq.alamat = tempTglLhrSipil
        saksiLhpReq.isi_keterangan_saksi = edt_ket_saksi_edit.text.toString()
        Log.e("edit Saksi", "$saksiLhpReq")
    }

    private fun deleteSaksiLhp(saksi: SaksiLhpResp?) {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val personelSaksi = data?.getParcelableExtra<PersonelModel>("ID_PERSONEL")
        if (resultCode == Activity.RESULT_OK && requestCode == REQ_SAKSI_LHP) {
            idPersonelSaksi = personelSaksi?.id
            txt_nama_saksi_edit.text = personelSaksi?.nama
            txt_pangkat_saksi_edit.text = personelSaksi?.pangkat.toString().toUpperCase()
            txt_nrp_saksi_edit.text = personelSaksi?.nrp
            txt_jabatan_saksi_edit.text = personelSaksi?.jabatan
            txt_kesatuan_saksi_edit.text = personelSaksi?.satuan_kerja?.kesatuan.toString().toUpperCase()
        }
    }

    companion object {
        const val EDIT_SAKSI_LHP = "EDIT_SAKSI_LHP"
        const val REQ_SAKSI_LHP = 212
    }
}