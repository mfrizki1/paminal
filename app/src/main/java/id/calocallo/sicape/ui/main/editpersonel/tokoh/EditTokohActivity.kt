package id.calocallo.sicape.ui.main.editpersonel.tokoh

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.TokohReq
import id.calocallo.sicape.network.response.TokohResp
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_tokoh.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditTokohActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var tokohReq = TokohReq()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_tokoh)
        sessionManager1 = SessionManager1(this)
        val namaPersonel = intent.extras?.getString("NAMA_PERSONEL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title =namaPersonel


        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_save_tokoh_edit.gone()
            btn_delete_tokoh_edit.gone()
        }

        val tokoh = intent.extras?.getParcelable<TokohResp>("TOKOH")
        apiDetailTokoh(tokoh)
//        getTokoh(tokoh)
        bindProgressButton(btn_save_tokoh_edit)
        btn_save_tokoh_edit.attachTextChangeAnimator()
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

    private fun apiDetailTokoh(tokoh: TokohResp?) {
        NetworkConfig().getServPers()
            .getDetailTokoh("Bearer ${sessionManager1.fetchAuthToken()}", tokoh?.id.toString())
            .enqueue(object : Callback<TokohResp> {
                override fun onFailure(call: Call<TokohResp>, t: Throwable) {
                    Toast.makeText(this@EditTokohActivity, "$t", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(call: Call<TokohResp>, response: Response<TokohResp>) {
                    if (response.isSuccessful) {
                        getTokoh(response.body())
                    } else {
                        Toast.makeText(this@EditTokohActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
    }

    private fun getTokoh(tokoh: TokohResp?) {
        edt_nama_tokoh_edit.setText(tokoh?.nama)
        edt_asal_negara_tokoh_edit.setText(tokoh?.asal_negara)
        edt_alasan_tokoh_edit.setText(tokoh?.alasan)
        edt_ket_tokoh_edit.setText(tokoh?.keterangan)
    }

    private fun doUpdateTokoh(tokoh: TokohResp?) {
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        tokohReq.nama = edt_nama_tokoh_edit.text.toString()
        tokohReq.asal_negara = edt_asal_negara_tokoh_edit.text.toString()
        tokohReq.alasan = edt_alasan_tokoh_edit.text.toString()
        tokohReq.keterangan = edt_alasan_tokoh_edit.text.toString()

        btn_save_tokoh_edit.showProgress {
            progressColor = Color.WHITE
        }

        NetworkConfig().getServPers().updateTokohSingle(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            tokoh?.id.toString(),
            tokohReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                btn_save_tokoh_edit.hideDrawable(R.string.save)
                Toast.makeText(this@EditTokohActivity, "$t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {

                    btn_save_tokoh_edit.showDrawable(animatedDrawable) {
                        textMarginRes = R.dimen.space_10dp
                        buttonTextRes = R.string.data_updated
                    }
//                    Toast.makeText(this@EditTokohActivity, "Berhasil Update Data Tokoh", Toast.LENGTH_SHORT).show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 500)
                } else {
                    Toast.makeText(
                        this@EditTokohActivity,
                        "${response.body()?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_tokoh_edit.hideDrawable(R.string.save)
                    }, 3000)
                    btn_save_tokoh_edit.hideDrawable(R.string.not_save)
                }
            }
        })
    }

    private fun doDeleteTokoh(tokoh: TokohResp?) {
        NetworkConfig().getServPers().deleteTokoh(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            tokoh?.id.toString()
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@EditTokohActivity, "$t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@EditTokohActivity,
                        R.string.data_deleted,
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    Toast.makeText(
                        this@EditTokohActivity,
                        "${response.body()?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

    }
}