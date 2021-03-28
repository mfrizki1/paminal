package id.calocallo.sicape.ui.main.lp.saksi

import android.annotation.SuppressLint
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
import id.calocallo.sicape.ui.main.lp.disiplin.ListLpDisiplinActivity
import id.calocallo.sicape.ui.main.lp.kke.ListLpKodeEtikActivity
import id.calocallo.sicape.ui.main.lp.pasal.PickPasalActivity
import id.calocallo.sicape.ui.main.lp.pidana.ListLpPidanaActivity
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.utils.ext.*
import kotlinx.android.synthetic.main.activity_add_saksi_lp.*
import kotlinx.android.synthetic.main.activity_add_saksi_lp.txt_jabatan_saksi_lp_add
import kotlinx.android.synthetic.main.activity_add_saksi_lp.txt_kesatuan_saksi_lp_add
import kotlinx.android.synthetic.main.activity_add_saksi_lp.txt_nama_saksi_lp_add
import kotlinx.android.synthetic.main.activity_add_saksi_lp.txt_nrp_saksi_lp_add
import kotlinx.android.synthetic.main.activity_add_saksi_lp.txt_pangkat_saksi_lp_add
import kotlinx.android.synthetic.main.activity_add_single_saksi_lhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddSaksiLpActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var saksiLpReq = SaksiLpReq()
    private var isSingleAdd: Boolean = false
    private var jenisSaksi: String? = null
    private var dataLp: LpMinResp? = null
    private var dataLpFull: LpResp? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_saksi_lp)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Saksi"
        sessionManager1 = SessionManager1(this)
        isSingleAdd = intent.getBooleanExtra("ADD_SINGLE_SAKSI", false)

        rg_jenis_saksi_lp_add.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            if (radio.isChecked && radio.text == "Polisi") {
                jenisSaksi = "personel"
                ll_personel_saksi_lp.visible()
                ll_sipil_saksi_lp.gone()
                /*  saksiLpReq.nama == null
                  saksiLpReq.tempat_lahir == null
                  saksiLpReq.tanggal_lahir == null
                  saksiLpReq.pekerjaan == null
                  saksiLpReq.alamat == null
                  saksiLpReq.is_korban == null*/
            } else {
                jenisSaksi = "sipil"
                ll_saksi_korban_saksi_add.visible()
                ll_sipil_saksi_lp.visible()
                ll_personel_saksi_lp.gone()

            }
        }


        btn_save_add_saksi.attachTextChangeAnimator()
        bindProgressButton(btn_save_add_saksi)
        btn_save_add_saksi.setOnClickListener {
            btn_save_add_saksi.showProgress {
                progressColor = Color.WHITE
            }
            if (isSingleAdd) {
                dataLp = intent.getParcelableExtra<LpMinResp>("DATA_LP")
                addSaksiLp(dataLp?.id)
            } else {
                dataLpFull = intent.getParcelableExtra<LpResp>(PickPasalActivity.DATA_LP)
                addSaksiLp(dataLpFull?.id)

            }
        }

        btn_choose_personel_saksi_lp_add.setOnClickListener {
            val intent = Intent(this, KatPersonelActivity::class.java)
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, REQ_PERSONEL)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

    }

    private fun addSaksiLp(idLp: Int?) {
        saksiLpReq.nama = edt_nama_saksi_single.text.toString()
        saksiLpReq.tempat_lahir = edt_tempat_lahir_saksi_single.text.toString()
        saksiLpReq.tanggal_lahir = edt_tanggal_lahir_saksi_single.text.toString()
        saksiLpReq.pekerjaan = edt_pekerjaan_saksi_single.text.toString()
        saksiLpReq.alamat = edt_alamat_saksi_single.text.toString()
        saksiLpReq.kesimpulan_keterangan = edt_kesimpulan_ket_saksi_add_single_lp.text.toString()
        val id: Int = rg_status_saksi_lp_add.checkedRadioButtonId
        if (id != -1) {
            val radio: RadioButton = findViewById(id)
            if (radio.text == "Korban") {
                saksiLpReq.is_korban = 1
            } else {
                saksiLpReq.is_korban = 0

            }
        }

        Log.e("dataSaksiReq", "$saksiLpReq, $idLp")

        apiAddSingleSaksi(idLp)
    }

    private fun apiAddSingleSaksi(idLp: Int?) {
        if (jenisSaksi == "sipil") {
            NetworkConfig().getServLp()
                .addSaksiSipil("Bearer ${sessionManager1.fetchAuthToken()}", idLp, saksiLpReq)
                .enqueue(
                    object : Callback<Base1Resp<AddSaksiSipilResp>> {
                        override fun onResponse(
                            call: Call<Base1Resp<AddSaksiSipilResp>>,
                            response: Response<Base1Resp<AddSaksiSipilResp>>
                        ) {
                            if (response.body()?.message == "Data saksi sipil saved succesfully") {
                                if (isSingleAdd) {
                                    btn_save_add_saksi.hideProgress(R.string.data_saved)
                                    Handler(Looper.getMainLooper()).postDelayed({
                                        finish()
                                    }, 750)
                                } else {
                                    gotoListSaksi(response.body()?.data?.saksi?.id_lp)
                                }
                            } else {
                                toast("${response.body()?.message}")
                                btn_save_add_saksi.hideProgress(R.string.not_save)
                            }
                        }

                        override fun onFailure(
                            call: Call<Base1Resp<AddSaksiSipilResp>>, t: Throwable
                        ) {
                            Toast.makeText(this@AddSaksiLpActivity, "$t", Toast.LENGTH_SHORT).show()
                            btn_save_add_saksi.hideProgress(R.string.not_save)
                        }
                    })
        } else {
            NetworkConfig().getServLp()
                .addSaksiPersonel("Bearer ${sessionManager1.fetchAuthToken()}", idLp, saksiLpReq)
                .enqueue(
                    object : Callback<Base1Resp<AddSaksiPersonelResp>> {
                        override fun onResponse(
                            call: Call<Base1Resp<AddSaksiPersonelResp>>,
                            response: Response<Base1Resp<AddSaksiPersonelResp>>
                        ) {
                            if (response.body()?.message == "Data saksi personel saved succesfully") {
                                if (isSingleAdd) {
                                    btn_save_add_saksi.hideProgress(R.string.data_saved)
                                    Handler(Looper.getMainLooper()).postDelayed({
                                        finish()
                                    }, 750)
                                } else {
                                    gotoListSaksi(response.body()?.data?.saksi?.id_lp)
                                }
                            } else {
                                toast("${response.body()?.message}")
                                btn_save_add_saksi.hideProgress(R.string.not_save)

                            }
                        }

                        override fun onFailure(
                            call: Call<Base1Resp<AddSaksiPersonelResp>>,
                            t: Throwable
                        ) {
                            btn_save_add_saksi.hideProgress(R.string.not_save)
                            Toast.makeText(this@AddSaksiLpActivity, "$t", Toast.LENGTH_SHORT).show()
                        }
                    })

        }
    }

    private fun gotoListSaksi(idLp: Int?) {
        btn_save_add_saksi.hideProgress(R.string.data_saved)
        btn_save_add_saksi.showSnackbar(R.string.data_saved) {
            action(R.string.next) {
                when (dataLpFull?.jenis_pelanggaran) {
                    "pidana" -> {
                        val intent = Intent(
                            this@AddSaksiLpActivity,
                            ListLpPidanaActivity::class.java
                        ).apply {
                            this.putExtra(ID_LP, idLp)
                            this.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        }
                        startActivity(intent)
                        finish()
                    }
                    "kode_etik" -> {
                        val intent = Intent(
                            this@AddSaksiLpActivity,
                            ListLpKodeEtikActivity::class.java
                        ).apply {
                            this.putExtra(ID_LP, idLp)
                            this.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        }
                        startActivity(intent)
                        finish()
                    }
                    "disiplin" -> {
                        val intent = Intent(
                            this@AddSaksiLpActivity,
                            ListLpDisiplinActivity::class.java
                        ).apply {
                            this.putExtra(ID_LP, idLp)
                            this.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        }
                        startActivity(intent)
                        finish()
                    }
                    else -> {
                        val intent = Intent(
                            this@AddSaksiLpActivity,
                            ListSaksiLpActivity::class.java
                        ).apply {
                            this.putExtra(ID_LP, idLp)
                            this.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        }
                        startActivity(intent)
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQ_PERSONEL) {
            val personelSaksi = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
            saksiLpReq.nama = personelSaksi?.nama
            saksiLpReq.id_personel = personelSaksi?.id
            txt_nama_saksi_lp_add.text = "Nama : ${personelSaksi?.nama}"
            txt_pangkat_saksi_lp_add.text =
                "Pangkat : ${personelSaksi?.pangkat.toString().toUpperCase()}"
            txt_nrp_saksi_lp_add.text = "NRP : ${personelSaksi?.nrp}"
            txt_jabatan_saksi_lp_add.text = "Jabatan : ${personelSaksi?.jabatan}"
            txt_kesatuan_saksi_lp_add.text =
                "Kesatuan : ${personelSaksi?.satuan_kerja?.kesatuan.toString().toUpperCase()}"
        }
    }

    companion object {
        const val REQ_PERSONEL = 123
        const val ID_LP = "ID_LP"
    }
}