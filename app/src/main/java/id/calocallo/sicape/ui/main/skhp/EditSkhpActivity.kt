package id.calocallo.sicape.ui.main.skhp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.ui.main.skhd.tinddisiplin.AddTindDisiplinSkhdActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_skhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class EditSkhpActivity : BaseActivity() {
    companion object {
        const val EDIT_SKHP = "EDIT_SKHP"
    }

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
        setContentView(R.layout.activity_edit_skhp)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data SKHP"
        sessionManager1 = SessionManager1(this)

        val getDetailSkhp = intent.extras?.getParcelable<SkhpMinResp>(EDIT_SKHP)
        apiEditSkhp(getDetailSkhp)
        btn_save_skhp_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_skhp_edit)
        btn_save_skhp_edit.setOnClickListener {
            btn_save_skhp_edit.showProgress{
                progressColor = Color.WHITE
            }
            updateSkhp(getDetailSkhp)
        }

        rg_memenuhi_syarat_skhp_edit.setOnCheckedChangeListener { _, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            if (radio.isChecked && radio.text == "Ya") {
                isSyarat = 1
            } else {
                isSyarat = 0
            }
        }
        var listKotaSkhp = listOf("Banjarmasin", "Banjarbaru")
        val adapterKota = ArrayAdapter(this, R.layout.item_spinner, listKotaSkhp)
        edt_kota_keluar_skhp_edit.setAdapter(adapterKota)
        edt_kota_keluar_skhp_edit.setOnItemClickListener { parent, view, position, id ->
            kotaSkhp = parent.getItemAtPosition(position).toString()
        }
        btn_choose_personel_skhp_edit.setOnClickListener {
            val intent = Intent(this, KatPersonelActivity::class.java)
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, AddTindDisiplinSkhdActivity.REQ_PERSONEL_TIND)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun apiEditSkhp(detailSkhp: SkhpMinResp?) {
        NetworkConfig().getServSkhp()
            .detailSkhp("Bearer ${sessionManager1.fetchAuthToken()}", detailSkhp?.id).enqueue(
                object :
                    Callback<SkhpResp> {
                    override fun onResponse(call: Call<SkhpResp>, response: Response<SkhpResp>) {
                        if (response.isSuccessful) {
                            getViewSkhpEdit(response.body())
                        } else {
                            Toast.makeText(
                                this@EditSkhpActivity, R.string.error, Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<SkhpResp>, t: Throwable) {
                        Toast.makeText(
                            this@EditSkhpActivity, "$t", Toast.LENGTH_SHORT
                        ).show()
                    }
                })
    }

    @SuppressLint("SetTextI18n")
    private fun getViewSkhpEdit(detailSkhp: SkhpResp?) {
        txt_nama_personel_skhp_edit.text = "Nama : ${detailSkhp?.personel?.nama}"
        txt_pangkat_personel_skhp_edit.text =
            "Pangkat ${detailSkhp?.personel?.pangkat.toString().toUpperCase()}: "
        txt_nrp_personel_skhp_edit.text = "NRP : ${detailSkhp?.personel?.nrp}"
        txt_jabatan_personel_skhp_edit.text = "Jabatan : ${detailSkhp?.personel?.jabatan}"
        txt_kesatuan_personel_skhp_edit.text =
            "Kesatuan : ${detailSkhp?.personel?.satuan_kerja?.kesatuan.toString().toUpperCase()}"



        if (detailSkhp?.is_memenuhi_syarat == 0) {
            Log.e("asd","Asd")
            rb_memenuhi_syarat_tidak_skhp_edit.isChecked = true
        } else {
            Log.e("123","123")
            rb_memenuhi_syarat_ya_skhp_edit.isChecked= true
        }
        edt_no_skhp_edit.setText(detailSkhp?.no_skhp)
        edt_tgl_kenaikan_pangkat_skhp_edit.setText(detailSkhp?.tanggal_kenaikan_pangkat)
        edt_kota_keluar_skhp_edit.setText(detailSkhp?.kota_keluar)
        edt_tanggal_keluar_skhp_edit.setText(detailSkhp?.tanggal_keluar)
        edt_nama_pimpinan_skhp_edit.setText(detailSkhp?.nama_kabid_propam)
        edt_nrp_pimpinan_skhp_edit.setText(detailSkhp?.nrp_kabid_propam)
        edt_pangkat_pimpinan_skhp_edit.setText(detailSkhp?.pangkat_kabid_propam)
        edt_kepada_skhp_edit.setText(detailSkhp?.kepada_penerima)
        edt_kota_penerima_skhp_edit.setText(detailSkhp?.kota_penerima)
        idPersonel = detailSkhp?.personel?.id

        /*   skhpReq.no_skhp = edt_no_skhp_edit.text.toString()
           skhpReq.hasil_keputusan = edt_isi_skhp_edit.text.toString()
//            skhpReq.kota_keluar = edt_kota_keluar_skhp_edit.text.toString()
           skhpReq.kota_keluar = kotaSkhp
           skhpReq.tanggal_keluar = edt_tanggal_keluar_skhp_edit.text.toString()
           skhpReq.nama_yang_mengeluarkan = edt_nama_pimpinan_skhp_edit.text.toString()
           skhpReq.pangkat_yang_mengeluarkan =
               edt_pangkat_pimpinan_skhp_edit.text.toString().toUpperCase()
           skhpReq.nrp_yang_mengeluarkan = edt_nrp_pimpinan_skhp_edit.text.toString()
           skhpReq.jabatan_yang_mengeluarkan = edt_jabatan_pimpinan_skhp_edit.text.toString()
           skhpReq.kepada = edt_kepada_skhp_edit.text.toString()
           skhpReq.is_memiliki_pelanggaran_pidana = isPidana
           skhpReq.is_memiliki_pelanggaran_kode_etik = isKke
           skhpReq.is_memiliki_pelanggaran_disiplin = isDisiplin
           skhpReq.is_status_selesai = tempSttsCatPelanggaran*/
        isSyarat = detailSkhp?.is_memenuhi_syarat
        kotaSkhp = detailSkhp?.kota_keluar
        edt_no_skhp_edit.setText(detailSkhp?.no_skhp)
        /*edt_no_skhp_edit.setText(detailSkhp?.no_skhp)
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
        }*/
    }

    private fun updateSkhp(detailSkhp: SkhpMinResp?) {
        skhpReq.id_personel = idPersonel
        skhpReq.is_memenuhi_syarat = isSyarat
        skhpReq.tanggal_kenaikan_pangkat = edt_tgl_kenaikan_pangkat_skhp_edit.text.toString()
        skhpReq.kota_keluar = kotaSkhp
        skhpReq.tanggal_keluar = edt_tanggal_keluar_skhp_edit.text.toString()
        skhpReq.nama_kabid_propam = edt_nama_pimpinan_skhp_edit.text.toString()
        skhpReq.nrp_kabid_propam = edt_nrp_pimpinan_skhp_edit.text.toString()
        skhpReq.pangkat_kabid_propam = edt_pangkat_pimpinan_skhp_edit.text.toString()
        skhpReq.kepada_penerima = edt_kepada_skhp_edit.text.toString()
        skhpReq.kota_penerima = edt_kota_penerima_skhp_edit.text.toString()
        /*    skhpReq.is_memiliki_pelanggaran_pidana = isPidana
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

            Log.e("edit Skhp", "$skhpReq")*/
        apiUpdSkhp(detailSkhp)
    }

    private fun apiUpdSkhp(skhp: SkhpMinResp?) {
        NetworkConfig().getServSkhp().updSkhp("Bearer ${sessionManager1.fetchAuthToken()}", skhp?.id, skhpReq).enqueue(
            object : Callback<Base1Resp<AddSkhpResp>> {
                override fun onResponse(
                    call: Call<Base1Resp<AddSkhpResp>>,
                    response: Response<Base1Resp<AddSkhpResp>>
                ) {
                    if (response.body()?.message == "Data skhp updated succesfully") {
                        btn_save_skhp_edit.hideProgress(R.string.data_updated)
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        },750)
                    } else {
                        btn_save_skhp_edit.hideProgress(R.string.not_update)
                    }
                }

                override fun onFailure(call: Call<Base1Resp<AddSkhpResp>>, t: Throwable) {
                    Toast.makeText(this@EditSkhpActivity, "$t", Toast.LENGTH_SHORT).show()
                    btn_save_skhp_edit.hideProgress(R.string.not_update)
                }
            })
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AddTindDisiplinSkhdActivity.REQ_PERSONEL_TIND) {
            if (resultCode == Activity.RESULT_OK) {
                val personel = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
                idPersonel = personel?.id
                txt_nama_personel_skhp_edit.text = "Nama : ${personel?.nama}"
                txt_pangkat_personel_skhp_edit.text =
                    "Pangkat ${personel?.pangkat.toString().toUpperCase()}: "
                txt_nrp_personel_skhp_edit.text = "NRP : ${personel?.nrp}"
                txt_jabatan_personel_skhp_edit.text = "Jabatan : ${personel?.jabatan}"
                txt_kesatuan_personel_skhp_edit.text =
                    "Kesatuan : ${personel?.satuan_kerja?.kesatuan.toString().toUpperCase()}"

                /*  val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                  val date = formatter.parse(personel?.tanggal_lahir)
                  val desiredFormat =
                      DateTimeFormatter.ofPattern("dd, MMM yyyy", Locale("id", "ID")).format(date)

                  txt_ttl_personel_skhp_edit.text = "TTL :${
                      personel?.tempat_lahir.toString()
                          .toUpperCase()
                  }, $desiredFormat"

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
  //                txt_agaman_personel_skhp_edit.text = "Agama : ${personel?.agama_sekarang}"*/
            }
        }
    }
}