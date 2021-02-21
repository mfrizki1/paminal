package id.calocallo.sicape.ui.manajemen

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
import id.calocallo.sicape.network.response.HakAksesPersonel1
import id.calocallo.sicape.network.response.UserCreatorResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_operator.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailOperatorActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_operator)
        setupActionBarWithBackButton(toolbar)
        sessionManager1 = SessionManager1(this)
        supportActionBar?.title = "Detail Operator"
        val dataOper = intent.extras?.getParcelable<UserCreatorResp>("acc")
        getDetailOperator(dataOper)
    }

    private fun getDetailOperator(dataOper: UserCreatorResp?) {
        NetworkConfig().getService().getDetailPersOperator(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            dataOper?.id.toString()
        ).enqueue(object : Callback<HakAksesPersonel1> {
            override fun onFailure(call: Call<HakAksesPersonel1>, t: Throwable) {
                Toast.makeText(this@DetailOperatorActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(
                call: Call<HakAksesPersonel1>,
                response: Response<HakAksesPersonel1>
            ) {
                if (response.isSuccessful) {
                    viewDetailOperator(response.body())
                } else {
                    Toast.makeText(this@DetailOperatorActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    private fun viewDetailOperator(data: HakAksesPersonel1?) {
        txt_nama_operator_detail.text = data?.personel?.nama
        txt_pangkat_operator_detail.text = data?.personel?.pangkat.toString().toUpperCase()

        txt_nrp_operator_detail.text = data?.personel?.nrp
        btn_copy_nrp_detail.setOnClickListener {
            //COPY
            copyTextToClipboard(txt_nrp_operator_detail)
        }
        txt_jabatan_operator_detail.text = data?.personel?.jabatan
        txt_kesatuan_operator_detail.text = data?.satuan_kerja?.kesatuan
        btn_edit_personel_operator.setOnClickListener {
            val intent = Intent(this, EditOperatorActivity::class.java)
            intent.putExtra("operator", data)
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
        val dataOper = intent.extras?.getParcelable<UserCreatorResp>("acc")

        NetworkConfig().getService()
            .delPersOperator("Bearer ${sessionManager1.fetchAuthToken()}", dataOper?.id.toString())
            .enqueue(object : Callback<BaseResp> {
                override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                    Toast.makeText(
                        this@DetailOperatorActivity,
                        "Error Koneksi / Lakukan Beberapa Saat",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@DetailOperatorActivity,
                            R.string.data_deleted,
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@DetailOperatorActivity,
                            "Error Koneksi / Lakukan Beberapa Saat",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
    }

    override fun onResume() {
        super.onResume()
        val dataOper = intent.extras?.getParcelable<UserCreatorResp>("acc")
        getDetailOperator(dataOper)
    }
}