package id.calocallo.sicape.ui.manajemen

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.PersonelOperatorReq
import id.calocallo.sicape.network.request.SipilOperatorReq
import id.calocallo.sicape.network.response.PersonelMinResp
import id.calocallo.sicape.network.response.PersonelModelMax2
import id.calocallo.sicape.network.response.SatKerResp
import id.calocallo.sicape.ui.kesatuan.polda.PoldaActivity
import id.calocallo.sicape.ui.kesatuan.polres.PolresActivity
import id.calocallo.sicape.ui.kesatuan.polres.SatPolresActivity
import id.calocallo.sicape.ui.kesatuan.polsek.PolsekActivity
import id.calocallo.sicape.ui.kesatuan.polsek.SatPolsekActivity
import id.calocallo.sicape.ui.login.KatUserActivity
import id.calocallo.sicape.ui.main.MainActivity
import id.calocallo.sicape.ui.main.addpersonal.AddPersonelActivity
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.ui.main.profil.ProfilFragment
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.action
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.showSnackbar
import id.calocallo.sicape.utils.ext.visible
import id.calocallo.sicape.utils.hideKeyboard
import id.co.iconpln.smartcity.ui.base.BaseActivity
import id.rizmaulana.sheenvalidator.lib.SheenValidator
import kotlinx.android.synthetic.main.activity_add_operator.*
import kotlinx.android.synthetic.main.activity_add_personel.*
import kotlinx.android.synthetic.main.activity_change_pass.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddOperatorActivity : BaseActivity() {
    companion object {
        const val REQ_PERSONEL_OPERATOR = 123
    }

    private var idSatker: Int? = null
    private lateinit var sessionManager1: SessionManager1
    private var idPersonel: Int? = null
    private var idKesatuan: Int? = null
    private var idStatus: Int? = null
    private var personelOperatorReq = PersonelOperatorReq()
    private var sipilOperatorReq = SipilOperatorReq()
    private lateinit var sheenValidator: SheenValidator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_operator)
        sessionManager1 = SessionManager1(this)
        sheenValidator = SheenValidator(this)

        setupActionBarWithBackButton(toolbar)
        val status = intent.extras?.getString("manajemen")
        sheenValidator.registerHasMinLength(edt_password_operator, 6)

        sheenValidator.registerHasMinLength(edt_repassword_operator, 6)

        when (status) {
            "polisi" -> {
                supportActionBar?.title = "Tambah Operator Polisi"
                btn_save_operator.attachTextChangeAnimator()
                bindProgressButton(btn_save_operator)
                btn_save_operator.setOnClickListener {
                    sheenValidator.validate()
                    btn_save_operator.showProgress {
                        progressColor = Color.WHITE
                    }
                    if (edt_password_operator.text.toString() != edt_repassword_operator.text.toString()) {
                        btn_save_operator.hideProgress("Password Harus Sama")
                    } else if (edt_password_operator.text.toString() == edt_repassword_operator.text.toString()) {
                        personelOperatorReq.password = edt_password_operator.text.toString()
                        personelOperatorReq.password_confirmation =
                            edt_repassword_operator.text.toString()
                        personelOperatorReq.is_aktif = idStatus
                        personelOperatorReq.id_satuan_kerja = idKesatuan
                        personelOperatorReq.id_personel = idPersonel
                        personelOperatorReq.id_satuan_kerja = idSatker

                        Log.e("add_operator", "$personelOperatorReq")
                        addPolisiOperator(personelOperatorReq)


                    }
                }
            }
            /*    "sipil" -> {
                    supportActionBar?.title = "Tambah Operator Sipil"
                    btn_save_operator.attachTextChangeAnimator()
                    bindProgressButton(btn_save_operator)
                    btn_save_operator.setOnClickListener {
                        sheenValidator.validate()

                        if (edt_password_operator.text.toString() != edt_repassword_operator.text.toString()) {
                            Log.e("notSame", "notSame")
                            Toast.makeText(this, "Password Harus Sama", Toast.LENGTH_LONG).show()
                        } else if (edt_password_operator.text.toString() == edt_repassword_operator.text.toString()) {
                            personelOperatorReq.password = edt_password_operator.text.toString()
                            personelOperatorReq.password_confirmation =
                                edt_repassword_operator.text.toString()
                            personelOperatorReq.is_aktif = idStatus
                            personelOperatorReq.id_satuan_kerja = idKesatuan
                            personelOperatorReq.id_personel = idPersonel

                            Log.e("add_operator", "$personelOperatorReq")
                            addSipilOperator()
                        }
                    }
                }*/
           /* "admin" -> {
                supportActionBar?.title = "Tambah Admin"
                ll_kesatuan_operator.gone()
                btn_save_operator.attachTextChangeAnimator()
                bindProgressButton(btn_save_operator)
                btn_save_operator.setOnClickListener {
                    if (edt_password_operator.text.toString() != edt_repassword_operator.text.toString()) {
                        Log.e("notSame", "notSame")
                        Toast.makeText(this, "Password Harus Sama", Toast.LENGTH_SHORT).show()
                    } else if (edt_password_operator.text.toString() == edt_repassword_operator.text.toString()) {
                        personelOperatorReq.password = edt_password_operator.text.toString()
                        personelOperatorReq.password_confirmation =
                            edt_repassword_operator.text.toString()
                        personelOperatorReq.is_aktif = idStatus
                        personelOperatorReq.id_satuan_kerja = idKesatuan
                        personelOperatorReq.id_personel = idPersonel

                        Log.e("add_operator", "$personelOperatorReq")
                        addAdmin()
                    }
                }
            }*/
        }
        btn_choose_personel_operator.setOnClickListener {
            val intent = Intent(this, KatPersonelActivity::class.java)
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, REQ_PERSONEL_OPERATOR)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        }


        val item = listOf("Aktif", "Tidak")
        val adapterStatus = ArrayAdapter(this, R.layout.item_spinner, item)
        spinner_status_operator.setAdapter(adapterStatus)
        spinner_status_operator.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> idStatus = 1
                1 -> idStatus = 0
            }
        }



        btn_personel_add_polda_polisi_oper.setOnClickListener {
            btn_personel_add_polda_polisi_oper.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            btn_personel_add_polda_polisi_oper.setTextColor(resources.getColor(R.color.white))

            btn_personel_add_polre_polisi_oper.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_add_polre_polisi_oper.setTextColor(resources.getColor(R.color.colorPrimary))

            btn_personel_add_polsek_polisi_oper.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_add_polsek_polisi_oper.setTextColor(resources.getColor(R.color.colorPrimary))
            startActivityForResult(
                Intent(this, PoldaActivity::class.java), AddPersonelActivity.REQ_POLDA
            )
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

            Log.e("polda text", "${txt_satker_polisi_oper_add.text}")
            Log.e("polres text", "${txt_satker_polisi_oper_add.text}")
            Log.e("polsek text", "${txt_satker_polisi_oper_add.text}")
            txt_satker_polisi_oper_add.gone()
            txt_satker_polisi_oper_add.text = ""
        }
        btn_personel_add_polre_polisi_oper.setOnClickListener {
            btn_personel_add_polre_polisi_oper.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            btn_personel_add_polre_polisi_oper.setTextColor(resources.getColor(R.color.white))

            btn_personel_add_polda_polisi_oper.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_add_polda_polisi_oper.setTextColor(resources.getColor(R.color.colorPrimary))

            btn_personel_add_polsek_polisi_oper.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_add_polda_polisi_oper.setTextColor(resources.getColor(R.color.colorPrimary))
            startActivityForResult(
                Intent(this, PolresActivity::class.java),
                AddPersonelActivity.REQ_POLRES
            )
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

            txt_satker_polisi_oper_add.gone()
            txt_satker_polisi_oper_add.text = ""
        }
        btn_personel_add_polsek_polisi_oper.setOnClickListener {
            btn_personel_add_polsek_polisi_oper.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            btn_personel_add_polsek_polisi_oper.setTextColor(resources.getColor(R.color.white))

            btn_personel_add_polda_polisi_oper.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_add_polda_polisi_oper.setTextColor(resources.getColor(R.color.colorPrimary))

            btn_personel_add_polre_polisi_oper.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_add_polre_polisi_oper.setTextColor(resources.getColor(R.color.colorPrimary))
            val intent = Intent(this, PolsekActivity::class.java)
            startActivityForResult(intent, AddPersonelActivity.REQ_POLSEK)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

            txt_satker_polisi_oper_add.gone()
            txt_satker_polisi_oper_add.text = ""
        }
    }

    private fun addPolisiOperator(personelOperatorReq: PersonelOperatorReq) {
        NetworkConfig().getService().addPersOperator(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            personelOperatorReq
        ).enqueue(object : Callback<PersonelModelMax2> {
            override fun onFailure(call: Call<PersonelModelMax2>, t: Throwable) {
                btn_save_operator.hideProgress("Error Koneksi")
            }

            override fun onResponse(
                call: Call<PersonelModelMax2>,
                response: Response<PersonelModelMax2>
            ) {
                if (response.isSuccessful) {
                    hideKeyboard()
                    btn_save_operator.hideProgress(R.string.data_saved)
                    btn_save_operator.showSnackbar(R.string.data_saved) {
                        action(R.string.back) {
                            startActivity(
                                Intent(this@AddOperatorActivity, MainActivity::class.java)
                            )
                        }
                    }
                } else {
                    btn_save_operator.hideProgress("Gagal Simpan / Sudah Terdaftar")
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
                    txt_satker_polisi_oper_add.visible()
                    txt_satker_polisi_oper_add.text = "Satuan Kerja : ${polda?.kesatuan}"
                    idSatker = polda?.id
                } else {
                    txt_satker_polisi_oper_add.gone()
                }
            }
            AddPersonelActivity.REQ_POLRES -> {
                if (resultCode == AddPersonelActivity.RES_POLRES) {
                    val polres =
                        data?.getParcelableExtra<SatKerResp>(SatPolresActivity.DATA_POLRES_SAT)
                    txt_satker_polisi_oper_add.visible()
                    txt_satker_polisi_oper_add.text = "Kepolisian Resort : ${polres?.kesatuan}"
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
                    txt_satker_polisi_oper_add.gone()
//                    txt_polres_add.gone()
                }
            }
            AddPersonelActivity.REQ_POLSEK -> {
                if (resultCode == AddPersonelActivity.RES_POLSEK) {
                    val polsek = data?.getParcelableExtra<SatKerResp>(SatPolsekActivity.UNIT_POLSEK)
                    txt_satker_polisi_oper_add.visible()
                    txt_satker_polisi_oper_add.text = "Kepolisian Sektor : ${polsek?.kesatuan}"
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
            REQ_PERSONEL_OPERATOR -> {
                if (resultCode == Activity.RESULT_OK) {
                    val personel = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
                    txt_nama_personel_operator_add.text = "Nama ${personel?.nama}"
                    txt_pangkat_personel_operator_add.text =
                        "Pangkat : ${personel?.pangkat.toString().toUpperCase()}"
                    txt_nrp_personel_operator_add.text = "NRP : ${personel?.nrp}"
                    txt_jabatan_personel_operator_add.text = "Jabatan : ${personel?.jabatan}"
                    idPersonel = personel?.id
                }
            }
        }
    }
}