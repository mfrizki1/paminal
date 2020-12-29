package id.calocallo.sicape.ui.main.editpersonel.pernah_dihukum

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.HukumanReq
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_single_pernah_dihukum.*
import kotlinx.android.synthetic.main.activity_add_single_relasi.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddSinglePernahDihukumActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var hukumanReq = HukumanReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_single_pernah_dihukum)
        sessionManager = SessionManager(this)
        val personel = intent.extras?.getParcelable<PersonelModel>("PERSONEL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = personel?.nama

        btn_save_single_pernah_dihukum.attachTextChangeAnimator()
        bindProgressButton(btn_save_single_pernah_dihukum)
        btn_save_single_pernah_dihukum.setOnClickListener {
            addPernahDihukum()
        }
    }

    private fun addPernahDihukum() {
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        btn_save_single_pernah_dihukum.showProgress {
            progressColor = Color.WHITE
        }
        hukumanReq.perkara = edt_perkara_hukum.text.toString()
        NetworkConfig().getService().addSingleDihukum(
            "Bearer ${sessionManager.fetchAuthToken()}",
            sessionManager.fetchID().toString(),
            hukumanReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                btn_save_single_pernah_dihukum.hideDrawable(R.string.save)
                Toast.makeText(this@AddSinglePernahDihukumActivity, R.string.error_conn, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if(response.isSuccessful){
                    btn_save_single_pernah_dihukum.showDrawable(animatedDrawable) {
                        buttonTextRes = R.string.data_saved
                        textMarginRes = R.dimen.space_10dp
                    }
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 500)
                }else{
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_single_pernah_dihukum.hideDrawable(R.string.save)
                    },3000)
                    btn_save_single_pernah_dihukum.hideDrawable(R.string.not_save)
                }
            }
        })
    }
}