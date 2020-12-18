package id.calocallo.sicape.ui.main.skhd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.calocallo.sicape.R
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_skhd.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class PickSkhdActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_skhd)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Surat Keterangan Hasil Disiplin"

        btn_skhd_pidana.setOnClickListener {
            val intent = Intent(this, SkhdActivity::class.java)
            intent.putExtra(SkhdActivity.SKHD, "Pidana")
            startActivity(intent)
        }

        btn_skhd_disiplin.setOnClickListener {
            val intent = Intent(this, SkhdActivity::class.java)
            intent.putExtra(SkhdActivity.SKHD, "Disiplin")
            startActivity(intent)
        }
        btn_skhd_kkep.setOnClickListener {
            val intent = Intent(this, SkhdActivity::class.java)
            intent.putExtra(SkhdActivity.SKHD, "KKEP")
            startActivity(intent)
        }

        btn_skhd_tind_disp.setOnClickListener {
            startActivity(Intent(this, SkhdTindDisiplinActivity::class.java))
        }
    }
}