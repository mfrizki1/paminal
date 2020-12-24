package id.calocallo.sicape.ui.main.editpersonel.alamat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.AlamatReq
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager
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
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_single_alamat)
        setupActionBarWithBackButton(toolbar)
//        supportActionBar?.title =

//        number = 2
//        bcd = "SDDSFNSIDASNDI"
        sessionManager = SessionManager(this)
        btn_save_alamat.setOnClickListener {
            alamatReq.alamat = edt_alamat.text.toString()
            alamatReq.tahun_awal = edt_thn_awal_alamat.text.toString()
            alamatReq.tahun_akhir = edt_thn_akhir_alamat.text.toString()
            alamatReq.dalam_rangka = edt_rangka_alamat.text.toString()
            alamatReq.keterangan = edt_ket_alamat.text.toString()

            NetworkConfig().getService().addAlamatSingle(
                "Bearer ${sessionManager.fetchAuthToken()}",
//                "4",
                sessionManager.fetchID().toString(),
                alamatReq
            ).enqueue(object : Callback<BaseResp> {
                override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                    Toast.makeText(this@AddSingleAlamatActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@AddSingleAlamatActivity, "Data Alamat Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@AddSingleAlamatActivity, "Error", Toast.LENGTH_SHORT).show()
                    }

                }
            })
        }
    }
}