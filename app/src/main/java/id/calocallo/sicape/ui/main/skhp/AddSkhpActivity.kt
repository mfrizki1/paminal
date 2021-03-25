package id.calocallo.sicape.ui.main.skhp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.SkhpReq
import id.calocallo.sicape.network.response.AddSkhpResp
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.PersonelMinResp
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.ui.main.skhd.tinddisiplin.AddTindDisiplinSkhdActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.action
import id.calocallo.sicape.utils.ext.showSnackbar
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.utils.ext.toast
import kotlinx.android.synthetic.main.activity_add_skhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddSkhpActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var skhpReq = SkhpReq()
    private var idPersonel: Int? = null
    private var tempSttsCatPelanggaran: Int? = null
    private var isPidana: Int? = null
    private var isKke: Int? = null
    private var isDisiplin: Int? = null
    private var kotaSkhp: String? = null
    private var isSyarat: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_skhp)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data SKHP"
        sessionManager1 = SessionManager1(this)
        radioGroupSkhp()

        btn_choose_personel_skhp_add.setOnClickListener {
            val intent = Intent(this, KatPersonelActivity::class.java)
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, AddTindDisiplinSkhdActivity.REQ_PERSONEL_TIND)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        var listKotaSkhp = listOf("Banjarmasin", "Banjarbaru")
        val adapterKota = ArrayAdapter(this, R.layout.item_spinner, listKotaSkhp)
        edt_kota_keluar_skhp_add.setAdapter(adapterKota)
        edt_kota_keluar_skhp_add.setOnItemClickListener { parent, _, position, _ ->
            kotaSkhp = parent.getItemAtPosition(position).toString()
        }
        btn_save_skhp_add.attachTextChangeAnimator()
        bindProgressButton(btn_save_skhp_add)
        btn_save_skhp_add.setOnClickListener {
            btn_save_skhp_add.showProgress { progressColor = Color.WHITE }
            skhpReq.is_memenuhi_syarat = isSyarat
            skhpReq.tanggal_kenaikan_pangkat = edt_tgl_kenaikan_pangkat_skhp_add.text.toString()
            skhpReq.kota_keluar = kotaSkhp
            skhpReq.tanggal_keluar = edt_tanggal_keluar_skhp_add.text.toString()
            skhpReq.nama_kabid_propam = edt_nama_pimpinan_skhp_add.text.toString()
            skhpReq.nrp_kabid_propam = edt_nrp_pimpinan_skhp_add.text.toString()
            skhpReq.pangkat_kabid_propam = edt_pangkat_pimpinan_skhp_add.text.toString()
            skhpReq.kepada_penerima = edt_kepada_skhp_add.text.toString()
            skhpReq.kota_penerima = edt_kota_penerima_skhp_add.text.toString()
            /*   skhpReq.no_skhp = edt_no_skhp_add.text.toString()
               skhpReq.hasil_keputusan = edt_isi_skhp_add.text.toString()
   //            skhpReq.kota_keluar = edt_kota_keluar_skhp_add.text.toString()
               skhpReq.kota_keluar = kotaSkhp
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
               skhpReq.is_status_selesai = tempSttsCatPelanggaran*/
            skhpReq.id_personel = idPersonel
            Log.e("add SKHP", "$skhpReq")
            apiAddSkhp()
        }
    }

    private fun apiAddSkhp() {
        NetworkConfig().getServSkhp().addSkhp("Bearer ${sessionManager1.fetchAuthToken()}", skhpReq)
            .enqueue(object : Callback<Base1Resp<AddSkhpResp>> {
                override fun onResponse(
                    call: Call<Base1Resp<AddSkhpResp>>,
                    response: Response<Base1Resp<AddSkhpResp>>
                ) {
                    if (response.body()?.message == "Data skhp saved succesfully") {
                        btn_save_skhp_add.hideProgress(R.string.data_saved)
                        btn_save_skhp_add.showSnackbar(R.string.data_saved) {
                            action(R.string.next) {
                                finish()
                            }
                        }

                    } else {
                        toast("${response.body()?.message}")
                        btn_save_skhp_add.hideProgress(R.string.not_save)
                    }
                }

                override fun onFailure(call: Call<Base1Resp<AddSkhpResp>>, t: Throwable) {
                    Toast.makeText(this@AddSkhpActivity, "$t", Toast.LENGTH_SHORT).show()
                    btn_save_skhp_add.hideProgress(R.string.not_save)
                }
            })
    }

    private fun radioGroupSkhp() {
        rg_memenuhi_syarat_skhp_add.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            if (radio.isChecked && radio.text == "Ya") {
                isSyarat = 1
            } else {
                isSyarat = 0
            }
        }
        rg_memiliki_pidana.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            isPidana = if (radio.isChecked && radio.text == "Memiliki") {
                1
            } else {
                0
            }
        }

        rg_memiliki_kke.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            isKke = if (radio.isChecked && radio.text == "Memiliki") {
                1
            } else {
                0
            }
        }

        rg_memiliki_disiplin.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            isDisiplin = if (radio.isChecked && radio.text == "Memiliki") {
                1
            } else {
                0
            }
        }
        rg_status_cat_pelanggaran_skhp_add.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            tempSttsCatPelanggaran = if (radio.isChecked && radio.text == "Selesai") {
                1
            } else {
                0
            }

        }
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AddTindDisiplinSkhdActivity.REQ_PERSONEL_TIND) {
            if (resultCode == Activity.RESULT_OK) {
                val personel = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
                idPersonel = personel?.id
                txt_nama_personel_skhp_add.text = "Nama : ${personel?.nama}"
                txt_pangkat_personel_skhp_add.text =
                    "Pangkat ${personel?.pangkat.toString().toUpperCase()}: "
                txt_nrp_personel_skhp_add.text = "NRP : ${personel?.nrp}"
                txt_jabatan_personel_skhp_add.text = "Jabatan : ${personel?.jabatan}"
                txt_kesatuan_personel_skhp_add.text =
                    "Kesatuan : ${personel?.satuan_kerja?.kesatuan.toString().toUpperCase()}"

                /*`txt_ttl_personel_skhp_add.text = "TTL :${
                    personel?.tempat_lahir.toString()
                        .toUpperCase()
                }, ${formatterTanggal(personel?.tanggal_lahir)}"

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
                }*/
            }
        }
    }
}