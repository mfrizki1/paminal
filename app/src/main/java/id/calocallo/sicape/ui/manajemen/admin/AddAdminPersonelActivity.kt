package id.calocallo.sicape.ui.manajemen.admin

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.AdminReq
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.PersonelMinResp
import id.calocallo.sicape.network.response.PersonelModelMax2
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.ui.manajemen.AddOperatorActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.action
import id.calocallo.sicape.utils.ext.showSnackbar
import id.co.iconpln.smartcity.ui.base.BaseActivity
import id.rizmaulana.sheenvalidator.lib.SheenValidator
import kotlinx.android.synthetic.main.activity_add_admin_personel.*
import kotlinx.android.synthetic.main.activity_add_operator.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddAdminPersonelActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var adminReq = AdminReq()
    private var idPersonel: Int? = null
    private var isAktif: Int? = null
    private lateinit var sheenValidator: SheenValidator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_admin_personel)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Admin"
        sessionManager1 = SessionManager1(this)
        sheenValidator = SheenValidator(this)


        btn_choose_personel_admin.setOnClickListener {
            val intent = Intent(this, KatPersonelActivity::class.java)
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, AddOperatorActivity.REQ_PERSONEL_OPERATOR)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }


        val item = listOf("Masih", "Tidak")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        spinner_status_admin.setAdapter(adapter)
        spinner_status_admin.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> isAktif = 1
                1 -> isAktif = 0
            }
        }

        sheenValidator.registerHasMinLength(edt_password_admin, 6)
        sheenValidator.registerHasMinLength(edt_repassword_admin, 6)

        bindProgressButton(btn_save_admin)
        btn_save_admin.attachTextChangeAnimator()
        btn_save_admin.setOnClickListener {
            sheenValidator.validate()
            btn_save_admin.showProgress {
                progressColor = Color.WHITE
            }
            if(edt_password_admin.text.toString() != edt_repassword_admin.text.toString()){
                btn_save_admin.hideProgress("Password Harus Sama")
            }else {
                addAdmin()
            }
        }
    }

    private fun addAdmin() {

        adminReq.password = edt_password_admin.text.toString()
        adminReq.password_confirmation = edt_repassword_admin.text.toString()
        adminReq.id_personel = idPersonel
        adminReq.is_aktif = isAktif
        NetworkConfig().getService().addAdmin(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            adminReq
        ).enqueue(object : Callback<Base1Resp<PersonelModelMax2>> {
            override fun onFailure(call: Call<Base1Resp<PersonelModelMax2>>, t: Throwable) {
                btn_save_admin.hideProgress("Error Koneksi")
            }

            override fun onResponse(
                call: Call<Base1Resp<PersonelModelMax2>>,
                response: Response<Base1Resp<PersonelModelMax2>>
            ) {
                if (response.isSuccessful) {
                    btn_save_admin.showSnackbar(R.string.data_saved) {
                        action(R.string.back) {
                            finish()
                        }
                    }

                } else {
                    btn_save_admin.hideProgress("Gagal Simpan / Sudah Terdaftar")
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            AddOperatorActivity.REQ_PERSONEL_OPERATOR -> {
                if (resultCode == Activity.RESULT_OK) {
                    val personel = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
                    txt_nama_personel_admin_add.text = "Nama ${personel?.nama}"
                    txt_pangkat_personel_admin_add.text =
                        "Pangkat : ${personel?.pangkat.toString().toUpperCase()}"
                    txt_nrp_personel_admin_add.text = "NRP : ${personel?.nrp}"
                    txt_jabatan_personel_admin_add.text = "Jabatan : ${personel?.jabatan}"
                    idPersonel = personel?.id
                }
            }
        }
    }
}