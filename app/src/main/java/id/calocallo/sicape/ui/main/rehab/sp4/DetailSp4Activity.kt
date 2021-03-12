package id.calocallo.sicape.ui.main.rehab.sp4

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
import id.calocallo.sicape.network.response.Sp4MinResp
import id.calocallo.sicape.network.response.Sp4Resp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.formatterTanggal
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_sp4.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailSp4Activity : BaseActivity() {
    private var getSp4: Sp4MinResp? = null
    private lateinit var sessionManager1: SessionManager1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_sp4)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Data SP4"
        sessionManager1 = SessionManager1(this)

        getSp4 = intent.extras?.getParcelable<Sp4MinResp>(DETAIL_SP4)
        apiDetailSp4(getSp4)
//        getDataSp3View(getSp4)

        btn_generate_sp4_detail.attachTextChangeAnimator()
        bindProgressButton(btn_generate_sp4_detail)
        btn_generate_sp4_detail.setOnClickListener {
            btn_generate_sp4_detail.showProgress {
                progressColor = Color.WHITE
            }

            Handler(Looper.getMainLooper()).postDelayed({
                btn_generate_sp4_detail.hideProgress(R.string.success_generate_doc)
                alert(R.string.download) {
                    positiveButton(R.string.iya) {
                        btn_generate_sp4_detail.hideProgress(R.string.generate_dokumen)

                    }
                    negativeButton(R.string.tidak) {
                        btn_generate_sp4_detail.hideProgress(R.string.generate_dokumen)

                    }
                }.show()
            }, 2000)
        }

    }

    private fun apiDetailSp4(sp4: Sp4MinResp?) {
        NetworkConfig().getServSp4()
            .detailSp4("Bearer ${sessionManager1.fetchAuthToken()}", sp4?.id).enqueue(
                object :
                    Callback<Sp4Resp> {
                    override fun onResponse(call: Call<Sp4Resp>, response: Response<Sp4Resp>) {
                        if (response.isSuccessful) {
                            getDataSp3View(response.body())
                        } else {
                            Toast.makeText(
                                this@DetailSp4Activity, R.string.error, Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<Sp4Resp>, t: Throwable) {
                        Toast.makeText(this@DetailSp4Activity, "$t", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    @SuppressLint("SetTextI18n")
    private fun getDataSp3View(sp4: Sp4Resp?) {

        btn_edit_sp4_detail.setOnClickListener {
            val intent = Intent(this, EditSp4Activity::class.java)
            intent.putExtra(EditSp4Activity.EDT_SP4, sp4)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        txt_no_sp4_detail.text = "No: ${sp4?.no_sp4}"
        txt_no_lp_sktt_detail.text = "No LP: ${sp4?.lp?.no_lp}"
        txt_no_surat_perintah_kapolri_sktt_detail.text =
            "No Surat Perintah Kapolri: ${sp4?.no_surat_perintah_kapolri}, Tanggal: ${
                formatterTanggal(sp4?.tanggal_surat_perintah_kapolri)
            }"
        txt_no_hasil_audit_sktt_detail.text =
            "No Hasil Audit: ${sp4?.no_hasil_audit}, Tanggal: ${formatterTanggal(sp4?.tanggal_hasil_audit)}"
        txt_kota_penetapan_sp4_detail.text = "Kota: ${sp4?.kota_penetapan}"
        txt_tanggal_penetapan_sp4_detail.text =
            "Tanggal ${formatterTanggal(sp4?.tanggal_penetapan)}"
        txt_nama_akreditor_sp4_detail.text = "Nama : ${sp4?.nama_akreditor}"
        txt_pangkat_nrp_akreditor_sp4_detail.text = "Pangkat : ${
            sp4?.pangkat_akreditor.toString()
                .toUpperCase()
        }, NRP : ${sp4?.nrp_akreditor}"
    }

    override fun onResume() {
        super.onResume()
        apiDetailSp4(getSp4)
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
                ApiDelete(getSp4)
                finish()
            }
            negativeButton("Tidak") {
            }
        }.show()
    }

    private fun ApiDelete(sp4: Sp4MinResp?) {
        NetworkConfig().getServSp4().delSp4("Bearer ${sessionManager1.fetchAuthToken()}", sp4?.id)
            .enqueue(
                object : Callback<BaseResp> {
                    override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                        if (response.body()?.message == "Data sp4 removed succesfully") {
                            Toast.makeText(
                                this@DetailSp4Activity,
                                R.string.data_deleted,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this@DetailSp4Activity,
                                R.string.failed_deleted,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                        Toast.makeText(this@DetailSp4Activity, "$t", Toast.LENGTH_SHORT).show()

                    }
                })
    }

    companion object {
        const val DETAIL_SP4 = "DETAIL_SP4"
    }
}