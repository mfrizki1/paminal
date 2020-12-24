package id.calocallo.sicape.ui.main.editpersonel.tokoh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.model.TokohReq
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_single_tokoh.*
import kotlinx.android.synthetic.main.activity_edit_tokoh.*
import kotlinx.android.synthetic.main.activity_pick_tokoh.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddSingleTokohActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var tokohReq = TokohReq()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_single_tokoh)

        sessionManager = SessionManager(this)
        val detailPersonel = intent.extras?.getParcelable<PersonelModel>("PERSONEL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = detailPersonel?.nama

        val hak = sessionManager.fetchHakAkses()
        if(hak == "operator"){
            btn_save_single_tokoh.gone()
        }
        btn_save_single_tokoh.setOnClickListener {
            tokohReq.nama = edt_nama_single_tokoh.text.toString()
            tokohReq.asal_negara = edt_asal_negara_single_tokoh.text.toString()
            tokohReq.alasan = edt_alasan_single_tokoh.text.toString()
            tokohReq.keterangan = edt_alasan_single_tokoh.text.toString()

            NetworkConfig().getService().addTokohSingle(
                "Bearer ${sessionManager.fetchAuthToken()}",
                sessionManager.fetchID().toString(),
                tokohReq
            ).enqueue(object : Callback<BaseResp> {
                override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                    Toast.makeText(this@AddSingleTokohActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@AddSingleTokohActivity,
                            "Berhasil Tambah Data Tokoh",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(this@AddSingleTokohActivity, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            })

        }

    }
}