package id.calocallo.sicape.ui.main.editpersonel.orangs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.model.OrangsReq
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.model.SaudaraReq
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_single_orangs.*
import kotlinx.android.synthetic.main.activity_add_single_saudara.*
import kotlinx.android.synthetic.main.activity_pick_orangs.*
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
            "disegani" ->  spinner_orangs_single.setText("Orang Disegani Karena Adat")
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
        btn_save_single_orangs.setOnClickListener {
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
                }

                override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@AddSingleOrangsActivity,
                            "Berhasil Menambahkan Data",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(this@AddSingleOrangsActivity, "Error", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
        }
    }
}