package com.ruben.data.mapper

import com.ruben.domain.model.BasicMenuRecord
import com.ruben.domain.model.CategoryName
import com.ruben.domain.model.CategoryRecord
import com.ruben.domain.model.Document
import com.ruben.remote.model.response.basicMenuResponse.BasicMenuResponse
import com.ruben.remote.model.response.menuCategoryResponse.CategoryResponse

/**
 * Created by ruben.quadros on 10/03/20.
 **/
class MenuMapper {

  fun mapBasicMenu(basicMenuResponse: BasicMenuResponse?): BasicMenuRecord? {
    return if(basicMenuResponse != null) {
      val basicMenuRecord = BasicMenuRecord(ArrayList())
      for(document in basicMenuResponse.documents!!) {
        val menuDocument = Document("", "", "")
        menuDocument.name = document.data!!["name"].toString()
        menuDocument.description = document.data!!["description"].toString()
        menuDocument.image = document.data!!["image"].toString()
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