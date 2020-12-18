package id.calocallo.sicape.ui.main.addpersonal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
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
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.setLocalImage
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_foto.*
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
    private lateinit var sessionManager: SessionManager
    private lateinit var fotoReq: FotoReq

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_foto)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Foto"
        sessionManager = SessionManager(this)
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
            fotoReq.foto_kanan = mKananUrl
            fotoReq.foto_kiri = mKiriUrl
            fotoReq.foto_muka = mDepanUrl
            sessionManager.setFoto(fotoReq)
            Log.e("file", "$mDepanUrl $mKananUrl $mKiriUrl")

            /**
             * mDepanFile, mKananFile, mKiriFile
             */

            startActivity(Intent(this, AddSignalementActivity::class.java))
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
            when (requestCode) {
                DEPAN -> {
                    mDepanFile = file

                    val requestBody = RequestBody.create(MediaType.parse("*/*"), mDepanFile)
                    val filePart =
                        MultipartBody.Part.createFormData("foto", mDepanFile!!.name, requestBody)
                    img_foto_depan.setLocalImage(file, false)
                    NetworkConfig().getService().uploadMuka(
                        "Bearer ${sessionManager.fetchAuthToken()}",
                        filePart
                    ).enqueue(object : Callback<Base1Resp<FotoResp>> {
                        override fun onFailure(call: Call<Base1Resp<FotoResp>>, t: Throwable) {
                            Toast.makeText(this@AddFotoActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(
                            call: Call<Base1Resp<FotoResp>>,
                            response: Response<Base1Resp<FotoResp>>
                        ) {
                            if(response.isSuccessful){
                                mDepanUrl  = response.body()?.data?.url
                            }else{
                                Toast.makeText(this@AddFotoActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()

                            }
                        }
                    })
                    uploadFoto(mDepanFile)
                }
                KANAN -> {
                    mKananFile = file
                    img_foto_kanan.setLocalImage(file, false)
                    uploadFoto(mKananFile)
                    val requestBody = RequestBody.create(MediaType.parse("*/*"), mKananFile)
                    val filePart =
                        MultipartBody.Part.createFormData("foto", mKananFile!!.name, requestBody)
                    NetworkConfig().getService().uploadKanan(
                        "Bearer ${sessionManager.fetchAuthToken()}",
                        filePart
                    ).enqueue(object : Callback<Base1Resp<FotoResp>>{
                        override fun onFailure(call: Call<Base1Resp<FotoResp>>, t: Throwable) {
                            Toast.makeText(this@AddFotoActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(
                            call: Call<Base1Resp<FotoResp>>,
                            response: Response<Base1Resp<FotoResp>>
                        ) {
                            if(response.isSuccessful){
                                mKananUrl  = response.body()?.data?.url
                            }else{
                                Toast.makeText(this@AddFotoActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })

                }
                KIRI -> {
                    mKiriFile = file
                    img_foto_kiri.setLocalImage(file, false)
                    uploadFoto(mKiriFile)
                    val requestBody = RequestBody.create(MediaType.parse("*/*"), mKiriFile)
                    val filePart =
                        MultipartBody.Part.createFormData("foto", mKiriFile!!.name, requestBody)
                    NetworkConfig().getService().uploadKiri(
                        "Bearer ${sessionManager.fetchAuthToken()}",
                        filePart
                    ).enqueue(object : Callback<Base1Resp<FotoResp>>{
                        override fun onFailure(call: Call<Base1Resp<FotoResp>>, t: Throwable) {
                            Toast.makeText(this@AddFotoActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(
                            call: Call<Base1Resp<FotoResp>>,
                            response: Response<Base1Resp<FotoResp>>
                        ) {
                            if(response.isSuccessful){
                                mKiriUrl  = response.body()?.data?.url
                            }else{
                                Toast.makeText(this@AddFotoActivity, "Error Koneksi", Toast.LENGTH_SHORT).show()
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

    private fun uploadFoto(file: File?) {

    }
}