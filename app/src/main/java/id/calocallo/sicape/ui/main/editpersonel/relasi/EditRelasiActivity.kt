package id.calocallo.sicape.ui.main.editpersonel.relasi

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.response.RelasiResp
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.RelasiReq
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_relasi.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditRelasiActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var relasiReq = RelasiReq()
    private var jenisRelasi: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_relasi)

        sessionManager = SessionManager(this)
        val personel = intent.extras?.getParcelable<PersonelModel>("PERSONEL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = personel?.nama

        val hak = sessionManager.fetchHakAkses()
        if (hak == "operator") {
            btn_save_edit_relasi.gone()
            btn_delete_edit_relasi.gone()
        }
        when (intent.extras?.getString("JENIS_RELASI")) {
            "dalam_negeri" -> spinner_jenis_relasi_edit.setText("Dalam Negeri")
            "luar_negeri" -> spinner_jenis_relasi_edit.setText("Luar Negeri")
        }
        val relasi = intent.extras?.getParcelable<RelasiResp>("RELASI")
        edt_nama_relasi_edit.setText(relasi?.nama)
        spRelasi()

        btn_save_edit_relasi.attachTextChangeAnimator()
        bindProgressButton(btn_save_edit_relasi)
        btn_save_edit_relasi.setOnClickListener {
            updateRelasi(relasi)
        }

        btn_delete_edit_relasi.attachTextChangeAnimator()
        bindProgressButton(btn_delete_edit_relasi)
        btn_delete_edit_relasi.setOnClickListener {
            alert("Yakin Hapus Data?") {
                positiveButton("Iya") {
                    deleteRelasi(relasi)
                }
                negativeButton("TIdak") {}
            }.show()
        }
    }

    private fun updateRelasi(relasi: RelasiResp?) {
        btn_save_edit_relasi.showProgress {
            progressColor = Color.WHITE
        }

        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        relasiReq.nama = edt_nama_relasi_edit.text.toString()
        NetworkConfig().getService().updateSingleRelasi(
            "Bearer ${sessionManager.fetchAuthToken()}",
            relasi?.id.toString(),
            relasiReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@EditRelasiActivity, R.string.error_conn, Toast.LENGTH_SHORT)
                    .show()
                btn_save_edit_relasi.hideDrawable(R.string.save)
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    btn_save_edit_relasi.showDrawable(animatedDrawable) {
                        buttonTextRes = R.string.data_updated
                        textMarginRes = R.dimen.space_10dp
                    }
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 500)
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_edit_relasi.hideDrawable(R.string.save)
                    }, 3000)
                    btn_save_edit_relasi.hideDrawable(R.string.not_update)

                }
            }
        })
    }

    private fun deleteRelasi(relasi: RelasiResp?) {
        NetworkConfig().getService().deleteSingleRelasi(
            "Bearer ${sessionManager.fetchAuthToken()}",
            relasi?.id.toString()
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@EditRelasiActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    finish()
                } else {
                    Toast.makeText(this@EditRelasiActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })

    }


    private fun spRelasi() {
        val item = listOf("Dalam Negeri", "Luar Negeri")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        spinner_jenis_relasi_edit.setAdapter(adapter)
        spinner_jenis_relasi_edit.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> {
                    jenisRelasi = "dalam_negeri"

                }
                1 -> {
                    jenisRelasi = "luar_negeri"
                }
            }
        }
    }
}