package id.calocallo.sicape.ui.main.editpersonel.alamat

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.AlamatReq
import id.calocallo.sicape.network.response.AlamatResp
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_alamat.*
import kotlinx.android.synthetic.main.activity_edit_keluarga.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditAlamatActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private val alamatReq = AlamatReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_alamat)
        sessionManager = SessionManager(this)
        val personel = intent.extras?.getParcelable<PersonelModel>("PERSONEL")
        val alamat = intent.extras?.getParcelable<AlamatResp>("ALAMAT")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = personel?.nama

        val hak = sessionManager.fetchHakAkses()
        if (hak == "operator") {
            btn_save_alamat_edit.gone()
            btn_delete_alamat_edit.gone()
        }

        edt_alamat_edit.setText(alamat?.alamat)
        edt_thn_awal_alamat_edit.setText(alamat?.tahun_awal)
        edt_thn_akhir_alamat_edit.setText(alamat?.tahun_akhir)
        edt_rangka_alamat_edit.setText(alamat?.dalam_rangka)
        edt_ket_alamat_edit.setText(alamat?.keterangan)

        btn_save_alamat_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_alamat_edit)
        btn_save_alamat_edit.setOnClickListener {
            if (alamat != null) {
                doUpdateAlamat(alamat)
            }
        }

        btn_delete_alamat_edit.setOnClickListener {
            alert("Hapus Data", "Yakin Hapus?") {
                positiveButton("Iya") {
                    if (alamat != null) {
                        doDeletePekerjaan(alamat)
                    }
                }
                negativeButton("Tidak") {
                }
            }.show()
        }
    }

    private fun doDeletePekerjaan(alamat: AlamatResp) {
        NetworkConfig().getService().deleteAlamat(
            "Bearer ${sessionManager.fetchAuthToken()}",
            alamat.id.toString()
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@EditAlamatActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    finish()
                } else {
                    Toast.makeText(this@EditAlamatActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun doUpdateAlamat(alamat: AlamatResp?) {
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        alamatReq.alamat = edt_alamat_edit.text.toString()
        alamatReq.tahun_awal = edt_thn_awal_alamat_edit.text.toString()
        alamatReq.tahun_akhir = edt_thn_akhir_alamat_edit.text.toString()
        alamatReq.dalam_rangka = edt_rangka_alamat_edit.text.toString()
        alamatReq.keterangan = edt_ket_alamat_edit.text.toString()

        btn_save_alamat_edit.showProgress {
            progressColor = Color.WHITE
        }

        NetworkConfig().getService().updateAlamatSingle(
            "Bearer ${sessionManager.fetchAuthToken()}",
            alamat?.id.toString(),
            alamatReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@EditAlamatActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
                btn_save_alamat_edit.hideDrawable(R.string.save)
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    btn_save_alamat_edit.showDrawable(animatedDrawable) {
                        buttonTextRes = R.string.data_updated
                        textMarginRes = R.dimen.space_10dp
                    }
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 500)
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_alamat_edit.hideDrawable(R.string.save)
                    },3000)
                    btn_save_alamat_edit.hideDrawable(R.string.not_update)
                }
            }
        })

    }
}