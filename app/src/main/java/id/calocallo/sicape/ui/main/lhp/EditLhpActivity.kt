package id.calocallo.sicape.ui.main.lhp

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.*
import id.calocallo.sicape.network.request.EditLhpReq
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_lhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditLhpActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private lateinit var adapterLidik: LidikAdapter
    private lateinit var adapterSaksi: SaksiAdapter
    private lateinit var adapterSurat: SuratAdapter
    private lateinit var adapterPetunjuk: PetunjukAdapter
    private lateinit var adapterBukti: BuktiAdapter
    private lateinit var adapterTerlapor: TerlaporAdapter
    private lateinit var adapterAnalisa: AnalisaAdapter
    private var editLhpReq = EditLhpReq()

    companion object {
        const val EDIT_LHP = "EDIT_LHP"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_lhp)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Laporan Hasil Penyelidikan"
        sessionManager1 = SessionManager1(this)

        val bundle = intent.extras
        val editLHP = bundle?.getParcelable<LhpResp>(EDIT_LHP)

        getViewLhpEdit(editLHP)
        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_save_lhp_edit.gone()
        }

        bindProgressButton(btn_save_lhp_edit)
        btn_save_lhp_edit.attachTextChangeAnimator()
        btn_save_lhp_edit.setOnClickListener {
            updateLhp(editLHP)
        }

    }

    private fun updateLhp(editLHP: LhpResp?) {
        val animated = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animated.setBounds(0, 0, size, size)

        btn_save_lhp_edit.showProgress {
            progressColor = Color.WHITE
        }
        btn_save_lhp_edit.showDrawable(animated) {
            textMarginRes = R.dimen.space_10dp
            buttonTextRes = R.string.data_updated
        }
        btn_save_lhp_edit.hideDrawable(R.string.save)
        editLhpReq.no_lhp = edt_no_lhp_edit.text.toString()
        editLhpReq.tentang = edt_isi_pengaduan_lhp_edit.text.toString()
        editLhpReq.no_surat_perintah_penyelidikan = edt_no_sp_lhp_edit.text.toString()
        editLhpReq.tanggal_mulai_penyelidikan = edt_waktu_penugasan_lhp_edit.text.toString()
//        editLhpReq.daerah_penyelidikan = edt_tempat_penyelidikan_lhp_edit.text.toString()
        editLhpReq.tugas_pokok = edt_tugas_pokok_lhp_edit.text.toString()
        editLhpReq.pokok_permasalahan = edt_pokok_permasalahan_lhp_edit.text.toString()
        editLhpReq.keterangan_ahli = edt_keterangan_ahli_lhp_edit.text.toString()
        editLhpReq.kesimpulan = edt_kesimpulan_lhp_edit.text.toString()
        editLhpReq.rekomendasi = edt_rekomendasi_lhp_edit.text.toString()
        editLhpReq.kota_buat_laporan = edt_kota_buat_edit_lhp.text.toString()
        editLhpReq.tanggal_buat_laporan = edt_tgl_buat_edit_lhp.text.toString()
        editLhpReq.surat = edt_surat_lhp_edit.text.toString()
        editLhpReq.petunjuk = edt_petunjuk_lhp_edit.text.toString()
        editLhpReq.barang_bukti = edt_barbukti_lhp_edit.text.toString()
        editLhpReq.analisa = edt_analisa_lhp_edit.text.toString()
        rg_terbukti_edit.setOnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = findViewById(checkedId)
            if (radio.isChecked && radio.text == "Terbukti") {
                editLhpReq.isTerbukti = 1
            } else {
                editLhpReq.isTerbukti = 0
            }
        }
        Log.e("updateLHP", "$editLhpReq")
    }

    private fun getViewLhpEdit(editLHP: LhpResp?) {
        edt_no_lhp_edit.setText(editLHP?.no_lhp)
        edt_isi_pengaduan_lhp_edit.setText(editLHP?.tentang)
        edt_no_sp_lhp_edit.setText(editLHP?.no_surat_perintah_penyelidikan)
        edt_waktu_penugasan_lhp_edit.setText(editLHP?.tanggal_mulai_penyelidikan)
        edt_tempat_penyelidikan_lhp_edit.setText(editLHP?.wilayah_hukum_penyelidikan)
        edt_tugas_pokok_lhp_edit.setText(editLHP?.tugas_pokok)
        edt_pokok_permasalahan_lhp_edit.setText(editLHP?.pokok_permasalahan)
        edt_keterangan_ahli_lhp_edit.setText(editLHP?.keterangan_ahli)
        edt_kesimpulan_lhp_edit.setText(editLHP?.kesimpulan)
        edt_rekomendasi_lhp_edit.setText(editLHP?.rekomendasi)
        edt_kota_buat_edit_lhp.setText(editLHP?.kota_buat_laporan)
        edt_tgl_buat_edit_lhp.setText(editLHP?.tanggal_buat_laporan)
        edt_surat_lhp_edit.setText(editLHP?.surat)
        edt_petunjuk_lhp_edit.setText(editLHP?.petunjuk)
        edt_barbukti_lhp_edit.setText(editLHP?.barang_bukti)
        edt_analisa_lhp_edit.setText(editLHP?.analisa)
        if (editLHP?.isTerbukti == 0) {
            rb_tidak_terbukti_edit.isChecked = true
            editLhpReq.isTerbukti = 0
        } else {
            rb_terbukti_edit.isChecked = true
            editLhpReq.isTerbukti = 1
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