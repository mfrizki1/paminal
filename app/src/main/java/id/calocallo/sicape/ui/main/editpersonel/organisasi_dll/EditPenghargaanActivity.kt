package id.calocallo.sicape.ui.main.editpersonel.organisasi_dll

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.PenghargaanReq
import id.calocallo.sicape.network.response.PenghargaanResp
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_penghargaan.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditPenghargaanActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var penghargaanReq =
        PenghargaanReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_penghargaan)
        sessionManager = SessionManager(this)
        val bundel = intent.extras
        val detail = bundel?.getParcelable<PersonelModel>("PERSONEL")
        val penghargaan = bundel?.getParcelable<PenghargaanResp>("PENGHARGAAN")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = detail?.nama

        val hak = sessionManager.fetchHakAkses()
        if (hak == "operator") {
            btn_delete_penghargaan_edit.gone()
            btn_save_penghargaan_edit.gone()
        }

        edt_nama_penghargaan_edit.setText(penghargaan?.penghargaan)
        edt_diterima_penghargaan_edit.setText(penghargaan?.diterima_dari)
        edt_rangka_penghargaan_edit.setText(penghargaan?.dalam_rangka)
        edt_tgl_penghargaan_edit.setText(penghargaan?.tahun)
        edt_ket_penghargaan_edit.setText(penghargaan?.keterangan)

        bindProgressButton(btn_save_penghargaan_edit)
        btn_save_penghargaan_edit.attachTextChangeAnimator()
        btn_save_penghargaan_edit.setOnClickListener {
            doUpdatePenghargaan(penghargaan)
        }

        btn_delete_penghargaan_edit.setOnClickListener {
            alert("Hapus Data", "Yakin Hapus?") {
                positiveButton("Iya") {
                    doDeletePenghargaan(penghargaan)
                }
                negativeButton("Tidak") {
                }
            }.show()
        }
    }


    private fun doUpdatePenghargaan(penghargaan: PenghargaanResp?) {
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        btn_save_penghargaan_edit.showProgress {
            progressColor = Color.WHITE
        }

        penghargaanReq.penghargaan = edt_nama_penghargaan_edit.text.toString()
        penghargaanReq.diterima_dari = edt_diterima_penghargaan_edit.text.toString()
        penghargaanReq.dalam_rangka = edt_rangka_penghargaan_edit.text.toString()
        penghargaanReq.tahun = edt_tgl_penghargaan_edit.text.toString()
        penghargaanReq.keterangan = edt_ket_penghargaan_edit.text.toString()

        NetworkConfig().getService().updatePenghargaanSingle(
            "Bearer ${sessionManager.fetchAuthToken()}",
            penghargaan?.id.toString(),
            penghargaanReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                btn_save_penghargaan_edit.hideDrawable(R.string.save)
                Toast.makeText(this@EditPenghargaanActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    btn_save_penghargaan_edit.showDrawable(animatedDrawable) {
                        buttonTextRes = R.string.data_updated
                        textMarginRes = R.dimen.space_10dp
                    }
//                    Toast.makeText(this@EditPenghargaanActivity, R.string.data_updated, Toast.LENGTH_SHORT).show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 500)
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_penghargaan_edit.hideDrawable(R.string.save)
                    },3000)
                    btn_save_penghargaan_edit.hideDrawable(R.string.not_update)
                }
            }
        })
    }

    private fun doDeletePenghargaan(penghargaan: PenghargaanResp?) {
        NetworkConfig().getService().deletePenghargaan(
            "Bearer ${sessionManager.fetchAuthToken()}",
            penghargaan?.id.toString()
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@EditPenghargaanActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                    .show()

            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@EditPenghargaanActivity,
                        "Berhasil Hapus Data Penghargaan",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    Toast.makeText(this@EditPenghargaanActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}