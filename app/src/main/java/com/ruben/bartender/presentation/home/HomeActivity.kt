package com.ruben.bartender.presentation.home

import android.annotation.SuppressLint
import android.content.Intent
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
import com.google.android.material.navigation.NavigationView
import com.ruben.bartender.R
import com.ruben.bartender.base.BaseActivity
import com.ruben.bartender.presentation.onboarding.OnBoardingActivity
import com.ruben.bartender.utils.ApplicationConstants
import com.ruben.bartender.utils.ApplicationUtility
import com.ruben.domain.interactor.user.UserHandler
import com.ruben.domain.model.BasicMenuRecord
import com.ruben.domain.model.CategoryRecord
import com.ruben.domain.model.MakeDrinkRecord
import com.ruben.domain.model.SignOutRecord
import com.ruben.domain.model.UserRecord
import com.ruben.remote.utils.ApiConstants
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.all_appbar_layout.*
import kotlinx.android.synthetic.main.nav_header_home.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@Suppress("PrivatePropertyName")
@ExperimentalCoroutinesApi
class HomeActivity : BaseActivity(), IDrinkClickListener,
  NavigationView.OnNavigationItemSelectedListener {

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  @Inject
  lateinit var userHandler: UserHandler

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

  @BindView(R.id.nav_view)
  lateinit var navigationView: NavigationView

  @BindString(R.string.nav_header_title)
  lateinit var hello: String

  @BindString(R.string.all_home)
  lateinit var home: String

  @BindString(R.string.all_ok)
  lateinit var ok: String

  @BindString(R.string.all_generic_err)
  lateinit var errorMessage: String

  private lateinit var homeViewModel: HomeViewModel
  private lateinit var popupMenu: PopupMenu
  private val TAG = javaClass.simpleName

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)
    ButterKnife.bind(this)
    navigationView.setNavigationItemSelectedListener(this)
    setupToolBar()
    setupViewModel()
    observeData()
    retrieveMenu()
    retrieveUserData()
    initRecyclerView()
  }

  private fun setupToolBar() {
    setSupportActionBar(toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_nav_drawer_button)
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

  private fun retrieveUserData() {
    if (userHandler.phoneNumber() != null) {
      homeViewModel.retrieveUserData(userHandler.phoneNumber()!!)
    }
  }

  private fun initRecyclerView() {
    val layoutManager = GridLayoutManager(this, 2)
    homeRecyclerView.layoutManager = layoutManager
  }

  private fun initPopupMenu(categoryRecord: CategoryRecord?) {
    if (categoryRecord != null) {
      val wrapper = ContextThemeWrapper(this, R.style.BasePopupMenu)
      popupMenu = PopupMenu(wrapper, homeMenuBtn)
      for (i in 0 until categoryRecord.categories!!.size) {
        if (categoryRecord.categories!![i].name != ApplicationConstants.BASIC_MENU) {
          val title = SpannableString(categoryRecord.categories!![i].name)
          popupMenu.menu.add(title)
        }
      }
      popupMenu.setOnMenuItemClickListener {
        Log.d(TAG, it.title.toString())
        return@setOnMenuItemClickListener false
      }
    } else {
      homeMenuBtn.hide()
    }
  }

  private fun updateUIWithMenu(menuRecord: BasicMenuRecord?) {
    ApplicationUtility.stopProgress(progressBar, this)
    if (menuRecord != null) {
      val homeAdapter = HomeAdapter(menuRecord, this)
      homeRecyclerView.adapter = homeAdapter
    } else {
      ApplicationUtility.showSnack(errorMessage, parentView, ok)
    }
  }

  @SuppressLint("SetTextI18n")
  private fun updateUIWithUser(userRecord: UserRecord?) {
    if (userRecord != null) {
      homeNameTv.text = hello + " " + userRecord.firstName
      homePhoneTv.text = userRecord.phoneNumber
    }
  }

  private fun parseMakeDrinkResponse(makeDrinkRecord: MakeDrinkRecord?) {
    ApplicationUtility.stopProgress(progressBar, this)
    if (makeDrinkRecord != null) {
      when (makeDrinkRecord.responseCode) {
        ApiConstants.HTTP_OK -> {
          Log.d(TAG, makeDrinkRecord.responseMessage)
          ApplicationUtility.showDrinkSuccessDialog(this)
        }
        ApiConstants.HTTP_NEW_USER -> {
          ApplicationUtility.showSnack(makeDrinkRecord.responseMessage, parentView, ok)
        }
        else -> {
          ApplicationUtility.showSnack(errorMessage, parentView, ok)
        }
      }
    }
  }

  private fun parseSignOutResponse(signOutRecord: SignOutRecord?) {
    ApplicationUtility.stopProgress(progressBar, this)
    if (signOutRecord != null) {
      when (signOutRecord.responseCode) {
        ApiConstants.HTTP_OK -> {
          val intent = Intent(this, OnBoardingActivity::class.java)
          intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
          startActivity(intent)
        }
        else                 -> {
          ApplicationUtility.showSnack(
            this.resources.getString(R.string.all_generic_err),
            parentView,
            this.resources.getString(R.string.all_ok)
          )
        }
      }
      Log.d(TAG, signOutRecord.message)
    } else {
      ApplicationUtility.showSnack(
        this.resources.getString(R.string.all_generic_err),
        parentView,
        this.resources.getString(R.string.all_ok)
      )
    }
  }

  private fun observeData() {
    homeViewModel.getMenuCategories().observe(this, Observer { it?.let { initPopupMenu(it) } })
    homeViewModel.getBasicMenu().observe(this, Observer { it?.let { updateUIWithMenu(it) } })
    homeViewModel.getUserDataResponse().observe(this, Observer { it?.let { updateUIWithUser(it) } })
    homeViewModel.observeMakeDrink()
      .observe(this, Observer { it?.let { parseMakeDrinkResponse(it) } })
    homeViewModel.getSignOutResponse()
      .observe(this, Observer { it?.let { parseSignOutResponse(it) } })
  }

  @OnClick(value = [R.id.home_menu])
  fun onClick(view: View) {
    when (view.id) {
      R.id.home_menu -> {
        popupMenu.show()
      }
    }
  }

  override fun onDrinkClicked(drinkName: String) {
    ApplicationUtility.showProgress(progressBar, this)
    homeViewModel.makeDrink(drinkName)
  }

  override fun onNavigationItemSelected(p0: MenuItem): Boolean {
    when (p0.itemId) {
      R.id.nav_favourites -> {

      }
      R.id.nav_history    -> {

      }
      R.id.nav_logout     -> {
        ApplicationUtility.showProgress(progressBar, this)
        homeViewModel.logout()
      }
    }
    drawerLayout.closeDrawer(GravityCompat.START)
    return false
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
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