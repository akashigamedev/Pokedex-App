package com.akashi.pokedex.di

import com.akashi.pokedex.data.remote.PokeAPI
import com.akashi.pokedex.repository.PokemonRepository
import com.akashi.pokedex.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePokemonRepository(api: PokeAPI) = PokemonRepository(api)

    @Provides
    @Singleton
    fun providePokeAPI(): PokeAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokeAPI::class.java)
    }
}