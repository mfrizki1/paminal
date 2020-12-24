package id.calocallo.sicape.ui.main.editpersonel.sahabat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.model.SahabatReq
import id.calocallo.sicape.model.SahabatResp
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_orangs.*
import kotlinx.android.synthetic.main.activity_edit_sahabat.*
import kotlinx.android.synthetic.main.activity_edit_tokoh.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditSahabatActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var sahabatReq = SahabatReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_sahabat)

        sessionManager = SessionManager(this)
        val namaPersonel = intent.extras?.getString("NAMA_PERSONEL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = namaPersonel

        val hak = sessionManager.fetchHakAkses()
        if (hak == "operator") {
            btn_save_sahabat_edit.gone()
            btn_delete_sahabat_edit.gone()
        }

        val sahabat = intent.extras?.getParcelable<SahabatResp>("SAHABAT")

        getSahabat(sahabat)

        btn_save_sahabat_edit.setOnClickListener {
            doUpdateSahabat(sahabat)
        }
        btn_delete_sahabat_edit.setOnClickListener {
            alert("Yakin Hapus Data?") {
                positiveButton("Iya") {
                    doDeleteSahabat(sahabat)
                }
                negativeButton("Tidak") {}
            }.show()
        }
        val jk = listOf("Laki-Laki", "Perempuan")
        val adapterJK = ArrayAdapter(this, R.layout.item_spinner, jk)
        spinner_jk_sahabat_edit.setAdapter(adapterJK)
        spinner_jk_sahabat_edit.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> sahabatReq.jenis_kelamin = "laki_laki"
                1 -> sahabatReq.jenis_kelamin = "perempuan"
            }
        }

    }

    private fun getSahabat(sahabat: SahabatResp?) {
        when (sahabat?.jenis_kelamin) {
            "laki_laki" -> {
                sahabatReq.jenis_kelamin = "laki_laki"
                spinner_jk_sahabat_edit.setText("Laki-Laki")
            }
            "perempuan" -> {
                sahabatReq.jenis_kelamin = "perempuan"
                spinner_jk_sahabat_edit.setText("Perempuan")
            }

        }
        edt_nama_sahabat_edit.setText(sahabat?.nama)
        edt_umur_sahabat_edit.setText(sahabat?.umur)
        edt_alamat_sahabat_edit.setText(sahabat?.alamat)
        edt_pekerjaan_sahabat_edit.setText(sahabat?.pekerjaan)
        edt_alasan_sahabat_edit.setText(sahabat?.alasan)
    }

    private fun doUpdateSahabat(sahabat: SahabatResp?) {
        sahabatReq.nama = edt_nama_sahabat_edit.text.toString()
        sahabatReq.umur = edt_umur_sahabat_edit.text.toString()
        sahabatReq.alamat = edt_alamat_sahabat_edit.text.toString()
        sahabatReq.pekerjaan = edt_pekerjaan_sahabat_edit.text.toString()
        sahabatReq.alasan = edt_alasan_sahabat_edit.text.toString()
        NetworkConfig().getService().updateSahabatSingle(
            "Bearer ${sessionManager.fetchAuthToken()}",
            sahabat?.id.toString(),
            sahabatReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@EditSahabatActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EditSahabatActivity, "Berhasil Update Data Sahabat", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@EditSahabatActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun doDeleteSahabat(sahabat: SahabatResp?){
        NetworkConfig().getService().deleteSahabat(
            "Bearer ${sessionManager.fetchAuthToken()}",
            sahabat?.id.toString()
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@EditSahabatActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EditSahabatActivity, "Berhasil Hapus Data Sahabat", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@EditSahabatActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}