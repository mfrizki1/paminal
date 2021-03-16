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
import id.calocallo.sicape.model.AllPersonelModel1
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.network.response.DetailPenghargaanResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.utils.ext.toast
import kotlinx.android.synthetic.main.activity_edit_penghargaan.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditPenghargaanActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var penghargaanReq =
        PenghargaanReq()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_penghargaan)
        sessionManager1 = SessionManager1(this)
        val bundel = intent.extras
        val detail = bundel?.getParcelable<AllPersonelModel1>("PERSONEL")
        val penghargaan = bundel?.getParcelable<PenghargaanResp>("PENGHARGAAN")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = detail?.nama

        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_delete_penghargaan_edit.gone()
            btn_save_penghargaan_edit.gone()
        }
        getDetailPenghargaan(penghargaan)


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

    private fun getDetailPenghargaan(penghargaan: PenghargaanResp?) {
        NetworkConfig().getServPers().getDetailPenghargaan(
            "Bearer ${sessionManager1.fetchAuthToken()}", penghargaan?.id.toString()
        ).enqueue(object : Callback<DetailPenghargaanResp> {
            override fun onFailure(call: Call<DetailPenghargaanResp>, t: Throwable) {
                toast("$t")
            }

            override fun onResponse(
                call: Call<DetailPenghargaanResp>,
                response: Response<DetailPenghargaanResp>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    edt_nama_penghargaan_edit.setText(data?.penghargaan)
                    edt_diterima_penghargaan_edit.setText(data?.diterima_dari)
                    edt_rangka_penghargaan_edit.setText(data?.dalam_rangka)
                    edt_tgl_penghargaan_edit.setText(data?.tahun)
                    edt_ket_penghargaan_edit.setText(data?.keterangan)
                } else {
                 toast(R.string.error_conn)
                }
            }
        })

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

        NetworkConfig().getServPers().updatePenghargaanSingle(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            penghargaan?.id.toString(),
            penghargaanReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                btn_save_penghargaan_edit.hideDrawable(R.string.save)
                toast("$t")
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    btn_save_penghargaan_edit.showDrawable(animatedDrawable) {
                        buttonTextRes = R.string.data_updated
                        textMarginRes = R.dimen.space_10dp
                    }
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 500)
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_penghargaan_edit.hideDrawable(R.string.save)
                    }, 3000)
                    btn_save_penghargaan_edit.hideDrawable(R.string.not_update)
                    toast("${response.body()?.message}")
                }
            }
        })
    }

    private fun doDeletePenghargaan(penghargaan: PenghargaanResp?) {
        NetworkConfig().getServPers().deletePenghargaan(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            penghargaan?.id.toString()
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                toast("$t")
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    toast(R.string.data_deleted)
                    finish()
                } else {
                    toast("${response.body()?.message}")

                }
            }
        })
    }
}