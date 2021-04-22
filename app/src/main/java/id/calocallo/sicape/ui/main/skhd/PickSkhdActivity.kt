package id.calocallo.sicape.ui.main.skhd

import android.content.Intent
import android.os.Bundle
import id.calocallo.sicape.R
import id.calocallo.sicape.ui.main.putkke.ListPutKkeActivity
import id.calocallo.sicape.ui.main.skhd.tinddisiplin.SkhdTindDisiplinActivity
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_skhd.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class PickSkhdActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_skhd)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Surat Keputusan Hukuman Disiplin"

        btn_skhd_pidana.setOnClickListener {
            val intent = Intent(this, ListSkhdActivity::class.java)
            intent.putExtra(ListSkhdActivity.SKHD, "Pidana")
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        btn_skhd_disiplin.setOnClickListener {
            val intent = Intent(this, ListSkhdActivity::class.java)
            intent.putExtra(ListSkhdActivity.SKHD, "Disiplin")
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        btn_skhd_tind_disp.setOnClickListener {
            startActivity(Intent(this, SkhdTindDisiplinActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        btn_skhd_kkep.setOnClickListener {
            val intent = Intent(this, ListPutKkeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }
}