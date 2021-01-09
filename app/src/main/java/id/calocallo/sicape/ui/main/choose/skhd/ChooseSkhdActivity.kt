package id.calocallo.sicape.ui.main.choose.skhd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Adapter
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PutKkeOnRpphModel
import id.calocallo.sicape.model.SkhdOnRpsModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.NetworkDummy
import id.calocallo.sicape.ui.main.rehab.rpph.AddRpphActivity
import id.calocallo.sicape.ui.main.rehab.rps.AddRpsActivity
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.toggleVisibility
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_choose_skhd.*
import kotlinx.android.synthetic.main.layout_1_text_clickable.view.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChooseSkhdActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var skhdOnRpsModel = ArrayList<SkhdOnRpsModel>()
    private var adapterSkhdOnRps = ReusableAdapter<SkhdOnRpsModel>(this)
    private lateinit var callbackSkhdOnRps: AdapterCallback<SkhdOnRpsModel>

    private var putKkeOnRpphModel = ArrayList<PutKkeOnRpphModel>()
    private var adapterPutKkeOnRpph = ReusableAdapter<PutKkeOnRpphModel>(this)
    private lateinit var callbackPutKkeOnRpph: AdapterCallback<PutKkeOnRpphModel>

    private var putkke: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_skhd)
        setupActionBarWithBackButton(toolbar)

        sessionManager = SessionManager(this)
        putkke = intent.extras?.getString(PUT_KKE)
        if (putkke == null) {
            getListChooseSkhd()
            supportActionBar?.title = "Pilih Data SKHD"
        } else {
            getListChoosePutKke()
            supportActionBar?.title = "Pilih Data PUT KKE"
        }
    }

    private fun getListChoosePutKke() {
        rl_pb.visible()
        NetworkDummy().getService().getPutKkeOnRpph()
            .enqueue(object : Callback<ArrayList<PutKkeOnRpphModel>> {
                override fun onFailure(call: Call<ArrayList<PutKkeOnRpphModel>>, t: Throwable) {
                    rl_no_data.visible()
                    rv_choose_skhd.gone()
                }

                override fun onResponse(
                    call: Call<ArrayList<PutKkeOnRpphModel>>,
                    response: Response<ArrayList<PutKkeOnRpphModel>>
                ) {
                    rl_pb.gone()
                    if (response.isSuccessful) {
                        callbackPutKkeOnRpph = object : AdapterCallback<PutKkeOnRpphModel> {
                            override fun initComponent(itemView: View, data: PutKkeOnRpphModel, itemIndex: Int) {
                                itemView.txt_1_clickable.text = data.no_putkke
                            }

                            override fun onItemClicked(itemView: View, data: PutKkeOnRpphModel, itemIndex: Int) {
                                itemView.img_clickable.toggleVisibility()
                                val intent = Intent()
                                intent.putExtra(AddRpphActivity.GET_RPPH, data)
                                setResult(AddRpphActivity.RES_PUT_KKE_ON_RPPH, intent)
                                finish()
                            }
                        }
                        response.body()?.let {
                            adapterPutKkeOnRpph.adapterCallback(callbackPutKkeOnRpph)
                                .isVerticalView()
                                .filterable()
                                .addData(it)
                                .setLayout(R.layout.layout_1_text_clickable)
                                .build(rv_choose_skhd)
                        }
                    } else {
                        rl_no_data.visible()
                        rv_choose_skhd.gone()
                    }
                }
            })
//        putKkeOnRpphModel.add(PutKkeOnRpphModel(1, "PUTKKE/1/2021"))
//        putKkeOnRpphModel.add(PutKkeOnRpphModel(2, "PUTKKE/2/2020"))

    }

    private fun getListChooseSkhd() {
        rl_pb.visible()
        NetworkDummy().getService().getSkhdOnRps()
            .enqueue(object : Callback<ArrayList<SkhdOnRpsModel>> {
                override fun onFailure(call: Call<ArrayList<SkhdOnRpsModel>>, t: Throwable) {
                    rl_no_data.visible()
                    rv_choose_skhd.gone()
                }

                override fun onResponse(
                    call: Call<ArrayList<SkhdOnRpsModel>>,
                    response: Response<ArrayList<SkhdOnRpsModel>>
                ) {
                    rl_pb.gone()
                    if (response.isSuccessful) {
                        callbackSkhdOnRps = object : AdapterCallback<SkhdOnRpsModel> {
                            override fun initComponent(
                                itemView: View,
                                data: SkhdOnRpsModel,
                                itemIndex: Int
                            ) {
                                itemView.txt_1_clickable.text = data.no_skhd
                            }

                            override fun onItemClicked(
                                itemView: View,
                                data: SkhdOnRpsModel,
                                itemIndex: Int
                            ) {
                                itemView.img_clickable.toggleVisibility()
                                val intent = Intent()
                                intent.putExtra(AddRpsActivity.GET_SKHD, data)
                                setResult(AddRpsActivity.RES_ID_SKHD, intent)
                                finish()
                            }
                        }
                        response.body()?.let {
                            adapterSkhdOnRps.adapterCallback(callbackSkhdOnRps)
                                .isVerticalView()
                                .addData(it)
                                .filterable()
                                .setLayout(R.layout.layout_1_text_clickable)
                                .build(rv_choose_skhd)
                        }
                    } else {
                        rl_no_data.visible()
                        rv_choose_skhd.gone()
                    }
                }
            })
        /*
        skhdOnRpsModel.add(SkhdOnRpsModel(1, "SKHD1/2020?xxx"))
        skhdOnRpsModel.add(SkhdOnRpsModel(2, "SKHD2/2020?xxx"))
        skhdOnRpsModel.add(SkhdOnRpsModel(3, "SKHD3/2020?xxx"))

         */

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        if (putkke == null) {
            searchView.queryHint = "Cari SKHD"
        } else {
            searchView.queryHint = "Cari PUT KKE"

        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterSkhdOnRps.filter.filter(newText)
                adapterPutKkeOnRpph.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    companion object {
        const val PUT_KKE = "PUT_KKE"
    }
}