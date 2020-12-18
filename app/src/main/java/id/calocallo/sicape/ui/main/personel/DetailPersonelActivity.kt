package id.calocallo.sicape.ui.main.personel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.ui.main.editpersonel.pendidikan.EditPendidikanActivity
import id.calocallo.sicape.ui.main.editpersonel.EditPersonelActivity
import id.calocallo.sicape.ui.main.editpersonel.alamat.PickAlamatActivity
import id.calocallo.sicape.ui.main.editpersonel.pekerjaan.EditPekerjaanActivity
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.alert
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
        detail?.id?.let { sessionManager.saveID(it) }
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
            val intent = Intent(this, EditPendidikanActivity::class.java)
            intent.putExtra("PERSONEL_DETAIL", detail)
            startActivity(intent)
        }
        btn_edit_pekerjaan.setOnClickListener {
            val intent = Intent(this, EditPekerjaanActivity::class.java)
            intent.putExtra("PERSONEL_DETAIL", detail)
            startActivity(intent)
        }
        btn_edit_alamat.setOnClickListener {
            val intent = Intent(this, PickAlamatActivity::class.java)
            intent.putExtra("PERSONEL_DETAIL", detail)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            Toast.makeText(this, "Alamat", Toast.LENGTH_SHORT).show()
        }
        btn_edit_organisasi.setOnClickListener {
            Toast.makeText(this, "organisasi", Toast.LENGTH_SHORT).show()
        }
        btn_edit_perjuangan.setOnClickListener {
            Toast.makeText(this, "Perjuangan Menggapai Cita-cita", Toast.LENGTH_SHORT).show()
        }
        btn_edit_penghargaan.setOnClickListener {
            Toast.makeText(this, "Penghargaan", Toast.LENGTH_SHORT).show()
        }
        btn_edit_pasangan.setOnClickListener {
            Toast.makeText(this, "Pasangan", Toast.LENGTH_SHORT).show()
        }
        btn_edit_ayah_kandung.setOnClickListener {
            Toast.makeText(this, "Ayah Kandung", Toast.LENGTH_SHORT).show()
        }
        btn_edit_ayah_tiri.setOnClickListener {
            Toast.makeText(this, "Ayah Tiri", Toast.LENGTH_SHORT).show()
        }
        btn_edit_ibu_kandung.setOnClickListener {
            Toast.makeText(this, "Ibu Kandung", Toast.LENGTH_SHORT).show()
        }
        btn_edit_ibu_tiri.setOnClickListener {
            Toast.makeText(this, "Ibu Tiri", Toast.LENGTH_SHORT).show()
        }
        btn_edit_mertua_laki.setOnClickListener {
            Toast.makeText(this, "Mertua Laki", Toast.LENGTH_SHORT).show()
        }
        btn_edit_mertua_perempuan.setOnClickListener {
            Toast.makeText(this, "Mertua Perempuan", Toast.LENGTH_SHORT).show()
        }
        btn_edit_anak.setOnClickListener {
            Toast.makeText(this, "Anak", Toast.LENGTH_SHORT).show()
        }
        btn_edit_saudara.setOnClickListener {
            Toast.makeText(this, "Saudara", Toast.LENGTH_SHORT).show()
        }
        btn_edit_orang_berjasa.setOnClickListener {
            Toast.makeText(this, "Orang Berjasa", Toast.LENGTH_SHORT).show()
        }
        btn_edit_orang_disegani.setOnClickListener {
            Toast.makeText(this, "Orang Disegani", Toast.LENGTH_SHORT).show()
        }
        btn_edit_tokoh.setOnClickListener {
            Toast.makeText(this, "Tokoh", Toast.LENGTH_SHORT).show()
        }
        btn_edit_kawan.setOnClickListener {
            Toast.makeText(this, "Sahabat", Toast.LENGTH_SHORT).show()
        }
        btn_edit_media_informasi.setOnClickListener {
            Toast.makeText(this, "Media Informasi", Toast.LENGTH_SHORT).show()
        }
        btn_edit_medsos.setOnClickListener {
            Toast.makeText(this, "Media Sosial", Toast.LENGTH_SHORT).show()
        }
        btn_edit_foto.setOnClickListener {
            Toast.makeText(this, "FOto", Toast.LENGTH_SHORT).show()
        }
        btn_edit_signalement.setOnClickListener {
            Toast.makeText(this, "Signalement", Toast.LENGTH_SHORT).show()
        }
        btn_edit_relasi.setOnClickListener {
            Toast.makeText(this, "Relasi", Toast.LENGTH_SHORT).show()
        }
        btn_edit_catpers.setOnClickListener {
            Toast.makeText(this, "Catatarn Personel Personel", Toast.LENGTH_SHORT).show()
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