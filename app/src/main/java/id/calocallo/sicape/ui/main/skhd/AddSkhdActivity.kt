package id.calocallo.sicape.ui.main.skhd

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LhpResp
import id.calocallo.sicape.network.request.SkhdReq
import id.calocallo.sicape.network.response.LpDisiplinResp
import id.calocallo.sicape.network.response.LpPidanaResp
import id.calocallo.sicape.ui.main.choose.lhp.ChooseLhpActivity
import id.calocallo.sicape.ui.main.choose.lp.ChooseLpSkhdActivity
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_skhd.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddSkhdActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var skhdReq = SkhdReq()
    private var idLhp: Int? = null
    private var idLp: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_skhd)
        sessionManager1 = SessionManager1(this)

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data SKHD"

        /*set button for add id_lhp*/
        bnt_pick_lhp_skhd_add.setOnClickListener {
            val intent = Intent(this, ChooseLhpActivity::class.java)
            startActivityForResult(intent, REQ_CHOOSE_LHP)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        /*set button for add id_lp*/
        bnt_pick_lp_skhd_add.setOnClickListener {
            val intent = Intent(this, ChooseLpSkhdActivity::class.java)
            intent.putExtra(IDLHP_FOR_LP, idLhp)
            startActivityForResult(intent, REQ_CHOOSE_LP)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        }
        /*set add data skhd*/
        btn_save_skhd_add.attachTextChangeAnimator()
        bindProgressButton(btn_save_skhd_add)
        btn_save_skhd_add.setOnClickListener {
            addSkhd()
        }

    }

    private fun addSkhd() {
        val animated = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animated.setBounds(0, 0, size, size)
        skhdReq.no_skhd = edt_no_skhd_add.text.toString()
        skhdReq.bidang = edt_satker_skhd_add.text.toString().toUpperCase()
        skhdReq.menimbang_p2 = edt_berkas_perkara_skhd_add.text.toString()
        skhdReq.memperlihatkan = edt_memperlihat_skhd_add.text.toString()
        skhdReq.hukuman = edt_hukuman_skhd_add.text.toString()
        skhdReq.tanggal_disampaikan_ke_terhukum = edt_tgl_disampaikan_skhd_add.text.toString()
        skhdReq.waktu_disampaikan_ke_terhukum = edt_pukul_disampaikan_skhd_add.text.toString()
        skhdReq.kota_penetapan = edt_kota_dibuat_skhd_add.text.toString()
        skhdReq.tanggal_penetapan = edt_tgl_dibuat_skhd_add.text.toString()
        skhdReq.nama_yang_menetapkan = edt_nama_bidang_skhd_add.text.toString()
        skhdReq.jabatan_yang_menetapkan = edt_jabatan_kepala_bidang_skhd_add.text.toString()
        skhdReq.pangkat_yang_menetapkan = edt_pangkat_bidang_skhd_add.text.toString().toUpperCase()
        skhdReq.nrp_yang_menetapkan = edt_nrp_bidang_skhd_add.text.toString()
        skhdReq.tembusan = edt_tembusan_skhd_add.text.toString()
        skhdReq.id_lp = idLp
        skhdReq.id_lhp = idLhp
        btn_save_skhd_add.showProgress {
            progressColor = Color.WHITE
        }
        btn_save_skhd_add.showDrawable(animated) {
            textMarginRes = R.dimen.space_10dp
            buttonTextRes = R.string.data_saved
        }
        btn_save_skhd_add.hideDrawable(R.string.save)
//        Log.e("add SKHD", "$skhdReq")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CHOOSE_LHP) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val lhpResp = data?.getParcelableExtra<LhpResp>("CHOOSE_LHP")
                    idLhp = lhpResp?.id
                    txt_lhp_skhd_add.text = lhpResp?.no_lhp
                }
            }
        } else if (requestCode == REQ_CHOOSE_LP) {
            when (resultCode) {
                ChooseLpSkhdActivity.RES_LP_PIDANA_SKHD -> {
                    val pidana = data?.getParcelableExtra<LpPidanaResp>(ID_LP)
                    idLp = pidana?.id
                    txt_lp_skhd_add.text = pidana?.no_lp
                }
                ChooseLpSkhdActivity.RES_LP_DISIPLIN_SKHD -> {
                    val disiplin = data?.getParcelableExtra<LpDisiplinResp>(ID_LP)
                    idLp = disiplin?.id
                    txt_lp_skhd_add.text = disiplin?.no_lp
                }
            }
        }
    }

    companion object {
        const val REQ_CHOOSE_LHP = 2
        const val REQ_CHOOSE_LP = 1
        const val IDLHP_FOR_LP = "LP_SKHD"
        const val ID_LP = "ID_LP"
    }
}