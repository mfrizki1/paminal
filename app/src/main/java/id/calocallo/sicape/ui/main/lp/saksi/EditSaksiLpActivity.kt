package id.calocallo.sicape.ui.main.lp.saksi

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.RadioButton
import android.widget.Toast
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.SaksiLpReq
import id.calocallo.sicape.network.response.AddSaksiPersonelResp
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.network.response.LpSaksiResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_saksi_lp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditSaksiLpActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var saksiReq = SaksiLpReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_saksi_lp)
        sessionManager1 = SessionManager1(this)
        val namaJenis = intent.extras?.getString("NAMA_JENIS")
        val saksi = intent.extras?.getParcelable<LpSaksiResp>("SAKSI_EDIT")
        setupActionBarWithBackButton(toolbar)
        when (namaJenis) {
            "pidana" -> supportActionBar?.title = "Edit Data Laporan Pidana"
            "disiplin" -> supportActionBar?.title = "Edit Data Laporan Disiplin"
            "kode_etik" -> supportActionBar?.title = "Edit Data Laporan Kode Etik"
        }

        getViewSaksi(saksi)
        btn_save_edit_saksi_lp.attachTextChangeAnimator()
        bindProgressButton(btn_save_edit_saksi_lp)
        btn_save_edit_saksi_lp.setOnClickListener {
            saksiReq.nama = edt_saksi_edit.text.toString()
            saksiReq.tempat_lahir = edt_tempat_lahir_saksi_edit.text.toString()
            saksiReq.tanggal_lahir = edt_tanggal_lahir_saksi_edit.text.toString()
            saksiReq.pekerjaan = edt_pekerjaan_saksi_edit.text.toString()
            saksiReq.alamat = edt_alamat_saksi_edit.text.toString()

            btn_save_edit_saksi_lp.showProgress { progressColor = Color.WHITE }
            updSaksiSingle(saksi)
            /* val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
             val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
             animatedDrawable.setBounds(0, 0, size, size)

             btn_save_edit_saksi_lp.showProgress {
                 progressColor = Color.WHITE
             }
             btn_save_edit_saksi_lp.showDrawable(animatedDrawable) {
                 buttonTextRes = R.string.data_updated
                 textMarginRes = R.dimen.space_10dp
             }
             Handler(Looper.getMainLooper()).postDelayed({
                 btn_save_edit_saksi_lp.hideDrawable(R.string.save)
                 Log.e("edit_saksi", "$saksiReq")
             }, 3000)*/
        }

        btn_delete_edit_saksi_lp.setOnClickListener {
            alert("Yakin Hapus Data?") {
                positiveButton("Iya") {
                    deleteSaksiSingle(saksi)
                }
                negativeButton("Tidak") {}
            }.show()
        }
    }

    private fun updSaksiSingle(saksi: LpSaksiResp?) {
        saksi?.id?.let {
            NetworkConfig().getServLp().updSaksiSingle(
                "Bearer ${sessionManager1.fetchAuthToken()}", it, saksiReq
            ).enqueue(object : Callback<Base1Resp<AddSaksiPersonelResp>> {
                override fun onFailure(call: Call<Base1Resp<AddSaksiPersonelResp>>, t: Throwable) {
                    btn_save_edit_saksi_lp.hideProgress(R.string.not_update)
                    Toast.makeText(
                        this@EditSaksiLpActivity, R.string.error_conn, Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResponse(
                    call: Call<Base1Resp<AddSaksiPersonelResp>>,
                    response: Response<Base1Resp<AddSaksiPersonelResp>>
                ) {
                    if (response.body()?.message == "Data saksi kode etik updated succesfully") {
                        btn_save_edit_saksi_lp.hideProgress(R.string.data_updated)
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        }, 500)
                    } else {
                        btn_save_edit_saksi_lp.hideProgress(R.string.not_update)
                        Toast.makeText(
                            this@EditSaksiLpActivity, R.string.error_conn, Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
        }
    }

    private fun deleteSaksiSingle(saksi: LpSaksiResp?) {
        saksi?.id?.let {
            NetworkConfig().getServLp().delSaksiSingle(
                "Bearer ${sessionManager1.fetchAuthToken()}", it
            ).enqueue(object : Callback<BaseResp> {
                override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                    Toast.makeText(
                        this@EditSaksiLpActivity, R.string.error_conn, Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                    if (response.body()?.message == "Data saksi kode etik removed succesfully") {
                        Toast.makeText(
                            this@EditSaksiLpActivity, R.string.data_deleted, Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@EditSaksiLpActivity, R.string.error_conn, Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            })
        }
    }

    private fun getViewSaksi(saksi: LpSaksiResp?) {
        edt_saksi_edit.setText(saksi?.nama)
        edt_tempat_lahir_saksi_edit.setText(saksi?.tempat_lahir)
        edt_tanggal_lahir_saksi_edit.setText(saksi?.tanggal_lahir)
        edt_pekerjaan_saksi_edit.setText(saksi?.pekerjaan)
        edt_alamat_saksi_edit.setText(saksi?.alamat)
        if (saksi?.is_korban == 1) {
            rb_korban_saksi.isChecked = true
            saksiReq.is_korban = 1
        } else if (saksi?.is_korban == 0) {
            rb_saksi.isChecked = true
            saksiReq.is_korban = 0
        }

        rg_korban_saksi_edit.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            if (radio.text.toString().toLowerCase() == "korban") {
                radio.isChecked = true
                saksiReq.is_korban = 1
                //set pelapor =>saksi
            } else {
                radio.isChecked = true
                saksiReq.is_korban = 0
                //set pelapor =>korban
            }
        }
    }
}