package id.calocallo.sicape.ui.main.editpersonel.tokoh

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.request.TokohReq
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_single_tokoh.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddSingleTokohActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var tokohReq = TokohReq()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_single_tokoh)

        sessionManager = SessionManager(this)
        val detailPersonel = intent.extras?.getParcelable<PersonelModel>("PERSONEL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = detailPersonel?.nama

        val hak = sessionManager.fetchHakAkses()
        if (hak == "operator") {
            btn_save_single_tokoh.gone()
        }
        bindProgressButton(btn_save_single_tokoh)
        btn_save_single_tokoh.attachTextChangeAnimator()
        btn_save_single_tokoh.setOnClickListener {
            tokohReq.nama = edt_nama_single_tokoh.text.toString()
            tokohReq.asal_negara = edt_asal_negara_single_tokoh.text.toString()
            tokohReq.alasan = edt_alasan_single_tokoh.text.toString()
            tokohReq.keterangan = edt_alasan_single_tokoh.text.toString()
            val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
            //Defined bounds are required for your drawable
            val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
            animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

            btn_save_single_tokoh.showProgress {
                progressColor = Color.WHITE
            }

            NetworkConfig().getService().addTokohSingle(
                "Bearer ${sessionManager.fetchAuthToken()}",
                sessionManager.fetchID().toString(),
                tokohReq
            ).enqueue(object : Callback<BaseResp> {
                override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                    Toast.makeText(this@AddSingleTokohActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                        .show()
                    btn_save_single_tokoh.hideDrawable(R.string.save)
                }

                override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                    if (response.isSuccessful) {

                        btn_save_single_tokoh.showDrawable(animatedDrawable) {
                            buttonTextRes = R.string.data_saved
                            textMarginRes = R.dimen.space_10dp
                        }
//                        Toast.makeText(this@AddSingleTokohActivity, "Berhasil Tambah Data Tokoh", Toast.LENGTH_SHORT).show()
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        }, 500)
                    } else {
                        Handler(Looper.getMainLooper()).postDelayed({
                            btn_save_single_tokoh.hideDrawable(R.string.save)
                        },3000)
                        btn_save_single_tokoh.hideDrawable(R.string.not_save)
                    }
                }
            })

        }

    }
}