package com.example.neurontask.di

import com.example.neurontask.data.repository.PurchaseRepositoryImpl
import com.example.neurontask.data.repository.UserRepositoryImpl
import com.example.neurontask.domain.repository.PurchaseRepository
import com.example.neurontask.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun provideUserRepository(repository: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    fun providePurchaseRepository(repository: PurchaseRepositoryImpl): PurchaseRepository
}