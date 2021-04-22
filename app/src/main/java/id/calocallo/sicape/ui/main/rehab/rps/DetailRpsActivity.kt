package id.calocallo.sicape.ui.main.rehab.rps

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.Uri
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
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_rps.*
import kotlinx.android.synthetic.main.activity_detail_skhd.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailRpsActivity : BaseActivity() {
    private var idSkhd: Int? = null
    private lateinit var downloadID: Any
    private lateinit var sessionManager1: SessionManager1
    private var dataMinRps: RpsMinResp? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_rps)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Data RPS"
        sessionManager1 = SessionManager1(this)
        dataMinRps = intent.extras?.getParcelable<RpsMinResp>(DETAIL_RPS)
        registerReceiver(onDownloadComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
//            btn_generate_rps_detail.gone()
            btn_edit_rps.gone()

        }
        apiDetailRps(dataMinRps)
//        getViewRps(dataMinRps)

        bindProgressButton(btn_generate_rps_detail)
        btn_generate_rps_detail.attachTextChangeAnimator()
        btn_generate_rps_detail.setOnClickListener {
            btn_generate_rps_detail.showProgress {
                progressColor = Color.WHITE
            }
            apiDocRps(dataMinRps)
/*            Handler(Looper.getMainLooper()).postDelayed({
                btn_generate_rps_detail.hideProgress(R.string.success_generate_doc)
                alert(R.string.download) {
                    positiveButton(R.string.iya) {
                        btn_generate_rps_detail.hideProgress(R.string.generate_dokumen)

                    }
                    negativeButton(R.string.tidak) {
                        btn_generate_rps_detail.hideProgress(R.string.generate_dokumen)

                    }
                }.show()
            }, 2000)*/
        }

    }

    override fun onResume() {
        super.onResume()
        apiDetailRps(dataMinRps)
    }

    private fun apiDocRps(dataMinRps: RpsMinResp?) {
        NetworkConfig().getServRps()
            .docRps("Bearer ${sessionManager1.fetchAuthToken()}", dataMinRps?.id)
            .enqueue(object : Callback<Base1Resp<AddRpsResp>> {
                override fun onResponse(
                    call: Call<Base1Resp<AddRpsResp>>,
                    response: Response<Base1Resp<AddRpsResp>>
                ) {
                    if (response.body()?.message == "Document rps generated successfully") {
                        Log.e("data", "${response.body()?.data?.rps}")
                        alert(R.string.download) {
                            positiveButton(R.string.iya) {
                                btn_generate_rps_detail.hideProgress(R.string.success_generate_doc)
                                downloadRps(response.body()?.data?.rps)
                            }
                            negativeButton(R.string.tidak) {
                                btn_generate_rps_detail.hideProgress(R.string.generate_dokumen)
                            }
                        }.show()
                    } else {
                        toast("${response.body()?.message}")
                        btn_generate_rps_detail.hideProgress(R.string.failed_generate_doc)
                    }
                }

                override fun onFailure(call: Call<Base1Resp<AddRpsResp>>, t: Throwable) {
                    Toast.makeText(this@DetailRpsActivity, "$t", Toast.LENGTH_SHORT).show()
                    btn_generate_rps_detail.hideProgress(R.string.failed_generate_doc)

                }
            })
    }

    private fun downloadRps(rps: RpsResp?) {
        val url = rps?.dokumen?.url
        val filename: String = "${rps?.no_rps}.${rps?.dokumen?.jenis}"
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
                btn_generate_rps_detail.hideProgress(R.string.generate_dokumen)
                btn_generate_rps_detail.showSnackbar(R.string.success_download_doc) {
                    action(R.string.action_ok) {
                        btn_generate_rps_detail.hideProgress(R.string.generate_dokumen)
                    }
                }
            }
        }
    }

    private fun apiDetailRps(dataMinRps: RpsMinResp?) {
        NetworkConfig().getServRps()
            .detailRps("Bearer ${sessionManager1.fetchAuthToken()}", dataMinRps?.id).enqueue(
                object :
                    Callback<RpsResp> {
                    override fun onResponse(call: Call<RpsResp>, response: Response<RpsResp>) {
                        if (response.isSuccessful) {
                            getViewRps(response.body())
                        } else {
                            Toast.makeText(
                                this@DetailRpsActivity, R.string.error, Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<RpsResp>, t: Throwable) {
                        Toast.makeText(this@DetailRpsActivity, "$t", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    @SuppressLint("SetTextI18n")
    private fun getViewRps(dataRPS: RpsResp?) {
        if (dataRPS?.is_ada_dokumen == 1) {
            btn_lihat_rps_detail.visible()
            btn_lihat_rps_detail.setOnClickListener {
                val uri = Uri.parse(dataRPS.dokumen?.url)
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri.toString())))
            }
        }
        btn_edit_rps.setOnClickListener {
            val intent = Intent(this, EditRpsActivity::class.java)
            intent.putExtra(EditRpsActivity.EDIT_RPS, dataRPS)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

//        idSkhd = dataRPS?.skhd?.id
        txt_no_rps_detail.text = "No: ${dataRPS?.no_rps}"
        txt_no_lp_rps_detail.text = "No: ${dataRPS?.lp?.no_lp}"
        txt_nama_dinas_rps_detail.text = "Nama Dinas: ${dataRPS?.nama_dinas}"
        txt_no_nota_dinas_rps_detail.text =
            "No Dinas: ${dataRPS?.no_nota_dinas}, Tanggal: ${formatterTanggal(dataRPS?.tanggal_nota_dinas)}"
        txt_kota_penetapan_rps_detail.text = "Kota: ${dataRPS?.kota_penetapan}"
        txt_tanggal_penetapan_rps_detail.text =
            "Tanggal: ${formatterTanggal(dataRPS?.tanggal_penetapan)}"
        txt_nama_pimpinan_rps_detail.text = "Nama : ${dataRPS?.nama_yang_mengetahui}"
        txt_pangkat_nrp_pimpinan_rps_detail.text =
            "Pangkat: ${
                dataRPS?.pangkat_yang_mengetahui.toString()
                    .toUpperCase()
            }, NRP : ${dataRPS?.nrp_yang_mengetahui}"
        txt_tembusan_rps_detail.text = dataRPS?.tembusan
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_delete, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_delete_item -> {
                val hak = sessionManager1.fetchHakAkses()
                if(hak != "operator"){
                    alertDialogDelete()
                }else{
                    toast("Hanya bisa melalui Admin")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun alertDialogDelete() {
        this.alert("Yakin Hapus Data?") {
            positiveButton("Iya") {
                ApiDelete(dataMinRps)
                finish()
            }
            negativeButton("Tidak") {
            }
        }.show()
    }

    private fun ApiDelete(dataMinRps: RpsMinResp?) {
        NetworkConfig().getServRps()
            .delRps("Bearer ${sessionManager1.fetchAuthToken()}", dataMinRps?.id).enqueue(
                object : Callback<BaseResp> {
                    override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                        if (response.body()?.message == "Data rps removed succesfully") {
                            Toast.makeText(
                                this@DetailRpsActivity,
                                R.string.data_deleted,
                                Toast.LENGTH_SHORT
                            ).show()
                            Handler(Looper.getMainLooper()).postDelayed({
                                finish()
                            }, 750)
                        } else {
                            Toast.makeText(
                                this@DetailRpsActivity,
                                R.string.failed_deleted,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                        Toast.makeText(
                            this@DetailRpsActivity, "$t", Toast.LENGTH_SHORT
                        ).show()
                    }
                })
    }

    companion object {
        const val DETAIL_RPS = "DETAIL_RPS"
    }
}