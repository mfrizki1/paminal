package id.calocallo.sicape.ui.main.editpersonel.sahabat

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.hideDrawable
import com.github.razir.progressbutton.showDrawable
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.request.SahabatReq
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_single_sahabat.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddSingleSahabatActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var sahabatReq = SahabatReq()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_single_sahabat)

        sessionManager = SessionManager(this)
        val detailPersonel = intent.extras?.getParcelable<PersonelModel>("PERSONEL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = detailPersonel?.nama

        val hak = sessionManager.fetchHakAkses()
        if (hak == "operator") {
            btn_save_single_sahabat.gone()
        }
        val jk = listOf("Laki-Laki", "Perempuan")
        val adapterJK = ArrayAdapter(this, R.layout.item_spinner, jk)
        spinner_jk_single_sahabat.setAdapter(adapterJK)
        spinner_jk_single_sahabat.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> sahabatReq.jenis_kelamin = "laki_laki"
                1 -> sahabatReq.jenis_kelamin = "perempuan"
            }
        }
        btn_save_single_sahabat.setOnClickListener {
            val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
            //Defined bounds are required for your drawable
            val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
            animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

            sahabatReq.nama = edt_nama_single_sahabat.text.toString()
            sahabatReq.umur = edt_umur_single_sahabat.text.toString()
            sahabatReq.alamat = edt_alamat_single_sahabat.text.toString()
            sahabatReq.pekerjaan = edt_pekerjaan_single_sahabat.text.toString()
            sahabatReq.alasan = edt_alasan_single_sahabat.text.toString()

            NetworkConfig().getService().addSahabatSingle(
                "Bearer ${sessionManager.fetchAuthToken()}",
                sessionManager.fetchID().toString(),
                sahabatReq
            ).enqueue(object : Callback<BaseResp> {
                override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                    btn_save_single_sahabat.hideDrawable(R.string.not_save)
                    Toast.makeText(this@AddSingleSahabatActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                    if (response.isSuccessful) {
                        btn_save_single_sahabat.showDrawable(animatedDrawable) {
                            buttonTextRes = R.string.data_saved
                            textMarginRes = R.dimen.space_10dp
                        }
//                        Toast.makeText(this@AddSingleSahabatActivity, "Berhasil Tambah Data Sahabat", Toast.LENGTH_SHORT).show()
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        }, 500)
                    } else {
                        Handler(Looper.getMainLooper()).postDelayed({
                            btn_save_single_sahabat.hideDrawable(R.string.not_save)
                        },3000)
                        btn_save_single_sahabat.hideDrawable(R.string.save)
                    }
                }
            })

        }

    }
}