package id.calocallo.sicape.ui.main.choose.lp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.LpResp
import id.calocallo.sicape.ui.main.lhp.AddLhpActivity
import id.calocallo.sicape.ui.main.lhp.AddLhpActivity.Companion.DATA_LP
import id.calocallo.sicape.ui.main.lhp.AddLhpActivity.Companion.REQ_LP
import id.calocallo.sicape.ui.main.lp.LpActivity
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_choose_lp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class ChooseLpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_lp)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Pilih Jenis Laporan Polisi"
        btn_lp_pidana_choose.setOnClickListener {
            val intent = Intent(this, LpChooseActivity::class.java)
            intent.putExtra(LpChooseActivity.JENIS_LP_CHOOSE, "Pidana")
//            intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT)
            startActivityForResult(intent, AddLhpActivity.REQ_LP)
        }

        btn_lp_kkep_choose.setOnClickListener {
            val intent = Intent(this, LpChooseActivity::class.java)
            intent.putExtra(LpChooseActivity.JENIS_LP_CHOOSE, "Kode Etik")
//            intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT)
            startActivityForResult(intent, AddLhpActivity.REQ_LP)

        }
        btn_lp_disiplin_choose.setOnClickListener {
            val intent = Intent(this, LpChooseActivity::class.java)
            intent.putExtra(LpChooseActivity.JENIS_LP_CHOOSE, "Disiplin")
//            intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT)
            startActivityForResult(intent, REQ_LP)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val lp = data?.getParcelableExtra<LpResp>(AddLhpActivity.DATA_LP)
        val intentLP = Intent()
        intentLP.putExtra(DATA_LP, lp)
        when (resultCode) {
            Activity.RESULT_OK -> {
                when (requestCode) {
                    REQ_LP -> {
                        setResult(Activity.RESULT_OK, intentLP)
                        finish()
                    }

                }
            }

        }
    }
}