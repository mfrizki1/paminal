package id.calocallo.sicape.ui.manajemen.sipil

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.network.response.UserResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_sipil_operator.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailSipilOperatorActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_sipil_operator)
        setupActionBarWithBackButton(toolbar)
        sessionManager1 = SessionManager1(this)
        supportActionBar?.title = "Detail Data Operator Sipil"
        val dataSipil = intent.extras?.getParcelable<UserResp>("acc")
        getDetailSipil(dataSipil)
    }

    private fun getDetailSipil(dataSipil: UserResp?) {
        NetworkConfig().getServUser().getDetailSipilOperator(
               "Bearer ${sessionManager1.fetchAuthToken()}",
               dataSipil?.id.toString()
           ).enqueue(object : Callback<UserResp> {
               override fun onFailure(call: Call<UserResp>, t: Throwable) {
                   Toast.makeText(
                       this@DetailSipilOperatorActivity,
                       "Error Koneksi",
                       Toast.LENGTH_SHORT
                   ).show()
               }

               override fun onResponse(call: Call<UserResp>, response: Response<UserResp>) {
                   if (response.isSuccessful) {
                       viewDetailSipil(response.body())
                   } else {
                       Toast.makeText(
                           this@DetailSipilOperatorActivity,
                           "Error Koneksi",
                           Toast.LENGTH_SHORT
                       ).show()
                   }
               }
           })
    }

    private fun viewDetailSipil(data: UserResp?) {
        txt_nama_sipil_operator_detail.text = data?.nama
//        txt_alamat_sipil_operator_detail.text = data??.alamat
        txt_username_sipil_operator_detail.text = data?.username
        btn_copy_username_detail.setOnClickListener {
            copyTextToClipboard(txt_username_sipil_operator_detail)
        }
//        txt_bekerja_sipil_operator_detail.text = data?.satuan_kerja?.kesatuan
        if (data?.is_aktif == 1) {
            txt_status_aktif_operator_detail.text = "Aktif"
        } else {
            txt_status_aktif_operator_detail.text = "Tidak Aktif"
        }
//        txt_status_aktif_operator_detail.text = data?.operator_sipil?.nama
        btn_edit_personel_operator_sipil.setOnClickListener {
            val intent = Intent(this, EditSipilOperatorActivity::class.java)
            intent.putExtra("acc", data)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun copyTextToClipboard(tvText: TextView) {
        val textToCopy = tvText.text
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", textToCopy)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(this, "Text copied to clipboard", Toast.LENGTH_LONG).show()
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
                finish()
            }
            negativeButton("Tidak") {
            }
        }.show()
    }

    private fun ApiDelete() {
        val sipilOper = intent.extras?.getParcelable<UserResp>("acc")

        NetworkConfig().getServUser()
            .delSipilOperator(
                "Bearer ${sessionManager1.fetchAuthToken()}",
                sipilOper?.id.toString()
            )
            .enqueue(object : Callback<BaseResp> {
                override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                    Toast.makeText(
                        this@DetailSipilOperatorActivity,
                        "Error Koneksi / Lakukan Beberapa Saat",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                    if (response.body()?.message == "Data sipil operator removed succesfully") {
                        Toast.makeText(
                            this@DetailSipilOperatorActivity,
                            R.string.data_deleted,
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@DetailSipilOperatorActivity,
                            "Error Koneksi / Lakukan Beberapa Saat",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
    }

    override fun onResume() {
        super.onResume()
        val dataSipil = intent.extras?.getParcelable<UserResp>("acc")
        getDetailSipil(dataSipil)
    }
}