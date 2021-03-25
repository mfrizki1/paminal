package id.calocallo.sicape.ui.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.ChangePassReq
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.action
import id.calocallo.sicape.utils.ext.showSnackbar
import id.calocallo.sicape.utils.hideKeyboard
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.utils.ext.toast
import id.rizmaulana.sheenvalidator.lib.SheenValidator
import kotlinx.android.synthetic.main.activity_change_pass.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePassActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var changePassReq = ChangePassReq()
    private lateinit var sheenValidator: SheenValidator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_pass)
        setupActionBarWithBackButton(toolbar)
        sessionManager1 = SessionManager1(this)
        supportActionBar?.title = "Ubah Password"
        val userPersonel = sessionManager1.fetchUser()
        txt_nama_change_pass.text = "Nama : ${userPersonel?.nama}"
        txt_user_nrp_change_pass.text = "NRP : ${userPersonel?.username}"
        sheenValidator = SheenValidator(this)
        sheenValidator.registerHasMinLength(edt_password_change_pass, 6)
        sheenValidator.registerHasMinLength(edt_repassword_change_pass, 6)
        btn_save_change_pass.attachTextChangeAnimator()
        bindProgressButton(btn_save_change_pass)
        btn_save_change_pass.setOnClickListener {
            sheenValidator.validate()

            btn_save_change_pass.showProgress {
                progressColor = Color.WHITE
            }
            if (edt_password_change_pass.text?.length!! < 6 && edt_repassword_change_pass.text?.length!! < 6) {
                btn_save_change_pass.hideProgress("Gagal Ubah Password")
            } else {
                changePassReq.password = edt_password_change_pass.text.toString()
                changePassReq.password_confirmation = edt_repassword_change_pass.text.toString()


                NetworkConfig().getServUser().changePassword(
                    "Bearer ${sessionManager1.fetchAuthToken()}", changePassReq
                ).enqueue(object : Callback<BaseResp> {
                    override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                        btn_save_change_pass.hideProgress("Gagal Ubah Password")
                    }

                    override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                        if (response.isSuccessful) {
                            hideKeyboard()
                            btn_save_change_pass.hideProgress(R.string.success_change_pass)
                            sessionManager1.clearUser()
                            btn_save_change_pass.showSnackbar(R.string.success_change_pass) {
                                action(R.string.back) {
                                    val intent = Intent(
                                        this@ChangePassActivity, KatUserActivity::class.java
                                    )
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    startActivity(intent)
                                    finish()
                                }
                            }
                        } else {
                            toast("${response.body()?.message}")
                            btn_save_change_pass.hideProgress("Password Harus Sama")
                        }
                    }
                })
            }
        }


    }
}