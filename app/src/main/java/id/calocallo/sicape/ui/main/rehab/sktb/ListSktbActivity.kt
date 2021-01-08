package id.calocallo.sicape.ui.main.rehab.sktb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.calocallo.sicape.R
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class ListSktbActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_sktb)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Data SKTB"
    }
}