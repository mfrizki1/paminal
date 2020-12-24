package id.calocallo.sicape.ui.main.editpersonel.sahabat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.model.SahabatReq
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_single_orangs.*
import kotlinx.android.synthetic.main.activity_add_single_sahabat.*
import kotlinx.android.synthetic.main.activity_edit_sahabat.*
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
                    Toast.makeText(this@AddSingleSahabatActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@AddSingleSahabatActivity, "Berhasil Tambah Data Sahabat", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@AddSingleSahabatActivity, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            })

        }

    }
}