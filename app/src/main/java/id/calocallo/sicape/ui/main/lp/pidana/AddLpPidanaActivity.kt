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
import id.calocallo.sicape.ui.main.lp.pasal.PickPasalActivity.Companion.SIPIL
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
    private var agamaSipil: String? = null
    private var namaSipil: String? = null
    private var pekerjaanSipil: String? = null
    private var alamatSipil: String? = null
    private var notelpSipil: String? = null
    private var nikSipil: String? = null
    private var kwgSipil: String? = null

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
            sessionManager.setUraianPelanggaranLP(edt_uraian_pelanggaran_pidana.text.toString())
            sipilPelaporReq.nama_sipil = namaSipil
            sipilPelaporReq.agama_sipil = agamaSipil
            sipilPelaporReq.alamat_sipil = alamatSipil
            sipilPelaporReq.kewarganegaraan_sipil = kwgSipil
            sipilPelaporReq.nik_sipil = nikSipil
            sipilPelaporReq.no_telp_sipil = notelpSipil
            sipilPelaporReq.pekerjaan_sipil = pekerjaanSipil
            val intent = Intent(this, PickPasalActivity::class.java)
            if(idPelapor == null || idPelapor == 0){
                intent.putExtra(SIPIL, sipilPelaporReq)
            }else{
                intent.putExtra(ID_PELAPOR, idPelapor!!)

            }
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
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
        val namaSipilView = sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_nama_sipil)
        val pekerjaanSipilView =
            sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_pekerjaan_sipil)
        val kwgSipilView = sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_kwg_sipil)
        val alamatSipilView =
            sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_alamat_sipil)
        val noTelpSipilView =
            sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_no_telp_sipil)
        val nikSipilView = sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_nik_sipil)

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
                    agamaSipil = "islam"
                    txt_agama_sipil_pidana_lp_add.text = "Islam"
                }
                1 -> {
                    agamaSipil = "katolik"
                    txt_agama_sipil_pidana_lp_add.text = "Katolik"
                }
                2 -> {
                    agamaSipil="protestan"
                    sipilPelaporReq.agama_sipil = "protestan"
                    txt_agama_sipil_pidana_lp_add.text = "Protestan"
                }
                3 -> {
                    agamaSipil="buddha"
                    txt_agama_sipil_pidana_lp_add.text = "Buddha"
                }
                4 -> {
                    agamaSipil="hindu"
                    txt_agama_sipil_pidana_lp_add.text = "Hindu"
                }
                5 -> {
                    agamaSipil="konghuchu"
                    txt_agama_sipil_pidana_lp_add.text = "Konghuchu"
                }
            }
        }
        materialAlertDialogBuilder.setView(sipilAlertDialog)
            .setTitle("Tambah Data Sipil")
//            .setMessage("Masukkan Data Sipil")
            .setPositiveButton("Tambah") { dialog, _ ->
                namaSipil = namaSipilView.text.toString()
                txt_nama_sipil_pidana_lp_add.text = namaSipilView.text.toString()

                pekerjaanSipil = pekerjaanSipilView.text.toString()
                txt_pekerjaan_sipil_pidana_lp_add.text = pekerjaanSipilView.text.toString()

                kwgSipil = kwgSipilView.text.toString()
                txt_kwg_sipil_pidana_lp_add.text = kwgSipilView.text.toString()

                alamatSipil = alamatSipilView.text.toString()
                txt_alamat_sipil_pidana_lp_add.text = alamatSipilView.text.toString()

                notelpSipil = noTelpSipilView.text.toString()
                txt_no_telp_sipil_pidana_lp_add.text = noTelpSipilView.text.toString()

                nikSipil = nikSipilView.text.toString()
                txt_nik_ktp_sipil_pidana_lp_add.text = nikSipilView.text.toString()

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