package id.calocallo.sicape.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import id.calocallo.sicape.R
import id.calocallo.sicape.ui.login.LoginActivity
import id.calocallo.sicape.ui.main.MainActivity
import id.calocallo.sicape.utils.SessionManager

class SplashActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        sessionManager = SessionManager(this)
//        sessionManager.clearAllPers()
//        sessionManager.clearAllAPP()

//        val user: PersonelModel = BaseApp.getInstance(this).personel

//        val user = sessionManager.fetchUser()
        val isUserLoged = sessionManager.getUserLogin()
        if (isUserLoged == true) {
            Handler(Looper.getMainLooper()).postDelayed({
                run {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }
            }, 3000)
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                run {
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                    finish()
                }
            }, 3000)

        }

    }
}

