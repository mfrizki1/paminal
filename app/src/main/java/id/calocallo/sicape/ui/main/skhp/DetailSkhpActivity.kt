package id.calocallo.sicape.ui.main.skhp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.network.response.SkhpMinResp
import id.calocallo.sicape.network.response.SkhpResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.formatterTanggal
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_skhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.format.DateTimeFormatter
import java.util.*

class DetailSkhpActivity : BaseActivity() {
    companion object {
        const val DETAIL_SKHP = "DETAIL_SKHP"
    }

    private lateinit var sessionManager1: SessionManager1
    private var getDataSkhp: SkhpMinResp? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_skhp)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Data Surat Keterangan Hasil Penelitian"
        sessionManager1 = SessionManager1(this)

        getDataSkhp = intent.extras?.getParcelable<SkhpMinResp>(DETAIL_SKHP)
        apiDetailSkhp(getDataSkhp)
//        getViewSkhp(getDataSkhp)
        btn_edit_skhp_detail.setOnClickListener {
            val intent = Intent(this, EditSkhpActivity::class.java)
            intent.putExtra(EditSkhpActivity.EDIT_SKHP, getDataSkhp)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_generate_skhp_detail.attachTextChangeAnimator()
        bindProgressButton(btn_generate_skhp_detail)
        btn_generate_skhp_detail.setOnClickListener {
            btn_generate_skhp_detail.showProgress {
                progressColor = Color.WHITE
            }
            Handler(Looper.getMainLooper()).postDelayed({
                btn_generate_skhp_detail.hideProgress("Berhasil Generate Dokumen")
                alert("Download") {
                    positiveButton("Iya") {
                        btn_generate_skhp_detail.hideProgress(R.string.generate_dokumen)
                    }
                    negativeButton("Tidak") {
                        btn_generate_skhp_detail.hideProgress(R.string.generate_dokumen)
                    }
                }.show()
            }, 2000)


        }

    }

    private fun apiDetailSkhp(dataSkhp: SkhpMinResp?) {
        NetworkConfig().getServSkhp()
            .detailSkhp("Bearer ${sessionManager1.fetchAuthToken()}", dataSkhp?.id).enqueue(object :
                Callback<SkhpResp> {
                override fun onResponse(call: Call<SkhpResp>, response: Response<SkhpResp>) {
                    if (response.isSuccessful) {
                        getViewSkhp(response.body())
                    } else {
                        Toast.makeText(
                            this@DetailSkhpActivity, R.string.error, Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<SkhpResp>, t: Throwable) {
                    Toast.makeText(this@DetailSkhpActivity, "$t", Toast.LENGTH_SHORT).show()
                }
            })
    }

    @SuppressLint("SetTextI18n")
    private fun getViewSkhp(dataSkhp: SkhpResp?) {
        txt_no_skhp_detail.text = dataSkhp?.no_skhp
        txt_nama_personel_skhp_detail.text = "Nama : ${dataSkhp?.personel?.nama}"

        when (dataSkhp?.personel?.jenis_kelamin) {
            "laki_laki" -> {
                txt_jk_personel_skhp_detail.text = "Jenis Kelamin : Laki-Laki"
            }
            "perempuan" -> {
                txt_jk_personel_skhp_detail.text = "Jenis Kelamin : Perempuan"
            }
        }
        when (dataSkhp?.personel?.agama_sekarang) {
            "islam" -> txt_agama_personel_skhp_detail.text = "Agama : Islam"
            "buddha" -> txt_agama_personel_skhp_detail.text = "Agama : Buddha"
            "katolik" -> txt_agama_personel_skhp_detail.text = "Agama : Katolik"
            "hindu" -> txt_agama_personel_skhp_detail.text = "Agama : Hindu"
            "protestan" -> txt_agama_personel_skhp_detail.text = "Agama : Protestan"
            "konghuchu" -> txt_agama_personel_skhp_detail.text = "Agama : Konghuchu"
        }

        txt_pangkat_nrp_personel_skhp_detail.text =
            "Pangkat : ${
                dataSkhp?.personel?.pangkat.toString()
                    .toUpperCase()
            } / ${dataSkhp?.personel?.nrp}"
        txt_jabatan_personel_skhp_detail.text = "Jabatan ${dataSkhp?.personel?.jabatan}"
        txt_kesatuan_personel_skhp_detail.text =
            "Kesatuan ${dataSkhp?.personel?.satuan_kerja?.kesatuan.toString().toUpperCase()}"

        if (dataSkhp?.is_memenuhi_syarat == 1) {
            txt_isi_skhp_detail.text =
                "Memenuhi syarat untuk usulan kenaikan pangkat setingkat lebih tinggi pada periode ${
                    formatterTanggal(dataSkhp.tanggal_kenaikan_pangkat)
                }"
        } else {
            txt_isi_skhp_detail.text = "Tidak Memenuhi Syarat"
        }

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = formatter.parse(dataSkhp?.personel?.tanggal_lahir)
        val desiredFormat =
            DateTimeFormatter.ofPattern("dd, MMMM yyyy", Locale("id", "ID")).format(date)
        txt_ttl_personel_skhp_detail.text = "TTL : ${
            dataSkhp?.personel?.tempat_lahir.toString().toUpperCase()
        },Tanggal $desiredFormat"

        txt_kota_keluar_skhp_detail.text = "Kota ${dataSkhp?.kota_keluar}"

        txt_tanggal_keluar_skhp_detail.text =
            "Tanggal ${formatterTanggal(dataSkhp?.tanggal_keluar)}"

        txt_nama_pimpinan_skhp_detail.text = "Nama ${dataSkhp?.nama_kabid_propam}"
        txt_pangkat_nrp_pimpinan_skhp_detail.text =
            "Pangkat : ${
                dataSkhp?.pangkat_kabid_propam.toString().toUpperCase()
            } / ${dataSkhp?.nrp_kabid_propam}"

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_delete, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_delete_item -> {
                alertDialogDelete()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun alertDialogDelete() {
        this.alert("Yakin Hapus Data?") {
            positiveButton("Iya") {
                ApiDelete(getDataSkhp)
                finish()
            }
            negativeButton("Tidak") {
            }
        }.show()
    }

    private fun ApiDelete(dataSkhp: SkhpMinResp?) {
        NetworkConfig().getServSkhp()
            .dellSkhp("Bearer ${sessionManager1.fetchAuthToken()}", dataSkhp?.id).enqueue(
                object : Callback<BaseResp> {
                    override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                        if (response.body()?.message == "Data skhp removed succesfully") {
                            Toast.makeText(
                                this@DetailSkhpActivity, R.string.data_deleted, Toast.LENGTH_SHORT
                            ).show()
                            Handler(Looper.getMainLooper()).postDelayed({
                                finish()
                            }, 750)
                        } else {
                            Toast.makeText(
                                this@DetailSkhpActivity, R.string.failed_deleted, Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                        Toast.makeText(this@DetailSkhpActivity, "$t", Toast.LENGTH_SHORT).show()
                    }
                })
    }
}