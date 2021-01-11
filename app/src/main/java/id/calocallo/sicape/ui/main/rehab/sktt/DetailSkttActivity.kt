package id.calocallo.sicape.ui.main.rehab.sktt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.SkttResp
import id.calocallo.sicape.utils.ext.alert
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_sktt.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class DetailSkttActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_sktt)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Data SKTT"

        val getSktt = intent.extras?.getParcelable<SkttResp>(DETAIL_SKTT)
        getDetailSktt(getSktt)
        btn_edit_sktt.setOnClickListener {
            val intent = Intent(this, EditSkttActivity::class.java)
            intent.putExtra(EditSkttActivity.EDIT_SKTT, getSktt)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun getDetailSktt(sktt: SkttResp?) {
        txt_no_sktt_detail.text = sktt?.no_sktt
        txt_no_lhp_sktt_detail.text = sktt?.lhp?.no_lhp
        txt_no_lp_sktt_detail.text = sktt?.lp?.no_lp
        txt_menimbang_sktt_detail.text = sktt?.menimbang
        txt_mengingat_p5_sktt_detail.text = sktt?.mengingat_p5
        txt_kota_penetapan_sktt_detail.text = "Kota : ${sktt?.kota_penetapan}"
        txt_tanggal_penetapan_sktt_detail.text = "Tanggal : ${sktt?.tanggal_penetapan}"
        txt_nama_pimpinan_sktt_detail.text = "Nama : ${sktt?.nama_yang_menetapkan}"
        txt_pangkat_nrp_pimpinan_sktt_detail.text =
            "Pangkat : ${sktt?.pangkat_yang_menetapkan.toString()
                .toUpperCase()}, NRP : ${sktt?.nrp_yang_menetapkan}"
        txt_jabatan_pimpinan_sktt_detail.text = "Jabatan : ${sktt?.jabatan_yang_menetapkan}"
        txt_tembusan_sktt_detail.text = sktt?.no_sktt
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
    companion object {
        const val DETAIL_SKTT = "DETAIL_SKTT"
    }
}