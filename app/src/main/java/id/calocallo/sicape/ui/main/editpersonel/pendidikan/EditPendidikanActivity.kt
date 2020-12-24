package id.calocallo.sicape.ui.main.editpersonel.pendidikan

import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import androidx.fragment.app.FragmentTransaction
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class EditPendidikanActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pendidikan)

        val bundel = intent.extras
        val personel = bundel?.getParcelable<PersonelModel>("PERSONEL_DETAIL")


        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = personel?.nama.toString()


        val pickPendFragment = PickPendFragment()
            .apply {
            enterTransition = Slide(Gravity.END)
            exitTransition = Slide(Gravity.START)
        }
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fl_edit_pend, pickPendFragment)
        ft.commit()


    }


}