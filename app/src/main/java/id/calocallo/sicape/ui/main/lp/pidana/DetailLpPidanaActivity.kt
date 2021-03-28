package id.calocallo.sicape.ui.main.lp.pidana

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.Uri
import android.os.*
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.ui.main.lp.pasal.ListPasalDilanggarActivity
import id.calocallo.sicape.ui.main.lp.pasal.ListPasalDilanggarActivity.Companion.EDIT_PASAL_DILANGGAR
import id.calocallo.sicape.ui.main.lp.pidana.EditLpPidanaActivity.Companion.EDIT_PIDANA
import id.calocallo.sicape.ui.main.lp.saksi.ListSaksiLpActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.*
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_lp_kke.*
import kotlinx.android.synthetic.main.activity_detail_lp_pidana.*
import kotlinx.android.synthetic.main.item_2_text.view.*
import kotlinx.android.synthetic.main.item_pasal_lp.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailLpPidanaActivity : BaseActivity() {
    companion object {
        const val DETAIL_PIDANA = "DETAIL_PIDANA"
        const val PERMISSION_STORAGE_CODE = 1000
    }

    private lateinit var downloadID: Any

    private lateinit var sessionManager1: SessionManager1
    private lateinit var adapterDetailPasalDilanggar: ReusableAdapter<PasalDilanggarResp>
    private lateinit var callbackDetailPasalDilanggar: AdapterCallback<PasalDilanggarResp>

    private var adapterDetailSaksiPidana = ReusableAdapter<LpSaksiResp>(this)
    private lateinit var callbackDetailSaksiPidana: AdapterCallback<LpSaksiResp>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lp_pidana)
        sessionManager1 = SessionManager1(this)
        adapterDetailPasalDilanggar = ReusableAdapter(this)
        registerReceiver(onDownloadComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))


        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Data Laporan Polisi Pidana"
        val pidana = intent.extras?.getParcelable<LpMinResp>(DETAIL_PIDANA)
        apiDetailPidana(pidana)
//        getDetailPidana(pidana)

        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_edit_pidana.gone()
            btn_edit_pasal_pidana.gone()
        }


        btn_generate_pidana.attachTextChangeAnimator()
        bindProgressButton(btn_generate_pidana)
        btn_generate_pidana.setOnClickListener {
            btn_generate_pidana.showProgress {
                progressColor = Color.WHITE
            }
            generateDoc(pidana)

        }

        btn_edit_pasal_pidana.setOnClickListener {
            val intent = Intent(this, ListPasalDilanggarActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            intent.putExtra(EDIT_PASAL_DILANGGAR, pidana)
            startActivity(intent)
        }
        btn_edit_saksi_pidana.setOnClickListener {
            val intent = Intent(this, ListSaksiLpActivity::class.java)
            intent.putExtra(ListSaksiLpActivity.EDIT_SAKSI, pidana)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }


    private fun generateDoc(pidana: LpMinResp?) {
        NetworkConfig().getServLp()
            .generateLp("Bearer ${sessionManager1.fetchAuthToken()}", pidana?.id)
            .enqueue(object : Callback<Base1Resp<DokLpResp>> {
                override fun onFailure(call: Call<Base1Resp<DokLpResp>>, t: Throwable) {
                    Toast.makeText(
                        this@DetailLpPidanaActivity,
                        R.string.error_conn,
                        Toast.LENGTH_SHORT
                    ).show()
                    btn_generate_pidana.hideProgress(R.string.failed_generate_doc)
                    Log.e("t", "$t")
                }

                override fun onResponse(
                    call: Call<Base1Resp<DokLpResp>>,
                    response: Response<Base1Resp<DokLpResp>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.message == "Document lp generated successfully") {
                            saveDocLpPidana(response.body()?.data?.lp)
                        } else {
                            btn_generate_pidana.hideProgress(R.string.failed_generate_doc)

                        }
                    } else {
                        toast("${response.body()?.message}")

                        btn_generate_pidana.hideProgress(R.string.failed_generate_doc)

                    }
                }
            })
    }

    private fun saveDocLpPidana(dok: LpResp?) {
        Log.e("urlDok", "${dok?.dokumen?.url}")
        Handler(Looper.getMainLooper()).postDelayed({
            btn_generate_pidana.hideProgress(R.string.success_generate_doc)
            alert(R.string.download) {
                positiveButton(R.string.iya) {
                    downloadDok(dok)

                }
                negativeButton(R.string.tidak) {
                    btn_generate_pidana.hideProgress(R.string.generate_dokumen)
                }
            }.show()
        }, 2000)
    }

    private fun downloadDok(dok: LpResp?) {
        //getting url from user
        val url = dok?.dokumen?.url
        //getting filename
        val filename: String = "${dok?.no_lp}.${dok?.dokumen?.jenis}"
        //download request

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
                btn_generate_pidana.hideProgress(R.string.generate_dokumen)
                btn_generate_pidana.showSnackbar(R.string.success_download_doc) {
                    action(R.string.action_ok) {
                        btn_generate_pidana.hideProgress(R.string.generate_dokumen)
                    }
                }
            }
        }
    }


    private fun gotoBrowser(dok: LpResp?) {
        val uri = Uri.parse(dok?.dokumen?.url)
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri.toString())))

    }

/*   private fun downloadDok(lp: LpResp?) {
       val folder = File(Environment.DIRECTORY_DOWNLOADS + "/" + "LP")
       if (!folder.exists()) {
           folder.mkdirs()
       }

       val fileName = "PIDANA_${lp?.no_lp}.${lp?.dokumen?.jenis}"
       val urlOfTheFile = lp?.dokumen?.url
       urlOfTheFile?.let {
           FileDownloadManager.initDownload(
               this,
               it,
               folder.absolutePath,
               fileName
           )
       }
//        val fileName = SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(Date()) + ".mp3"
   }*/

    private fun apiDetailPidana(pidana: LpMinResp?) {
        NetworkConfig().getServLp()
            .getLpById("Bearer ${sessionManager1.fetchAuthToken()}", pidana?.id).enqueue(object :
                Callback<LpResp> {
                override fun onFailure(call: Call<LpResp>, t: Throwable) {
                    Log.e("t", "$t")
                    Toast.makeText(this@DetailLpPidanaActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(
                    call: Call<LpResp>,
                    response: Response<LpResp>
                ) {
                    if (response.isSuccessful) {
                        getDetailPidana(response.body())
                        Log.e("response", "${response.body()}")
                    } else {
                        Toast.makeText(
                            this@DetailLpPidanaActivity, "Error Koneksi", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
    }

    @SuppressLint("SetTextI18n")
    private fun getDetailPidana(pidana: LpResp?) {
        /*if (pidana?.status_pelapor == "polisi") {
            ll_detail_personel_pidana.visible()
            ll_detail_sipil_pidana.gone()
        } else {
            ll_detail_personel_pidana.gone()
            ll_detail_sipil_pidana.visible()
        }*/
        if(pidana?.lhp != null){
            ll_ref_lp_detail.visible()
            txt_ref_lp_detail.text = "No LHP: ${pidana?.lhp?.no_lhp}"
        }
        //general
        txt_detail_no_lp.text = "No LP : ${pidana?.no_lp}"
//        txt_detail_pembukaan_laporan_pidana.text = pidana?.detail_laporan?.pembukaan_laporan
        txt_detail_isi_laporan_pidana.text = pidana?.detail_laporan?.isi_laporan
        txt_detail_kota_buat_pidana.text = "Kota : ${pidana?.kota_buat_laporan}"
        txt_detail_tgl_buat_pidana.text =
            "Tanggal : ${formatterTanggal(pidana?.tanggal_buat_laporan)}"

        //pimpinan
        txt_detail_nama_pimpinan_pidana.text = "Nama : ${pidana?.nama_yang_mengetahui}"
        txt_detail_pangkat_nrp_pimpinan_pidana.text =
            "Pangkat : ${
                pidana?.pangkat_yang_mengetahui.toString().toUpperCase()
            }," + " NRP : ${pidana?.nrp_yang_mengetahui}"
        txt_detail_jabatan_pimpinan_pidana.text =
            "Jabatan : ${pidana?.jabatan_yang_mengetahui}"


        //terlapor
        when {
            pidana?.personel_terlapor != null -> {
                txt_detail_nama_terlapor.text = "Nama : ${pidana.personel_terlapor?.nama}"
                txt_detail_pangkat_nrp_terlapor.text =
                    "Pangkat : ${pidana.personel_terlapor?.pangkat.toString().toUpperCase()}," +
                            " NRP : ${pidana.personel_terlapor?.nrp}"
                txt_detail_jabatan_terlapor.text = "Jabatan : ${pidana.personel_terlapor?.jabatan}"
                txt_detail_kesatuan_terlapor.text =
                    "Kesatuan : ${
                        pidana.personel_terlapor?.satuan_kerja?.kesatuan.toString().toUpperCase()
                    }"
            }
            pidana?.personel_terlapor_lhp != null -> {
                txt_detail_nama_terlapor.text = "Nama : ${pidana.personel_terlapor_lhp?.personel?.nama}"
                txt_detail_pangkat_nrp_terlapor.text =
                    "Pangkat : ${pidana.personel_terlapor_lhp?.personel?.pangkat.toString().toUpperCase()}," +
                            " NRP : ${pidana.personel_terlapor_lhp?.personel?.nrp}"
                txt_detail_jabatan_terlapor.text =
                    "Jabatan : ${pidana.personel_terlapor_lhp?.personel?.jabatan}"
                txt_detail_kesatuan_terlapor.text =
                    "Kesatuan : ${
                        pidana.personel_terlapor_lhp?.personel?.satuan_kerja?.kesatuan.toString()
                            .toUpperCase()
                    }"
            }
        }


        /*  //pelapor
          txt_detail_nama_pelapor.text = "Nama : ${pidana?.detail_laporan?.nama_pelapor}"
          txt_detail_pangkat_nrp_pelapor.text =
              "Pangkat : ${pidana?.detail_laporan?.personel_pelapor?.pangkat.toString()
                  .toUpperCase()}, NRP : ${pidana?.detail_laporan?.personel_pelapor?.nrp}"
          txt_detail_jabatan_pelapor.text = "Jabatan : ${pidana?.detail_laporan?.personel_pelapor?.jabatan}"
          txt_detail_kesatuan_pelapor.text =
              "Kesatuan : ${pidana?.detail_laporan?.personel_pelapor?.satuan_kerja?.kesatuan.toString().toUpperCase()}"*/
        if (pidana?.status_pelapor == "sipil") {
            //sipil
            ll_detail_personel_pidana.gone()
            ll_detail_sipil_pidana.visible()
            txt_detail_nama_sipil.text = "Nama :  ${pidana?.nama_pelapor}"
            txt_detail_agama_sipil.text = "Agama : ${pidana?.agama_pelapor}"
            txt_detail_pekerjaan_sipil.text = "Pekerjaan : ${pidana?.pekerjaan_pelapor}"
            txt_detail_kwg_sipil.text =
                "Kewarganegaraan : ${pidana?.kewarganegaraan_pelapor}"
            txt_detail_alamat_sipil.text = "Alamat : ${pidana?.alamat_pelapor}"
            txt_detail_no_telp_sipil.text = "No Telepon : ${pidana?.no_telp_pelapor}"
            txt_detail_nik_sipil.text = "NIK KTP : ${pidana?.nik_ktp_pelapor}"
            txt_detail_jk_sipil.text =
                "Jenis Kelamin : ${pidana?.jenis_kelamin_pelapor}"
            txt_detail_ttl_sipil.text =
                "TTL : ${pidana?.tempat_lahir_pelapor}, ${formatterDiffTanggal(pidana?.tanggal_lahir_pelapor)}"
        } else {
            ll_detail_personel_pidana.visible()
            ll_detail_sipil_pidana.gone()
            txt_detail_nama_pelapor.text = "Nama: ${pidana?.personel_pelapor?.nama}"
            txt_detail_pangkat_nrp_pelapor.text = "Pangkat: ${
                pidana?.personel_pelapor?.pangkat.toString().toUpperCase()
            }, NRP: ${pidana?.personel_pelapor?.nrp}"
            txt_detail_jabatan_pelapor.text = "Jabatan: ${pidana?.personel_pelapor?.jabatan}"
            txt_detail_kesatuan_pelapor.text =
                "Kesatuan: ${pidana?.personel_pelapor?.satuan_kerja?.kesatuan}"
        }


        //setPasal
        callbackDetailPasalDilanggar = object : AdapterCallback<PasalDilanggarResp> {
            override fun initComponent(itemView: View, data: PasalDilanggarResp, itemIndex: Int) {
                itemView.txt_item_1.text = data.pasal?.nama_pasal
            }

            override fun onItemClicked(itemView: View, data: PasalDilanggarResp, itemIndex: Int) {
            }
        }
        pidana?.pasal_dilanggar?.let {
            adapterDetailPasalDilanggar.adapterCallback(callbackDetailPasalDilanggar)
                .isVerticalView().addData(it)
                .setLayout(R.layout.item_pasal_lp).build(rv_detail_lp_pidana_pasal)

        }
        //saksi
        callbackDetailSaksiPidana = object : AdapterCallback<LpSaksiResp> {
            override fun initComponent(itemView: View, data: LpSaksiResp, itemIndex: Int) {
                if (data.status_saksi == "personel") {
                    itemView.txt_detail_1.text = data.personel?.nama
                    itemView.txt_detail_2.text = "Personel"
                } else {
                    itemView.txt_detail_1.text = data.nama
                    if (data.is_korban == 0) {
                        itemView.txt_detail_2.text = "Saksi"
                    } else {
                        itemView.txt_detail_2.text = "Korban"
                    }
                }

            }

            override fun onItemClicked(itemView: View, data: LpSaksiResp, itemIndex: Int) {}
        }
        pidana?.saksi?.let {
            adapterDetailSaksiPidana.adapterCallback(callbackDetailSaksiPidana)
                .isVerticalView().addData(it).setLayout(R.layout.item_2_text)
                .build(rv_detail_saksi_pidana)
        }

        /*button DOK*/
        if (pidana?.is_ada_dokumen == 0) {
            btn_lihat_dok_pidana.gone()
        } else {
            btn_lihat_dok_pidana.visible()
            btn_lihat_dok_pidana.setOnClickListener {
                gotoBrowser(pidana)
            }
        }

        btn_edit_pidana.setOnClickListener {
            val intent = Intent(this, EditLpPidanaActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            intent.putExtra(EDIT_PIDANA, pidana)
            startActivity(intent)
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
                ApiDelete()
//                finish()
            }
            negativeButton("Tidak") {
            }
        }.show()
    }

    private fun ApiDelete() {
        val pidana = intent.extras?.getParcelable<LpMinResp>(DETAIL_PIDANA)
        NetworkConfig().getServLp()
            .delLp("Bearer ${sessionManager1.fetchAuthToken()}", pidana?.id)
            .enqueue(object : Callback<BaseResp> {
                override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                    Toast.makeText(
                        this@DetailLpPidanaActivity,
                        R.string.error_conn,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                    if (response.isSuccessful) {
                        if (response.body()?.message == "Data lp removed succesfully") {
                            Toast.makeText(
                                this@DetailLpPidanaActivity,
                                R.string.data_deleted,
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else if (response.body()?.message == "Data lp has been used as reference in another data") {
                            Toast.makeText(
                                this@DetailLpPidanaActivity,
                                R.string.used_on_references_lp,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@DetailLpPidanaActivity,
                            "${response.body()?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
    }

    override fun onResume() {
        super.onResume()
        val pidana = intent.extras?.getParcelable<LpMinResp>(DETAIL_PIDANA)
        apiDetailPidana(pidana)
    }
}