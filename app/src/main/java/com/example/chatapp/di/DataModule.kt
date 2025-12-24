package com.example.chatapp.di

import android.content.Context
import com.example.chatapp.presentation.util.ErrorParser
import com.example.chatapp.presentation.util.ResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface DataModule {


    companion object {
        @Provides
        @Singleton
        fun provideResourceProvider(
            @ApplicationContext context: Context
        ): ResourceProvider {
            return ResourceProvider(context)
        }

        @Provides
        @Singleton
        fun provideErrorParser(
            resourceProvider: ResourceProvider
        ): ErrorParser {
            return ErrorParser(resourceProvider)
        }
    }
}