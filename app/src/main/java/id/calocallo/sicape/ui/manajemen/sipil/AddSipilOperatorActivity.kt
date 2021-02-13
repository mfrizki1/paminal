package id.calocallo.sicape.ui.manajemen.sipil

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.SipilOperatorReq
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.HakAksesSipil
import id.calocallo.sicape.network.response.PersonelMinResp
import id.calocallo.sicape.network.response.SatKerResp
import id.calocallo.sicape.ui.kesatuan.polda.PoldaActivity
import id.calocallo.sicape.ui.kesatuan.polres.PolresActivity
import id.calocallo.sicape.ui.kesatuan.polres.SatPolresActivity
import id.calocallo.sicape.ui.kesatuan.polsek.PolsekActivity
import id.calocallo.sicape.ui.kesatuan.polsek.SatPolsekActivity
import id.calocallo.sicape.ui.main.addpersonal.AddPersonelActivity
import id.calocallo.sicape.ui.manajemen.AddOperatorActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.action
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.showSnackbar
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import id.rizmaulana.sheenvalidator.lib.SheenValidator
import kotlinx.android.synthetic.main.activity_add_operator.*
import kotlinx.android.synthetic.main.activity_add_personel.*
import kotlinx.android.synthetic.main.activity_add_sipil_operator.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddSipilOperatorActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var isAktif: Int? = null
    private var idSatker: Int? = null
    private var sipilOperatorReq = SipilOperatorReq()
    private lateinit var sheenValidator: SheenValidator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_sipil_operator)
        sessionManager1 = SessionManager1(this)
        sheenValidator = SheenValidator(this)

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Operator Sipil"


        btn_personel_add_polda_sipil_oper.setOnClickListener {
            btn_personel_add_polda_sipil_oper.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            btn_personel_add_polda_sipil_oper.setTextColor(resources.getColor(R.color.white))

            btn_personel_add_polres_sipil_oper.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_add_polres_sipil_oper.setTextColor(resources.getColor(R.color.colorPrimary))

            btn_personel_add_polsek_sipil_oper.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_add_polsek_sipil_oper.setTextColor(resources.getColor(R.color.colorPrimary))
            startActivityForResult(
                Intent(this, PoldaActivity::class.java), AddPersonelActivity.REQ_POLDA
            )
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            txt_satker_sipil_oper_add.gone()
            txt_satker_sipil_oper_add.text = ""
        }
        btn_personel_add_polres_sipil_oper.setOnClickListener {
            btn_personel_add_polda_sipil_oper.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_add_polda_sipil_oper.setTextColor(resources.getColor(R.color.colorPrimary))

            btn_personel_add_polres_sipil_oper.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            btn_personel_add_polres_sipil_oper.setTextColor(resources.getColor(R.color.white))

            btn_personel_add_polsek_sipil_oper.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_add_polsek_sipil_oper.setTextColor(resources.getColor(R.color.colorPrimary))
            startActivityForResult(
                Intent(this, PolresActivity::class.java),
                AddPersonelActivity.REQ_POLRES
            )
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            txt_satker_sipil_oper_add.gone()
            txt_satker_sipil_oper_add.text = ""
        }
        btn_personel_add_polsek_sipil_oper.setOnClickListener {
            btn_personel_add_polda_sipil_oper.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_add_polda_sipil_oper.setTextColor(resources.getColor(R.color.colorPrimary))

            btn_personel_add_polres_sipil_oper.setBackgroundColor(resources.getColor(R.color.white))
            btn_personel_add_polres_sipil_oper.setTextColor(resources.getColor(R.color.colorPrimary))

            btn_personel_add_polsek_sipil_oper.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            btn_personel_add_polsek_sipil_oper.setTextColor(resources.getColor(R.color.white))
            val intent = Intent(this, PolsekActivity::class.java)
            startActivityForResult(intent, AddPersonelActivity.REQ_POLSEK)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            txt_satker_sipil_oper_add.gone()
            txt_satker_sipil_oper_add.text = ""
        }


        val item = listOf("Masih", "Tidak")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        spinner_status_operator_sipil.setAdapter(adapter)
        spinner_status_operator_sipil.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> isAktif = 1
                1 -> isAktif = 0
            }
        }
        sheenValidator.registerHasMinLength(edt_password_operator_sipil, 6)
        sheenValidator.registerHasMinLength(edt_repassword_operator_sipil, 6)

        bindProgressButton(btn_save_operator_sipil)
        btn_save_operator_sipil.attachTextChangeAnimator()
        btn_save_operator_sipil.setOnClickListener {
            sheenValidator.validate()
            btn_save_operator_sipil.showProgress {
                progressColor = Color.WHITE
            }
            if (edt_password_operator_sipil.text.toString() != edt_repassword_operator_sipil.text.toString()) {
                btn_save_operator_sipil.hideProgress("Password Harus Sama")
            } else {
                addSipil()
            }

        }

    }

    private fun addSipil() {


        sipilOperatorReq.nama = edt_nama_operator_sipil.text.toString()
        sipilOperatorReq.alamat = edt_alamat_operator_sipil.text.toString()
        sipilOperatorReq.password = edt_password_operator_sipil.text.toString()
        sipilOperatorReq.password_confirmation = edt_repassword_operator_sipil.text.toString()
        sipilOperatorReq.id_satuan_kerja = idSatker
        sipilOperatorReq.is_aktif = isAktif
        Log.e("sipil add", "$sipilOperatorReq")
        NetworkConfig().getService().addSipilOperator(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            sipilOperatorReq
        ).enqueue(object : Callback<Base1Resp<HakAksesSipil>> {
            override fun onFailure(call: Call<Base1Resp<HakAksesSipil>>, t: Throwable) {
                btn_save_operator_sipil.hideDrawable("Gagal Simpan")
            }

            override fun onResponse(
                call: Call<Base1Resp<HakAksesSipil>>,
                response: Response<Base1Resp<HakAksesSipil>>
            ) {
                if (response.isSuccessful) {
                    btn_save_operator_sipil.hideDrawable(R.string.data_saved)
                    btn_save_operator_sipil.showSnackbar(R.string.data_saved) {
                        action(R.string.back) {
                            finish()
                        }
                    }

                } else {
                    btn_save_operator_sipil.hideDrawable("Gagal Simpan / Sudah Terdaftar")
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
                    txt_satker_sipil_oper_add.visible()
                    txt_satker_sipil_oper_add.text = "Satuan Kerja : ${polda?.kesatuan}"
                    idSatker = polda?.id
                } else {
                    txt_satker_sipil_oper_add.gone()
                }
            }
            AddPersonelActivity.REQ_POLRES -> {
                if (resultCode == AddPersonelActivity.RES_POLRES) {
                    val polres =
                        data?.getParcelableExtra<SatKerResp>(SatPolresActivity.DATA_POLRES_SAT)
                    txt_satker_sipil_oper_add.visible()
                    txt_satker_sipil_oper_add.text = "Kepolisian Resort : ${polres?.kesatuan}"
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
                    txt_satker_sipil_oper_add.gone()
//                    txt_polres_add.gone()
                }
            }
            AddPersonelActivity.REQ_POLSEK -> {
                if (resultCode == AddPersonelActivity.RES_POLSEK) {
                    val polsek = data?.getParcelableExtra<SatKerResp>(SatPolsekActivity.UNIT_POLSEK)
                    txt_satker_sipil_oper_add.visible()
                    txt_satker_sipil_oper_add.text = "Kepolisian Sektor : ${polsek?.kesatuan}"
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
