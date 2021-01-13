package id.calocallo.sicape.ui.main.skhp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.request.SkhpReq
import id.calocallo.sicape.ui.main.choose.ChoosePersonelActivity
import id.calocallo.sicape.ui.main.skhd.tinddisiplin.AddTindDisiplinSkhdActivity
import id.calocallo.sicape.utils.ext.formatterTanggal
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_skhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddSkhpActivity : BaseActivity() {
    private var skhpReq = SkhpReq()
    private var idPersonel: Int? = null
    private var tempSttsCatPelanggaran: Int? = null
    private var isPidana: Int? = null
    private var isKke: Int? = null
    private var isDisiplin: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_skhp)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data SKHP"

        rg_memiliki_pidana.setOnCheckedChangeListener { group, checkedId ->
            val radio :RadioButton = findViewById(checkedId)
            isPidana = if(radio.isChecked && radio.text == "Memiliki"){
                1
            }else{
                0
            }
        }

        rg_memiliki_kke.setOnCheckedChangeListener { group, checkedId ->
            val radio :RadioButton = findViewById(checkedId)
            isKke = if(radio.isChecked && radio.text == "Memiliki"){
                1
            }else{
                0
            }
        }

        rg_memiliki_disiplin.setOnCheckedChangeListener { group, checkedId ->
            val radio :RadioButton = findViewById(checkedId)
            isDisiplin = if(radio.isChecked && radio.text == "Memiliki"){
                1
            }else{
                0
            }
        }
        rg_status_cat_pelanggaran_skhp_add.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            tempSttsCatPelanggaran = if (radio.isChecked && radio.text == "Selesai") {
                1
            } else {
                0
            }

        }
        btn_choose_personel_skhp.setOnClickListener {
            val intent = Intent(this, ChoosePersonelActivity::class.java)
            startActivityForResult(intent, AddTindDisiplinSkhdActivity.REQ_PERSONEL_TIND)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_save_skhp_add.setOnClickListener {
            skhpReq.no_skhp = edt_no_skhp_add.text.toString()
            skhpReq.hasil_keputusan = edt_isi_skhp_add.text.toString()
            skhpReq.kota_keluar = edt_kota_keluar_skhp_add.text.toString()
            skhpReq.tanggal_keluar = edt_tanggal_keluar_skhp_add.text.toString()
            skhpReq.nama_yang_mengeluarkan = edt_nama_pimpinan_skhp_add.text.toString()
            skhpReq.pangkat_yang_mengeluarkan =
                edt_pangkat_pimpinan_skhp_add.text.toString().toUpperCase()
            skhpReq.nrp_yang_mengeluarkan = edt_nrp_pimpinan_skhp_add.text.toString()
            skhpReq.jabatan_yang_mengeluarkan = edt_jabatan_pimpinan_skhp_add.text.toString()
            skhpReq.kepada = edt_kepada_skhp_add.text.toString()
            skhpReq.is_memiliki_pelanggaran_pidana = isPidana
            skhpReq.is_memiliki_pelanggaran_kode_etik = isKke
            skhpReq.is_memiliki_pelanggaran_disiplin = isDisiplin
            skhpReq.is_status_selesai = tempSttsCatPelanggaran
            skhpReq.id_personel = idPersonel
            Log.e("add SKHP", "$skhpReq")
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AddTindDisiplinSkhdActivity.REQ_PERSONEL_TIND) {
            if (resultCode == Activity.RESULT_OK) {
                val personel = data?.getParcelableExtra<PersonelModel>("ID_PERSONEL")
                idPersonel = personel?.id
                txt_nama_personel_skhp_add.text = "Nama : ${personel?.nama}"
                txt_pangkat_personel_skhp_add.text =
                    "Pangkat ${personel?.pangkat.toString().toUpperCase()}: "
                txt_nrp_personel_skhp_add.text = "NRP : ${personel?.nrp}"
                txt_jabatan_personel_skhp_add.text = "Jabatan : ${personel?.jabatan}"
                txt_kesatuan_personel_skhp_add.text =
                    "Kesatuan : ${personel?.satuan_kerja?.kesatuan.toString().toUpperCase()}"

                txt_ttl_personel_skhp_add.text = "TTL :${personel?.tempat_lahir.toString()
                    .toUpperCase()}, ${formatterTanggal(personel?.tanggal_lahir)}"

                when (personel?.jenis_kelamin) {
                    "laki_laki" -> {
                        txt_jk_personel_skhp_add.text = "Jenis Kelamin : Laki-Laki"
                    }
                    "perempuan" -> {
                        txt_jk_personel_skhp_add.text = "Jenis Kelamin : Perempuan"
                    }
                }
                when (personel?.agama_sekarang) {
                    "islam" -> txt_agaman_personel_skhp_add.text = "Agama : Islam"
                    "buddha" -> txt_agaman_personel_skhp_add.text = "Agama : Buddha"
                    "katolik" -> txt_agaman_personel_skhp_add.text = "Agama : Katolik"
                    "hindu" -> txt_agaman_personel_skhp_add.text = "Agama : Hindu"
                    "protestan" -> txt_agaman_personel_skhp_add.text = "Agama : Protestan"
                    "konghuchu" -> txt_agaman_personel_skhp_add.text = "Agama : Konghuchu"
                }
            }
        }
    }
}