package id.calocallo.sicape.ui.main.skhd.tinddisiplin

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.request.TindDisiplinReq
import id.calocallo.sicape.ui.main.choose.ChoosePersonelActivity
import id.calocallo.sicape.ui.main.lhp.add.AddPersonelLidikActivity
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_tind_disiplin_skhd.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddTindDisiplinSkhdActivity : BaseActivity() {
    private var tindDisiplinReq = TindDisiplinReq()
    private var idPersonel: Int? = null
    private var tindakan: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tind_disiplin_skhd)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Tindakan Disiplin"

        btn_save_tind_disiplin_add.setOnClickListener {
            tindDisiplinReq.id_personel =idPersonel
            tindDisiplinReq.isi_tindakan_disiplin = tindakan
            Log.e("add Tind", "$tindDisiplinReq")
        }

        /*set */
        val itemTind = listOf(
            "Lari",
            "Push Up",
            "Sit Up",
            "Jumping Jack",
            "Hormat Bendera",
            "Mengguling",
            "Jalan Jongkok"
        )
        val adapter = ArrayAdapter(this, R.layout.item_spinner, itemTind)
        spinner_tind_disiplin.setAdapter(adapter)
        spinner_tind_disiplin.setOnItemClickListener { parent, view, position, id ->
            tindakan =  parent.getItemAtPosition(position).toString()
//            Log.e("spinner", parent.getItemAtPosition(position).toString())
        }

        btn_choose_personel_tind_disiplin.setOnClickListener {
            val intent = Intent(this, ChoosePersonelActivity::class.java)
            startActivityForResult(intent, REQ_PERSONEL_TIND)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_PERSONEL_TIND) {
            if (resultCode == Activity.RESULT_OK) {
                val personel = data?.getParcelableExtra<PersonelModel>("ID_PERSONEL")
                idPersonel = personel?.id
                txt_nama_personel_tind_disiplin_add.text = "Nama : ${personel?.nama}"
                txt_pangkat_personel_tind_disiplin_add.text = "Pangkat ${personel?.pangkat.toString().toUpperCase()}: "
                txt_nrp_personel_tind_disiplin_add.text = "NRP : ${personel?.nrp}"
                txt_jabatan_personel_tind_disiplin_add.text = "Jabatan : ${personel?.jabatan}"
                txt_kesatuan_personel_tind_disiplin_add.text =
                    "Kesatuan : ${personel?.satuan_kerja?.kesatuan}"
            }
        }
    }

    companion object {
        const val REQ_PERSONEL_TIND = 1
    }
}