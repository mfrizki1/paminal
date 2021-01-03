package id.calocallo.sicape.ui.main.lp.disiplin

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
import id.calocallo.sicape.network.request.LpDisiplinReq
import id.calocallo.sicape.network.response.LpDisiplinResp
import id.calocallo.sicape.ui.main.choose.ChoosePersonelActivity
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_lp_disiplin.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditLpDisiplinActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var lpDisiplinReq = LpDisiplinReq()
    private var changedIdPelapor: Int? = null
    private var changedIdTerlapor: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_lp_disiplin)
        setupActionBarWithBackButton(toolbar)
        sessionManager = SessionManager(this)
        supportActionBar?.title = "Edit Data Laporan Polisi Disiplin"
        val disiplin = intent.extras?.getParcelable<LpDisiplinResp>(EDIT_DISIPLIN)
        getViewEditDisipilin(disiplin)
        //set terlapor
        btn_choose_personel_terlapor_disiplin_edit.setOnClickListener {
            val intent = Intent(this, ChoosePersonelActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivityForResult(intent, REQ_TERLAPOR)
        }

        //set terlapor
        btn_choose_personel_pelapor_lp_edit_disiplin.setOnClickListener {
            val intent = Intent(this, ChoosePersonelActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivityForResult(intent, REQ_PELAPOR)
        }

        //update
        btn_save_edit_lp_disiplin.attachTextChangeAnimator()
        bindProgressButton(btn_save_edit_lp_disiplin)
        btn_save_edit_lp_disiplin.setOnClickListener {
            val animated = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
            val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
            animated.setBounds(0, 0, size, size)
            btn_save_edit_lp_disiplin.showProgress {
                progressColor = Color.WHITE
            }
            btn_save_edit_lp_disiplin.showDrawable(animated) {
                textMarginRes = R.dimen.space_10dp
                buttonTextRes = R.string.data_updated
            }

            Handler(Looper.getMainLooper()).postDelayed({
                btn_save_edit_lp_disiplin.hideDrawable(R.string.save)
            }, 3000)

            updateLpDisiplin(disiplin, changedIdTerlapor, changedIdPelapor)
        }


    }

    private fun updateLpDisiplin(
        disiplin: LpDisiplinResp?,
        changedIdTerlapor: Int?,
        changedIdPelapor: Int?
    ) {
        lpDisiplinReq.no_lp = edt_no_lp_disiplin_edit.text.toString()
        lpDisiplinReq.macam_pelanggaran = edt_macam_pelanggaran_disiplin_edit.text.toString()
//        txt_nama_terlapor_lp_disiplin_edit.text.toString()
//        txt_pangkat_terlapor_lp_disiplin_edit.text.toString()
//        txt_nrp_terlapor_lp_disiplin_edit.text.toString()
//        txt_jabatan_terlapor_lp_disiplin_edit.text.toString()
//        txt_kesatuan_terlapor_lp_disiplin_edit.text.toString()

        lpDisiplinReq.keterangan_pelapor = edt_ket_pelapor_disiplin_edit.text.toString()

//        lpDisiplinReq. = txt_nama_pelapor_disiplin_lp_add.text.toString()
//        lpDisiplinReq. = txt_pangkat_pelapor_disiplin_lp_add.text.toString()
//        lpDisiplinReq. = txt_nrp_pelapor_disiplin_lp_add.text.toString()
//        lpDisiplinReq. = txt_jabatan_pelapor_disiplin_lp_add.text.toString()
//        lpDisiplinReq. = txt_kesatuan_pelapor_disiplin_lp_add.text.toString()

        lpDisiplinReq.kronologis_dari_pelapor = edt_kronologis_pelapor_disiplin_edit.text.toString()
        lpDisiplinReq.rincian_pelanggaran_disiplin =
            edt_rincian_pelanggaran_disiplin.text.toString()

        lpDisiplinReq.kota_buat_laporan = edt_kota_buat_edit_lp_disiplin.text.toString()
        lpDisiplinReq.tanggal_buat_laporan = edt_tgl_buat_edit_lp_disiplin.text.toString()
        lpDisiplinReq.nama_yang_mengetahui = edt_nama_pimpinan_bidang_edit_disiplin.text.toString()
        lpDisiplinReq.pangkat_yang_mengetahui =
            edt_pangkat_pimpinan_bidang_edit_disiplin.text.toString()
        lpDisiplinReq.nrp_yang_mengetahui = edt_nrp_pimpinan_bidang_edit_disiplin.text.toString()
        lpDisiplinReq.jabatan_yang_mengetahui =
            edt_jabatan_pimpinan_bidang_edit_disiplin.text.toString()
        lpDisiplinReq.kategori = sessionManager.getJenisLP()
        lpDisiplinReq.id_personel_operator = sessionManager.fetchUser()?.id
        lpDisiplinReq.id_personel_pelapor = disiplin?.id_personel_pelapor
        lpDisiplinReq.id_personel_terlapor = disiplin?.id_personel_terlapor
//        lpDisiplinReq.id_personel_pelapor = this.changedIdPelapor

        if(changedIdPelapor == null){
            lpDisiplinReq.id_personel_pelapor = disiplin?.id_personel_pelapor
        }else{
            lpDisiplinReq.id_personel_pelapor = changedIdPelapor
        }
        if(changedIdTerlapor == null){
            lpDisiplinReq.id_personel_terlapor = disiplin?.id_personel_terlapor
        }else{
            lpDisiplinReq.id_personel_terlapor = changedIdTerlapor
        }

        Log.e("is same", "${lpDisiplinReq.id_personel_pelapor == disiplin?.id_personel_pelapor}")
//        if(lpDisiplinReq.id_personel_pelapor != disiplin?.id_personel_pelapor) {
//            lpDisiplinReq.id_personel_pelapor = this.changedIdPelapor
//        }
        Log.e("edit Disiplin", "$lpDisiplinReq")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val personel = data?.getParcelableExtra<PersonelModel>("ID_PERSONEL")
        when (resultCode) {
            Activity.RESULT_OK -> {
                when (requestCode) {
                    REQ_PELAPOR -> {
                        changedIdPelapor = personel?.id
                        txt_nama_pelapor_disiplin_lp_add.text = personel?.nama
                        txt_pangkat_pelapor_disiplin_lp_add.text = personel?.pangkat
                        txt_nrp_pelapor_disiplin_lp_add.text = personel?.nrp
                        txt_jabatan_pelapor_disiplin_lp_add.text = personel?.jabatan
                        txt_kesatuan_pelapor_disiplin_lp_add.text = personel?.satuan_kerja?.kesatuan
                        Log.e("id pelapor", "$changedIdPelapor")

                    }
                    REQ_TERLAPOR -> {
                        changedIdTerlapor = personel?.id
                        txt_nama_terlapor_lp_disiplin_edit.text = personel?.nama
                        txt_pangkat_terlapor_lp_disiplin_edit.text = personel?.pangkat
                        txt_nrp_terlapor_lp_disiplin_edit.text = personel?.nrp
                        txt_jabatan_terlapor_lp_disiplin_edit.text = personel?.jabatan
                        txt_kesatuan_terlapor_lp_disiplin_edit.text =
                            personel?.satuan_kerja?.kesatuan
                        Log.e("id terlapor", "$changedIdTerlapor")

                    }
                }
            }
        }
    }

    private fun getViewEditDisipilin(disiplin: LpDisiplinResp?) {
        edt_no_lp_disiplin_edit.setText(disiplin?.no_lp)
        edt_macam_pelanggaran_disiplin_edit.setText(disiplin?.macam_pelanggaran)
        txt_nama_terlapor_lp_disiplin_edit.text = disiplin?.id_personel_terlapor.toString()
        txt_pangkat_terlapor_lp_disiplin_edit.text = disiplin?.id_personel_terlapor.toString()
        txt_nrp_terlapor_lp_disiplin_edit.text = disiplin?.id_personel_terlapor.toString()
        txt_jabatan_terlapor_lp_disiplin_edit.text = disiplin?.id_personel_terlapor.toString()
        txt_kesatuan_terlapor_lp_disiplin_edit.text = disiplin?.id_personel_terlapor.toString()

        edt_ket_pelapor_disiplin_edit.setText(disiplin?.keterangan_terlapor)

        txt_nama_pelapor_disiplin_lp_add.text = disiplin?.id_personel_pelapor.toString()
        txt_pangkat_pelapor_disiplin_lp_add.text = disiplin?.id_personel_pelapor.toString()
        txt_nrp_pelapor_disiplin_lp_add.text = disiplin?.id_personel_pelapor.toString()
        txt_jabatan_pelapor_disiplin_lp_add.text = disiplin?.id_personel_pelapor.toString()
        txt_kesatuan_pelapor_disiplin_lp_add.text = disiplin?.id_personel_pelapor.toString()

        edt_kronologis_pelapor_disiplin_edit.setText(disiplin?.kronologis_dari_pelapor)
        edt_rincian_pelanggaran_disiplin.setText(disiplin?.rincian_pelanggaran_disiplin)

        edt_kota_buat_edit_lp_disiplin.setText(disiplin?.kota_buat_laporan)
        edt_tgl_buat_edit_lp_disiplin.setText(disiplin?.tanggal_buat_laporan)
        edt_nama_pimpinan_bidang_edit_disiplin.setText(disiplin?.nama_yang_mengetahui)
        edt_pangkat_pimpinan_bidang_edit_disiplin.setText(disiplin?.pangkat_yang_mengetahui)
        edt_nrp_pimpinan_bidang_edit_disiplin.setText(disiplin?.nrp_yang_mengetahui)
        edt_jabatan_pimpinan_bidang_edit_disiplin.setText(disiplin?.jabatan_yang_mengetahui)


    }

    companion object {
        const val EDIT_DISIPLIN = "EDIT_DISIPLIN"
        const val REQ_PELAPOR = 202
        const val REQ_TERLAPOR = 101
    }
}