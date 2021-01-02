package id.calocallo.sicape.ui.main.lp.disiplin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.calocallo.sicape.R
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditLpDisiplinActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_lp_disiplin)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Laporan Polisi Disiplin"


    }
}