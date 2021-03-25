package id.calocallo.sicape.ui.main.putkke

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LhpResp
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.PutKkeReq
import id.calocallo.sicape.network.response.AddPutKkeResp
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.LpKkeResp
import id.calocallo.sicape.network.response.PutKkeResp
import id.calocallo.sicape.ui.main.choose.lhp.ChooseLhpActivity
import id.calocallo.sicape.ui.main.choose.lp.ChooseLpSkhdActivity
import id.calocallo.sicape.ui.main.choose.lp.ListLpKkePutKkeActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.utils.ext.toast
import kotlinx.android.synthetic.main.activity_add_put_kke.*
import kotlinx.android.synthetic.main.activity_edit_put_kke.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditPutKkeActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var idLhp: Int? = null
    private var idLp: Int? = null
    private var putKkeReq = PutKkeReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_put_kke)
        sessionManager1 = SessionManager1(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data Putusan Kode Etik"

        val detailPutKke =
            intent.extras?.getParcelable<PutKkeResp>(DetailPutKkeActivity.EDIT_PUT_KKE)

        getDataPutKke(detailPutKke)
        btn_pick_lhp_put_kke_edit.setOnClickListener {
            val intent = Intent(this, ChooseLhpActivity::class.java)
            startActivityForResult(intent, AddPutKkeActivity.REQ_CHOOSE_LHP)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        btn_pick_lp_put_kke_edit.setOnClickListener {
            val intent = Intent(this, ChooseLpSkhdActivity::class.java)
            intent.putExtra(AddPutKkeActivity.IDLHP_PUTKKE, idLhp)
            startActivityForResult(intent, AddPutKkeActivity.REQ_CHOOSE_LP)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_save_put_kke_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_put_kke_edit)
        btn_save_put_kke_edit.setOnClickListener {
            updatePutKke(detailPutKke)
        }
    }

    private fun getDataPutKke(detailPutKke: PutKkeResp?) {
        edt_no_put_kke_edit.setText(detailPutKke?.no_putkke)
        txt_lp_put_kke_edit.text = detailPutKke?.lp?.no_lp
        edt_no_berkas_pemeriksaan_put_kke_edit.setText(detailPutKke?.no_berkas_pemeriksaan_pendahuluan)
        edt_tgl_berkas_pemeriksaan_put_kke_edit.setText(detailPutKke?.tanggal_berkas_pemeriksaan_pendahuluan)
        edt_no_keputusan_kapolda_put_kke_edit.setText(detailPutKke?.no_keputusan_kapolda_kalsel)
        edt_tgl_keputusan_kapolda_put_kke_edit.setText(detailPutKke?.tanggal_keputusan_kapolda_kalsel)
        edt_no_surat_persangkaan_put_kke_edit.setText(detailPutKke?.no_surat_persangkaan_pelanggaran_kode_etik)
        edt_tgl_surat_persangkaan_put_kke_edit.setText(detailPutKke?.tanggal_surat_persangkaan_pelanggaran_kode_etik)
        edt_no_tuntunan_pelanggaran_put_kke_edit.setText(detailPutKke?.no_tuntutan_pelanggaran_kode_etik)
        edt_tgl_tuntunan_pelanggaran_put_kke_edit.setText(detailPutKke?.tanggal_tuntutan_pelanggaran_kode_etik)
        edt_sanksi_hasil_putusan_put_kke_edit.setText(detailPutKke?.sanksi_hasil_keputusan)
        edt_tanggal_penetapan_put_kke_edit.setText(detailPutKke?.tanggal_putusan)
        edt_lokasi_sidang_put_kke_edit.setText(detailPutKke?.lokasi_sidang)


        edt_nama_ketua_komisi_put_kke_edit.setText(detailPutKke?.nama_ketua_komisi)
        edt_pangkat_ketua_komisi_put_kke_edit.setText(
            detailPutKke?.pangkat_ketua_komisi.toString().toUpperCase()
        )
        edt_nrp_ketua_komisi_put_kke_edit.setText(detailPutKke?.nrp_ketua_komisi)
        edt_jabatan_ketua_komisi_put_kke_edit.setText(detailPutKke?.jabatan_ketua_komisi)
        edt_kesatuan_ketua_komisi_put_kke_edit.setText(detailPutKke?.kesatuan_ketua_komisi)

        edt_nama_wakil_ketua_komisi_put_kke_edit.setText(detailPutKke?.nama_wakil_ketua_komisi)
        edt_pangkat_wakil_ketua_komisi_put_kke_edit.setText(
            detailPutKke?.pangkat_wakil_ketua_komisi.toString().toUpperCase()
        )
        edt_nrp_wakil_ketua_komisi_put_kke_edit.setText(detailPutKke?.nrp_wakil_ketua_komisi)
        edt_jabatan_wakil_ketua_komisi_put_kke_edit.setText(detailPutKke?.jabatan_wakil_ketua_komisi)
        edt_kesatuan_wakil_ketua_komisi_put_kke_edit.setText(detailPutKke?.kesatuan_wakil_ketua_komisi)

        edt_nama_anggota_komisi_put_kke_edit.setText(detailPutKke?.nama_anggota_komisi)
        edt_pangkat_anggota_komisi_put_kke_edit.setText(
            detailPutKke?.pangkat_anggota_komisi.toString().toUpperCase()
        )
        edt_nrp_anggota_komisi_put_kke_edit.setText(detailPutKke?.nrp_anggota_komisi)
        edt_jabatan_anggota_komisi_put_kke_edit.setText(detailPutKke?.jabatan_anggota_komisi)
        edt_kesatuan_anggota_komisi_put_kke_edit.setText(detailPutKke?.kesatuan_anggota_komisi)
    }

    private fun updatePutKke(detailPutKke: PutKkeResp?) {
        /* putKkeReq.no_putkke = edt_no_put_kke_edit.text.toString()
         putKkeReq.pembukaan_putusan = edt_pembukan_putusan_edit.text.toString()
         putKkeReq.menimbang_p2 = edt_berkas_pemeriksaan_put_kke_edit.text.toString()
         putKkeReq.mengingat_p4 = edt_keputusan_kapolda_put_kke_edit.text.toString()
         putKkeReq.memperhatikan_p1 = edt_memperhatikan_p1_put_kke_edit.text.toString()
         putKkeReq.memperhatikan_p3 = edt_memperhatikan_p3_put_kke_edit.text.toString()
         putKkeReq.memperhatikan_p5 = edt_memperhatikan_p5_put_kke_edit.text.toString()
         putKkeReq.memutuskan_p1 = edt_memutuskan_p1_put_kke_edit.text.toString()
         putKkeReq.sanksi_rekomendasi = edt_sanksi_rekomendasi_put_kke_edit.text.toString()*/
        putKkeReq.no_berkas_pemeriksaan_pendahuluan =
            edt_no_berkas_pemeriksaan_put_kke_edit.text.toString()

        putKkeReq.tanggal_berkas_pemeriksaan_pendahuluan =
            edt_tanggal_penetapan_put_kke_edit.text.toString()

        putKkeReq.no_keputusan_kapolda_kalsel =
            edt_no_keputusan_kapolda_put_kke_edit.text.toString()

        putKkeReq.tanggal_keputusan_kapolda_kalsel =
            edt_tgl_keputusan_kapolda_put_kke_edit.text.toString()

        putKkeReq.no_surat_persangkaan_pelanggaran_kode_etik =
            edt_no_surat_persangkaan_put_kke_edit.text.toString()

        putKkeReq.tanggal_surat_persangkaan_pelanggaran_kode_etik =
            edt_tgl_surat_persangkaan_put_kke_edit.text.toString()

        putKkeReq.no_tuntutan_pelanggaran_kode_etik =
            edt_no_tuntunan_pelanggaran_put_kke_edit.text.toString()

        putKkeReq.tanggal_tuntutan_pelanggaran_kode_etik =
            edt_tgl_tuntunan_pelanggaran_put_kke_edit.text.toString()

        putKkeReq.sanksi_hasil_keputusan = edt_sanksi_hasil_putusan_put_kke_edit.text.toString()
        putKkeReq.nama_ketua_komisi = edt_nama_ketua_komisi_put_kke_edit.text.toString()
        putKkeReq.pangkat_ketua_komisi =
            edt_pangkat_ketua_komisi_put_kke_edit.text.toString().toUpperCase()
        putKkeReq.nrp_ketua_komisi = edt_nrp_ketua_komisi_put_kke_edit.text.toString()
        putKkeReq.jabatan_ketua_komisi = edt_jabatan_ketua_komisi_put_kke_edit.text.toString()
        putKkeReq.kesatuan_ketua_komisi = edt_kesatuan_ketua_komisi_put_kke_edit.text.toString()

        putKkeReq.nama_wakil_ketua_komisi = edt_nama_wakil_ketua_komisi_put_kke_edit.text.toString()
        putKkeReq.pangkat_wakil_ketua_komisi =
            edt_pangkat_wakil_ketua_komisi_put_kke_edit.text.toString().toUpperCase()
        putKkeReq.nrp_wakil_ketua_komisi = edt_nrp_wakil_ketua_komisi_put_kke_edit.text.toString()
        putKkeReq.jabatan_wakil_ketua_komisi =
            edt_jabatan_wakil_ketua_komisi_put_kke_edit.text.toString()
        putKkeReq.kesatuan_wakil_ketua_komisi =
            edt_kesatuan_wakil_ketua_komisi_put_kke_edit.text.toString()

        putKkeReq.nama_anggota_komisi = edt_nama_anggota_komisi_put_kke_edit.text.toString()
        putKkeReq.pangkat_anggota_komisi =
            edt_pangkat_anggota_komisi_put_kke_edit.text.toString().toUpperCase()
        putKkeReq.nrp_anggota_komisi = edt_nrp_anggota_komisi_put_kke_edit.text.toString()
        putKkeReq.jabatan_anggota_komisi = edt_jabatan_anggota_komisi_put_kke_edit.text.toString()
        putKkeReq.kesatuan_anggota_komisi = edt_kesatuan_anggota_komisi_put_kke_edit.text.toString()
        /* putKkeReq.id_lhp = idLhp*/
//        putKkeReq.id_lp = idLp
        /*  putKkeReq.kota_putusan = edt_kota_penetapan_put_kke_edit.text.toString()*/
        putKkeReq.tanggal_putusan = edt_tanggal_penetapan_put_kke_edit.text.toString()
        putKkeReq.lokasi_sidang = edt_lokasi_sidang_put_kke_edit.text.toString()
        Log.e("edit PUTKKE", "$putKkeReq")

        btn_save_put_kke_edit.showProgress {
            progressColor = Color.WHITE
        }
        apiUpdPutKke(detailPutKke)
    }

    private fun apiUpdPutKke(putKke: PutKkeResp?) {
        NetworkConfig().getServSkhd()
            .updPutKke("Bearer ${sessionManager1.fetchAuthToken()}", putKke?.id, putKkeReq).enqueue(
                object :
                    Callback<Base1Resp<AddPutKkeResp>> {
                    override fun onResponse(
                        call: Call<Base1Resp<AddPutKkeResp>>,
                        response: Response<Base1Resp<AddPutKkeResp>>
                    ) {
                        if (response.body()?.message == "Data putkke updated succesfully") {
                            btn_save_put_kke_edit.hideProgress(R.string.data_updated)
                            Handler(Looper.getMainLooper()).postDelayed({
                                finish()
                            }, 750)
                        } else {
                            toast("${response.body()?.message}")
                            btn_save_put_kke_edit.hideProgress(R.string.not_update)
                        }
                    }

                    override fun onFailure(call: Call<Base1Resp<AddPutKkeResp>>, t: Throwable) {
                        Toast.makeText(this@EditPutKkeActivity, "$t", Toast.LENGTH_SHORT).show()
                        btn_save_put_kke_edit.hideProgress(R.string.not_update)
                    }
                })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AddPutKkeActivity.REQ_CHOOSE_LHP) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val lhpResp = data?.getParcelableExtra<LhpResp>("CHOOSE_LHP")
                    idLhp = lhpResp?.id
                    txt_lhp_put_kke_edit.text = lhpResp?.no_lhp
                }
                ChooseLpSkhdActivity.RES_LP_PUT_KKE -> {
                    val lpKke =
                        data?.getParcelableExtra<LpKkeResp>(ListLpKkePutKkeActivity.DATA_KKE)
                    idLp = lpKke?.id
                    txt_lp_put_kke_edit.text = lpKke?.no_lp
                }
            }
        }
    }
}