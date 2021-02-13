package id.calocallo.sicape.ui.main.lp.kke

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
import id.calocallo.sicape.model.AllPersonelModel
import id.calocallo.sicape.network.request.EditLpKkeReq
import id.calocallo.sicape.network.response.LpKkeResp
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_lp_kke.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditLpKkeActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var editLpKkeReq = EditLpKkeReq()
    private var changedIdPelapor: Int? = null
    private var changedIdTerlapor: Int? = null
    private var namaSatker: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_lp_kke)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Laporan Polisi Kode Etik"
        val kke = intent.extras?.getParcelable<LpKkeResp>(EDIT_KKE)
        getViewKKeEdit(kke)
        sessionManager1 = SessionManager1(this)
        bindProgressButton(btn_save_edit_lp_kke)
        btn_save_edit_lp_kke.attachTextChangeAnimator()
        btn_save_edit_lp_kke.setOnClickListener {
            val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
            val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
            animatedDrawable.setBounds(0, 0, size, size)
            updateKke(kke, changedIdTerlapor, changedIdPelapor)
            btn_save_edit_lp_kke.showProgress {
                progressColor = Color.WHITE
            }
            btn_save_edit_lp_kke.showDrawable(animatedDrawable) {
                textMarginRes = R.dimen.space_10dp
                buttonTextRes = R.string.data_updated
            }
            Handler(Looper.getMainLooper()).postDelayed({
                btn_save_edit_lp_kke.hideDrawable(R.string.save)
            }, 3000)
        }

        spinner_kesatuan_lp_kke_edit.setAdapter(
            ArrayAdapter(
                this,
                R.layout.item_spinner,
                resources.getStringArray(R.array.satker)
            )
        )
        spinner_kesatuan_lp_kke_edit.setOnItemClickListener { parent, view, position, id ->
            namaSatker = parent.getItemAtPosition(position) as String?
        }

    }

    private fun updateKke(kke: LpKkeResp?, changedIdTerlapor: Int?, changedIdPelapor: Int?) {
        editLpKkeReq.no_lp = edt_no_lp_kke_edit.text.toString()
        editLpKkeReq.isi_laporan = edt_isi_laporan_kke_edit.text.toString()
        editLpKkeReq.alat_bukti = edt_alat_bukti_kke_edit.text.toString()
        editLpKkeReq.kota_buat_laporan = edt_kota_buat_edit_lp.text.toString()
        editLpKkeReq.tanggal_buat_laporan = edt_tgl_buat_edit.text.toString()
        editLpKkeReq.nama_yang_mengetahui = edt_nama_pimpinan_bidang_edit.text.toString()
        editLpKkeReq.pangkat_yang_mengetahui = edt_pangkat_pimpinan_bidang_edit.text.toString()
        editLpKkeReq.nrp_yang_mengetahui = edt_nrp_pimpinan_bidang_edit.text.toString()
        editLpKkeReq.jabatan_yang_mengetahui = edt_jabatan_pimpinan_bidang_edit.text.toString()
        editLpKkeReq.kesatuan_yang_mengetahui = namaSatker
//        lpKodeEtikReq.id_personel_operator = sessionManager.fetchUser()?.id
        editLpKkeReq.id_personel_terlapor = kke?.personel_terlapor?.id
        editLpKkeReq.id_personel_pelapor = kke?.personel_pelapor?.id
        editLpKkeReq.uraian_pelanggaran = sessionManager1.getJenisLP()

        if (changedIdTerlapor == null) {
            editLpKkeReq.id_personel_terlapor = kke?.personel_terlapor?.id
        } else {
            editLpKkeReq.id_personel_terlapor = changedIdTerlapor
        }
        if (changedIdPelapor == null) {
            editLpKkeReq.id_personel_pelapor = kke?.personel_pelapor?.id
        } else {
            editLpKkeReq.id_personel_pelapor = changedIdPelapor
        }
        Log.e("edit_kke", "$editLpKkeReq")
    }

    private fun getViewKKeEdit(kke: LpKkeResp?) {
        //edittext
        edt_no_lp_kke_edit.setText(kke?.no_lp)
        edt_isi_laporan_kke_edit.setText(kke?.isi_laporan)
        edt_alat_bukti_kke_edit.setText(kke?.alat_bukti)
        edt_kota_buat_edit_lp.setText(kke?.kota_buat_laporan)
        edt_tgl_buat_edit.setText(kke?.tanggal_buat_laporan)
        edt_nama_pimpinan_bidang_edit.setText(kke?.nama_yang_mengetahui)
        edt_pangkat_pimpinan_bidang_edit.setText(kke?.pangkat_yang_mengetahui.toString().toUpperCase())
        edt_nrp_pimpinan_bidang_edit.setText(kke?.nrp_yang_mengetahui)
        edt_jabatan_pimpinan_bidang_edit.setText(kke?.jabatan_yang_mengetahui)
        spinner_kesatuan_lp_kke_edit.setText(kke?.kesatuan_yang_mengetahui.toString().toUpperCase())
        namaSatker = kke?.kesatuan_yang_mengetahui
        //terlapor
        btn_choose_personel_terlapor_kke_edit.setOnClickListener {
            val intent = Intent(this, KatPersonelActivity::class.java)
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, REQ_TERLAPOR)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        txt_nama_terlapor_lp_edit.text = "Nama : ${kke?.personel_terlapor?.nama}"
        txt_pangkat_terlapor_lp_edit.text = "Pangkat : ${kke?.personel_terlapor?.pangkat.toString().toUpperCase()}"
        txt_nrp_terlapor_lp_edit.text = "NRP : ${kke?.personel_terlapor?.nrp}"
        txt_jabatan_terlapor_lp_edit.text = "Jabatan : ${kke?.personel_terlapor?.jabatan}"
        txt_kesatuan_terlapor_lp_edit.text = "Kesatuan : ${kke?.personel_terlapor?.kesatuan.toString().toUpperCase()}"

        //pelapor
        btn_choose_personel_pelapor_kke_edit.setOnClickListener {
            val intent = Intent(this, KatPersonelActivity::class.java)
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, REQ_PELAPOR)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        txt_nama_pelapor_kke_lp_edit.text = "Nama : ${kke?.personel_pelapor?.nama}"
        txt_pangkat_pelapor_kke_lp_edit.text = "Pangkat : ${kke?.personel_pelapor?.pangkat.toString().toUpperCase()}"
        txt_nrp_pelapor_kke_lp_edit.text = "NRP : ${kke?.personel_pelapor?.nrp}"
        txt_jabatan_pelapor_kke_lp_edit.text = "Jabatan : ${kke?.personel_pelapor?.jabatan}"
        txt_kesatuan_pelapor_kke_lp_edit.text = "Kesatuan : ${kke?.personel_pelapor?.kesatuan.toString().toUpperCase()}"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val personel = data?.extras?.getParcelable<AllPersonelModel>("ID_PERSONEL")
        when (resultCode) {
            Activity.RESULT_OK -> {
                when (requestCode) {
                    REQ_PELAPOR -> {
                        //req set id
                        changedIdPelapor = personel?.id

                        txt_nama_pelapor_kke_lp_edit.text = "Nama : ${personel?.nama}"
                        txt_pangkat_pelapor_kke_lp_edit.text = "Pangkat : ${personel?.pangkat.toString().toUpperCase()}"
                        txt_nrp_pelapor_kke_lp_edit.text = "NRP : ${personel?.nrp}"
                        txt_jabatan_pelapor_kke_lp_edit.text = "Jabatan : ${personel?.jabatan}"
                        txt_kesatuan_pelapor_kke_lp_edit.text =
                            "Kesatuan : ${personel?.satuan_kerja?.kesatuan.toString().toUpperCase()}"
                    }
                    REQ_TERLAPOR -> {
                        //req set id
                        changedIdTerlapor = personel?.id

                        txt_nama_terlapor_lp_edit.text = "Nama : ${personel?.nama}"
                        txt_pangkat_terlapor_lp_edit.text = "Pangkat : ${personel?.pangkat.toString().toUpperCase()}"
                        txt_nrp_terlapor_lp_edit.text = "NRP : ${personel?.nrp}"
                        txt_jabatan_terlapor_lp_edit.text = "Jabatan : ${personel?.jabatan}"
                        txt_kesatuan_terlapor_lp_edit.text =
                            "Kesatuan : ${personel?.satuan_kerja?.kesatuan.toString().toUpperCase()}"

                    }
                }
            }
        }
    }

    companion object {
        const val EDIT_KKE = "EDIT_KKE"
        const val REQ_TERLAPOR = 202
        const val REQ_PELAPOR = 102
    }
}