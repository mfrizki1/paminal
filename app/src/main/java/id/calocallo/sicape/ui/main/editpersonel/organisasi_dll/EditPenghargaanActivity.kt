package id.calocallo.sicape.ui.main.editpersonel.organisasi_dll

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PenghargaanReq
import id.calocallo.sicape.model.PenghargaanResp
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
    private var penghargaanReq = PenghargaanReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_penghargaan)
        sessionManager = SessionManager(this    )
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
                Toast.makeText(this@EditPenghargaanActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@EditPenghargaanActivity,
                        "Berhasil Update Data Penghargaan",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    Toast.makeText(this@EditPenghargaanActivity, "Error", Toast.LENGTH_SHORT).show()
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