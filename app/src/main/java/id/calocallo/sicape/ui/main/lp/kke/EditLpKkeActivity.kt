package id.calocallo.sicape.ui.main.lp.kke

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
import id.calocallo.sicape.network.request.LpKkeReq
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.ui.main.choose.ChoosePersonelActivity
import id.calocallo.sicape.ui.main.choose.lhp.ChooseLhpActivity
import id.calocallo.sicape.ui.main.lp.AddLpActivity
import id.calocallo.sicape.ui.main.lp.pidana.EditLpPidanaActivity
import id.calocallo.sicape.utils.ext.formatterDiffTanggal
import id.calocallo.sicape.utils.ext.formatterTanggal
import id.calocallo.sicape.utils.ext.reverseTanggal
import id.calocallo.sicape.utils.ext.visible
import kotlinx.android.synthetic.main.activity_edit_lp_kke.*
import kotlinx.android.synthetic.main.activity_edit_lp_kke.edt_jabatan_pimpinan_bidang_edit
import kotlinx.android.synthetic.main.activity_edit_lp_kke.edt_kota_buat_edit_lp
import kotlinx.android.synthetic.main.activity_edit_lp_kke.edt_nama_pimpinan_bidang_edit
import kotlinx.android.synthetic.main.activity_edit_lp_kke.edt_nrp_pimpinan_bidang_edit
import kotlinx.android.synthetic.main.activity_edit_lp_kke.edt_pangkat_pimpinan_bidang_edit
import kotlinx.android.synthetic.main.activity_edit_lp_kke.edt_tgl_buat_edit
import kotlinx.android.synthetic.main.activity_edit_lp_pidana.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditLpKkeActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var editLpKkeReq = LpKkeReq()
    private var changedIdPelapor: Int? = null
    private var changedIdTerlapor: Int? = null
    private var _idLhp: Int? = null
    private var namaSatker: String? = null

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
        setContentView(R.layout.activity_edit_lp_kke)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Laporan Polisi Kode Etik"
        val kke = intent.extras?.getParcelable<LpResp>(EDIT_KKE)

        getViewKKeEdit(kke)
        sessionManager1 = SessionManager1(this)

        materialAlertDialogBuilder =
            MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
        btn_sipil_edit_kke.setOnClickListener {
            sipilAlertDialog = LayoutInflater.from(this)
                .inflate(R.layout.item_sipil_pidana, null, false)
            launchSipilView(kke)
        }
        bindProgressButton(btn_save_edit_lp_kke)
        btn_save_edit_lp_kke.attachTextChangeAnimator()
        btn_save_edit_lp_kke.setOnClickListener {
            updateKke(kke)
            btn_save_edit_lp_kke.showProgress {
                progressColor = Color.WHITE
            }

            /*Handler(Looper.getMainLooper()).postDelayed({
                btn_save_edit_lp_kke.hideDrawable(R.string.save)
            }, 3000)*/
        }
        /*
            spinner_kesatuan_lp_kke_edit.setAdapter(
                ArrayAdapter(
                    this,
                    R.layout.item_spinner,
                    resources.getStringArray(R.array.satker)
                )
            )
            spinner_kesatuan_lp_kke_edit.setOnItemClickListener { parent, view, position, id ->
                namaSatker = parent.getItemAtPosition(position) as String?
            }*/

    }

    private fun launchSipilView(kke: LpResp?) {
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
        namaSipilView.setText(kke?.nama_pelapor)
        pekerjaanSipilView.setText(kke?.pekerjaan_pelapor)
        kwgSipilView.setText(kke?.kewarganegaraan_pelapor)
        alamatSipilView.setText(kke?.alamat_pelapor)
        noTelpSipilView.setText(kke?.no_telp_pelapor)
        nikSipilView.setText(kke?.nik_ktp_pelapor)
        tmptSipilView.setText(kke?.tempat_lahir_pelapor)
        tglSipilView.setText(reverseTanggal(kke?.tanggal_lahir_pelapor))

        namaSipil = namaSipilView.text.toString()
        jkSipil = kke?.jenis_kelamin_pelapor
        agamaSipil = kke?.agama_pelapor
        noTelpSipil = noTelpSipilView.text.toString()
        nikSipil = nikSipilView.text.toString()
        alamatSipil = alamatSipilView.text.toString()
        kwgSipil = kwgSipilView.text.toString()
        pekerjaanSipil = pekerjaanSipilView.text.toString()
        tglSipil = tglSipilView.text.toString()
        tmptSipil = tmptSipilView.text.toString()

        if (kke?.jenis_kelamin_pelapor == "laki_laki") {
            jkSipilView.setText("Laki-Laki")
        } else {
            jkSipilView.setText("Perempuan")
        }
        when (kke?.agama_pelapor) {
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
            txt_jk_sipil_kke_lp_edit.text =
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
                    txt_agama_sipil_kke_lp_edit.text = "Agama : Islam"
                }
                1 -> {
                    agamaSipil = "katolik"
                    txt_agama_sipil_kke_lp_edit.text = "Agama : Katolik"
                }
                2 -> {
                    agamaSipil = "protestan"
                    txt_agama_sipil_kke_lp_edit.text = "Agama : Protestan"
                }
                3 -> {
                    agamaSipil = "buddha"
                    txt_agama_sipil_kke_lp_edit.text = "Agama : Buddha"
                }
                4 -> {
                    agamaSipil = "hindu"
                    txt_agama_sipil_kke_lp_edit.text = "Agama : Hindu"
                }
                5 -> {
                    agamaSipil = "konghuchu"
                    txt_agama_sipil_kke_lp_edit.text = "Agama : Konghuchu"
                }
            }
        }
        materialAlertDialogBuilder.setView(sipilAlertDialog)
            .setTitle("Tambah Data Sipil")
//            .setMessage("Masukkan Data Sipil")
            .setPositiveButton("Tambah") { _, _ ->
                namaSipil = namaSipilView.text.toString()
                txt_nama_sipil_kke_lp_edit.text = "Nama : ${namaSipilView.text.toString()}"

                tmptSipil = tmptSipilView.text.toString()
                tglSipil = tglSipilView.text.toString()
                txt_ttl_sipil_kke_lp_edit.text =
                    "TTL : ${tmptSipil}, ${formatterTanggal(tglSipil)}"
                pekerjaanSipil = pekerjaanSipilView.text.toString()
                txt_pekerjaan_sipil_kke_lp_edit.text =
                    "Pekerjaan : ${pekerjaanSipilView.text.toString()}"

                kwgSipil = kwgSipilView.text.toString()
                txt_kwg_sipil_kke_lp_edit.text =
                    "Kewarganegaraan : ${kwgSipilView.text.toString()}"

                alamatSipil = alamatSipilView.text.toString()
                txt_alamat_sipil_kke_lp_edit.text = "Alamat : ${alamatSipilView.text.toString()}"

                noTelpSipil = noTelpSipilView.text.toString()
                txt_no_telp_sipil_kke_lp_edit.text =
                    "No Telepon : ${noTelpSipilView.text.toString()}"

                nikSipil = nikSipilView.text.toString()
                txt_nik_ktp_sipil_kke_lp_edit.text = "NIK KTP : ${nikSipilView.text.toString()}"

//                dialog.dismiss()

            }
            .setNegativeButton("Batal") { _, _ ->
//                displayMessage("Operation cancelled!")
//                dialog.dismiss()
            }
            .show()

    }

    private fun updateKke(kke: LpResp?) {
        editLpKkeReq.no_lp = edt_no_lp_kke_edit.text.toString()
        editLpKkeReq.isi_laporan = edt_isi_laporan_kke_edit.text.toString()
        editLpKkeReq.alat_bukti = edt_alat_bukti_kke_edit.text.toString()
        editLpKkeReq.kota_buat_laporan = edt_kota_buat_edit_lp.text.toString()
        editLpKkeReq.tanggal_buat_laporan = edt_tgl_buat_edit.text.toString()
        editLpKkeReq.nama_yang_mengetahui = edt_nama_pimpinan_bidang_edit.text.toString()
        editLpKkeReq.pangkat_yang_mengetahui = edt_pangkat_pimpinan_bidang_edit.text.toString()
        editLpKkeReq.nrp_yang_mengetahui = edt_nrp_pimpinan_bidang_edit.text.toString()
        editLpKkeReq.jabatan_yang_mengetahui = edt_jabatan_pimpinan_bidang_edit.text.toString()
//        editLpKkeReq.kesatuan_yang_mengetahui = namaSatker
//        lpKodeEtikReq.id_personel_operator = sessionManager.fetchUser()?.id
        editLpKkeReq.id_lhp = _idLhp
        editLpKkeReq.id_personel_terlapor = changedIdTerlapor
        editLpKkeReq.id_personel_terlapor_lhp = changedIdTerlapor
        editLpKkeReq.id_personel_pelapor = changedIdPelapor
        editLpKkeReq.nama_pelapor = namaSipil
        editLpKkeReq.agama_pelapor = agamaSipil
        editLpKkeReq.pekerjaan_pelapor = pekerjaanSipil
        editLpKkeReq.kewarganegaraan_pelapor = kwgSipil
        editLpKkeReq.jenis_kelamin_pelapor = jkSipil
        editLpKkeReq.alamat_pelapor = alamatSipil
        editLpKkeReq.no_telp_pelapor = noTelpSipil
        editLpKkeReq.nik_ktp_pelapor = nikSipil
        editLpKkeReq.tempat_lahir_pelapor = tmptSipil
        editLpKkeReq.tanggal_lahir_pelapor = tglSipil
        editLpKkeReq.uraian_pelanggaran = edt_uraian_kke_edit.text.toString()
        var jenisLhp: String? = null
        if (kke?.is_ada_lhp == 1) {
            jenisLhp = "dengan"
        } else {
            jenisLhp = "tanpa"
        }

        Log.e("edit_kke", "$editLpKkeReq")
        apiUpdKke(kke, jenisLhp)
    }

    private fun apiUpdKke(kke: LpResp?, jenisLhp: String) {
        NetworkConfig().getServLp()
            .updLpKke(
                "Bearer ${sessionManager1.fetchAuthToken()}",
                kke?.id, kke?.status_pelapor, jenisLhp, editLpKkeReq
            )
            .enqueue(object :
                Callback<Base1Resp<DokLpResp>> {
                override fun onFailure(call: Call<Base1Resp<DokLpResp>>, t: Throwable) {
                    Toast.makeText(this@EditLpKkeActivity, R.string.error_conn, Toast.LENGTH_SHORT)
                        .show()
                    btn_save_edit_lp_kke.hideProgress(R.string.not_update)
                }

                override fun onResponse(
                    call: Call<Base1Resp<DokLpResp>>,
                    response: Response<Base1Resp<DokLpResp>>
                ) {
                    if (response.body()?.message == "Data lp kode etik updated succesfully") {
                        val animatedDrawable =
                            ContextCompat.getDrawable(
                                this@EditLpKkeActivity, R.drawable.animated_check
                            )!!
                        val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
                        animatedDrawable.setBounds(0, 0, size, size)
                        btn_save_edit_lp_kke.showDrawable(animatedDrawable) {
                            textMarginRes = R.dimen.space_10dp
                            buttonTextRes = R.string.data_updated
                        }
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        }, 500)
                    } else {
                        Toast.makeText(
                            this@EditLpKkeActivity, R.string.error_conn, Toast.LENGTH_SHORT
                        )
                            .show()
                        btn_save_edit_lp_kke.hideProgress(R.string.not_update)
                    }
                }
            })
    }

    @SuppressLint("SetTextI18n")
    private fun getViewKKeEdit(kke: LpResp?) {
        _idLhp = kke?.lhp?.id
        Log.e("idLHP", "$_idLhp")

        if (kke?.is_ada_lhp == 1) {
            ll_pick_lhp_lp_edit_kke.visible()
            txt_no_lhp_lp_edit_kke.text = "No LHP: ${kke?.lhp?.no_lhp}"
        }
        btn_choose_lhp_lp_edit_kke.setOnClickListener {
            val intent = Intent(this, ChooseLhpActivity::class.java)
            startActivityForResult(intent, REQ_LHP)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_choose_personel_terlapor_kke_edit.setOnClickListener {
            if (_idLhp == null || _idLhp == 0) {
                val intent = Intent(this, KatPersonelActivity::class.java)
                intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
                startActivityForResult(intent, REQ_TERLAPOR)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            } else {
                val intent = Intent(this, ChoosePersonelActivity::class.java).apply {
                    this.putExtra(AddLpActivity.IS_LHP_PERSONEL, _idLhp!!)
                }
                startActivityForResult(intent, EditLpPidanaActivity.REQ_TERLAPOR_LHP)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }

        //terlapor
        /* btn_choose_personel_terlapor_kke_edit.setOnClickListener {
             val intent = Intent(this, KatPersonelActivity::class.java)
             intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
             startActivityForResult(intent, REQ_TERLAPOR)
             overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
         }*/
        when {
            kke?.personel_terlapor_lhp != null -> {
                changedIdTerlapor = kke?.personel_terlapor_lhp?.id
                txt_nama_menerima_lp_edit.text =
                    "Nama : ${kke?.personel_terlapor_lhp?.personel?.nama}"
                txt_pangkat_menerima_lp_edit.text =
                    "Pangkat : ${
                        kke?.personel_terlapor_lhp?.personel?.pangkat.toString().toUpperCase()
                    }"
                txt_nrp_menerima_lp_edit.text = "NRP : ${kke?.personel_terlapor_lhp?.personel?.nrp}"
                txt_jabatan_menerima_lp_edit.text =
                    "Jabatan : ${kke?.personel_terlapor_lhp?.personel?.jabatan}"

            }
            kke?.personel_terlapor != null -> {
                changedIdTerlapor = kke?.personel_terlapor?.id
                txt_nama_menerima_lp_edit.text = "Nama : ${kke?.personel_terlapor?.nama}"
                txt_pangkat_menerima_lp_edit.text =
                    "Pangkat : ${kke?.personel_terlapor?.pangkat.toString().toUpperCase()}"
                txt_nrp_menerima_lp_edit.text = "NRP : ${kke?.personel_terlapor?.nrp}"
                txt_jabatan_menerima_lp_edit.text = "Jabatan : ${kke?.personel_terlapor?.jabatan}"

            }
        }
        if (kke?.status_pelapor == "sipil") {
            changedIdPelapor = null
            ll_sipil_edit_kke.visible()
            txt_nama_sipil_kke_lp_edit.text = "Nama :  ${kke.nama_pelapor}"
            txt_ttl_sipil_kke_lp_edit.text =
                "TTL : ${kke.tempat_lahir_pelapor}, ${formatterDiffTanggal(kke.tanggal_lahir_pelapor)}"
            txt_agama_sipil_kke_lp_edit.text = "Agama : ${kke.agama_pelapor}"
            txt_pekerjaan_sipil_kke_lp_edit.text =
                "Pekerjaan : ${kke.pekerjaan_pelapor}"
            txt_kwg_sipil_kke_lp_edit.text =
                "Kewarganegaraan : ${kke.kewarganegaraan_pelapor}"
            txt_alamat_sipil_kke_lp_edit.text = "Alamat : ${kke.alamat_pelapor}"
            txt_no_telp_sipil_kke_lp_edit.text =
                "No Telepon : ${kke.no_telp_pelapor}"
            txt_nik_ktp_sipil_kke_lp_edit.text =
                "NIK KTP : ${kke.nik_ktp_pelapor}"
            txt_jk_sipil_kke_lp_edit.text =
                "Jenis Kelamin : ${kke.jenis_kelamin_pelapor}"

            namaSipil = kke.nama_pelapor
            jkSipil = kke.jenis_kelamin_pelapor
            agamaSipil = kke.agama_pelapor
            noTelpSipil = kke.no_telp_pelapor
            nikSipil = kke.nik_ktp_pelapor
            alamatSipil = kke.alamat_pelapor
            kwgSipil = kke.kewarganegaraan_pelapor
            pekerjaanSipil = kke.pekerjaan_pelapor
            tglSipil = reverseTanggal(kke.tanggal_lahir_pelapor)
            tmptSipil = kke.tempat_lahir_pelapor
        } else {
            ll_personel_edit_kke.visible()
            changedIdPelapor = kke?.personel_pelapor?.id
            txt_nama_pelapor_kke_lp_edit.text = "Nama : ${kke?.personel_pelapor?.nama}"
            txt_pangkat_pelapor_kke_lp_edit.text =
                "Pangkat : ${kke?.personel_pelapor?.pangkat.toString().toUpperCase()}"
            txt_nrp_pelapor_kke_lp_edit.text = "NRP : ${kke?.personel_pelapor?.nrp}"
            txt_jabatan_pelapor_kke_lp_edit.text =
                "Jabatan : ${kke?.personel_pelapor?.jabatan}"
            txt_kesatuan_pelapor_kke_lp_edit.text =
                "Kesatuan : ${
                    kke?.personel_pelapor?.satuan_kerja?.kesatuan.toString()
                        .toUpperCase()
                }"
        }

        //edittext
        edt_no_lp_kke_edit.setText(kke?.no_lp)
        edt_isi_laporan_kke_edit.setText(kke?.detail_laporan?.isi_laporan)
        edt_alat_bukti_kke_edit.setText(kke?.detail_laporan?.alat_bukti)
        edt_kota_buat_edit_lp.setText(kke?.kota_buat_laporan)
        edt_uraian_kke_edit.setText(kke?.uraian_pelanggaran)
        edt_tgl_buat_edit.setText(kke?.tanggal_buat_laporan)
        edt_nama_pimpinan_bidang_edit.setText(kke?.nama_yang_mengetahui)
        edt_pangkat_pimpinan_bidang_edit.setText(
            kke?.pangkat_yang_mengetahui.toString().toUpperCase()
        )
        edt_nrp_pimpinan_bidang_edit.setText(kke?.nrp_yang_mengetahui)
        edt_jabatan_pimpinan_bidang_edit.setText(kke?.jabatan_yang_mengetahui)
//        spinner_kesatuan_lp_kke_edit.setText(kke?.kesatuan_yang_mengetahui.toString().toUpperCase())
//        namaSatker = kke?.kesatuan_yang_mengetahui
        /* txt_kesatuan_menerima_lp_edit.text =
             "Kesatuan : ${kke?.personel_terlapor?.kesatuan.toString().toUpperCase()}"*/

        //pelapor
        btn_choose_personel_pelapor_kke_edit.setOnClickListener {
            val intent = Intent(this, KatPersonelActivity::class.java)
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, REQ_PELAPOR)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val personel = data?.extras?.getParcelable<PersonelMinResp>("ID_PERSONEL")
        when (resultCode) {
            Activity.RESULT_OK -> {
                when (requestCode) {
                    REQ_PELAPOR -> {
                        //req set id
                        changedIdPelapor = personel?.id

                        txt_nama_pelapor_kke_lp_edit.text = "Nama : ${personel?.nama}"
                        txt_pangkat_pelapor_kke_lp_edit.text =
                            "Pangkat : ${personel?.pangkat.toString().toUpperCase()}"
                        txt_nrp_pelapor_kke_lp_edit.text = "NRP : ${personel?.nrp}"
                        txt_jabatan_pelapor_kke_lp_edit.text = "Jabatan : ${personel?.jabatan}"
                        txt_kesatuan_pelapor_kke_lp_edit.text =
                            "Kesatuan : ${
                                personel?.satuan_kerja?.kesatuan.toString()
                                    .toUpperCase()
                            }"
                    }
                    REQ_TERLAPOR -> {
                        //req set id
                        changedIdTerlapor = personel?.id

                        txt_nama_menerima_lp_edit.text = "Nama : ${personel?.nama}"
                        txt_pangkat_menerima_lp_edit.text =
                            "Pangkat : ${personel?.pangkat.toString().toUpperCase()}"
                        txt_nrp_menerima_lp_edit.text = "NRP : ${personel?.nrp}"
                        txt_jabatan_menerima_lp_edit.text = "Jabatan : ${personel?.jabatan}"
                        txt_kesatuan_menerima_lp_edit.text =
                            "Kesatuan : ${
                                personel?.satuan_kerja?.kesatuan.toString()
                                    .toUpperCase()
                            }"
                    }
                    REQ_LHP -> {
                        val dataLhp =
                            data?.getParcelableExtra<LhpMinResp>(ChooseLhpActivity.DATA_LP)
                        _idLhp = dataLhp?.id
                        txt_no_lhp_lp_edit_kke.text = "No LHP: ${dataLhp?.no_lhp}"
                    }
                }
            }
        }
    }

    companion object {
        const val EDIT_KKE = "EDIT_KKE"
        const val REQ_TERLAPOR = 202
        const val REQ_PELAPOR = 102
        const val REQ_LHP = 103
    }
}