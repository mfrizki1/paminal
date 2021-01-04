package id.calocallo.sicape.ui.main.lhp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import id.calocallo.sicape.R
import id.calocallo.sicape.model.*
import id.calocallo.sicape.network.response.LpResp
import id.calocallo.sicape.ui.main.choose.lp.ChooseLpActivity
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_lhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditLhpActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var adapterLidik: LidikAdapter
    private lateinit var adapterSaksi: SaksiAdapter
    private lateinit var adapterSurat: SuratAdapter
    private lateinit var adapterPetunjuk: PetunjukAdapter
    private lateinit var adapterBukti: BuktiAdapter
    private lateinit var adapterTerlapor: TerlaporAdapter
    private lateinit var adapterAnalisa: AnalisaAdapter

    companion object {
        const val EDIT_LHP = "EDIT_LHP"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_lhp)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Laporan Hasil Penyelidikan"
        sessionManager = SessionManager(this)

        val bundle = intent.extras
        val editLHP = bundle?.getParcelable<LhpModel>(EDIT_LHP)

        getViewLhpEdit(editLHP)
        val hak = sessionManager.fetchHakAkses()
        if(hak =="operator"){

        }

        btn_choose_lp_lhp_edit.setOnClickListener {
            val intent = Intent(this, ChooseLpActivity::class.java)
            startActivityForResult(intent, AddLhpActivity.REQ_LP)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val lp = data?.getParcelableExtra<LpResp>(AddLhpActivity.DATA_LP)
        when (resultCode) {
            Activity.RESULT_OK -> {
                when (requestCode) {
                    AddLhpActivity.REQ_LP -> {
                        when (lp?.jenis) {
                            "pidana" -> txt_jenis_lp_lhp_edit.text = "Laporan Polisi Pidana"
                            "kode_etik" -> txt_jenis_lp_lhp_edit.text = "Laporan Polisi Kode Etik"
                            "disiplin" -> txt_jenis_lp_lhp_edit.text = "Laporan Polisi Disiplin"
                        }
                        txt_no_lp_lhp_edit.text = lp?.no_lp
                        txt_nama_personel_lhp_edit.text = lp?.id_personel_terlapor.toString()
                        txt_pangkat_nrp_personel_lhp_edit.text =
                            "Pangkat ${lp?.id_personel_terlapor}, NRP : ${lp?.id_personel_terlapor}"
                        txt_jabatan_personel_lhp_edit.text = lp?.id_personel_terlapor.toString()
                        txt_kesatuan_personel_lhp_edit.text = lp?.id_personel_terlapor.toString()
                    }
                }
            }

        }
    }

    private fun getViewLhpEdit(editLHP: LhpModel?) {
        Log.e("isTerbukti", "${editLHP?.isTerbukti}")
        edt_no_lhp_edit.setText(editLHP?.no_lhp)
        edt_isi_pengaduan_lhp_edit.setText(editLHP?.tentang)
        edt_no_sp_lhp_edit.setText(editLHP?.no_surat_perintah_penyelidikan)
        edt_waktu_penugasan_lhp_edit.setText(editLHP?.waktu_penugasan)
        edt_tempat_penyelidikan_lhp_edit.setText(editLHP?.daerah_penyelidikan)
        edt_tugas_pokok_lhp_edit.setText(editLHP?.tugas_pokok)
//        edt_rencana_pelaksanaan_lhp_edit.setText(editLHP?.rencana_pelaksanaan_penyelidikan)
//        edt_pelaksanaan_lhp_edit.setText(editLHP?.pelaksanan)
        edt_pokok_permasalahan_lhp_edit.setText(editLHP?.pokok_permasalahan)
        edt_keterangan_ahli_lhp_edit.setText(editLHP?.keterangan_ahli)
        edt_kesimpulan_lhp_edit.setText(editLHP?.kesimpulan)
        edt_rekomendasi_lhp_edit.setText(editLHP?.rekomendasi)
//        edt_nama_ketua_tim_lhp_edit.setText(editLHP?.nama_ketua_tim)
//        edt_pangkat_ketua_tim_lhp_edit.setText(editLHP?.pangkat_ketua_tim)
//        edt_nrp_ketua_tim_lhp_edit.setText(editLHP?.nrp_ketua_tim)
        if(editLHP?.isTerbukti == 0){
            rb_tidak_terbukti_edit.isChecked = true
        }else{
            rb_terbukti_edit.isChecked = true
        }
        /*
        val listLidik = editLHP?.listLidik
        adapterLidik = LidikAdapter(this, listLidik!!, object : LidikAdapter.OnClickLidik {
            override fun onAdd() {
                listLidik.add(ListLidik())
                val position = if (listLidik.isEmpty()) 0 else listLidik.size - 1
                adapterLidik.notifyItemInserted(position)
                adapterLidik.notifyDataSetChanged()
            }

            override fun onDelete(position: Int) {
                listLidik.removeAt(position)
                adapterLidik.notifyDataSetChanged()
            }
        })
        rv_lidik.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_lidik.adapter = adapterLidik

        val listSaksi = editLHP.listSaksi
        adapterSaksi = listSaksi?.let {
            SaksiAdapter(this, it, object : SaksiAdapter.OnClickSaksi {
                override fun onAdd() {
                    listSaksi.add(ListSaksi())
                    val position = if (listSaksi.isEmpty()) 0 else listSaksi.size - 1
                    adapterSaksi.notifyItemInserted(position)
                    adapterSaksi.notifyDataSetChanged()
                }

                override fun onDelete(position: Int) {
                    listSaksi.removeAt(position)
                    adapterSaksi.notifyDataSetChanged()
                }
            })
        }!!
        rv_saksi.adapter = adapterSaksi

        val listSurat = editLHP.listSurat
        adapterSurat = listSurat?.let {
            SuratAdapter(this, it, object : SuratAdapter.OnClickSurat {
                override fun onAdd() {
                    listSurat.add(ListSurat())
                    val position = if (listSurat.isEmpty()) 0 else listSurat.size - 1
                    adapterSurat.notifyItemInserted(position)
                    adapterSurat.notifyDataSetChanged()
                }

                override fun onDelete(position: Int) {
                    listSurat.removeAt(position)
                    adapterSurat.notifyDataSetChanged()
                }
            })
        }!!
        rv_surat.adapter = adapterSurat

        val listPetunjuk = editLHP.listPetunjuk
        adapterPetunjuk = listPetunjuk?.let {
            PetunjukAdapter(this, it, object : PetunjukAdapter.OnClickPetunjuk {
                override fun onAdd() {
                    listPetunjuk.add(ListPetunjuk())
                    val position = if (listPetunjuk.isEmpty()) 0 else listPetunjuk.size - 1
                    adapterPetunjuk.notifyItemInserted(position)
                    adapterPetunjuk.notifyDataSetChanged()
                }

                override fun onDelete(position: Int) {
                    listPetunjuk.removeAt(position)
                    adapterPetunjuk.notifyDataSetChanged()
                }
            })
        }!!
        rv_petunjuk.adapter = adapterPetunjuk

        val listBukti = editLHP.listBukti
        adapterBukti = listBukti?.let {
            BuktiAdapter(this, it, object : BuktiAdapter.OnClickBukti {
                override fun onAdd() {
                    listBukti.add(ListBukti())
                    val position = if (listBukti.isEmpty()) 0 else listBukti.size - 1
                    adapterBukti.notifyItemInserted(position)
                    adapterBukti.notifyDataSetChanged()
                }

                override fun onDelete(position: Int) {
                    listBukti.removeAt(position)
                    adapterBukti.notifyDataSetChanged()
                }

            })
        }!!
        rv_bukti.adapter = adapterBukti

        val listTerlapor = editLHP.listTerlapor
        adapterTerlapor = listTerlapor?.let {
            TerlaporAdapter(this, it, object : TerlaporAdapter.OnClickTerlapor {
                override fun onAdd() {
                    listTerlapor.add(ListTerlapor())
                    val position = if (listTerlapor.isEmpty()) 0 else listTerlapor.size - 1
                    adapterTerlapor.notifyItemInserted(position)
                    adapterTerlapor.notifyDataSetChanged()
                }

                override fun onDelete(position: Int) {
                    listTerlapor.removeAt(position)
                    adapterTerlapor.notifyDataSetChanged()
                }
            })
        }!!
        rv_terlapor.adapter = adapterTerlapor

        val listAnalisa = editLHP.listAnalisa
        adapterAnalisa = listAnalisa?.let {
            AnalisaAdapter(this, it, object : AnalisaAdapter.OnClickAnalisa {
                override fun onAdd() {
                    listAnalisa.add(ListAnalisa())
                    val position = if (listAnalisa.isEmpty()) 0 else listAnalisa.size - 1
                    adapterAnalisa.notifyItemInserted(position)
                    adapterAnalisa.notifyDataSetChanged()
                }

                override fun onDelete(position: Int) {
                    listAnalisa.removeAt(position)
                    adapterAnalisa.notifyDataSetChanged()
                }
            })
        }!!
        rv_analisa.adapter = adapterAnalisa


        Log.e("size", "${}")

         */
    }

}