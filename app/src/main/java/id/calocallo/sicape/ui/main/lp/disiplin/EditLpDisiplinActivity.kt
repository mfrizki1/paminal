package id.calocallo.sicape.ui.main.lp.disiplin

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
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.LpDisiplinReq
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.ui.main.choose.ChoosePersonelActivity
import id.calocallo.sicape.ui.main.choose.lhp.ChooseLhpActivity
import id.calocallo.sicape.ui.main.lp.AddLpActivity
import id.calocallo.sicape.ui.main.lp.kke.EditLpKkeActivity
import id.calocallo.sicape.utils.ext.formatterDiffTanggal
import id.calocallo.sicape.utils.ext.formatterTanggal
import id.calocallo.sicape.utils.ext.reverseTanggal
import id.calocallo.sicape.utils.ext.visible
import kotlinx.android.synthetic.main.activity_detail_lp_kke.*
import kotlinx.android.synthetic.main.activity_edit_lp_disiplin.*
import kotlinx.android.synthetic.main.activity_edit_lp_kke.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditLpDisiplinActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var editLpDisiplinReq = LpDisiplinReq()
    private var changedIdPelapor: Int? = null
    private var changedIdTerlapor: Int? = null
    private var namaSatker: String? = null
    private var _idLhp: Int? = null
    private var namaSipil: String? = null
    private var jkSipil: String? = null
    private var agamaSipil: String? = null
    private var noTelpSipil: String? = null
    private var nikSipil: String? = null
    private var alamatSipil: String? = null
    private var kwgSipil: String? = null
    private var pekerjaanSipil: String? = null
    private var tmptSipil: String? = null
    private var tglSipil: String? = null
    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder
    private lateinit var sipilAlertDialog: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_lp_disiplin)
        setupActionBarWithBackButton(toolbar)
        sessionManager1 = SessionManager1(this)
        supportActionBar?.title = "Edit Data Laporan Polisi Disiplin"
        val disiplin = intent.extras?.getParcelable<LpResp>(EDIT_DISIPLIN)
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
            updateLpDisiplin(disiplin)
        }

        materialAlertDialogBuilder =
            MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
        btn_sipil_edit_disiplin.setOnClickListener {
            sipilAlertDialog = LayoutInflater.from(this)
                .inflate(R.layout.item_sipil_pidana, null, false)
            launchSipilView(disiplin)
        }
    }

    private fun launchSipilView(disiplin: LpResp?) {

        val spAgama =
            sipilAlertDialog.findViewById<AutoCompleteTextView>(R.id.spinner_agama_sipil)
        val jkSipilView =
            sipilAlertDialog.findViewById<AutoCompleteTextView>(R.id.spinner_jk_sipil)
        val namaSipilView = sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_nama_sipil)
        val pekerjaanSipilView =
            sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_pekerjaan_sipil)
        val kwgSipilView = sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_kwg_sipil)
        val alamatSipilView =
            sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_alamat_sipil)
        val noTelpSipilView =
            sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_no_telp_sipil)
        val nikSipilView = sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_nik_sipil)
        val tmptSipilView =
            sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_tempat_lahir_sipil)
        val tglSipilView =
            sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_tanggal_lahir_sipil)

        /*getData*/
        namaSipilView.setText(disiplin?.nama_pelapor)
        pekerjaanSipilView.setText(disiplin?.pekerjaan_pelapor)
        kwgSipilView.setText(disiplin?.kewarganegaraan_pelapor)
        alamatSipilView.setText(disiplin?.alamat_pelapor)
        noTelpSipilView.setText(disiplin?.no_telp_pelapor)
        nikSipilView.setText(disiplin?.nik_ktp_pelapor)
        tmptSipilView.setText(disiplin?.tempat_lahir_pelapor)
        tglSipilView.setText(reverseTanggal(disiplin?.tanggal_lahir_pelapor))

        namaSipil = namaSipilView.text.toString()
        jkSipil = disiplin?.jenis_kelamin_pelapor
        agamaSipil = disiplin?.agama_pelapor
        noTelpSipil = noTelpSipilView.text.toString()
        nikSipil = nikSipilView.text.toString()
        alamatSipil = alamatSipilView.text.toString()
        kwgSipil = kwgSipilView.text.toString()
        pekerjaanSipil = pekerjaanSipilView.text.toString()
        tglSipil = tglSipilView.text.toString()
        tmptSipil = tmptSipilView.text.toString()

        if (disiplin?.jenis_kelamin_pelapor == "laki_laki") {
            jkSipilView.setText("Laki-Laki")
        } else {
            jkSipilView.setText("Perempuan")
        }
        when (disiplin?.agama_pelapor) {
            "islam" -> spAgama.setText("Islam")
            "katolik" -> spAgama.setText("Katolik")
            "protestan" -> spAgama.setText("Protestan")
            "buddha" -> spAgama.setText("Buddha")
            "hindu" -> spAgama.setText("Hindu")
            "khonghuchu" -> spAgama.setText("Khonghuchu")
        }

        val jkItem = listOf("Laki-Laki", "Perempuan")
        val adapterJk = ArrayAdapter(this, R.layout.item_spinner, jkItem)
        jkSipilView.setAdapter(adapterJk)
        jkSipilView.setOnItemClickListener { parent, _, position, _ ->
            txt_jk_sipil_disiplin_lp_edit.text =
                "Jenis Kelamin : ${parent.getItemAtPosition(position)}"
            if (position == 0) {
                jkSipil = "laki_laki"
            } else {
                jkSipil = "perempuan"
            }
        }

        val agamaItem =
            listOf("Islam", "Katolik", "Protestan", "Buddha", "Hindu", "Khonghucu")
        val adapterAgama = ArrayAdapter(this, R.layout.item_spinner, agamaItem)
        spAgama.setAdapter(adapterAgama)
        spAgama.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> {
                    agamaSipil = "islam"
                    txt_agama_sipil_disiplin_lp_edit.text = "Agama : Islam"
                }
                1 -> {
                    agamaSipil = "katolik"
                    txt_agama_sipil_disiplin_lp_edit.text = "Agama : Katolik"
                }
                2 -> {
                    agamaSipil = "protestan"
                    txt_agama_sipil_disiplin_lp_edit.text = "Agama : Protestan"
                }
                3 -> {
                    agamaSipil = "buddha"
                    txt_agama_sipil_disiplin_lp_edit.text = "Agama : Buddha"
                }
                4 -> {
                    agamaSipil = "hindu"
                    txt_agama_sipil_disiplin_lp_edit.text = "Agama : Hindu"
                }
                5 -> {
                    agamaSipil = "konghuchu"
                    txt_agama_sipil_disiplin_lp_edit.text = "Agama : Konghuchu"
                }
            }
        }
        materialAlertDialogBuilder.setView(sipilAlertDialog)
            .setTitle("Tambah Data Sipil")
//            .setMessage("Masukkan Data Sipil")
            .setPositiveButton("Tambah") { _, _ ->
                namaSipil = namaSipilView.text.toString()
                txt_nama_sipil_disiplin_lp_edit.text = "Nama : ${namaSipilView.text.toString()}"

                tmptSipil = tmptSipilView.text.toString()
                tglSipil = tglSipilView.text.toString()
                txt_ttl_sipil_disiplin_lp_edit.text =
                    "TTL : ${tmptSipil}, ${formatterTanggal(tglSipil)}"
                pekerjaanSipil = pekerjaanSipilView.text.toString()
                txt_pekerjaan_sipil_disiplin_lp_edit.text =
                    "Pekerjaan : ${pekerjaanSipilView.text.toString()}"

                kwgSipil = kwgSipilView.text.toString()
                txt_kwg_sipil_disiplin_lp_edit.text =
                    "Kewarganegaraan : ${kwgSipilView.text.toString()}"

                alamatSipil = alamatSipilView.text.toString()
                txt_alamat_sipil_disiplin_lp_edit.text =
                    "Alamat : ${alamatSipilView.text.toString()}"

                noTelpSipil = noTelpSipilView.text.toString()
                txt_no_telp_sipil_disiplin_lp_edit.text =
                    "No Telepon : ${noTelpSipilView.text.toString()}"

                nikSipil = nikSipilView.text.toString()
                txt_nik_ktp_sipil_disiplin_lp_edit.text =
                    "NIK KTP : ${nikSipilView.text.toString()}"

//                dialog.dismiss()

            }
            .setNegativeButton("Batal") { _, _ ->
//                displayMessage("Operation cancelled!")
//                dialog.dismiss()
            }
            .show()

    }

    private fun apiEditDisiplinView(disiplin: LpResp?) {
        NetworkConfig().getServLp()
            .getLpById("Bearer ${sessionManager1.fetchAuthToken()}", disiplin?.id).enqueue(object :
                Callback<LpResp> {
                override fun onFailure(call: Call<LpResp>, t: Throwable) {
                    Toast.makeText(
                        this@EditLpDisiplinActivity, R.string.error_conn, Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResponse(
                    call: Call<LpResp>,
                    response: Response<LpResp>
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
    private fun getViewEditDisipilin(disiplin: LpResp?) {
        if (disiplin?.is_ada_lhp == 1) {
            _idLhp = disiplin.lhp?.id
            ll_pick_lhp_lp_edit_disiplin.visible()
            txt_no_lhp_lp_edit_disiplin.text = "No LHP: ${disiplin.lhp?.no_lhp}"
        }

        if (disiplin?.personel_terlapor != null) {
            changedIdTerlapor = disiplin.personel_terlapor?.id
            txt_nama_terlapor_lp_disiplin_edit.text = "Nama : ${disiplin?.personel_terlapor?.nama}"
            txt_pangkat_terlapor_lp_disiplin_edit.text =
                "Pangkat : ${disiplin?.personel_terlapor?.pangkat.toString().toUpperCase()}"
            txt_nrp_terlapor_lp_disiplin_edit.text = "NRP : ${disiplin?.personel_terlapor?.nrp}"
            txt_jabatan_terlapor_lp_disiplin_edit.text =
                "Jabatan : ${disiplin?.personel_terlapor?.jabatan}"
            txt_kesatuan_terlapor_lp_disiplin_edit.text =
                "Kesatuan : ${
                    disiplin?.personel_terlapor?.satuan_kerja?.kesatuan.toString()
                        .toUpperCase()
                }"
        } else {
            changedIdTerlapor = disiplin?.personel_terlapor_lhp?.personel?.id
            txt_nama_terlapor_lp_disiplin_edit.text =
                "Nama : ${disiplin?.personel_terlapor_lhp?.personel?.nama}"
            txt_pangkat_terlapor_lp_disiplin_edit.text =
                "Pangkat : ${
                    disiplin?.personel_terlapor_lhp?.personel?.pangkat.toString().toUpperCase()
                }"
            txt_nrp_terlapor_lp_disiplin_edit.text =
                "NRP : ${disiplin?.personel_terlapor_lhp?.personel?.nrp}"
            txt_jabatan_terlapor_lp_disiplin_edit.text =
                "Jabatan : ${disiplin?.personel_terlapor_lhp?.personel?.jabatan}"
            txt_kesatuan_terlapor_lp_disiplin_edit.text =
                "Kesatuan : ${
                    disiplin?.personel_terlapor_lhp?.personel?.satuan_kerja?.kesatuan.toString()
                        .toUpperCase()
                }"
        }

        if (disiplin?.status_pelapor == "sipil") {
            ll_sipil_edit_disiplin.visible()

            txt_nama_sipil_disiplin_lp_edit.text = "Nama: ${disiplin.nama_pelapor}"
            txt_ttl_sipil_disiplin_lp_edit.text =
                "TTL: ${disiplin.tempat_lahir_pelapor}, ${formatterDiffTanggal(disiplin.tanggal_lahir_pelapor)}"
            txt_jk_sipil_disiplin_lp_edit.text = "Jenis Kelamin: ${disiplin.jenis_kelamin_pelapor}"
            txt_agama_sipil_disiplin_lp_edit.text = "Agama: ${disiplin.agama_pelapor}"
            txt_pekerjaan_sipil_disiplin_lp_edit.text = "Pekerjaan: ${disiplin.pekerjaan_pelapor}"
            txt_kwg_sipil_disiplin_lp_edit.text =
                "Kewarganegaraan: ${disiplin.kewarganegaraan_pelapor}"
            txt_alamat_sipil_disiplin_lp_edit.text = "Alamat: ${disiplin.nama_pelapor}"
            txt_no_telp_sipil_disiplin_lp_edit.text = "No Telepon: ${disiplin.nama_pelapor}"
            txt_nik_ktp_sipil_disiplin_lp_edit.text = "NIK KTP: ${disiplin.nama_pelapor}"
        } else {
            changedIdPelapor = disiplin?.personel_pelapor?.id
            ll_personel_disiplin_edit.visible()
            txt_nama_pelapor_disiplin_lp_add.text =
                "Nama : ${disiplin?.personel_pelapor?.nama}"
            txt_pangkat_pelapor_disiplin_lp_add.text =
                "Pangkat : ${disiplin?.personel_pelapor?.pangkat.toString().toUpperCase()}"
            txt_nrp_pelapor_disiplin_lp_add.text =
                "NRP : ${disiplin?.personel_pelapor?.nrp}"
            txt_jabatan_pelapor_disiplin_lp_add.text =
                "Jabatan : ${disiplin?.personel_pelapor?.jabatan}"
            txt_kesatuan_pelapor_disiplin_lp_add.text =
                "Kesatuan : ${
                    disiplin?.personel_pelapor?.satuan_kerja?.kesatuan.toString().toUpperCase()
                }"
        }


        edt_pukul_buat_edit_lp_disiplin.setText(disiplin?.detail_laporan?.waktu_buat_laporan)
        edt_no_lp_disiplin_edit.setText(disiplin?.no_lp)
        edt_macam_pelanggaran_disiplin_edit.setText(disiplin?.detail_laporan?.macam_pelanggaran)


        edt_ket_pelapor_disiplin_edit.setText(disiplin?.detail_laporan?.keterangan_pelapor)



        edt_kronologis_pelapor_disiplin_edit.setText(disiplin?.detail_laporan?.kronologis_dari_pelapor)
        edt_rincian_pelanggaran_disiplin.setText(disiplin?.detail_laporan?.rincian_pelanggaran_disiplin)

        edt_kota_buat_edit_lp_disiplin.setText(disiplin?.kota_buat_laporan)
        edt_tgl_buat_edit_lp_disiplin.setText(disiplin?.tanggal_buat_laporan)
        edt_nama_pimpinan_bidang_edit_disiplin.setText(disiplin?.nama_yang_mengetahui)
        edt_pangkat_pimpinan_bidang_edit_disiplin.setText(
            disiplin?.pangkat_yang_mengetahui.toString().toUpperCase()
        )
        edt_nrp_pimpinan_bidang_edit_disiplin.setText(disiplin?.nrp_yang_mengetahui)
        edt_jabatan_pimpinan_bidang_edit_disiplin.setText(disiplin?.jabatan_yang_mengetahui)
        edt_uraian_pelanggaran_disiplin_edit.setText(disiplin?.uraian_pelanggaran)

//        spinner_kesatuan_pimpinan_bidang_edit_disiplin.setText(disiplin?.kesatuan_yang_mengetahui.toString().toUpperCase())
//        namaSatker = disiplin?.kesatuan_yang_mengetahui
        btn_choose_lhp_lp_edit_disiplin.setOnClickListener {
            val intent = Intent(this, ChooseLhpActivity::class.java)
            startActivityForResult(intent, REQ_LHP)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_choose_personel_terlapor_disiplin_edit.setOnClickListener {
            if (_idLhp == null || _idLhp == 0) {
                val intent = Intent(this, KatPersonelActivity::class.java)
                intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
                startActivityForResult(intent, EditLpKkeActivity.REQ_TERLAPOR)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            } else {
                val intent = Intent(this, ChoosePersonelActivity::class.java).apply {
                    this.putExtra(AddLpActivity.IS_LHP_PERSONEL, _idLhp!!)
                }
                startActivityForResult(intent, REQ_TERLAPOR_LHP)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
    }

    private fun updateLpDisiplin(disiplin: LpResp?) {
        editLpDisiplinReq.id_lhp = _idLhp
        editLpDisiplinReq.id_personel_terlapor = changedIdTerlapor
        editLpDisiplinReq.id_personel_terlapor_lhp = changedIdTerlapor
//        changedIdPelapor = disiplin?.detail_laporan?.personel_pelapor?.id
//        changedIdTerlapor = disiplin?.personel_terlapor?.id
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
//        editLpDisiplinReq.id_personel_operator = sessionManager1.fetchUser()?.id
        editLpDisiplinReq.id_personel_pelapor = changedIdPelapor
//        editLpDisiplinReq.kesatuan_yang_mengetahui = namaSatker
//        lpDisiplinReq.id_personel_pelapor = this.changedIdPelapor

//        Log.e("is same", "${lpDisiplinReq.id_personel_pelapor == disiplin?.personel_pelapor}")
//        if(lpDisiplinReq.id_personel_pelapor != disiplin?.id_personel_pelapor) {
//            lpDisiplinReq.id_personel_pelapor = this.changedIdPelapor
//        }
        editLpDisiplinReq.nama_pelapor = namaSipil
        editLpDisiplinReq.agama_pelapor = agamaSipil
        editLpDisiplinReq.pekerjaan_pelapor = pekerjaanSipil
        editLpDisiplinReq.kewarganegaraan_pelapor = kwgSipil
        editLpDisiplinReq.jenis_kelamin_pelapor = jkSipil
        editLpDisiplinReq.alamat_pelapor = alamatSipil
        editLpDisiplinReq.no_telp_pelapor = noTelpSipil
        editLpDisiplinReq.nik_ktp_pelapor = nikSipil
        editLpDisiplinReq.tempat_lahir_pelapor = tmptSipil
        editLpDisiplinReq.tanggal_lahir_pelapor = tglSipil
        Log.e("edit Disiplin", "$editLpDisiplinReq")
        var jenisLhp: String? = null
        if (disiplin?.is_ada_lhp == 1) {
            jenisLhp = "dengan"
        } else {
            jenisLhp = "tanpa"
        }
        apiUpdDisiplin(disiplin,jenisLhp)
    }

    private fun apiUpdDisiplin(disiplin: LpResp?, jenisLhp: String) {
        NetworkConfig().getServLp().updLpDisp(
            "Bearer ${sessionManager1.fetchAuthToken()}",  disiplin?.id, disiplin?.status_pelapor, jenisLhp,  editLpDisiplinReq
        ).enqueue(object : Callback<Base1Resp<DokLpResp>> {
            override fun onFailure(call: Call<Base1Resp<DokLpResp>>, t: Throwable) {
                Toast.makeText(this@EditLpDisiplinActivity, "$t", Toast.LENGTH_SHORT)
                    .show()
                Handler(Looper.getMainLooper()).postDelayed({
                    btn_save_edit_lp_disiplin.hideDrawable(R.string.save)
                }, 1000)
                btn_save_edit_lp_disiplin.hideDrawable(R.string.not_update)
            }

            override fun onResponse(
                call: Call<Base1Resp<DokLpResp>>,
                response: Response<Base1Resp<DokLpResp>>
            ) {
                if (response.body()?.message == "Data lp disiplin updated succesfully") {
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
                        "${response.body()?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_edit_lp_disiplin.hideDrawable(R.string.save)
                    }, 1000)
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
                            " Kesatuan : ${
                                personel?.satuan_kerja?.kesatuan.toString()
                                    .toUpperCase()
                            }"
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
                            "Kesatuan : ${
                                personel?.satuan_kerja?.kesatuan.toString()
                                    .toUpperCase()
                            }"
                        Log.e("id terlapor", "$changedIdTerlapor")
                    }
                    REQ_LHP -> {
                        val dataLhp =
                            data?.getParcelableExtra<LhpMinResp>(ChooseLhpActivity.DATA_LP)
                        _idLhp = dataLhp?.id
                        txt_no_lhp_lp_edit_disiplin.text = "No LHP: ${dataLhp?.no_lhp}"
                    }
                    REQ_TERLAPOR_LHP -> {
                        val dataPersLhp =
                            data?.getParcelableExtra<PersonelPenyelidikResp>(ChoosePersonelActivity.DATA_PERSONEL)
                        changedIdTerlapor = dataPersLhp?.id
                        txt_nama_terlapor_lp_disiplin_edit  .text =
                            "Nama : ${dataPersLhp?.personel?.nama}"
                        txt_pangkat_terlapor_lp_disiplin_edit   .text =
                            "Pangkat : ${dataPersLhp?.personel?.pangkat.toString().toUpperCase()}"
                        txt_nrp_terlapor_lp_disiplin_edit   .text =
                            "NRP : ${dataPersLhp?.personel?.nrp}"
                        txt_jabatan_terlapor_lp_disiplin_edit   .text =
                            "Jabatan : ${dataPersLhp?.personel?.jabatan}"
                        txt_kesatuan_terlapor_lp_disiplin_edit  .text =
                            "Kesatuan : ${
                                dataPersLhp?.personel?.satuan_kerja?.kesatuan.toString()
                                    .toUpperCase()
                            }"
                    }

                }
            }
        }
    }

    companion object {
        const val EDIT_DISIPLIN = "EDIT_DISIPLIN"
        const val REQ_PELAPOR = 202
        const val REQ_TERLAPOR = 101
        const val REQ_LHP = 103
        const val REQ_TERLAPOR_LHP = 104
    }
}