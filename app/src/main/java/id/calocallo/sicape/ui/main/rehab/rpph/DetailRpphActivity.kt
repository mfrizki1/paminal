package id.calocallo.sicape.ui.main.rehab.rpph

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
import id.calocallo.sicape.network.response.RpphMinResp
import id.calocallo.sicape.network.response.RpphResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.formatterTanggal
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_rpph.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DetailRpphActivity : BaseActivity() {


    private lateinit var sessionManager1: SessionManager1
    private var detailRpph: RpphMinResp? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_rpph)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Data RPPH"
        sessionManager1 = SessionManager1(this)

        detailRpph = intent.extras?.getParcelable<RpphMinResp>(DETAIL_RPPH)
        apiDetailRpph(detailRpph)
//        getViewRpph(detailRpph)

        btn_generate_dok_rpph_detail.attachTextChangeAnimator()
        bindProgressButton(btn_generate_dok_rpph_detail)
        btn_generate_dok_rpph_detail.setOnClickListener {
            btn_generate_dok_rpph_detail.showProgress {
                progressColor = Color.WHITE
            }
            Handler(Looper.getMainLooper()).postDelayed({
                btn_generate_dok_rpph_detail.hideProgress("Berhasil Generate Dokumen")
                alert("Download") {
                    positiveButton("Iya") {
                        btn_generate_dok_rpph_detail.hideProgress(R.string.generate_dokumen)

                    }
                    negativeButton("TIdak") {
                        btn_generate_dok_rpph_detail.hideProgress(R.string.generate_dokumen)
                    }
                }.show()
            }, 2000)
        }
    }

    private fun apiDetailRpph(detailRpph: RpphMinResp?) {
        NetworkConfig().getServRpph()
            .detailRpph("Bearer ${sessionManager1.fetchAuthToken()}", detailRpph?.id).enqueue(
                object :
                    Callback<RpphResp> {
                    override fun onResponse(call: Call<RpphResp>, response: Response<RpphResp>) {
                        if (response.isSuccessful) {
                            getViewRpph(response.body())
                        } else {
                            Toast.makeText(
                                this@DetailRpphActivity,
                                R.string.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<RpphResp>, t: Throwable) {
                        Toast.makeText(this@DetailRpphActivity, "$t", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    @SuppressLint("SetTextI18n")
    private fun getViewRpph(detailRpph: RpphResp?) {

        btn_edit_rpph_detail.setOnClickListener {
            val intent = Intent(this, EditRpphActivity::class.java)
            intent.putExtra(EditRpphActivity.EDIT_RPPH_EDIT, detailRpph)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        txt_no_rpph_detail.text = "No: ${detailRpph?.no_rpph}"
        txt_no_lp_rpph_detail.text = "No: ${detailRpph?.lp?.no_lp}"
        txt_nama_dinas_rpph_detail.text = "Nama Dinas: ${detailRpph?.nama_dinas}"
        txt_no_nota_dinas_rpph_detail.text =
            "No Nota: ${detailRpph?.no_nota_dinas}, Tanggal: ${formatterTanggal(detailRpph?.tanggal_nota_dinas)}"
        txt_kota_penetapan_rpph_detail.text = "Kota: ${detailRpph?.kota_penetapan}"
        txt_tanggal_penetapan_rpph_detail.text =
            "Tanggal: ${formatterTanggal(detailRpph?.tanggal_penetapan)}"
        txt_penerima_salinan_keputusan_rpph_detail.text = detailRpph?.penerima_salinan_keputusan
        txt_nama_pimpinan_rpph_detail.text = "Nama : ${detailRpph?.nama_kabid_propam}"
        txt_pangkat_nrp_pimpinan_rpph_detail.text =
            "Pangkat : ${
                detailRpph?.pangkat_kabid_propam.toString().toUpperCase(Locale.ROOT)
            }, NRP : ${detailRpph?.nrp_kabid_propam}"
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
                ApiDelete(detailRpph)
                finish()
            }
            negativeButton("Tidak") {
            }
        }.show()
    }

    private fun ApiDelete(detailRpph: RpphMinResp?) {
        NetworkConfig().getServRpph()
            .delRpph("Bearer ${sessionManager1.fetchAuthToken()}", detailRpph?.id).enqueue(
                object : Callback<BaseResp> {
                    override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                        if (response.body()?.message == "Data rpph removed succesfully") {
                            Toast.makeText(
                                this@DetailRpphActivity,
                                R.string.data_deleted,
                                Toast.LENGTH_SHORT
                            ).show()
                            Handler(Looper.getMainLooper()).postDelayed({
                                finish()
                            },750)
                        } else {
                            Toast.makeText(
                                this@DetailRpphActivity,
                                R.string.failed_deleted,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                        Toast.makeText(this@DetailRpphActivity, "$t", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    companion object {
        const val DETAIL_RPPH = "DETAIL_RPPH"
    }

    override fun onResume() {
        super.onResume()
        apiDetailRpph(detailRpph)
    }
}