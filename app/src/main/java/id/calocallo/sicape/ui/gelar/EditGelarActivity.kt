package id.calocallo.sicape.ui.gelar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.LhgResp
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_gelar.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditGelarActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var detailLhg : LhgResp? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_gelar)
        sessionManager1 = SessionManager1(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Gelar Perkara"

        detailLhg = intent.getParcelableExtra(DetailGelarActivity.DETAIL_LHG)
        viewEditLhg(detailLhg)
        btn_next_lhg_edit.setOnClickListener {

        }
    }

    private fun viewEditLhg(detailLhg: LhgResp?) {

    }
}