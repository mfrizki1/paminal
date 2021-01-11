package id.calocallo.sicape.ui.main.choose.lp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.RefPenyelidikanReq
import id.calocallo.sicape.network.response.LpCustomResp
import id.calocallo.sicape.network.response.LpDisiplinResp
import id.calocallo.sicape.network.response.LpKkeResp
import id.calocallo.sicape.network.response.LpPidanaResp
import id.calocallo.sicape.ui.main.lhp.add.AddLhpActivity
import id.calocallo.sicape.ui.main.lhp.add.AddLhpActivity.Companion.DATA_LP
import id.calocallo.sicape.ui.main.lhp.add.AddLhpActivity.Companion.REQ_LP
import id.calocallo.sicape.ui.main.lhp.add.ListKetTerlaporLhpActivity.Companion.LIST_KET_TERLAPOR
import id.calocallo.sicape.ui.main.lhp.add.ReferensiPenyelidikanLhpActivity
import id.calocallo.sicape.ui.main.rehab.sktt.AddSkttActivity
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_choose_lp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class ChooseLpActivity : BaseActivity() {
    private var sktt :String? = null
    private var refLp = RefPenyelidikanReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_lp)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Pilih Jenis Laporan Polisi"

        /*get value from sktt*/
        sktt = intent.extras?.getString(AddSkttActivity.LP_SKTT)
        /*set jika ad ket terlapor dari activity ListKetTerlaporLhpActivity*/
        btn_lp_pidana_choose.setOnClickListener {
            val intent = Intent(this, LpChooseActivity::class.java)
            intent.putExtra(LpChooseActivity.JENIS_LP_CHOOSE, "Pidana")
            if (sktt != null) {
                intent.putExtra(AddSkttActivity.LP_SKTT, sktt)
            }
            startActivityForResult(intent, REQ_LP)
        }

        btn_lp_kkep_choose.setOnClickListener {
            val intent = Intent(this, LpChooseActivity::class.java)
            intent.putExtra(LpChooseActivity.JENIS_LP_CHOOSE, "Kode Etik")
            if (sktt != null) {
                intent.putExtra(AddSkttActivity.LP_SKTT, sktt)
            }
            startActivityForResult(intent, REQ_LP)

        }
        btn_lp_disiplin_choose.setOnClickListener {
            val intent = Intent(this, LpChooseActivity::class.java)
            intent.putExtra(LpChooseActivity.JENIS_LP_CHOOSE, "Disiplin")
            if (sktt != null) {
                intent.putExtra(AddSkttActivity.LP_SKTT, sktt)
            }
            startActivityForResult(intent, REQ_LP)

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val noLp: String
        val intentLP = Intent()
        if (requestCode == REQ_LP) {
            if(sktt == null) {
                when (resultCode) {
                    /*
                999 -> {
                    val lpPidana = data?.getParcelableExtra<LpPidanaResp>(DATA_LP)
                    refLp.id_lp = lpPidana?.id
                    refLp.no_lp = lpPidana?.no_lp
                    intentLP.putExtra(DATA_LP, refLp)
                    when (requestCode) {
                        REQ_LP -> {
                            setResult(Activity.RESULT_OK, intentLP)
                            finish()
                        }
                    }
                }
                888 -> {
                    val lpKke = data?.getParcelableExtra<LpKkeResp>(DATA_LP)
                    refLp.id_lp = lpKke?.id
                    refLp.no_lp = lpKke?.no_lp
                    intentLP.putExtra(DATA_LP, refLp)
                    when (requestCode) {
                        REQ_LP -> {
                            setResult(Activity.RESULT_OK, intentLP)
                            finish()
                        }
                    }
                }
                777 -> {
                    val lpDisiplin = data?.getParcelableExtra<LpDisiplinResp>(DATA_LP)
                    refLp.id_lp = lpDisiplin?.id
                    refLp.no_lp = lpDisiplin?.no_lp
                    intentLP.putExtra(DATA_LP, refLp)
                    when (requestCode) {
                        REQ_LP -> {
                            setResult(Activity.RESULT_OK, intentLP)
                            finish()
                        }
                    }
                }
                 */
                    RES_LP_CHOOSE -> {
                        val lpAll = data?.getParcelableExtra<LpCustomResp>(DATA_LP)
                        val intent = Intent()

                        refLp.id_lp = lpAll?.id
                        refLp.no_lp = lpAll?.no_lp
                        intent.putExtra(
                            ReferensiPenyelidikanLhpActivity.GET_LP_FROM_CHOOSE_LP,
                            refLp
                        )
                        setResult(Activity.RESULT_OK, intent)
                        finish()
                    }
                }
            }else{
                when(resultCode){
                    RES_LP_CHOOSE->{
                        val lpAll = data?.getParcelableExtra<LpCustomResp>(GET_LP_WITHOUT_SKTBB_DLL)
//                        Log.e("lpall","$lpAll")
                        val intent = Intent()
                        intent.putExtra(AddSkttActivity.GET_LP_ON_SKTT,lpAll)
                        setResult(Activity.RESULT_OK, intent)
                        finish()
                    }
                }
            }
        }


    }

    companion object {
        const val NO_LP = "NO_LP"
        const val RES_LP_CHOOSE = 1
        const val GET_LP_WITHOUT_SKTBB_DLL = "GET_LP_WITHOUT_SKTBB_DLL"
    }
}