package id.calocallo.sicape.ui.main.skhp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.RadioButton
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AllPersonelModel
import id.calocallo.sicape.network.request.SkhpReq
import id.calocallo.sicape.network.response.SkhpResp
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.ui.main.skhd.tinddisiplin.AddTindDisiplinSkhdActivity
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_skhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import java.time.format.DateTimeFormatter
import java.util.*

class EditSkhpActivity : BaseActivity() {
    companion object {
        const val EDIT_SKHP = "EDIT_SKHP"
    }

    private var skhpReq = SkhpReq()
    private var idPersonel: Int? = null
    private var tempSttsCatPelanggaran: Int? = null
    private var isPidana: Int? = null
    private var isKke: Int? = null
    private var isDisiplin: Int? = null
    private var kotaSkhp: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_skhp)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data SKHP"

        val getDetailSkhp = intent.extras?.getParcelable<SkhpResp>(EDIT_SKHP)
        getViewSkhpEdit(getDetailSkhp)
        btn_save_skhp_edit.setOnClickListener {
            updateSkhp(getDetailSkhp)
        }

        rg_memiliki_pidana_edit.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            isPidana = if (radio.isChecked && radio.text == "Memiliki") {
                1
            } else {
                0
            }
        }
        rg_memiliki_kke_edit.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            isKke = if (radio.isChecked && radio.text == "Memiliki") {
                1
            } else {
                0
            }
        }
        rg_memiliki_disiplin_edit.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            isDisiplin = if (radio.isChecked && radio.text == "Memiliki") {
                1
            } else {
                0
            }
        }
        rg_status_cat_pelanggaran_skhp_edit.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            tempSttsCatPelanggaran = if (radio.isChecked && radio.text == "Selesai") {
                1
            } else {
                0
            }

        }
        btn_choose_personel_skhp_edit.setOnClickListener {
            val intent = Intent(this, KatPersonelActivity::class.java)
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, AddTindDisiplinSkhdActivity.REQ_PERSONEL_TIND)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getViewSkhpEdit(detailSkhp: SkhpResp?) {
        edt_no_skhp_edit.setText(detailSkhp?.no_skhp)
        edt_isi_skhp_edit.setText(detailSkhp?.hasil_keputusan)
        edt_kota_keluar_skhp_edit.setText(detailSkhp?.kota_keluar)
        edt_tanggal_keluar_skhp_edit.setText(detailSkhp?.tanggal_keluar)
        edt_nama_pimpinan_skhp_edit.setText(detailSkhp?.nama_yang_mengeluarkan)
        edt_pangkat_pimpinan_skhp_edit.setText(detailSkhp?.pangkat_yang_mengeluarkan)
        edt_nrp_pimpinan_skhp_edit.setText(detailSkhp?.nrp_yang_mengeluarkan)
        edt_jabatan_pimpinan_skhp_edit.setText(detailSkhp?.jabatan_yang_mengeluarkan)
        edt_kepada_skhp_edit.setText(detailSkhp?.kepada)
        txt_nama_personel_skhp_edit.text = "Nama : ${detailSkhp?.personel?.nama}"
        txt_pangkat_personel_skhp_edit.text =
            "Pangkat ${detailSkhp?.personel?.pangkat.toString().toUpperCase()}: "
        txt_nrp_personel_skhp_edit.text = "NRP : ${detailSkhp?.personel?.nrp}"
        txt_jabatan_personel_skhp_edit.text = "Jabatan : ${detailSkhp?.personel?.jabatan}"
        txt_kesatuan_personel_skhp_edit.text =
            "Kesatuan : ${detailSkhp?.personel?.kesatuan.toString().toUpperCase()}"
        if (detailSkhp?.is_status_selesai == 1) {
            rb_status_selesai_skhp_edit.isChecked = true
        } else {
            rb_status_tidak_skhp_edit.isChecked = true

        }
        if (detailSkhp?.is_memiliki_pelanggaran_pidana == 1) {
            rb_have_pidana_skhp.isChecked = true
            ll_status_pelanggaran_skhp_edit.visible()
        } else {
            rb_have_not_pidana_skhp.isChecked = true
            ll_status_pelanggaran_skhp_edit.gone()
        }

        if (detailSkhp?.is_memiliki_pelanggaran_kode_etik == 1) {
            rb_have_kke_skhp.isChecked = true
            ll_status_pelanggaran_skhp_edit.visible()
        } else {
            rb_have_not_kke_skhp.isChecked = true
            ll_status_pelanggaran_skhp_edit.gone()
        }
        if (detailSkhp?.is_memiliki_pelanggaran_disiplin == 1) {
            rb_have_disiplin_skhp.isChecked = true
            ll_status_pelanggaran_skhp_edit.visible()
        } else {
            rb_have_not_disiplin_skhp.isChecked = true
            ll_status_pelanggaran_skhp_edit.gone()
        }


        tempSttsCatPelanggaran = detailSkhp?.is_status_selesai
        isPidana = detailSkhp?.is_memiliki_pelanggaran_pidana
        isKke = detailSkhp?.is_memiliki_pelanggaran_kode_etik
        isDisiplin = detailSkhp?.is_memiliki_pelanggaran_disiplin
        idPersonel = detailSkhp?.personel?.id

        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val date = formatter.parse(detailSkhp?.personel?.tanggal_lahir)
        val desiredFormat =
            DateTimeFormatter.ofPattern("dd, MMM yyyy", Locale("id", "ID")).format(date)

        txt_ttl_personel_skhp_edit.text = "TTL :${detailSkhp?.personel?.tempat_lahir.toString()
            .toUpperCase()}, $desiredFormat"

        when (detailSkhp?.personel?.jenis_kelamin) {
            "laki_laki" -> {
                txt_jk_personel_skhp_edit.text = "Jenis Kelamin : Laki-Laki"
            }
            "perempuan" -> {
                txt_jk_personel_skhp_edit.text = "Jenis Kelamin : Perempuan"
            }
        }
        when (detailSkhp?.personel?.agama_sekarang) {
            "islam" -> txt_agaman_personel_skhp_edit.text = "Agama : Islam"
            "buddha" -> txt_agaman_personel_skhp_edit.text = "Agama : Buddha"
            "katolik" -> txt_agaman_personel_skhp_edit.text = "Agama : Katolik"
            "hindu" -> txt_agaman_personel_skhp_edit.text = "Agama : Hindu"
            "protestan" -> txt_agaman_personel_skhp_edit.text = "Agama : Protestan"
            "konghuchu" -> txt_agaman_personel_skhp_edit.text = "Agama : Konghuchu"
        }
        var listKotaSkhp = listOf("Banjarmasin", "Banjarbaru")
        val adapterKota = ArrayAdapter(this, R.layout.item_spinner, listKotaSkhp)
        edt_kota_keluar_skhp_edit.setAdapter(adapterKota)
        edt_kota_keluar_skhp_edit.setOnItemClickListener { parent, view, position, id ->
            kotaSkhp = parent.getItemAtPosition(position).toString()
        }
    }

    private fun updateSkhp(detailSkhp: SkhpResp?) {

        skhpReq.is_memiliki_pelanggaran_pidana = isPidana
        skhpReq.is_memiliki_pelanggaran_kode_etik = isKke
        skhpReq.is_memiliki_pelanggaran_disiplin = isDisiplin
        skhpReq.is_status_selesai = tempSttsCatPelanggaran
        skhpReq.id_personel = idPersonel

        skhpReq.no_skhp = edt_no_skhp_edit.text.toString()
        skhpReq.hasil_keputusan = edt_isi_skhp_edit.text.toString()
        skhpReq.kota_keluar =kotaSkhp
        skhpReq.tanggal_keluar = edt_tanggal_keluar_skhp_edit.text.toString()
        skhpReq.nama_yang_mengeluarkan = edt_nama_pimpinan_skhp_edit.text.toString()
        skhpReq.pangkat_yang_mengeluarkan =
            edt_pangkat_pimpinan_skhp_edit.text.toString().toUpperCase()
        skhpReq.nrp_yang_mengeluarkan = edt_nrp_pimpinan_skhp_edit.text.toString()
        skhpReq.jabatan_yang_mengeluarkan = edt_jabatan_pimpinan_skhp_edit.text.toString()
        skhpReq.kepada = edt_kepada_skhp_edit.text.toString()

        Log.e("edit Skhp", "$skhpReq")
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AddTindDisiplinSkhdActivity.REQ_PERSONEL_TIND) {
            if (resultCode == Activity.RESULT_OK) {
                val personel = data?.getParcelableExtra<AllPersonelModel>("ID_PERSONEL")
                idPersonel = personel?.id
                txt_nama_personel_skhp_edit.text = "Nama : ${personel?.nama}"
                txt_pangkat_personel_skhp_edit.text =
                    "Pangkat ${personel?.pangkat.toString().toUpperCase()}: "
                txt_nrp_personel_skhp_edit.text = "NRP : ${personel?.nrp}"
                txt_jabatan_personel_skhp_edit.text = "Jabatan : ${personel?.jabatan}"
                txt_kesatuan_personel_skhp_edit.text =
                    "Kesatuan : ${personel?.satuan_kerja?.kesatuan.toString().toUpperCase()}"

                val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                val date = formatter.parse(personel?.tanggal_lahir)
                val desiredFormat =
                    DateTimeFormatter.ofPattern("dd, MMM yyyy", Locale("id", "ID")).format(date)

                txt_ttl_personel_skhp_edit.text = "TTL :${personel?.tempat_lahir.toString()
                    .toUpperCase()}, $desiredFormat"

                when (personel?.jenis_kelamin) {
                    "laki_laki" -> {
                        txt_jk_personel_skhp_edit.text = "Jenis Kelamin : Laki-Laki"
                    }
                    "perempuan" -> {
                        txt_jk_personel_skhp_edit.text = "Jenis Kelamin : Perempuan"
                    }
                }
                when (personel?.agama_sekarang) {
                    "islam" -> txt_agaman_personel_skhp_edit.text = "Agama : Islam"
                    "buddha" -> txt_agaman_personel_skhp_edit.text = "Agama : Buddha"
                    "katolik" -> txt_agaman_personel_skhp_edit.text = "Agama : Katolik"
                    "hindu" -> txt_agaman_personel_skhp_edit.text = "Agama : Hindu"
                    "protestan" -> txt_agaman_personel_skhp_edit.text = "Agama : Protestan"
                    "konghuchu" -> txt_agaman_personel_skhp_edit.text = "Agama : Konghuchu"
                }
//                txt_agaman_personel_skhp_edit.text = "Agama : ${personel?.agama_sekarang}"
            }
        }
    }
}