package id.calocallo.sicape.ui.main.editpersonel.sahabat

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideDrawable
import com.github.razir.progressbutton.showDrawable
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.SahabatReq
import id.calocallo.sicape.network.response.SahabatResp
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_sahabat.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditSahabatActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var sahabatReq = SahabatReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_sahabat)

        sessionManager1 = SessionManager1(this)
        val namaPersonel = intent.extras?.getString("NAMA_PERSONEL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = namaPersonel

        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_save_sahabat_edit.gone()
            btn_delete_sahabat_edit.gone()
        }

        val sahabat = intent.extras?.getParcelable<SahabatResp>("SAHABAT")
        apiDetailSahabat(sahabat)
//        getSahabat(sahabat)
        btn_save_sahabat_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_sahabat_edit)
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
        initSp()
    }

    private fun initSp() {
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

    private fun apiDetailSahabat(sahabat: SahabatResp?) {
        NetworkConfig().getServPers()
            .getDetailSahabat("Bearer ${sessionManager1.fetchAuthToken()}", sahabat?.id.toString())
            .enqueue(object : Callback<SahabatResp> {
                override fun onFailure(call: Call<SahabatResp>, t: Throwable) {
                    Toast.makeText(this@EditSahabatActivity, "$t", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(call: Call<SahabatResp>, response: Response<SahabatResp>) {
                    if (response.isSuccessful) {
                        getSahabat(response.body())
                    } else {
                        Toast.makeText(
                            this@EditSahabatActivity,
                            "Error Koneksi",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            })

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

        initSp()

    }

    private fun doUpdateSahabat(sahabat: SahabatResp?) {
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        sahabatReq.nama = edt_nama_sahabat_edit.text.toString()
        sahabatReq.umur = edt_umur_sahabat_edit.text.toString()
        sahabatReq.alamat = edt_alamat_sahabat_edit.text.toString()
        sahabatReq.pekerjaan = edt_pekerjaan_sahabat_edit.text.toString()
        sahabatReq.alasan = edt_alasan_sahabat_edit.text.toString()
        NetworkConfig().getServPers().updateSahabatSingle(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            sahabat?.id.toString(),
            sahabatReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@EditSahabatActivity, "$t", Toast.LENGTH_SHORT).show()
                btn_save_sahabat_edit.hideDrawable(R.string.save)
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    btn_save_sahabat_edit.showDrawable(animatedDrawable) {
                        buttonTextRes = R.string.data_updated
                        textMarginRes = R.dimen.space_10dp
                    }
//                    Toast.makeText(this@EditSahabatActivity, "Berhasil Update Data Sahabat", Toast.LENGTH_SHORT).show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 500)
                } else {
                    Toast.makeText(
                        this@EditSahabatActivity,
                        "${response.body()?.message}",
                        Toast.LENGTH_SHORT
                    ).show()

                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_sahabat_edit.hideDrawable(R.string.save)
                    }, 3000)
                    btn_save_sahabat_edit.hideDrawable(R.string.not_update)
                }
            }
        })
    }

    private fun doDeleteSahabat(sahabat: SahabatResp?) {
        NetworkConfig().getServPers().deleteSahabat(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            sahabat?.id.toString()
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@EditSahabatActivity, "$t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@EditSahabatActivity,
                        R.string.data_deleted,
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    Toast.makeText(
                        this@EditSahabatActivity,
                        "${response.body()?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}