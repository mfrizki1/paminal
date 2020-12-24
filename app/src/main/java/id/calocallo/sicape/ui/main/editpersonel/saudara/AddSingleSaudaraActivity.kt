package id.calocallo.sicape.ui.main.editpersonel.saudara

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.model.SaudaraReq
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
        if(hak =="operator"){
            btn_save_single_saudara.gone()
        }

        btn_save_single_saudara.setOnClickListener {
            doSaveSaudara()
        }

        val item = listOf("Kandung","Angkat","Tiri")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        sp_ikatan_single_saudara.setAdapter(adapter)
        sp_ikatan_single_saudara.setOnItemClickListener { parent, view, position, id ->
            when(position){
                0->{
                    saudaraReq.status_ikatan = "kandung"
                }
                1->{
                    saudaraReq.status_ikatan = "angkat"
                }
                2->{
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

        NetworkConfig().getService().addSaudaraSingle(
            "Bearer ${sessionManager.fetchAuthToken()}",
            sessionManager.fetchID().toString(),
            saudaraReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@AddSingleSaudaraActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if(response.isSuccessful){
                    Toast.makeText(this@AddSingleSaudaraActivity, "Data Saudara Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    Toast.makeText(this@AddSingleSaudaraActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

}