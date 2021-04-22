package id.calocallo.sicape.ui.main.rehab.sp4

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
import kotlinx.android.synthetic.main.activity_detail_rpph.*
import kotlinx.android.synthetic.main.activity_detail_sktt.*
import kotlinx.android.synthetic.main.activity_detail_sp4.*
import kotlinx.android.synthetic.main.activity_detail_sp4.txt_no_lp_sktt_detail
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailSp4Activity : BaseActivity() {
    private lateinit var downloadID: Any
    private var getSp4: Sp4MinResp? = null
    private lateinit var sessionManager1: SessionManager1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_sp4)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Data SP4"
        sessionManager1 = SessionManager1(this)
        registerReceiver(onDownloadComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        getSp4 = intent.extras?.getParcelable<Sp4MinResp>(DETAIL_SP4)
        apiDetailSp4(getSp4)
//        getDataSp3View(getSp4)
        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_edit_sp4_detail.gone()
//            btn_generate_sp4_detail.gone()
        }
        btn_generate_sp4_detail.attachTextChangeAnimator()
        bindProgressButton(btn_generate_sp4_detail)
        btn_generate_sp4_detail.setOnClickListener {
            btn_generate_sp4_detail.showProgress {
                progressColor = Color.WHITE
            }
            apiDocSp4(getSp4)
            /*Handler(Looper.getMainLooper()).postDelayed({
                btn_generate_sp4_detail.hideProgress(R.string.success_generate_doc)
                alert(R.string.download) {
                    positiveButton(R.string.iya) {
                        btn_generate_sp4_detail.hideProgress(R.string.generate_dokumen)

                    }
                    negativeButton(R.string.tidak) {
                        btn_generate_sp4_detail.hideProgress(R.string.generate_dokumen)

                    }
                }.show()
            }, 2000)*/
        }

    }

    private fun apiDocSp4(sp4: Sp4MinResp?) {
        NetworkConfig().getServSp4().docSp4("Bearer ${sessionManager1.fetchAuthToken()}", sp4?.id)
            .enqueue(object : Callback<Base1Resp<AddSp4Resp>> {
                override fun onResponse(
                    call: Call<Base1Resp<AddSp4Resp>>,
                    response: Response<Base1Resp<AddSp4Resp>>
                ) {
                    if (response.body()?.message == "Document sp4 generated successfully") {
                        btn_generate_sp4_detail.hideProgress(R.string.success_generate_doc)
                        alert(R.string.download) {
                            positiveButton(R.string.iya) {
                                downloadSp4(response.body()?.data?.sp4)
                            }
                            negativeButton(R.string.tidak) {
                                btn_generate_sp4_detail.hideProgress(R.string.generate_dokumen)

                            }
                        }.show()
                    } else {
                        toast("${response.body()?.message}")
                        btn_generate_sp4_detail.hideProgress(R.string.failed_generate_doc)
                    }
                }

                override fun onFailure(call: Call<Base1Resp<AddSp4Resp>>, t: Throwable) {
                    Toast.makeText(this@DetailSp4Activity, "$t", Toast.LENGTH_SHORT).show()
                    btn_generate_sp4_detail.hideProgress(R.string.failed_generate_doc)
                }
            })

    }

    private fun downloadSp4(sp4: Sp4Resp?) {
        val url = sp4?.dokumen?.url
        val filename = "${sp4?.no_sp4}.${sp4?.dokumen?.jenis}"
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
                btn_generate_sp4_detail.hideProgress(R.string.generate_dokumen)
                btn_generate_sp4_detail.showSnackbar(R.string.success_download_doc) {
                    action(R.string.action_ok) {
                        btn_generate_sp4_detail.hideProgress(R.string.generate_dokumen)
                    }
                }
            }
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
        if (sp4?.is_ada_dokumen == 1) {
            btn_lihat_doc_sp4_detail.visible()
            btn_lihat_doc_sp4_detail.setOnClickListener {
                val uri = Uri.parse(sp4.dokumen?.url)
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri.toString())))
            }
        }
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
                            toast("${response.body()?.message}")

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