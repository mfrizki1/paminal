package id.calocallo.sicape.ui.main.rehab

import android.content.Intent
import android.os.Bundle
import id.calocallo.sicape.R
import id.calocallo.sicape.ui.main.rehab.rpph.ListRpphActivity
import id.calocallo.sicape.ui.main.rehab.rps.ListRpsActivity
import id.calocallo.sicape.ui.main.rehab.sktb.ListSktbActivity
import id.calocallo.sicape.ui.main.rehab.sktt.ListSkttActivity
import id.calocallo.sicape.ui.main.rehab.sp4.ListSp4Activity
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_rehab.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class PickRehabActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_rehab)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Pilih Menu Rehab"

        /*RPS*/
        btn_personel_polres.setOnClickListener {
            val intent = Intent(this, ListRpsActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        }

        /*RPPH*/
        btn_rpph.setOnClickListener {
            val intent = Intent(this, ListRpphActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        }

        /*SKTB*/
        btn_sktb.setOnClickListener {
            val intent = Intent(this, ListSktbActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        }

        /*SKTT*/
        btn_sktt.setOnClickListener {
            val intent = Intent(this, ListSkttActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        /*SP3*/
        btn_sp4.setOnClickListener {
            val intent = Intent(this, ListSp4Activity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }
}