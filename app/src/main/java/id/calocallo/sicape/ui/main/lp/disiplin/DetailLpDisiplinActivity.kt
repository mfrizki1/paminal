package id.calocallo.sicape.ui.main.lp.disiplin

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
import id.calocallo.sicape.ui.main.lp.saksi.ListSaksiLpActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.*
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.ui.main.lhp.EditLhpActivity
import id.calocallo.sicape.ui.main.lhp.edit.saksi.PickEditSaksiLhpActivity
import kotlinx.android.synthetic.main.activity_detail_lp_disiplin.*
import kotlinx.android.synthetic.main.activity_detail_lp_disiplin.*
import kotlinx.android.synthetic.main.activity_detail_lp_pidana.*
import kotlinx.android.synthetic.main.item_2_text.view.*
import kotlinx.android.synthetic.main.item_pasal_lp.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DetailLpDisiplinActivity : BaseActivity() {
    private lateinit var downloadID: Any

    private lateinit var sessionManager1: SessionManager1
    private var adapterDetailPasalDisiplin = ReusableAdapter<PasalDilanggarResp>(this)
    private lateinit var callbackDetailPasalDilanggarDisiplin: AdapterCallback<PasalDilanggarResp>

    private var adapterDetailSaksiDisp = ReusableAdapter<LpSaksiResp>(this)
    private lateinit var callbackDetailSaksiDisp: AdapterCallback<LpSaksiResp>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lp_disiplin)
        sessionManager1 = SessionManager1(this)
        adapterDetailPasalDisiplin = ReusableAdapter(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Laporan Polisi Disiplin"
        //get item disiplin
        val disiplin = intent.extras?.getParcelable<LpMinResp>(DETAIL_DISIPLIN)
        apiDetailDisiplin(disiplin)

        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_edit_disiplin.gone()
            btn_edit_pasal_disiplin.gone()
        }
        btn_edit_pasal_disiplin.setOnClickListener {
            val intent = Intent(this, ListPasalDilanggarActivity::class.java)
            intent.putExtra(ListPasalDilanggarActivity.EDIT_PASAL_DILANGGAR, disiplin)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent)
        }
        btn_generate_disiplin.attachTextChangeAnimator()
        bindProgressButton(btn_generate_disiplin)
        btn_generate_disiplin.setOnClickListener {
            btn_generate_disiplin.showProgress {
                progressColor = Color.WHITE
            }
            apiGenerateDoc(disiplin)
        }
        registerReceiver(onDownloadComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

    }

    private fun apiGenerateDoc(disiplin: LpMinResp?) {
        NetworkConfig().getServLp()
            .generateLp("Bearer ${sessionManager1.fetchAuthToken()}", disiplin?.id)
            .enqueue(object : Callback<Base1Resp<DokLpResp>> {
                override fun onFailure(call: Call<Base1Resp<DokLpResp>>, t: Throwable) {
                    Toast.makeText(
                        this@DetailLpDisiplinActivity,
                        R.string.error_conn,
                        Toast.LENGTH_SHORT
                    ).show()
                    btn_generate_disiplin.hideProgress(R.string.failed_generate_doc)
                }

                override fun onResponse(
                    call: Call<Base1Resp<DokLpResp>>,
                    response: Response<Base1Resp<DokLpResp>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.message == "Document lp generated successfully") {
                            saveDocLpDisiplin(response.body()?.data?.lp)
                        }
                    } else {
                        toast("${response.body()?.message}")
                        btn_generate_disiplin.hideProgress(R.string.failed_generate_doc)
                    }
                }
            })
    }

    private fun saveDocLpDisiplin(lp: LpResp?) {
        Handler(Looper.getMainLooper()).postDelayed({
            btn_generate_disiplin.hideProgress(R.string.success_generate_doc)
            alert(R.string.download) {
                positiveButton(R.string.iya) {
                    downloadDok(lp)
                }
                negativeButton(R.string.tidak) {
                    btn_generate_disiplin.hideProgress(R.string.generate_dokumen)
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
                btn_generate_disiplin.hideProgress(R.string.generate_dokumen)
                btn_generate_disiplin.showSnackbar(R.string.success_download_doc) {
                    action(R.string.action_ok) {
                        btn_generate_disiplin.hideProgress(R.string.generate_dokumen)
                    }
                }
            }
        }
    }

    private fun viewDocDispl(lp: LpResp?) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(lp?.dokumen?.url)))
//        finish()
    }

    private fun apiDetailDisiplin(disiplin: LpMinResp?) {
        NetworkConfig().getServLp()
            .getLpById("Bearer ${sessionManager1.fetchAuthToken()}", disiplin?.id).enqueue(object :
                Callback<LpResp> {
                override fun onFailure(call: Call<LpResp>, t: Throwable) {
                    Toast.makeText(
                        this@DetailLpDisiplinActivity, "Error Koneksi", Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResponse(
                    call: Call<LpResp>,
                    response: Response<LpResp>
                ) {
                    if (response.isSuccessful) {
                        getViewDisiplin(response.body())
                    } else {
                        Toast.makeText(
                            this@DetailLpDisiplinActivity,
                            "Error Koneksi",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            })
    }


    @SuppressLint("SetTextI18n")
    private fun getViewDisiplin(disiplin: LpResp?) {
        Log.e("disiplin", "$disiplin")
        btn_edit_saksi_disiplin.setOnClickListener {
            if (disiplin?.is_ada_lhp == 1) {
                toast("Edit Data Saksi DI Fitur LHP")
                val intent = Intent(this, PickEditSaksiLhpActivity::class.java)
                intent.putExtra(EditLhpActivity.EDIT_LHP, disiplin.lhp)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            } else {
                val intent = Intent(this, ListSaksiLpActivity::class.java)
                intent.putExtra(ListSaksiLpActivity.EDIT_SAKSI, disiplin)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
        when {
            disiplin?.personel_terlapor != null -> {
                txt_detail_nama_terlapor_disiplin.text =
                    "Nama : ${disiplin.personel_terlapor?.nama}"
                txt_detail_pangkat_nrp_terlapor_disiplin.text =
                    "Pangkat : ${
                        disiplin.personel_terlapor?.pangkat.toString()
                            .toUpperCase()
                    }, NRP : ${disiplin.personel_terlapor?.nrp}"
                txt_detail_jabatan_terlapor_disiplin.text =
                    "Jabatan : ${disiplin.personel_terlapor?.jabatan}"
                txt_detail_kesatuan_terlapor_disiplin.text =
                    "Kesatuan : ${
                        disiplin.personel_terlapor?.satuan_kerja?.kesatuan.toString()
                            .toUpperCase()
                    }"
            }
            disiplin?.personel_terlapor_lhp != null -> {
                txt_detail_nama_terlapor_disiplin.text =
                    "Nama : ${disiplin.personel_terlapor_lhp?.personel?.nama}"
                txt_detail_pangkat_nrp_terlapor_disiplin.text =
                    "Pangkat : ${
                        disiplin.personel_terlapor_lhp?.personel?.pangkat.toString()
                            .toUpperCase()
                    }, NRP : ${disiplin.personel_terlapor_lhp?.personel?.nrp}"
                txt_detail_jabatan_terlapor_disiplin.text =
                    "Jabatan : ${disiplin.personel_terlapor_lhp?.personel?.jabatan}"
                txt_detail_kesatuan_terlapor_disiplin.text =
                    "Kesatuan : ${
                        disiplin.personel_terlapor_lhp?.personel?.satuan_kerja?.kesatuan.toString()
                            .toUpperCase()
                    }"
            }
        }
        when (disiplin?.status_pelapor) {
            "sipil" -> {
                ll_detail_sipil_disiplin.visible()
                txt_detail_nama_sipil_disiplin.text = "Nama: ${disiplin.nama_pelapor}"
                txt_detail_ttl_sipil_disiplin.text =
                    "TTL: ${disiplin.tempat_lahir_pelapor}, ${formatterDiffTanggal(disiplin.tanggal_lahir_pelapor)}"
                txt_detail_jk_sipil_disiplin.text =
                    "Jenis Kelamin: ${disiplin.jenis_kelamin_pelapor}"
                txt_detail_agama_sipil_disiplin.text = "Agama: ${disiplin.agama_pelapor}"
                txt_detail_pekerjaan_sipil_disiplin.text =
                    "Pekerjaan: ${disiplin.pekerjaan_pelapor}"
                txt_detail_kwg_sipil_disiplin.text =
                    "Kewarganegaraan: ${disiplin.kewarganegaraan_pelapor}"
                txt_detail_alamat_sipil_disiplin.text = "Alamat: ${disiplin.nama_pelapor}"
                txt_detail_no_telp_sipil_disiplin.text = "No Telepon: ${disiplin.nama_pelapor}"
                txt_detail_nik_sipil_disiplin.text = "NIK KTP: ${disiplin.nama_pelapor}"
            }
            "personel" -> {
                ll_detail_personel_disiplin.visible()
                Log.e("personelDisiplin", "${disiplin.personel_pelapor}")
                txt_detail_nama_pelapor_disiplin.text = "Nama: ${disiplin.personel_pelapor?.nama}"
                txt_detail_pangkat_nrp_pelapor_disiplin.text =
                    "Pangkat : ${
                        disiplin.personel_pelapor?.pangkat.toString()
                            .toUpperCase()
                    }, NRP : ${disiplin.personel_pelapor?.nrp}"
                txt_detail_jabatan_pelapor_disiplin.text =
                    "Jabatan : ${disiplin.personel_pelapor?.jabatan}"
                txt_detail_kesatuan_pelapor_disiplin.text =
                    "Kesatuan : ${
                        disiplin.personel_pelapor?.satuan_kerja?.kesatuan.toString()
                            .toUpperCase()
                    }"
            }
        }

        if (disiplin?.is_ada_lhp == 1) {
            ll_ref_lp_detail_disiplin.visible()
            txt_ref_lp_detail_disiplin.text = "No LHP: ${disiplin.lhp?.no_lhp}"
        }

        //general
        txt_detail_no_lp_disiplin.text = disiplin?.no_lp
        txt_detail_macam_pelanggaran_disiplin.text = disiplin?.detail_laporan?.macam_pelanggaran
        txt_detail_kota_buat_disiplin.text = "Kota : ${disiplin?.kota_buat_laporan}"
        txt_detail_tgl_buat_disiplin.text =
            "Tanggal : ${formatterTanggal(disiplin?.tanggal_buat_laporan)}"
        txt_detail_rincian_pelanggaran_disiplin.text =
            disiplin?.detail_laporan?.rincian_pelanggaran_disiplin
        //pimpinan
        txt_detail_nama_pimpinan_disiplin.text =
            "Nama : ${disiplin?.nama_yang_mengetahui}"
        txt_detail_pangkat_nrp_pimpinan_disiplin.text =
            "Pangkat : ${
                disiplin?.pangkat_yang_mengetahui.toString()
                    .toUpperCase(Locale.ROOT)
            }, NRP : ${disiplin?.nrp_yang_mengetahui}"
        txt_detail_jabatan_pimpinan_disiplin.text =
            "Jabatan : ${disiplin?.jabatan_yang_mengetahui}"

        txt_detail_keterangan_pelapor_disiplin.text = disiplin?.detail_laporan?.keterangan_pelapor
        txt_detail_kronologis_pelapor_disiplin.text =
            disiplin?.detail_laporan?.kronologis_dari_pelapor

        //pasal
        callbackDetailPasalDilanggarDisiplin = object : AdapterCallback<PasalDilanggarResp> {
            override fun initComponent(itemView: View, data: PasalDilanggarResp, itemIndex: Int) {
                itemView.txt_item_1.text = data.pasal?.nama_pasal

            }

            override fun onItemClicked(itemView: View, data: PasalDilanggarResp, itemIndex: Int) {
            }
        }
        disiplin?.pasal_dilanggar?.let {
            adapterDetailPasalDisiplin.adapterCallback(callbackDetailPasalDilanggarDisiplin)
                .isVerticalView().addData(it).setLayout(R.layout.item_pasal_lp)
                .build(rv_detail_pasal_disiplin)
        }

        //saksi
        callbackDetailSaksiDisp = object : AdapterCallback<LpSaksiResp> {
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

            override fun onItemClicked(itemView: View, data: LpSaksiResp, itemIndex: Int) {
            }
        }
        disiplin?.saksi?.let {
            adapterDetailSaksiDisp.adapterCallback(callbackDetailSaksiDisp)
                .isVerticalView().addData(it).setLayout(R.layout.item_2_text)
                .build(rv_detail_saksi_disiplin)
        }

        /*buton lihat dok*/
        if (disiplin?.is_ada_dokumen == 0) {
            btn_lihat_dok_disiplin.gone()
        } else {
            btn_lihat_dok_disiplin.visible()
            btn_lihat_dok_disiplin.setOnClickListener {
                viewDocDispl(disiplin)
            }

        }
        btn_edit_disiplin.setOnClickListener {
            val intent = Intent(this, EditLpDisiplinActivity::class.java)
            intent.putExtra(EditLpDisiplinActivity.EDIT_DISIPLIN, disiplin)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
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
        this.alert("Hapus Data", "Yakin Hapus?") {
            positiveButton("Iya") {
                ApiDelete()

            }
            negativeButton("Tidak") {
            }
        }.show()
    }

    private fun ApiDelete() {
        val disiplin = intent.extras?.getParcelable<LpMinResp>(DETAIL_DISIPLIN)
        NetworkConfig().getServLp()
            .delLp("Bearer ${sessionManager1.fetchAuthToken()}", disiplin?.id)
            .enqueue(object : Callback<BaseResp> {
                override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                    Toast.makeText(
                        this@DetailLpDisiplinActivity,
                        R.string.error_conn,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                    if (response.isSuccessful) {
                        if (response.body()?.message == "Data lp removed succesfully") {
                            Toast.makeText(
                                this@DetailLpDisiplinActivity,
                                R.string.data_deleted,
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else if (response.body()?.message == "Data lp has been used as reference in another data") {
                            Toast.makeText(
                                this@DetailLpDisiplinActivity,
                                R.string.used_on_references_lp,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@DetailLpDisiplinActivity,
                            "${response.body()?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
    }

    override fun onResume() {
        super.onResume()
        val disiplin = intent.extras?.getParcelable<LpMinResp>(DETAIL_DISIPLIN)
        apiDetailDisiplin(disiplin)
    }

    companion object {
        const val DETAIL_DISIPLIN = "DETAIL_DISIPLIN"
    }
}