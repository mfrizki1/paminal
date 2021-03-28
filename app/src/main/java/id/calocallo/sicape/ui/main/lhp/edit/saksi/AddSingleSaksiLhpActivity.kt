package id.calocallo.sicape.ui.main.lhp.edit.saksi

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import com.github.razir.progressbutton.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.SaksiLhpReq
import id.calocallo.sicape.network.response.AddSaksiLhpResp
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.LhpMinResp
import id.calocallo.sicape.network.response.PersonelMinResp
import id.calocallo.sicape.ui.main.lhp.add.PickSaksiAddLhpActivity
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.utils.ext.toast
import kotlinx.android.synthetic.main.activity_add_single_saksi_lhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddSingleSaksiLhpActivity : BaseActivity() {
    private lateinit var mAlerDBuilder: MaterialAlertDialogBuilder
    private lateinit var sipilAlertD: View
    private lateinit var sessionManager1: SessionManager1
    private var saksiReqLhp = SaksiLhpReq()
    private var _jenisSaksi: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_single_saksi_lhp)
        sessionManager1 = SessionManager1(this)
        val detailLhp = intent.extras?.getParcelable<LhpMinResp>(ADD_SAKSI_LHP)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Saksi LHP"
        val addSaksi = intent.extras?.getString(PickSaksiAddLhpActivity.ADD_SAKSI)


        rg_korban_saksi_add.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            if (radio.isChecked && radio.text == "Korban") {
                saksiReqLhp.is_korban = 1
            } else {
                saksiReqLhp.is_korban = 0
            }
        }

        btn_add_saksi_lhp_single.attachTextChangeAnimator()
        bindProgressButton(btn_add_saksi_lhp_single)
        btn_add_saksi_lhp_single.setOnClickListener {
            //for add single saksi from edit lhp
            saksiReqLhp.detail_keterangan = edt_ket_saksi.text.toString()
            saksiReqLhp.kesimpulan_keterangan = edt_kesimpulan_ket_saksi.text.toString()
            if (addSaksi == null) {
                Log.e("addSaksi", "$saksiReqLhp")
                //for add on lhp add(all)
                addSaksiLhpSingle(detailLhp)
            } else {
                val intent = Intent()
                intent.putExtra(SET_DATA_SAKSI, saksiReqLhp)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }

        }

        rg_add_saksi.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            if (radio.isChecked && radio.text == "Sipil") {
                _jenisSaksi = "sipil"
                saksiReqLhp.status_saksi = "sipil"
                ll_personel_saksi.gone()
                ll_sipil_saksi.visible()
                saksiReqLhp.id_personel = null

                //sipil view
                mAlerDBuilder = MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
                btn_sipil_add_saksi.setOnClickListener {
                    sipilAlertD = LayoutInflater.from(this)
                        .inflate(R.layout.item_sipil_saksi, null, false)
                    launchSipilView()
                }


            } else {
                _jenisSaksi = "personel"
                saksiReqLhp.status_saksi = "polisi"
                ll_personel_saksi.visible()
                ll_sipil_saksi.gone()
                saksiReqLhp.tempat_lahir = null
                saksiReqLhp.tanggal_lahir = null
                saksiReqLhp.alamat = null
                saksiReqLhp.pekerjaan = null

                //set button for add personel saksi
                btn_choose_personel_saksi.setOnClickListener {
                    val intent = Intent(this, KatPersonelActivity::class.java)
                    intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
                    startActivityForResult(intent, REQ_POLISI)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                }

            }

        }
    }

    private fun launchSipilView() {
        val namaSipilView = sipilAlertD.findViewById<TextInputEditText>(R.id.edt_nama_sipil_saksi)
        val pekerjaanSipilView =
            sipilAlertD.findViewById<TextInputEditText>(R.id.edt_pekerjaan_sipil_saksi)
        val tempatSipilView =
            sipilAlertD.findViewById<TextInputEditText>(R.id.edt_tempat_lahir_sipil_saksi)
        val tanggalSipilView =
            sipilAlertD.findViewById<TextInputEditText>(R.id.edt_tanggal_lahir_sipil_saksi)
        val alamatSipilView =
            sipilAlertD.findViewById<TextInputEditText>(R.id.edt_alamat_sipil_saksi)

        mAlerDBuilder.setView(sipilAlertD)
            .setTitle("Tambah Data Sipil Saksi")
            .setPositiveButton("Tambah") { dialog, _ ->
                saksiReqLhp.alamat = alamatSipilView.text.toString()
                saksiReqLhp.tanggal_lahir = tanggalSipilView.text.toString()
                saksiReqLhp.tempat_lahir = tempatSipilView.text.toString()
                saksiReqLhp.pekerjaan = pekerjaanSipilView.text.toString()
                saksiReqLhp.nama = namaSipilView.text.toString()
                txt_nama_sipil_saksi.text = "Nama : ${saksiReqLhp.nama}"
                txt_alamat_sipil_saksi.text = "Alamat : ${saksiReqLhp.alamat}"
                txt_pekerjaan_sipil_saksi.text = "Pekerjaan : ${saksiReqLhp.pekerjaan}"
                txt_tanggal_lahir_sipil_saksi.text = "Tanggal Lahir : ${saksiReqLhp.tanggal_lahir}"
                txt_tempat_lahir_sipil_saksi.text = "Tempat Lahir : ${saksiReqLhp.tempat_lahir}"
            }
            .setNegativeButton("Batal") { dialog, _ -> }
            .show()
    }

    private fun addSaksiLhpSingle(detailLhp: LhpMinResp?) {

        btn_add_saksi_lhp_single.showProgress {
            progressColor = Color.WHITE
        }
        apiAddSaksiLhp(detailLhp)
//        saksiReqLhp.nama = edt_nama_saksi_lhp_single.text.toString()
//        saksiReqLhp.uraian_saksi = edt_uraian_saksi_lhp_single.text.toString()

    }

    private fun apiAddSaksiLhp(detailLhp: LhpMinResp?) {
        NetworkConfig().getServLhp().addSaksiLhp(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            detailLhp?.id,
            _jenisSaksi,
            saksiReqLhp
        ).enqueue(object : Callback<Base1Resp<AddSaksiLhpResp>> {
            override fun onResponse(
                call: Call<Base1Resp<AddSaksiLhpResp>>,
                response: Response<Base1Resp<AddSaksiLhpResp>>
            ) {
                if (_jenisSaksi == "sipil") {
                    if (response.body()?.message == "Data saksi sipil saved succesfully") {
                        btn_add_saksi_lhp_single.hideProgress(R.string.data_saved)
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        }, 750)
                    } else {
                        toast("${response.body()?.message}")
                        btn_add_saksi_lhp_single.hideProgress(R.string.not_save)
                    }

                } else {
                    if (response.body()?.message == "Data saksi personel saved succesfully") {
                        btn_add_saksi_lhp_single.hideProgress(R.string.data_saved)
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        }, 750)
                    } else {
                        toast("${response.body()?.message}")
                        btn_add_saksi_lhp_single.hideProgress(R.string.not_save)
                    }
                }
            }

            override fun onFailure(call: Call<Base1Resp<AddSaksiLhpResp>>, t: Throwable) {
                toast("$t")
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val personelSaksi = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
        if (resultCode == Activity.RESULT_OK && requestCode == REQ_POLISI) {
            saksiReqLhp.nama = personelSaksi?.nama
            saksiReqLhp.id_personel = personelSaksi?.id
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
        const val ADD_SAKSI_LHP = "ADD_SAKSI_LHP"
        const val REQ_POLISI = 123
        const val SET_DATA_SAKSI = "SET_DATA_SAKSI"
    }
}