package id.calocallo.sicape.ui.main.lhp.add

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.request.PersonelPenyelidikReq
import id.calocallo.sicape.network.response.PersonelPenyelidikResp
import id.calocallo.sicape.ui.main.choose.ChoosePersonelActivity
import id.calocallo.sicape.ui.main.lhp.edit.lidik.AddSingleLidikLhpActivity
import id.calocallo.sicape.utils.LhpDataManager
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_personel_lidik.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddPersonelLidikActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var lhpDataManager: LhpDataManager
    private var idPersonel: Int? = null
    private var penyelidikResp = PersonelPenyelidikResp()
    private var lidikReq = PersonelPenyelidikReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_personel_lidik)
        setupActionBarWithBackButton(toolbar)
        sessionManager = SessionManager(this)
        lhpDataManager = LhpDataManager(this)

        val addLidik = intent.extras?.getString(AddSingleLidikLhpActivity.ADD_LIDIK)
        if(addLidik.isNullOrEmpty()){
            supportActionBar?.title = "Tambah Data Laporan Hasil Penyelidikan"
        }else{
            supportActionBar?.title = "Tambah Data Personel Penyelidikan"
        }
        //set save add lidik
        btn_save_single_lidik.setOnClickListener {
            if(addLidik.isNullOrEmpty()) {
                val intent = Intent()
                intent.putExtra(LIDIK, penyelidikResp)
                intent.putExtra(LIDIK_REQ, lidikReq)
                setResult(RESULT_OK, intent)
                finish()
            }else{
                Log.e("addSingleLidik", "$lidikReq" )
            }
        }
        //set Radio Group
        rg_lidik.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            if (radio.isChecked) {
                if (radio.text == "Ketua Tim") {
                    penyelidikResp.is_ketua = 1
                    lidikReq.is_ketua = penyelidikResp.is_ketua
                }else{
                    penyelidikResp.is_ketua = 0
                    lidikReq.is_ketua = penyelidikResp.is_ketua
                }
            }

        }

        //set Pick Personel
        btn_choose_personel_lidik.setOnClickListener {
            val intent = Intent(this, ChoosePersonelActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivityForResult(intent, REQ_LIDIK)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val personel = data?.getParcelableExtra<PersonelModel>("ID_PERSONEL")
        if (resultCode == Activity.RESULT_OK && requestCode == REQ_LIDIK) {
//            lidikReq.id = personel?.id
            penyelidikResp.nrp = personel?.nrp
            penyelidikResp.id_satuan_kerja = personel?.id_satuan_kerja
            penyelidikResp.pangkat = personel?.pangkat
            penyelidikResp.id_personel = personel?.id
            penyelidikResp.nama = personel?.nama
            penyelidikResp.jabatan = personel?.jabatan
            penyelidikResp.kesatuan = personel?.satuan_kerja?.kesatuan
            idPersonel = personel?.id

//set lidik req
            lidikReq.id_personel = penyelidikResp.id_personel

            txt_nama_lidik_add.text = "Nama : ${personel?.nama}"
            txt_pangkat_lidik_add.text = "Pangkat : ${personel?.pangkat}"
            txt_nrp_lidik_add.text = "NRP : ${personel?.nrp}"
            txt_jabatan_lidik_add.text = "Jabatan : ${personel?.jabatan}"
            txt_kesatuan_lidik_add.text = "Kesatuan : ${personel?.satuan_kerja?.kesatuan}"
        }
    }

    companion object {
        const val REQ_LIDIK = 129
        const val LIDIK = "LIDIK"
        const val LIDIK_REQ = "LIDIK_REQ"
    }
}