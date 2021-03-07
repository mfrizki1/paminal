package id.calocallo.sicape.ui.main.lp.disiplin

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.EditLpDisiplinReq
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_lp_disiplin.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditLpDisiplinActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var editLpDisiplinReq = EditLpDisiplinReq()
    private var changedIdPelapor: Int? = null
    private var changedIdTerlapor: Int? = null
    private var namaSatker: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_lp_disiplin)
        setupActionBarWithBackButton(toolbar)
        sessionManager1 = SessionManager1(this)
        supportActionBar?.title = "Edit Data Laporan Polisi Disiplin"
        val disiplin = intent.extras?.getParcelable<LpPidanaResp>(EDIT_DISIPLIN)
        apiEditDisiplinView(disiplin)

        //set terlapor
        btn_choose_personel_terlapor_disiplin_edit.setOnClickListener {
            val intent = Intent(this, KatPersonelActivity::class.java)
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, REQ_TERLAPOR)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        //set pelapor
        btn_choose_personel_pelapor_lp_edit_disiplin.setOnClickListener {
            val intent = Intent(this, KatPersonelActivity::class.java)
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, REQ_PELAPOR)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        //update
        btn_save_edit_lp_disiplin.attachTextChangeAnimator()
        bindProgressButton(btn_save_edit_lp_disiplin)
        btn_save_edit_lp_disiplin.setOnClickListener {
            updateLpDisiplin(disiplin, changedIdTerlapor, changedIdPelapor)
        }

        /* spinner_kesatuan_pimpinan_bidang_edit_disiplin.setAdapter(
             ArrayAdapter(
                 this,
                 R.layout.item_spinner,
                 resources.getStringArray(R.array.satker)
             )
         )
         spinner_kesatuan_pimpinan_bidang_edit_disiplin.setOnItemClickListener { parent, view, position, id ->
             namaSatker = parent.getItemAtPosition(position) as String?
         }*/
    }

    private fun apiEditDisiplinView(disiplin: LpPidanaResp?) {
        NetworkConfig().getServLp()
            .getLpById("Bearer ${sessionManager1.fetchAuthToken()}", disiplin?.id).enqueue(object :
                Callback<LpPidanaResp> {
                override fun onFailure(call: Call<LpPidanaResp>, t: Throwable) {
                    Toast.makeText(
                        this@EditLpDisiplinActivity, R.string.error_conn, Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResponse(
                    call: Call<LpPidanaResp>,
                    response: Response<LpPidanaResp>
                ) {
                    if (response.isSuccessful) {
                        getViewEditDisipilin(response.body())
                    } else {
                        Toast.makeText(
                            this@EditLpDisiplinActivity, R.string.error_conn, Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
    }

    @SuppressLint("SetTextI18n")
    private fun getViewEditDisipilin(disiplin: LpPidanaResp?) {
        edt_pukul_buat_edit_lp_disiplin.setText(disiplin?.detail_laporan?.waktu_buat_laporan)
        edt_no_lp_disiplin_edit.setText(disiplin?.no_lp)
        edt_macam_pelanggaran_disiplin_edit.setText(disiplin?.detail_laporan?.macam_pelanggaran)

        txt_nama_terlapor_lp_disiplin_edit.text = "Nama : ${disiplin?.personel_terlapor?.nama}"
        txt_pangkat_terlapor_lp_disiplin_edit.text =
            "Pangkat : ${disiplin?.personel_terlapor?.pangkat.toString().toUpperCase()}"
        txt_nrp_terlapor_lp_disiplin_edit.text = "NRP : ${disiplin?.personel_terlapor?.nrp}"
        txt_jabatan_terlapor_lp_disiplin_edit.text =
            "Jabatan : ${disiplin?.personel_terlapor?.jabatan}"
        txt_kesatuan_terlapor_lp_disiplin_edit.text =
            "Kesatuan : ${disiplin?.personel_terlapor?.satuan_kerja?.kesatuan.toString()
                .toUpperCase()}"
        edt_ket_pelapor_disiplin_edit.setText(disiplin?.detail_laporan?.keterangan_pelapor)

        txt_nama_pelapor_disiplin_lp_add.text =
            "Nama : ${disiplin?.detail_laporan?.personel_pelapor?.nama}"
        txt_pangkat_pelapor_disiplin_lp_add.text =
            "Pangkat : ${disiplin?.detail_laporan?.personel_pelapor?.pangkat.toString()
                .toUpperCase()}"
        txt_nrp_pelapor_disiplin_lp_add.text =
            "NRP : ${disiplin?.detail_laporan?.personel_pelapor?.nrp}"
        txt_jabatan_pelapor_disiplin_lp_add.text =
            "Jabatan : ${disiplin?.detail_laporan?.personel_pelapor?.jabatan}"
        txt_kesatuan_pelapor_disiplin_lp_add.text =
            "Kesatuan : ${disiplin?.detail_laporan?.personel_pelapor?.satuan_kerja?.kesatuan.toString()
                .toUpperCase()}"

        edt_kronologis_pelapor_disiplin_edit.setText(disiplin?.detail_laporan?.kronologis_dari_pelapor)
        edt_rincian_pelanggaran_disiplin.setText(disiplin?.detail_laporan?.rincian_pelanggaran_disiplin)

        edt_kota_buat_edit_lp_disiplin.setText(disiplin?.kota_buat_laporan)
        edt_tgl_buat_edit_lp_disiplin.setText(disiplin?.tanggal_buat_laporan)
        edt_nama_pimpinan_bidang_edit_disiplin.setText(disiplin?.detail_laporan?.nama_yang_mengetahui)
        edt_pangkat_pimpinan_bidang_edit_disiplin.setText(
            disiplin?.detail_laporan?.pangkat_yang_mengetahui.toString().toUpperCase()
        )
        edt_nrp_pimpinan_bidang_edit_disiplin.setText(disiplin?.detail_laporan?.nrp_yang_mengetahui)
        edt_jabatan_pimpinan_bidang_edit_disiplin.setText(disiplin?.detail_laporan?.jabatan_yang_mengetahui)
        edt_uraian_pelanggaran_disiplin_edit.setText(disiplin?.uraian_pelanggaran)

//        spinner_kesatuan_pimpinan_bidang_edit_disiplin.setText(disiplin?.kesatuan_yang_mengetahui.toString().toUpperCase())
//        namaSatker = disiplin?.kesatuan_yang_mengetahui

    }

    private fun updateLpDisiplin(
        disiplin: LpPidanaResp?, changedIdTerlapor: Int?, changedIdPelapor: Int?
    ) {
        editLpDisiplinReq.id_satuan_kerja = 123
        editLpDisiplinReq.waktu_buat_laporan = edt_pukul_buat_edit_lp_disiplin.text.toString()
        editLpDisiplinReq.no_lp = edt_no_lp_disiplin_edit.text.toString()
        editLpDisiplinReq.macam_pelanggaran = edt_macam_pelanggaran_disiplin_edit.text.toString()
        editLpDisiplinReq.keterangan_pelapor = edt_ket_pelapor_disiplin_edit.text.toString()
        editLpDisiplinReq.kronologis_dari_pelapor =
            edt_kronologis_pelapor_disiplin_edit.text.toString()
        editLpDisiplinReq.rincian_pelanggaran_disiplin =
            edt_rincian_pelanggaran_disiplin.text.toString()

        editLpDisiplinReq.kota_buat_laporan = edt_kota_buat_edit_lp_disiplin.text.toString()
        editLpDisiplinReq.tanggal_buat_laporan = edt_tgl_buat_edit_lp_disiplin.text.toString()
        editLpDisiplinReq.nama_yang_mengetahui =
            edt_nama_pimpinan_bidang_edit_disiplin.text.toString()
        editLpDisiplinReq.pangkat_yang_mengetahui =
            edt_pangkat_pimpinan_bidang_edit_disiplin.text.toString()
        editLpDisiplinReq.nrp_yang_mengetahui =
            edt_nrp_pimpinan_bidang_edit_disiplin.text.toString()
        editLpDisiplinReq.jabatan_yang_mengetahui =
            edt_jabatan_pimpinan_bidang_edit_disiplin.text.toString()
        editLpDisiplinReq.uraian_pelanggaran = edt_uraian_pelanggaran_disiplin_edit.text.toString()
        editLpDisiplinReq.id_personel_operator = sessionManager1.fetchUser()?.id
//        editLpDisiplinReq.id_personel_pelapor = disiplin?.personel_pelapor?.id
        editLpDisiplinReq.id_personel_terlapor = disiplin?.personel_terlapor?.id
//        editLpDisiplinReq.kesatuan_yang_mengetahui = namaSatker
//        lpDisiplinReq.id_personel_pelapor = this.changedIdPelapor

        if (changedIdPelapor == null) {
            editLpDisiplinReq.id_personel_pelapor = disiplin?.detail_laporan?.personel_pelapor?.id
        } else {
            editLpDisiplinReq.id_personel_pelapor = changedIdPelapor
        }
        if (changedIdTerlapor == null) {
            editLpDisiplinReq.id_personel_terlapor = disiplin?.personel_terlapor?.id
        } else {
            editLpDisiplinReq.id_personel_terlapor = changedIdTerlapor
        }

//        Log.e("is same", "${lpDisiplinReq.id_personel_pelapor == disiplin?.personel_pelapor}")
//        if(lpDisiplinReq.id_personel_pelapor != disiplin?.id_personel_pelapor) {
//            lpDisiplinReq.id_personel_pelapor = this.changedIdPelapor
//        }
        Log.e("edit Disiplin", "$editLpDisiplinReq")
        apiUpdDisiplin(disiplin)
    }

    private fun apiUpdDisiplin(disiplin: LpPidanaResp?) {
        NetworkConfig().getServLp().updLpDisiplin(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            disiplin?.id,
            editLpDisiplinReq
        ).enqueue(object : Callback<Base1Resp<DokLpResp>> {
            override fun onFailure(call: Call<Base1Resp<DokLpResp>>, t: Throwable) {
                Toast.makeText(this@EditLpDisiplinActivity, R.string.error_conn, Toast.LENGTH_SHORT)
                    .show()
                btn_save_edit_lp_disiplin.hideDrawable(R.string.not_update)
            }

            override fun onResponse(
                call: Call<Base1Resp<DokLpResp>>,
                response: Response<Base1Resp<DokLpResp>>
            ) {
                if (response.body()?.message == "Data lp updated succesfully") {
                    val animated = ContextCompat.getDrawable(
                        this@EditLpDisiplinActivity,
                        R.drawable.animated_check
                    )!!
                    val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
                    animated.setBounds(0, 0, size, size)
                    btn_save_edit_lp_disiplin.showProgress {
                        progressColor = Color.WHITE
                    }
                    btn_save_edit_lp_disiplin.showDrawable(animated) {
                        textMarginRes = R.dimen.space_10dp
                        buttonTextRes = R.string.data_updated
                    }
//                    startActivity(Intent(this@EditLpDisiplinActivity, DetailLpDisiplinActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(
                        this@EditLpDisiplinActivity,
                        R.string.error_conn,
                        Toast.LENGTH_SHORT
                    ).show()
                    btn_save_edit_lp_disiplin.hideDrawable(R.string.not_update)
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val personel = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
        when (resultCode) {
            Activity.RESULT_OK -> {
                when (requestCode) {
                    REQ_PELAPOR -> {
                        changedIdPelapor = personel?.id
                        txt_nama_pelapor_disiplin_lp_add.text = " Nama : ${personel?.nama}"
                        txt_pangkat_pelapor_disiplin_lp_add.text =
                            " Pangkat : ${personel?.pangkat.toString().toUpperCase()}"
                        txt_nrp_pelapor_disiplin_lp_add.text = " NRP : ${personel?.nrp}"
                        txt_jabatan_pelapor_disiplin_lp_add.text = " Jabatan : ${personel?.jabatan}"
                        txt_kesatuan_pelapor_disiplin_lp_add.text =
                            " Kesatuan : ${personel?.satuan_kerja?.kesatuan.toString()
                                .toUpperCase()}"
                        Log.e("id pelapor", "$changedIdPelapor")

                    }
                    REQ_TERLAPOR -> {
                        changedIdTerlapor = personel?.id
                        txt_nama_terlapor_lp_disiplin_edit.text = "Nama : ${personel?.nama}"
                        txt_pangkat_terlapor_lp_disiplin_edit.text =
                            "Pangkat : ${personel?.pangkat.toString().toUpperCase()}"
                        txt_nrp_terlapor_lp_disiplin_edit.text = "NRP : ${personel?.nrp}"
                        txt_jabatan_terlapor_lp_disiplin_edit.text =
                            "Jabatan : ${personel?.jabatan}"
                        txt_kesatuan_terlapor_lp_disiplin_edit.text =
                            "Kesatuan : ${personel?.satuan_kerja?.kesatuan.toString()
                                .toUpperCase()}"
                        Log.e("id terlapor", "$changedIdTerlapor")

                    }
                }
            }
        }
    }

    companion object {
        const val EDIT_DISIPLIN = "EDIT_DISIPLIN"
        const val REQ_PELAPOR = 202
        const val REQ_TERLAPOR = 101
    }
}