package id.calocallo.sicape.ui.main.skhp

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
import kotlinx.android.synthetic.main.activity_detail_skhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DetailSkhpActivity : BaseActivity() {
    companion object {
        const val DETAIL_SKHP = "DETAIL_SKHP"
    }

    private lateinit var sessionManager1: SessionManager1
    private lateinit var downloadID: Any
    private var getDataSkhp: SkhpMinResp? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_skhp)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Data Surat Keterangan Hasil Penelitian"
        registerReceiver(onDownloadComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))


        sessionManager1 = SessionManager1(this)
        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_generate_skhp_detail.gone()
            btn_edit_skhp_detail.gone()
        }
        getDataSkhp = intent.extras?.getParcelable<SkhpMinResp>(DETAIL_SKHP)
        apiDetailSkhp(getDataSkhp)
//        getViewSkhp(getDataSkhp)
        btn_edit_skhp_detail.setOnClickListener {
            val intent = Intent(this, EditSkhpActivity::class.java)
            intent.putExtra(EditSkhpActivity.EDIT_SKHP, getDataSkhp)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_generate_skhp_detail.attachTextChangeAnimator()
        bindProgressButton(btn_generate_skhp_detail)
        btn_generate_skhp_detail.setOnClickListener {
            btn_generate_skhp_detail.showProgress {
                progressColor = Color.WHITE
            }
            apiDocSkhp(getDataSkhp)
/*            Handler(Looper.getMainLooper()).postDelayed({
                btn_generate_skhp_detail.hideProgress("Berhasil Generate Dokumen")
                alert("Download") {
                    positiveButton("Iya") {
                        btn_generate_skhp_detail.hideProgress(R.string.generate_dokumen)
                    }
                    negativeButton("Tidak") {
                        btn_generate_skhp_detail.hideProgress(R.string.generate_dokumen)
                    }
                }.show()
            }, 2000)*/


        }

    }

    private fun apiDocSkhp(dataSkhp: SkhpMinResp?) {
        NetworkConfig().getServSkhp()
            .docSkhp("Bearer ${sessionManager1.fetchAuthToken()}", dataSkhp?.id).enqueue(
                object : Callback<Base1Resp<AddSkhpResp>> {
                    override fun onResponse(
                        call: Call<Base1Resp<AddSkhpResp>>,
                        response: Response<Base1Resp<AddSkhpResp>>
                    ) {

                        if (response.body()?.message == "Document skhp generated successfully") {
                            btn_generate_skhp_detail.hideProgress(R.string.success_generate_doc)
                            alert("Download") {
                                positiveButton("Iya") {
                                    downloadSkhp(response.body()?.data?.skhp)
                                }
                                negativeButton("Tidak") {
                                    btn_generate_skhp_detail.hideProgress(R.string.generate_dokumen)
                                }
                            }.show()
                            btn_generate_skhp_detail.hideProgress(R.string.success_generate_doc)

                        } else {
                            btn_generate_skhp_detail.hideProgress(R.string.failed_generate_doc)

                        }
                    }

                    override fun onFailure(call: Call<Base1Resp<AddSkhpResp>>, t: Throwable) {
                        Toast.makeText(this@DetailSkhpActivity, "$t", Toast.LENGTH_SHORT).show()
                        btn_generate_skhp_detail.hideProgress(R.string.failed_generate_doc)
                    }
                })
    }

    private fun downloadSkhp(skhp: SkhpResp?) {
        Log.e("data", "${skhp?.dokumen}")
        val url = skhp?.dokumen?.url
        val filename: String = "${skhp?.no_skhp}.${skhp?.dokumen?.jenis}"
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
                btn_generate_skhp_detail.hideProgress(R.string.generate_dokumen)
                btn_generate_skhp_detail.showSnackbar(R.string.success_download_doc) {
                    action(R.string.action_ok) {
                        btn_generate_skhp_detail.hideProgress(R.string.generate_dokumen)
                    }
                }
            }
        }
    }

    private fun apiDetailSkhp(dataSkhp: SkhpMinResp?) {
        NetworkConfig().getServSkhp()
            .detailSkhp("Bearer ${sessionManager1.fetchAuthToken()}", dataSkhp?.id).enqueue(object :
                Callback<SkhpResp> {
                override fun onResponse(call: Call<SkhpResp>, response: Response<SkhpResp>) {
                    if (response.isSuccessful) {
                        getViewSkhp(response.body())
                    } else {
                        Toast.makeText(
                            this@DetailSkhpActivity, R.string.error, Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<SkhpResp>, t: Throwable) {
                    Toast.makeText(this@DetailSkhpActivity, "$t", Toast.LENGTH_SHORT).show()
                }
            })
    }

    @SuppressLint("SetTextI18n")
    private fun getViewSkhp(dataSkhp: SkhpResp?) {
        if (dataSkhp?.is_ada_dokumen == 1) {
            btn_lihat_doc_skhp_detail.visible()
            btn_lihat_doc_skhp_detail.setOnClickListener {
                val uri = Uri.parse(dataSkhp.dokumen?.url)
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri.toString())))
            }
        }
        txt_no_skhp_detail.text = dataSkhp?.no_skhp
        txt_nama_personel_skhp_detail.text = "Nama : ${dataSkhp?.personel?.nama}"

        when (dataSkhp?.personel?.jenis_kelamin) {
            "laki_laki" -> {
                txt_jk_personel_skhp_detail.text = "Jenis Kelamin : Laki-Laki"
            }
            "perempuan" -> {
                txt_jk_personel_skhp_detail.text = "Jenis Kelamin : Perempuan"
            }
        }
        when (dataSkhp?.personel?.agama_sekarang) {
            "islam" -> txt_agama_personel_skhp_detail.text = "Agama : Islam"
            "buddha" -> txt_agama_personel_skhp_detail.text = "Agama : Buddha"
            "katolik" -> txt_agama_personel_skhp_detail.text = "Agama : Katolik"
            "hindu" -> txt_agama_personel_skhp_detail.text = "Agama : Hindu"
            "protestan" -> txt_agama_personel_skhp_detail.text = "Agama : Protestan"
            "konghuchu" -> txt_agama_personel_skhp_detail.text = "Agama : Konghuchu"
        }

        txt_pangkat_nrp_personel_skhp_detail.text =
            "Pangkat : ${
                dataSkhp?.personel?.pangkat.toString()
                    .toUpperCase()
            } / ${dataSkhp?.personel?.nrp}"
        txt_jabatan_personel_skhp_detail.text = "Jabatan ${dataSkhp?.personel?.jabatan}"
        txt_kesatuan_personel_skhp_detail.text =
            "Kesatuan ${dataSkhp?.personel?.satuan_kerja?.kesatuan.toString().toUpperCase()}"

        if (dataSkhp?.is_memenuhi_syarat == 1) {
            txt_isi_skhp_detail.text =
                "Memenuhi syarat untuk usulan kenaikan pangkat setingkat lebih tinggi pada periode ${
                    formatterTanggal(dataSkhp.tanggal_kenaikan_pangkat)
                }"
        } else {
            txt_isi_skhp_detail.text = "Tidak Memenuhi Syarat"
        }
        txt_ttl_personel_skhp_detail.text = "TTL : ${
            dataSkhp?.personel?.tempat_lahir.toString().toUpperCase()
        },Tanggal ${formatterTanggal(dataSkhp?.personel?.tanggal_lahir)}"

        txt_kota_keluar_skhp_detail.text = "Kota ${dataSkhp?.kota_keluar}"

        txt_tanggal_keluar_skhp_detail.text =
            "Tanggal ${formatterTanggal(dataSkhp?.tanggal_keluar)}"

        txt_nama_pimpinan_skhp_detail.text = "Nama ${dataSkhp?.nama_kabid_propam}"
        txt_pangkat_nrp_pimpinan_skhp_detail.text =
            "Pangkat : ${
                dataSkhp?.pangkat_kabid_propam.toString().toUpperCase()
            } / ${dataSkhp?.nrp_kabid_propam}"

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
                ApiDelete(getDataSkhp)
                finish()
            }
            negativeButton("Tidak") {
            }
        }.show()
    }

    private fun ApiDelete(dataSkhp: SkhpMinResp?) {
        NetworkConfig().getServSkhp()
            .dellSkhp("Bearer ${sessionManager1.fetchAuthToken()}", dataSkhp?.id).enqueue(
                object : Callback<BaseResp> {
                    override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                        if (response.body()?.message == "Data skhp removed succesfully") {
                            Toast.makeText(
                                this@DetailSkhpActivity, R.string.data_deleted, Toast.LENGTH_SHORT
                            ).show()
                            Handler(Looper.getMainLooper()).postDelayed({
                                finish()
                            }, 750)
                        } else {
                            Toast.makeText(
                                this@DetailSkhpActivity, R.string.failed_deleted, Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                        Toast.makeText(this@DetailSkhpActivity, "$t", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    override fun onResume() {
        super.onResume()
        apiDetailSkhp(getDataSkhp)
    }
}
