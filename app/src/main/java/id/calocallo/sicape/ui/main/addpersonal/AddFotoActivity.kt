package id.calocallo.sicape.ui.main.addpersonal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.github.dhaval2404.imagepicker.ImagePicker
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.FotoReq
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.FotoResp
import id.calocallo.sicape.utils.IntentUtil
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.setFromUrl
import id.calocallo.sicape.utils.ext.setLocalImage
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_foto.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddFotoActivity : BaseActivity() {
    companion object {
        private const val DEPAN = 101
        private const val KANAN = 102
        private const val KIRI = 103
    }

    private var mDepanFile: File? = null
    private var mDepanUrl: String? = null
    private var mKananFile: File? = null
    private var mKananUrl: String? = null
    private var mKiriFile: File? = null
    private var mKiriUrl: String? = null
    private var idKiri: Int? = null
    private var idKanan: Int? = null
    private var idMuka: Int? = null
    private lateinit var sessionManager1: SessionManager1
    private lateinit var fotoReq: FotoReq

    private var listFoto = ArrayList<FotoResp>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_foto)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Foto"
        sessionManager1 = SessionManager1(this)
        fotoReq = FotoReq()

        btn_add_foto_depan.setOnClickListener {
            picker(DEPAN)
        }

        btn_add_foto_kanan.setOnClickListener {
            picker(KANAN)
        }

        btn_add_foto_kiri.setOnClickListener {
            picker(KIRI)
        }

        btn_next_foto.setOnClickListener {
            //TODO multipart foto
            fotoReq.id_foto_kanan = idKanan
            fotoReq.id_foto_kiri = idKiri
            fotoReq.id_foto_muka = idMuka
            sessionManager1.setIdFoto(fotoReq)
            Log.e("file", "${sessionManager1.getIdFoto()}")

            /**
             * mDepanFile, mKananFile, mKiriFile
             */
            startActivity(Intent(this, AddSignalementActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    fun showImage(view: View) {
        val file = when (view) {
            img_foto_depan -> {
                mDepanFile
            }
            img_foto_kanan -> {
                mKananFile
            }
            img_foto_kiri -> {
                mKiriFile
            }
            else -> null
        }
        file?.let {
            IntentUtil.showImage(this, file)
        }

    }

    fun picker(req: Int) {
        ImagePicker.with(this)
            .crop()
            .compress(3096)
//            .saveDir(Environment.getExternalStorageDirectory())
            .saveDir(this.getExternalFilesDir(null)!!)
            .maxResultSize(512, 512)
            .start(req)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            Log.e("TAG", "Path:${ImagePicker.getFilePath(data)}")
            val file = ImagePicker.getFile(data)!!
            val file2 = ImagePicker.getFile(data)!!
            val file3 = ImagePicker.getFile(data)!!
            when (requestCode) {
                DEPAN -> {
                    mDepanFile = file
                    rl_pb.visible()
                    img_foto_depan.gone()
                    val requestBody = RequestBody.create(MediaType.parse("*/*"), mDepanFile)
                    val filePart =
                        MultipartBody.Part.createFormData("foto", mDepanFile!!.name, requestBody)
//                    img_foto_depan.setLocalImage(file, false)
                    NetworkConfig().getService().uploadMuka(
                        "Bearer ${sessionManager1.fetchAuthToken()}",
                        filePart
                    ).enqueue(object : Callback<Base1Resp<ArrayList<FotoResp>>> {
                        override fun onFailure(
                            call: Call<Base1Resp<ArrayList<FotoResp>>>,
                            t: Throwable
                        ) {
                            Log.e("errro", "$t")
                            Toast.makeText(
                                this@AddFotoActivity,
                                "Error Koneksi",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onResponse(
                            call: Call<Base1Resp<ArrayList<FotoResp>>>,
                            response: Response<Base1Resp<ArrayList<FotoResp>>>
                        ) {
                            if (response.isSuccessful) {
                                rl_pb.gone()
                                img_foto_depan.visible()
                                img_foto_depan.setLocalImage(file, false)
                                mDepanUrl = response.body()?.data?.get(0)?.url
                                idMuka = response.body()?.data?.get(0)?.id
                                sessionManager1.setAllDepanFoto(response.body()!!.data[0])
                                response.body()?.data?.get(0)?.let {
                                    sessionManager1.setAllDepanFoto(it)
                                }
                            } else {
                                Log.e("errro", "erroe")
                                Toast.makeText(
                                    this@AddFotoActivity,
                                    "Error Koneksi",
                                    Toast.LENGTH_SHORT
                                ).show()
                                rl_pb.gone()
                                img_foto_depan.visible()
                            }
                        }
                    })
//                    uploadFoto(mDepanFile)
                }
                KANAN -> {
                    mKananFile = file2
                    pb_foto_kanan.visible()
                    img_foto_kanan.gone()
//                    uploadFoto(mKananFile)
                    val requestBody = RequestBody.create(MediaType.parse("*/*"), mKananFile)
                    val filePart =
                        MultipartBody.Part.createFormData("foto", mKananFile!!.name, requestBody)
                    NetworkConfig().getService().uploadKanan(
                        "Bearer ${sessionManager1.fetchAuthToken()}",
                        filePart
                    ).enqueue(object : Callback<Base1Resp<ArrayList<FotoResp>>> {
                        override fun onFailure(
                            call: Call<Base1Resp<ArrayList<FotoResp>>>,
                            t: Throwable
                        ) {
                            Toast.makeText(
                                this@AddFotoActivity,
                                "Error Koneksi",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onResponse(
                            call: Call<Base1Resp<ArrayList<FotoResp>>>,
                            response: Response<Base1Resp<ArrayList<FotoResp>>>
                        ) {
                            if (response.isSuccessful) {
                                img_foto_kanan.setLocalImage(file2, false)
                                pb_foto_kanan.gone()
                                img_foto_kanan.visible()
                                mKananUrl = response.body()?.data?.get(0)?.url
                                idKanan = response.body()?.data?.get(0)?.id
                                response.body()?.data?.get(0)?.let {
                                    sessionManager1.setAllKananFoto(it)
                                }

                            } else {
                                pb_foto_kanan.gone()
                                img_foto_kanan.visible()
                                Toast.makeText(
                                    this@AddFotoActivity,
                                    "Error Koneksi",
                                    Toast.LENGTH_SHORT

                                ).show()
                            }
                        }
                    })

                }
                KIRI -> {
                    mKiriFile = file3
                    pb_foto_kiri.visible()
                    img_foto_kiri.gone()
//                    uploadFoto(mKiriFile)
                    val requestBody = RequestBody.create(MediaType.parse("*/*"), mKiriFile)
                    val filePart =
                        MultipartBody.Part.createFormData("foto", mKiriFile!!.name, requestBody)
                    NetworkConfig().getService().uploadKiri(
                        "Bearer ${sessionManager1.fetchAuthToken()}",
                        filePart
                    ).enqueue(object : Callback<Base1Resp<ArrayList<FotoResp>>> {
                        override fun onFailure(
                            call: Call<Base1Resp<ArrayList<FotoResp>>>,
                            t: Throwable
                        ) {
                            Toast.makeText(
                                this@AddFotoActivity,
                                "Error Koneksi",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onResponse(
                            call: Call<Base1Resp<ArrayList<FotoResp>>>,
                            response: Response<Base1Resp<ArrayList<FotoResp>>>
                        ) {
                            if (response.isSuccessful) {
                                img_foto_kiri.setLocalImage(file3, false)
                                pb_foto_kiri.gone()
                                img_foto_kiri.visible()
                                mKiriUrl = response.body()?.data?.get(0)?.url
                                idKiri = response.body()?.data?.get(0)?.id
                                response.body()?.data?.get(0)?.let {
                                    sessionManager1.setAllKiriFoto(it)
                                }

                            } else {
                                pb_foto_kiri.gone()
                                img_foto_kiri.visible()
                                Toast.makeText(
                                    this@AddFotoActivity,
                                    "Error Koneksi",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
                }
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

   /* override fun onResume() {
        super.onResume()
        val fotoDepan = sessionManager1.getAllDepanFoto()
        val fotoKanan = sessionManager1.getAllKananFoto()
        val fotoKiri = sessionManager1.getAllKiriFoto()
        Log.e("foto","Foto Depan${fotoDepan}\n" +
                "FotoKanan ${fotoKanan}\n" +
                "FotoKiri ${fotoKiri}")
        idMuka = fotoDepan.id
        idKanan = fotoKanan.id
        idKiri = fotoKiri.id

        img_foto_depan.setFromUrl(fotoDepan.url.toString())
        img_foto_kanan.setFromUrl(fotoKanan.url.toString())
        img_foto_kiri.setFromUrl(fotoKiri.url.toString())
    }*/

}