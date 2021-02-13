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
import id.calocallo.sicape.network.request.SaudaraReq
import id.calocallo.sicape.network.response.SaudaraResp
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_saudara.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditSaudaraActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var saudaraReq = SaudaraReq()
    private var tempSttsIktn: String? = null
    private var tempJK: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_saudara)
        sessionManager1 = SessionManager1(this)
        val namaPersonel = intent.extras?.getString("NAMA_PERSONEL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = namaPersonel

        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_delete_saudara_edit.gone()
            btn_save_saudara_edit.gone()
        }

        val saudara = intent.extras?.getParcelable<SaudaraResp>("SAUDARA")

        btn_save_saudara_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_saudara_edit)
        btn_save_saudara_edit.setOnClickListener {
            doUpdateSaudara(saudara)
        }

        btn_delete_saudara_edit.setOnClickListener {
            alert("Yakin Hapus Data?") {
                positiveButton("Iya") {
                    doDeleteSaudara(saudara)
                }
                negativeButton("Tidak") {}
            }.show()
        }
        apiDetailSaudara(saudara)
    }

    private fun apiDetailSaudara(saudara: SaudaraResp?) {
        NetworkConfig().getService()
            .getDetailSaudara("Bearer ${sessionManager1.fetchAuthToken()}", saudara?.id.toString()).enqueue(object :Callback<SaudaraResp>{
                override fun onFailure(call: Call<SaudaraResp>, t: Throwable) {
                    Toast.makeText(this@EditSaudaraActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(call: Call<SaudaraResp>, response: Response<SaudaraResp>) {
                    if(response.isSuccessful){
                        viewDetailSaudara(response.body())
                    }else{
                        Toast.makeText(this@EditSaudaraActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
    }

    private fun viewDetailSaudara(saudara: SaudaraResp?) {
        edt_nama_lengkap_saudara_edit.setText(saudara?.nama)
        edt_tmpt_ttl_saudara_edit.setText(saudara?.tempat_lahir)
        edt_tgl_ttl_saudara_edit.setText(saudara?.tanggal_lahir)
        edt_pekerjaan_saudara_edit.setText(saudara?.pekerjaan_atau_sekolah)
        edt_organisasi_saudara_edit.setText(saudara?.organisasi_yang_diikuti)
        edt_ket_saudara_edit.setText(saudara?.keterangan)


        /*2*/
        when (saudara?.status_ikatan) {
            "kandung" -> tempSttsIktn = "Kandung"
            "angkat" -> tempSttsIktn = "Angkat"
            "tiri" -> tempSttsIktn = "Tiri"
        }
        when (saudara?.jenis_kelamin) {
            "laki_laki" -> tempJK = "Laki-Laki"
            "perempuan" -> tempJK = "Perempuan"
        }

        saudaraReq.status_ikatan = saudara?.status_ikatan
        saudaraReq.jenis_kelamin = saudara?.jenis_kelamin

        val listStts = listOf("Kandung", "Angkat", "Tiri")
        sp_ikatan_saudara_edit.setText(tempSttsIktn)
        val adapterStts = ArrayAdapter(this, R.layout.item_spinner, listStts)
        sp_ikatan_saudara_edit.setAdapter(adapterStts)
        sp_ikatan_saudara_edit.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> saudaraReq.status_ikatan = "kandung"
                1 -> saudaraReq.status_ikatan = "angkat"
                2 -> saudaraReq.status_ikatan = "tiri"

            }
        }
        val listJK = listOf("Laki-Laki", "Perempuan")
        spinner_jk_saudara_edit.setText(tempJK)
        val adapterJK = ArrayAdapter(this, R.layout.item_spinner, listJK)
        spinner_jk_saudara_edit.setAdapter(adapterJK)
        spinner_jk_saudara_edit.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> saudaraReq.jenis_kelamin = "laki_laki"
                1 -> saudaraReq.jenis_kelamin = "perempuan"
            }
        }
    }

    private fun doUpdateSaudara(saudara: SaudaraResp?) {
        saudaraReq.nama = edt_nama_lengkap_saudara_edit.text.toString()
        saudaraReq.tempat_lahir = edt_tmpt_ttl_saudara_edit.text.toString()
        saudaraReq.tanggal_lahir = edt_tgl_ttl_saudara_edit.text.toString()
        saudaraReq.pekerjaan_atau_sekolah = edt_pekerjaan_saudara_edit.text.toString()
        saudaraReq.organisasi_yang_diikuti = edt_organisasi_saudara_edit.text.toString()
        saudaraReq.keterangan = edt_ket_saudara_edit.text.toString()

        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        btn_save_saudara_edit.showProgress {
            progressColor = Color.WHITE
        }

        NetworkConfig().getService().updateSaudaraSingle(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            saudara?.id.toString(),
            saudaraReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@EditSaudaraActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
                btn_save_saudara_edit.hideDrawable(R.string.save)
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    btn_save_saudara_edit.showDrawable(animatedDrawable) {
                        buttonTextRes = R.string.data_updated
                        textMarginRes = R.dimen.space_10dp
                    }
//                    Toast.makeText(this@EditSaudaraActivity, R.string.data_updated, Toast.LENGTH_SHORT).show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 500)
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_saudara_edit.hideDrawable(R.string.save)
                    }, 3000)
                    btn_save_saudara_edit.hideDrawable(R.string.not_update)
                }
            }
        })
    }

    private fun doDeleteSaudara(saudara: SaudaraResp?) {
        NetworkConfig().getService().deleteSaudara(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            saudara?.id.toString()
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@EditSaudaraActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@EditSaudaraActivity,
                        "Berhasil Hapus Data Saudara",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    Toast.makeText(this@EditSaudaraActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}