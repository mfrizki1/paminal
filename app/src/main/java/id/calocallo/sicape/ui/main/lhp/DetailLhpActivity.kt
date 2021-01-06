package id.calocallo.sicape.ui.main.lhp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.model.*
import id.calocallo.sicape.network.response.KetTerlaporLhpResp
import id.calocallo.sicape.network.response.PersonelPenyelidikResp
import id.calocallo.sicape.network.response.RefPenyelidikanResp
import id.calocallo.sicape.network.response.SaksiLhpResp
import id.calocallo.sicape.ui.main.lhp.add.ReferensiPenyelidikanLhpActivity
import id.calocallo.sicape.ui.main.lhp.edit.ListDetailRefPenyelidikanActivity
import id.calocallo.sicape.ui.main.lhp.edit.lidik.PickLidikLhpActivity
import id.calocallo.sicape.ui.main.lhp.edit.saksi.PickEditSaksiLhpActivity
import id.calocallo.sicape.ui.main.lhp.edit.terlapor.PickTerlaporLhpActivity
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.toggleVisibility
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_lhp.*
import kotlinx.android.synthetic.main.item_2_text.view.*
import kotlinx.android.synthetic.main.item_pasal_lp.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import java.util.ArrayList

class DetailLhpActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager

    private var adapterRefLP = ReusableAdapter<RefPenyelidikanResp>(this)
    private lateinit var callbackRefLP: AdapterCallback<RefPenyelidikanResp>

    private var adapterLidik = ReusableAdapter<PersonelPenyelidikResp>(this)
    private lateinit var callbackLidk: AdapterCallback<PersonelPenyelidikResp>

    private var adapterSaksi = ReusableAdapter<SaksiLhpResp>(this)
    private lateinit var callbackSaksi: AdapterCallback<SaksiLhpResp>
    private var adapterketTerlapor = ReusableAdapter<KetTerlaporLhpResp>(this)
    private lateinit var callbackketTerlapor: AdapterCallback<KetTerlaporLhpResp>
    var terbukti = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lhp)
        sessionManager = SessionManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Laporan Hasil Penyelidikan"

        val dataLhp = intent.extras?.getParcelable<LhpResp>(DETAIL_LHP)
        getDetailLHP(dataLhp)


        val hakAkses = sessionManager.fetchHakAkses()
        if (hakAkses == "operator") {
            btn_edit_lhp.toggleVisibility()
            btn_edit_lidik_lhp.toggleVisibility()
            btn_edit_saksi_lhp.toggleVisibility()
            btn_edit_ket_terlapor_lhp.toggleVisibility()
        }
        btn_edit_lhp.setOnClickListener {
            val intent = Intent(this, EditLhpActivity::class.java)
            intent.putExtra(EditLhpActivity.EDIT_LHP, dataLhp)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        btn_edit_lidik_lhp.setOnClickListener {
            val intent = Intent(this, PickLidikLhpActivity::class.java)
            intent.putExtra(EditLhpActivity.EDIT_LHP, dataLhp)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_edit_saksi_lhp.setOnClickListener {
            val intent = Intent(this, PickEditSaksiLhpActivity::class.java)
            intent.putExtra(EditLhpActivity.EDIT_LHP, dataLhp)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        btn_edit_ket_terlapor_lhp.setOnClickListener {
            val intent = Intent(this, PickTerlaporLhpActivity::class.java)
            intent.putExtra(EditLhpActivity.EDIT_LHP, dataLhp)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        btn_edit_ref_lp_lhp.setOnClickListener {
            val intent = Intent(this, ListDetailRefPenyelidikanActivity::class.java)
            intent.putExtra(DETAIL_REF, dataLhp)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

    }

    private fun getDetailLHP(dataLhp: LhpResp?) {
        txt_no_lhp_detail.text = dataLhp?.no_lhp
        txt_kesimpulan_detail.text = dataLhp?.kesimpulan
        txt_rekomendasi_detail.text = dataLhp?.rekomendasi
//        txt_pelaksanaan_detail.text = dataLhp?.pelaksanan
//        txt_rencana_penyelidikan_detail.text = dataLhp?.rencana_pelaksanaan_penyelidikan
        txt_pengaduan_detail.text = dataLhp?.tentang
        txt_no_sp_detail.text = dataLhp?.no_surat_perintah_penyelidikan
        txt_waktu_penugasan_detail.text = dataLhp?.tanggal_mulai_penyelidikan
        txt_tempat_penyelidikan_detail.text = dataLhp?.daerah_penyelidikan
        txt_tugas_pokok_detail.text = dataLhp?.tugas_pokok
        txt_pokok_permasalahan_detail.text = dataLhp?.pokok_permasalahan
        txt_ket_ahli_detail.text = dataLhp?.keterangan_ahli
        when (dataLhp?.isTerbukti) {
            0 -> txt_isTerbukti_detail.text = "Tidak Terbukti"
            1 -> txt_isTerbukti_detail.text = "Terbukti"
        }
        val lidik = dataLhp?.personel_penyelidik?.find { it.is_ketua == 1 }
        txt_ketua_tim_detail.text =
            "Nama : ${lidik?.nama}\nPangkat : ${lidik?.pangkat} \t ${lidik?.nrp}"
        txt_surat_detail_lhp.text = dataLhp?.surat
        txt_petunjuk_detail_lhp.text = dataLhp?.petunjuk
        txt_analisa_detail_lhp.text = dataLhp?.analisa
        txt_barbukti_detail_lhp.text = dataLhp?.barang_bukti
        txt_kota_buat_lhp.text = "Kota : ${dataLhp?.kota_buat_laporan}"
        txt_tanggal_buat_lhp.text = "Tanggal : ${dataLhp?.tanggal_buat_laporan}"
        listOfRefLP(dataLhp?.referensi_penyelidikan)
        listOfLidik(dataLhp?.personel_penyelidik)
        listOfSaksi(dataLhp?.saksi)
        listOfKetTerlapor(dataLhp?.keterangan_terlapor)

    }

    private fun listOfKetTerlapor(listTerlapor: ArrayList<KetTerlaporLhpResp>?) {
        callbackketTerlapor = object : AdapterCallback<KetTerlaporLhpResp> {
            override fun initComponent(itemView: View, data: KetTerlaporLhpResp, itemIndex: Int) {
                itemView.txt_item_1.text = data.nama
            }

            override fun onItemClicked(itemView: View, data: KetTerlaporLhpResp, itemIndex: Int) {
            }
        }
        listTerlapor?.let {
            adapterketTerlapor.adapterCallback(callbackketTerlapor)
                .isVerticalView()
                .addData(it)
                .build(rv_ket_terlapor_detail)
                .setLayout(R.layout.item_pasal_lp)
        }
    }

    private fun listOfSaksi(saksi: ArrayList<SaksiLhpResp>?) {
        callbackSaksi = object : AdapterCallback<SaksiLhpResp> {
            override fun initComponent(itemView: View, data: SaksiLhpResp, itemIndex: Int) {
                itemView.txt_detail_1.textSize = 14F
                itemView.txt_detail_2.textSize = 12F
                itemView.txt_detail_1.text = data.nama
                if (data.status_saksi == "sipil") {
                    itemView.txt_detail_2.text = "Sipil"
                } else {
                    itemView.txt_detail_2.text = "Polisi"
                }
            }

            override fun onItemClicked(itemView: View, data: SaksiLhpResp, itemIndex: Int) {
            }
        }
        saksi?.let {
            adapterSaksi.adapterCallback(callbackSaksi)
                .isVerticalView()
                .addData(it)
                .setLayout(R.layout.item_2_text)
                .build(rv_saksi_detail)
        }
    }

    private fun listOfLidik(listLidik: ArrayList<PersonelPenyelidikResp>?) {
        callbackLidk = object : AdapterCallback<PersonelPenyelidikResp> {
            override fun initComponent(
                itemView: View,
                data: PersonelPenyelidikResp,
                itemIndex: Int
            ) {
                itemView.txt_detail_1.textSize = 14F
                itemView.txt_detail_2.textSize = 12F
                itemView.txt_detail_1.text = data.nama
                if (data.is_ketua == 1) {
                    itemView.txt_detail_2.text = "Ketua Tim"
                } else {
                    itemView.txt_detail_2.text = "Anggota"
                }
            }

            override fun onItemClicked(
                itemView: View,
                data: PersonelPenyelidikResp,
                itemIndex: Int
            ) {
            }
        }
        listLidik?.let {
            adapterLidik.adapterCallback(callbackLidk)
                .isVerticalView()
                .addData(it)
                .setLayout(R.layout.item_2_text)
                .build(rv_detail_lidik)
        }
    }

    private fun listOfRefLP(referensiPenyelidikan: ArrayList<RefPenyelidikanResp>?) {
        callbackRefLP = object : AdapterCallback<RefPenyelidikanResp> {
            override fun initComponent(itemView: View, data: RefPenyelidikanResp, itemIndex: Int) {
                itemView.txt_item_1.text = data.no_lp
            }

            override fun onItemClicked(itemView: View, data: RefPenyelidikanResp, itemIndex: Int) {
            }
        }
        referensiPenyelidikan?.let {
            adapterRefLP.adapterCallback(callbackRefLP)
                .isVerticalView()
                .addData(it)
                .setLayout(R.layout.item_pasal_lp)
                .build(rv_detail_ref)
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
//                ApiDelete()
                finish()
            }
            negativeButton("Tidak") {
            }
        }.show()
    }

    companion object {
        const val DETAIL_LHP = "DETAIL_LHP"
        const val DETAIL_REF = "DETAIL_REF"
    }
}