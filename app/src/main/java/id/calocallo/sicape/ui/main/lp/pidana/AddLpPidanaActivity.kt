package id.calocallo.sicape.ui.main.lp.pidana

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AllPersonelModel
import id.calocallo.sicape.network.request.SipilPelaporReq
import id.calocallo.sicape.network.response.PersonelMinResp
import id.calocallo.sicape.ui.main.lp.pasal.PickPasalActivity
import id.calocallo.sicape.ui.main.lp.pasal.PickPasalActivity.Companion.ID_PELAPOR
import id.calocallo.sicape.ui.main.lp.pasal.PickPasalActivity.Companion.SIPIL
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.formatterTanggal
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.ui.main.choose.ChoosePersonelActivity
import kotlinx.android.synthetic.main.activity_add_lp_pidana.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddLpPidanaActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private lateinit var materialAlertDialogBuilder: MaterialAlertDialogBuilder
    private lateinit var sipilAlertDialog: View
    private var sipilPelaporReq = SipilPelaporReq()
    private var idPelapor: Int? = null
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
        setContentView(R.layout.activity_add_lp_pidana)
        sessionManager1 = SessionManager1(this)
        val jenis = intent.extras?.getString(JENIS_PIDANA)
        setupActionBarWithBackButton(toolbar)
//        supportActionBar?.title = "Tambah Data Laporan Polisi"
        when (jenis) {
            "pidana" -> supportActionBar?.title = "Tambah Data Laporan Polisi Pidana"
            "kode_etik" -> supportActionBar?.title = "Tambah Data Laporan Polisi Kode Etik"
            "disiplin" -> supportActionBar?.title = "Tambah Data Laporan Polisi Disiplin"
        }

        txt_title_pembukaan_laporan_pidana_add.gone()
        txt_layout_pembukaan_laporan_pidana_add.gone()

        btn_choose_personel_pelapor_lp_add_pidana.setOnClickListener {
            var intent = Intent()
            val hak = sessionManager1.fetchHakAkses()
            if(hak == "operator"){
                intent = Intent(this, ChoosePersonelActivity::class.java)
            }else {
                intent = Intent(this, KatPersonelActivity::class.java)
            }
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, REQ_PELAPOR)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        var _jenisPelapor : String? = null
        rg_pidana.setOnCheckedChangeListener { group, checkedId ->
            val radio :RadioButton = findViewById(checkedId)
            if(radio.isChecked && radio.text == "Sipil"){
                ll_personel.gone()
                ll_sipil.visible()
                _jenisPelapor = radio.text.toString().toLowerCase()
            }else{
                ll_personel.visible()
                ll_sipil.gone()
                _jenisPelapor = radio.text.toString().toLowerCase()
                sipilPelaporReq.nama_sipil = null
                sipilPelaporReq.agama_sipil = null
                sipilPelaporReq.jenis_kelamin = null
                sipilPelaporReq.alamat_sipil = null
                sipilPelaporReq.kewarganegaraan_sipil = null
                sipilPelaporReq.nik_sipil = null
                sipilPelaporReq.no_telp_sipil = null
                sipilPelaporReq.pekerjaan_sipil = null
                sipilPelaporReq.tempat_lahir_pelapor = null
                sipilPelaporReq.tanggal_lahir_pelapor = null
            }
        }

        btn_next_lp_pidana.setOnClickListener {
            _jenisPelapor?.let { it1 -> sessionManager1.setJenisPelapor(it1) }
            idPelapor?.let { it1 -> sessionManager1.setIDPersonelPelapor(it1) }
            sessionManager1.setIsiLapLP(edt_isi_laporan_pidana.text.toString())
            sessionManager1.setUraianPelanggaranLP(edt_uraian_pelanggaran_pidana.text.toString())
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
            if(idPelapor == null || idPelapor == 0){
                intent.putExtra(SIPIL, sipilPelaporReq)
            }else{
                intent.putExtra(ID_PELAPOR, idPelapor!!)

            }
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
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
        val jkSipilView = sipilAlertDialog.findViewById<AutoCompleteTextView>(R.id.spinner_jk_sipil)
        val alamatSipilView =
            sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_alamat_sipil)
        val noTelpSipilView =
            sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_no_telp_sipil)
        val nikSipilView = sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_nik_sipil)

        val tempatLahirSipilView = sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_tempat_lahir_sipil)
        val tglLahirSipilView = sipilAlertDialog.findViewById<TextInputEditText>(R.id.edt_tanggal_lahir_sipil)

        val agamaItem =
            listOf("Islam", "Katolik", "Protestan", "Buddha", "Hindu", "Khonghucu")
        val adapterAgama = ArrayAdapter(this, R.layout.item_spinner, agamaItem)
        spAgama.setAdapter(adapterAgama)
        spAgama.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> {
                    agamaSipil = "islam"
                    txt_agama_sipil_pidana_lp_add.text = "Agama : Islam"
                }
                1 -> {
                    agamaSipil = "katolik"
                    txt_agama_sipil_pidana_lp_add.text = "Agama : Katolik"
                }
                2 -> {
                    agamaSipil="protestan"
                    sipilPelaporReq.agama_sipil = "protestan"
                    txt_agama_sipil_pidana_lp_add.text = "Agama : Protestan"
                }
                3 -> {
                    agamaSipil="buddha"
                    txt_agama_sipil_pidana_lp_add.text = "Agama : Buddha"
                }
                4 -> {
                    agamaSipil="hindu"
                    txt_agama_sipil_pidana_lp_add.text = "Agama : Hindu"
                }
                5 -> {
                    agamaSipil="konghuchu"
                    txt_agama_sipil_pidana_lp_add.text = "Agama : Konghuchu"
                }
            }
        }

        val jkItem = listOf("Laki-Laki", "Perempuan")
        val adapterJk = ArrayAdapter(this, R.layout.item_spinner, jkItem)
        jkSipilView.setAdapter(adapterJk)
        jkSipilView.setOnItemClickListener { parent, _, position, _ ->
            txt_jk_sipil_pidana_lp_add.text ="Jenis Kelamin : ${parent.getItemAtPosition(position).toString()}"
            when(position){
                0->jkSipil = "laki_laki"
                1->jkSipil = "perempuan"
            }
        }

        materialAlertDialogBuilder.setView(sipilAlertDialog)
            .setTitle("Tambah Data Sipil")
//            .setMessage("Masukkan Data Sipil")
            .setPositiveButton("Tambah") { _, _ ->
                namaSipil = namaSipilView.text.toString()
                txt_nama_sipil_pidana_lp_add.text ="Nama : ${namaSipilView.text.toString()}"

                tmptSipil = tempatLahirSipilView.text.toString()
                tglSipil = tglLahirSipilView.text.toString()
                txt_ttl_sipil_pidana_lp_add.text = "TTL : ${tmptSipil}, ${formatterTanggal(tglSipil)}"

                pekerjaanSipil = pekerjaanSipilView.text.toString()
                txt_pekerjaan_sipil_pidana_lp_add.text ="Pekerjaan : ${pekerjaanSipilView.text.toString()}"

                kwgSipil = kwgSipilView.text.toString()
                txt_kwg_sipil_pidana_lp_add.text ="Kewarganegaraan : ${kwgSipilView.text.toString()}"

                alamatSipil = alamatSipilView.text.toString()
                txt_alamat_sipil_pidana_lp_add.text ="Alamat : ${alamatSipilView.text.toString()}"

                notelpSipil = noTelpSipilView.text.toString()
                txt_no_telp_sipil_pidana_lp_add.text ="No Telp : ${noTelpSipilView.text.toString()}"

                nikSipil = nikSipilView.text.toString()
                txt_nik_ktp_sipil_pidana_lp_add.text ="NIK KTP : ${nikSipilView.text.toString()}"

//                dialog.dismiss()

            }
            .setNegativeButton("Batal") { _, _ ->
//                displayMessage("Operation cancelled!")
//                dialog.dismiss()
            }
            .show()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val personel = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
        if(resultCode == 123 || resultCode == Activity.RESULT_OK){
                when (requestCode) {
                    REQ_PELAPOR -> {
                        idPelapor = personel?.id
//                        personel?.id?.let { sessionManager.setIDPersonelPelapor(it) }
                        txt_nama_pelapor_pidana_lp_add.text ="Nama : ${personel?.nama}"
                        txt_pangkat_pelapor_pidana_lp_add.text ="Pangkat : ${personel?.pangkat.toString().toUpperCase()}"
                        txt_nrp_pelapor_pidana_lp_add.text ="NRP : ${personel?.nrp}"
                        txt_jabatan_pelapor_pidana_lp_add.text ="Jabatan : ${personel?.jabatan}"
                        txt_kesatuan_pelapor_pidana_lp_add.text ="Kesatuan : ${personel?.satuan_kerja?.kesatuan}"
                    }
                }
        }
    }

    companion object {
        const val JENIS_PIDANA = "JENIS_PIDANA"
        const val REQ_PELAPOR = 202
    }
}