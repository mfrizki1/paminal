package id.calocallo.sicape.ui.main.lp.pasal

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.PasalReq
import id.calocallo.sicape.network.response.AddPasalResp
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_pasal.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddPasalActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var pasalReq = PasalReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pasal)
        sessionManager1 = SessionManager1(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Pasal"

        bindProgressButton(btn_save_single_pasal)
        btn_save_single_pasal.attachTextChangeAnimator()
        btn_save_single_pasal.setOnClickListener {
            pasalReq.nama_pasal = edt_pasal_lp.text.toString()
            pasalReq.tentang_pasal = edt_tentang_pasal_lp.text.toString()
            pasalReq.isi_pasal = edt_isi_pasal_lp.text.toString()
            addPasal()
        }

    }

    private fun addPasal() {
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, size, size)

        btn_save_single_pasal.showProgress {
            progressColor = Color.WHITE
        }
        NetworkConfig().getServLp().addPasal("Bearer ${sessionManager1.fetchAuthToken()}", pasalReq)
            .enqueue(object : Callback<Base1Resp<AddPasalResp>> {
                override fun onFailure(call: Call<Base1Resp<AddPasalResp>>, t: Throwable) {
                    Toast.makeText(this@AddPasalActivity, R.string.error_conn, Toast.LENGTH_SHORT)
                        .show()
                    btn_save_single_pasal.hideDrawable(R.string.not_save)
                }

                override fun onResponse(
                    call: Call<Base1Resp<AddPasalResp>>,
                    response: Response<Base1Resp<AddPasalResp>>
                ) {
                    if(response.isSuccessful){
                        if(response.body()?.message == "Data pasal saved succesfully"){
                            btn_save_single_pasal.showDrawable(animatedDrawable) {
                                buttonTextRes = R.string.data_saved
                                textMarginRes = R.dimen.space_10dp
                            }
                            finish()
                        }else{
                            btn_save_single_pasal.hideDrawable(R.string.not_save)
                        }
                    }else{
                        btn_save_single_pasal.hideDrawable(R.string.not_save)
                    }
                }
            })
    }
}