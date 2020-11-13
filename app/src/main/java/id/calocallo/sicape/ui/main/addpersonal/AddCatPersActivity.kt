package id.calocallo.sicape.ui.main.addpersonal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.ui.main.personel.DetailPersonelActivity
import id.calocallo.sicape.ui.main.personel.PersonelActivity
import id.calocallo.sicape.utils.ext.action
import id.calocallo.sicape.utils.ext.showSnackbar
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_cat_pers.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddCatPersActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_cat_pers)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Catatan Personal"

        initSP()

        btn_next_cat_pers.setOnClickListener {
            it.showSnackbar(R.string.data_saved) {
                action(R.string.next) {
                    startActivity(Intent(this@AddCatPersActivity, PersonelActivity::class.java))

                }
            }
        }
    }

    private fun initSP() {
        val item = listOf("Pidana", "Kode Etik", "Disiplin")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        spinner_cat_pers.setAdapter(adapter)
        spinner_cat_pers.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT)
                .show()
        }
    }
}