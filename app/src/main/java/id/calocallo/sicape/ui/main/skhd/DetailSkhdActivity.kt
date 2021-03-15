package id.calocallo.sicape.ui.main.skhd

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
import id.calocallo.sicape.ui.main.skhd.edit.EditSkhdActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.*
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_lp_pidana.*
import kotlinx.android.synthetic.main.activity_detail_skhd.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DetailSkhdActivity : BaseActivity() {

    companion object {
        const val DETAIL_SKHD = "DETAIL_SKHD"
    }

    private lateinit var downloadID: Any

    private lateinit var sessionManager1: SessionManager1
    private var detailSKHD: SkhdMinResp? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_skhd)

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail SKHD"
        sessionManager1 = SessionManager1(this)
        detailSKHD = intent.extras?.getParcelable<SkhdMinResp>(DETAIL_SKHD)
//        Log.e("detailSKHD", "$detailSKHD")
        registerReceiver(onDownloadComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        apiDetailSkhd(detailSKHD)
        val hakAkses = sessionManager1.fetchHakAkses()
        if (hakAkses == "operator") {
            btn_edit_skhd.gone()
            btn_generate_skhd.gone()
        }

        btn_edit_skhd.setOnClickListener {
            val intent = Intent(this, EditSkhdActivity::class.java)
            intent.putExtra(DETAIL_SKHD, detailSKHD)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        }

        btn_generate_skhd.attachTextChangeAnimator()
        bindProgressButton(btn_generate_skhd)
        btn_generate_skhd.setOnClickListener {
            btn_generate_skhd.showProgress {
                progressColor = Color.WHITE
            }
            apiDocSkhd(detailSKHD)
        }

    }

    private fun apiDocSkhd(detailSKHD: SkhdMinResp?) {
        NetworkConfig().getServSkhd()
            .docSkhd("Bearer ${sessionManager1.fetchAuthToken()}", detailSKHD?.id)
            .enqueue(object : Callback<Base1Resp<AddSkhdResp>> {
                override fun onResponse(
                    call: Call<Base1Resp<AddSkhdResp>>,
                    response: Response<Base1Resp<AddSkhdResp>>
                ) {
                    if (response.isSuccessful) {
                        btn_generate_skhd.hideProgress(R.string.success_generate_doc)
                        alert("Lihat Dokumen") {
                            positiveButton(R.string.iya) {
                                downloadSkhd(response?.body()?.data)
                            }
                            negativeButton(R.string.tidak) { btn_generate_pidana.hideProgress(R.string.generate_dokumen) }
                        }.show()
                    } else {

                    }
                }

                override fun onFailure(call: Call<Base1Resp<AddSkhdResp>>, t: Throwable) {
                    Toast.makeText(this@DetailSkhdActivity, "$t", Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun downloadSkhd(skhd: AddSkhdResp?) {
        Log.e("skhd", "${skhd?.skhd?.dokumen}")
        val url = skhd?.skhd?.dokumen?.url
        val filename: String = "${skhd?.skhd?.no_skhd}.${skhd?.skhd?.dokumen?.jenis}"
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
                btn_generate_skhd.showSnackbar(R.string.success_download_doc) { action(R.string.action_ok) {} }
            }
        }
    }

    private fun apiDetailSkhd(detailSKHD: SkhdMinResp?) {
        NetworkConfig().getServSkhd()
            .detailSkhd("Bearer ${sessionManager1.fetchAuthToken()}", detailSKHD?.id).enqueue(
                object :
                    Callback<SkhdResp> {
                    override fun onResponse(call: Call<SkhdResp>, response: Response<SkhdResp>) {
                        if (response.isSuccessful) {
                            getDetailSkhd(response.body())
                        } else {
                            Toast.makeText(
                                this@DetailSkhdActivity, R.string.error, Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<SkhdResp>, t: Throwable) {
                        Toast.makeText(
                            this@DetailSkhdActivity, "$t", Toast.LENGTH_SHORT
                        ).show()
                    }
                })
    }

    @SuppressLint("SetTextI18n")
    private fun getDetailSkhd(detailSKHD: SkhdResp?) {
        if (detailSKHD?.is_ada_dokumen == 1) {
            btn_see_doc_skhd.visible()
            btn_see_doc_skhd.setOnClickListener {
                val uri = Uri.parse(detailSKHD.dokumen?.url)
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri.toString())))
            }
        }

        txt_no_skhd_detail.text = detailSKHD?.no_skhd
        /*   txt_no_lhp_skhd_detail.text = detailSKHD?.lhp?.no_lhp*/
        txt_no_lp_skhd_detail.text = detailSKHD?.lp?.no_lp
        txt_bidang_skhd_detail.text = detailSKHD?.bidang.toString().toUpperCase()
        txt_berkas_skhd_detail.text = "Nomor : ${detailSKHD?.no_berkas_perkara} Tanggal ${
            formatterTanggal(detailSKHD?.tanggal_buat_berkas_perkara)
        }"
        txt_berkas_pemeriksaan_skhd_detail.text =
            "Tanggal : ${formatterTanggal(detailSKHD?.tanggal_sidang_disiplin)}"

        txt_hukuman_skhd_detail.text = detailSKHD?.hukuman
        txt_tanggal_disampaikan_skhd_detail.text =
            "Tanggal : ${formatterTanggal(detailSKHD?.tanggal_disampaikan_ke_terhukum)}"
        txt_waktu_disampaikan_skhd_detail.text =
            "Pukul : ${detailSKHD?.waktu_disampaikan_ke_terhukum}"
        txt_kota_penetapan_skhd_detail.text = "Kota : ${detailSKHD?.kota_penetapan}"
        txt_tanggal_penetapan_skhd_detail.text = "Tanggal : ${
            formatterTanggal(detailSKHD?.tanggal_penetapan)
        }"
        txt_nama_pimpinan_skhd_detail.text = "Nama : ${detailSKHD?.nama_yang_menetapkan}"
        txt_pangkat_nrp_pimpinan_skhd_detail.text =
            "Pangkat : ${
                detailSKHD?.pangkat_yang_menetapkan.toString()
                    .toUpperCase()
            }, NRP : ${detailSKHD?.nrp_yang_menetapkan}"
        txt_jabatan_pimpinan_skhd_detail.text = "Jabatan : ${detailSKHD?.jabatan_yang_menetapkan}"
        txt_kesatuan_pimpinan_skhd_detail.text =
            "Kesatuan : ${detailSKHD?.kesatuan_yang_menetapkan}"
        txt_tembusan_skhd_detail.text = detailSKHD?.tembusan

        /*personel terhukum*/
        txt_nama_personel_detail_skhd.text = "Nama: ${detailSKHD?.lp?.personel_terlapor?.nama}"
        txt_pangkat_personel_detail_skhd.text =
            "Pangkat: ${
                detailSKHD?.lp?.personel_terlapor?.pangkat.toString().toUpperCase(Locale.ROOT)
            }"
        txt_nrp_personel_detail_skhd.text = "NRP: ${detailSKHD?.lp?.personel_terlapor?.nrp}"
        txt_jabatan_personel_detail_skhd.text =
            "Jabatan: ${detailSKHD?.lp?.personel_terlapor?.jabatan}"
        txt_kesatuan_personel_detail_skhd.text =
            "Kesatuan: ${
                detailSKHD?.lp?.personel_terlapor?.satuan_kerja?.kesatuan.toString()
                    .toUpperCase(Locale.ROOT)
            }"
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
                ApiDelete(detailSKHD)
                finish()
            }
            negativeButton("Tidak") {
            }
        }.show()
    }

    private fun ApiDelete(detailSKHD: SkhdMinResp?) {
        NetworkConfig().getServSkhd()
            .delSkhd("Bearer ${sessionManager1.fetchAuthToken()}", detailSKHD?.id).enqueue(
                object : Callback<BaseResp> {
                    override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                        if (response.body()?.message == "Data skhd removed succesfully") {
                            Toast.makeText(
                                this@DetailSkhdActivity, R.string.data_deleted, Toast.LENGTH_SHORT
                            ).show()
                            Handler(Looper.getMainLooper()).postDelayed({
                                finish()
                            }, 750)
                        } else {
                            Toast.makeText(
                                this@DetailSkhdActivity, R.string.failed_deleted, Toast.LENGTH_SHORT
                            ).show()

                        }
                    }

                    override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                        Toast.makeText(this@DetailSkhdActivity, "$t", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    override fun onResume() {
        super.onResume()
        apiDetailSkhd(detailSKHD)
    }
}