package id.calocallo.sicape.ui.gelar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PaparanGelarModel
import id.calocallo.sicape.utils.GelarDataManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_gelar2.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddGelar2Activity : BaseActivity() {
    private lateinit var gelarDataManager: GelarDataManager
    private var paparanGelarModel = PaparanGelarModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_gelar2)
        gelarDataManager = GelarDataManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Paparan Gelar"
        Logger.addLogAdapter(AndroidLogAdapter())

        btn_next_paparan_gelar_add.setOnClickListener {
            savePaparanGelar()
            startActivity(Intent(this, AddGelar3Activity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun savePaparanGelar() {
        paparanGelarModel.dasar_paparan =
            edt_dasar_paparan_gelar_add.editText?.text.toString()
        paparanGelarModel.kronologis_paparan =
            edt_kronologis_paparan_gelar_add.editText?.text.toString()
        gelarDataManager.setPaparanGelar(paparanGelarModel)
        Logger.e("${gelarDataManager.getPaparanGelar()}")

    }
}