package id.calocallo.sicape.ui.main.editpersonel.med_sos

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.MedSosReq
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_single_med_sos.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddSingleMedSosActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var medSosReq = MedSosReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_single_med_sos)
        sessionManager = SessionManager(this)
        val personel = intent.extras?.getParcelable<PersonelModel>("PERSONEL")

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = personel?.nama
        btn_save_single_medsos.attachTextChangeAnimator()
        bindProgressButton(btn_save_single_medsos)
        btn_save_single_medsos.setOnClickListener {
            addMedSos()
        }
    }

    private fun addMedSos() {
        btn_save_single_medsos.showProgress {
            progressColor = Color.WHITE
        }
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        medSosReq.nama_medsos = edt_nama_single_medsos.text.toString()
        medSosReq.nama_akun = edt_acc_single_medsos.text.toString()
        medSosReq.alasan = edt_alasan_single_medsos.text.toString()
        medSosReq.keterangan = edt_ket_single_medsos.text.toString()

        NetworkConfig().getService().addMedSosSingle(
            "Bearer ${sessionManager.fetchAuthToken()}",
            sessionManager.fetchID().toString(),
            medSosReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                btn_save_single_medsos.hideDrawable(R.string.save)
                Toast.makeText(this@AddSingleMedSosActivity, R.string.error_conn, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if(response.isSuccessful){
                    btn_save_single_medsos.showDrawable(animatedDrawable){
                        buttonTextRes = R.string.data_saved
                        textMarginRes = R.dimen.space_10dp
                    }

                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 500)
                }else{
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_single_medsos.hideDrawable(R.string.save)
                    },3000)
                    btn_save_single_medsos.hideDrawable(R.string.not_save)
                }
            }
        })
    }
}