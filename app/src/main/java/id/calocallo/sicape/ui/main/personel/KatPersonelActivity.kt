package id.calocallo.sicape.ui.main.personel

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.PersonelMinResp
import id.calocallo.sicape.ui.kesatuan.polda.PoldaActivity
import id.calocallo.sicape.ui.kesatuan.polres.PolresActivity
import id.calocallo.sicape.ui.kesatuan.polsek.PolsekActivity
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_kat_personel.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class KatPersonelActivity : BaseActivity() {
    companion object {
        const val KAT_POLDA = "KAT_POLDA"
        const val KAT_POLRES = "KAT_POLRES"
        const val KAT_POLSEK = "KAT_POLSEK"
        const val PICK_PERSONEL = "PICK_PERSONEL"
        const val PICK_PERSONEL_2 = "PICK_PERSONEL_2"
        const val REQ_PICK_PERSONEL = 222
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kat_personel)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Pilih Kategori Personel"


        btn_personel_polda.setOnClickListener {
            if (intent.extras?.getBoolean(PICK_PERSONEL) == true) {
                val intent = Intent(this, PoldaActivity::class.java)
                intent.putExtra(PICK_PERSONEL, true)
                startActivityForResult(intent, REQ_PICK_PERSONEL)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            } else {
                val intent = Intent(this, PoldaActivity::class.java)
                intent.putExtra(KAT_POLDA, KAT_POLDA)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }

        btn_personel_polres.setOnClickListener {
            if (intent.extras?.getBoolean(PICK_PERSONEL) == true) {
                val intent = Intent(this, PolresActivity::class.java)
                intent.putExtra(PICK_PERSONEL, true)
                startActivityForResult(intent, REQ_PICK_PERSONEL)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            } else {
                val intent = Intent(this, PolresActivity::class.java)
                intent.putExtra(KAT_POLRES, KAT_POLRES)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
        btn_personel_polsek.setOnClickListener {
            //PICK PERSONEL
            if (intent.extras?.getBoolean(PICK_PERSONEL) == true) {
                val intent = Intent(this, PolsekActivity::class.java)
                intent.putExtra(PICK_PERSONEL_2, true)
                startActivityForResult(intent, REQ_PICK_PERSONEL)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            } else {
                val intent = Intent(this, PolsekActivity::class.java)
                intent.putExtra(KAT_POLSEK, KAT_POLSEK)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_PICK_PERSONEL) {
            if (resultCode == 123) {
                val intent = Intent()
                val dataPersonel = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
                intent.putExtra("ID_PERSONEL", dataPersonel)
                setResult(Activity.RESULT_OK, intent)
//                startActivity(intent)
                finish()
            }
        }
    }
}