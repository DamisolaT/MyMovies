package com.example.mymovies.ApiInterface

import com.example.mymovies.data.GetMoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import javax.security.auth.callback.Callback

interface Api {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "230c9ea7bc88c3bc90f267fc39180809",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = "230c9ea7bc88c3bc90f267fc39180809",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/upcoming")
    fun getUpComingMovies(
        @Query("api_key") apiKey: String = "230c9ea7bc88c3bc90f267fc39180809",
        @Query("page") page: Int

    ): Call<GetMoviesResponse>


}