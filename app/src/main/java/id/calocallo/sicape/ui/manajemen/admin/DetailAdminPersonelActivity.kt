package id.calocallo.sicape.ui.manajemen.admin

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.network.response.UserCreatorResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_admin_personel.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailAdminPersonelActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_admin_personel)
        setupActionBarWithBackButton(toolbar)
        sessionManager1 = SessionManager1(this)
        supportActionBar?.title = "Detail Data Admin"
        val detailAdmin = intent.getParcelableExtra<UserCreatorResp>("admin")
        apiDetailAdmin(detailAdmin)



        btn_edit_personel_admin.setOnClickListener {
            val intent= Intent(this, EditAdminPersonelActivity::class.java)
            intent.putExtra("admin", detailAdmin)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

    }

    private fun apiDetailAdmin(detailAdmin: UserCreatorResp?) {
        NetworkConfig().getService().getDetailAdmin("Bearer ${sessionManager1.fetchAuthToken()}",
        detailAdmin?.id.toString())
            .enqueue(object : Callback<UserCreatorResp> {
                override fun onFailure(call: Call<UserCreatorResp>, t: Throwable) {
                    Toast.makeText(
                        this@DetailAdminPersonelActivity,
                        "Error Koneksi",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResponse(
                    call: Call<UserCreatorResp>,
                    response: Response<UserCreatorResp>
                ) {
                    if(response.isSuccessful){
                        viewDetailAdmin(response.body())
                    }else{
                        Toast.makeText(
                            this@DetailAdminPersonelActivity,
                            "Error Koneksi",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
    }

    private fun viewDetailAdmin(admin: UserCreatorResp?) {
        txt_nama_admin_detail.text = admin?.personel?.nama
        txt_pangkat_admin_detail.text = admin?.personel?.pangkat.toString().toUpperCase()
        txt_nrp_admin_detail.text = admin?.personel?.nrp
        btn_copy_nrp_detail.setOnClickListener {
            copyTextToClipboard(txt_nrp_admin_detail)
        }
        txt_jabatan_admin_detail.text = admin?.personel?.jabatan
        txt_kesatuan_admin_detail.text = admin?.personel?.satuan_kerja?.kesatuan
        if(admin?.is_aktif == 1){
            txt_status_aktif_admin_detail.text = "Aktif"
        }else{
            txt_status_aktif_admin_detail.text = "Tidak Aktif"
        }
    }

    private fun copyTextToClipboard(tvText: TextView) {
        val textToCopy = tvText.text
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", textToCopy)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(this, "Text copied to clipboard", Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        val detailAdmin = intent.getParcelableExtra<UserCreatorResp>("admin")
        apiDetailAdmin(detailAdmin)
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
        val detailAdmin = intent.getParcelableExtra<UserCreatorResp>("admin")

        NetworkConfig().getService()
            .delAdmin("Bearer ${sessionManager1.fetchAuthToken()}", detailAdmin?.id.toString())
            .enqueue(object : Callback<BaseResp> {
                override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                    Toast.makeText(
                        this@DetailAdminPersonelActivity,
                        "Error Koneksi / Lakukan Beberapa Saat",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@DetailAdminPersonelActivity,
                            R.string.data_deleted,
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@DetailAdminPersonelActivity,
                            "Error Koneksi / Lakukan Beberapa Saat",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
    }
}