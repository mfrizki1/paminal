package id.calocallo.sicape.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import id.calocallo.sicape.R
import id.calocallo.sicape.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            run {
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            }
        }, 3000)
    }
}