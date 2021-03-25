package id.calocallo.sicape.ui.main.lp.saksi

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.SaksiLpReq
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.utils.ext.toast
import kotlinx.android.synthetic.main.activity_edit_saksi_lp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class EditSaksiLpActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var saksiReq = SaksiLpReq()
    private var jenisSaksi: String? = null
    private var idPersonel: Int? = null
    private var isKorban: Int? = null
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

        apiDetailSaksi(saksi)
//        getViewSaksi(saksi)
        btn_save_edit_saksi_lp.attachTextChangeAnimator()
        bindProgressButton(btn_save_edit_saksi_lp)
        btn_save_edit_saksi_lp.setOnClickListener {
            saksiReq.nama = edt_saksi_edit.text.toString()
            saksiReq.tempat_lahir = edt_tempat_lahir_saksi_edit.text.toString()
            saksiReq.tanggal_lahir = edt_tanggal_lahir_saksi_edit.text.toString()
            saksiReq.pekerjaan = edt_pekerjaan_saksi_edit.text.toString()
            saksiReq.alamat = edt_alamat_saksi_edit.text.toString()
            saksiReq.is_korban = isKorban
            saksiReq.id_personel = idPersonel

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

        btn_choose_personel_saksi_lp_edit.setOnClickListener {
            val intent = Intent(this, KatPersonelActivity::class.java)
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, REQ_PERSONEL)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        rg_korban_saksi_edit.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            if (radio.isChecked && radio.text == "Korban") {
                isKorban = 1
            } else {
                isKorban = 0
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQ_PERSONEL) {
            val personelSaksi = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
            idPersonel = personelSaksi?.id
            txt_nama_saksi_lp_edit.text = personelSaksi?.nama
            txt_pangkat_saksi_lp_edit.text =
                personelSaksi?.pangkat.toString().toUpperCase(Locale.ROOT)
            txt_nrp_saksi_lp_edit.text = personelSaksi?.nrp
            txt_jabatan_saksi_lp_edit.text = personelSaksi?.jabatan
            txt_kesatuan_saksi_lp_edit.text =
                personelSaksi?.satuan_kerja?.kesatuan.toString().toUpperCase(
                    Locale.ROOT
                )
        }
    }


    private fun apiDetailSaksi(saksi: LpSaksiResp?) {
        NetworkConfig().getServLp()
            .getSaksiById("Bearer ${sessionManager1.fetchAuthToken()}", saksi?.id).enqueue(
                object : Callback<LpSaksiResp> {
                    override fun onResponse(
                        call: Call<LpSaksiResp>,
                        response: Response<LpSaksiResp>
                    ) {
                        if (response.isSuccessful) {
                            getViewSaksi(response.body())
                        } else {
                            Toast.makeText(
                                this@EditSaksiLpActivity, R.string.error, Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<LpSaksiResp>, t: Throwable) {
                        Toast.makeText(this@EditSaksiLpActivity, "$t", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    private fun getViewSaksi(saksi: LpSaksiResp?) {

        if (saksi?.status_saksi == "personel") {
            text_edit_saksi.text = "Edit Data Saksi Personel"
            idPersonel = saksi.personel?.id
            jenisSaksi = "personel"
            ll_personel_saksi_lp_edit.visible()
            ll_sipil_saksi_lp_edit.gone()
            /*isKorban*/
            text_is_korban_edit.gone()
            rg_korban_saksi_edit.gone()
            txt_nama_saksi_lp_edit.text = saksi.personel?.nama
            txt_pangkat_saksi_lp_edit.text = saksi.personel?.pangkat.toString().toUpperCase()
            txt_nrp_saksi_lp_edit.text = saksi.personel?.nrp
            txt_jabatan_saksi_lp_edit.text = saksi.personel?.jabatan
            txt_kesatuan_saksi_lp_edit.text = saksi.personel?.satuan_kerja?.kesatuan
        } else {
            text_edit_saksi.text = "Edit Data Saksi Sipil"
            jenisSaksi = "sipil"
            ll_sipil_saksi_lp_edit.visible()
            ll_personel_saksi_lp_edit.gone()
            edt_saksi_edit.setText(saksi?.nama)
            edt_tempat_lahir_saksi_edit.setText(saksi?.tempat_lahir)
            edt_tanggal_lahir_saksi_edit.setText(saksi?.tanggal_lahir)
            edt_pekerjaan_saksi_edit.setText(saksi?.pekerjaan)
            edt_alamat_saksi_edit.setText(saksi?.alamat)
        }

        if (saksi?.is_korban == 1) {
            isKorban = 1
            rb_korban_saksi.isChecked = true
        } else if (saksi?.is_korban == 0) {
            isKorban = 0
            rb_saksi.isChecked = true
        }

        /* rg_korban_saksi_edit.setOnCheckedChangeListener { _, checkedId ->
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
         }*/
    }

    private fun updSaksiSingle(saksi: LpSaksiResp?) {
        Log.e("jenis", "$jenisSaksi")
        if (jenisSaksi == "personel") {
            updSaksiPersonel(saksi)
        } else {
            updSaksiSipil(saksi)
        }
        /* saksi?.id?.let {
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
         }*/
    }


    private fun updSaksiPersonel(saksi: LpSaksiResp?) {
        NetworkConfig().getServLp()
            .updSaksiPersonel("Bearer ${sessionManager1.fetchAuthToken()}", saksi?.id, saksiReq)
            .enqueue(
                object : Callback<Base1Resp<AddSaksiPersonelResp>> {
                    override fun onResponse(
                        call: Call<Base1Resp<AddSaksiPersonelResp>>,
                        response: Response<Base1Resp<AddSaksiPersonelResp>>
                    ) {
                        if (response.body()?.message == "Data saksi personel updated succesfully") {
                            btn_save_edit_saksi_lp.hideProgress(R.string.data_updated)
                            Handler(Looper.getMainLooper()).postDelayed({
                              finish()
                            }, 750)
                        } else {
                            btn_save_edit_saksi_lp.hideProgress(R.string.not_update)
                            toast("${response.body()?.message}")

                        }
                    }

                    override fun onFailure(
                        call: Call<Base1Resp<AddSaksiPersonelResp>>,
                        t: Throwable
                    ) {
                        btn_save_edit_saksi_lp.hideProgress(R.string.not_update)
                        Toast.makeText(this@EditSaksiLpActivity, "$t", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    private fun updSaksiSipil(saksi: LpSaksiResp?) {
        NetworkConfig().getServLp()
            .updSaksiSipil("Bearer ${sessionManager1.fetchAuthToken()}", saksi?.id, saksiReq)
            .enqueue(object : Callback<Base1Resp<AddSaksiSipilResp>> {
                override fun onResponse(
                    call: Call<Base1Resp<AddSaksiSipilResp>>,
                    response: Response<Base1Resp<AddSaksiSipilResp>>
                ) {
                    if (response.body()?.message == "Data saksi sipil updated succesfully") {
                        btn_save_edit_saksi_lp.hideProgress(R.string.data_updated)
                    } else {
                        btn_save_edit_saksi_lp.hideProgress(R.string.not_update)
                        toast("${response.body()?.message}")

                    }
                }

                override fun onFailure(call: Call<Base1Resp<AddSaksiSipilResp>>, t: Throwable) {
                    Toast.makeText(this@EditSaksiLpActivity, "$t", Toast.LENGTH_SHORT).show()
                    btn_save_edit_saksi_lp.hideProgress(R.string.not_update)
                }
            })

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
                    if (response.body()?.message == "Data saksi removed succesfully") {
                        Toast.makeText(
                            this@EditSaksiLpActivity, R.string.data_deleted, Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        toast("${response.body()?.message}")

                    }
                }

            })
        }
    }

    companion object {
        const val REQ_PERSONEL = 123
    }
}