package id.calocallo.sicape.ui.main.editpersonel.alamat

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AllPersonelModel1
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.AlamatReq
import id.calocallo.sicape.network.response.AlamatResp
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.network.response.DetailAlamatResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.utils.ext.toast
import kotlinx.android.synthetic.main.activity_edit_alamat.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditAlamatActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private val alamatReq = AlamatReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_alamat)
        sessionManager1 = SessionManager1(this)
        val personel = intent.extras?.getParcelable<AllPersonelModel1>("PERSONEL")
        val alamat = intent.extras?.getParcelable<AlamatResp>("ALAMAT")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = personel?.nama

        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_save_alamat_edit.gone()
            btn_delete_alamat_edit.gone()
        }
        getDetail(alamat)


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

    private fun getDetail(alamat: AlamatResp?) {
        NetworkConfig().getServPers()
            .getDetailAlamat("Bearer ${sessionManager1.fetchAuthToken()}", alamat?.id.toString())
            .enqueue(object : Callback<DetailAlamatResp> {
                override fun onFailure(call: Call<DetailAlamatResp>, t: Throwable) {
                    toast("$t")
                }

                override fun onResponse(
                    call: Call<DetailAlamatResp>,
                    response: Response<DetailAlamatResp>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        edt_alamat_edit.setText(data?.alamat)
                        edt_thn_awal_alamat_edit.setText(data?.tahun_awal)
                        edt_thn_akhir_alamat_edit.setText(data?.tahun_akhir)
                        edt_rangka_alamat_edit.setText(data?.dalam_rangka)
                        edt_ket_alamat_edit.setText(data?.keterangan)
                    } else {
                        toast(R.string.error_conn)
                    }
                }
            })

    }

    private fun doDeletePekerjaan(alamat: AlamatResp) {
        NetworkConfig().getServPers().deleteAlamat(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            alamat.id.toString()
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

        NetworkConfig().getServPers().updateAlamatSingle(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            alamat?.id.toString(),
            alamatReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                toast("$t")
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
                    toast("${response.body()?.message}")
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_alamat_edit.hideDrawable(R.string.save)
                    }, 3000)
                    btn_save_alamat_edit.hideDrawable(R.string.not_update)
                }
            }
        })

    }
}