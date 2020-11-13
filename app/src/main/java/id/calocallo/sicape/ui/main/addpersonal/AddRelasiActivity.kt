package id.calocallo.sicape.ui.main.addpersonal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.calocallo.sicape.R
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_relasi.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddRelasiActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_relasi)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Relasi"

        btn_next_relasi.setOnClickListener {
            startActivity(Intent(this, AddCatPersActivity::class.java))
        }
    }
}