package id.calocallo.sicape.ui.gelar

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
import id.calocallo.sicape.ui.gelar.peserta_gelar.ListPesertaGelarActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.*
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_gelar.*
import kotlinx.android.synthetic.main.activity_detail_lp_pidana.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DetailGelarActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var dataLhg: LhgMinResp? = null
    private lateinit var downloadID: Any
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_gelar)
        sessionManager1 = SessionManager1(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Data Gelar Perkara"

        dataLhg = intent.getParcelableExtra(ListGelarActivity.DATA_LHG)
        registerReceiver(onDownloadComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        apiDetailLhg(dataLhg)
        buttonOnDetailLhg()

        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_peserta_gelar_detail_lhg.gone()
            btn_generate_doc_lhg.gone()
            btn_see_doc_lhg.gone()
        }
    }

    private fun buttonOnDetailLhg() {
        btn_peserta_gelar_detail_lhg.setOnClickListener {
            val intent = Intent(this, ListPesertaGelarActivity::class.java)
            intent.putExtra(DETAIL_LHG, dataLhg)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_generate_doc_lhg.attachTextChangeAnimator()
        bindProgressButton(btn_generate_doc_lhg)
        btn_generate_doc_lhg.setOnClickListener {
            btn_generate_doc_lhg.showProgress { progressColor = Color.WHITE }
            apiGenDocLhg(dataLhg)
        }
    }

    private fun apiGenDocLhg(dataLhg: LhgMinResp?) {
        NetworkConfig().getServLhg()
            .docLhg("Bearer ${sessionManager1.fetchAuthToken()}", dataLhg?.id).enqueue(
                object : Callback<Base1Resp<AddLhgResp>> {
                    override fun onResponse(
                        call: Call<Base1Resp<AddLhgResp>>,
                        response: Response<Base1Resp<AddLhgResp>>
                    ) {
                        if (response.body()?.message == "Document lhg generated successfully") {
                            Log.e("data", "${response.body()?.data}")
                            btn_generate_doc_lhg.hideProgress(R.string.success_generate_doc)
                            alert("Lihat Dokumen") {
                                positiveButton(R.string.iya) {
                                    downloadLhg(response.body()?.data?.lhg)
                                }
                                negativeButton(R.string.tidak) { btn_generate_doc_lhg.hideProgress(R.string.generate_dokumen) }
                            }.show()

                        } else {
                            btn_generate_doc_lhg.hideProgress(R.string.generate_dokumen)
                        }
                    }

                    override fun onFailure(call: Call<Base1Resp<AddLhgResp>>, t: Throwable) {
                        Toast.makeText(this@DetailGelarActivity, "$t", Toast.LENGTH_SHORT).show()
                        btn_generate_doc_lhg.hideProgress(R.string.failed_generate_doc)

                    }
                })
    }

    private fun downloadLhg(dok: LhgResp?) {
//        Log.e("asd", "${dok?.lhg}")
        val url = dok?.dokumen?.url
        val filename = "LHG-${dok?.id}.${dok?.dokumen?.jenis}"
        Log.e("url", "$url, $filename")
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
                btn_generate_doc_lhg.hideProgress(R.string.generate_dokumen)
                btn_generate_doc_lhg.showSnackbar(R.string.success_download_doc) {
                    action(R.string.action_ok) {
                        btn_generate_doc_lhg.hideProgress(R.string.generate_dokumen)
                    }
                }
            }
        }
    }

    private fun apiDetailLhg(dataLhg: LhgMinResp?) {
        NetworkConfig().getServLhg()
            .detailLhg("Bearer ${sessionManager1.fetchAuthToken()}", dataLhg?.id).enqueue(
                object :
                    Callback<LhgResp> {
                    override fun onResponse(call: Call<LhgResp>, response: Response<LhgResp>) {
                        if (response.isSuccessful) {
                            viewDetailLhg(response.body())
                        } else {
                            Toast.makeText(
                                this@DetailGelarActivity, R.string.error, Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<LhgResp>, t: Throwable) {
                        Toast.makeText(this@DetailGelarActivity, "$t", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    @SuppressLint("SetTextI18n")
    private fun viewDetailLhg(lhgResp: LhgResp?) {
        if (lhgResp?.is_ada_dokumen == 1) {
            btn_see_doc_lhg.visible()
            btn_see_doc_lhg.setOnClickListener {
                val uri = Uri.parse(lhgResp?.dokumen?.url)
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri.toString())))
            }
        }
        txt_pasal_kuh_pidana_detail_lhg.text = lhgResp?.pasal_kuh_pidana
        txt_dugaan_detail_lhg.text = lhgResp?.dugaan
        txt_dasar_detail_lhg.text = lhgResp?.dasar
        txt_tgl_waktu_detail_lhg.text = "Tanggal: ${formatterTanggal(lhgResp?.tanggal)}"
        txt_tempat_waktu_detail_lhg.text = "Tempat: ${lhgResp?.tempat}"

//        txt_personel_terduga_detail_lhg.text = "Nama: ${lhgResp?.lp}"

        txt_pimpinan_detail_lhg.text = "Nama: ${lhgResp?.nama_pimpinan}, Pangkat: ${
            lhgResp?.pangkat_pimpinan.toString().toUpperCase(Locale.ROOT)
        }, NRP: ${lhgResp?.nrp_pimpinan}"
        txt_pemapar_detail_lhg.text =
            "Nama: ${lhgResp?.nama_pemapar}, Pangkat: ${lhgResp?.pangkat_pemapar}"

        txt_waktu_mulai_pelaksanaan_detail_lhg.text = "Waktu Mulai: ${lhgResp?.waktu_mulai}"
        txt_waktu_selesai_pelaksanaan_detail_lhg.text = "Waktu Selesai: ${lhgResp?.waktu_selesai}"

        txt_kronologis_kasus_detail_lhg.text = lhgResp?.kronologis_kasus

        txt_personel_notulen_detail_lhg.text = "Nama: ${lhgResp?.nama_notulen}, Pangkat: ${
            lhgResp?.pangkat_notulen.toString().toUpperCase(Locale.ROOT)
        }, NRP: ${lhgResp?.nrp_notulen}"


        btn_edit_detail_lhg.setOnClickListener {
            val intent = Intent(this, EditGelarActivity::class.java)
            intent.putExtra(DETAIL_LHG, lhgResp)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
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
                ApiDelete(dataLhg)
                finish()
            }
            negativeButton("Tidak") {
            }
        }.show()
    }

    private fun ApiDelete(dataLhg: LhgMinResp?) {
        NetworkConfig().getServLhg()
            .delLhg("Bearer ${sessionManager1.fetchAuthToken()}", dataLhg?.id).enqueue(
                object : Callback<BaseResp> {
                    override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                        if (response.body()?.message == "Data lhg removed succesfully") {
                            Toast.makeText(
                                this@DetailGelarActivity, R.string.data_deleted, Toast.LENGTH_SHORT
                            ).show()
                            Handler(Looper.getMainLooper()).postDelayed({
                                finish()
                            }, 750)
                        } else if (response.body()?.message == "Data lhg has been used as reference in another data") {
                            Toast.makeText(
                                this@DetailGelarActivity,
                                R.string.used_on_references_lp,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this@DetailGelarActivity,
                                R.string.failed_deleted,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                        Toast.makeText(
                            this@DetailGelarActivity,
                            "$t",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
    }

    companion object {
        const val DETAIL_LHG = "DETAIL_LHG"
    }

    override fun onResume() {
        super.onResume()
        apiDetailLhg(dataLhg)
        buttonOnDetailLhg()
    }
}