package id.calocallo.sicape.ui.main.editpersonel.orangs

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.hideDrawable
import com.github.razir.progressbutton.showDrawable
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.OrangsReq
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_single_orangs.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddSingleOrangsActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var orangsReq = OrangsReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_single_orangs)
        sessionManager = SessionManager(this)
        val detailPersonel = intent.extras?.getParcelable<PersonelModel>("PERSONEL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = detailPersonel?.nama

        val hak = sessionManager.fetchHakAkses()
        if (hak == "operator") {
            btn_save_single_orangs.gone()
        }

        var menu = intent.extras?.getString("ORANGS")
        when (menu) {
            "berjasa" -> spinner_orangs_single.setText("Orang Berjasa Selain Orang Tua")
            "disegani" -> spinner_orangs_single.setText("Orang Disegani Karena Adat")
        }

        //spinner
        val item = listOf("Orang Berjasa Selain Orang Tua", "Orang Disegani Karena Adat")
//        spinner_orangs.setText(currMenu)
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        spinner_orangs_single.setAdapter(adapter)
        spinner_orangs_single.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> menu = "berjasa"
                1 -> menu = "disegani"
            }
        }

        val jk = listOf("Laki-Laki", "Perempuan")
        val adapterJK = ArrayAdapter(this, R.layout.item_spinner, jk)
        spinner_jk_single_orangs.setAdapter(adapterJK)
        spinner_jk_single_orangs.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> orangsReq.jenis_kelamin = "laki_laki"
                1 -> orangsReq.jenis_kelamin = "perempuan"
            }
        }
        btn_save_single_orangs.attachTextChangeAnimator()
        btn_save_single_orangs.setOnClickListener {
            val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
            //Defined bounds are required for your drawable
            val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
            animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

            btn_save_single_orangs.showProgress {
                progressColor = Color.WHITE
            }
            orangsReq.nama = edt_nama_lengkap_single_orangs.text.toString()
            orangsReq.umur = edt_umur_single_orangs.text.toString()
            orangsReq.alamat = edt_alamat_single_orangs.text.toString()
            orangsReq.pekerjaan = edt_pekerjaan_single_orangs.text.toString()
            orangsReq.keterangan = edt_ket_single_orangs.text.toString()
            NetworkConfig().getService().addOrangsSingle(
                "Bearer ${sessionManager.fetchAuthToken()}",
                sessionManager.fetchID().toString(),
                menu.toString(),
                orangsReq
            ).enqueue(object : Callback<BaseResp> {
                override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                    Toast.makeText(
                        this@AddSingleOrangsActivity,
                        "Error Koneksi",
                        Toast.LENGTH_SHORT
                    ).show()
                    btn_save_single_orangs.hideDrawable(R.string.save)
                }

                override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                    if (response.isSuccessful) {
                        btn_save_single_orangs.showDrawable(animatedDrawable) {
                            buttonTextRes = R.string.data_saved
                            textMarginRes = R.dimen.space_10dp
                        }
//                        Toast.makeText(this@AddSingleOrangsActivity, R.string.data_saved, Toast.LENGTH_SHORT).show()
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        }, 500)
                    } else {
                        Handler(Looper.getMainLooper()).postDelayed({
                            btn_save_single_orangs.hideDrawable(R.string.save)
                        },3000)
                        btn_save_single_orangs.hideDrawable(R.string.not_save)
                    }
                }
            })
        }
    }
}