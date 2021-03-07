package id.calocallo.sicape.ui.main.editpersonel.orangs

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.OrangsReq
import id.calocallo.sicape.network.response.OrangsResp
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_orangs.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditOrangsActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var orangsReq = OrangsReq()
    private var menu = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_orangs)
        sessionManager1 = SessionManager1(this)

        val namaPersonel = intent.extras?.getString("NAMA_PERSONEL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = namaPersonel

        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_save_orangs_edit.gone()
            btn_delete_orangs_edit.gone()
        }

        val orangs = intent.extras?.getParcelable<OrangsResp>("ORANGS")

        menu = intent.extras?.getString("MENU").toString()
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

        apiDetailOrangs(orangs, menu)

        btn_save_orangs_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_orangs_edit)
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

        initSp()

    }

    private fun initSp() {
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
    }

    private fun apiDetailOrangs(orangs: OrangsResp?, menu: String?) {
        NetworkConfig().getServPers().getDetailOrang(
            "Bearer ${sessionManager1.fetchAuthToken()}", menu.toString(), orangs?.id.toString()
        ).enqueue(object : Callback<OrangsResp> {
            override fun onFailure(call: Call<OrangsResp>, t: Throwable) {
                Toast.makeText(this@EditOrangsActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<OrangsResp>, response: Response<OrangsResp>) {
                if (response.isSuccessful) {
                    getOrangs(response.body())
                } else {
                    Toast.makeText(this@EditOrangsActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                        .show()

                }
            }
        })
    }

    private fun getOrangs(data: OrangsResp?) {
        edt_nama_lengkap_orangs_edit.setText(data?.nama)
        edt_umur_orangs_edit.setText(data?.umur)
        edt_alamat_orangs_edit.setText(data?.alamat)
        edt_pekerjaan_orangs_edit.setText(data?.pekerjaan)
        edt_ket_orangs_edit.setText(data?.keterangan)
        orangsReq.jenis_kelamin = data?.jenis_kelamin
        /*2*/
        when (data?.jenis_kelamin) {
            "laki_laki" -> {
                spinner_jk_orangs_edit.setText("Laki-Laki")
            }
            "perempuan" -> {
                spinner_jk_orangs_edit.setText("Perempuan")
            }
        }
        initSp()

    }

    private fun doUpdateOrangs(data: OrangsResp?, menu: String?) {
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        btn_save_orangs_edit.showProgress {
            progressColor = Color.WHITE
        }

        orangsReq.nama = edt_nama_lengkap_orangs_edit.text.toString()
        orangsReq.umur = edt_umur_orangs_edit.text.toString()
        orangsReq.alamat = edt_alamat_orangs_edit.text.toString()
        orangsReq.pekerjaan = edt_pekerjaan_orangs_edit.text.toString()
        orangsReq.keterangan = edt_ket_orangs_edit.text.toString()
        NetworkConfig().getServPers().updateOrangsSingle(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            menu.toString(),
            data?.id.toString(),
            orangsReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@EditOrangsActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
                btn_save_orangs_edit.hideDrawable(R.string.save)
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    btn_save_orangs_edit.showDrawable(animatedDrawable) {
                        buttonTextRes = R.string.data_updated
                        textMarginRes = R.dimen.space_10dp
                    }
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 500)
//                    Toast.makeText(this@EditOrangsActivity, R.string.data_updated, Toast.LENGTH_SHORT).show()
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_orangs_edit.hideDrawable(R.string.save)
                    }, 3000)
                    btn_save_orangs_edit.hideDrawable(R.string.not_update)
                }
            }
        })
    }

    private fun doDeleteOrangs(data: OrangsResp?, menu: String?) {
        NetworkConfig().getServPers().deleteOrangs(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            menu.toString(),
            data?.id.toString()
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@EditOrangsActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@EditOrangsActivity,
                        "Berhasil Hapus Data",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    Toast.makeText(this@EditOrangsActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }
}
