package com.ruben.bartender.presentation.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.ruben.bartender.R
import com.ruben.bartender.domain.model.BasicMenuRecord
import com.ruben.bartender.utils.ApplicationConstants
import com.squareup.picasso.Picasso

/**
 * Created by ruben.quadros on 01/03/20.
 **/
class HomeAdapter(
  private val menuList: BasicMenuRecord,
  private val listener: IDrinkClickListener
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view =
      LayoutInflater.from(parent.context).inflate(R.layout.item_home_recycler_view, parent, false)
    return ViewHolder(view)
  }

  override fun getItemCount(): Int {
    return menuList.menuRecord!!.size
  }

  @SuppressLint("SetTextI18n")
  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.menuTitle.text = menuList.menuRecord!![position].name
    holder.menuTitle.setOnClickListener {
      listener.onDrinkClicked(
        menuList.menuRecord!![position].name!!,
        menuList.menuRecord!![position].price!!
      )
    }
    Picasso.get().load(menuList.menuRecord!![position].image)
      .placeholder(R.drawable.drink_menu_placeholder).fit().into(holder.menuImage)
    holder.menuImage.setOnClickListener {
      listener.onDrinkClicked(
        menuList.menuRecord!![position].name!!,
        menuList.menuRecord!![position].price!!
      )
    }
    holder.menuDesc.text = menuList.menuRecord!![position].description
    holder.menuDesc.setOnClickListener {
      listener.onDrinkClicked(
        menuList.menuRecord!![position].name!!,
        menuList.menuRecord!![position].price!!
      )
    }
    holder.price.text = ApplicationConstants.RUPEE_SYMBOL + menuList.menuRecord!![position].price
    holder.price.setOnClickListener {
      listener.onDrinkClicked(
        menuList.menuRecord!![position].name!!,
        menuList.menuRecord!![position].price!!
      )
    }
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val menuTitle: AppCompatTextView = itemView.findViewById(R.id.home_menu_title)
    val menuImage: AppCompatImageView = itemView.findViewById(R.id.home_menu_image)
    val menuDesc: AppCompatTextView = itemView.findViewById(R.id.home_menu_desc)
    val price: AppCompatTextView = itemView.findViewById(R.id.home_price)
  }

}