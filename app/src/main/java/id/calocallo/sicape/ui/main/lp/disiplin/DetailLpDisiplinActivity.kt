package id.calocallo.sicape.ui.main.lp.disiplin

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
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
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.ui.main.lp.pasal.ListPasalDilanggarActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.formatterTanggal
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_lp_disiplin.*
import kotlinx.android.synthetic.main.item_pasal_lp.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailLpDisiplinActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var adapterDetailPasalDisiplin = ReusableAdapter<PasalDilanggarResp>(this)
    private lateinit var callbackDetailPasalDilanggarDisiplin: AdapterCallback<PasalDilanggarResp>
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
                        btn_generate_disiplin.hideProgress(R.string.failed_generate_doc)
                    }
                }
            })
    }

    private fun saveDocLpDisiplin(lp: LpPidanaResp?) {
        Handler(Looper.getMainLooper()).postDelayed({
            btn_generate_disiplin.hideProgress(R.string.success_generate_doc)
            alert("Lihat Dokumen") {
                positiveButton(R.string.iya) {
                    viewDocDispl(lp)
                    btn_generate_disiplin.hideProgress(R.string.generate_dokumen)

                }
                negativeButton(R.string.tidak) {
                    btn_generate_disiplin.hideProgress(R.string.generate_dokumen)
                }
            }.show()
        }, 2000)
    }

    private fun viewDocDispl(lp: LpPidanaResp?) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(lp?.dokumen?.url)))
//        finish()
    }

    private fun apiDetailDisiplin(disiplin: LpMinResp?) {
        NetworkConfig().getServLp()
            .getLpById("Bearer ${sessionManager1.fetchAuthToken()}", disiplin?.id).enqueue(object :
                Callback<LpPidanaResp> {
                override fun onFailure(call: Call<LpPidanaResp>, t: Throwable) {
                    Toast.makeText(
                        this@DetailLpDisiplinActivity,
                        "Error Koneksi",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onResponse(
                    call: Call<LpPidanaResp>,
                    response: Response<LpPidanaResp>
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
    private fun getViewDisiplin(disiplin: LpPidanaResp?) {
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
            "Nama : ${disiplin?.detail_laporan?.nama_yang_mengetahui}"
        txt_detail_pangkat_nrp_pimpinan_disiplin.text =
            "Pangkat : ${disiplin?.detail_laporan?.pangkat_yang_mengetahui.toString()
                .toUpperCase()}, NRP : ${disiplin?.detail_laporan?.nrp_yang_mengetahui}"
        txt_detail_jabatan_pimpinan_disiplin.text =
            "Jabatan : ${disiplin?.detail_laporan?.jabatan_yang_mengetahui}"
//         txt_detail_kesatuan_pimpinan_disiplin.text =
//             "Kesatuan : ${disiplin?.kesatuan_yang_mengetahui.toString().toUpperCase()}"

        //terlapor
        txt_detail_nama_terlapor_disiplin.text = "Nama : ${disiplin?.personel_terlapor?.nama}"
        txt_detail_pangkat_nrp_terlapor_disiplin.text =
            "Pangkat : ${disiplin?.personel_terlapor?.pangkat.toString()
                .toUpperCase()}, NRP : ${disiplin?.personel_terlapor?.nrp}"
        txt_detail_jabatan_terlapor_disiplin.text =
            "Jabatan : ${disiplin?.personel_terlapor?.jabatan}"
        txt_detail_kesatuan_terlapor_disiplin.text =
            "Kesatuan : ${disiplin?.personel_terlapor?.satuan_kerja?.kesatuan.toString()
                .toUpperCase()}"

        //pelapor
        txt_detail_nama_pelapor_disiplin.text =
            "Nama : ${disiplin?.detail_laporan?.personel_pelapor?.nama}"
        txt_detail_pangkat_nrp_pelapor_disiplin.text =
            "Pangkat : ${disiplin?.detail_laporan?.personel_pelapor?.pangkat.toString()
                .toUpperCase()}, NRP : ${disiplin?.detail_laporan?.personel_pelapor?.nrp}"

        txt_detail_jabatan_pelapor_disiplin.text =
            "Jabatan : ${disiplin?.detail_laporan?.personel_pelapor?.jabatan}"
        txt_detail_kesatuan_pelapor_disiplin.text =
            "Kesatuan : ${disiplin?.detail_laporan?.personel_pelapor?.satuan_kerja?.kesatuan.toString()
                .toUpperCase()}"
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
                alertDialogDelete()
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
                        if(response.body()?.message == "Data lp removed succesfully"){
                            Toast.makeText(this@DetailLpDisiplinActivity, R.string.data_deleted, Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }else{
                        Toast.makeText(this@DetailLpDisiplinActivity, R.string.error_conn, Toast.LENGTH_SHORT).show()
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