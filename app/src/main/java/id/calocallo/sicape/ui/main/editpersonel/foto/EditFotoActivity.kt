package id.calocallo.sicape.ui.main.editpersonel.foto

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.EditFotoReq
import id.calocallo.sicape.model.AllPersonelModel1
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.IntentUtil
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.*
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_edit_foto.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class EditFotoActivity : BaseActivity() {
    companion object {
        private const val DEPAN = 201
        private const val KANAN = 202
        private const val KIRI = 203
    }

    private var mDepanFile: File? = null
    private var mDepanUrl: String? = null
    private var mKananFile: File? = null
    private var mKananUrl: String? = null
    private var mKiriFile: File? = null
    private var mKiriUrl: String? = null
    private var idDepan: Int? = null
    private var idKanan: Int? = null
    private var idKiri: Int? = null

    private lateinit var sessionManager1: SessionManager1
    private var editFotoReq = EditFotoReq()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_foto)
        sessionManager1 = SessionManager1(this)

        val detailPersonel = intent.extras?.getParcelable<AllPersonelModel1>("PERSONEL_DETAIL")
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Foto ${detailPersonel?.nama.toString()}"

        btn_edit_foto_depan_edit.setOnClickListener {
            picker(DEPAN)
        }

        btn_edit_foto_kanan_edit.setOnClickListener {
            picker(KANAN)
        }

        btn_edit_foto_kiri_edit.setOnClickListener {
            picker(KIRI)
        }
        getFoto(detailPersonel)
        btn_save_foto_edit.attachTextChangeAnimator()
        bindProgressButton(btn_save_foto_edit)
        btn_save_foto_edit.setOnClickListener {
            when {
                idDepan == 0 -> {
                    Toast.makeText(this, "Harus Ada Foto Depan", Toast.LENGTH_SHORT).show()
                }
                idKanan == 0 -> {
                    Toast.makeText(this, "Harus Ada Foto Samping Kanan", Toast.LENGTH_SHORT).show()
                }
                idKiri == 0 -> {
                    Toast.makeText(this, "Harus Ada Foto Samping Kiri", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    updateFoto()
                }
            }

        }
    }

    private fun updateFoto() {
        val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        btn_save_foto_edit.showProgress {
            progressColor = Color.WHITE
        }

        editFotoReq.id_foto_kanan = idKanan
        editFotoReq.id_foto_kiri = idKiri
        editFotoReq.id_foto_muka = idDepan
        NetworkConfig().getServPers().updateFoto(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            sessionManager1.fetchID().toString(),
            editFotoReq
        ).enqueue(object : Callback<BaseResp> {
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                btn_save_foto_edit.hideDrawable(R.string.save)
                Toast.makeText(this@EditFotoActivity, "$t", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if (response.isSuccessful) {
                    btn_save_foto_edit.showDrawable(animatedDrawable) {
                        buttonTextRes = R.string.data_updated
                        textMarginRes = R.dimen.space_10dp
                    }
                } else {
                    Toast.makeText(
                        this@EditFotoActivity,
                        "${response.body()?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_foto_edit.hideDrawable(R.string.save)
                    }, 3000)
                    btn_save_foto_edit.hideDrawable(R.string.not_update)
                }
            }
        })
    }

    fun showImage(view: View) {
        val file = when (view) {
            img_foto_depan_edit -> {
                mDepanFile
            }
            img_foto_kanan_edit -> {
                mKananFile
            }
            img_foto_kiri_edit -> {
                mKiriFile
            }
            else -> null
        }
        file?.let {
            IntentUtil.showImage(this, file)
        }
    }

    private fun getFoto(personel: AllPersonelModel1?) {
        /* NetworkConfig().getService().getFoto(
             "Bearer ${sessionManager1.fetchAuthToken()}",
             sessionManager1.fetchID().toString()
         ).enqueue(object : Callback<FotoModel> {
             override fun onFailure(call: Call<FotoModel>, t: Throwable) {
                 Toast.makeText(this@EditFotoActivity, R.string.error_conn, Toast.LENGTH_SHORT)
                     .show()
             }

             override fun onResponse(call: Call<FotoModel>, response: Response<FotoModel>) {
                 if (response.isSuccessful) {
                     if (response.body()!!.id_foto_kanan == null) {
                         img_foto_kanan_null.visible()
                         img_foto_kanan_edit.gone()
                     }
                     if (response.body()!!.id_foto_kiri == null) {
                         img_foto_kiri_null.visible()
                         img_foto_kiri_edit.gone()
                     }
                     if (response.body()!!.id_foto_muka == null) {
                         img_foto_depan_null.visible()
                         img_foto_depan_edit.gone()
                     }

                     response.body()!!.id_foto_muka?.let {
                         idDepan = it.toInt()
                         img_foto_depan_edit.setFromUrl(it)
                     }
                     response.body()!!.id_foto_kanan?.let {
                         idKanan = it.toInt()
                         img_foto_kanan_edit.setFromUrl(it)
                     }
                     response.body()!!.id_foto_kiri?.let {
                         idKiri = it.toInt()
                         img_foto_kiri_edit.setFromUrl(it)
                     }

                 } else {
                     Toast.makeText(this@EditFotoActivity, R.string.error, Toast.LENGTH_SHORT).show()
                 }
             }
         })*/

        if (personel?.foto_kanan == null) {
            img_foto_kanan_edit.gone()
            img_foto_kanan_null.visible()
        } else {
            personel.foto_kanan?.url?.let { img_foto_kanan_edit.setFromUrl(it) }
        }

        if (personel?.foto_kiri == null) {
            img_foto_kiri_edit.gone()
            img_foto_kiri_null.visible()
        } else {
            personel?.foto_kiri?.url?.let { img_foto_kiri_edit.setFromUrl(it) }
        }
        if (personel?.foto_muka == null) {
            img_foto_depan_null.visible()
            img_foto_depan_edit.gone()
        } else {
            personel?.foto_muka?.url?.let { img_foto_depan_edit.setFromUrl(it) }
        }

    }

    private fun picker(req: Int) {
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
        val detailPersonel = intent.extras?.getParcelable<AllPersonelModel1>("PERSONEL_DETAIL")
        if (resultCode == RESULT_OK) {
            Log.e("TAG", "Path:${ImagePicker.getFilePath(data)}")
            val file = ImagePicker.getFile(data)!!
            when (requestCode) {
                DEPAN -> {
                    mDepanFile = file
                    pb_foto_depan_edit.visible()
                    img_foto_depan_edit.gone()
                    val requestBody = RequestBody.create(MediaType.parse("*/*"), mDepanFile)
                    val filePart =
                        MultipartBody.Part.createFormData("foto", mDepanFile!!.name, requestBody)
//                    img_foto_depan_edit.setLocalImage(file, false)
                    uploadEdit(detailPersonel, "foto_muka", filePart)
                    //                    uploadFoto(mDepanFile)
                }
                KANAN -> {
                    mKananFile = file
                    pb_foto_kanan_edit.visible()
                    img_foto_kanan_edit.gone()
//                    uploadFoto(mKananFile)
                    val requestBody = RequestBody.create(MediaType.parse("*/*"), mKananFile)
                    val filePart =
                        MultipartBody.Part.createFormData("foto", mKananFile!!.name, requestBody)
                    uploadEdit(detailPersonel, "foto_kanan", filePart)
                    /*  NetworkConfig().getService().uploadKanan(
                        "Bearer ${sessionManager1.fetchAuthToken()}",
                        filePart
                    ).enqueue(object : Callback<Base1Resp<ArrayList<FotoResp>>> {
                        override fun onFailure(
                            call: Call<Base1Resp<ArrayList<FotoResp>>>,
                            t: Throwable
                        ) {
                            Toast.makeText(
                                this@EditFotoActivity,
                                "Error Koneksi",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onResponse(
                            call: Call<Base1Resp<ArrayList<FotoResp>>>,
                            response: Response<Base1Resp<ArrayList<FotoResp>>>
                        ) {
                            if (response.isSuccessful) {
                                img_foto_kanan_edit.setLocalImage(file, false)
                                pb_foto_kanan_edit.gone()
                                img_foto_kanan_edit.visible()
                                img_foto_kanan_null.gone()
                                mKananUrl = response.body()?.data?.get(0)?.url
                                idKanan = response.body()?.data?.get(0)?.id
                            } else {
                                pb_foto_kanan_edit.gone()
                                img_foto_kanan_edit.visible()
                                Toast.makeText(
                                    this@EditFotoActivity,
                                    "Error Koneksi",
                                    Toast.LENGTH_SHORT

                                ).show()
                            }
                        }
                    })*/

                }
                KIRI -> {
                    mKiriFile = file
//                    uploadFoto(mKiriFile)
                    pb_foto_kiri_edit.visible()
                    img_foto_kiri_edit.gone()
                    val requestBody = RequestBody.create(MediaType.parse("*/*"), mKiriFile)
                    val filePart =
                        MultipartBody.Part.createFormData("foto", mKiriFile!!.name, requestBody)
                    uploadEdit(detailPersonel, "foto_kiri", filePart)

                    /* NetworkConfig().getService().uploadKiri(
                        "Bearer ${sessionManager1.fetchAuthToken()}",
                        filePart
                    ).enqueue(object : Callback<Base1Resp<ArrayList<FotoResp>>> {
                        override fun onFailure(
                            call: Call<Base1Resp<ArrayList<FotoResp>>>,
                            t: Throwable
                        ) {
                            Toast.makeText(
                                this@EditFotoActivity,
                                "Error Koneksi",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onResponse(
                            call: Call<Base1Resp<ArrayList<FotoResp>>>,
                            response: Response<Base1Resp<ArrayList<FotoResp>>>
                        ) {
                            if (response.isSuccessful) {
                                img_foto_kiri_edit.setLocalImage(file, false)
                                pb_foto_kiri_edit.gone()
                                img_foto_kiri_edit.visible()
                                img_foto_kiri_null.gone()
                                mKiriUrl = response.body()?.data?.get(0)?.url
                                idKiri = response.body()?.data?.get(0)?.id
                            } else {
                                pb_foto_kiri.gone()
                                img_foto_kiri.visible()
                                Toast.makeText(
                                    this@EditFotoActivity,
                                    "Error Koneksi",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })*/
                }
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadEdit(
        detailPersonel: AllPersonelModel1?, jenis: String, filePart: MultipartBody.Part
    ) {

        NetworkConfig().getServPers().uploadEditFoto(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            detailPersonel?.id.toString(),
            jenis,
            filePart
        ).enqueue(object : Callback<Base1Resp<ArrayList<AllPersonelModel1>>> {
            override fun onFailure(
                call: Call<Base1Resp<ArrayList<AllPersonelModel1>>>, t: Throwable
            ) {
                Log.e("error", "$t")
                Toast.makeText(
                    this@EditFotoActivity,
                    "Error Koneksi",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(
                call: Call<Base1Resp<ArrayList<AllPersonelModel1>>>,
                response: Response<Base1Resp<ArrayList<AllPersonelModel1>>>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.message == "Data foto personel updated succesfully") {
                        setImg(jenis, response.body())
                    }
                    /* pb_foto_depan_edit.gone()
                     img_foto_depan_null.gone()
                     img_foto_depan_edit.visible()
                     Log.e("depan", "${response.body()?.data?.foto}")
 //                                img_foto_depan_edit.setLocalImage(file, false)
                     mDepanUrl = response.body()?.data?.foto?.foto_muka?.url
                     mDepanUrl?.let { img_foto_depan_edit.setFromUrl(it, false) }*/
//                                idDepan = response.body()?.data?.get(0)?.id
                } else {
                    Toast.makeText(
                        this@EditFotoActivity,
                        "Error Koneksi",
                        Toast.LENGTH_SHORT
                    ).show()
                    pb_foto_depan_edit.gone()
                    img_foto_depan_edit.visible()
                }
            }
        })
    }

    private fun setImg(
        jenis: String,
        body: Base1Resp<ArrayList<AllPersonelModel1>>?
    ) {
        when (jenis) {
            "foto_muka" -> {
                pb_foto_depan_edit.gone()
                img_foto_depan_null.gone()
                img_foto_depan_edit.visible()
                Log.e("depan", "${body?.data?.get(0)?.foto_muka}")
//                                img_foto_depan_edit.setLocalImage(file, false)
                mDepanUrl = body?.data?.get(0)?.foto_muka?.url
                mDepanUrl?.let { img_foto_depan_edit.setFromUrl(it, false) }
            }
            "foto_kanan" -> {
                pb_foto_kanan_edit.gone()
                img_foto_kanan_null.gone()
                img_foto_kanan_edit.visible()
                Log.e("kanan", "${body?.data?.get(0)?.foto_kanan}")
//                                img_foto_depan_edit.setLocalImage(file, false)
                mKananUrl = body?.data?.get(0)?.foto_kanan?.url
                mKananUrl?.let { img_foto_kanan_edit.setFromUrl(it, false) }
            }
            "foto_kiri" -> {
                pb_foto_kiri_edit.gone()
                img_foto_kiri_null.gone()
                img_foto_kiri_edit.visible()
                Log.e("kiri", "${body?.data?.get(0)?.foto_kiri}")
//                                img_foto_depan_edit.setLocalImage(file, false)
                mKiriUrl = body?.data?.get(0)?.foto_kiri?.url
                mKiriUrl?.let { img_foto_kiri_edit.setFromUrl(it, false) }
            }
        }
    }
}
