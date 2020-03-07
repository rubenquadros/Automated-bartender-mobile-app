package com.ruben.bartender.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.ruben.bartender.R
import com.ruben.remote.model.response.basicMenuResponse.BasicMenuResponse
import com.squareup.picasso.Picasso

/**
 * Created by ruben.quadros on 01/03/20.
 **/
class HomeAdapter(private val menuList: BasicMenuResponse, private val listener: IDrinkClickListener): RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home_recycler_view, parent, false)
    return ViewHolder(view)
  }

  override fun getItemCount(): Int {
    return menuList.documents.size
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.menuTitle.text = menuList.documents[position].name
    holder.menuTitle.setOnClickListener { listener.onDrinkClicked(menuList.documents[position].name!!) }
    Picasso.get().load(menuList.documents[position].image).placeholder(R.drawable.placeholder).fit().into(holder.menuImage)
    holder.menuImage.setOnClickListener { listener.onDrinkClicked(menuList.documents[position].name!!) }
    holder.menuDesc.text = menuList.documents[position].description
    holder.menuDesc.setOnClickListener { listener.onDrinkClicked(menuList.documents[position].name!!) }
  }

  class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val menuTitle: AppCompatTextView = itemView.findViewById(R.id.home_menu_title)
    val menuImage: AppCompatImageView = itemView.findViewById(R.id.home_menu_image)
    val menuDesc: AppCompatTextView = itemView.findViewById(R.id.home_menu_desc)
  }

}