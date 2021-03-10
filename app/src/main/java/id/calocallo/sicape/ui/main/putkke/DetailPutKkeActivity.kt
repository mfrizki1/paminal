package id.calocallo.sicape.ui.main.putkke

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.network.response.PutKkeMinResp
import id.calocallo.sicape.network.response.PutKkeResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.formatterTanggal
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_put_kke.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPutKkeActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var detailPutKke: PutKkeMinResp? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_put_kke)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Data Putusan Kode Etik"
        sessionManager1 = SessionManager1(this)

        detailPutKke =
            intent.extras?.getParcelable<PutKkeMinResp>(ListPutKkeActivity.DETAIL_PUTKKE)
        apiDetailPutKke(detailPutKke)


        btn_generate_put_kke_detail.attachTextChangeAnimator()
        bindProgressButton(btn_generate_put_kke_detail)
        btn_generate_put_kke_detail.setOnClickListener {
            btn_generate_put_kke_detail.showProgress {
                progressColor = Color.WHITE
                Handler(Looper.getMainLooper()).postDelayed({
                    btn_generate_put_kke_detail.hideProgress(R.string.success_generate_doc)
                    alert(R.string.download) {
                        positiveButton(R.string.iya) {
                            btn_generate_put_kke_detail.hideProgress(R.string.generate_dokumen)

                        }
                        negativeButton(R.string.tidak) {
                            btn_generate_put_kke_detail.hideProgress(R.string.generate_dokumen)

                        }
                    }.show()
                }, 2000)
            }
        }
    }

    private fun apiDetailPutKke(detailPutKke: PutKkeMinResp?) {
        NetworkConfig().getServSkhd()
            .detailPutKke("Bearer ${sessionManager1.fetchAuthToken()}", detailPutKke?.id).enqueue(
                object :
                    Callback<PutKkeResp> {
                    override fun onResponse(
                        call: Call<PutKkeResp>,
                        response: Response<PutKkeResp>
                    ) {
                        if (response.isSuccessful) {
                            getDetailPutKKe(response.body())
                        } else {
                            Toast.makeText(
                                this@DetailPutKkeActivity, R.string.error, Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<PutKkeResp>, t: Throwable) {
                        Toast.makeText(this@DetailPutKkeActivity, "$t", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    companion object {
        const val EDIT_PUT_KKE = "EDIT_PUT_KKE"
    }

    @SuppressLint("SetTextI18n")
    private fun getDetailPutKKe(detailPutKke: PutKkeResp?) {

        btn_edit_put_kke_detail.setOnClickListener {
            val intent = Intent(this, EditPutKkeActivity::class.java)
            intent.putExtra(EDIT_PUT_KKE, detailPutKke)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        txt_no_put_kke_detail.text = detailPutKke?.no_putkke
        txt_no_lp_put_kke_detail.text = detailPutKke?.lp?.no_lp

        txt_no_berkas_pemeriksaan_detail.text =
            "No : ${detailPutKke?.no_berkas_pemeriksaan_pendahuluan}, Tanggal ${
                formatterTanggal(detailPutKke?.tanggal_berkas_pemeriksaan_pendahuluan)
            }"

        txt_keputusan_kapolda_put_kke_detail.text =
            "No : ${detailPutKke?.no_keputusan_kapolda_kalsel}, Tanggal ${
                formatterTanggal(
                    detailPutKke?.tanggal_keputusan_kapolda_kalsel
                )
            }"

        txt_surat_persangkaan_put_kke_detail.text =
            "No : ${detailPutKke?.no_surat_persangkaan_pelanggaran_kode_etik}, Tanggal ${
                formatterTanggal(detailPutKke?.tanggal_surat_persangkaan_pelanggaran_kode_etik)
            }"


        txt_tuntutan_pelanggaran_put_kke_detail.text =
            "No : ${detailPutKke?.no_tuntutan_pelanggaran_kode_etik}, Tanggal ${
                formatterTanggal(detailPutKke?.tanggal_tuntutan_pelanggaran_kode_etik)
            }"

        txt_sanksi_hasil_putusan_put_kke_detail.text = detailPutKke?.sanksi_hasil_keputusan
        txt_lokasi_sidang_put_kke_detail.text ="Lokasi : ${detailPutKke?.lokasi_sidang}"

        txt_tanggal_putusan_put_kke_detail.text =
            "Tanggal : ${formatterTanggal(detailPutKke?.tanggal_putusan)}"

        txt_nama_ketua_komisi_put_kke_detail.text = "Nama : ${detailPutKke?.nama_ketua_komisi}"
        txt_pangkat_nrp_ketua_komisi_put_kke_detail.text =
            "Pangkat : ${
                detailPutKke?.pangkat_ketua_komisi.toString()
                    .toUpperCase()
            }, NRP : ${detailPutKke?.nrp_ketua_komisi}"

        txt_nama_wakil_ketua_komisi_put_kke_detail.text =
            "Nama : ${detailPutKke?.nama_wakil_ketua_komisi}"
        txt_pangkat_nrp_wakil_ketua_komisi_put_kke_detail.text =
            "Pangkat : ${
                detailPutKke?.pangkat_wakil_ketua_komisi.toString()
                    .toUpperCase()
            }, NRP : ${detailPutKke?.nrp_wakil_ketua_komisi}"


        txt_nama_anggota_komisi_put_kke_detail.text = "Nama : ${detailPutKke?.nama_anggota_komisi}"
        txt_pangkat_nrp_anggota_komisi_put_kke_detail.text =
            "Pangkat : ${
                detailPutKke?.pangkat_anggota_komisi.toString()
                    .toUpperCase()
            }, NRP : ${detailPutKke?.nrp_anggota_komisi}"

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
                ApiDelete(detailPutKke)
                finish()
            }
            negativeButton("Tidak") {
            }
        }.show()
    }

    private fun ApiDelete(detailPutKke: PutKkeMinResp?) {
        NetworkConfig().getServSkhd()
            .delPutKke("Bearer ${sessionManager1.fetchAuthToken()}", detailPutKke?.id).enqueue(
                object : Callback<BaseResp> {
                    override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                        if (response.body()?.message == "Data putkke removed succesfully") {
                            Toast.makeText(
                                this@DetailPutKkeActivity, R.string.data_deleted, Toast.LENGTH_SHORT
                            ).show()
                            Handler(Looper.getMainLooper()).postDelayed({
                                finish()
                            }, 750)
                        } else {
                            Toast.makeText(
                                this@DetailPutKkeActivity, R.string.error, Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                        Toast.makeText(this@DetailPutKkeActivity, "$t", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    override fun onResume() {
        super.onResume()
        apiDetailPutKke(detailPutKke)
    }
}