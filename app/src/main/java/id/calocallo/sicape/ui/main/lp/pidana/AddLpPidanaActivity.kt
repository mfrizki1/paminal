package id.calocallo.sicape.ui.main.lp.pidana

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.request.SipilPelaporReq
import id.calocallo.sicape.ui.main.choose.ChoosePersonelActivity
import id.calocallo.sicape.ui.main.lp.pasal.PickPasalActivity
import id.calocallo.sicape.ui.main.lp.pasal.PickPasalActivity.Companion.ID_PELAPOR
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_lp_pidana.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddLpPidanaActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder
    private lateinit var sipilAlertDialog: View
    private var sipilPelaporReq = SipilPelaporReq()
    private var idPelapor: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lp_pidana)
        sessionManager = SessionManager(this)
        val jenis = intent.extras?.getString(JENIS_PIDANA)
        setupActionBarWithBackButton(toolbar)
//        supportActionBar?.title = "Tambah Data Laporan Polisi"
        when (jenis) {
            "pidana" -> supportActionBar?.title = "Tambah Data Laporan Polisi Pidana"
            "kode_etik" -> supportActionBar?.title = "Tambah Data Laporan Polisi Kode Etik"
            "disiplin" -> supportActionBar?.title = "Tambah Data Laporan Polisi Disiplin"
        }
        btn_choose_personel_pelapor_lp_add_pidana.setOnClickListener {
            val intent = Intent(this, ChoosePersonelActivity::class.java)
            startActivityForResult(intent, REQ_PELAPOR)
        }

        btn_next_lp_pidana.setOnClickListener {
            sessionManager.setPembukaanLapLP(edt_pembukaan_laporan_pidana.text.toString())
            sessionManager.setIsiLapLP(edt_isi_laporan_pidana.text.toString())
            val intent = Intent(this, PickPasalActivity::class.java)
            intent.putExtra(ID_PELAPOR, idPelapor)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent)
        }
        rg_pidana.setOnCheckedChangeListener { group, checkedId ->
            val radio = findViewById<RadioButton>(checkedId)
            if (radio.isChecked) sessionManager.setPelapor(radio.text.toString().toLowerCase())
            if (radio.text == "Polisi") {
                ll_personel.visible()
                ll_sipil.gone()
            } else {
                ll_personel.gone()
                ll_sipil.visible()

            }
        }

        //sipil view
        materialAlertDialogBuilder = MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
        btn_sipil_add.setOnClickListener {
            sipilAlertDialog = LayoutInflater.from(this)
                .inflate(R.layout.item_sipil_pidana, null, false)
            launchSipilView()
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
                    sessionManager.setAgamaPelapor("islam")
                    txt_agama_sipil_pidana_lp_add.text = "Islam"
                }
                1 -> {
                    sessionManager.setAgamaPelapor("katolik")
                    txt_agama_sipil_pidana_lp_add.text = "Katolik"
                }
                2 -> {
                    sessionManager.setAgamaPelapor("protestan")
                    sipilPelaporReq.agama_sipil = "protestan"
                    txt_agama_sipil_pidana_lp_add.text = "Protestan"
                }
                3 -> {
                    sessionManager.setAgamaPelapor("buddha")
                    txt_agama_sipil_pidana_lp_add.text = "Buddha"
                }
                4 -> {
                    sessionManager.setAgamaPelapor("hindu")
                    txt_agama_sipil_pidana_lp_add.text = "Hindu"
                }
                5 -> {
                    sessionManager.setAgamaPelapor("konghuchu")
                    txt_agama_sipil_pidana_lp_add.text = "Konghuchu"
                }
            }
        }
        materialAlertDialogBuilder.setView(sipilAlertDialog)
            .setTitle("Tambah Data Sipil")
//            .setMessage("Masukkan Data Sipil")
            .setPositiveButton("Tambah") { dialog, _ ->
                sessionManager.setNamaPelapor(namaSipil.text.toString())
                txt_nama_sipil_pidana_lp_add.text = namaSipil.text.toString()

                sessionManager.setPekerjaanPelapor(pekerjaanSipil.text.toString())
                txt_pekerjaan_sipil_pidana_lp_add.text = pekerjaanSipil.text.toString()

                sessionManager.setKwgPelapor(kwgSipil.text.toString())
                txt_kwg_sipil_pidana_lp_add.text = kwgSipil.text.toString()

                sessionManager.setAlamatPelapor(alamatSipil.text.toString())
                txt_alamat_sipil_pidana_lp_add.text = alamatSipil.text.toString()

                sessionManager.setNoTelpPelapor(noTelpSipil.text.toString())
                txt_no_telp_sipil_pidana_lp_add.text = noTelpSipil.text.toString()

                sessionManager.setNIKPelapor(nikSipil.text.toString())
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
            RESULT_OK ->
                when (requestCode) {
                    REQ_PELAPOR -> {
                        idPelapor = personel?.id
//                        personel?.id?.let { sessionManager.setIDPersonelPelapor(it) }
                        txt_nama_pelapor_pidana_lp_add.text = personel?.nama
                        txt_pangkat_pelapor_pidana_lp_add.text = personel?.pangkat
                        txt_nrp_pelapor_pidana_lp_add.text = personel?.nrp
                        txt_jabatan_pelapor_pidana_lp_add.text = personel?.jabatan
                        txt_kesatuan_pelapor_pidana_lp_add.text = personel?.satuan_kerja?.kesatuan
                    }
                }
        }
    }

    companion object {
        const val JENIS_PIDANA = "JENIS_PIDANA"
        const val REQ_PELAPOR = 202
    }
}