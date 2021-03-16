package id.calocallo.sicape.ui.main.editpersonel.media_info

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.MedInfoReq
import id.calocallo.sicape.model.AllPersonelModel1
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_single_med_info.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddSingleMedInfoActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var medInfoReq = MedInfoReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_single_med_info)
        sessionManager1 = SessionManager1(this)

        val personel = intent.extras?.getParcelable<AllPersonelModel1>("PERSONEL")

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = personel?.nama
        btn_save_single_med_info.attachTextChangeAnimator()
        bindProgressButton(btn_save_single_med_info)
        btn_save_single_med_info.setOnClickListener {
            addMedInfo()
        }

    }

    private fun addMedInfo() {
        btn_save_single_med_info.showProgress {
            progressColor = Color.WHITE
        }
        //animatedButton
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
//        Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)
        medInfoReq.sumber = edt_nama_single_med_info.text.toString()
        medInfoReq.topik = edt_topik_single_med_info.text.toString()
        medInfoReq.alasan = edt_alasan_single_med_info.text.toString()
        medInfoReq.keterangan = edt_ket_single_med_info.text.toString()


        NetworkConfig().getServPers().addMedInfoSingle(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            sessionManager1.fetchID().toString(),
            medInfoReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                btn_save_single_med_info.hideDrawable(R.string.save)
                Toast.makeText(this@AddSingleMedInfoActivity, "$t", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    btn_save_single_med_info.showDrawable(animatedDrawable) {
                        buttonTextRes = R.string.data_saved
                        textMarginRes = R.dimen.space_10dp
                    }
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 500)
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_single_med_info.hideDrawable(R.string.save)
                    }, 3000)
                    btn_save_single_med_info.hideDrawable(R.string.not_save)
                    Toast.makeText(
                        this@AddSingleMedInfoActivity,
                       "${response.body()?.message}",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        })
    }
}