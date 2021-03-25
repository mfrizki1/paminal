package id.calocallo.sicape.ui.main.lhp

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
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.model.*
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.ui.main.lhp.edit.ref_penyelidikan.ListDetailRefPenyelidikanActivity
import id.calocallo.sicape.ui.main.lhp.edit.lidik.PickLidikLhpActivity
import id.calocallo.sicape.ui.main.lhp.edit.saksi.PickEditSaksiLhpActivity
import id.calocallo.sicape.ui.main.lhp.edit.terlapor.PickTerlaporLhpActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.*
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_lhp.*
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
import java.util.ArrayList

class DetailLhpActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1

    private var adapterRefLP = ReusableAdapter<RefPenyelidikanResp>(this)
    private lateinit var callbackRefLP: AdapterCallback<RefPenyelidikanResp>

    private var adapterLidik = ReusableAdapter<PersonelPenyelidikResp>(this)
    private lateinit var callbackLidk: AdapterCallback<PersonelPenyelidikResp>

    private var adapterSaksi = ReusableAdapter<SaksiLhpResp>(this)
    private lateinit var callbackSaksi: AdapterCallback<SaksiLhpResp>
    private var adapterketTerlapor = ReusableAdapter<KetTerlaporLhpResp>(this)
    private lateinit var callbackketTerlapor: AdapterCallback<KetTerlaporLhpResp>
    var terbukti = ""
    private lateinit var downloadID: Any

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lhp)
        sessionManager1 = SessionManager1(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Laporan Hasil Penyelidikan"
        registerReceiver(onDownloadComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        val dataLhp = intent.extras?.getParcelable<LhpMinResp>(DETAIL_LHP)
        apiDetailLhp(dataLhp)
//        getDetailLHP(dataLhp)


        val hakAkses = sessionManager1.fetchHakAkses()
        if (hakAkses == "operator") {
            btn_edit_lhp.toggleVisibility()
            btn_edit_lidik_lhp.toggleVisibility()
            btn_edit_saksi_lhp.toggleVisibility()
            btn_edit_ket_terlapor_lhp.toggleVisibility()
        }
        btn_edit_lhp.setOnClickListener {
            val intent = Intent(this, EditLhpActivity::class.java)
            intent.putExtra(EditLhpActivity.EDIT_LHP, dataLhp)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        btn_edit_lidik_lhp.setOnClickListener {
            val intent = Intent(this, PickLidikLhpActivity::class.java)
            intent.putExtra(EditLhpActivity.EDIT_LHP, dataLhp)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_edit_saksi_lhp.setOnClickListener {
            val intent = Intent(this, PickEditSaksiLhpActivity::class.java)
            intent.putExtra(EditLhpActivity.EDIT_LHP, dataLhp)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        btn_edit_ket_terlapor_lhp.setOnClickListener {
            val intent = Intent(this, PickTerlaporLhpActivity::class.java)
            intent.putExtra(EditLhpActivity.EDIT_LHP, dataLhp)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        btn_edit_ref_lp_lhp.setOnClickListener {
            val intent = Intent(this, ListDetailRefPenyelidikanActivity::class.java)
            intent.putExtra(DETAIL_REF, dataLhp)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_generate_lhp.attachTextChangeAnimator()
        bindProgressButton(btn_generate_lhp)
        btn_generate_lhp.setOnClickListener {
            btn_generate_lhp.showProgress {
                progressColor = Color.WHITE
            }
            apiGenerateDoc(dataLhp)
        }

    }

    private fun apiGenerateDoc(dataLhp: LhpMinResp?) {
        NetworkConfig().getServLhp()
            .generateDokLhp("Bearer ${sessionManager1.fetchAuthToken()}", dataLhp?.id).enqueue(
                object : Callback<Base1Resp<AddLhpResp>> {
                    override fun onResponse(
                        call: Call<Base1Resp<AddLhpResp>>,
                        response: Response<Base1Resp<AddLhpResp>>
                    ) {
                        if (response.body()?.message == "Document lhp generated successfully") {
                            saveDocLhp(response.body()?.data?.lhp)
                        } else {
                            toast("${response.body()?.message}")
                            btn_generate_lhp.hideProgress(R.string.failed_generate_doc)
                        }
                    }

                    override fun onFailure(call: Call<Base1Resp<AddLhpResp>>, t: Throwable) {
                        btn_generate_lhp.hideProgress(R.string.failed_generate_doc)
                        Toast.makeText(this@DetailLhpActivity, "$t", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    private fun saveDocLhp(dataDoc: LhpResp?) {
        btn_generate_lhp.hideProgress(R.string.success_generate_doc)
        alert("Lihat Dokumen") {
            positiveButton(R.string.iya) {
                downloadLhp(dataDoc)
//                    viewDocLhp(dataDoc)
            }
            negativeButton(R.string.tidak) {
                btn_generate_lhp.hideProgress(R.string.generate_dokumen)
            }
        }.show()
    }

    private fun downloadLhp(lhp: LhpResp?) {
        val url = lhp?.dokumen?.url
        val filename: String = "${lhp?.no_lhp}.${lhp?.dokumen?.jenis}"

        val request: DownloadManager.Request = DownloadManager.Request(Uri.parse(url))
            .setTitle(filename)
            .setDescription("Downloading")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename)

        val manager: DownloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadID = manager.enqueue(request)
    }

    private fun viewDocLhp(dataDoc: LhpResp?) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(dataDoc?.dokumen?.url)))
    }

    private val onDownloadComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val completedId = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (completedId == downloadID) {
                btn_generate_lhp.hideProgress(R.string.generate_dokumen)
                btn_generate_lhp.showSnackbar(R.string.success_download_doc) {
                    action(R.string.action_ok) {
                        btn_generate_lhp.hideProgress(R.string.generate_dokumen)
                    }
                }
            }
        }
    }

    private fun apiDetailLhp(dataLhp: LhpMinResp?) {
        dataLhp?.id?.let {
            NetworkConfig().getServLhp().getLhpById(
                "Bearer ${sessionManager1.fetchAuthToken()}", it
            ).enqueue(object : Callback<LhpResp> {
                override fun onResponse(call: Call<LhpResp>, response: Response<LhpResp>) {
                    if (response.isSuccessful) {
                        getDetailLHP(response.body())
                    } else {
                        Toast.makeText(this@DetailLhpActivity, R.string.error, Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<LhpResp>, t: Throwable) {
                    Toast.makeText(this@DetailLhpActivity, "$t", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getDetailLHP(dataLhp: LhpResp?) {
        /* *//*personel terlapor*//*
        val terlapor = dataLhp?.referensi_penyelidikan
        for(i in 0 until terlapor?.size!!){

        }
        txt_nama_terlapor_lhp_detail.text = "Nama: ${dataLhp.referensi_penyelidikan?.}"
        txt_pangkat_nrp_terlapor_lhp_detail.text = "Nama: ${dataLhp}"
        txt_jabatan_terlapor_lhp_detail.text = "Nama: ${dataLhp}"
        txt_kesatuan_terlapor_lhp_detail.text = "Nama: ${dataLhp}"*/

        txt_no_lhp_detail.text = dataLhp?.no_lhp
        txt_kesimpulan_detail.text = dataLhp?.kesimpulan
        txt_rekomendasi_detail.text = dataLhp?.rekomendasi
//        txt_pelaksanaan_detail.text = dataLhp?.pelaksanan
//        txt_rencana_penyelidikan_detail.text = dataLhp?.rencana_pelaksanaan_penyelidikan
        txt_pengaduan_detail.text = dataLhp?.tentang
        txt_no_sp_detail.text = dataLhp?.no_surat_perintah_penyelidikan
        txt_waktu_penugasan_detail.text = formatterTanggal(dataLhp?.tanggal_mulai_penyelidikan)
        txt_tempat_penyelidikan_detail.text = dataLhp?.wilayah_hukum_penyelidikan
        txt_tugas_pokok_detail.text = dataLhp?.tugas_pokok
        txt_pokok_permasalahan_detail.text = dataLhp?.pokok_permasalahan
        txt_ket_ahli_detail.text = dataLhp?.keterangan_ahli
        val lidik = dataLhp?.personel_penyelidik?.find { it.is_ketua == 1 }
        txt_ketua_tim_detail.text =
            "Nama : ${lidik?.personel?.nama}\nPangkat : ${
                lidik?.personel?.pangkat.toString().toUpperCase()
            }\nNRP : ${lidik?.personel?.nrp}\nKesatuan : ${
                lidik?.personel?.satuan_kerja?.kesatuan.toString().toUpperCase()
            }"
        txt_surat_detail_lhp.text = dataLhp?.surat
        txt_petunjuk_detail_lhp.text = dataLhp?.petunjuk
        txt_analisa_detail_lhp.text = dataLhp?.analisa
        txt_barbukti_detail_lhp.text = dataLhp?.barang_bukti
        txt_kota_buat_lhp.text = "Kota : ${dataLhp?.kota_buat_laporan}"
        txt_tanggal_buat_lhp.text = "Tanggal : ${formatterTanggal(dataLhp?.tanggal_buat_laporan)}"
        listOfRefLP(dataLhp?.referensi_penyelidikan)
        listOfLidik(dataLhp?.personel_penyelidik)
//        listOfSaksi(dataLhp?.saksi)
//        listOfKetTerlapor(dataLhp?.keterangan_terlapor)

        if (dataLhp?.is_ada_dokumen == 1) {
            btn_lihat_doc_lhp.visible()
            btn_lihat_doc_lhp.setOnClickListener {
                viewDocLhp(dataLhp)
            }
        } else {
            btn_lihat_doc_lhp.gone()
        }

    }

    private fun listOfSaksi(saksi: ArrayList<SaksiLhpResp>?) {
        callbackSaksi = object : AdapterCallback<SaksiLhpResp> {
            override fun initComponent(itemView: View, data: SaksiLhpResp, itemIndex: Int) {
                itemView.txt_detail_1.textSize = 14F
                itemView.txt_detail_2.textSize = 12F
                if (data.personel == null) {
                    itemView.txt_detail_1.text = data.nama
                } else {
                    itemView.txt_detail_1.text = data.personel?.nama
                }
                if (data.is_korban == 1) {
                    itemView.txt_detail_2.text = "Sipil"
                } else {
                    itemView.txt_detail_2.text = "Polisi"
                }
            }

            override fun onItemClicked(itemView: View, data: SaksiLhpResp, itemIndex: Int) {
            }
        }
        saksi?.let {
            adapterSaksi.adapterCallback(callbackSaksi)
                .isVerticalView()
                .addData(it)
                .setLayout(R.layout.item_2_text)
                .build(rv_saksi_detail)
        }
    }

    private fun listOfLidik(listLidik: ArrayList<PersonelPenyelidikResp>?) {
        callbackLidk = object : AdapterCallback<PersonelPenyelidikResp> {
            override fun initComponent(
                itemView: View,
                data: PersonelPenyelidikResp,
                itemIndex: Int
            ) {
                itemView.txt_detail_1.textSize = 14F
                itemView.txt_detail_2.textSize = 12F
                itemView.txt_detail_1.text = data.personel?.nama
                if (data.is_ketua == 1) {
                    itemView.txt_detail_2.text = "Ketua Tim"
                } else {
                    itemView.txt_detail_2.text = "Anggota"
                }
            }

            override fun onItemClicked(
                itemView: View,
                data: PersonelPenyelidikResp,
                itemIndex: Int
            ) {
            }
        }
        listLidik?.let {
            adapterLidik.adapterCallback(callbackLidk)
                .isVerticalView()
                .addData(it)
                .setLayout(R.layout.item_2_text)
                .build(rv_detail_lidik)
        }
    }

    private fun listOfRefLP(referensiPenyelidikan: ArrayList<RefPenyelidikanResp>?) {
        callbackRefLP = object : AdapterCallback<RefPenyelidikanResp> {
            @SuppressLint("SetTextI18n")
            override fun initComponent(itemView: View, data: RefPenyelidikanResp, itemIndex: Int) {
                itemView.txt_detail_1.text = data.lp?.no_lp
                itemView.txt_detail_2.text = "Terlapor\n" +
                        "Nama: ${data.lp?.personel_terlapor?.nama}, " +
                        "Pangkat: ${
                            data?.lp?.personel_terlapor?.pangkat.toString().toUpperCase()
                        }/" +
                        "NRP: ${data.lp?.personel_terlapor?.nrp}\n" +
                        "Kesatuan: ${data.lp?.personel_terlapor?.satuan_kerja?.kesatuan}"
            }

            override fun onItemClicked(itemView: View, data: RefPenyelidikanResp, itemIndex: Int) {
            }
        }
        referensiPenyelidikan?.let {
            adapterRefLP.adapterCallback(callbackRefLP)
                .isVerticalView()
                .addData(it)
                .setLayout(R.layout.item_2_text)
                .build(rv_detail_ref)
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
        val dataLhp = intent.extras?.getParcelable<LhpMinResp>(DETAIL_LHP)
        this.alert("Hapus Data", "Yakin Hapus?") {
            positiveButton("Iya") {
                ApiDelete(dataLhp)
                finish()
            }
            negativeButton("Tidak") {
            }
        }.show()
    }

    private fun ApiDelete(dataLhp: LhpMinResp?) {
        dataLhp?.id?.let {
            NetworkConfig().getServLhp().delLhp(
                "Bearer ${sessionManager1.fetchAuthToken()}", it
            ).enqueue(object : Callback<BaseResp> {
                override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                    if (response.body()?.message == "Data lhp removed succesfully") {
                        Toast.makeText(
                            this@DetailLhpActivity,
                            R.string.data_deleted,
                            Toast.LENGTH_SHORT
                        ).show()
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        }, 1000)
                    }else{
                        toast("${response.body()?.message}")
                    }
                }

                override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                    Toast.makeText(this@DetailLhpActivity, "$t", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    companion object {
        const val DETAIL_LHP = "DETAIL_LHP"
        const val DETAIL_REF = "DETAIL_REF"
    }

    override fun onResume() {
        super.onResume()
        val dataLhp = intent.extras?.getParcelable<LhpMinResp>(DETAIL_LHP)
        apiDetailLhp(dataLhp)
    }
}