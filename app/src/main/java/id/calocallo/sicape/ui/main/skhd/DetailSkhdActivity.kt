package id.calocallo.sicape.ui.main.skhd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.SkhdResp
import id.calocallo.sicape.ui.main.skhd.edit.EditSkhdActivity
import id.calocallo.sicape.utils.ext.alert
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_skhd.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class DetailSkhdActivity : BaseActivity() {

    companion object {
        const val DETAIL_SKHD = "DETAIL_SKHD"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_skhd)

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail SKHD"

        val detailSKHD = intent.extras?.getParcelable<SkhdResp>(DETAIL_SKHD)
//        Log.e("detailSKHD", "$detailSKHD")
        getDetailSkhd(detailSKHD)

        btn_edit_skhd.setOnClickListener {
            val intent = Intent(this, EditSkhdActivity::class.java)
            intent.putExtra(DETAIL_SKHD, detailSKHD)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        }

    }

    private fun getDetailSkhd(detailSKHD: SkhdResp?) {
        txt_no_skhd_detail.text = detailSKHD?.no_skhd
        txt_no_lhp_skhd_detail.text = detailSKHD?.lhp?.no_lhp
        txt_no_lp_skhd_detail.text = detailSKHD?.lp?.no_lp
        txt_bidang_skhd_detail.text = detailSKHD?.bidang
        txt_berkas_skhd_detail.text = detailSKHD?.menimbang_p2
        txt_berkas_pemeriksaan_skhd_detail.text = detailSKHD?.memperlihatkan
        txt_hukuman_skhd_detail.text = detailSKHD?.hukuman
        txt_tanggal_disampaikan_skhd_detail.text =
            "Tanggal : ${detailSKHD?.tanggal_disampaikan_ke_terhukum}"
        txt_waktu_disampaikan_skhd_detail.text =
            "Pukul : ${detailSKHD?.waktu_disampaikan_ke_terhukum}"
        txt_kota_penetapan_skhd_detail.text = "Kota : ${detailSKHD?.kota_penetapan}"
        txt_tanggal_penetapan_skhd_detail.text = "Tanggal : ${detailSKHD?.tanggal_penetapan}"
        txt_nama_pimpinan_skhd_detail.text = "Nama : ${detailSKHD?.nama_yang_menetapkan}"
        txt_pangkat_nrp_pimpinan_skhd_detail.text =
            "Pangkat : ${detailSKHD?.pangkat_yang_menetapkan.toString().toUpperCase()}, NRP : ${detailSKHD?.nrp_yang_menetapkan}"
        txt_jabatan_pimpinan_skhd_detail.text = "Jabatan : ${detailSKHD?.jabatan_yang_menetapkan}"
        txt_tembusan_skhd_detail.text = detailSKHD?.tembusan
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
        this.alert("Hapus Data", "Yakin Hapus?") {
            positiveButton("Iya") {
//                ApiDelete()
                finish()
            }
            negativeButton("Tidak") {
            }
        }.show()
    }
}