package id.calocallo.sicape.ui.manajemen.admin

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.AdminReq
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.PersonelMinResp
import id.calocallo.sicape.network.response.UserResp
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.action
import id.calocallo.sicape.utils.ext.showSnackbar
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.utils.ext.toast
import kotlinx.android.synthetic.main.activity_add_admin_personel.*
import kotlinx.android.synthetic.main.activity_add_operator.*
import kotlinx.android.synthetic.main.activity_edit_admin_personel.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditAdminPersonelActivity : BaseActivity() {
    companion object{
        const val REQ_PERSONEL_ADMIN = 1
        const val RES_PERSONEL_ADMIN = 2
    }
    private lateinit var sessionManager1: SessionManager1
    private var adminReq = AdminReq()
    private var isAktif: Int? = null
    private var namaPersonel: String? = null
    private var nrpPersonel: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_admin_personel)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Admin"
        sessionManager1 = SessionManager1(this)

        val detailAdmin = intent.extras?.getParcelable<UserResp>("admin")
        viewEditAdmin(detailAdmin)

        val item = listOf("Masih", "Tidak")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        spinner_status_admin_edit.setAdapter(adapter)
        spinner_status_admin_edit.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> isAktif = 1
                1 -> isAktif = 0
            }
        }
        btn_choose_personel_admin_edit.setOnClickListener {
            val intent = Intent(this, KatPersonelActivity::class.java)
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, REQ_PERSONEL_ADMIN)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        btn_save_admin_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_admin_edit)
        btn_save_admin_edit.setOnClickListener {
            updateAdmin(detailAdmin)
        }
    }

    private fun updateAdmin(detailAdmin: UserResp?) {
        btn_save_admin_edit.showProgress{
            progressColor = Color.WHITE
        }
        adminReq.nama = namaPersonel
        adminReq.username = nrpPersonel
        adminReq.password = edt_password_admin_edit.text.toString()
        adminReq.password_confirmation = edt_repassword_admin_edit.text.toString()
        adminReq.is_aktif = isAktif
        NetworkConfig().getServUser().updAdmin("Bearer ${sessionManager1.fetchAuthToken()}",
        detailAdmin?.id.toString(), adminReq).enqueue(object :
            Callback<Base1Resp<UserResp>> {
            override fun onFailure(call: Call<Base1Resp<UserResp>>, t: Throwable) {
                Toast.makeText(this@EditAdminPersonelActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                    .show()
                btn_save_admin_edit.hideProgress(R.string.not_update)
            }

            override fun onResponse(
                call: Call<Base1Resp<UserResp>>,
                response: Response<Base1Resp<UserResp>>
            ) {
                if(response.body()?.message == "Data personel admin updated succesfully"){
                    btn_save_admin_edit.hideProgress(R.string.data_updated)
                    btn_save_admin_edit.showSnackbar(R.string.data_updated){
                        action(R.string.back){
                            finish()
                        }
                    }

                }else{
                    toast("${response.body()?.message}")
                    btn_save_admin_edit.hideProgress(R.string.not_update)
                }
            }
        })
    }

    private fun viewEditAdmin(detailAdmin: UserResp?) {
        if (detailAdmin?.is_aktif == 1) {
            spinner_status_admin_edit.setText("Masih")
            isAktif = detailAdmin.is_aktif
        } else {
            spinner_status_admin_edit.setText("TIdak")
            isAktif = detailAdmin?.is_aktif
        }

        txt_nama_personel_admin_edit.text = "Nama : ${detailAdmin?.nama}"
        txt_nrp_personel_admin_edit.text = "NRP : ${detailAdmin?.username}"

        nrpPersonel = detailAdmin?.username
        namaPersonel = detailAdmin?.nama
//        txt_pangkat_personel_admin_edit.text =
//            "Pangkat : ${detailAdmin?.personel?.pangkat.toString().toUpperCase()}"
//        txt_jabatan_personel_admin_edit.text =
//            "Jabatan : ${detailAdmin?.personel?.satuan_kerja?.kesatuan}"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQ_PERSONEL_ADMIN){
            if (resultCode == Activity.RESULT_OK) {
                val personel = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
                txt_nama_personel_admin_edit.text = "Nama ${personel?.nama}"
                txt_nrp_personel_admin_edit.text = "NRP : ${personel?.nrp}"
                namaPersonel = personel?.nama
                nrpPersonel = personel?.nrp
            }
        }
    }
}