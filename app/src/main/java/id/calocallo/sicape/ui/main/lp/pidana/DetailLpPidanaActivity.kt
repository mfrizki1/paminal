package id.calocallo.sicape.ui.main.lp.pidana

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
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
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.ui.main.lp.pasal.ListPasalDilanggarActivity
import id.calocallo.sicape.ui.main.lp.pasal.ListPasalDilanggarActivity.Companion.EDIT_PASAL_DILANGGAR
import id.calocallo.sicape.ui.main.lp.pasal.ListPasalDilanggarActivity.Companion.EDIT_PASAL_PIDANA
import id.calocallo.sicape.ui.main.lp.pidana.EditLpPidanaActivity.Companion.EDIT_PIDANA
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.formatterTanggal
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_lp_pidana.*
import kotlinx.android.synthetic.main.item_pasal_lp.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class DetailLpPidanaActivity : BaseActivity() {
    companion object {
        const val DETAIL_PIDANA = "DETAIL_PIDANA"
    }

    private lateinit var sessionManager1: SessionManager1
    private lateinit var adapterDetailPasalDilanggar: ReusableAdapter<PasalDilanggarResp>
    private lateinit var callbackDetailPasalDilanggar: AdapterCallback<PasalDilanggarResp>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lp_pidana)
        sessionManager1 = SessionManager1(this)
        adapterDetailPasalDilanggar = ReusableAdapter(this)

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
                        Toast.makeText(
                            this@DetailLpPidanaActivity,
                            R.string.error_conn,
                            Toast.LENGTH_SHORT
                        ).show()
                        btn_generate_pidana.hideProgress(R.string.failed_generate_doc)

                    }
                }
            })
    }

    private fun saveDocLpPidana(dok: LpPidanaResp?) {
        Log.e("urlDok", "${dok?.dokumen?.url}")
        Handler(Looper.getMainLooper()).postDelayed({
            btn_generate_pidana.hideProgress(R.string.success_generate_doc)
            alert("Lihat Dokumen") {
                positiveButton(R.string.iya) {
//                    downloadDok(dok)
                    gotoBrowser(dok)
                    btn_generate_pidana.hideProgress(R.string.generate_dokumen)

                }
                negativeButton(R.string.tidak) {
                    btn_generate_pidana.hideProgress(R.string.generate_dokumen)
                }
            }.show()
        }, 2000)
    }

    private fun gotoBrowser(dok: LpPidanaResp?) {
        val uri = Uri.parse(dok?.dokumen?.url)
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri.toString())))

    }

    private fun apiDetailPidana(pidana: LpMinResp?) {
        NetworkConfig().getServLp()
            .getLpById("Bearer ${sessionManager1.fetchAuthToken()}", pidana?.id).enqueue(object :
                Callback<LpPidanaResp> {
                override fun onFailure(call: Call<LpPidanaResp>, t: Throwable) {
                    Log.e("t", "$t")
                    Toast.makeText(this@DetailLpPidanaActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(
                    call: Call<LpPidanaResp>,
                    response: Response<LpPidanaResp>
                ) {
                    if (response.isSuccessful) {
                        getDetailPidana(response.body())
                        Log.e("response", "${response.body()}")
                    } else {
                        Toast.makeText(
                            this@DetailLpPidanaActivity,
                            "Error Koneksi",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            })
    }

    @SuppressLint("SetTextI18n")
    private fun getDetailPidana(pidana: LpPidanaResp?) {
        /*if (pidana?.status_pelapor == "polisi") {
            ll_detail_personel_pidana.visible()
            ll_detail_sipil_pidana.gone()
        } else {
            ll_detail_personel_pidana.gone()
            ll_detail_sipil_pidana.visible()
        }*/
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
              "Pangkat : ${pidana?.pangkat_yang_mengetahui.toString()
                  .toUpperCase()}, NRP : ${pidana?.nrp_yang_mengetahui}"
          txt_detail_jabatan_pimpinan_pidana.text = "Jabatan : ${pidana?.jabatan_yang_mengetahui}"
//          txt_detail_kesatuan_pimpinan_pidana.text =
//              "Kesatuan : ${pidana?.kesatuan_yang_mengetahui.toString().toUpperCase()}"

        //terlapor
        txt_detail_nama_terlapor.text = "Nama : ${pidana?.personel_terlapor?.nama}"
        txt_detail_pangkat_nrp_terlapor.text =
            "Pangkat : ${pidana?.personel_terlapor?.pangkat.toString()
                .toUpperCase()}, NRP : ${pidana?.personel_terlapor?.nrp}"
        txt_detail_jabatan_terlapor.text = "Jabatan : ${pidana?.personel_terlapor?.jabatan}"
        txt_detail_kesatuan_terlapor.text =
            "Kesatuan : ${pidana?.personel_terlapor?.satuan_kerja?.kesatuan.toString()
                .toUpperCase()}"

        /*  //pelapor
          txt_detail_nama_pelapor.text = "Nama : ${pidana?.detail_laporan?.nama_pelapor}"
          txt_detail_pangkat_nrp_pelapor.text =
              "Pangkat : ${pidana?.detail_laporan?.personel_pelapor?.pangkat.toString()
                  .toUpperCase()}, NRP : ${pidana?.detail_laporan?.personel_pelapor?.nrp}"
          txt_detail_jabatan_pelapor.text = "Jabatan : ${pidana?.detail_laporan?.personel_pelapor?.jabatan}"
          txt_detail_kesatuan_pelapor.text =
              "Kesatuan : ${pidana?.detail_laporan?.personel_pelapor?.satuan_kerja?.kesatuan.toString().toUpperCase()}"*/

        //sipil
        txt_detail_nama_sipil.text = "Nama :  ${pidana?.detail_laporan?.nama_pelapor}"
        txt_detail_agama_sipil.text = "Agama : ${pidana?.detail_laporan?.agama_pelapor}"
        txt_detail_pekerjaan_sipil.text = "Pekerjaan : ${pidana?.detail_laporan?.pekerjaan_pelapor}"
        txt_detail_kwg_sipil.text =
            "Kewarganegaraan : ${pidana?.detail_laporan?.kewarganegaraan_pelapor}"
        txt_detail_alamat_sipil.text = "Alamat : ${pidana?.detail_laporan?.alamat_pelapor}"
        txt_detail_no_telp_sipil.text = "No Telepon : ${pidana?.detail_laporan?.no_telp_pelapor}"
        txt_detail_nik_sipil.text = "NIK KTP : ${pidana?.detail_laporan?.nik_ktp_pelapor}"
        txt_detail_jk_sipil.text = "Jenis Kelamin : ${pidana?.detail_laporan?.jenis_kelamin_pelapor}"
        txt_detail_ttl_sipil.text =
            "TTL : ${pidana?.detail_laporan?.tempat_lahir_pelapor}, ${formatterTanggal(pidana?.detail_laporan?.tanggal_lahir_pelapor)}"


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
                        }
                    } else {
                        Toast.makeText(
                            this@DetailLpPidanaActivity,
                            R.string.error_conn,
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