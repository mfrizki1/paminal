package id.calocallo.sicape.ui.main.editpersonel.relasi

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.RelasiReq
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_single_alamat.*
import kotlinx.android.synthetic.main.activity_add_single_relasi.*
import kotlinx.android.synthetic.main.activity_pick_relasi.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddSingleRelasiActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var jenisRelasi: String? = null
    private var relasiReq = RelasiReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_single_relasi)
        sessionManager = SessionManager(this)
        val personel = intent.extras?.getParcelable<PersonelModel>("PERSONEL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = personel?.nama


        val item = listOf("Dalam Negeri", "Luar Negeri")
        val adapter = ArrayAdapter(this, R.layout.item_spinner, item)
        spinner_jenis_relasi_single.setAdapter(adapter)
        spinner_jenis_relasi_single.setOnItemClickListener { parent, view, position, id ->
            when (position) {
                0 -> {
                    jenisRelasi = "dalam_negeri"

                }
                1 -> {
                    jenisRelasi = "luar_negeri"
                }
            }
        }
        btn_save_single_relasi.attachTextChangeAnimator()
        bindProgressButton(btn_save_single_relasi)
        btn_save_single_relasi.setOnClickListener {
            addRelasi(jenisRelasi)
        }

    }

    private fun addRelasi(jenisRelasi: String?) {
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        btn_save_single_relasi.showProgress {
            progressColor = Color.WHITE
        }
        relasiReq.nama = edt_nama_relasi_single.text.toString()
        NetworkConfig().getService().addSingleRelasi(
            "Bearer ${sessionManager.fetchAuthToken()}",
            sessionManager.fetchID().toString(),
            jenisRelasi.toString(),
            relasiReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(this@AddSingleRelasiActivity, R.string.error_conn, Toast.LENGTH_SHORT).show()
                btn_save_single_relasi.hideDrawable(R.string.save)
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if(response.isSuccessful){
                    btn_save_single_relasi.showDrawable(animatedDrawable) {
                        buttonTextRes = R.string.data_saved
                        textMarginRes = R.dimen.space_10dp
                    }
                    Handler(Looper.getMainLooper()).postDelayed({
                        finish()
                    }, 500)
                }else{
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_single_relasi.hideDrawable(R.string.save)
                    },3000)
                    btn_save_single_relasi.hideDrawable(R.string.not_save)
                }
            }
        })
    }
}