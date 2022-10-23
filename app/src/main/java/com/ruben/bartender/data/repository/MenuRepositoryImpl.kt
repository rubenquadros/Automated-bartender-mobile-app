package com.ruben.bartender.data.repository

import com.ruben.bartender.data.DataSource
import com.ruben.bartender.domain.record.MainMenuRecord
import com.ruben.bartender.domain.record.CategoryRecord
import com.ruben.bartender.domain.repository.MenuRepository
import com.ruben.bartender.data.repository.mapper.MenuMapper
import com.ruben.bartender.data.repository.mapper.toMainMenuBaseRecord
import com.ruben.bartender.domain.BaseRecord
import com.ruben.bartender.domain.record.ErrorRecord
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by ruben.quadros on 29/02/20.
 **/
class MenuRepositoryImpl @Inject constructor(dataSource: DataSource) : MenuRepository {

    private val firebaseApi = dataSource.api().firebaseApiHandler()
    private val menuMapper = MenuMapper()

    override suspend fun getMainMenu(): Flow<BaseRecord<MainMenuRecord, ErrorRecord>> {
        return firebaseApi.getMainMenu().map {
            it.toMainMenuBaseRecord()
        }
    }

    override fun getMenuCategories(): Flow<CategoryRecord?> {
        return firebaseApi.getMenuCategories().map {
            menuMapper.mapCategories(it)
        }
    }

}