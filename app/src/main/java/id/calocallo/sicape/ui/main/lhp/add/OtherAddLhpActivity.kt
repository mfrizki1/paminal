package id.calocallo.sicape.ui.main.lhp.add

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.LhpReq
import id.calocallo.sicape.utils.LhpDataManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_other_add_lhp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class OtherAddLhpActivity : BaseActivity() {
    private lateinit var lhpDataManager: LhpDataManager
    private var lhpReq = LhpReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_add_lhp)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Laporan Hasil Penyelidikan"
        lhpDataManager = LhpDataManager(this)

        btn_add_all_lhp.attachTextChangeAnimator()
        bindProgressButton(btn_add_all_lhp)
        btn_add_all_lhp.setOnClickListener {
            addAllLHP()

        }

    }

    private fun addAllLHP() {
        val animated = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        val size = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animated.setBounds(0,0,size,size)
        btn_add_all_lhp.showProgress {
            progressColor = Color.WHITE
        }
        btn_add_all_lhp.showDrawable(animated){
            textMarginRes = R.dimen.space_10dp
            buttonTextRes = R.string.data_saved
        }
        btn_add_all_lhp.hideDrawable(R.string.save)
        lhpReq.no_lhp = lhpDataManager.getNoLHP()
        lhpReq.tentang = lhpDataManager.getTentangLHP()
        lhpReq.no_surat_perintah_penyelidikan = lhpDataManager.getSPLHP()
        lhpReq.tanggal_mulai_penyelidikan = lhpDataManager.getWaktuLHP()
        lhpReq.wilayah_hukum_penyelidikan = lhpDataManager.getDaerahLHP()
        lhpReq.pokok_permasalahan = lhpDataManager.getPokokPermasalahanLHP()
        lhpReq.keterangan_ahli = lhpDataManager.getKetAhliLHP()
        lhpReq.kesimpulan = lhpDataManager.getKesimpulanLHP()
        lhpReq.rekomendasi = lhpDataManager.getRekomendasiLHP()
        lhpReq.tugas_pokok = lhpDataManager.getTugasPokokLHP()
        lhpReq.isTerbukti = lhpDataManager.getIsTerbukti()
        lhpReq.referensi_penyelidikan = lhpDataManager.getListRefLp()
        lhpReq.personel_penyelidik = lhpDataManager.getListLidikLHP()
        lhpReq.saksi = lhpDataManager.getListSaksiLHP()
        lhpReq.keterangan_terlapor = lhpDataManager.getListTerlaporLHP()
        lhpReq.barang_bukti = edt_barang_bukti_lhp_add.text.toString()
        lhpReq.analisa = edt_analisa_lhp_add.text.toString()
        lhpReq.surat = edt_surat_lhp_add.text.toString()
        lhpReq.petunjuk = edt_petunjuk_lhp_add.text.toString()
        lhpReq.kota_buat_laporan = edt_kota_buat_add_lhp.text.toString()
        lhpReq.tanggal_buat_laporan = edt_tgl_buat_add_lhp.text.toString()
        Log.e("add all LHP", "$lhpReq")

        Handler(Looper.getMainLooper()).postDelayed({
            lhpDataManager.clearLHP()
            Log.e("clear", "clearLHP")
        },2000)
    }
}