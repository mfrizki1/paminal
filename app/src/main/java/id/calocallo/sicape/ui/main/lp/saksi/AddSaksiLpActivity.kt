package id.calocallo.sicape.ui.main.lp.saksi

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.RadioButton
import android.widget.Toast
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.SaksiReq
import id.calocallo.sicape.network.response.AddSaksiResp
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.LpMinResp
import id.calocallo.sicape.ui.main.lp.pasal.PickSaksiActivity
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_saksi_lp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddSaksiLpActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var saksiLpReq = SaksiReq()
    private var isSingleAdd: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_saksi_lp)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Saksi"
        sessionManager1 = SessionManager1(this)
        isSingleAdd = intent.getBooleanExtra("ADD_SINGLE_SAKSI", false)
        val dataLp = intent.getParcelableExtra<LpMinResp>("DATA_LP")

        btn_save_add_saksi.attachTextChangeAnimator()
        bindProgressButton(btn_save_add_saksi)
        btn_save_add_saksi.setOnClickListener {
            btn_save_add_saksi.showProgress {
                progressColor = Color.WHITE
            }
            addSaksiLp(dataLp)
        }
    }

    private fun addSaksiLp(dataLp: LpMinResp?) {
        saksiLpReq.nama = edt_nama_saksi_single.text.toString()
        saksiLpReq.tempat_lahir = edt_tempat_lahir_saksi_single.text.toString()
        saksiLpReq.tanggal_lahir = edt_tanggal_lahir_saksi_single.text.toString()
        saksiLpReq.pekerjaan = edt_pekerjaan_saksi_single.text.toString()
        saksiLpReq.alamat = edt_alamat_saksi_single.text.toString()
        val id: Int = rg_korban_saksi.checkedRadioButtonId
        if (id != -1) {
            val radio: RadioButton = findViewById(id)
            if (radio.text == "Korban") {
                saksiLpReq.is_korban = 1
            } else {
                saksiLpReq.is_korban = 0

            }
        }

        if (isSingleAdd) {
            apiAddSingleSaksi(dataLp)
        } else {
            val intent = Intent().apply {
                this.putExtra("DATA_SAKSI", saksiLpReq)
            }
            setResult(PickSaksiActivity.RES_SAKSI_ADD, intent)
            finish()
        }
        /* val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
         val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
         animatedDrawable.setBounds(0, 0, size, size)
         btn_save_add_saksi.showProgress {
             progressColor = Color.WHITE
         }
         btn_save_add_saksi.showDrawable(animatedDrawable) {
             buttonTextRes = R.string.data_saved
             textMarginRes = R.dimen.space_10dp
         }

         Handler(Looper.getMainLooper()).postDelayed({
             btn_save_add_saksi.hideDrawable(R.string.save)
             Log.e("add_saksi", "$saksiLpReq")
         }, 3000)*/
    }

    private fun apiAddSingleSaksi(dataLp: LpMinResp?) {
        dataLp?.id?.let {
            NetworkConfig().getServLp().addSaksiByIdLp(
                "Bearer ${sessionManager1.fetchAuthToken()}", it, saksiLpReq
            ).enqueue(object : Callback<Base1Resp<AddSaksiResp>> {
                override fun onFailure(call: Call<Base1Resp<AddSaksiResp>>, t: Throwable) {
                    btn_save_add_saksi.hideProgress(R.string.not_save)
                    Toast.makeText(this@AddSaksiLpActivity, R.string.error_conn, Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(
                    call: Call<Base1Resp<AddSaksiResp>>,
                    response: Response<Base1Resp<AddSaksiResp>>
                ) {
                    if (response.body()?.message == "Data saksi kode etik saved succesfully") {
                        btn_save_add_saksi.hideProgress(R.string.data_saved)
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        }, 500)
                    } else {
                        btn_save_add_saksi.hideProgress(R.string.not_save)
                        Toast.makeText(
                            this@AddSaksiLpActivity,
                            R.string.error_conn,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            })
        }
    }
}