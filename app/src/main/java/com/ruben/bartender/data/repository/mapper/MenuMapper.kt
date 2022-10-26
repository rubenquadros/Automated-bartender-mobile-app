package com.ruben.bartender.data.repository.mapper

import com.ruben.bartender.data.remote.model.response.MainMenuResponse
import com.ruben.bartender.data.remote.model.response.menuCategoryResponse.CategoryResponse
import com.ruben.bartender.domain.BaseRecord
import com.ruben.bartender.domain.record.CategoryName
import com.ruben.bartender.domain.record.CategoryRecord
import com.ruben.bartender.domain.record.ErrorRecord
import com.ruben.bartender.domain.record.MainMenuRecord
import com.ruben.bartender.domain.record.MenuItem

/**
 * Created by ruben.quadros on 10/03/20.
 **/
class MenuMapper {

    fun mapCategories(categoryResponse: CategoryResponse?): CategoryRecord? {
        return if (categoryResponse != null) {
            val categoryRecord = CategoryRecord(ArrayList())
            for (document in categoryResponse.categoryName!!) {
                val categoryName = CategoryName("")
                categoryName.name = document.id
                categoryRecord.categories!!.add(categoryName)
            }
            categoryRecord
        } else {
            null
        }
    }
}

fun MainMenuResponse.toMainMenuBaseRecord(): BaseRecord<MainMenuRecord, ErrorRecord> {
    return when (this) {
        is MainMenuResponse.MainMenuSuccess -> {
            BaseRecord.Success(
                body = MainMenuRecord(
                    menuRecord = this.mainMenu.map {
                        MenuItem(
                            name = it.name,
                            price = it.price,
                            image = it.image.orEmpty(),
                            id = it.uniqueId
                        )
                    }
                )
            )
        }
        is MainMenuResponse.MainMenuFail -> {
            BaseRecord.Error(
                error = ErrorRecord.GenericErrorRecord(message = this.message)
            )
        }
    }
}