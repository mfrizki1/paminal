package id.calocallo.sicape.ui.main.lhp.edit.saksi

import android.annotation.SuppressLint
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
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AllPersonelModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.SaksiLhpReq
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_saksi_lhp.*
import kotlinx.android.synthetic.main.activity_edit_saksi_lhp.txt_alamat_sipil_saksi
import kotlinx.android.synthetic.main.activity_edit_saksi_lhp.txt_jabatan_saksi_edit
import kotlinx.android.synthetic.main.activity_edit_saksi_lhp.txt_kesatuan_saksi_edit
import kotlinx.android.synthetic.main.activity_edit_saksi_lhp.txt_nama_saksi_edit
import kotlinx.android.synthetic.main.activity_edit_saksi_lhp.txt_nama_sipil_saksi
import kotlinx.android.synthetic.main.activity_edit_saksi_lhp.txt_nrp_saksi_edit
import kotlinx.android.synthetic.main.activity_edit_saksi_lhp.txt_pangkat_saksi_edit
import kotlinx.android.synthetic.main.activity_edit_saksi_lhp.txt_pekerjaan_sipil_saksi
import kotlinx.android.synthetic.main.activity_edit_saksi_lhp.txt_tanggal_lahir_sipil_saksi
import kotlinx.android.synthetic.main.activity_edit_saksi_lhp.txt_tempat_lahir_sipil_saksi
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditSaksiLhpActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private lateinit var mAlerDBuilder: MaterialAlertDialogBuilder
    private lateinit var sipilAlertD: View
    private var saksiLhpReq = SaksiLhpReq()
    private var idPersonelSaksi: Int? = null
    private var tempStatusSaksi: String? = null
    private var tempNamaSipil: String? = null
    private var tempPekerjananSipil: String? = null
    private var tempTmptLhrSipil: String? = null
    private var tempTglLhrSipil: String? = null
    private var tempAlamatSipil: String? = null
    private var tempIsKorban: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_saksi_lhp)
        sessionManager1 = SessionManager1(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Saksi LHP"

        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_save_saksi_lhp_edit.gone()
            btn_delete_saksi_lhp_edit.gone()
        }

        val saksi = intent.extras?.getParcelable<SaksiLhpResp>(EDIT_SAKSI_LHP)
        Log.e("model", "$saksi")
        getViewSaksiLhp(saksi)

        btn_save_saksi_lhp_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_saksi_lhp_edit)
        btn_save_saksi_lhp_edit.setOnClickListener {
            btn_save_saksi_lhp_edit.showProgress {
                progressColor = Color.WHITE
            }
            saksiLhpReq.status_saksi = tempStatusSaksi
            saksiLhpReq.id_personel = idPersonelSaksi
            saksiLhpReq.nama = tempNamaSipil
            saksiLhpReq.tempat_lahir = tempTmptLhrSipil
            saksiLhpReq.tanggal_lahir = tempTglLhrSipil
            saksiLhpReq.pekerjaan = tempPekerjananSipil
            saksiLhpReq.alamat = tempAlamatSipil
            saksiLhpReq.isi_keterangan_saksi = edt_ket_saksi_edit.text.toString()
            saksiLhpReq.is_korban =tempIsKorban
            updateSaksiLhp(saksi)
        }

        btn_delete_saksi_lhp_edit.setOnClickListener {
            alert("Yakin Hapus Data?") {
                positiveButton("Iya") {
                    deleteSaksiLhp(saksi)
                }
                negativeButton("Tidak") {}
            }.show()
        }

        btn_choose_personel_saksi_edit.setOnClickListener {
            val intent = Intent(this, KatPersonelActivity::class.java)
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, REQ_SAKSI_LHP)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        mAlerDBuilder = MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
        btn_sipil_saksi_edit.setOnClickListener {
            sipilAlertD = LayoutInflater.from(this)
                .inflate(R.layout.item_sipil_saksi, null, false)
            launchSipilView(saksi)
        }
    }

    private fun launchSipilView(saksi: SaksiLhpResp?) {
        val namaSipilView = sipilAlertD.findViewById<TextInputEditText>(R.id.edt_nama_sipil_saksi)
        val pekerjaanSipilView =
            sipilAlertD.findViewById<TextInputEditText>(R.id.edt_pekerjaan_sipil_saksi)
        val tempatSipilView =
            sipilAlertD.findViewById<TextInputEditText>(R.id.edt_tempat_lahir_sipil_saksi)
        val tanggalSipilView =
            sipilAlertD.findViewById<TextInputEditText>(R.id.edt_tanggal_lahir_sipil_saksi)
        val alamatSipilView =
            sipilAlertD.findViewById<TextInputEditText>(R.id.edt_alamat_sipil_saksi)
        namaSipilView.setText(saksi?.nama)
        pekerjaanSipilView.setText(saksi?.pekerjaan)
        tempatSipilView.setText(saksi?.tempat_lahir)
        tanggalSipilView.setText(saksi?.tanggal_lahir)
        alamatSipilView.setText(saksi?.alamat)
        mAlerDBuilder.setView(sipilAlertD)
            .setTitle("Tambah Data Sipil Saksi")
            .setPositiveButton("Tambah") { dialog, _ ->
                tempAlamatSipil = alamatSipilView.text.toString()
                tempTmptLhrSipil = tempatSipilView.text.toString()
                tempTglLhrSipil = tanggalSipilView.text.toString()
                tempPekerjananSipil = pekerjaanSipilView.text.toString()
                tempNamaSipil = namaSipilView.text.toString()
                txt_nama_sipil_saksi.text = "Nama : ${tempNamaSipil}"
                txt_alamat_sipil_saksi.text = "Alamat : ${tempAlamatSipil}"
                txt_pekerjaan_sipil_saksi.text = "Pekerjaan : ${tempPekerjananSipil}"
                txt_tanggal_lahir_sipil_saksi.text = "Tanggal Lahir : ${tempTglLhrSipil}"
                txt_tempat_lahir_sipil_saksi.text = "Tempat Lahir : ${tempTmptLhrSipil}"
            }
            .setNegativeButton("Batal") { dialog, _ -> }
            .show()
    }

    @SuppressLint("SetTextI18n")
    private fun getViewSaksiLhp(saksi: SaksiLhpResp?) {
//        idPersonelSaksi = saksi?.personel?.id
        Log.e("idPersonelSaksi", "$idPersonelSaksi")
        if (saksi?.personel != null) {
            rb_polisi_saksi_edit.isChecked = true
            ll_sipil_saksi_edit.gone()
            ll_personel_saksi_edit.visible()
            txt_nama_saksi_edit.text = "Nama : ${saksi.personel?.nama}"
            txt_pangkat_saksi_edit.text =
                "Pangkat : ${saksi.personel?.pangkat.toString().toUpperCase()}"
            txt_nrp_saksi_edit.text = "NRP : ${saksi.personel?.nrp}"
            txt_jabatan_saksi_edit.text = "Jabatan : ${saksi.personel?.jabatan}"
            txt_kesatuan_saksi_edit.text =
                "Kesatuan : ${saksi.personel?.satuan_kerja?.kesatuan.toString().toUpperCase()}"
            tempStatusSaksi = "personel"
            idPersonelSaksi = saksi.personel?.id

        } else {
            rb_sipil_saksi_edit.isChecked = true
            ll_sipil_saksi_edit.visible()
            ll_personel_saksi_edit.gone()
            txt_nama_sipil_saksi.text = saksi?.nama
            txt_tempat_lahir_sipil_saksi.text = saksi?.tempat_lahir
            txt_tanggal_lahir_sipil_saksi.text = saksi?.tanggal_lahir
            txt_pekerjaan_sipil_saksi.text = saksi?.pekerjaan
            txt_alamat_sipil_saksi.text = saksi?.alamat
            tempNamaSipil = saksi?.nama
            tempTmptLhrSipil = saksi?.tempat_lahir
            tempTglLhrSipil = saksi?.tanggal_lahir
            tempPekerjananSipil = saksi?.pekerjaan
            tempAlamatSipil = saksi?.alamat
            tempStatusSaksi = "sipil"
            idPersonelSaksi = null
        }

        edt_ket_saksi_edit.setText(saksi?.isi_keterangan_saksi)
        tempIsKorban = saksi?.is_korban
        /*set for radio group and other stuff*/
        rg_saksi_edit.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            if (radio.isChecked && radio.text == "Sipil") {
                ll_sipil_saksi_edit.visible()
                ll_personel_saksi_edit.gone()
                tempStatusSaksi = "sipil"
                idPersonelSaksi = null

            } else {
                tempStatusSaksi = "personel"
                ll_sipil_saksi_edit.gone()
                ll_personel_saksi_edit.visible()
                tempNamaSipil = null
                tempPekerjananSipil = null
                tempTmptLhrSipil = null
                tempTglLhrSipil = null
                tempAlamatSipil = null
            }
        }

        if (saksi?.is_korban == 1) {
            rb_korban_saksi_edit.isChecked = true
        } else {
            rb_not_korban_saksi_edit.isChecked = true
        }
        rg_is_korban_edit.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            if (radio.isChecked && radio.text == "Korban") {
                tempIsKorban = 1
            } else {
                tempIsKorban = 0
            }
        }

    }

    private fun updateSaksiLhp(saksi: SaksiLhpResp?) {
      NetworkConfig().getServLhp().updSaksiLhp("Bearer ${sessionManager1.fetchAuthToken()}", saksi?.id, saksiLhpReq).enqueue(
          object : Callback<Base1Resp<AddSaksiLhpResp>> {
              override fun onResponse(
                  call: Call<Base1Resp<AddSaksiLhpResp>>,
                  response: Response<Base1Resp<AddSaksiLhpResp>>
              ) {
                  if (response.body()?.message == "Data saksi updated succesfully") {
                      btn_save_saksi_lhp_edit.hideProgress(R.string.data_updated)
                      Handler(Looper.getMainLooper()).postDelayed({
                          finish()
                      },750)

                  } else {
                      btn_save_saksi_lhp_edit.hideProgress(R.string.not_update)

                  }
              }

              override fun onFailure(call: Call<Base1Resp<AddSaksiLhpResp>>, t: Throwable) {
                  Toast.makeText(this@EditSaksiLhpActivity, "$t", Toast.LENGTH_SHORT).show()
                    btn_save_saksi_lhp_edit.hideProgress(R.string.not_update)
              }
          })

        Log.e("edit Saksi", "$saksiLhpReq")
    }

    private fun deleteSaksiLhp(saksi: SaksiLhpResp?) {
        NetworkConfig().getServLhp()
            .delSaksiLhp("Bearer ${sessionManager1.fetchAuthToken()}", saksi?.id).enqueue(
                object :
                    Callback<BaseResp> {
                    override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                        Toast.makeText(
                            this@EditSaksiLhpActivity, R.string.data_deleted, Toast.LENGTH_SHORT
                        ).show()
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        }, 1000)
                    }

                    override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                        Toast.makeText(this@EditSaksiLhpActivity, "$t", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val personelSaksi = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
        if (resultCode == Activity.RESULT_OK && requestCode == REQ_SAKSI_LHP) {
            idPersonelSaksi = personelSaksi?.id
            txt_nama_saksi_edit.text = "Nama : ${personelSaksi?.nama}"
            txt_pangkat_saksi_edit.text =
                "Pangkat : ${personelSaksi?.pangkat.toString().toUpperCase()}"
            txt_nrp_saksi_edit.text = "NRP : ${personelSaksi?.nrp}"
            txt_jabatan_saksi_edit.text = "Jabatan : ${personelSaksi?.jabatan}"
            txt_kesatuan_saksi_edit.text =
                "Kesatuan : ${personelSaksi?.satuan_kerja?.kesatuan.toString().toUpperCase()}"
        }
    }

    companion object {
        const val EDIT_SAKSI_LHP = "EDIT_SAKSI_LHP"
        const val REQ_SAKSI_LHP = 212
    }
}