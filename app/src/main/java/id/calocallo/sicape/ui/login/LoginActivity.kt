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
import id.calocallo.sicape.network.response.LoginPersonelResp
import id.calocallo.sicape.network.response.LoginSipilResp
import id.calocallo.sicape.network.response.LoginSuperAdminResp
import id.calocallo.sicape.network.response.PersonelModelMax2
import id.calocallo.sicape.ui.main.MainActivity
import id.calocallo.sicape.utils.SessionManager1
import id.rizmaulana.sheenvalidator.lib.SheenValidator
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var sessionManager1: SessionManager1
    private lateinit var sheenValidator: SheenValidator
    private var loginReq = LoginReq()
    private var isSipilLogin: Boolean? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sheenValidator = SheenValidator(this)
        sessionManager1 = SessionManager1(this)

        /*
        validasi user dan password
        - user harus ada huruf kapital, kecil, angka dan minimal 6
        - password huruf kapital, kecil, angka dan minimal 8
         */
        sheenValidator.registerAsRequired(edt_username)
        sheenValidator.registerHasMinLength(edt_password, 6)

        isSipilLogin = intent?.getBooleanExtra("IS_SIPIL",false)
        if (isSipilLogin == true) {
            txt_layout_username.hint = "Masukkan Username"
        } else {
            txt_layout_username.hint = "Masukkan NRP"

        }

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
        loginReq.username = username
        loginReq.password = password

        button.showProgress {
            progressColor = Color.WHITE
            gravity = DrawableButton.GRAVITY_CENTER
        }
        if(username == "0001" && password == "9pK6aU8mE7VYTaBV"){
            NetworkConfig().getService().loginSuper(loginReq).enqueue(object :Callback<LoginSuperAdminResp>{
                override fun onFailure(call: Call<LoginSuperAdminResp>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Gagal Koneksi", Toast.LENGTH_SHORT).show()
                    btn_login.hideProgress(getString(R.string.login))
                }

                override fun onResponse(
                    call: Call<LoginSuperAdminResp>,
                    response: Response<LoginSuperAdminResp>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == 1) {
                            response.body()!!.user.hak_akses?.let {
                                sessionManager1.saveHakAkses(it)
                            }

                            button.hideProgress(R.string.login)
                            val token = response.body()?.token
                            Log.e("token", token.toString())
                            sessionManager1.saveAuthToken(token.toString())
                            sessionManager1.setUserLogin(true)
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            button.hideProgress(R.string.login)
                            Toast.makeText(
                                this@LoginActivity,
                                "Username / Password Salah",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } else {
                        Toast.makeText(this@LoginActivity, "Error", Toast.LENGTH_SHORT).show()
                        btn_login.hideProgress(getString(R.string.login))
                    }
                }
            })
        }
        else if (isSipilLogin == true) {
            NetworkConfig().getService().loginSipil(loginReq).enqueue(object : Callback<LoginSipilResp> {
                override fun onFailure(call: Call<LoginSipilResp>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Gagal Koneksi", Toast.LENGTH_SHORT).show()
                    btn_login.hideProgress(getString(R.string.login))
                }

                override fun onResponse(call: Call<LoginSipilResp>, response: Response<LoginSipilResp>) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == 1) {
                            response.body()!!.hak_akses.hak_akses?.let {
                                sessionManager1.saveHakAkses(it)
                            }

                            button.hideProgress(R.string.login)
                            val token = response.body()?.token
                            Log.e("token", token.toString())
                            sessionManager1.saveAuthToken(token.toString())
                            sessionManager1.setUserLogin(true)
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.putExtra("IS_SIPIL", true)
                            startActivity(intent)
                            finish()
                        } else {
                            button.hideProgress(R.string.login)
                            Toast.makeText(
                                this@LoginActivity,
                                "Username / Password Salah",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } else {
                        Toast.makeText(this@LoginActivity, "Error", Toast.LENGTH_SHORT).show()
                        btn_login.hideProgress(getString(R.string.login))
                    }
                }
            })
        } else if(isSipilLogin == false){
            NetworkConfig().getService().loginPersonel(loginReq).enqueue(object : Callback<LoginPersonelResp> {
                override fun onFailure(call: Call<LoginPersonelResp>, t: Throwable) {
                    Log.e("errorPersonel", "$t")
                    Toast.makeText(this@LoginActivity, "Gagal Koneksi", Toast.LENGTH_SHORT).show()
                    btn_login.hideProgress(getString(R.string.login))
                }

                override fun onResponse(call: Call<LoginPersonelResp>, response: Response<LoginPersonelResp>) {
                    if (response.isSuccessful) {
                        if (response.body()?.status == 1) {
                            response.body()!!.hak_akses.hak_akses?.let {
                                sessionManager1.saveHakAkses(it)
                            }

                            button.hideProgress(R.string.login)
                            val token = response.body()?.token
                            Log.e("token", token.toString())
                            sessionManager1.saveAuthToken(token.toString())
                            sessionManager1.setUserLogin(true)

                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        } else {
                            button.hideProgress(R.string.login)
                            Toast.makeText(
                                this@LoginActivity,
                                "Username / Password Salah",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } else {
                        Toast.makeText(this@LoginActivity, "Error", Toast.LENGTH_SHORT).show()
                        btn_login.hideProgress(getString(R.string.login))
                    }
                }
            })

        }
    }


}