package id.calocallo.sicape.ui.satker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.calocallo.sicape.R
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_kat_satker.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class KatSatkerActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kat_satker)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Pilih Kategori Satker/Satwil"

        btn_satker_polda.setOnClickListener {
            val intent = Intent(this, ListSatkerActivity::class.java).apply {
                this.putExtra(AddSatkerActivity.JENIS_SATKERN,"polda")
            }
            startActivity(intent)
        }
        btn_satker_polres.setOnClickListener {
            val intent = Intent(this, ListSatkerActivity::class.java).apply {
                this.putExtra(AddSatkerActivity.JENIS_SATKERN,"polres")
            }
            startActivity(intent)
        }
        btn_satker_polsek.setOnClickListener {
            val intent = Intent(this, ListSatkerActivity::class.java).apply {
                this.putExtra(AddSatkerActivity.JENIS_SATKERN,"polsek")
            }
            startActivity(intent)
        }
    }
}