package id.calocallo.sicape.ui.main.skhd.edit

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.hideDrawable
import com.github.razir.progressbutton.showDrawable
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LhpResp
import id.calocallo.sicape.network.request.SkhdReq
import id.calocallo.sicape.network.response.LpDisiplinResp
import id.calocallo.sicape.network.response.LpResp
import id.calocallo.sicape.network.response.SkhdResp
import id.calocallo.sicape.ui.main.choose.lp.ChooseLpSkhdActivity
import id.calocallo.sicape.ui.main.skhd.AddSkhdActivity
import id.calocallo.sicape.ui.main.skhd.DetailSkhdActivity
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_skhd.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditSkhdActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var skhdReq = SkhdReq()
    private var idLhp: Int? = null
    private var idLp: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_skhd)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data SKHD"

        val detailSkhd = intent.extras?.getParcelable<SkhdResp>(DetailSkhdActivity.DETAIL_SKHD)
        getDetailSkhd(detailSkhd)

        btn_save_skhd_edit.setOnClickListener {
            edt_no_skhd_edit.text.toString()
            edt_satker_skhd_edit.text.toString()
            edt_berkas_perkara_skhd_edit.text.toString()
            edt_memperlihat_skhd_edit.text.toString()
            edt_hukuman_skhd_edit.text.toString()
            edt_tgl_disampaikan_skhd_edit.text.toString()
            edt_pukul_disampaikan_skhd_edit.text.toString()
            edt_kota_dibuat_skhd_edit.text.toString()
            edt_tgl_dibuat_skhd_edit.text.toString()
            edt_nama_bidang_skhd_edit.text.toString()
            edt_pangkat_bidang_skhd_edit.text.toString()
            edt_nrp_bidang_skhd_edit.text.toString()
            edt_jabatan_kepala_bidang_skhd_edit.text.toString()
            edt_tembusan_skhd_edit.text.toString()
        }
/*

        bnt_pick_lhp_skhd_edit.setOnClickListener {
            val intent = Intent(this, ChooseLhpActivity::class.java)
            startActivityForResult(intent, AddSkhdActivity.REQ_CHOOSE_LHP)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
*/

        /*set button for edit id_lp*/
        btn_pick_lp_skhd_edit.setOnClickListener {
            val intent = Intent(this, ChooseLpSkhdActivity::class.java)
            intent.putExtra(AddSkhdActivity.IDLHP_FOR_LP, idLhp)
            startActivityForResult(intent, AddSkhdActivity.REQ_CHOOSE_LP)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        btn_save_skhd_edit.setOnClickListener {
            updateSkhd(detailSkhd)
        }
    }

    private fun updateSkhd(detailSkhd: SkhdResp?) {
        val animated = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animated.setBounds(0, 0, size, size)

        skhdReq.no_skhd = edt_no_skhd_edit.text.toString()
        skhdReq.bidang = edt_satker_skhd_edit.text.toString().toUpperCase()
        skhdReq.menimbang_p2 = edt_berkas_perkara_skhd_edit.text.toString()
        skhdReq.memperlihatkan = edt_memperlihat_skhd_edit.text.toString()
        skhdReq.hukuman = edt_hukuman_skhd_edit.text.toString()
        skhdReq.tanggal_disampaikan_ke_terhukum = edt_tgl_disampaikan_skhd_edit.text.toString()
        skhdReq.waktu_disampaikan_ke_terhukum = edt_pukul_disampaikan_skhd_edit.text.toString()
        skhdReq.kota_penetapan = edt_kota_dibuat_skhd_edit.text.toString()
        skhdReq.tanggal_penetapan = edt_tgl_dibuat_skhd_edit.text.toString()
        skhdReq.nama_yang_menetapkan = edt_nama_bidang_skhd_edit.text.toString()
        skhdReq.jabatan_yang_menetapkan = edt_jabatan_kepala_bidang_skhd_edit.text.toString().toUpperCase()
        skhdReq.pangkat_yang_menetapkan = edt_pangkat_bidang_skhd_edit.text.toString()
        skhdReq.nrp_yang_menetapkan = edt_nrp_bidang_skhd_edit.text.toString()
        skhdReq.tembusan = edt_tembusan_skhd_edit.text.toString()
        skhdReq.id_lhp = idLhp
        skhdReq.id_lp = idLp
        Log.e("editSKHD", "$skhdReq")
        btn_save_skhd_edit.showProgress{
                progressColor = Color.WHITE
            }

        btn_save_skhd_edit.showDrawable(animated){
                textMarginRes = R.dimen.space_10dp
                buttonTextRes = R.string.data_updated
            }

        btn_save_skhd_edit.hideDrawable(R.string.save)
    }

    private fun getDetailSkhd(detailSkhd: SkhdResp?) {
        edt_no_skhd_edit.setText(detailSkhd?.no_skhd)
        edt_satker_skhd_edit.setText(detailSkhd?.bidang)
        edt_berkas_perkara_skhd_edit.setText(detailSkhd?.menimbang_p2)
        edt_memperlihat_skhd_edit.setText(detailSkhd?.memperlihatkan)
        edt_hukuman_skhd_edit.setText(detailSkhd?.hukuman)
        edt_tgl_disampaikan_skhd_edit.setText(detailSkhd?.tanggal_disampaikan_ke_terhukum)
        edt_pukul_disampaikan_skhd_edit.setText(detailSkhd?.waktu_disampaikan_ke_terhukum)
        edt_kota_dibuat_skhd_edit.setText(detailSkhd?.kota_penetapan)
        edt_tgl_dibuat_skhd_edit.setText(detailSkhd?.tanggal_penetapan)
        edt_nama_bidang_skhd_edit.setText(detailSkhd?.nama_yang_menetapkan)
        edt_pangkat_bidang_skhd_edit.setText(detailSkhd?.pangkat_yang_menetapkan.toString().toUpperCase())
        edt_nrp_bidang_skhd_edit.setText(detailSkhd?.nrp_yang_menetapkan)
        edt_jabatan_kepala_bidang_skhd_edit.setText(detailSkhd?.jabatan_yang_menetapkan)
        edt_tembusan_skhd_edit.setText(detailSkhd?.tembusan)
        txt_lhp_skhd_edit.text = detailSkhd?.lhp?.no_lhp
        txt_lp_skhd_edit.text = detailSkhd?.lp?.no_lp
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AddSkhdActivity.REQ_CHOOSE_LHP) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val lhpResp = data?.getParcelableExtra<LhpResp>("CHOOSE_LHP")
                    idLhp = lhpResp?.id
                    txt_lhp_skhd_edit.text = lhpResp?.no_lhp
                }
            }
        } else if (requestCode == AddSkhdActivity.REQ_CHOOSE_LP) {
            when (resultCode) {
                ChooseLpSkhdActivity.RES_LP_PIDANA_SKHD -> {
                    val pidana = data?.getParcelableExtra<LpResp>(AddSkhdActivity.ID_LP)
                    idLp = pidana?.id
                    txt_lp_skhd_edit.text = pidana?.no_lp
                }
                ChooseLpSkhdActivity.RES_LP_DISIPLIN_SKHD -> {
                    val disiplin = data?.getParcelableExtra<LpDisiplinResp>(AddSkhdActivity.ID_LP)
                    idLp = disiplin?.id
                    txt_lp_skhd_edit.text = disiplin?.no_lp
                }
            }
        }
    }
}