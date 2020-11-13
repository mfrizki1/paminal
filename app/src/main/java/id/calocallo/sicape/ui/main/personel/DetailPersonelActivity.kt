package id.calocallo.sicape.ui.main.personel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.calocallo.sicape.R
import id.calocallo.sicape.ui.main.editpersonel.EditPendidikanActivity
import kotlinx.android.synthetic.main.activity_detail_personel.*

class DetailPersonelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_personel)

        btn_edit_pendidikan.setOnClickListener {
            startActivity(Intent(this, EditPendidikanActivity::class.java))
        }
    }
}