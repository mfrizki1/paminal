package id.calocallo.sicape.ui.main.skhd.tinddisiplin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AllPersonelModel
import id.calocallo.sicape.network.request.TindDisiplinReq
import id.calocallo.sicape.network.response.TindDisplMinResp
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_tind_disiplin_skhd.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditTindDisiplinSkhdActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var tindakan: String? = null
    private var tindDisiplinReq = TindDisiplinReq()
    private var idPersonel: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_tind_disiplin_skhd)
        sessionManager1 = SessionManager1(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Tindakan Disiplin"

        val dataTindDisp =
            intent.extras?.getParcelable<TindDisplMinResp>(SkhdTindDisiplinActivity.EDIT_TIND_DISIPLIN)
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
            val intent = Intent(this, KatPersonelActivity::class.java)
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
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
                val personel = data?.getParcelableExtra<AllPersonelModel>("ID_PERSONEL")
                idPersonel = personel?.id
                txt_nama_personel_tind_disiplin_edit.text = "Nama : ${personel?.nama}"
                txt_pangkat_personel_tind_disiplin_edit.text = "Pangkat ${personel?.pangkat.toString().toUpperCase()}: "
                txt_nrp_personel_tind_disiplin_edit.text = "NRP : ${personel?.nrp}"
                txt_jabatan_personel_tind_disiplin_edit.text = "Jabatan : ${personel?.jabatan}"
                txt_kesatuan_personel_tind_disiplin_edit.text =
                    "Kesatuan : ${personel?.satuan_kerja?.kesatuan}"
            }
        }
    }

    private fun getDataTindDisiplin(dataTindDisp: TindDisplMinResp?) {
       /* spinner_tind_disiplin_edit.setText(dataTindDisp?.isi_tindakan_disiplin)
        tindakan = dataTindDisp?.isi_tindakan_disiplin
        txt_nama_personel_tind_disiplin_edit.text = dataTindDisp?.personel?.nama
        txt_pangkat_personel_tind_disiplin_edit.text = dataTindDisp?.personel?.pangkat
        txt_nrp_personel_tind_disiplin_edit.text = dataTindDisp?.personel?.nrp
        txt_jabatan_personel_tind_disiplin_edit.text = dataTindDisp?.personel?.jabatan
        txt_kesatuan_personel_tind_disiplin_edit.text = dataTindDisp?.personel?.kesatuan*/
    }

    companion object {
        const val REQ_PERSONEL_TIND_DISP = 2
    }
}