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
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.utils.ext.*
import kotlinx.android.synthetic.main.activity_edit_saksi_lhp.*
import kotlinx.android.synthetic.main.activity_edit_saksi_lhp.txt_alamat_sipil_saksi
import kotlinx.android.synthetic.main.activity_edit_saksi_lhp.txt_jabatan_saksi_lp_add
import kotlinx.android.synthetic.main.activity_edit_saksi_lhp.txt_kesatuan_saksi_lp_add
import kotlinx.android.synthetic.main.activity_edit_saksi_lhp.txt_nama_saksi_lp_add
import kotlinx.android.synthetic.main.activity_edit_saksi_lhp.txt_nama_sipil_saksi
import kotlinx.android.synthetic.main.activity_edit_saksi_lhp.txt_nrp_saksi_lp_add
import kotlinx.android.synthetic.main.activity_edit_saksi_lhp.txt_pangkat_saksi_lp_add
import kotlinx.android.synthetic.main.activity_edit_saksi_lhp.txt_pekerjaan_sipil_saksi
import kotlinx.android.synthetic.main.activity_edit_saksi_lhp.txt_tanggal_lahir_sipil_saksi
import kotlinx.android.synthetic.main.activity_edit_saksi_lhp.txt_tempat_lahir_sipil_saksi
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

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
    private var isKorban: Int? = null
    private var statusSaksi: String? = null
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
        apiDetailSaksiLhp(saksi)
//        getViewSaksiLhp(saksi)

        btn_save_saksi_lhp_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_saksi_lhp_edit)
        btn_save_saksi_lhp_edit.setOnClickListener {
            btn_save_saksi_lhp_edit.showProgress {
                progressColor = Color.WHITE
            }

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

        rg_is_korban_edit.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            if (radio.isChecked && radio.text == "Korban") {
                isKorban = 1
            } else {
                isKorban = 0
            }
        }
        rg_saksi_edit.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            if (radio.isChecked && radio.text == "Polisi") {
                statusSaksi = "personel"
                ll_personel_saksi_edit.visible()
                ll_sipil_saksi_edit.gone()
                tempAlamatSipil = null
                tempNamaSipil = null
                tempPekerjananSipil = null
                tempTglLhrSipil = null
                tempTmptLhrSipil = null

            } else {
                statusSaksi = "sipil"
                ll_sipil_saksi_edit.visible()
                ll_personel_saksi_edit.gone()
                idPersonelSaksi = null
            }
        }
    }

    private fun apiDetailSaksiLhp(saksi: SaksiLhpResp?) {
        NetworkConfig().getServLhp()
            .detailSaksiLhp("Bearer ${sessionManager1.fetchAuthToken()}", saksi?.id)
            .enqueue(object :
                Callback<DetailSaksiLhpResp> {
                override fun onResponse(
                    call: Call<DetailSaksiLhpResp>,
                    response: Response<DetailSaksiLhpResp>
                ) {
                    if (response.isSuccessful) {
                        getViewSaksiLhp(response.body())
                    } else {
                        toast("Error")
                    }
                }

                override fun onFailure(call: Call<DetailSaksiLhpResp>, t: Throwable) {
                    toast("$t")
                }
            })
    }

    @SuppressLint("SetTextI18n")
    private fun getViewSaksiLhp(saksi: DetailSaksiLhpResp?) {
        idPersonelSaksi = saksi?.personel?.id
        isKorban = saksi?.is_korban
        statusSaksi = saksi?.status_saksi
        tempAlamatSipil = saksi?.alamat
        tempNamaSipil = saksi?.nama
        tempPekerjananSipil = saksi?.pekerjaan
        tempTglLhrSipil = saksi?.tanggal_lahir
        tempTmptLhrSipil = saksi?.tempat_lahir
        if (saksi?.is_korban == 1) {
            rb_korban_saksi_edit.isChecked = true
        } else {
            rb_not_korban_saksi_edit.isChecked = true
        }

        if (saksi?.status_saksi == "personel") {
            rb_polisi_saksi_edit.isChecked = true
            ll_personel_saksi_edit.visible()
            txt_nama_saksi_lp_add.text = "Nama: ${saksi.personel?.nama}"
            txt_pangkat_saksi_lp_add.text = "Pangkat: ${
                saksi.personel?.pangkat.toString().toUpperCase(Locale.ROOT)
            }"
            txt_nrp_saksi_lp_add.text = "NRP: ${saksi.personel?.nrp}"
            txt_jabatan_saksi_lp_add.text = "Jabatan: ${saksi.personel?.jabatan}"
            txt_kesatuan_saksi_lp_add.text = "Kesatuan: ${saksi.personel?.satuan_kerja?.kesatuan}"
            tempAlamatSipil = null
            tempNamaSipil = null
            tempPekerjananSipil = null
            tempTglLhrSipil = null
            tempTmptLhrSipil = null

        } else {
            rb_sipil_saksi_edit.isChecked = true
            ll_sipil_saksi_edit.visible()
            txt_nama_sipil_saksi.text = "Nama: ${saksi?.nama}"
            txt_tempat_lahir_sipil_saksi.text = "Tempat Lahir: ${saksi?.tempat_lahir}"
            txt_tanggal_lahir_sipil_saksi.text =
                "Tanggal Lahir: ${formatterTanggal(saksi?.tanggal_lahir)}"
            txt_pekerjaan_sipil_saksi.text = "Pekerjaan: ${saksi?.pekerjaan}"
            txt_alamat_sipil_saksi.text = "Alamat: ${saksi?.alamat}"
            idPersonelSaksi = null
        }
        edt_kesimpulan_ket_saksi_edit.setText(saksi?.kesimpulan_keterangan)
        edt_ket_saksi_edit.setText(saksi?.detail_keterangan)

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
                txt_tanggal_lahir_sipil_saksi.text = "Tempat Lahir : ${tempTmptLhrSipil}"
                txt_tempat_lahir_sipil_saksi.text = "Tanggal Lahir : ${tempTglLhrSipil}"
            }
            .setNegativeButton("Batal") { dialog, _ -> }
            .show()
    }

    private fun updateSaksiLhp(saksi: SaksiLhpResp?) {
        saksiLhpReq.is_korban = isKorban
        saksiLhpReq.status_saksi = tempStatusSaksi
        saksiLhpReq.id_personel = idPersonelSaksi
        saksiLhpReq.nama = tempNamaSipil
        saksiLhpReq.tempat_lahir = tempTmptLhrSipil
        saksiLhpReq.tanggal_lahir = tempTglLhrSipil
        saksiLhpReq.pekerjaan = tempPekerjananSipil
        saksiLhpReq.alamat = tempAlamatSipil
        saksiLhpReq.detail_keterangan = edt_ket_saksi_edit.text.toString()
        saksiLhpReq.kesimpulan_keterangan = edt_kesimpulan_ket_saksi_edit.text.toString()
        Log.e("skasi", "$saksiLhpReq")
        NetworkConfig().getServLhp().updSaksiLhp(
            "Bearer ${sessionManager1.fetchAuthToken()}", saksi?.id, statusSaksi, saksiLhpReq
        ).enqueue(object : Callback<Base1Resp<AddSaksiLhpResp>> {
            override fun onResponse(
                call: Call<Base1Resp<AddSaksiLhpResp>>,
                response: Response<Base1Resp<AddSaksiLhpResp>>
            ) {
                if (response.isSuccessful) {
                    if (statusSaksi == "sipil") {
                        if (response.body()?.message == "Data saksi sipil updated succesfully") {
                            btn_save_saksi_lhp_edit.hideProgress(R.string.data_updated)
                        } else {
                            Handler(Looper.getMainLooper()).postDelayed({
                                btn_save_saksi_lhp_edit.hideProgress(R.string.save)
                            }, 850)
                            btn_save_saksi_lhp_edit.hideProgress(R.string.not_save)
                            toast("${response.body()?.message}")
                        }
                    } else {
                        if (response.body()?.message == "Data saksi personel updated succesfully") {
                            btn_save_saksi_lhp_edit.hideProgress(R.string.data_updated)
                        } else {
                            Handler(Looper.getMainLooper()).postDelayed({
                                btn_save_saksi_lhp_edit.hideProgress(R.string.save)
                            }, 850)
                            btn_save_saksi_lhp_edit.hideProgress(R.string.not_save)
                            toast("${response.body()?.message}")
                        }

                    }
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_saksi_lhp_edit.hideProgress(R.string.save)
                    }, 850)
                    btn_save_saksi_lhp_edit.hideProgress(R.string.not_save)
                    toast("${response.body()?.message}")
                }
            }

            override fun onFailure(call: Call<Base1Resp<AddSaksiLhpResp>>, t: Throwable) {
                Handler(Looper.getMainLooper()).postDelayed({
                    btn_save_saksi_lhp_edit.hideProgress(R.string.save)
                }, 850)
                btn_save_saksi_lhp_edit.hideProgress(R.string.not_save)
                toast("$t")
            }
        })
    }

    private fun deleteSaksiLhp(saksi: SaksiLhpResp?) {
        NetworkConfig().getServLhp()
            .delPersTerlapor("Bearer ${sessionManager1.fetchAuthToken()}", saksi?.id)
            .enqueue(object : Callback<BaseResp> {
                override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                    if (response.body()?.message == "Data saksi removed succesfully" || response.body()?.message == "Data personel terlapor removed succesfully") {
                        toast(R.string.data_deleted)
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        }, 1000)
                    } else {
                        toast("${response.body()?.message}")
                    }
                }

                override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                    toast("$t")
                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val personelSaksi = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
        if (resultCode == Activity.RESULT_OK && requestCode == REQ_SAKSI_LHP) {
            idPersonelSaksi = personelSaksi?.id
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
        const val EDIT_SAKSI_LHP = "EDIT_SAKSI_LHP"
        const val REQ_SAKSI_LHP = 212
    }
}