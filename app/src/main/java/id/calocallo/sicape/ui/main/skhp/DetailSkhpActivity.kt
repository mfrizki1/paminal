package id.calocallo.sicape.ui.main.skhp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.SkhpResp
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.formatterTanggal
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_skhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import java.time.format.DateTimeFormatter
import java.util.*

class DetailSkhpActivity : BaseActivity() {
    companion object {
        const val DETAIL_SKHP = "DETAIL_SKHP"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_skhp)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Data Surat Keterangan Hasil Penelitian"
        val getDataSkhp = intent.extras?.getParcelable<SkhpResp>(DETAIL_SKHP)
        getViewSkhp(getDataSkhp)
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

    @SuppressLint("SetTextI18n")
    private fun getViewSkhp(dataSkhp: SkhpResp?) {
        txt_no_skhp_detail.text = dataSkhp?.no_skhp

        txt_nama_personel_skhp_detail.text = "Nama : ${dataSkhp?.personel?.nama}"

        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val date = formatter.parse(dataSkhp?.personel?.tanggal_lahir)
        val desiredFormat =
            DateTimeFormatter.ofPattern("dd, MMM yyyy", Locale("id", "ID")).format(date)

        txt_ttl_personel_skhp_detail.text =
            "TTL : ${dataSkhp?.personel?.tempat_lahir.toString().toUpperCase()}, $desiredFormat"

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
            "Pangkat : ${dataSkhp?.personel?.pangkat.toString()
                .toUpperCase()} / ${dataSkhp?.personel?.nrp}"
        txt_jabatan_personel_skhp_detail.text = "Jabatan ${dataSkhp?.personel?.jabatan}"
        txt_kesatuan_personel_skhp_detail.text =
            "Kesatuan ${dataSkhp?.personel?.kesatuan.toString().toUpperCase()}"
        txt_alamat_kantor_personel_skhp_detail.text ="Alamat : ${dataSkhp?.personel?.alamat_kantor}"
        if (dataSkhp?.is_memiliki_pelanggaran_pidana == 1) {
            txt_memiliki_pidana_skhp_detail.text = "Pidana : Memiliki"
        } else {
            txt_memiliki_pidana_skhp_detail.text = "Pidana : Tidak"
        }

        if (dataSkhp?.is_memiliki_pelanggaran_kode_etik == 1) {
            txt_memiliki_kke_skhp_detail.text = "Kode Etik : Memiliki"
        } else {
            txt_memiliki_kke_skhp_detail.text = "Kode Etik : Tidak"
        }

        if (dataSkhp?.is_memiliki_pelanggaran_disiplin == 1) {
            txt_memiliki_disiplin_skhp_detail.text = "Disiplin : Memiliki"
        } else {
            txt_memiliki_disiplin_skhp_detail.text = "Disiplin : Tidak"
        }

        if (dataSkhp?.is_status_selesai == 1) {
            txt_status_cat_pelangg_skhp_detail.text = "Status : Selesai"
        } else {
            txt_status_cat_pelangg_skhp_detail.text = "Status : Tidak"
        }
        txt_isi_skhp_detail.text = dataSkhp?.hasil_keputusan
        txt_kota_keluar_skhp_detail.text = "Kota ${dataSkhp?.kota_keluar}"

        txt_tanggal_keluar_skhp_detail.text =
            "Tanggal ${formatterTanggal(dataSkhp?.tanggal_keluar)}"
        txt_nama_pimpinan_skhp_detail.text = "Nama ${dataSkhp?.nama_yang_mengeluarkan}"
        txt_pangkat_nrp_pimpinan_skhp_detail.text =
            "Pangkat : ${dataSkhp?.pangkat_yang_mengeluarkan.toString()
                .toUpperCase()} / ${dataSkhp?.nrp_yang_mengeluarkan}"
        txt_jabatan_pimpinan_skhp_detail.text = "Jabatan ${dataSkhp?.jabatan_yang_mengeluarkan}"
        txt_kepada_skhp_detail.text = dataSkhp?.kepada
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
//                ApiDelete()
                finish()
            }
            negativeButton("Tidak") {
            }
        }.show()
    }
}