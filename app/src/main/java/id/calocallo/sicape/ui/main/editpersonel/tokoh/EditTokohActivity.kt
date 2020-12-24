package id.calocallo.sicape.ui.main.editpersonel.tokoh

import android.os.Bundle
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.model.TokohReq
import id.calocallo.sicape.model.TokohResp
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_tokoh.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditTokohActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var tokohReq = TokohReq()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_tokoh)
        sessionManager = SessionManager(this)
        val namaPersonel = intent.extras?.getString("NAMA_PERSONEL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = namaPersonel


        val hak = sessionManager.fetchHakAkses()
        if (hak == "operator") {
            btn_save_tokoh_edit.gone()
            btn_delete_tokoh_edit.gone()
        }

        val tokoh = intent.extras?.getParcelable<TokohResp>("TOKOH")
        getTokoh(tokoh)

        btn_save_tokoh_edit.setOnClickListener {
            doUpdateTokoh(tokoh)
        }

        btn_delete_tokoh_edit.setOnClickListener {
            alert("Yakin Hapus Data?") {
                positiveButton("Iya") {
                    doDeleteTokoh(tokoh)
                }
                negativeButton("Tidak") {}
            }.show()
        }
    }

    private fun getTokoh(tokoh: TokohResp?) {
        edt_nama_tokoh_edit.setText(tokoh?.nama)
        edt_asal_negara_tokoh_edit.setText(tokoh?.asal_negara)
        edt_alasan_tokoh_edit.setText(tokoh?.alasan)
        edt_alasan_tokoh_edit.setText(tokoh?.keterangan)
    }

    private fun doUpdateTokoh(tokoh: TokohResp?) {
        tokohReq.nama = edt_nama_tokoh_edit.text.toString()
        tokohReq.asal_negara = edt_asal_negara_tokoh_edit.text.toString()
        tokohReq.alasan = edt_alasan_tokoh_edit.text.toString()
        tokohReq.keterangan = edt_alasan_tokoh_edit.text.toString()
        NetworkConfig().getService().updateTokohSingle(
            "Bearer ${sessionManager.fetchAuthToken()}",
            tokoh?.id.toString(),
            tokohReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@EditTokohActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@EditTokohActivity,
                        "Berhasil Update Data Tokoh",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    Toast.makeText(this@EditTokohActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun doDeleteTokoh(tokoh: TokohResp?) {
        NetworkConfig().getService().deleteTokoh(
            "Bearer ${sessionManager.fetchAuthToken()}",
            tokoh?.id.toString()
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@EditTokohActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@EditTokohActivity,
                        "Berhasil Hapus Data Tokoh",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    Toast.makeText(this@EditTokohActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })

    }
}