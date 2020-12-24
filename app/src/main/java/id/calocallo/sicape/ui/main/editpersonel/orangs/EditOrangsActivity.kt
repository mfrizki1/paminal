package id.calocallo.sicape.ui.main.editpersonel.orangs

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.model.OrangsReq
import id.calocallo.sicape.model.OrangsResp
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_orangs.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditOrangsActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var orangsReq = OrangsReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_orangs)
        sessionManager = SessionManager(this)

        val namaPersonel = intent.extras?.getString("NAMA_PERSONEL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = namaPersonel

        val hak = sessionManager.fetchHakAkses()
        if (hak == "operator") {
            btn_save_orangs_edit.gone()
            btn_delete_orangs_edit.gone()
        }
        val orangs = intent.extras?.getParcelable<OrangsResp>("ORANGS")
        when (orangs?.jenis_kelamin) {
            "laki_laki" -> {
                orangsReq.jenis_kelamin = "laki_laki"
                spinner_jk_orangs_edit.setText("Laki-Laki")
            }
            "perempuan" -> {
                orangsReq.jenis_kelamin = "perempuan"
                spinner_jk_orangs_edit.setText("Perempuan")
            }
        }
        var menu = intent.extras?.getString("MENU")
        when (menu) {
            "berjasa" -> {
                menu = "berjasa"
                spinner_orangs_edit.setText("Orang Berjasa Selain Orang Tua")
            }
            "disegani" -> {
                menu = "disegani"
                spinner_orangs_edit.setText("Orang Disegani Karena Adat")
            }
        }

        btn_save_orangs_edit.setOnClickListener {
            doUpdateOrangs(orangs, menu)
        }

        btn_delete_orangs_edit.setOnClickListener {
            alert("Yakin Hapus Data?") {
                positiveButton("Iya") {
                    doDeleteOrangs(orangs, menu)
                }
                negativeButton("Tidak") {}
            }.show()
        }

        val item = listOf("Orang Berjasa Selain Orang Tua", "Orang Disegani Karena Adat")
//        spinner_orangs.setText(currMenu)
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        spinner_orangs_edit.setAdapter(adapter)
        spinner_orangs_edit.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> menu = "berjasa"
                1 -> menu = "disegani"
            }
        }

        val jk = listOf("Laki-Laki", "Perempuan")
        val adapterJK = ArrayAdapter(this, R.layout.item_spinner, jk)
        spinner_jk_orangs_edit.setAdapter(adapterJK)
        spinner_jk_orangs_edit.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> orangsReq.jenis_kelamin = "laki_laki"
                1 -> orangsReq.jenis_kelamin = "perempuan"
            }
        }
        getOrangs(orangs)

    }

    private fun getOrangs(data: OrangsResp?){
        edt_nama_lengkap_orangs_edit.setText(data?.nama)
        edt_umur_orangs_edit.setText(data?.umur)
        edt_alamat_orangs_edit.setText(data?.alamat)
        edt_pekerjaan_orangs_edit.setText(data?.pekerjaan)
        edt_ket_orangs_edit.setText(data?.keterangan)
    }

    private fun doUpdateOrangs(data: OrangsResp?, menu: String?) {
        orangsReq.nama = edt_nama_lengkap_orangs_edit.text.toString()
        orangsReq.umur = edt_umur_orangs_edit.text.toString()
        orangsReq.alamat = edt_alamat_orangs_edit.text.toString()
        orangsReq.pekerjaan = edt_pekerjaan_orangs_edit.text.toString()
        orangsReq.keterangan = edt_ket_orangs_edit.text.toString()
        NetworkConfig().getService().updateOrangsSingle(
            "Bearer ${sessionManager.fetchAuthToken()}",
            menu.toString(),
            data?.id.toString(),
            orangsReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@EditOrangsActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EditOrangsActivity, "Berhasil Update Data", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@EditOrangsActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun doDeleteOrangs(data: OrangsResp?, menu: String?) {
        NetworkConfig().getService().deleteOrangs(
            "Bearer ${sessionManager.fetchAuthToken()}",
            menu.toString(),
            data?.id.toString()
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@EditOrangsActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if(response.isSuccessful){
                    Toast.makeText(this@EditOrangsActivity, "Berhasil Hapus Data", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@EditOrangsActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }
}
