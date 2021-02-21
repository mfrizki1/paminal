package id.calocallo.sicape.ui.main.lp.kke

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.ui.main.lp.kke.EditLpKkeActivity.Companion.EDIT_KKE
import id.calocallo.sicape.ui.main.lp.pasal.ListPasalDilanggarActivity
import id.calocallo.sicape.ui.main.lp.saksi.PickSaksiLpEditActivity
import id.calocallo.sicape.ui.main.lp.saksi.PickSaksiLpEditActivity.Companion.EDIT_SAKSI_KKE
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.formatterTanggal
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_lp_kke.*
import kotlinx.android.synthetic.main.item_2_text.view.*
import kotlinx.android.synthetic.main.item_pasal_lp.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailLpKkeActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var adapterDetailPasalKke = ReusableAdapter<PasalDilanggarResp>(this)
    private lateinit var callbackDetailPasalDilanggarKke: AdapterCallback<PasalDilanggarResp>

    private var adapterDetailSaksiKke = ReusableAdapter<LpSaksiResp>(this)
    private lateinit var callbackDetailSaksiKke: AdapterCallback<LpSaksiResp>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lp_kke)
        sessionManager1 = SessionManager1(this)

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Data Laporan Polisi Kode Etik"
        val detailKKe = intent.extras?.getParcelable<LpMinResp>(DETAIL_KKE)
        apiDetailKKe(detailKKe)
        adapterDetailPasalKke = ReusableAdapter(this)
        adapterDetailSaksiKke = ReusableAdapter(this)
        Logger.addLogAdapter(AndroidLogAdapter())

        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_edit_pasal_kke.gone()
            btn_edit_saksi_kke.gone()
            btn_edit_kke.gone()
        }


        //EDIT LP KKE (SAKSI)
        btn_edit_saksi_kke.setOnClickListener {
            val intent = Intent(this, PickSaksiLpEditActivity::class.java)
            intent.putExtra(EDIT_SAKSI_KKE, detailKKe)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent)
        }

        //EDIT LP KKE (PASAL)
        btn_edit_pasal_kke.setOnClickListener {
            val intent = Intent(this, ListPasalDilanggarActivity::class.java)
            intent.putExtra(ListPasalDilanggarActivity.EDIT_PASAL_DILANGGAR, detailKKe)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent)
        }

        btn_generate_kke.attachTextChangeAnimator()
        bindProgressButton(btn_generate_kke)
        btn_generate_kke.setOnClickListener {
            btn_generate_kke.showProgress {
                progressColor = Color.WHITE
            }
            apiGenerateKke(detailKKe)

        }

    }

    private fun apiGenerateKke(kKe: LpMinResp?) {
        NetworkConfig().getServLp()
            .generateLp("Bearer ${sessionManager1.fetchAuthToken()}", kKe?.id)
            .enqueue(object : Callback<Base1Resp<DokLpResp>> {
                override fun onFailure(call: Call<Base1Resp<DokLpResp>>, t: Throwable) {
                    Toast.makeText(
                        this@DetailLpKkeActivity,
                        R.string.error_conn,
                        Toast.LENGTH_SHORT
                    ).show()
                    btn_generate_kke.hideProgress(R.string.failed_generate_doc)
                }

                override fun onResponse(
                    call: Call<Base1Resp<DokLpResp>>,
                    response: Response<Base1Resp<DokLpResp>>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.message == "Document lp generated successfully") {
                            saveDocLpKke(response.body()?.data?.lp)
                        }
                    } else {
                        Toast.makeText(
                            this@DetailLpKkeActivity,
                            R.string.error_conn,
                            Toast.LENGTH_SHORT
                        ).show()
                        btn_generate_kke.hideProgress(R.string.failed_generate_doc)

                    }
                }
            })
    }

    private fun saveDocLpKke(lp: LpPidanaResp?) {
        Handler(Looper.getMainLooper()).postDelayed({
            btn_generate_kke.hideProgress(R.string.success_generate_doc)
            alert("Lihat Dokumen") {
                positiveButton(R.string.iya) {
                    btn_generate_kke.hideProgress(R.string.generate_dokumen)
                    viewDokKke(lp)
                }
                negativeButton(R.string.tidak) {
                    btn_generate_kke.hideProgress(R.string.generate_dokumen)
                }
            }.show()
        }, 2000)
    }

    private fun viewDokKke(lp: LpPidanaResp?) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(lp?.dokumen?.url)))
//        finish()
    }

    private fun apiDetailKKe(kke: LpMinResp?) {
        NetworkConfig().getServLp().getLpById("Bearer ${sessionManager1.fetchAuthToken()}", kke?.id)
            .enqueue(object :
                Callback<LpPidanaResp> {
                override fun onFailure(call: Call<LpPidanaResp>, t: Throwable) {
                    Toast.makeText(this@DetailLpKkeActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(
                    call: Call<LpPidanaResp>,
                    response: Response<LpPidanaResp>
                ) {
                    if (response.isSuccessful) {
                        getViewKke(response.body())
                        Logger.e("${response.body()}")
                    } else {
                        Toast.makeText(
                            this@DetailLpKkeActivity,
                            "Error Koneksi",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
    }

    @SuppressLint("SetTextI18n")
    private fun getViewKke(detailKKe: LpPidanaResp?) {
        //EDIT LP KKE
        btn_edit_kke.setOnClickListener {
            val intent = Intent(this, EditLpKkeActivity::class.java)
            intent.putExtra(EDIT_KKE, detailKKe)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent)
        }

        //general
        txt_detail_no_lp_kke.text = "No LP : ${detailKKe?.no_lp}"
        txt_detail_isi_laporan_kke.text = detailKKe?.detail_laporan?.isi_laporan

        //terlapor
        txt_detail_nama_terlapor_kke.text = "Nama : ${detailKKe?.personel_terlapor?.nama}"
        txt_detail_pangkat_nrp_terlapor_kke.text =
            "Pangkat : ${detailKKe?.personel_terlapor?.pangkat.toString()
                .toUpperCase()}, NRP : ${detailKKe?.personel_terlapor?.nrp}"
        txt_detail_jabatan_terlapor_kke.text = "Jabatan : ${detailKKe?.personel_terlapor?.jabatan}"
        txt_detail_kesatuan_terlapor_kke.text =
            "Kesatuan : ${detailKKe?.personel_terlapor?.satuan_kerja?.kesatuan.toString()
                .toUpperCase()}"

        //pelapor
        txt_detail_nama_pelapor_kke.text =
            "Nama : ${detailKKe?.detail_laporan?.personel_pelapor?.nama}"
        txt_detail_pangkat_nrp_pelapor_kke.text =
            "Pangkat : ${detailKKe?.detail_laporan?.personel_pelapor?.pangkat.toString()
                .toUpperCase()}, NRP : ${detailKKe?.detail_laporan?.personel_pelapor?.nrp}"
        txt_detail_jabatan_pelapor_kke.text =
            "Jabatan : ${detailKKe?.detail_laporan?.personel_pelapor?.jabatan}"
        txt_detail_kesatuan_pelapor_kke.text =
            "Kesatuan : ${detailKKe?.detail_laporan?.personel_pelapor?.satuan_kerja?.kesatuan.toString()
                .toUpperCase()}"

        txt_detail_alat_bukti_kke.text = detailKKe?.detail_laporan?.alat_bukti
        txt_detail_kota_buat_kke.text = "Kota : ${detailKKe?.kota_buat_laporan}"
        txt_detail_tgl_buat_kke.text =
            "Tanggal : ${formatterTanggal(detailKKe?.tanggal_buat_laporan)}"
        txt_detail_nama_pimpinan_kke.text =
            "Nama : ${detailKKe?.detail_laporan?.nama_yang_mengetahui}"
        txt_detail_pangkat_nrp_pimpinan_kke.text =
            "Pangkat : ${detailKKe?.detail_laporan?.pangkat_yang_mengetahui.toString()
                .toUpperCase()}, NRP : ${detailKKe?.detail_laporan?.nrp_yang_mengetahui}"
//        txt_detail_kesatuan_pimpinan_kke.text =
//            "Kesatuan : ${detailKKe?.kesatuan_yang_mengetahui.toString().toUpperCase()}"
        txt_detail_jabatan_pimpinan_kke.text =
            "Jabatan : ${detailKKe?.detail_laporan?.jabatan_yang_mengetahui.toString()
                .toUpperCase()}"

        //pasal
        callbackDetailPasalDilanggarKke = object : AdapterCallback<PasalDilanggarResp> {
            override fun initComponent(itemView: View, data: PasalDilanggarResp, itemIndex: Int) {
                itemView.txt_item_1.text = data.pasal?.nama_pasal
            }

            override fun onItemClicked(itemView: View, data: PasalDilanggarResp, itemIndex: Int) {
            }
        }
        detailKKe?.pasal_dilanggar?.let {
            adapterDetailPasalKke.adapterCallback(callbackDetailPasalDilanggarKke)
                .isVerticalView().addData(it).setLayout(R.layout.item_pasal_lp)
                .build(rv_detail_pasal_kke)
        }

        //saksi
        callbackDetailSaksiKke = object : AdapterCallback<LpSaksiResp> {
            override fun initComponent(itemView: View, data: LpSaksiResp, itemIndex: Int) {
                itemView.txt_detail_1.text = data.nama
                when (data.is_korban) {
                    1 -> itemView.txt_detail_2.text = "Korban"
                    0 -> itemView.txt_detail_2.text = "Saksi"
                }

            }

            override fun onItemClicked(itemView: View, data: LpSaksiResp, itemIndex: Int) {
            }
        }
        detailKKe?.saksi_kode_etik?.let {
            adapterDetailSaksiKke.adapterCallback(callbackDetailSaksiKke)
                .isVerticalView().addData(it).setLayout(R.layout.item_2_text)
                .build(rv_detail_saksi_kke)
        }

        /*button liat dok*/
        if (detailKKe?.is_ada_dokumen == 0) {
            btn_lihat_dok_kke.gone()
        } else {
            btn_lihat_dok_kke.visible()
            btn_lihat_dok_kke.setOnClickListener {
                viewDokKke(detailKKe)
            }
        }
    }

    companion object {
        const val DETAIL_KKE = "DETAIL_KKE"
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
        val detailKKe = intent.extras?.getParcelable<LpMinResp>(DETAIL_KKE)
        NetworkConfig().getServLp()
            .delLp("Bearer ${sessionManager1.fetchAuthToken()}", detailKKe?.id)
            .enqueue(object : Callback<BaseResp> {
                override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                    Toast.makeText(
                        this@DetailLpKkeActivity,
                        R.string.error_conn,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                    if (response.isSuccessful) {
                        if (response.body()?.message == "Data lp removed succesfully") {
                            Toast.makeText(
                                this@DetailLpKkeActivity,
                                R.string.data_deleted,
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        }
                    } else {
                        Toast.makeText(
                            this@DetailLpKkeActivity,
                            R.string.error_conn,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
    }

    override fun onResume() {
        super.onResume()
        val detailKKe = intent.extras?.getParcelable<LpMinResp>(DETAIL_KKE)
        apiDetailKKe(detailKKe)
        Log.e("onResume", "onResume")
    }


}