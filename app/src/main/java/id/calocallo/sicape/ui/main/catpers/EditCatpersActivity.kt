package id.calocallo.sicape.ui.main.catpers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import id.calocallo.sicape.R
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_catpers.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditCatpersActivity : BaseActivity() {
    companion object {
        const val EDIT_CATPERS = "EDIT_CATPERS"
    }

    var tempStatus: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_catpers)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Catatan Personel"

        val item = listOf("Proses", "Dalam Masa Hukuman", "Selesai")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        spinner_status_kasus_catpers_edit.setAdapter(adapter)
        spinner_status_kasus_catpers_edit.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> {
                    tempStatus = "proses"
                }
                1 -> {
                    tempStatus = "dalam_masa_hukuman"
                }
                2 -> {
                    tempStatus = "selesai"
                }
            }
            Log.e("Asd", "${parent.getItemAtPosition(position)},$tempStatus")
        }
        btn_save_catpers_edit.setOnClickListener {
            Log.e("Asd", "$tempStatus")

        }
    }
}