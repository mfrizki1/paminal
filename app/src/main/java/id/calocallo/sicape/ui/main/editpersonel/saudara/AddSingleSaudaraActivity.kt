package id.calocallo.sicape.ui.main.editpersonel.saudara

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.request.SaudaraReq
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_single_saudara.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddSingleSaudaraActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var saudaraReq = SaudaraReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_single_saudara)
        sessionManager = SessionManager(this)
        val detailPersonel = intent.extras?.getParcelable<PersonelModel>("PERSONEL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = detailPersonel?.nama

        val hak = sessionManager.fetchHakAkses()
        if (hak == "operator") {
            btn_save_single_saudara.gone()
        }

        bindProgressButton(btn_save_single_saudara)
        btn_save_single_saudara.attachTextChangeAnimator()
        btn_save_single_saudara.setOnClickListener {
            doSaveSaudara()
        }

        val item = listOf("Kandung", "Angkat", "Tiri")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        sp_ikatan_single_saudara.setAdapter(adapter)
        sp_ikatan_single_saudara.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> {
                    saudaraReq.status_ikatan = "kandung"
                }
                1 -> {
                    saudaraReq.status_ikatan = "angkat"
                }
                2 -> {
                    saudaraReq.status_ikatan = "tiri"
                }
            }
        }

        val jk = listOf("Laki-Laki", "Perempuan")
        val adapterJK = ArrayAdapter(this, R.layout.item_spinner, jk)
        spinner_jk_single_saudara.setAdapter(adapterJK)
        spinner_jk_single_saudara.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> saudaraReq.jenis_kelamin = "laki_laki"
                1 -> saudaraReq.jenis_kelamin = "perempuan"
            }
        }

    }

    private fun doSaveSaudara() {
        saudaraReq.nama = edt_nama_lengkap_single_saudara.text.toString()
        saudaraReq.tempat_lahir = edt_tmpt_ttl_single_saudara.text.toString()
        saudaraReq.tanggal_lahir = edt_tgl_ttl_single_saudara.text.toString()
        saudaraReq.pekerjaan_atau_sekolah = edt_pekerjaan_single_saudara.text.toString()
        saudaraReq.organisasi_yang_diikuti = edt_organisasi_single_saudara.text.toString()
        saudaraReq.keterangan = edt_ket_single_saudara.text.toString()

        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)
        btn_save_single_saudara.showProgress {
            progressColor = Color.WHITE
        }
        NetworkConfig().getService().addSaudaraSingle(
            "Bearer ${sessionManager.fetchAuthToken()}",
            sessionManager.fetchID().toString(),
            saudaraReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                btn_save_single_saudara.hideDrawable(R.string.save)
                Toast.makeText(this@AddSingleSaudaraActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    btn_save_single_saudara.showDrawable(animatedDrawable) {
                        buttonTextRes = R.string.data_saved
                        textMarginRes = R.dimen.space_10dp
                    }
//                    Toast.makeText(this@AddSingleSaudaraActivity, R.string.data_saved, Toast.LENGTH_SHORT).show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 500)
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_single_saudara.hideDrawable(R.string.save)
                    },3000)
                    btn_save_single_saudara.hideDrawable(R.string.not_save)
                }
            }
        })
    }

}