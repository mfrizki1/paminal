package id.calocallo.sicape.ui.manajemen

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.github.razir.progressbutton.hideProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.PersonelOperatorReq
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.ui.kesatuan.polda.PoldaActivity
import id.calocallo.sicape.ui.kesatuan.polres.PolresActivity
import id.calocallo.sicape.ui.kesatuan.polres.SatPolresActivity
import id.calocallo.sicape.ui.kesatuan.polsek.PolsekActivity
import id.calocallo.sicape.ui.kesatuan.polsek.SatPolsekActivity
import id.calocallo.sicape.ui.main.addpersonal.AddPersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.action
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.showSnackbar
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_operator.*
import kotlinx.android.synthetic.main.activity_add_personel.*
import kotlinx.android.synthetic.main.activity_edit_operator.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditOperatorActivity : BaseActivity() {
    private var personelOperatorReq = PersonelOperatorReq()
    private lateinit var sessionManager1: SessionManager1
    private var idSatker: Int? = null
    private var isAktif: Int? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_operator)
        sessionManager1 = SessionManager1(this)
        setupActionBarWithBackButton(toolbar)
//        val status = intent.extras?.getString("manajemen")
//        when (status) {
//            "operator" -> {
        supportActionBar?.title = "Edit Operator"
        val operator = intent.extras?.getParcelable<HakAksesPersonel1>("operator")
        Log.e("operator", "$operator")
        with(operator) {
            txt_nama_personel_operator_edit.text = "Nama : ${this?.personel?.nama}"
            txt_pangkat_personel_operator_edit.text =
                "Pangkat : ${this?.personel?.pangkat.toString().toUpperCase()}"
            txt_nrp_personel_operator_edit.text = "NRP : ${this?.personel?.nrp}"
            txt_jabatan_personel_operator_edit.text = "Jabatan : ${this?.personel?.jabatan}"
//            spinner_kesatuan_operator_edit.setText(this?.satuan_kerja?.kesatuan)
            if (this?.is_aktif == 1) {
                spinner_status_operator_edit.setText("Aktif")
                isAktif = this?.is_aktif
            } else {
                spinner_status_operator_edit.setText("Tidak")
                isAktif = this?.is_aktif
            }
            txt_satker_polisi_oper_edit.visible()
            txt_satker_polisi_oper_edit.text = this?.satuan_kerja?.kesatuan
            idSatker = this?.satuan_kerja?.id
        }
        /*}
        "admin" -> {
            supportActionBar?.title = "Edit Admin"
            ll_kesatuan_operator_edit.gone()
            admin = intent.extras?.getParcelable("acc")!!
            with(admin){
                txt_nama_personel_operator_edit.text = "Nama : ${personel?.nama}"
                txt_pangkat_personel_operator_edit.text = "Pangkat : ${personel?.pangkat.toString().toUpperCase()}"
                txt_nrp_personel_operator_edit.text ="NRP : ${personel?.nrp}"
                txt_jabatan_personel_operator_edit.text = "Jabatan : ${personel?.jabatan}"
                spinner_kesatuan_operator_edit.setText(personel?.kesatuan)
                if(is_aktif == 1){
                    spinner_status_operator_edit.setText("Aktif")
                }else{
                    spinner_status_operator_edit.setText("Tidak")
                }
            }
        }*/


        btn_personel_edit_polda_polisi_oper.setOnClickListener {
            btn_personel_edit_polda_polisi_oper.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            btn_personel_edit_polda_polisi_oper.setTextColor(resources.getColor(R.color.white))

            btn_personel_edit_polre_polisi_oper.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_edit_polre_polisi_oper.setTextColor(resources.getColor(R.color.colorPrimary))

            btn_personel_edit_polsek_polisi_oper.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_edit_polsek_polisi_oper.setTextColor(resources.getColor(R.color.colorPrimary))
            startActivityForResult(
                Intent(this, PoldaActivity::class.java), AddPersonelActivity.REQ_POLDA
            )
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            txt_satker_polisi_oper_edit.gone()
            txt_satker_polisi_oper_edit.text = ""
        }
        btn_personel_edit_polre_polisi_oper.setOnClickListener {
            btn_personel_edit_polre_polisi_oper.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            btn_personel_edit_polre_polisi_oper.setTextColor(resources.getColor(R.color.white))

            btn_personel_edit_polda_polisi_oper.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_edit_polda_polisi_oper.setTextColor(resources.getColor(R.color.colorPrimary))

            btn_personel_edit_polsek_polisi_oper.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_edit_polsek_polisi_oper.setTextColor(resources.getColor(R.color.colorPrimary))
            startActivityForResult(
                Intent(this, PolresActivity::class.java),
                AddPersonelActivity.REQ_POLRES
            )
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

            txt_satker_polisi_oper_edit.gone()
            txt_satker_polisi_oper_edit.text = ""
        }
        btn_personel_edit_polsek_polisi_oper.setOnClickListener {
            btn_personel_edit_polsek_polisi_oper.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            btn_personel_edit_polsek_polisi_oper.setTextColor(resources.getColor(R.color.white))

            btn_personel_edit_polre_polisi_oper.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_edit_polre_polisi_oper.setTextColor(resources.getColor(R.color.colorPrimary))

            btn_personel_edit_polda_polisi_oper.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_edit_polda_polisi_oper.setTextColor(resources.getColor(R.color.colorPrimary))
            val intent = Intent(this, PolsekActivity::class.java)
            startActivityForResult(intent, AddPersonelActivity.REQ_POLSEK)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            txt_satker_polisi_oper_edit.gone()
            txt_satker_polisi_oper_edit.text = ""
        }

        btn_choose_personel_operator_edit
        txt_nama_personel_operator_edit
        txt_pangkat_personel_operator_edit
        txt_nrp_personel_operator_edit
        txt_jabatan_personel_operator_edit
        ll_kesatuan_operator_edit

        edt_password_new_operator_edit

        edt_password_renew_operator_edit
        val item = listOf("Aktif", "Tidak")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        spinner_status_operator_edit.setAdapter(adapter)
        spinner_status_operator_edit.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> isAktif = 1
                1 -> isAktif = 0
            }
        }
        btn_save_operator_edit.setOnClickListener {
            personelOperatorReq.id_satuan_kerja = idSatker
            personelOperatorReq.is_aktif = isAktif
            personelOperatorReq.password = edt_password_new_operator_edit.text.toString()
            personelOperatorReq.password_confirmation =
                edt_password_renew_operator_edit.text.toString()
            Log.e("personelReq", "$personelOperatorReq")

            apiUpdateOperator(operator)
        }
    }

    private fun apiUpdateOperator(operator: HakAksesPersonel1?) {
        NetworkConfig().getService().updPersOperator(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            operator?.id.toString(), personelOperatorReq
        ).enqueue(object : Callback<Base1Resp<PersonelModelMax2>> {
            override fun onFailure(call: Call<Base1Resp<PersonelModelMax2>>, t: Throwable) {
                Toast.makeText(this@EditOperatorActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<Base1Resp<PersonelModelMax2>>,
                response: Response<Base1Resp<PersonelModelMax2>>
            ) {
               if(response.isSuccessful){
                   btn_save_operator_edit.hideProgress(R.string.data_updated)
                   btn_save_operator_edit.showSnackbar(R.string.data_updated){
                       action(R.string.back){
                           finish()
                       }
                   }
               }else{
                   Toast.makeText(this@EditOperatorActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
               }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            AddPersonelActivity.REQ_POLDA -> {
                if (resultCode == AddPersonelActivity.RES_POLDA) {
                    val polda = data?.getParcelableExtra<SatKerResp>(PoldaActivity.DATA_POLDA)
                    txt_satker_polisi_oper_edit.visible()
                    txt_satker_polisi_oper_edit.text = "Satuan Kerja : ${polda?.kesatuan}"
                    idSatker = polda?.id
                } else {
                    txt_satker_polisi_oper_edit.gone()
                }
            }
            AddPersonelActivity.REQ_POLRES -> {
                if (resultCode == AddPersonelActivity.RES_POLRES) {
                    val polres =
                        data?.getParcelableExtra<SatKerResp>(SatPolresActivity.DATA_POLRES_SAT)
                    txt_satker_polisi_oper_edit.visible()
                    txt_satker_polisi_oper_edit.text = "Kepolisian Resort : ${polres?.kesatuan}"
                    idSatker = polres?.id
                    /*val polres = data?.extras
                                     val dataPolres = polres?.getParcelable<SatPolresResp>(SatPolresActivity.DATA_POLRES_SAT)
                                     txt_sat_polres_add.visible()
                                     txt_polres_add.visible()
                                     txt_sat_polres_add.text =
                                         "Satuan Kerja  : ${dataPolres?.nama_satuan_res}"
                                     txt_polres_add.text =
                                         "Resort : ${polres?.getString(PolresActivity.DATA_POLRES)}"
                                     jenisKesatuan = "polres"
                                     idSatker = dataPolres?.id*/
                } else {
                    txt_satker_polisi_oper_edit.gone()
//                    txt_polres_add.gone()
                }
            }
            AddPersonelActivity.REQ_POLSEK -> {
                if (resultCode == AddPersonelActivity.RES_POLSEK) {
                    val polsek = data?.getParcelableExtra<SatKerResp>(SatPolsekActivity.UNIT_POLSEK)
                    txt_satker_polisi_oper_edit.visible()
                    txt_satker_polisi_oper_edit.text = "Kepolisian Sektor : ${polsek?.kesatuan}"
                    idSatker = polsek?.id
                    /* val polsek = data?.extras
                     val dataPolsek = polsek?.getParcelable<UnitPolsekResp>(SatPolsekActivity.UNIT_POLSEK)
                     txt_sat_polsek_add.visible()
                     txt_polsek_add.visible()

                     txt_sat_polsek_add.text =
                         "Unit : ${dataPolsek?.nama_unit}"
                     txt_polsek_add.text =
                         "Sektor : ${polsek?.getString(PolsekActivity.NAMA_POLSEK)}"
                     jenisKesatuan = "polsek"
                     idSatker = dataPolsek?.id*/
                } else {
                    txt_sat_polsek_add.gone()
                    txt_polsek_add.gone()
                }
            }
            AddOperatorActivity.REQ_PERSONEL_OPERATOR -> {
                if (resultCode == Activity.RESULT_OK) {
                    val personel = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
                    txt_nama_personel_operator_add.text = "Nama ${personel?.nama}"
                    txt_pangkat_personel_operator_add.text =
                        "Pangkat : ${personel?.pangkat.toString().toUpperCase()}"
                    txt_nrp_personel_operator_add.text = "NRP : ${personel?.nrp}"
                    txt_jabatan_personel_operator_add.text = "Jabatan : ${personel?.jabatan}"
//                    idPersonel = personel?.id
                }
            }
        }
    }
}