package id.calocallo.sicape.ui.main.editpersonel.pekerjaan

import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import androidx.fragment.app.FragmentTransaction
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AllPersonelModel1
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditPekerjaanActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pekerjaan)

        val bundle = intent.extras
        val personel = bundle?.getParcelable<AllPersonelModel1>("PERSONEL_DETAIL")

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = personel?.nama

        val pickPekerjaanFrag = PickPekerjaanFragment()
            .apply {
                enterTransition = Slide(Gravity.END)
                exitTransition = Slide(Gravity.START)
            }
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fl_edit_pekerjaan, pickPekerjaanFrag)
        ft.commit()
    }
}