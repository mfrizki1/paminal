package id.calocallo.sicape.ui.main.editpersonel.anak

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.AnakReq
import id.calocallo.sicape.model.AllPersonelModel
import id.calocallo.sicape.model.AllPersonelModel1
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_single_anak.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddSingleAnakActivity : BaseActivity() {
    private var mStatus_ikatan = ""
    private var mJenis_kelamin = ""
    private lateinit var sessionManager1: SessionManager1
    private var anakReq = AnakReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_single_anak)
        sessionManager1 = SessionManager1(this)
        val detailPersonel = intent.extras?.getParcelable<AllPersonelModel1>("PERSONEL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = detailPersonel?.nama
        val jk = listOf("Laki-Laki", "Perempuan")
        val adapterJK = ArrayAdapter(this, R.layout.item_spinner, jk)
        spinner_jk_single_anak.setAdapter(adapterJK)
        spinner_jk_single_anak.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> mJenis_kelamin = "laki_laki"
                1 -> mJenis_kelamin = "perempuan"
            }
        }


        val stts_ikatan = listOf("Kandung", "Angkat", "Tiri")
        val adapterStts = ArrayAdapter(this, R.layout.item_spinner, stts_ikatan)
        sp_status_ikatan_single.setAdapter(adapterStts)
        sp_status_ikatan_single.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> mStatus_ikatan = "kandung"
                1 -> mStatus_ikatan = "angkat"
                2 -> mStatus_ikatan = "tiri"
            }
        }

        btn_save_single_anak.attachTextChangeAnimator()
        bindProgressButton(btn_save_single_anak)
        btn_save_single_anak.setOnClickListener {
            Log.e("anak", "$anakReq")
            anakReq.nama = edt_nama_lengkap_single_anak.text.toString()
            anakReq.tempat_lahir = edt_tmpt_ttl_single_anak.text.toString()
            anakReq.tanggal_lahir = edt_tgl_ttl_single_anak.text.toString()
            anakReq.pekerjaan_atau_sekolah = edt_pekerjaan_single_anak.text.toString()
            anakReq.organisasi_yang_diikuti = edt_organisasi_single_anak.text.toString()
            anakReq.keterangan = edt_ket_single_anak.text.toString()
            anakReq.status_ikatan = mStatus_ikatan
            anakReq.jenis_kelamin = mJenis_kelamin

            AddAnak(anakReq)
        }

    }

    private fun AddAnak(anakReq: AnakReq) {
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)
        btn_save_single_anak.showProgress {
            progressColor = Color.WHITE
        }
        NetworkConfig().getService().addAnakSingle(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            sessionManager1.fetchID().toString(),
            anakReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                btn_save_single_anak.hideDrawable(R.string.save)
                Toast.makeText(this@AddSingleAnakActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    btn_save_single_anak.showDrawable(animatedDrawable) {
                        buttonTextRes = R.string.data_saved
                        textMarginRes = R.dimen.space_10dp
                    }
//                    Toast.makeText(this@AddSingleAnakActivity, R.string.data_saved, Toast.LENGTH_SHORT).show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 500)
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_single_anak.hideDrawable(R.string.save)
                    }, 3000)
                    btn_save_single_anak.hideDrawable(R.string.not_save)

                }
            }
        })
    }
}