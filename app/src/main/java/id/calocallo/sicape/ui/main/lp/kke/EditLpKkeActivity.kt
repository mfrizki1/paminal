package id.calocallo.sicape.ui.main.lp.kke

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
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.request.LpKodeEtikReq
import id.calocallo.sicape.network.response.LpKkeResp
import id.calocallo.sicape.ui.main.choose.ChoosePersonelActivity
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_lp_kke.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditLpKkeActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var lpKodeEtikReq = LpKodeEtikReq()
    private var changedIdPelapor: Int? = null
    private var changedIdTerlapor: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_lp_kke)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Laporan Polisi Kode Etik"
        val kke = intent.extras?.getParcelable<LpKkeResp>(EDIT_KKE)
        getViewKKeEdit(kke)
        sessionManager = SessionManager(this)
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

    }

    private fun updateKke(
        kke: LpKkeResp?,
        changedIdTerlapor: Int?,
        changedIdPelapor: Int?
    ) {
        lpKodeEtikReq.no_lp = edt_no_lp_kke_edit.text.toString()
        lpKodeEtikReq.isi_laporan = edt_isi_laporan_kke_edit.text.toString()
        lpKodeEtikReq.alat_bukti = edt_alat_bukti_kke_edit.text.toString()
        lpKodeEtikReq.kota_buat_laporan = edt_kota_buat_edit_lp.text.toString()
        lpKodeEtikReq.tanggal_buat_laporan = edt_tgl_buat_edit.text.toString()
        lpKodeEtikReq.nama_yang_mengetahui = edt_nama_pimpinan_bidang_edit.text.toString()
        lpKodeEtikReq.pangkat_yang_mengetahui = edt_pangkat_pimpinan_bidang_edit.text.toString()
        lpKodeEtikReq.nrp_yang_mengetahui = edt_nrp_pimpinan_bidang_edit.text.toString()
        lpKodeEtikReq.jabatan_yang_mengetahui = edt_jabatan_pimpinan_bidang_edit.text.toString()
        lpKodeEtikReq.id_personel_operator = sessionManager.fetchUser()?.id
        lpKodeEtikReq.id_personel_terlapor = kke?.id_personel_terlapor
        lpKodeEtikReq.id_personel_pelapor = kke?.id_personel_pelapor
        lpKodeEtikReq.kategori = sessionManager.getJenisLP()

        if(changedIdTerlapor == null){
            lpKodeEtikReq.id_personel_terlapor = kke?.id_personel_terlapor
        }else{
            lpKodeEtikReq.id_personel_terlapor = changedIdTerlapor
        }
        if(changedIdPelapor == null){
            lpKodeEtikReq.id_personel_pelapor = kke?.id_personel_pelapor
        }else{
            lpKodeEtikReq.id_personel_pelapor = changedIdPelapor
        }
        Log.e("edit_kke","$lpKodeEtikReq")
    }

    private fun getViewKKeEdit(kke: LpKkeResp?) {
        //edittext
        edt_no_lp_kke_edit.setText(kke?.no_lp)
        edt_isi_laporan_kke_edit.setText(kke?.isi_laporan)
        edt_alat_bukti_kke_edit.setText(kke?.alat_bukti)
        edt_kota_buat_edit_lp.setText(kke?.kota_buat_laporan)
        edt_tgl_buat_edit.setText(kke?.tanggal_buat_laporan)
        edt_nama_pimpinan_bidang_edit.setText(kke?.nama_yang_mengetahui)
        edt_pangkat_pimpinan_bidang_edit.setText(kke?.pangkat_yang_mengetahui)
        edt_nrp_pimpinan_bidang_edit.setText(kke?.nrp_yang_mengetahui)
        edt_jabatan_pimpinan_bidang_edit.setText(kke?.jabatan_yang_mengetahui)

        //terlapor
        btn_choose_personel_terlapor_kke_edit.setOnClickListener {
            val intent = Intent(this, ChoosePersonelActivity::class.java)
            startActivityForResult(intent, REQ_TERLAPOR)
        }
        txt_nama_terlapor_lp_edit.text = "Nama : ${kke?.id_personel_terlapor}"
        txt_pangkat_terlapor_lp_edit.text = "Pangkat : ${kke?.id_personel_terlapor}"
        txt_nrp_terlapor_lp_edit.text = "NRP : ${kke?.id_personel_terlapor}"
        txt_jabatan_terlapor_lp_edit.text = "Jabatan : ${kke?.id_personel_terlapor}"
        txt_kesatuan_terlapor_lp_edit.text = "Kesatuan : ${kke?.id_personel_terlapor}"

        //pelapor
        btn_choose_personel_pelapor_kke_edit.setOnClickListener {
            val intent = Intent(this, ChoosePersonelActivity::class.java)
            startActivityForResult(intent, REQ_PELAPOR)
        }
        txt_nama_pelapor_kke_lp_edit.text = "Nama : ${kke?.id_personel_pelapor}"
        txt_pangkat_pelapor_kke_lp_edit.text = "Pangkat : ${kke?.id_personel_pelapor}"
        txt_nrp_pelapor_kke_lp_edit.text = "NRP : ${kke?.id_personel_pelapor}"
        txt_jabatan_pelapor_kke_lp_edit.text = "Jabatan : ${kke?.id_personel_pelapor}"
        txt_kesatuan_pelapor_kke_lp_edit.text = "Kesatuan : ${kke?.id_personel_pelapor}"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val personel = data?.extras?.getParcelable<PersonelModel>("ID_PERSONEL")
        when (resultCode) {
            Activity.RESULT_OK -> {
                when (requestCode) {
                    REQ_PELAPOR -> {
                        //req set id
                        changedIdPelapor = personel?.id

                        txt_nama_pelapor_kke_lp_edit.text = "Nama : ${personel?.nama}"
                        txt_pangkat_pelapor_kke_lp_edit.text = "Pangkat : ${personel?.pangkat}"
                        txt_nrp_pelapor_kke_lp_edit.text = "NRP : ${personel?.nrp}"
                        txt_jabatan_pelapor_kke_lp_edit.text = "Jabatan : ${personel?.jabatan}"
                        txt_kesatuan_pelapor_kke_lp_edit.text =
                            "Kesatuan : ${personel?.satuan_kerja?.kesatuan}"
                    }
                    REQ_TERLAPOR -> {
                        //req set id
                        changedIdTerlapor = personel?.id

                        txt_nama_terlapor_lp_edit.text = "Nama : ${personel?.nama}"
                        txt_pangkat_terlapor_lp_edit.text = "Pangkat : ${personel?.pangkat}"
                        txt_nrp_terlapor_lp_edit.text = "NRP : ${personel?.nrp}"
                        txt_jabatan_terlapor_lp_edit.text = "Jabatan : ${personel?.jabatan}"
                        txt_kesatuan_terlapor_lp_edit.text =
                            "Kesatuan : ${personel?.satuan_kerja?.kesatuan}"

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