package id.calocallo.sicape.ui.main.lhp.add

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.PersonelPenyelidikReq
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.ui.main.lhp.edit.lidik.AddSingleLidikLhpActivity
import id.calocallo.sicape.ui.main.lhp.edit.lidik.PickLidikLhpActivity
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.utils.LhpDataManager
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_personel_lidik.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddPersonelLidikActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private lateinit var lhpDataManager: LhpDataManager
    private var idPersonel: Int? = null
    private var penyelidikResp = PersonelPenyelidikResp()
    private var lidikReq = PersonelPenyelidikReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_personel_lidik)
        setupActionBarWithBackButton(toolbar)
        sessionManager1 = SessionManager1(this)
        lhpDataManager = LhpDataManager(this)

        val addLidik = intent.extras?.getString(AddSingleLidikLhpActivity.ADD_LIDIK)
        if (addLidik.isNullOrEmpty()) {
            supportActionBar?.title = "Tambah Data Laporan Hasil Penyelidikan"
        } else {
            supportActionBar?.title = "Tambah Data Personel Penyelidikan"
        }
        val dataLhp = intent.getParcelableExtra<LhpMinResp>(PickLidikLhpActivity.DATA_LHP)
        //set save add lidik
        btn_save_single_lidik.attachTextChangeAnimator()
        bindProgressButton(btn_save_single_lidik)
        btn_save_single_lidik.setOnClickListener {
            btn_save_single_lidik.showProgress {
                progressColor = Color.WHITE
            }
            if (addLidik.isNullOrEmpty()) {
                val intent = Intent()
                intent.putExtra(LIDIK, penyelidikResp)
                intent.putExtra(LIDIK_REQ, lidikReq)
                setResult(RESULT_OK, intent)
                finish()
            } else {
                Log.e("addSingleLidik", "$lidikReq")
                addSingleLidik(dataLhp)
            }
        }
        //set Radio Group
        rg_lidik.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            if (radio.isChecked) {
                if (radio.text == "Ketua Tim") {
                    penyelidikResp.is_ketua = 1
                    lidikReq.is_ketua = penyelidikResp.is_ketua
                } else {
                    penyelidikResp.is_ketua = 0
                    lidikReq.is_ketua = penyelidikResp.is_ketua
                }
            }

        }

        //set Pick Personel
        btn_choose_personel_lidik.setOnClickListener {
            val intent = Intent(this, KatPersonelActivity::class.java)
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, REQ_LIDIK)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun addSingleLidik(dataLhp: LhpMinResp?) {
        NetworkConfig().getServLhp()
            .addPersLidik("Bearer ${sessionManager1.fetchAuthToken()}", dataLhp?.id, lidikReq)
            .enqueue(
                object :
                    Callback<Base1Resp<AddPersLidikResp>> {
                    override fun onResponse(
                        call: Call<Base1Resp<AddPersLidikResp>>,
                        response: Response<Base1Resp<AddPersLidikResp>>
                    ) {
                        if (response.body()?.message == "Data personel penyelidik saved succesfully") {
                            btn_save_single_lidik.hideProgress(R.string.data_saved)
                            Handler(Looper.getMainLooper()).postDelayed({
                                finish()
                            },1000)
                        } else {
                            btn_save_single_lidik.hideProgress(R.string.not_save)
                        }
                    }

                    override fun onFailure(call: Call<Base1Resp<AddPersLidikResp>>, t: Throwable) {
                        btn_save_single_lidik.hideProgress(R.string.not_save)
                        Toast.makeText(this@AddPersonelLidikActivity, "$t", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val personel = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
        if (resultCode == Activity.RESULT_OK && requestCode == REQ_LIDIK) {
            lidikReq.id_personel = personel?.id
            lidikReq.nama_personel = personel?.nama

            /*for addOnLhpAll*/
//            penyelidikResp.personel?.nama = personel?.nama
//            penyelidikResp.personel?.id = personel?.id
            /*  penyelidikResp.nrp = personel?.nrp
              penyelidikResp.id_satuan_kerja = personel?.satuan_kerja?.id
              penyelidikResp.pangkat = personel?.pangkat.toString().toUpperCase()
              penyelidikResp.personel = personel?.id
              penyelidikResp.nama = personel?.nama
              penyelidikResp.jabatan = personel?.jabatan
              penyelidikResp.kesatuan = personel?.satuan_kerja?.kesatuan.toString().toUpperCase()
              idPersonel = personel?.id*/

//set lidik req
//            lidikReq.id_personel = penyelidikResp.personel?.id
            txt_nama_lidik_add.text = "Nama : ${personel?.nama}"
            txt_pangkat_lidik_add.text = "Pangkat : ${personel?.pangkat.toString().toUpperCase()}"
            txt_nrp_lidik_add.text = "NRP : ${personel?.nrp}"
            txt_jabatan_lidik_add.text = "Jabatan : ${personel?.jabatan}"
            txt_kesatuan_lidik_add.text =
                "Kesatuan : ${personel?.satuan_kerja?.kesatuan.toString().toUpperCase()}"
        }
    }

    companion object {
        const val REQ_LIDIK = 129
        const val LIDIK = "LIDIK"
        const val LIDIK_REQ = "LIDIK_REQ"
    }
}