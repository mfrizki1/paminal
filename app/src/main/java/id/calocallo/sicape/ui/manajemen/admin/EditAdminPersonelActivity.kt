package id.calocallo.sicape.ui.manajemen.admin

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
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
import id.calocallo.sicape.network.response.PersonelModelMax2
import id.calocallo.sicape.network.response.UserCreatorResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.action
import id.calocallo.sicape.utils.ext.showSnackbar
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_admin_personel.*
import kotlinx.android.synthetic.main.activity_edit_admin_personel.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditAdminPersonelActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var adminReq = AdminReq()
    private var isAktif: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_admin_personel)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Admin"
        sessionManager1 = SessionManager1(this)

        val detailAdmin = intent.extras?.getParcelable<UserCreatorResp>("admin")
        viewEditAdmin(detailAdmin)

        val item = listOf("Masih", "Tidak")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        spinner_status_admin_edit.setAdapter(adapter)
        spinner_status_admin_edit.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> isAktif = 1
                1 -> isAktif = 0
            }
        }
        btn_save_admin_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_admin_edit)
        btn_save_admin_edit.setOnClickListener {
            updateAdmin(detailAdmin)
        }
    }

    private fun updateAdmin(detailAdmin: UserCreatorResp?) {
        btn_save_admin_edit.showProgress{
            progressColor = Color.WHITE
        }
        adminReq.password = edt_password_admin_edit.text.toString()
        adminReq.password_confirmation = edt_repassword_admin_edit.text.toString()
        adminReq.is_aktif = isAktif
        NetworkConfig().getService().updAdmin("Bearer ${sessionManager1.fetchAuthToken()}",
        detailAdmin?.id.toString(), adminReq).enqueue(object : Callback<Base1Resp<PersonelModelMax2>>{
            override fun onFailure(call: Call<Base1Resp<PersonelModelMax2>>, t: Throwable) {
                Toast.makeText(this@EditAdminPersonelActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                    .show()
                btn_save_admin_edit.hideProgress("Gagal Update")
            }

            override fun onResponse(
                call: Call<Base1Resp<PersonelModelMax2>>,
                response: Response<Base1Resp<PersonelModelMax2>>
            ) {
                if(response.isSuccessful){
                    btn_save_admin_edit.hideProgress(R.string.data_updated)
                    btn_save_admin_edit.showSnackbar(R.string.data_updated){
                        action(R.string.back){
                            finish()
                        }
                    }

                }else{
                    Toast.makeText(this@EditAdminPersonelActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                        .show()
                    btn_save_admin_edit.hideProgress("Gagal Update")
                }
            }
        })
    }

    private fun viewEditAdmin(detailAdmin: UserCreatorResp?) {
        if (detailAdmin?.is_aktif == 1) {
            spinner_status_admin_edit.setText("Masih")
            isAktif = detailAdmin?.is_aktif
        } else {
            spinner_status_admin_edit.setText("TIdak")
            isAktif = detailAdmin?.is_aktif
        }

        txt_nama_personel_admin_edit.text = "Nama : ${detailAdmin?.personel?.nama}"
        txt_pangkat_personel_admin_edit.text =
            "Pangkat : ${detailAdmin?.personel?.pangkat.toString().toUpperCase()}"
        txt_nrp_personel_admin_edit.text = "NRP : ${detailAdmin?.personel?.nrp}"
        txt_jabatan_personel_admin_edit.text =
            "Jabatan : ${detailAdmin?.personel?.satuan_kerja?.kesatuan}"
    }
}