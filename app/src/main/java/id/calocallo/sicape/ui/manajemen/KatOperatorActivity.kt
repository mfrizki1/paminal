package id.calocallo.sicape.ui.manajemen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import id.calocallo.sicape.R
import id.calocallo.sicape.ui.manajemen.sipil.ListSipilOperatorActivity
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_kat_operator.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class KatOperatorActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kat_operator)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Kategori Operator"
        val stts = intent.extras?.getString("manajemen")
        Log.e("sttsKat", "$stts")

        btn_personel_operator.setOnClickListener {
            if (stts.isNullOrEmpty()) {
                val intent = Intent(this, ManajemenOperatorActivity::class.java)
                intent.putExtra("list", "polisi")
                intent.putExtra("manajemen", "operator")

                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            } else {
                val intent = Intent(this, AddOperatorActivity::class.java)
                intent.putExtra("manajemen", "polisi")
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }

        }
        btn_sipil_operator.setOnClickListener {
            if (stts.isNullOrEmpty()) {
                val intent = Intent(this, ListSipilOperatorActivity::class.java)
//                intent.putExtra("list", "sipil")
//                intent.putExtra("manajemen", "operator")

                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            } else {
                val intent = Intent(this, AddOperatorActivity::class.java)
                intent.putExtra("manajemen", "sipil")
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }

        }

    }
}