package id.calocallo.sicape.ui.main.rehab.rpph

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
import kotlinx.android.synthetic.main.activity_detail_rpph.*
import kotlinx.android.synthetic.main.activity_detail_skhd.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DetailRpphActivity : BaseActivity() {

    private lateinit var downloadID: Any
    private lateinit var sessionManager1: SessionManager1
    private var detailRpph: RpphMinResp? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_rpph)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Data RPPH"
        sessionManager1 = SessionManager1(this)
        registerReceiver(onDownloadComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        detailRpph = intent.extras?.getParcelable<RpphMinResp>(DETAIL_RPPH)
        apiDetailRpph(detailRpph)
//        getViewRpph(detailRpph)

        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_edit_rpph_detail.gone()
            btn_generate_dok_rpph_detail.gone()
        }
        btn_generate_dok_rpph_detail.attachTextChangeAnimator()
        bindProgressButton(btn_generate_dok_rpph_detail)
        btn_generate_dok_rpph_detail.setOnClickListener {
            btn_generate_dok_rpph_detail.showProgress {
                progressColor = Color.WHITE
            }
            apiDocRpph(detailRpph)
            /*Handler(Looper.getMainLooper()).postDelayed({
                btn_generate_dok_rpph_detail.hideProgress(R.string.success_generate_doc)
                alert(R.string.download) {
                    positiveButton(R.string.iya) {
                        btn_generate_dok_rpph_detail.hideProgress(R.string.generate_dokumen)

                    }
                    negativeButton(R.string.tidak) {}
                }.show()
            }, 2000)*/
        }
    }

    private fun apiDocRpph(detailRpph: RpphMinResp?) {
        NetworkConfig().getServRpph()
            .docRpph("Bearer ${sessionManager1.fetchAuthToken()}", detailRpph?.id).enqueue(
                object : Callback<Base1Resp<AddRpphResp>> {
                    override fun onResponse(
                        call: Call<Base1Resp<AddRpphResp>>,
                        response: Response<Base1Resp<AddRpphResp>>
                    ) {
                        if (response.body()?.message == "Document rpph generated successfully") {
                            Log.e("response", "${response.body()?.data?.rpph}")
                            btn_generate_dok_rpph_detail.hideProgress(R.string.success_generate_doc)
                            btn_generate_dok_rpph_detail.hideProgress(R.string.success_generate_doc)
                            alert(R.string.download) {
                                positiveButton(R.string.iya) {
                                    downloadRpph(response.body()?.data?.rpph)
                                }
                                negativeButton(R.string.tidak) {
                                    btn_generate_dok_rpph_detail.hideProgress(R.string.generate_dokumen)
                                }
                            }.show()
                        } else {
                            btn_generate_dok_rpph_detail.hideProgress(R.string.failed_generate_doc)
                        }
                    }

                    override fun onFailure(call: Call<Base1Resp<AddRpphResp>>, t: Throwable) {
                        Toast.makeText(this@DetailRpphActivity, "$t", Toast.LENGTH_SHORT).show()
                        btn_generate_dok_rpph_detail.hideProgress(R.string.failed_generate_doc)
                    }
                })
    }

    private fun downloadRpph(rpph: RpphResp?) {
        Log.e("rpph", "$rpph")
        val url = rpph?.dokumen?.url
        val filename: String = "${rpph?.no_rpph}.${rpph?.dokumen?.jenis}"
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
                btn_generate_dok_rpph_detail.hideProgress(R.string.generate_dokumen)
                btn_generate_dok_rpph_detail.showSnackbar(R.string.success_download_doc) {
                    action(R.string.action_ok) {
                        btn_generate_dok_rpph_detail.hideProgress(R.string.generate_dokumen)
                    }
                }
            }
        }
    }

    private fun apiDetailRpph(detailRpph: RpphMinResp?) {
        NetworkConfig().getServRpph()
            .detailRpph("Bearer ${sessionManager1.fetchAuthToken()}", detailRpph?.id).enqueue(
                object :
                    Callback<RpphResp> {
                    override fun onResponse(call: Call<RpphResp>, response: Response<RpphResp>) {
                        if (response.isSuccessful) {
                            getViewRpph(response.body())
                        } else {
                            Toast.makeText(
                                this@DetailRpphActivity,
                                R.string.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<RpphResp>, t: Throwable) {
                        Toast.makeText(this@DetailRpphActivity, "$t", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    @SuppressLint("SetTextI18n")
    private fun getViewRpph(detailRpph: RpphResp?) {
        if (detailRpph?.is_ada_dokumen == 1) {
            btn_lihat_dok_rpph_detail.visible()
            btn_lihat_dok_rpph_detail.setOnClickListener {
                val uri = Uri.parse(detailRpph.dokumen?.url)
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri.toString())))
            }
        }
        btn_edit_rpph_detail.setOnClickListener {
            val intent = Intent(this, EditRpphActivity::class.java)
            intent.putExtra(EditRpphActivity.EDIT_RPPH_EDIT, detailRpph)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        txt_no_rpph_detail.text = "No: ${detailRpph?.no_rpph}"
        txt_no_lp_rpph_detail.text = "No: ${detailRpph?.lp?.no_lp}"
        txt_nama_dinas_rpph_detail.text = "Nama Dinas: ${detailRpph?.nama_dinas}"
        txt_no_nota_dinas_rpph_detail.text =
            "No Nota: ${detailRpph?.no_nota_dinas}, Tanggal: ${formatterTanggal(detailRpph?.tanggal_nota_dinas)}"
        txt_kota_penetapan_rpph_detail.text = "Kota: ${detailRpph?.kota_penetapan}"
        txt_tanggal_penetapan_rpph_detail.text =
            "Tanggal: ${formatterTanggal(detailRpph?.tanggal_penetapan)}"
        txt_penerima_salinan_keputusan_rpph_detail.text = detailRpph?.penerima_salinan_keputusan
        txt_nama_pimpinan_rpph_detail.text = "Nama : ${detailRpph?.nama_kabid_propam}"
        txt_pangkat_nrp_pimpinan_rpph_detail.text =
            "Pangkat : ${
                detailRpph?.pangkat_kabid_propam.toString().toUpperCase(Locale.ROOT)
            }, NRP : ${detailRpph?.nrp_kabid_propam}"
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
                ApiDelete(detailRpph)
                finish()
            }
            negativeButton("Tidak") {
            }
        }.show()
    }

    private fun ApiDelete(detailRpph: RpphMinResp?) {
        NetworkConfig().getServRpph()
            .delRpph("Bearer ${sessionManager1.fetchAuthToken()}", detailRpph?.id).enqueue(
                object : Callback<BaseResp> {
                    override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                        if (response.body()?.message == "Data rpph removed succesfully") {
                            Toast.makeText(
                                this@DetailRpphActivity,
                                R.string.data_deleted,
                                Toast.LENGTH_SHORT
                            ).show()
                            Handler(Looper.getMainLooper()).postDelayed({
                                finish()
                            }, 750)
                        } else {
                            Toast.makeText(
                                this@DetailRpphActivity,
                                R.string.failed_deleted,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                        Toast.makeText(this@DetailRpphActivity, "$t", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    companion object {
        const val DETAIL_RPPH = "DETAIL_RPPH"
    }

    override fun onResume() {
        super.onResume()
        apiDetailRpph(detailRpph)
    }
}