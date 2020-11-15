package id.calocallo.sicape.ui.main.personel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.ui.main.editpersonel.EditPendidikanActivity
import id.calocallo.sicape.ui.main.editpersonel.EditPersonelActivity
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.toggleVisibility
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_personel.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPersonelActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var idPersonel = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_personel)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Personel"

        sessionManager = SessionManager(this)
        val hakAkses = sessionManager.fetchHakAkses()
        if (hakAkses == "operator") {
            btn_edit_personel.toggleVisibility()
        }
        val bundle = intent.extras
        val detail = bundle?.getParcelable<PersonelModel>("PERSONEL")
        idPersonel = detail?.id.toString()
        if (bundle != null) {
            val nama = detail?.nama
            val alias = detail?.nama_alias
            val jabatan = detail?.jabatan
            val kesatuan = detail?.kesatuan
            val pangkat = detail?.pangkat
            val nrp = detail?.nrp
            val tgl_lhr = detail?.tanggal_lahir
            val tmpt_lhr = detail?.tempat_lahir
            var jk = detail?.jenis_kelamin
            var agama = detail?.agama_sekarang
            val almt_kntr = detail?.alamat_kantor

            if (jk == "laki_laki") {
                jk = "Laki-laki"
            } else {
                jk = "Perempuan"
            }

            agama = when (agama) {
                "islam" -> "Islam"
                "katolik" -> "Katolik"
                "protestas" -> "Protestan"
                "budha" -> "Budha"
                "hindu" -> "Hindu"
                else -> "Khonghucu"
            }

            txt_nama_personel.text = nama
            txt_ttl_personel.text = "$tmpt_lhr, $tgl_lhr"
            txt_jk.text = jk
            txt_agama.text = agama
            txt_pangkat_nrp.text = "$pangkat / $nrp"
            txt_jabatan_personel.text = jabatan
            txt_kesatuan_personel.text = kesatuan
            txt_alamat_kantor_personel.text = almt_kntr

        } else {
            Log.e("tidak masuk", "tidak masuk")
        }

        btn_edit_personel.setOnClickListener {
            val intent = Intent(this, EditPersonelActivity::class.java)
            intent.putExtra("PERSONEL_DETAIL", detail)
            startActivity(intent)
        }
        btn_edit_pendidikan.setOnClickListener {
            startActivity(Intent(this, EditPendidikanActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_delete, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_delete_personel -> {
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
        NetworkConfig().getService()
            .deletePersonel("Bearer ${sessionManager.fetchAuthToken()}", idPersonel)
            .enqueue(object : Callback<BaseResp> {
                override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                    Toast.makeText(this@DetailPersonelActivity, "Gagal Koneksi", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                    if (response.isSuccessful) {
                        if (response.body()?.message == "Data personel removed succesfully") {
                        } else {
                            Toast.makeText(
                                this@DetailPersonelActivity,
                                "Error Hapus",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } else {
                        Toast.makeText(this@DetailPersonelActivity, "Error", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            })
    }
}