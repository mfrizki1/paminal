package id.calocallo.sicape.ui.main.editpersonel.alamat

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AllPersonelModel1
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.AlamatReq
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_single_alamat.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddSingleAlamatActivity : BaseActivity() {

    //    val number = 1
//    var bcd = "data a"
    private val alamatReq = AlamatReq()
    private lateinit var sessionManager1: SessionManager1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_single_alamat)

        val personel = intent.extras?.getParcelable<AllPersonelModel1>("PERSONEL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = personel?.nama

//        number = 2
//        bcd = "SDDSFNSIDASNDI"
        sessionManager1 = SessionManager1(this)
        bindProgressButton(btn_save_alamat)
        btn_save_alamat.attachTextChangeAnimator()
        btn_save_alamat.setOnClickListener {
            val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
            //Defined bounds are required for your drawable
            val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
            animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

            alamatReq.alamat = edt_alamat.text.toString()
            alamatReq.tahun_awal = edt_thn_awal_alamat.text.toString()
            alamatReq.tahun_akhir = edt_thn_akhir_alamat.text.toString()
            alamatReq.dalam_rangka = edt_rangka_alamat.text.toString()
            alamatReq.keterangan = edt_ket_alamat.text.toString()

            btn_save_alamat.showProgress {
                progressColor = Color.WHITE
            }

            NetworkConfig().getServPers().addAlamatSingle(
                "Bearer ${sessionManager1.fetchAuthToken()}",
//                "4",
                sessionManager1.fetchID().toString(),
                alamatReq
            ).enqueue(object : Callback<BaseResp> {

                override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                    btn_save_alamat.hideDrawable(R.string.save)
                    Toast.makeText(this@AddSingleAlamatActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                    if (response.isSuccessful) {

                        btn_save_alamat.showDrawable(animatedDrawable) {
                            buttonTextRes = R.string.data_saved
                            textMarginRes = R.dimen.space_10dp
                        }
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        }, 500)
                    } else {
                        Handler(Looper.getMainLooper()).postDelayed({
                            btn_save_alamat.hideDrawable(R.string.save)
                        }, 3000)
                        btn_save_alamat.hideDrawable(R.string.not_save)
                    }
                }
            })
        }
    }
}