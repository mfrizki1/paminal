package id.calocallo.sicape.ui.gelar

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AllPersonelModel
import id.calocallo.sicape.model.Gelar1Model
import id.calocallo.sicape.network.response.PersonelMinResp
import id.calocallo.sicape.ui.main.choose.ChoosePersonelActivity
import id.calocallo.sicape.ui.main.lp.AddLpActivity
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.utils.GelarDataManager
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_gelar.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import java.text.SimpleDateFormat
import java.util.*

class AddGelarActivity : BaseActivity() {
    private lateinit var gelarDataManager: GelarDataManager
    private var gelar1Model = Gelar1Model()
    private var mHari = ""
    private var idPimpinan: Int? = null
    private var idPeserta = arrayListOf<Int>()
    private var idPemapar: Int? = null

    companion object {
        const val REQ_PESERTA = 1
        const val RES_PESERTA = 4
        const val REQ_PEMAPAR = 2
        const val REQ_PIMPINAN = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_gelar)
        gelarDataManager = GelarDataManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Gelar Perkara"
        Logger.addLogAdapter(AndroidLogAdapter())

        btn_tanggal_gelar_add.setOnClickListener {
            datePick()

        }

        btn_peserta_gelar_add.setOnClickListener {
            val intent = Intent(this, ChoosePersonelActivity::class.java)
            intent.putExtra(ChoosePersonelActivity.MULTIPLE, true)
            startActivityForResult(intent, REQ_PESERTA)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        btn_pemapar_gelar_add.setOnClickListener {
            val intent = Intent(this, KatPersonelActivity::class.java)
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, REQ_PEMAPAR)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_pimpinan_gelar_add.setOnClickListener {
            val intent = Intent(this, KatPersonelActivity::class.java)
            intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
            startActivityForResult(intent, REQ_PIMPINAN)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        btn_next_gelar_add.setOnClickListener {
            saveGelar()
            val intent = Intent(this, AddGelar2Activity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }


    }

    private fun saveGelar() {
    /*    Logger.e(
            "${edt_tentang_gelar_add.editText?.text.toString()},\n " +
                    "${edt_dasar_gelar_add.editText?.text.toString()},\n" +
                    "${edt_tanggal_gelar_add.editText?.text.toString()},\n" +
                    "${edt_pukul_gelar_add.editText?.text.toString()},\n" +
                    "${edt_tempat_gelar_add.editText?.text.toString()},\n" +
                    "${edt_uraian_gelar_add.editText?.text.toString()},\n" +
                    "$mHari \n" +
                    "$idPemapar \n" +
                    "$idPeserta \n" +
                    "$idPimpinan"
        )*/
        gelar1Model.tentang = edt_tentang_gelar_add.editText?.text.toString()
        gelar1Model.dasar_dasar = edt_dasar_gelar_add.editText?.text.toString()
        gelar1Model.tanggal = edt_tanggal_gelar_add.editText?.text.toString()
        gelar1Model.pukul = edt_pukul_gelar_add.editText?.text.toString()
        gelar1Model.tempat = edt_tempat_gelar_add.editText?.text.toString()
        gelar1Model.uraian = edt_uraian_gelar_add.editText?.text.toString()
        gelar1Model.hari = mHari
        gelar1Model.id_pemapar = idPemapar
        gelar1Model.id_peserta = idPeserta
        gelar1Model.id_pimpinan = idPimpinan
        gelarDataManager.setGelar1(gelar1Model)
        Logger.e("${gelarDataManager.getGelar1()}")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_PESERTA -> {
                if (resultCode == RES_PESERTA) {
                    txt_peserta_add.text = ""
                    txt_peserta_add.textSize = 16f
                    val multiplePersonel =
                        data?.getParcelableArrayListExtra<PersonelMinResp>(ChoosePersonelActivity.DATA_MULTIPLE)
                    for (i in 0 until multiplePersonel?.size!!) {
                        txt_peserta_add.append("${multiplePersonel[i].nama},")
                        Log.e("nama", "${multiplePersonel[i].nama}")
                        multiplePersonel[i].id?.let { idPeserta.add(it) }
                    }
//                txt_peserta_add.text = multiplePersonel
                }
            }
            REQ_PIMPINAN -> {
                if (resultCode == Activity.RESULT_OK) {
                    txt_pimpinan_gelar_add.textSize = 16f
                    val pimpinanPersonel = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
                    txt_pimpinan_gelar_add.text = pimpinanPersonel?.nama
                    idPimpinan = pimpinanPersonel?.id
                }
            }
            REQ_PEMAPAR -> {
                if (resultCode == Activity.RESULT_OK) {
                    txt_pemapar_add.textSize = 16f
                    val pemaparPersonel = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
                    txt_pemapar_add.text = pemaparPersonel?.nama
                    idPemapar = pemaparPersonel?.id
                }
            }
        }
    }

    private fun datePick() {
        val c = Calendar.getInstance(Locale("id", "ID"))
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, mYear, monthOfYear, dayOfMonth ->

                val simpleDateFormat = SimpleDateFormat("EEEE", Locale("id", "ID"))
                val date = Date(mYear, month, dayOfMonth - 1)
                val dayString =
                    simpleDateFormat.format(date) //returns true day name for current month only
                mHari = dayString
                edt_tanggal_gelar_add.editText?.setText("$dayString, $dayOfMonth/${monthOfYear + 1}/$mYear")

            }, year, month, day
        )
        dpd.show()
    }
}