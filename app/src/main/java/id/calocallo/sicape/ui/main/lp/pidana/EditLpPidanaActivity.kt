package id.calocallo.sicape.ui.main.lp.pidana

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.request.LpPidanaReq
import id.calocallo.sicape.network.response.LpPidanaResp
import id.calocallo.sicape.ui.main.choose.ChoosePersonelActivity
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_catpers.*
import kotlinx.android.synthetic.main.activity_add_lp_pidana.*
import kotlinx.android.synthetic.main.activity_detail_lp_pidana.*
import kotlinx.android.synthetic.main.activity_edit_lp_pidana.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditLpPidanaActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder
    private lateinit var sipilAlertDialog: View
    private var lpPidanaReq = LpPidanaReq()

    companion object {
        const val EDIT_PIDANA = "EDIT_PIDANA"
        const val REQ_PELAPOR = 202
        const val REQ_TERLAPOR = 101

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_lp_pidana)
        sessionManager = SessionManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Laporan Polisi Pidana"
        val pidana = intent.extras?.getParcelable<LpPidanaResp>(EDIT_PIDANA)
        getViewEditPidana(pidana)

        btn_choose_personel_pelapor_pidana_edit.setOnClickListener {
            val intent = Intent(this, ChoosePersonelActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivityForResult(intent, REQ_PELAPOR)
        }

        btn_choose_personel_terlapor_pidana_edit.setOnClickListener {
            val intent = Intent(this, ChoosePersonelActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivityForResult(intent, REQ_TERLAPOR)
        }



        btn_save_edit_lp_pidana.setOnClickListener {
            updateLpPidana()
        }

        btn_sipil_edit.setOnClickListener {
            materialAlertDialogBuilder =
                MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
            btn_sipil_add.setOnClickListener {
                sipilAlertDialog = LayoutInflater.from(this)
                    .inflate(R.layout.item_sipil_pidana, null, false)
                launchSipilView()
            }
        }


    }

    private fun updateLpPidana() {
        lpPidanaReq.no_lp = edt_no_lp_pidana_edit.text.toString()
        lpPidanaReq.pembukaan_laporan = edt_pembukaan_laporan_pidana_edit.text.toString()
        lpPidanaReq.isi_laporan =edt_isi_laporan_pidana_edit.text.toString()
        lpPidanaReq.kota_buat_laporan = edt_kota_buat_edit_lp.text.toString()
        lpPidanaReq.tanggal_buat_laporan = edt_tgl_buat_edit.text.toString()
        lpPidanaReq.nama_yang_mengetahui = edt_nama_pimpinan_bidang_edit.text.toString()
        lpPidanaReq.pangkat_yang_mengetahui = edt_pangkat_pimpinan_bidang_edit.text.toString()
        lpPidanaReq.nrp_yang_mengetahui = edt_nrp_pimpinan_bidang_edit.text.toString()
        lpPidanaReq.jabatan_yang_mengetahui = edt_jabatan_pimpinan_bidang_edit.text.toString()
        lpPidanaReq.id_personel_operator = sessionManager.fetchUser()?.id

        Log.e("update", "$lpPidanaReq")
    }

    private fun getViewEditPidana(pidana: LpPidanaResp?) {
        //general
        edt_no_lp_pidana_edit.setText(pidana?.no_lp)
        edt_pembukaan_laporan_pidana_edit.setText(pidana?.pembukaan_laporan)
        edt_isi_laporan_pidana_edit.setText(pidana?.pembukaan_laporan)

        //dibuat
        edt_kota_buat_edit_lp.setText(pidana?.kota_buat_laporan)
        edt_tgl_buat_edit.setText(pidana?.tanggal_buat_laporan)

        //pimpinan
        edt_nama_pimpinan_bidang_edit.setText(pidana?.nama_yang_mengetahui)
        edt_pangkat_pimpinan_bidang_edit.setText(pidana?.pangkat_yang_mengetahui)
        edt_nrp_pimpinan_bidang_edit.setText(pidana?.nrp_yang_mengetahui)
        edt_jabatan_pimpinan_bidang_edit.setText(pidana?.jabatan_yang_mengetahui)

        //set radiobutton apakah dia pelapor sipil atau bukan
        if (pidana?.status_pelapor == "sipil") {
            ll_sipil_edit.visible()
            ll_personel_edit.gone()
            rb_sipil_pidana_edit.isChecked = true
            txt_nama_sipil_pidana_lp_edit.text = "Nama :  ${pidana?.nama_pelapor}"
            txt_agama_sipil_pidana_lp_edit.text = "Agama : ${pidana?.agama_pelapor}"
            txt_pekerjaan_sipil_pidana_lp_edit.text = "Pekerjaan : ${pidana?.pekerjaan_pelapor}"
            txt_kwg_sipil_pidana_lp_edit.text =
                "Kewarganegaraan : ${pidana?.kewarganegaraan_pelapor}"
            txt_alamat_sipil_pidana_lp_edit.text = "Alamat : ${pidana?.alamat_pelapor}"
            txt_no_telp_sipil_pidana_lp_edit.text = "No Telepon : ${pidana?.no_telp_pelapor}"
            txt_nik_ktp_sipil_pidana_lp_edit.text = "NIK KTP : ${pidana?.nik_ktp_pelapor}"
        } else {
            ll_sipil_edit.gone()
            ll_personel_edit.visible()
            rb_polisi_pidana_edit.isChecked = true
            txt_nama_pelapor_pidana_lp_edit.text =
                "Nama : ${pidana?.id_personel_pelapor.toString()}"
            txt_pangkat_pelapor_pidana_lp_edit.text =
                "Pangkat : ${pidana?.id_personel_pelapor.toString()}"
            txt_nrp_pelapor_pidana_lp_edit.text = "NRP :  ${pidana?.id_personel_pelapor.toString()}"
            txt_jabatan_pelapor_pidana_lp_edit.text =
                "Jabatan :  ${pidana?.id_personel_pelapor.toString()}"
            txt_kesatuan_pelapor_pidana_lp_edit.text =
                "Kesatuan :  ${pidana?.id_personel_pelapor.toString()}"
        }
        rg_pelapor_edit.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            if (radio.isChecked) {
                lpPidanaReq.pelapor = radio.text.toString().toLowerCase()
                Log.e("radio detail", "${radio.text}")
                if (radio.text.toString().toLowerCase() == "sipil") {
                    ll_sipil_edit.visible()
                    ll_personel_edit.gone()
                } else {
                    ll_sipil_edit.gone()
                    ll_personel_edit.visible()
                }
            }

            //personel terlapor
            txt_nama_terlapor_lp_edit.text = "Nama : ${pidana?.id_personel_terlapor.toString()}"
            txt_pangkat_terlapor_lp_edit.text =
                "Pangkat : ${pidana?.id_personel_terlapor.toString()}"
            txt_nrp_terlapor_lp_edit.text = "NRP : ${pidana?.id_personel_terlapor.toString()}"
            txt_jabatan_terlapor_lp_edit.text =
                "Jabatan : ${pidana?.id_personel_terlapor.toString()}"
            txt_kesatuan_terlapor_lp_edit.text =
                "Kesatuan : ${pidana?.id_personel_terlapor.toString()}"
        }
    }
        private fun launchSipilView() {
            val spAgama =
                sipilAlertDialog.findViewById<AutoCompleteTextView>(R.id.spinner_agama_sipil)
            val namaSipil = sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_nama_sipil)
            val pekerjaanSipil =
                sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_pekerjaan_sipil)
            val kwgSipil = sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_kwg_sipil)
            val alamatSipil =
                sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_alamat_sipil)
            val noTelpSipil =
                sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_no_telp_sipil)
            val nikSipil = sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_nik_sipil)

            val ll = sipilAlertDialog.findViewById<LinearLayout>(R.id.ll_add_sipil)
            val pb = sipilAlertDialog.findViewById<RelativeLayout>(R.id.rl_pb)

            //NetworkConfig().getService().
            //add Sipil
            //muncul pb
            //jika sudah berhasil menambahkan maka muncul id sipil_terlapor dan pb hilang
            //jika gagal maka logcat error ada error

            val agamaItem =
                listOf("Islam", "Katolik", "Protestan", "Budha", "Hindu", "Khonghucu")
            val adapterAgama = ArrayAdapter(this, R.layout.item_spinner, agamaItem)
            spAgama.setAdapter(adapterAgama)
            spAgama.setOnItemClickListener { parent, view, position, id ->
                when (position) {
                    0 -> {
                        lpPidanaReq.agama_pelapor = "islam"
                        txt_agama_sipil_pidana_lp_add.text = "Islam"
                    }
                    1 -> {
                        lpPidanaReq.agama_pelapor = "katolik"
                        txt_agama_sipil_pidana_lp_add.text = "Katolik"
                    }
                    2 -> {
                        lpPidanaReq.agama_pelapor = "protestan"
                        txt_agama_sipil_pidana_lp_add.text = "Protestan"
                    }
                    3 -> {
                        lpPidanaReq.agama_pelapor = "buddha"
                        txt_agama_sipil_pidana_lp_add.text = "Buddha"
                    }
                    4 -> {
                        lpPidanaReq.agama_pelapor = "hindu"
                        txt_agama_sipil_pidana_lp_add.text = "Hindu"
                    }
                    5 -> {
                        lpPidanaReq.agama_pelapor = "konghuchu"
                        txt_agama_sipil_pidana_lp_add.text = "Konghuchu"
                    }
                }
            }
            materialAlertDialogBuilder.setView(sipilAlertDialog)
                .setTitle("Tambah Data Sipil")
//            .setMessage("Masukkan Data Sipil")
                .setPositiveButton("Tambah") { dialog, _ ->
                    lpPidanaReq.nama_pelapor = namaSipil.text.toString()
                    txt_nama_sipil_pidana_lp_add.text = namaSipil.text.toString()

                    lpPidanaReq.pekerjaan_pelapor = pekerjaanSipil.text.toString()
                    txt_pekerjaan_sipil_pidana_lp_add.text = pekerjaanSipil.text.toString()

                    lpPidanaReq.kewarganegaraan_pelapor = kwgSipil.text.toString()
                    txt_kwg_sipil_pidana_lp_add.text = kwgSipil.text.toString()

                    lpPidanaReq.alamat_pelapor = alamatSipil.text.toString()
                    txt_alamat_sipil_pidana_lp_add.text = alamatSipil.text.toString()

                    lpPidanaReq.no_telp_pelapor = noTelpSipil.text.toString()
                    txt_no_telp_sipil_pidana_lp_add.text = noTelpSipil.text.toString()

                    lpPidanaReq.nik_pelapor = nikSipil.text.toString()
                    txt_nik_ktp_sipil_pidana_lp_add.text = nikSipil.text.toString()

//                dialog.dismiss()

                }
                .setNegativeButton("Batal") { dialog, _ ->
//                displayMessage("Operation cancelled!")
//                dialog.dismiss()
                }
                .show()

        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            val personel = data?.getParcelableExtra<PersonelModel>("ID_PERSONEL")
            when (resultCode) {
                Activity.RESULT_OK -> {
                    when (requestCode) {
                        REQ_TERLAPOR -> {
//                            personel?.id?.let { sessionManager.setIDPersonelTerlapor(it) }
                            lpPidanaReq.id_personel_terlapor = personel?.id
                            txt_nama_terlapor_lp_edit.text = personel?.nama
                            txt_pangkat_terlapor_lp_edit.text = personel?.pangkat
                            txt_nrp_terlapor_lp_edit.text = personel?.nrp
                            txt_jabatan_terlapor_lp_edit.text = personel?.jabatan
                            txt_kesatuan_terlapor_lp_edit.text = personel?.satuan_kerja?.kesatuan
                        }
                        REQ_PELAPOR -> {
//                            personel?.id?.let { sessionManager.setIDPersonelPelapor(it) }
                            lpPidanaReq.id_personel_pelapor = personel?.id
                            txt_nama_pelapor_pidana_lp_edit.text = personel?.nama
                            txt_pangkat_pelapor_pidana_lp_edit.text = personel?.pangkat
                            txt_nrp_pelapor_pidana_lp_edit.text = personel?.nrp
                            txt_jabatan_pelapor_pidana_lp_edit.text = personel?.jabatan
                            txt_kesatuan_pelapor_pidana_lp_edit.text =
                                personel?.satuan_kerja?.kesatuan
                        }
                    }
                }
            }
        }
    }