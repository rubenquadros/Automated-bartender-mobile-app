package com.ruben.bartender.data.repository.di

import com.ruben.bartender.data.repository.DrinkRepositoryImpl
import com.ruben.bartender.data.repository.MenuRepositoryImpl
import com.ruben.bartender.data.repository.OnBoardingRepositoryImpl
import com.ruben.bartender.data.repository.SignUpRepositoryImpl
import com.ruben.bartender.data.repository.UserRepositoryImpl
import com.ruben.bartender.domain.repository.DrinkRepository
import com.ruben.bartender.domain.repository.MenuRepository
import com.ruben.bartender.domain.repository.OnBoardingRepository
import com.ruben.bartender.domain.repository.SignUpRepository
import com.ruben.bartender.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Ruben Quadros on 22/10/22
 **/
@[Module InstallIn(SingletonComponent::class)]
internal interface RepositoryModule {

    @[Binds Singleton]
    fun bindDrinkRepository(drinkRepository: DrinkRepositoryImpl): DrinkRepository

    @[Binds Singleton]
    fun bindMenuRepository(menuRepository: MenuRepositoryImpl): MenuRepository

    @[Binds Singleton]
    fun bindOnBoardingRepository(onBoardingRepository: OnBoardingRepositoryImpl): OnBoardingRepository

    @[Binds Singleton]
    fun bindSignUpRepository(signUpRepository: SignUpRepositoryImpl): SignUpRepository

    @[Binds Singleton]
    fun bindUserRepository(userRepository: UserRepositoryImpl): UserRepository
}