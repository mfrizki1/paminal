package id.calocallo.sicape.ui.login

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.github.razir.progressbutton.*
import com.google.android.material.button.MaterialButton
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.LoginReq
import id.calocallo.sicape.network.response.LoginResp
import id.calocallo.sicape.ui.main.MainActivity
import id.calocallo.sicape.utils.Constants
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.action
import id.calocallo.sicape.utils.ext.showSnackbar
import id.rizmaulana.sheenvalidator.lib.SheenValidator
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var sheenValidator: SheenValidator
    private var loginReq = LoginReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sheenValidator = SheenValidator(this)
        sessionManager = SessionManager(this)

        /*
        validasi user dan password
        - user harus ada huruf kapital, kecil, angka dan minimal 6
        - password huruf kapital, kecil, angka dan minimal 8
         */
        sheenValidator.registerAsRequired(edt_username)
        sheenValidator.registerHasMinLength(edt_password, 6)

        bindProgressButton(btn_login)
        btn_login.attachTextChangeAnimator {
            fadeInMills = 300
            fadeOutMills = 300
        }
        btn_login.setOnClickListener {
            sheenValidator.validate()
            initAPI(btn_login)
        }


    }

    private fun initAPI(button: MaterialButton) {
        val username = edt_username.text.toString()
        val password = edt_password.text.toString()
        loginReq.nrp = username
        loginReq.password = password

        button.showProgress {
            progressColor = Color.WHITE
            gravity = DrawableButton.GRAVITY_CENTER
        }
        NetworkConfig().getService().login(loginReq).enqueue(object : Callback<LoginResp> {
            override fun onFailure(call: Call<LoginResp>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Gagal Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<LoginResp>, response: Response<LoginResp>) {
                if (response.isSuccessful) {
                    if (response.body()?.status == 1) {
                        sessionManager.saveHakAkses(response.body()!!.hak_akses)

                        button.hideProgress(R.string.login)
                        val token = response.body()?.token
                        Log.e("token", token.toString())
                        sessionManager.saveAuthToken(token.toString())
                        sessionManager.setUserLogin(true)

                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }else{
                        button.hideProgress(R.string.login)
                        Toast.makeText(this@LoginActivity, "Username / Password Salah", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    Toast.makeText(this@LoginActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


}