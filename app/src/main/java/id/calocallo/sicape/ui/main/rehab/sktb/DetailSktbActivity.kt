package id.calocallo.sicape.ui.main.rehab.sktb

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
import kotlinx.android.synthetic.main.activity_detail_sktb.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailSktbActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var detailSktb: SktbMinResp? = null
    private lateinit var downloadID: Any
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_sktb)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Data SKTB"
        sessionManager1 = SessionManager1(this)
        registerReceiver(onDownloadComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        detailSktb = intent.extras?.getParcelable<SktbMinResp>(DETAIL_SKTB)
        apiDetailSktb(detailSktb)
//        getDetailSktb(detailSktb)
        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_edit_sktb_detail.gone()
            btn_generate_sktb_detail.gone()
        }


        btn_generate_sktb_detail.attachTextChangeAnimator()
        bindProgressButton(btn_generate_sktb_detail)
        btn_generate_sktb_detail.setOnClickListener {
            btn_generate_sktb_detail.showProgress {
                progressColor = Color.WHITE
            }
            apiDocSktb(detailSktb)
            /*Handler(Looper.getMainLooper()).postDelayed({
                btn_generate_sktb_detail.hideProgress(getString(R.string.success_generate_doc))
                alert(getString(R.string.download)) {
                    positiveButton(getString(R.string.iya)) {
                        btn_generate_sktb_detail.hideProgress(R.string.generate_dokumen)
                    }
                    negativeButton(getString(R.string.tidak)) {
                        btn_generate_sktb_detail.hideProgress(R.string.generate_dokumen)

                    }
                }.show()
            }, 2000)*/
        }


    }

    private fun apiDocSktb(detailSktb: SktbMinResp?) {
        NetworkConfig().getServSktb()
            .docSktb("Bearer ${sessionManager1.fetchAuthToken()}", detailSktb?.id).enqueue(
                object : Callback<Base1Resp<AddSktbResp>> {
                    override fun onResponse(
                        call: Call<Base1Resp<AddSktbResp>>,
                        response: Response<Base1Resp<AddSktbResp>>
                    ) {
                        if (response.body()?.message == "Document sktb generated successfully") {
                            btn_generate_sktb_detail.hideProgress(R.string.success_generate_doc)
                            alert(getString(R.string.download)) {
                                positiveButton(getString(R.string.iya)) {
                                    downloadSktb(response.body()?.data?.sktb)
                                    btn_generate_sktb_detail.hideProgress(R.string.success_generate_doc)
                                }
                                negativeButton(getString(R.string.tidak)) {
                                    btn_generate_sktb_detail.hideProgress(R.string.generate_dokumen)
                                }
                            }.show()
                        } else {
                            btn_generate_sktb_detail.hideProgress(R.string.failed_generate_doc)
                        }
                    }

                    override fun onFailure(call: Call<Base1Resp<AddSktbResp>>, t: Throwable) {
                        Toast.makeText(this@DetailSktbActivity, "$t", Toast.LENGTH_SHORT).show()
                        btn_generate_sktb_detail.hideProgress(R.string.failed_generate_doc)
                    }
                })
    }

    private fun downloadSktb(sktb: SktbResp?) {
        Log.e("sktb:, ", "$sktb")
        val url = sktb?.dokumen?.url
        val filename: String = "${sktb?.no_sktb}.${sktb?.dokumen?.jenis}"
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
                btn_generate_sktb_detail.hideProgress(R.string.generate_dokumen)
                btn_generate_sktb_detail.showSnackbar(R.string.success_download_doc) {
                    action(R.string.action_ok) {
                        btn_generate_sktb_detail.hideProgress(R.string.generate_dokumen)
                    }
                }
            }
        }
    }

    private fun apiDetailSktb(detailSktb: SktbMinResp?) {
        NetworkConfig().getServSktb()
            .detailSktb("Bearer ${sessionManager1.fetchAuthToken()}", detailSktb?.id).enqueue(
                object :
                    Callback<SktbResp> {
                    override fun onResponse(call: Call<SktbResp>, response: Response<SktbResp>) {
                        if (response.isSuccessful) {
                            getDetailSktb(response.body())
                        } else {
                            Toast.makeText(
                                this@DetailSktbActivity, R.string.error, Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<SktbResp>, t: Throwable) {
                        Toast.makeText(this@DetailSktbActivity, "$t", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    @SuppressLint("SetTextI18n")
    private fun getDetailSktb(detailSktb: SktbResp?) {
        if (detailSktb?.is_ada_dokumen == 1) {
            btn_lihat_doc_sktb_detail.visible()
            btn_lihat_doc_sktb_detail.setOnClickListener {
                val uri = Uri.parse(detailSktb.dokumen?.url)
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri.toString())))
            }
        }
        btn_edit_sktb_detail.setOnClickListener {
            val intent = Intent(this, EditSktbActivity::class.java)
            intent.putExtra(EditSktbActivity.EDIT_SKTB, detailSktb)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        txt_no_sktb_detail.text = "No: ${detailSktb?.no_sktb}"
        txt_no_lp_sktb_detail.text = "No: ${detailSktb?.lp?.no_lp}"
        txt_kota_penetapan_sktb_detail.text = "Kota : ${detailSktb?.kota_penetapan}"
        txt_tanggal_penetapan_sktb_detail.text =
            "Tanggal : ${formatterTanggal(detailSktb?.tanggal_penetapan)}"
        txt_nama_pimpinan_sktb_detail.text = "Nama: ${detailSktb?.nama_kabid_propam}"
        txt_pangkat_nrp_sktb_detail.text =
            "Pangkat ${
                detailSktb?.pangkat_kabid_propam.toString()
                    .toUpperCase()
            }, NRP : ${detailSktb?.nrp_kabid_propam}"
        txt_tembusan_sktb_detail.text = detailSktb?.tembusan
    }

    override fun onResume() {
        super.onResume()
        apiDetailSktb(detailSktb)
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
                ApiDelete(detailSktb)
                finish()
            }
            negativeButton("Tidak") {
            }
        }.show()
    }

    private fun ApiDelete(detailSktb: SktbMinResp?) {
        NetworkConfig().getServSktb()
            .delSktb("Bearer ${sessionManager1.fetchAuthToken()}", detailSktb?.id).enqueue(
                object : Callback<BaseResp> {
                    override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                        if (response.body()?.message == "Data sktb removed succesfully") {
                            Toast.makeText(
                                this@DetailSktbActivity,
                                R.string.data_deleted,
                                Toast.LENGTH_SHORT
                            ).show()
                            Handler(Looper.getMainLooper()).postDelayed({
                                finish()
                            }, 750)
                        } else {
                            Toast.makeText(
                                this@DetailSktbActivity,
                                R.string.failed_deleted,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                        Toast.makeText(this@DetailSktbActivity, "$t", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    companion object {
        const val DETAIL_SKTB = "DETAIL_SKTB"
    }
}