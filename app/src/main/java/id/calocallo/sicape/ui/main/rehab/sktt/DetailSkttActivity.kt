package id.calocallo.sicape.ui.main.rehab.sktt

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
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
import id.calocallo.sicape.network.response.SkttResp
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.formatterTanggal
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

        btn_generate_sktt.attachTextChangeAnimator()
        bindProgressButton(btn_generate_sktt)
        btn_generate_sktt.setOnClickListener {
            btn_generate_sktt.showProgress{
                progressColor = Color.WHITE
            }
            Handler(Looper.getMainLooper()).postDelayed({
                btn_generate_sktt.hideProgress(R.string.success_generate_doc)
                alert(R.string.download) {
                    positiveButton(R.string.iya){
                        btn_generate_sktt.hideProgress(R.string.generate_dokumen)
                    }
                    negativeButton(R.string.tidak){
                        btn_generate_sktt.hideProgress(R.string.generate_dokumen)
                    }
                }.show()
            },2000)
        }
    }

    private fun getDetailSktt(sktt: SkttResp?) {
        txt_no_sktt_detail.text = sktt?.no_sktt
        txt_no_lhp_sktt_detail.text = sktt?.lhp?.no_lhp
        txt_no_lp_sktt_detail.text = sktt?.lp?.no_lp
        txt_menimbang_sktt_detail.text = sktt?.menimbang
        txt_mengingat_p5_sktt_detail.text = sktt?.mengingat_p5
        txt_kota_penetapan_sktt_detail.text = "Kota : ${sktt?.kota_penetapan}"
        txt_tanggal_penetapan_sktt_detail.text =
            "Tanggal : ${formatterTanggal(sktt?.tanggal_penetapan)}"
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