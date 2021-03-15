package id.calocallo.sicape.ui.main.lhp.add

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import id.calocallo.sicape.R
import id.calocallo.sicape.utils.LhpDataManager
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_lhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddLhpActivity : BaseActivity() {

//    private  var lhp= LhpModel()

    private var isTerbukti: Int? = null

    companion object {
        const val REQ_LP = 100
        const val DATA_LP = "DATA_LP"
    }
    private lateinit var lhp: LhpDataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lhp)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Laporan Hasil Penyelidikan"
        lhp = LhpDataManager(this)
//        lhp = LhpModel()
        //do function
//        initRV()
//        btn_choose_lp_lhp.setOnClickListener {
//            val intent = Intent(this, ChooseLpActivity::class.java)
//            startActivityForResult(intent, REQ_LP)
//        }
//        btn_next_lhp.attachTextChangeAnimator()
//        bindProgressButton(btn_next_lhp)

        btn_next_lhp.setOnClickListener {
            var id: Int = rg_terbukti.checkedRadioButtonId
            if (id != -1) {
                val radio: RadioButton = findViewById(id)
                when (radio.text) {
                    "Terbukti" -> isTerbukti = 1
                    "Tidak Terbukti" -> isTerbukti = 0
                }
                Log.e("isTerbutki", "${isTerbukti}")
            }
            lhp.setNoLHP(edt_no_lhp.text.toString())
            lhp.setTentangLHP(edt_isi_pengaduan_lhp.text.toString())
            lhp.setSPLHP(edt_no_sp_lhp.text.toString())
            lhp.setWaktuLHP(edt_waktu_penugasan_lhp.text.toString())
            lhp.setDaerahLHP(edt_tempat_penyelidikan_lhp.text.toString())
            lhp.setTugasPokokLHP(edt_tugas_pokok_lhp.text.toString())
            lhp.setPokokPermasalahanLHP(edt_pokok_permasalahan_lhp.text.toString())
            lhp.setKetAhliLHP(edt_keterangan_ahli_lhp.text.toString())
            lhp.setKesimpulanLHP(edt_kesimpulan_lhp.text.toString())
            lhp.setRekomendasiLHP(edt_rekomendasi_lhp.text.toString())
            isTerbukti?.let { it1 -> lhp.setIsTerbukti(it1) }
            val intent = Intent(this, ListRefPenyelidikanActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

            Log.e("lhp_add", "$lhp")

        }
    }

    /*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val lp = data?.getParcelableExtra<LpResp>(DATA_LP)
        when (resultCode) {
            Activity.RESULT_OK -> {
                when (requestCode) {
                    REQ_LP -> {
                        when (lp?.jenis) {
                            "pidana" -> txt_jenis_lp_lhp.text = "Laporan Polisi Pidana"
                            "kode_etik" -> txt_jenis_lp_lhp.text = "Laporan Polisi Kode Etik"
                            "disiplin" -> txt_jenis_lp_lhp.text = "Laporan Polisi Disiplin"
                        }
                        txt_no_lp_lhp.text = lp?.no_lp
                        txt_nama_personel_lhp.text = lp?.id_personel_terlapor.toString()
                        txt_pangkat_nrp_personel_lhp.text =
                            "Pangkat ${lp?.id_personel_terlapor}, NRP : ${lp?.id_personel_terlapor}"
                        txt_jabatan_personel_lhp.text = lp?.id_personel_terlapor.toString()
                        txt_kesatuan_personel_lhp.text = lp?.id_personel_terlapor.toString()
                    }
                }
            }

        }
    }

    private fun initRV() {
        //lidik
        listLidik.add(ListLidik())
        rv_lidik.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterLidik = LidikAdapter(
            this,
            listLidik,
            object : LidikAdapter.OnClickLidik {
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
        rv_lidik.adapter = adapterLidik

        //saksi
        listSaksi.add(ListSaksi())
        rv_saksi.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterSaksi = SaksiAdapter(
            this,
            listSaksi,
            object : SaksiAdapter.OnClickSaksi {
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
        rv_saksi.adapter = adapterSaksi

        //surat
        listSurat.add(ListSurat())
        rv_surat.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterSurat = SuratAdapter(
            this,
            listSurat,
            object : SuratAdapter.OnClickSurat {
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
        rv_surat.adapter = adapterSurat

        //petunjuk
        listPetunjuk.add(ListPetunjuk())
        rv_petunjuk.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterPetunjuk =
            PetunjukAdapter(
                this,
                listPetunjuk,
                object :
                    PetunjukAdapter.OnClickPetunjuk {
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
        rv_petunjuk.adapter = adapterPetunjuk

        //bukti
        listBukti.add(ListBukti())
        rv_bukti.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterBukti = BuktiAdapter(
            this,
            listBukti,
            object : BuktiAdapter.OnClickBukti {
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
        rv_bukti.adapter = adapterBukti

        //terlapor
        listTerlapor.add(ListTerlapor())
        rv_terlapor.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterTerlapor =
            TerlaporAdapter(
                this,
                listTerlapor,
                object :
                    TerlaporAdapter.OnClickTerlapor {
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
        rv_terlapor.adapter = adapterTerlapor

        //analisa
        listAnalisa.add(ListAnalisa())
        rv_analisa.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterAnalisa = AnalisaAdapter(
            this,
            listAnalisa,
            object :
                AnalisaAdapter.OnClickAnalisa {
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
        rv_analisa.adapter = adapterAnalisa
    }

     */

}