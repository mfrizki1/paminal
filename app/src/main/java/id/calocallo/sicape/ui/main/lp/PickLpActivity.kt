package id.calocallo.sicape.ui.main.lp

import android.content.Intent
import android.os.Bundle
import id.calocallo.sicape.R
import id.calocallo.sicape.ui.main.lp.disiplin.ListLpDisiplinActivity
import id.calocallo.sicape.ui.main.lp.kke.ListLpKodeEtikActivity
import id.calocallo.sicape.ui.main.lp.pidana.ListLpPidanaActivity
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_lp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class PickLpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_lp)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Laporan Polisi"

        btn_lp_pidana.setOnClickListener {
            val intent = Intent(this, ListLpPidanaActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        }

        btn_lp_disiplin.setOnClickListener {
            val intent = Intent(this, ListLpDisiplinActivity::class.java)
//            intent.putExtra(LpActivity.TYPEVIEW, 1)
//            intent.putExtra(LpActivity.JENIS, "Disiplin")
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        btn_lp_kkep.setOnClickListener {
            val intent = Intent(this, ListLpKodeEtikActivity::class.java)
//            intent.putExtra(LpActivity.TYPEVIEW, 1)
//            intent.putExtra(LpActivity.JENIS, "Kode Etik")
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

    }
}