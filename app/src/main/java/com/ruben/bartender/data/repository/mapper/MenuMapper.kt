package com.ruben.bartender.data.repository.mapper

import com.ruben.bartender.data.remote.model.response.basicMenuResponse.BasicMenuResponse
import com.ruben.bartender.data.remote.model.response.menuCategoryResponse.CategoryResponse
import com.ruben.bartender.domain.record.BasicMenuRecord
import com.ruben.bartender.domain.record.CategoryName
import com.ruben.bartender.domain.record.CategoryRecord
import com.ruben.bartender.domain.record.Document

/**
 * Created by ruben.quadros on 10/03/20.
 **/
class MenuMapper {

  fun mapBasicMenu(basicMenuResponse: BasicMenuResponse?): BasicMenuRecord? {
    return if(basicMenuResponse != null) {
      val basicMenuRecord = BasicMenuRecord(ArrayList())
      for(document in basicMenuResponse.documents!!) {
        val menuDocument = Document("", "", "", "")
        menuDocument.name = document.data!!["name"].toString()
        menuDocument.description = document.data!!["description"].toString()
        menuDocument.image = document.data!!["image"].toString()
        menuDocument.price = document.data!!["price"].toString()
        basicMenuRecord.menuRecord!!.add(menuDocument)
      }
      basicMenuRecord
    }else {
      null
    }
  }

  fun mapCategories(categoryResponse: CategoryResponse?): CategoryRecord? {
    return if(categoryResponse != null) {
      val categoryRecord = CategoryRecord(ArrayList())
      for(document in categoryResponse.categoryName!!) {
        val categoryName = CategoryName("")
        categoryName.name = document.id
        categoryRecord.categories!!.add(categoryName)
      }
      categoryRecord
    }else {
      null
    }
  }
}