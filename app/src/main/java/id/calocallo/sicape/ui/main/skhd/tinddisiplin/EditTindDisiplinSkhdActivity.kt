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
import id.calocallo.sicape.network.response.TindDisiplinResp
import id.calocallo.sicape.ui.main.choose.ChoosePersonelActivity
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_tind_disiplin_skhd.*
import kotlinx.android.synthetic.main.activity_edit_tind_disiplin_skhd.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditTindDisiplinSkhdActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var tindakan: String? = null
    private var tindDisiplinReq = TindDisiplinReq()
    private var idPersonel: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_tind_disiplin_skhd)
        sessionManager = SessionManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Tindakan Disiplin"

        val dataTindDisp =
            intent.extras?.getParcelable<TindDisiplinResp>(SkhdTindDisiplinActivity.EDIT_TIND_DISIPLIN)
        getDataTindDisiplin(dataTindDisp)


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
        spinner_tind_disiplin_edit.setAdapter(adapter)
        spinner_tind_disiplin_edit.setOnItemClickListener { parent, view, position, id ->
            tindakan = parent.getItemAtPosition(position).toString()
        }

        btn_choose_personel_tind_disiplin_edit.setOnClickListener {
            val intent = Intent(this, ChoosePersonelActivity::class.java)
            startActivityForResult(intent, REQ_PERSONEL_TIND_DISP)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_save_tind_disiplin_edit.setOnClickListener {
            tindDisiplinReq.isi_tindakan_disiplin = tindakan
            tindDisiplinReq.id_personel = idPersonel
            Log.e("edit Tind", "$tindDisiplinReq")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_PERSONEL_TIND_DISP) {
            if (resultCode == Activity.RESULT_OK) {
                val personel = data?.getParcelableExtra<PersonelModel>("ID_PERSONEL")
                idPersonel = personel?.id
                txt_nama_personel_tind_disiplin_edit.text = "Nama : ${personel?.nama}"
                txt_pangkat_personel_tind_disiplin_edit.text = "Pangkat ${personel?.pangkat}: "
                txt_nrp_personel_tind_disiplin_edit.text = "NRP : ${personel?.nrp}"
                txt_jabatan_personel_tind_disiplin_edit.text = "Jabatan : ${personel?.jabatan}"
                txt_kesatuan_personel_tind_disiplin_edit.text =
                    "Kesatuan : ${personel?.satuan_kerja?.kesatuan}"
            }
        }
    }

    private fun getDataTindDisiplin(dataTindDisp: TindDisiplinResp?) {
        spinner_tind_disiplin_edit.setText(dataTindDisp?.isi_tindakan_disiplin)
        tindakan = dataTindDisp?.isi_tindakan_disiplin
        txt_nama_personel_tind_disiplin_edit.text = dataTindDisp?.personel?.nama
        txt_pangkat_personel_tind_disiplin_edit.text = dataTindDisp?.personel?.pangkat
        txt_nrp_personel_tind_disiplin_edit.text = dataTindDisp?.personel?.nrp
        txt_jabatan_personel_tind_disiplin_edit.text = dataTindDisp?.personel?.jabatan
        txt_kesatuan_personel_tind_disiplin_edit.text = dataTindDisp?.personel?.kesatuan
    }

    companion object {
        const val REQ_PERSONEL_TIND_DISP = 2
    }
}