package id.calocallo.sicape.ui.main.lhp

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.model.*
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_lhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditLhpActivity : BaseActivity() {
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
        setContentView(R.layout.activity_add_lhp)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Laporan Hasil Penyelidikan"

        val bundle = intent.extras
        val editLHP = bundle?.getParcelable<LhpModel>(EDIT_LHP)
        edt_no_lhp.setText(editLHP?.no_lhp)
        edt_isi_pengaduan_lhp.setText(editLHP?.isi_pengaduan)
        edt_no_sp_lhp.setText(editLHP?.no_sp)
        edt_no_sp_lhp.setText(editLHP?.no_sp)

        val listLidik = editLHP?.listLidik
        adapterLidik = LidikAdapter(this, listLidik!!, object : LidikAdapter.OnClickLidik{
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
                    listSaksi.removeAt (position)
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

        Log.e("size", "${listLidik}")
    }
}