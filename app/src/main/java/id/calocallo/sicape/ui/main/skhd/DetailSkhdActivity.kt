package id.calocallo.sicape.ui.main.skhd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import id.calocallo.sicape.R
import id.calocallo.sicape.model.SkhdModel
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class DetailSkhdActivity : BaseActivity() {

    companion object {
        const val DETAIL_SKHD = "DETAIL_SKHD"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_skhd)

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail SKHD"

        val bundle = intent.extras
        val detailSKHD = bundle?.getParcelable<SkhdModel>(DETAIL_SKHD)
        Log.e("detailSKHD", "$detailSKHD")
    }
}