package id.calocallo.sicape.ui.manajemen

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.PersonelOperatorReq
import id.calocallo.sicape.network.request.SipilOperatorReq
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.ui.kesatuan.polda.PoldaActivity
import id.calocallo.sicape.ui.kesatuan.polres.PolresActivity
import id.calocallo.sicape.ui.kesatuan.polres.SatPolresActivity
import id.calocallo.sicape.ui.kesatuan.polsek.PolsekActivity
import id.calocallo.sicape.ui.kesatuan.polsek.SatPolsekActivity
import id.calocallo.sicape.ui.main.addpersonal.AddPersonelActivity
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.hideKeyboard
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.utils.ext.*
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
    private var namaPersonel: String? = null
    private var nrpPersonel: String? = null
    private var personelOperatorReq = PersonelOperatorReq()
    private var sipilOperatorReq = SipilOperatorReq()
    private lateinit var sheenValidator: SheenValidator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_operator)
        sessionManager1 = SessionManager1(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Operator Polisi"

        sheenValidator = SheenValidator(this)
        sheenValidator.registerHasMinLength(edt_password_operator, 6)
        sheenValidator.registerHasMinLength(edt_repassword_operator, 6)

        changeButton()

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
                personelOperatorReq.nama = namaPersonel
                personelOperatorReq.username = nrpPersonel
                personelOperatorReq.password = edt_password_operator.text.toString()
                personelOperatorReq.password_confirmation =
                    edt_repassword_operator.text.toString()
                personelOperatorReq.is_aktif = idStatus
                personelOperatorReq.id_personel = idPersonel
                personelOperatorReq.id_satuan_kerja = idSatker

                Log.e("add_operator", "$personelOperatorReq")
                addPolisiOperator(personelOperatorReq)


            }
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
        spinner_status_operator.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> idStatus = 1
                1 -> idStatus = 0
            }
        }


    }

    private fun changeButton() {
        btn_personel_add_polda_polisi_oper.setOnClickListener {
            btn_personel_add_polda_polisi_oper.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary))
            btn_personel_add_polda_polisi_oper.setTextColor(ContextCompat.getColor(this,R.color.white))

            btn_personel_add_polre_polisi_oper.setBackgroundColor(ContextCompat.getColor(this,R.color.white))
            btn_personel_add_polre_polisi_oper.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary))

            btn_personel_add_polsek_polisi_oper.setBackgroundColor(ContextCompat.getColor(this,R.color.white))
            btn_personel_add_polsek_polisi_oper.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary))
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
            btn_personel_add_polre_polisi_oper.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary))
            btn_personel_add_polre_polisi_oper.setTextColor(ContextCompat.getColor(this,R.color.white))

            btn_personel_add_polda_polisi_oper.setBackgroundColor(ContextCompat.getColor(this,R.color.white))
            btn_personel_add_polda_polisi_oper.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary))

            btn_personel_add_polsek_polisi_oper.setBackgroundColor(ContextCompat.getColor(this,R.color.white))
            btn_personel_add_polda_polisi_oper.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary))
            startActivityForResult(
                Intent(this, PolresActivity::class.java),
                AddPersonelActivity.REQ_POLRES
            )
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

            txt_satker_polisi_oper_add.gone()
            txt_satker_polisi_oper_add.text = ""
        }
        btn_personel_add_polsek_polisi_oper.setOnClickListener {
            btn_personel_add_polsek_polisi_oper.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary))
            btn_personel_add_polsek_polisi_oper.setTextColor(ContextCompat.getColor(this,R.color.white))

            btn_personel_add_polda_polisi_oper.setBackgroundColor(ContextCompat.getColor(this,R.color.white))
            btn_personel_add_polda_polisi_oper.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary))

            btn_personel_add_polre_polisi_oper.setBackgroundColor(ContextCompat.getColor(this,R.color.white))
            btn_personel_add_polre_polisi_oper.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary))
            val intent = Intent(this, PolsekActivity::class.java)
            startActivityForResult(intent, AddPersonelActivity.REQ_POLSEK)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

            txt_satker_polisi_oper_add.gone()
            txt_satker_polisi_oper_add.text = ""
        }
    }

    private fun addPolisiOperator(personelOperatorReq: PersonelOperatorReq) {
        NetworkConfig().getServUser().addPersOperator(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            personelOperatorReq
        ).enqueue(object : Callback<Base1Resp<UserResp>> {
            override fun onFailure(call: Call<Base1Resp<UserResp>>, t: Throwable) {
                btn_save_operator.hideProgress(R.string.error_conn)
            }

            override fun onResponse(
                call: Call<Base1Resp<UserResp>>,
                response: Response<Base1Resp<UserResp>>
            ) {
                if (response.body()?.message == "Data personel operator saved succesfully") {
                    hideKeyboard()
                    btn_save_operator.hideProgress(R.string.data_saved)
                    btn_save_operator.showSnackbar(R.string.data_saved) {
                        action(R.string.back) {
                            val intent = Intent(this@AddOperatorActivity, ManajemenOperatorActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            startActivity(intent)
                        }
                    }
                } else {
                    toast("${response.body()?.message}")
                    btn_save_operator.hideProgress("Gagal Simpan / Sudah Terdaftar")
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
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
                    namaPersonel = personel?.nama
                    nrpPersonel = personel?.nrp
                }
            }
        }
    }
}