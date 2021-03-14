package id.calocallo.sicape.ui.main.personel

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AllPersonelModel1
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.network.response.PersonelMinResp
import id.calocallo.sicape.ui.main.editpersonel.pendidikan.EditPendidikanActivity
import id.calocallo.sicape.ui.main.editpersonel.EditPersonelActivity
import id.calocallo.sicape.ui.main.editpersonel.alamat.PickAlamatActivity
import id.calocallo.sicape.ui.main.editpersonel.anak.PickAnakActivity
import id.calocallo.sicape.ui.main.editpersonel.foto.EditFotoActivity
import id.calocallo.sicape.ui.main.editpersonel.keluarga.PickKeluargaActivity
import id.calocallo.sicape.ui.main.editpersonel.keluarga.PickPasanganActivity
import id.calocallo.sicape.ui.main.editpersonel.med_sos.PickMedSosActivity
import id.calocallo.sicape.ui.main.editpersonel.media_info.PickMedInfoActivity
import id.calocallo.sicape.ui.main.editpersonel.orangs.PickOrangsActivity
import id.calocallo.sicape.ui.main.editpersonel.organisasi_dll.PickMenuOrganisasiDllActivity
import id.calocallo.sicape.ui.main.editpersonel.pekerjaan.EditPekerjaanActivity
import id.calocallo.sicape.ui.main.editpersonel.relasi.PickRelasiActivity
import id.calocallo.sicape.ui.main.editpersonel.sahabat.PickSahabatActivity
import id.calocallo.sicape.ui.main.editpersonel.saudara.PickSaudaraActivity
import id.calocallo.sicape.ui.main.editpersonel.signalement.EditSignalementActivity
import id.calocallo.sicape.ui.main.editpersonel.tokoh.PickTokohActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.formatterTanggal
import id.calocallo.sicape.utils.ext.setFromUrl
import id.calocallo.sicape.utils.ext.toggleVisibility
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_personel.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPersonelActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var idPersonel = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_personel)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Personel"

        sessionManager1 = SessionManager1(this)
        val hakAkses = sessionManager1.fetchHakAkses()
        if (hakAkses == "operator") {
            btn_edit_personel.toggleVisibility()
        }
        val bundle = intent.extras
        val detail = bundle?.getParcelable<PersonelMinResp>("PERSONEL")
        idPersonel = detail?.id.toString()
        detail?.id?.let { sessionManager1.saveID(it) }
        apiDetailPersonel(detail)

//        buttonDetailPersonel(detail)

    }

    private fun buttonDetailPersonel(detail: AllPersonelModel1?) {
        //        btn_edit_catpers.setOnClickListener {
//            Toast.makeText(this, "Catatarn Personel Personel", Toast.LENGTH_SHORT).show()
//        }
        btn_edit_personel.setOnClickListener {
            val intent = Intent(this, EditPersonelActivity::class.java).apply {
                this.putExtra("PERSONEL_DETAIL", detail)
            }
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_edit_pendidikan.setOnClickListener {
            val intent = Intent(this, EditPendidikanActivity::class.java)
            intent.putExtra("PERSONEL_DETAIL", detail)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_edit_pekerjaan.setOnClickListener {
            val intent = Intent(this, EditPekerjaanActivity::class.java)
            intent.putExtra("PERSONEL_DETAIL", detail)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_edit_alamat.setOnClickListener {
            val intent = Intent(this, PickAlamatActivity::class.java)
            intent.putExtra("PERSONEL_DETAIL", detail)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_edit_organisasi_dll.setOnClickListener {
            val intent = Intent(this, PickMenuOrganisasiDllActivity::class.java)
            intent.putExtra("PERSONEL_DETAIL", detail)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
//        btn_edit_perjuangan.setOnClickListener {
//            Toast.makeText(this, "Perjuangan Menggapai Cita-cita", Toast.LENGTH_SHORT).show()
//        }
//        btn_edit_penghargaan.setOnClickListener {
//            Toast.makeText(this, "Penghargaan", Toast.LENGTH_SHORT).show()
//        }
        btn_edit_keluarga.setOnClickListener {
            val intent = Intent(this, PickKeluargaActivity::class.java)
            intent.putExtra("PERSONEL_DETAIL", detail)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_edit_pasangan.setOnClickListener {
            val intent = Intent(this, PickPasanganActivity::class.java)
            intent.putExtra("PERSONEL_DETAIL", detail)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        /*btn_edit_ayah_kandung.setOnClickListener {
            val intent = Intent(this, EditKeluargaActivity::class.java)
            intent.putExtra("PERSONEL_DETAIL", detail)
            intent.putExtra("KELUARGA", "ayah_kandung")
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_edit_ayah_tiri.setOnClickListener {
            val intent = Intent(this, EditKeluargaActivity::class.java)
            intent.putExtra("PERSONEL_DETAIL", detail)
            intent.putExtra("KELUARGA", "ayah_tiri")
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_edit_ibu_kandung.setOnClickListener {
            val intent = Intent(this, EditKeluargaActivity::class.java)
            intent.putExtra("PERSONEL_DETAIL", detail)
            intent.putExtra("KELUARGA", "ibu_kandung")
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_edit_ibu_tiri.setOnClickListener {
            val intent = Intent(this, EditKeluargaActivity::class.java)
            intent.putExtra("PERSONEL_DETAIL", detail)
            intent.putExtra("KELUARGA", "ibu_tiri")
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_edit_mertua_laki.setOnClickListener {
            val intent = Intent(this, EditKeluargaActivity::class.java)
            intent.putExtra("PERSONEL_DETAIL", detail)
            intent.putExtra("KELUARGA", "mertua_laki_laki")
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_edit_mertua_perempuan.setOnClickListener {
            val intent = Intent(this, EditKeluargaActivity::class.java)
            intent.putExtra("PERSONEL_DETAIL", detail)
            intent.putExtra("KELUARGA", "mertua_perempuan")
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }*/
        btn_edit_anak.setOnClickListener {
            val intent = Intent(this, PickAnakActivity::class.java)
            intent.putExtra("PERSONEL_DETAIL", detail)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
//            Toast.makeText(this, "Anak", Toast.LENGTH_SHORT).show()
        }
        btn_edit_saudara.setOnClickListener {
            val intent = Intent(this, PickSaudaraActivity::class.java)
            intent.putExtra("PERSONEL_DETAIL", detail)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
//            Toast.makeText(this, "Saudara", Toast.LENGTH_SHORT).show()
        }
        btn_edit_orang_berjasa.setOnClickListener {
            val intent = Intent(this, PickOrangsActivity::class.java)
            intent.putExtra("PERSONEL_DETAIL", detail)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
//            Toast.makeText(this, "Orang Berjasa", Toast.LENGTH_SHORT).show()
        }
//        btn_edit_orang_disegani.setOnClickListener {
//            Toast.makeText(this, "Orang Disegani", Toast.LENGTH_SHORT).show()
//        }
        btn_edit_tokoh.setOnClickListener {
            val intent = Intent(this, PickTokohActivity::class.java)
            intent.putExtra("PERSONEL_DETAIL", detail)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
//            Toast.makeText(this, "Tokoh", Toast.LENGTH_SHORT).show()
        }
        btn_edit_kawan.setOnClickListener {
            val intent = Intent(this, PickSahabatActivity::class.java)
            intent.putExtra("PERSONEL_DETAIL", detail)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
//            Toast.makeText(this, "Sahabat", Toast.LENGTH_SHORT).show()
        }
        btn_edit_media_informasi.setOnClickListener {
            val intent = Intent(this, PickMedInfoActivity::class.java)
            intent.putExtra("PERSONEL_DETAIL", detail)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
//            Toast.makeText(this, "Media Informasi", Toast.LENGTH_SHORT).show()
        }
        btn_edit_medsos.setOnClickListener {
            val intent = Intent(this, PickMedSosActivity::class.java)
            intent.putExtra("PERSONEL_DETAIL", detail)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
//            Toast.makeText(this, "Media Sosial", Toast.LENGTH_SHORT).show()
        }
        btn_edit_foto.setOnClickListener {
            val intent = Intent(this, EditFotoActivity::class.java)
            intent.putExtra("PERSONEL_DETAIL", detail)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
//            Toast.makeText(this, "FOto", Toast.LENGTH_SHORT).show()
        }
        btn_edit_signalement.setOnClickListener {
            val intent = Intent(this, EditSignalementActivity::class.java)
            intent.putExtra("PERSONEL_DETAIL", detail)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
//            Toast.makeText(this, "Signalement", Toast.LENGTH_SHORT).show()
        }
        btn_edit_relasi.setOnClickListener {
            val intent = Intent(this, PickRelasiActivity::class.java)
            intent.putExtra("PERSONEL_DETAIL", detail)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    override fun onResume() {
        super.onResume()
        val bundle = intent.extras
        val detail = bundle?.getParcelable<PersonelMinResp>("PERSONEL")
        apiDetailPersonel(detail)
    }

    private fun apiDetailPersonel(detail: PersonelMinResp?) {
        NetworkConfig().getServPers().showPersonelById(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            detail?.id.toString()
        ).enqueue(object : Callback<AllPersonelModel1> {
            override fun onFailure(call: Call<AllPersonelModel1>, t: Throwable) {
                Toast.makeText(this@DetailPersonelActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(
                call: Call<AllPersonelModel1>,
                response: Response<AllPersonelModel1>
            ) {
                if (response.isSuccessful) {
                    getViewDetailPersonel(response.body())
                    buttonDetailPersonel(response.body())
                } else {
                    Toast.makeText(this@DetailPersonelActivity, "Error Koneksi", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    private fun getViewDetailPersonel(data: AllPersonelModel1?) {
        img_personel.setFromUrl(data?.foto_muka?.url.toString())
        txt_kesatuan_personel.text = data?.satuan_kerja?.kesatuan
        txt_nama_personel.text = data?.nama
        txt_ttl_personel.text =
            "${data?.tempat_lahir}, ${formatterTanggal(data?.tanggal_lahir)}"
        if (data?.jenis_kelamin == "laki_laki") {
            txt_jk.text = "Laki-Laki"
        } else {
            txt_jk.text = "Perempuan"
        }
        when (data?.agama_sekarang) {
            "islam" -> txt_agama.text = "Islam"
            "katolik" -> txt_agama.text = "Katolik"
            "protestas" -> txt_agama.text = "Protestan"
            "budha" -> txt_agama.text = "Budha"
            "hindu" -> txt_agama.text = "Hindu"
            else -> "Khonghucu"
        }

        txt_pangkat_nrp.text =" ${data?.pangkat.toString().toUpperCase()} / ${data?.nrp}"
        txt_jabatan_personel.text = data?.jabatan
        txt_alamat_kantor_personel.text = data?.satuan_kerja?.alamat_kantor
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
        NetworkConfig().getServPers()
            .deletePersonel("Bearer ${sessionManager1.fetchAuthToken()}", idPersonel)
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