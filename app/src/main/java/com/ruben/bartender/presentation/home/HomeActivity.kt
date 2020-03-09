package com.ruben.bartender.presentation.home

import android.os.Bundle
import android.text.SpannableString
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindString
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ruben.bartender.R
import com.ruben.bartender.base.BaseActivity
import com.ruben.bartender.utils.ApplicationConstants
import com.ruben.bartender.utils.ApplicationUtility
import com.ruben.remote.model.response.basicMenuResponse.BasicMenuResponse
import com.ruben.remote.model.response.makeDrinkResponse.MakeDrinkResponse
import com.ruben.remote.model.response.menuCategoryResponse.CategoryResponse
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.all_appbar_layout.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class HomeActivity : BaseActivity(), IDrinkClickListener {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  @BindView(R.id.home_parent)
  lateinit var parentView: CoordinatorLayout

  @BindView(R.id.toolbarTitle)
  lateinit var toolbarTitle: AppCompatTextView

  @BindView(R.id.drawer_layout)
  lateinit var drawerLayout: DrawerLayout

  @BindView(R.id.homeProgressBar)
  lateinit var progressBar: ProgressBar

  @BindView(R.id.homeRv)
  lateinit var homeRecyclerView: RecyclerView

  @BindView(R.id.home_menu)
  lateinit var homeMenuBtn: FloatingActionButton

  @BindString(R.string.all_home)
  lateinit var home: String

  @BindString(R.string.all_ok)
  lateinit var ok: String

  @BindString(R.string.all_generic_err)
  lateinit var errorMessage: String

  private lateinit var homeViewModel: HomeViewModel
  private lateinit var popupMenu: PopupMenu

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)
    ButterKnife.bind(this)
    setupToolBar()
    setupViewModel()
    observeData()
    retrieveMenu()
    initRecyclerView()
  }

  private fun setupToolBar() {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp)
    toolbarTitle.text = home
  }

  private fun setupViewModel() {
    homeViewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
  }

  private fun retrieveMenu() {
    ApplicationUtility.showProgress(progressBar, this)
    homeViewModel.retrieveCategories()
    homeViewModel.retrieveBasicMenu()
  }

  private fun initRecyclerView() {
    val layoutManager = GridLayoutManager(this, 2)
    homeRecyclerView.layoutManager = layoutManager
  }

  private fun initPopupMenu(categoryResponse: CategoryResponse?) {
    if(categoryResponse != null) {
      val wrapper = ContextThemeWrapper(this, R.style.BasePopupMenu)
      popupMenu = PopupMenu(wrapper, homeMenuBtn)
      for(i in 0 until categoryResponse.categoryName!!.size) {
        if(categoryResponse.categoryName!![i].name != ApplicationConstants.BASIC_MENU) {
          val title = SpannableString(categoryResponse.categoryName!![i].name)
          popupMenu.menu.add(title)
        }
      }
      popupMenu.setOnMenuItemClickListener {
        Log.d("@@@", it.title.toString())
        return@setOnMenuItemClickListener false
      }
    }else {
      homeMenuBtn.hide()
    }
  }

  private fun updateUIWithMenu(menuResponse: BasicMenuResponse?) {
    ApplicationUtility.stopProgress(progressBar, this)
    if(menuResponse != null) {
      val homeAdapter = HomeAdapter(menuResponse, this)
      homeRecyclerView.adapter = homeAdapter
    }else {
      ApplicationUtility.showSnack(errorMessage, parentView, ok)
    }
  }

  private fun parseMakeDrinkResponse(makeDrinkResponse: MakeDrinkResponse?) {
    ApplicationUtility.stopProgress(progressBar, this)
    if(makeDrinkResponse != null) {
      when(makeDrinkResponse.status) {
        ApplicationConstants.HTTP_OK -> {
          Log.d("@@@", makeDrinkResponse.message)
          ApplicationUtility.showDrinkSuccessDialog(this)
        }
        ApplicationConstants.FILE_NOT_FOUND -> {
          ApplicationUtility.showSnack(makeDrinkResponse.message, parentView, ok)
        }
        else -> {
          ApplicationUtility.showSnack(errorMessage, parentView, ok)
        }
      }
    }
  }

  private fun observeData() {
    homeViewModel.getMenuCategories().observe(this, Observer { it?.let { initPopupMenu(it) } })
    homeViewModel.getBasicMenu().observe(this, Observer { it?.let { updateUIWithMenu(it) } })
    homeViewModel.observeMakeDrink().observe(this, Observer { it?.let { parseMakeDrinkResponse(it) } })
  }

  @OnClick(value = [R.id.home_menu])
  fun onClick(view: View) {
    when(view.id) {
      R.id.home_menu -> {
        popupMenu.show()
      }
    }
  }

  override fun onDrinkClicked(drinkName: String) {
    ApplicationUtility.showProgress(progressBar, this)
    homeViewModel.makeDrink(drinkName)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when(item.itemId) {
      android.R.id.home -> {
        drawerLayout.openDrawer(GravityCompat.START)
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onBackPressed() {
    super.onBackPressed()
    finish()
  }
}