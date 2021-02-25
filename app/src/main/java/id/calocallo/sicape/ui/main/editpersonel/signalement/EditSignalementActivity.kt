package id.calocallo.sicape.ui.main.editpersonel.signalement

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AllPersonelModel1
import id.calocallo.sicape.model.SignalementModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_signalement.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditSignalementActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var signalementModel = SignalementModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_signalement)
        sessionManager1 = SessionManager1(this)
        val personel = intent?.extras?.getParcelable<AllPersonelModel1>("PERSONEL_DETAIL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = personel?.nama

        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_save_signalement_edit.gone()
        }
//        getSignalement()
        personel?.signalement?.let { viewSignalement(it) }
        btn_save_signalement_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_signalement_edit)
        btn_save_signalement_edit.setOnClickListener {
            updateSignalement()
        }

    }

    private fun updateSignalement() {
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)
        btn_save_signalement_edit.showProgress {
            progressColor = Color.WHITE
        }
        signalementModel.tinggi = edt_tinggi_edit.text.toString().toInt()
        signalementModel.rambut = edt_rambut_edit.text.toString()
        signalementModel.muka = edt_muka_edit.text.toString()
        signalementModel.mata = edt_mata_edit.text.toString()
        signalementModel.sidik_jari = edt_sidik_jari_edit.text.toString()
        signalementModel.cacat = edt_cacat_ciri_edit.text.toString()
        signalementModel.kesenangan = edt_kesenangan_edit.text.toString()
        signalementModel.kelemahan = edt_kelemahan_edit.text.toString()
        signalementModel.yang_mempengaruhi = edt_dapat_dipengaruhi_edit.text.toString()
        signalementModel.keluarga_dekat = edt_keluarga_dekat_edit.text.toString()
        signalementModel.lain_lain = edt_lainnya_edit.text.toString()

        NetworkConfig().getServPers().updateSignalement(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            sessionManager1.fetchID().toString(),
            signalementModel
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                btn_save_signalement_edit.hideDrawable(R.string.not_update)
                Toast.makeText(
                    this@EditSignalementActivity,
                    R.string.error_conn,
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    btn_save_signalement_edit.showDrawable(animatedDrawable) {
                        buttonTextRes = R.string.data_updated
                        textMarginRes = R.dimen.space_10dp
                    }
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_signalement_edit.hideDrawable(R.string.not_update)
                    }, 3000)
                    btn_save_signalement_edit.hideDrawable(R.string.save)
                }
            }

        })
    }

    private fun getSignalement() {
        NetworkConfig().getServPers().getSignalement(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            sessionManager1.fetchID().toString()
        ).enqueue(object : Callback<SignalementModel> {
            override fun onFailure(call: Call<SignalementModel>, t: Throwable) {
                Toast.makeText(
                    this@EditSignalementActivity,
                    R.string.error_conn,
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(
                call: Call<SignalementModel>,
                response: Response<SignalementModel>
            ) {
                if (response.isSuccessful) {
                    if (!response.body()!!.tinggi.toString().isNullOrEmpty()) {
                        viewSignalement(response.body()!!)
                    }
                } else {
                    Toast.makeText(this@EditSignalementActivity, R.string.error, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    private fun viewSignalement(body: SignalementModel) {
        edt_tinggi_edit.setText(body.tinggi.toString())
        edt_rambut_edit.setText(body.rambut)
        edt_muka_edit.setText(body.muka)
        edt_mata_edit.setText(body.mata)
        edt_sidik_jari_edit.setText(body.sidik_jari)
        edt_cacat_ciri_edit.setText(body.cacat)
        edt_kesenangan_edit.setText(body.kesenangan)
        edt_kelemahan_edit.setText(body.kelemahan)
        edt_dapat_dipengaruhi_edit.setText(body.yang_mempengaruhi)
        edt_keluarga_dekat_edit.setText(body.keluarga_dekat)
        edt_lainnya_edit.setText(body.lain_lain)
    }
}