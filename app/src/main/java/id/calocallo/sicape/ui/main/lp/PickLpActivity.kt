package id.calocallo.sicape.ui.main.lp

import android.content.Intent
import android.os.Bundle
import id.calocallo.sicape.R
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_lp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class PickLpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_lp)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Laporan Polisi"

        btn_lp_pidana.setOnClickListener{
            val intent = Intent(this, LpActivity::class.java)
            intent.putExtra(LpActivity.JENIS, "Pidana")
            startActivity(intent)
        }

        btn_lp_disiplin.setOnClickListener{
            val intent = Intent(this, LpActivity::class.java)
            intent.putExtra(LpActivity.JENIS, "Disiplin")
            startActivity(intent)
        }

        btn_lp_kkep.setOnClickListener{
            val intent = Intent(this, LpActivity::class.java)
            intent.putExtra(LpActivity.JENIS, "Kode Etik")
            startActivity(intent)
        }
    }
}