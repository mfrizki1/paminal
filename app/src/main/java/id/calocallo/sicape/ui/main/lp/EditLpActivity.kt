package id.calocallo.sicape.ui.main.lp

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LpDisiplinModel
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.request.LpReqEdit
import id.calocallo.sicape.network.response.LpResp
import id.calocallo.sicape.network.response.PelanggaranResp
import id.calocallo.sicape.ui.main.choose.ChoosePersonelActivity
import id.calocallo.sicape.ui.main.pelanggaran.PickPelanggaranActivity
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_lp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditLpActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var idPersonelTerlapor: Int? = null
    private var idPersonelDilapor: Int? = null
    private var idPelanggaran: Int? = null
    private var lpReqEdit = LpReqEdit()

    companion object {
        const val EDIT_LP = "EDIT_LP"
        const val REQ_DILAPOR = 112
        const val REQ_TERLAPOR = 222
        const val REQ_PELANGGARAN = 333
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lp)
        setupActionBarWithBackButton(toolbar)

        val editLp = intent.extras?.getParcelable<LpResp>(EDIT_LP)
        when (editLp?.jenis) {
            "pidana" -> supportActionBar?.title = "Edit Data Laporan Polisi Pidana"
            "disiplin" -> supportActionBar?.title = "Edit Data Laporan Polisi Displin"
            "kode_etik" -> supportActionBar?.title = "Edit Data Laporan Polisi Kode Etik"

        }
//        supportActionBar?.title = "Edit Data Laporan Polisi"
        getViewEditLP(editLp)


//        btn_choose_pelanggaran_lp_add.setOnClickListener {
//            val intent = Intent(this, PickPelanggaranActivity::class.java)
//            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
//            startActivityForResult(intent, AddLpActivity.REQ_PELANGGARAN)
//
//        }

        btn_choose_personel_terlapor_lp_add.setOnClickListener {
            val intent = Intent(this, ChoosePersonelActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivityForResult(intent, AddLpActivity.REQ_DILAPOR)
        }

        btn_choose_personel_terlapor_lp_add.setOnClickListener {
            val intent = Intent(this, ChoosePersonelActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivityForResult(intent, AddLpActivity.REQ_TERLAPOR)
        }

        btn_next_lp_add.attachTextChangeAnimator()
        bindProgressButton(btn_next_lp_add)
        btn_next_lp_add.text = resources.getString(R.string.save)
        btn_next_lp_add.setOnClickListener {
            lpReqEdit.no_lp = edt_no_lp_add.text.toString()
//            lpReqEdit.alat_bukti =  edt_alat_bukti_lp_add.text.toString()
//            lpReqEdit.keterangan = edt_ket_lp_add.text.toString()
            lpReqEdit.id_personel_dilapor = idPersonelDilapor
            lpReqEdit.id_personel_terlapor = idPersonelTerlapor
            lpReqEdit.id_pelanggaran = idPelanggaran

            val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
            val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
            animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

            btn_next_lp_add.showProgress {
                progressColor = Color.WHITE
            }

            btn_next_lp_add.showDrawable(animatedDrawable) {
                buttonTextRes = R.string.data_updated
                textMarginRes = R.dimen.space_10dp
            }
            Handler(Looper.getMainLooper()).postDelayed({
                btn_next_lp_add.hideDrawable(R.string.save)
            }, 3000)
            btn_next_lp_add.hideDrawable(R.string.data_updated)

        }
        /*
//        edt_no_lp.setText(editLp?.no_lp)
//        edt_hukuman_lp.setText(editLp?.hukuman)
//        edt_ket_lp.setText(editLp?.keterangan)
//        edt_nama_lp.setText(editLp?.nama_personel)
//        edt_pangkat_lp.setText(editLp?.pangkat_personel)
//        edt_nrp_lp.setText(editLp?.nrp_personel)
//        edt_kesatuan_lp.setText(editLp?.kesatuan)
//        edt_pasal_lp.setText(editLp?.pasal)
//        sp_jenis_lp.setText(editLp?.jenis_pelanggaran)
//
//        btn_save_lp.setOnClickListener {
//            edt_no_lp.text.toString()
//            edt_hukuman_lp.text.toString()
//            edt_ket_lp.text.toString()
//            edt_nama_lp.text.toString()
//            edt_pangkat_lp.text.toString()
//            edt_nrp_lp.text.toString()
//            edt_kesatuan_lp.text.toString()
//            edt_pasal_lp.text.toString()
//        }

         */
    }

    private fun getViewEditLP(editLp: LpResp?) {
        edt_no_lp_add.setText(editLp?.no_lp)
//        edt_alat_bukti_lp_add.setText(editLp?.alatBukti)
//        edt_ket_lp_add.setText(editLp?.keterangan)

//        txt_nama_dilapor_lp_add.text = editLp?.id_personel_dilapor.toString()
//        txt_pangkat_dilapor_lp_add.text = editLp?.id_personel_dilapor.toString()
//        txt_nrp_dilapor_lp_add.text = editLp?.id_personel_dilapor.toString()
//        txt_jabatan_dilapor_lp_add.text = editLp?.id_personel_dilapor.toString()
//        txt_kesatuan_dilapor_lp_add.text = editLp?.id_personel_dilapor.toString()

        txt_nama_terlapor_lp_add.text = editLp?.id_personel_terlapor.toString()
        txt_pangkat_terlapor_lp_add.text = editLp?.id_personel_terlapor.toString()
        txt_nrp_terlapor_lp_add.text = editLp?.id_personel_terlapor.toString()
        txt_jabatan_terlapor_lp_add.text = editLp?.id_personel_terlapor.toString()
        txt_kesatuan_terlapor_lp_add.text = editLp?.id_personel_terlapor.toString()

//        txt_pelanggaran_lp_add.text = editLp?.id_pelanggaran.toString()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val personel = data?.getParcelableExtra<PersonelModel>("ID_PERSONEL")
        val pelanggaran = data?.getParcelableExtra<PelanggaranResp>("PELANGGARAN")
        when (resultCode) {
            Activity.RESULT_OK -> {
                when (requestCode) {
                    AddLpActivity.REQ_DILAPOR -> {
                        idPersonelDilapor = personel?.id
//                        txt_jabatan_dilapor_lp_add.text = personel?.jabatan
//                        txt_kesatuan_dilapor_lp_add.text = personel?.satuan_kerja?.kesatuan
//                        txt_nama_dilapor_lp_add.text = personel?.nama
//                        txt_nrp_dilapor_lp_add.text = "NRP : ${personel?.nrp}"
//                        txt_pangkat_dilapor_lp_add.text = "Pangkat ${personel?.pangkat}"
                    }

                    AddLpActivity.REQ_TERLAPOR -> {
                        idPersonelTerlapor = personel?.id
                        txt_jabatan_terlapor_lp_add.text = personel?.jabatan
                        txt_kesatuan_terlapor_lp_add.text = personel?.satuan_kerja?.kesatuan
                        txt_nama_terlapor_lp_add.text = personel?.nama
                        txt_nrp_terlapor_lp_add.text = "NRP : ${personel?.nrp}"
                        txt_pangkat_terlapor_lp_add.text = "Pangkat ${personel?.pangkat}"
                    }
                    AddLpActivity.REQ_PELANGGARAN -> {
                        idPelanggaran = pelanggaran?.id
//                        txt_pelanggaran_lp_add.text =
//                            "Pelanggaran : ${pelanggaran?.nama_pelanggaran}"
                    }
                }
            }
        }
    }
}