package id.calocallo.sicape.ui.main.lhp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.*
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_lhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class AddLhpActivity : BaseActivity() {
    private lateinit var listLidik: ArrayList<ListLidik>
    private lateinit var listSaksi: ArrayList<ListSaksi>
    private lateinit var listSurat: ArrayList<ListSurat>
    private lateinit var listPetunjuk: ArrayList<ListPetunjuk>
    private lateinit var listBukti: ArrayList<ListBukti>
    private lateinit var listTerlapor: ArrayList<ListTerlapor>
    private lateinit var listAnalisa: ArrayList<ListAnalisa>
    private lateinit var lhp: LhpModel
    private lateinit var adapterLidik: LidikAdapter
    private lateinit var adapterSaksi: SaksiAdapter
    private lateinit var adapterSurat: SuratAdapter
    private lateinit var adapterPetunjuk: PetunjukAdapter
    private lateinit var adapterBukti: BuktiAdapter
    private lateinit var adapterTerlapor: TerlaporAdapter
    private lateinit var adapterAnalisa: AnalisaAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lhp)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Laporan Hasil Penyelidikan"

        listLidik = ArrayList()
        listSaksi = ArrayList()
        listSurat = ArrayList()
        listPetunjuk = ArrayList()
        listBukti = ArrayList()
        listTerlapor = ArrayList()
        listAnalisa = ArrayList()

        lhp = LhpModel()
        //do function
        initRV()
        var isTerbukti = 0
        rb_terbukti.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                isTerbukti = 1
            }
        }
        rb_tidak_terbukti.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                isTerbukti = 0
            }
        }

        btn_save_lhp.setOnClickListener {
            if(listLidik.size ==1 && listLidik[0].nama_lidik == ""){
                listLidik.clear()
            }
            if(listSaksi.size == 1 && listSaksi[0].nama_saksi == ""){
                listSaksi.clear()
            }
            if(listSurat.size ==1 && listSurat[0].surat == ""){
                listSurat.clear()
            }
            if(listPetunjuk.size ==1 && listPetunjuk[0].petunjuk == ""){
                listPetunjuk.clear()
            }
            if(listBukti.size ==1 && listBukti[0].bukti == ""){
                listBukti.clear()
            }
            if(listTerlapor.size ==1 && listTerlapor[0].nama_terlapor == ""){
                listTerlapor.clear()
            }
            if(listAnalisa.size == 1&& listAnalisa[0].analisa == ""){
                listAnalisa.clear()
            }

            Log.e("LIDIK", listLidik.size.toString())
//            Log.e("size saksi", listLidik[0].nrp_lidik)
//            Log.e("size saksi", listLidik[1].nama_lidik)
//            Log.e("size saksi", listLidik[1].nrp_lidik)
//
            Log.e("SAKSI", listSaksi.size.toString())
//            Log.e("size saksi", listSaksi[1].uraian_saksi.toString())


            Log.e("SURAT", listSurat.size.toString())
//            Log.e("SURAT", listSurat[0].surat.toString())
//            Log.e("SURAT", listSurat[1].surat.toString())

            Log.e("PETUNJUK", listPetunjuk.size.toString())
//            Log.e("PETUNJUK", listPetunjuk[0].petunjuk.toString())
//            Log.e("PETUNJUK", listPetunjuk[1].petunjuk.toString())

            Log.e("BUKTI", listBukti.size.toString())
//            Log.e("BUKTI", listBukti[0].bukti.toString())
//            Log.e("BUKTI", listBukti[1].bukti.toString())

            Log.e("TERLAPOR", listTerlapor.size.toString())
//            Log.e("TERLAPOR", listTerlapor[0].nama_terlapor.toString())
//            Log.e("TERLAPOR", listTerlapor[1].uraian_terlapor.toString())

            Log.e("ANALISA", listAnalisa.size.toString())
//            Log.e("ANALISA", listAnalisa[0].analisa.toString())
//            Log.e("ANALISA", listAnalisa[1].analisa.toString())

            Log.e("NO_LHP", "LHP ${edt_no_lhp.text.toString()}")
            lhp.no_lhp = edt_no_lhp.text.toString()
            lhp.listLidik = listLidik
            lhp.listSaksi = listSaksi
            lhp.listSurat = listSurat
            lhp.listAnalisa = listAnalisa
            lhp.listBukti = listBukti
            lhp.listPetunjuk = listPetunjuk
            lhp.listTerlapor = listTerlapor
            lhp.no_sp = edt_no_sp_lhp.text.toString()
            lhp.isi_pengaduan = edt_isi_pengaduan_lhp.text.toString()
            lhp.waktu_penugasan = edt_waktu_penugasan_lhp.text.toString()
            lhp.daerah_penyelidikan = edt_tempat_penyelidikan_lhp.text.toString()
            lhp.tugas_pokok = edt_tugas_pokok_lhp.text.toString()
            lhp.rencana_pelaksanaan_penyelidikan = edt_rencana_pelaksanaan_lhp.text.toString()
            lhp.pelaksanan = edt_pelaksanaan_lhp.text.toString()
            lhp.pokok_permasalahan = edt_pokok_permasalahan_lhp.text.toString()
            lhp.keterangan_ahli = edt_keterangan_ahli_lhp.text.toString()
            lhp.kesimpulan = edt_kesimpulan_lhp.text.toString()
            lhp.rekomendasi = edt_rekomendasi_lhp.text.toString()
            lhp.nama_ketua_tim = edt_nama_ketua_tim_lhp.text.toString()
            lhp.pangkat_ketua_tim = edt_pangkat_ketua_tim_lhp.text.toString()
            lhp.nrp_ketua_tim = edt_nrp_ketua_tim_lhp.text.toString()
            lhp.isTerbukti = isTerbukti

            Log.e("LHP", "${lhp}")

        }
    }

    private fun initRV() {
        //lidik
        listLidik.add(ListLidik())
        rv_lidik.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterLidik = LidikAdapter(this, listLidik, object : LidikAdapter.OnClickLidik {
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
        adapterSaksi = SaksiAdapter(this, listSaksi, object: SaksiAdapter.OnClickSaksi{
            override fun onAdd() {
                listSaksi.add(ListSaksi())
                val position = if(listSaksi.isEmpty())0 else listSaksi.size -1
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
        adapterSurat = SuratAdapter(this, listSurat, object: SuratAdapter.OnClickSurat{
            override fun onAdd() {
                listSurat.add(ListSurat())
                val position = if(listSurat.isEmpty())0 else listSurat.size -1
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
        adapterPetunjuk = PetunjukAdapter(this, listPetunjuk, object: PetunjukAdapter.OnClickPetunjuk{
            override fun onAdd() {
                listPetunjuk.add(ListPetunjuk())
                val position = if(listPetunjuk.isEmpty())0 else listPetunjuk.size -1
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
        adapterBukti = BuktiAdapter(this, listBukti, object: BuktiAdapter.OnClickBukti{
            override fun onAdd() {
                listBukti.add(ListBukti())
                val position = if(listBukti.isEmpty())0 else listBukti.size -1
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
        adapterTerlapor = TerlaporAdapter(this,listTerlapor, object: TerlaporAdapter.OnClickTerlapor{
            override fun onAdd() {
                listTerlapor.add(ListTerlapor())
                val position = if(listTerlapor.isEmpty())0 else listTerlapor.size -1
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
        adapterAnalisa = AnalisaAdapter(this,listAnalisa, object: AnalisaAdapter.OnClickAnalisa{
            override fun onAdd() {
                listAnalisa.add(ListAnalisa())
                val position = if(listAnalisa.isEmpty())0 else listAnalisa.size -1
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

}