package com.alkss.pokedex.di

import android.app.Application
import androidx.room.Room
import com.alkss.pokedex.core.data.remote.MyAPI
import com.alkss.pokedex.core.data.remote.endpoint.PokedexApi
import com.alkss.pokedex.core.data.repository.PokemonLocalRepository
import com.alkss.pokedex.core.data.repository.PokemonRemoteRepository
import com.alkss.pokedex.feature_detail.domain.use_case.DetailUseCases
import com.alkss.pokedex.feature_detail.domain.use_case.FetchPokemonByName
import com.alkss.pokedex.feature_detail.domain.use_case.SwitchPokemonFavorite
import com.alkss.pokedex.feature_list.data.data_source.PokemonDatabase
import com.alkss.pokedex.feature_list.domain.use_case.FetchPokemonListByName
import com.alkss.pokedex.feature_list.domain.use_case.ListUseCases
import com.alkss.pokedex.feature_list.domain.use_case.FetchPokemonRequest
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providePokedexApi(
        okHttpClient: OkHttpClient,
    ): PokedexApi {
        return Retrofit.Builder()
            .baseUrl(MyAPI.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().create()
                )
            )
            .build()
            .create(PokedexApi::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .followRedirects(false)
            .followSslRedirects(false)
            .build()
    }

    @Provides
    @Singleton
    fun provideRemotePokemonRepository(
        api: PokedexApi
    ) = PokemonRemoteRepository(api)


    @Provides
    @Singleton
    fun provideLocalRepository(
        db: PokemonDatabase
    ) = PokemonLocalRepository(db.pokemonDao)

    @Provides
    @Singleton
    fun providePokemonDatabase(app: Application): PokemonDatabase {
        return Room.databaseBuilder(
            app,
            PokemonDatabase::class.java,
            PokemonDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideListUseCases(
        localRepository: PokemonLocalRepository,
        remoteRepository: PokemonRemoteRepository
    ): ListUseCases {
        return ListUseCases(
            fetchPokemonRequest = FetchPokemonRequest(
                localRepository = localRepository,
                remoteRepository = remoteRepository
            ),
            getLocalPokemonByName = FetchPokemonListByName(
                remoteRepository = remoteRepository,
                localRepository = localRepository
            )
        )
    }

    @Singleton
    @Provides
    fun provideDetailUseCases(
        localRepository: PokemonLocalRepository,
        remoteRepository: PokemonRemoteRepository
    ): DetailUseCases {
        return DetailUseCases(
            switchFavorite = SwitchPokemonFavorite(localRepository = localRepository),
            fetchPokemonByName = FetchPokemonByName(
                remoteRepository = remoteRepository,
                localRepository = localRepository
            )
        )
    }

    @Singleton
    @Provides
    fun providePokemonDao(db: PokemonDatabase) = db.pokemonDao


}