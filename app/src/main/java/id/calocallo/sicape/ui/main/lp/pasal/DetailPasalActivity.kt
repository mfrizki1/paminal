package id.calocallo.sicape.ui.main.lp.pasal

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.network.response.PasalResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_pasal.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPasalActivity : BaseActivity() {
    companion object {
        const val DETAIL_PASAL = "DETAIL_PASAL"
    }

    private lateinit var sessionManager1: SessionManager1
    private var pasal: PasalResp? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_pasal)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Data Pasal"
        sessionManager1 = SessionManager1(this)

        pasal = intent.getParcelableExtra<PasalResp>(DETAIL_PASAL)
        apiDetailPasal(pasal)
//        viewDetailPasal(pasal)
        btn_edit_pasal_detail.setOnClickListener {
            val intent = Intent(this, EditPasalActivity::class.java).apply {
                this.putExtra(DETAIL_PASAL, pasal)
            }
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun apiDetailPasal(pasal: PasalResp?) {
        pasal?.id?.let {
            NetworkConfig().getServLp().getPasalById(
                "Bearer ${sessionManager1.fetchAuthToken()}", it
            ).enqueue(object :
                Callback<PasalResp> {
                override fun onFailure(call: Call<PasalResp>, t: Throwable) {
                    Toast.makeText(
                        this@DetailPasalActivity,
                        R.string.error_conn,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResponse(call: Call<PasalResp>, response: Response<PasalResp>) {
                    viewDetailPasal(response.body())
                }
            })
        }
    }

    private fun viewDetailPasal(pasal: PasalResp?) {
        txt_nama_pasal_detail.text = pasal?.nama_pasal
        txt_tentang_pasal_detail.text = pasal?.tentang_pasal
        txt_isi_pasal_detail.text = pasal?.isi_pasal
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
        pasal?.id?.let {
            NetworkConfig().getServLp()
                .delPasal("Bearer ${sessionManager1.fetchAuthToken()}", it)
                .enqueue(object : Callback<BaseResp> {
                    override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                        Toast.makeText(this@DetailPasalActivity, "Gagal Koneksi", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                        if (response.isSuccessful) {
                            if (response.body()?.message == "Data pasal removed succesfully") {
                                finish()
                            } else {
                                Toast.makeText(
                                    this@DetailPasalActivity,
                                    "Error Hapus",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        } else {
                            Toast.makeText(this@DetailPasalActivity, "Error", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                })
        }
    }

    override fun onResume() {
        super.onResume()
        val pasal = intent.getParcelableExtra<PasalResp>(DETAIL_PASAL)
        apiDetailPasal(pasal)
    }


}