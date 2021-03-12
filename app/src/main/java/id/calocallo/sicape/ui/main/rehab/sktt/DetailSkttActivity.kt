package id.calocallo.sicape.ui.main.rehab.sktt

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
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
import id.calocallo.sicape.network.response.SkttMinResp
import id.calocallo.sicape.network.response.SkttResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.formatterTanggal
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_sktt.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailSkttActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var getSktt: SkttMinResp? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_sktt)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Data SKTT"
        sessionManager1 = SessionManager1(this)

        getSktt = intent.extras?.getParcelable<SkttMinResp>(DETAIL_SKTT)
        apiDetailSktt(getSktt)
//        getDetailSktt(getSktt)

        btn_generate_sktt.attachTextChangeAnimator()
        bindProgressButton(btn_generate_sktt)
        btn_generate_sktt.setOnClickListener {
            btn_generate_sktt.showProgress {
                progressColor = Color.WHITE
            }
            Handler(Looper.getMainLooper()).postDelayed({
                btn_generate_sktt.hideProgress(R.string.success_generate_doc)
                alert(R.string.download) {
                    positiveButton(R.string.iya) {
                        btn_generate_sktt.hideProgress(R.string.generate_dokumen)
                    }
                    negativeButton(R.string.tidak) {
                        btn_generate_sktt.hideProgress(R.string.generate_dokumen)
                    }
                }.show()
            }, 2000)
        }
    }

    private fun apiDetailSktt(sktt: SkttMinResp?) {
        NetworkConfig().getServSktt()
            .detailSktt("Bearer ${sessionManager1.fetchAuthToken()}", sktt?.id).enqueue(
                object :
                    Callback<SkttResp> {
                    override fun onResponse(call: Call<SkttResp>, response: Response<SkttResp>) {
                        if (response.isSuccessful) {
                            getDetailSktt(response.body())
                        } else {
                            Toast.makeText(
                                this@DetailSkttActivity,
                                R.string.error,
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<SkttResp>, t: Throwable) {
                        Toast.makeText(this@DetailSkttActivity, "$t", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    @SuppressLint("SetTextI18n")
    private fun getDetailSktt(sktt: SkttResp?) {
        btn_edit_sktt.setOnClickListener {
            val intent = Intent(this, EditSkttActivity::class.java)
            intent.putExtra(EditSkttActivity.EDIT_SKTT, sktt)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        txt_no_sktt_detail.text = "No: ${sktt?.no_sktt}"
        txt_no_lp_sktt_detail.text = "No LP: ${sktt?.lp?.no_lp}"
        txt_no_laporan_audit_sktt_detail.text =
            "No Laporan Hasil Audit: ${sktt?.no_laporan_hasil_audit_investigasi}"
        txt_kota_penetapan_sktt_detail.text = "Kota : ${sktt?.kota_penetapan}"
        txt_tanggal_penetapan_sktt_detail.text =
            "Tanggal : ${formatterTanggal(sktt?.tanggal_penetapan)}"
        txt_nama_pimpinan_sktt_detail.text = "Nama : ${sktt?.nama_kabid_propam}"
        txt_pangkat_nrp_pimpinan_sktt_detail.text =
            "Pangkat : ${
                sktt?.pangkat_kabid_propam.toString()
                    .toUpperCase()
            }, NRP : ${sktt?.nrp_kabid_propam}"
        txt_tembusan_sktt_detail.text = sktt?.tembusan
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
                ApiDelete(getSktt)
                finish()
            }
            negativeButton("Tidak") {
            }
        }.show()
    }

    private fun ApiDelete(sktt: SkttMinResp?) {
        NetworkConfig().getServSktt()
            .delSktt("Bearer ${sessionManager1.fetchAuthToken()}", sktt?.id).enqueue(
                object : Callback<BaseResp> {
                    override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                        if (response.body()?.message == "Data sktt removed succesfully") {
                            Toast.makeText(
                                this@DetailSkttActivity,
                                R.string.data_deleted,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this@DetailSkttActivity,
                                R.string.failed_deleted,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                        Toast.makeText(this@DetailSkttActivity, "$t", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    companion object {
        const val DETAIL_SKTT = "DETAIL_SKTT"
    }

    override fun onResume() {
        super.onResume()
        apiDetailSktt(getSktt)
    }
}