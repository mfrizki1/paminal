package id.calocallo.sicape.ui.main.lp.disiplin

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.RadioButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.SipilPelaporReq
import id.calocallo.sicape.network.response.PersonelMinResp
import id.calocallo.sicape.ui.main.lp.pasal.PickPasalActivity
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.ui.main.choose.ChoosePersonelActivity
import id.calocallo.sicape.utils.ext.formatterTanggal
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import kotlinx.android.synthetic.main.activity_add_lp_disiplin.*
import kotlinx.android.synthetic.main.activity_add_lp_kode_etik.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddLpDisiplinActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var idPelapor: Int? = null
    private var jenisPelapor: String? = null
    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder
    private lateinit var sipilAlertDialog: View
    private var sipilPelaporReq = SipilPelaporReq()
    private var agamaSipil: String? = null
    private var namaSipil: String? = null
    private var tmptSipil: String? = null
    private var tglSipil: String? = null
    private var jkSipil: String? = null
    private var pekerjaanSipil: String? = null
    private var alamatSipil: String? = null
    private var notelpSipil: String? = null
    private var nikSipil: String? = null
    private var kwgSipil: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lp_disiplin)

        sessionManager1 = SessionManager1(this)
        val jenis = intent.extras?.getString(JENIS_DISIPLIN)
        setupActionBarWithBackButton(toolbar)
        when (jenis) {
            "pidana" -> supportActionBar?.title = "Tambah Data Laporan Polisi Pidana"
            "kode_etik" -> supportActionBar?.title = "Tambah Data Laporan Polisi Kode Etik"
            "disiplin" -> supportActionBar?.title = "Tambah Data Laporan Polisi Disiplin"
        }

        //get Id Pelapor(polisi)
        btn_choose_personel_pelapor_lp_add_disiplin.setOnClickListener {
            var intent = Intent()
            val hak = sessionManager1.fetchHakAkses()
            if (hak == "operator") {
                intent = Intent(this, ChoosePersonelActivity::class.java)
            } else {
                intent = Intent(this, KatPersonelActivity::class.java)
            }
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, REQ_PELAPOR)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        rg_disiplin.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            if (radio.isChecked && radio.text == "Sipil") {
                jenisPelapor = "sipil"
                ll_sipil_disiplin.visible()
                ll_personel_disiplin.gone()
            } else {
                jenisPelapor = "personel"
                ll_personel_disiplin.visible()
                ll_sipil_disiplin.gone()
            }
        }
        materialAlertDialogBuilder = MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
        btn_sipil_add_disiplin.setOnClickListener {
            sipilAlertDialog = LayoutInflater.from(this)
                .inflate(R.layout.item_sipil_pidana, null, false)
            launchSipilView()
        }

        //next to Pasal
        btn_next_lp_displin.setOnClickListener {
            jenisPelapor?.let { it1 -> sessionManager1.setJenisPelapor(it1) }
            idPelapor?.let { it1 -> sessionManager1.setIDPersonelPelapor(it1) }
            sessionManager1.setUraianPelanggaranLP(edt_uraian_pelanggaran_disiplin_add.text.toString())
            sessionManager1.setMacamPelanggaranLP(edt_macam_pelanggaran_disiplin.text.toString())
            sessionManager1.setKetPelaporLP(edt_ket_pelapor_disiplin.text.toString())
            sessionManager1.setKronologisPelapor(edt_kronologis_pelapor_disiplin.text.toString())
            sessionManager1.setRincianDisiplin(edt_rincian_pelanggaran_disiplin.text.toString())

            sipilPelaporReq.nama_sipil = namaSipil
            sipilPelaporReq.agama_sipil = agamaSipil
            sipilPelaporReq.jenis_kelamin = jkSipil
            sipilPelaporReq.alamat_sipil = alamatSipil
            sipilPelaporReq.kewarganegaraan_sipil = kwgSipil
            sipilPelaporReq.nik_sipil = nikSipil
            sipilPelaporReq.no_telp_sipil = notelpSipil
            sipilPelaporReq.pekerjaan_sipil = pekerjaanSipil
            sipilPelaporReq.tempat_lahir_pelapor = tmptSipil
            sipilPelaporReq.tanggal_lahir_pelapor = tglSipil

            val intent = Intent(this, PickPasalActivity::class.java)
            if (idPelapor == null || idPelapor == 0) {
                intent.putExtra(PickPasalActivity.SIPIL, sipilPelaporReq)
            } else {
                intent.putExtra(PickPasalActivity.ID_PELAPOR, idPelapor!!)

            }
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }


    }

    private fun launchSipilView() {
        val spAgama =
            sipilAlertDialog.findViewById<AutoCompleteTextView>(R.id.spinner_agama_sipil)
        val namaSipilView = sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_nama_sipil)
        val pekerjaanSipilView =
            sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_pekerjaan_sipil)
        val kwgSipilView = sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_kwg_sipil)
        val jkSipilView = sipilAlertDialog.findViewById<AutoCompleteTextView>(R.id.spinner_jk_sipil)
        val alamatSipilView =
            sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_alamat_sipil)
        val noTelpSipilView =
            sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_no_telp_sipil)
        val nikSipilView = sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_nik_sipil)

        val tempatLahirSipilView =
            sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_tempat_lahir_sipil)
        val tglLahirSipilView =
            sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_tanggal_lahir_sipil)

        val agamaItem =
            listOf("Islam", "Katolik", "Protestan", "Buddha", "Hindu", "Khonghucu")
        val adapterAgama = ArrayAdapter(this, R.layout.item_spinner, agamaItem)
        spAgama.setAdapter(adapterAgama)
        spAgama.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> {
                    agamaSipil = "islam"
                    txt_agama_sipil_disiplin_lp_add.text = "Agama : Islam"
                }
                1 -> {
                    agamaSipil = "katolik"
                    txt_agama_sipil_disiplin_lp_add.text = "Agama : Katolik"
                }
                2 -> {
                    agamaSipil = "protestan"
                    sipilPelaporReq.agama_sipil = "protestan"
                    txt_agama_sipil_disiplin_lp_add.text = "Agama : Protestan"
                }
                3 -> {
                    agamaSipil = "buddha"
                    txt_agama_sipil_disiplin_lp_add.text = "Agama : Buddha"
                }
                4 -> {
                    agamaSipil = "hindu"
                    txt_agama_sipil_disiplin_lp_add.text = "Agama : Hindu"
                }
                5 -> {
                    agamaSipil = "konghuchu"
                    txt_agama_sipil_disiplin_lp_add.text = "Agama : Konghuchu"
                }
            }
        }

        val jkItem = listOf("Laki-Laki", "Perempuan")
        val adapterJk = ArrayAdapter(this, R.layout.item_spinner, jkItem)
        jkSipilView.setAdapter(adapterJk)
        jkSipilView.setOnItemClickListener { parent, _, position, _ ->
            txt_jk_sipil_disiplin_lp_add.text =
                "Jenis Kelamin : ${parent.getItemAtPosition(position).toString()}"
            when (position) {
                0 -> jkSipil = "laki_laki"
                1 -> jkSipil = "perempuan"
            }
        }

        materialAlertDialogBuilder.setView(sipilAlertDialog)
            .setTitle("Tambah Data Sipil")
//            .setMessage("Masukkan Data Sipil")
            .setPositiveButton("Tambah") { _, _ ->
                namaSipil = namaSipilView.text.toString()
                txt_nama_sipil_disiplin_lp_add.text = "Nama : ${namaSipilView.text.toString()}"

                tmptSipil = tempatLahirSipilView.text.toString()
                tglSipil = tglLahirSipilView.text.toString()
                txt_ttl_sipil_disiplin_lp_add.text =
                    "TTL : ${tmptSipil}, ${formatterTanggal(tglSipil)}"

                pekerjaanSipil = pekerjaanSipilView.text.toString()
                txt_pekerjaan_sipil_disiplin_lp_add.text =
                    "Pekerjaan : ${pekerjaanSipilView.text.toString()}"

                kwgSipil = kwgSipilView.text.toString()
                txt_kwg_sipil_disiplin_lp_add.text =
                    "Kewarganegaraan : ${kwgSipilView.text.toString()}"

                alamatSipil = alamatSipilView.text.toString()
                txt_alamat_sipil_disiplin_lp_add.text =
                    "Alamat : ${alamatSipilView.text.toString()}"

                notelpSipil = noTelpSipilView.text.toString()
                txt_no_telp_sipil_disiplin_lp_add.text =
                    "No Telp : ${noTelpSipilView.text.toString()}"

                nikSipil = nikSipilView.text.toString()
                txt_nik_ktp_sipil_disiplin_lp_add.text = "NIK KTP : ${nikSipilView.text.toString()}"

//                dialog.dismiss()

            }
            .setNegativeButton("Batal") { _, _ ->
//                displayMessage("Operation cancelled!")
//                dialog.dismiss()
            }
            .show()

    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val personel = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
        if (resultCode == 123 || resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQ_PELAPOR -> {
                    idPelapor = personel?.id
                    txt_nama_pelapor_disiplin_lp_add.text = "Nama : ${personel?.nama}"
                    txt_pangkat_pelapor_disiplin_lp_add.text =
                        "Pangkat : ${personel?.pangkat.toString().toUpperCase()}"
                    txt_nrp_pelapor_disiplin_lp_add.text = "NRP : ${personel?.nrp}"
                    txt_jabatan_pelapor_disiplin_lp_add.text = "Jabatan : ${personel?.jabatan}"
                    txt_kesatuan_pelapor_disiplin_lp_add.text =
                        "Kesatuan : ${personel?.satuan_kerja?.kesatuan}"
                }

            }
        }
    }

    companion object {
        const val JENIS_DISIPLIN = "JENIS_DISIPLIN"
        const val REQ_PELAPOR = 202
    }
}