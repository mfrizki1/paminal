package id.calocallo.sicape.ui.main.choose.lp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.LpDisiplinResp
import id.calocallo.sicape.network.response.LpKkeResp
import id.calocallo.sicape.network.response.LpPidanaResp
import id.calocallo.sicape.ui.main.choose.lp.ListLpSkhdActivity.Companion.DISIPLIN
import id.calocallo.sicape.ui.main.putkke.AddPutKkeActivity
import id.calocallo.sicape.ui.main.skhd.AddSkhdActivity
import id.calocallo.sicape.ui.main.skhd.AddSkhdActivity.Companion.IDLHP_FOR_LP
import id.calocallo.sicape.utils.ext.toggleVisibility
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_choose_lp_skhd.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class ChooseLpSkhdActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_lp_skhd)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Pilih Jenis Laporan Polisi"
        /*get id_lhp from activity add skhd*/
        val idLhpSkhd = intent.extras?.getInt(IDLHP_FOR_LP)
        val idLhpPutKke = intent.extras?.getInt(AddPutKkeActivity.IDLHP_PUTKKE)
        Log.e("idLhpPutKke", "$idLhpPutKke")
        Log.e("idLhpSkhd", "$idLhpSkhd")

        if(idLhpSkhd != 0) {
            btn_lp_pidana_skhd_choose.setOnClickListener {
                val intent = Intent(this, ListLpSkhdActivity::class.java)
                intent.putExtra(CHOOSE_LP_SKHD, "pidana")
                intent.putExtra(ID_LHP_FOR_LP, idLhpSkhd)
                startActivityForResult(intent, REQ_LP_PIDANA_SKHD)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }

            btn_lp_disiplin_skhd_choose.setOnClickListener {
                val intent = Intent(this, ListLpSkhdActivity::class.java)
                intent.putExtra(CHOOSE_LP_SKHD, "disiplin")
                intent.putExtra(ID_LHP_FOR_LP, idLhpSkhd)
                startActivityForResult(intent, REQ_LP_DISIPLIN_SKHD)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            btn_lp_kke_put_kke_choose.toggleVisibility()
        }else if(idLhpPutKke != 0){
            btn_lp_kke_put_kke_choose.setOnClickListener {
                val intent = Intent(this, ListLpKkePutKkeActivity::class.java)
                intent.putExtra(ID_LHP_FOR_LP, idLhpPutKke)
                startActivityForResult(intent, REQ_LP_PUT_KKE)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            btn_lp_pidana_skhd_choose.toggleVisibility()
            btn_lp_disiplin_skhd_choose.toggleVisibility()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_LP_PIDANA_SKHD) {
            if (resultCode == 999) {
                val pidanaResp = data?.getParcelableExtra<LpPidanaResp>(ListLpSkhdActivity.PIDANA)
                val intent = Intent()
                intent.putExtra(AddSkhdActivity.ID_LP, pidanaResp)
                setResult(RES_LP_PIDANA_SKHD, intent)
                finish()
            }
        } else if (requestCode == REQ_LP_DISIPLIN_SKHD) {
            if (resultCode == 888) {
                val displinResp =
                    data?.getParcelableExtra<LpDisiplinResp>(ListLpSkhdActivity.DISIPLIN)
                val intent = Intent()
                intent.putExtra(AddSkhdActivity.ID_LP, displinResp)
                setResult(RES_LP_DISIPLIN_SKHD, intent)
                finish()
            }
        }else if(requestCode == REQ_LP_PUT_KKE){
            if(resultCode == RES_LP_PUT_KKE){
                val kke = data?.getParcelableExtra<LpKkeResp>(ListLpKkePutKkeActivity.DATA_KKE)
                val intent = Intent()
                intent.putExtra(AddPutKkeActivity.DATA_KKE, kke)
                setResult(RES_LP_PUT_KKE, intent)
                finish()
            }
        }
    }

    companion object {
        const val CHOOSE_LP_SKHD = "IS_PIDANA"
        const val ID_LHP_FOR_LP = "ID_LHP_FOR_LP"
        const val REQ_LP_PIDANA_SKHD = 1
        const val REQ_LP_DISIPLIN_SKHD = 2
        const val REQ_LP_PUT_KKE = 10
        const val RES_LP_PIDANA_SKHD = 1
        const val RES_LP_DISIPLIN_SKHD = 2
        const val RES_LP_PUT_KKE = 10
    }
}