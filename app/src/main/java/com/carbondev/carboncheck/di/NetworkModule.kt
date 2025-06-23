package com.carbondev.carboncheck.di

import com.carbondev.carboncheck.BuildConfig
import com.carbondev.carboncheck.data.remote.api.CarbonInterfaceService
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.serializer.MoshiSerializer
import kotlinx.datetime.Instant
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Dependency injection module for providing network-related dependencies.
 * Mostly used for supabase client and retrofit services.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    /**
     * Custom Moshi adapter for handling ISO 8601 date format.
     */
    private class IsoDateAdapter : JsonAdapter<Instant>() {
        // Locale doesn't matter because the timestamp is in UTC.
        private val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }

        @FromJson
        override fun fromJson(reader: JsonReader): Instant? {
            // Step 1: Check if the upcoming value in the JSON is null
            if (reader.peek() == JsonReader.Token.NULL) {
                // Step 2: If it is null, consume it from the reader and return a Kotlin null
                return reader.nextNull()
            }

            // Step 3: Only if we get past the null check, we can safely call nextString()
            val instantString = reader.nextString()

            // Step 4: Parse the string to an Instant
            return try {
                Instant.parse(instantString)
            } catch (e: Exception) {
                Timber.w(e, "Could not parse date: $instantString")
                null // Return null if the string is not a valid Instant
            }
        }

        @ToJson
        override fun toJson(writer: JsonWriter, value: Instant?) {
            // Write the Instant as an ISO 8601 string
            writer.value(value?.toString())
        }
    }

    /**
     * Provides a singleton instance of Moshi.
     * Moshi is a JSON library for Android and Java.
     * @return Moshi instance
     */
    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(IsoDateAdapter())
            .build()
    }

    /**
     * Provides a singleton instance of the Supabase client.
     * The client is configured with the Supabase URL and key from the build config.
     * It also installs the Postgrest and Auth plugins and sets up Moshi for JSON serialization.
     * @return SupabaseClient instance
     */
    @Provides
    @Singleton
    fun provideSupabaseClient(moshi: Moshi): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = BuildConfig.SUPABASE_URL,
            supabaseKey = BuildConfig.SUPABASE_KEY
        ) {
            install(Postgrest)
            install(Auth)
            defaultSerializer = MoshiSerializer(moshi = moshi)
        }
    }

    /**
     * Provides a singleton instance of the Okhttp client.
     * This is used for making HTTP requests to external API.
     * @return OkHttpClient instance
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(Interceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("Authorization", "Bearer ${BuildConfig.CARBON_INTERFACE_API_KEY}")
                    .header("Content-Type", "application/json")
                val request = requestBuilder.build()
                chain.proceed(request)
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    /**
     * Provides a singleton instance of Retrofit for the Carbon Interface API.
     * This is used for making HTTP requests to the Carbon Interface API.
     * @param client OkHttpClient instance
     * @param moshi Moshi instance
     * @return Retrofit instance
     */
    @Provides
    @Singleton
    @Named("carbon_interface")
    fun provideCarbonInterfaceRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.carboninterface.com/api/v1/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    /**
     * Provides a singleton instance of the Carbon Interface service.
     * This is used for making HTTP requests to the Carbon Interface API.
     * @param retrofit Retrofit instance
     * @return CarbonInterfaceService instance
     */
    @Provides
    @Singleton
    fun provideCarbonInterfaceService(@Named("carbon_interface") retrofit: Retrofit): CarbonInterfaceService {
        return retrofit.create(CarbonInterfaceService::class.java)
    }
}