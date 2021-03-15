package id.calocallo.sicape.ui.main.rehab.sktt

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.*
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_skhd.*
import kotlinx.android.synthetic.main.activity_detail_sktt.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailSkttActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var getSktt: SkttMinResp? = null
    private lateinit var downloadID: Any
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_sktt)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Data SKTT"
        sessionManager1 = SessionManager1(this)
        registerReceiver(onDownloadComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        getSktt = intent.extras?.getParcelable<SkttMinResp>(DETAIL_SKTT)
        apiDetailSktt(getSktt)
//        getDetailSktt(getSktt)
        val hak = sessionManager1.fetchHakAkses()
        if(hak == "operator"){
            btn_edit_sktt.gone()
            btn_generate_sktt.gone()
        }
        btn_generate_sktt.attachTextChangeAnimator()
        bindProgressButton(btn_generate_sktt)
        btn_generate_sktt.setOnClickListener {
            btn_generate_sktt.showProgress {
                progressColor = Color.WHITE
            }
            apiDocSktt(getSktt)
            /*Handler(Looper.getMainLooper()).postDelayed({
                btn_generate_sktt.hideProgress(R.string.success_generate_doc)
                alert(R.string.download) {
                    positiveButton(R.string.iya) {
                        btn_generate_sktt.hideProgress(R.string.generate_dokumen)
                    }
                    negativeButton(R.string.tidak) {
                        btn_generate_sktt.hideProgress(R.string.generate_dokumen)
                    }
                }.show()
            }, 2000)*/
        }
    }

    private fun apiDocSktt(sktt: SkttMinResp?) {
        NetworkConfig().getServSktt()
            .docSktt("Bearer ${sessionManager1.fetchAuthToken()}", sktt?.id).enqueue(
                object : Callback<Base1Resp<AddSkttResp>> {
                    override fun onResponse(
                        call: Call<Base1Resp<AddSkttResp>>,
                        response: Response<Base1Resp<AddSkttResp>>
                    ) {
                        if (response.body()?.message == "Document sktt generated successfully") {
                            btn_generate_sktt.hideProgress(R.string.success_generate_doc)
                            alert(R.string.download) {
                                positiveButton(R.string.iya) {
                                    btn_generate_sktt.hideProgress(R.string.success_generate_doc)
                                    downloadSktt(response.body()?.data?.sktt)
                                }
                                negativeButton(R.string.tidak) {
                                    btn_generate_sktt.hideProgress(R.string.generate_dokumen)
                                }
                            }.show()
                        } else {
                            btn_generate_sktt.hideProgress(R.string.failed_generate_doc)
                        }
                    }

                    override fun onFailure(call: Call<Base1Resp<AddSkttResp>>, t: Throwable) {
                        Toast.makeText(this@DetailSkttActivity, "$t", Toast.LENGTH_SHORT).show()
                        btn_generate_sktt.hideProgress(R.string.failed_generate_doc)
                    }
                })
    }

    private fun downloadSktt(sktt: SkttResp?) {
        Log.e("sktt", "$sktt")
        val url = sktt?.dokumen?.url
        val filename: String = "${sktt?.no_sktt}.${sktt?.dokumen?.jenis}"
        val request: DownloadManager.Request = DownloadManager.Request(Uri.parse(url))
            .setTitle(filename)
            .setDescription("Downloading")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename)

        val manager: DownloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadID = manager.enqueue(request)
    }

    private val onDownloadComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val completedId = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (completedId == downloadID) {
                btn_generate_sktt.showSnackbar(R.string.success_download_doc) { action(R.string.action_ok) {} }
            }
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
        if (sktt?.is_ada_dokumen == 1) {
            btn_lihat_doc_sktt.visible()
            btn_lihat_doc_sktt.setOnClickListener {
                val uri = Uri.parse(sktt.dokumen?.url)
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri.toString())))
            }
        }
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