package com.example.mymovies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovies.MoviesRepository.getTopRatedMovies
import com.example.mymovies.data.Movie
import com.example.mymovies.R

class MainActivity : AppCompatActivity() {
    private  lateinit var popularMovies : RecyclerView
    private lateinit var popularMoviesAdapter: MoviesAdapter
    private lateinit var popularMoviesLayoutManager: LinearLayoutManager

    private lateinit var topRatedMovies: RecyclerView
    private lateinit var topRatedMoviesAdapter: MoviesAdapter
    private lateinit var topRatedMoviesLayoutManager: LinearLayoutManager

    private lateinit var upComingMovies : RecyclerView
    private lateinit var upComingMoviesAdapter: MoviesAdapter
    private lateinit var upComingMoviesLayoutManager: LinearLayoutManager

    private  var popularMoviesPage = 1
    private  var topRatedMoviesPage = 1
    private var upComingMoviesPage = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        upComingMovies = findViewById(R.id.upcoming_movies)
        upComingMoviesLayoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        upComingMovies.layoutManager = upComingMoviesLayoutManager
        upComingMoviesAdapter = MoviesAdapter(mutableListOf()) { movie ->  showMovieDetails(movie) }
        upComingMovies.adapter = upComingMoviesAdapter


        topRatedMovies = findViewById(R.id.top_rated_movies)
        topRatedMoviesLayoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        topRatedMovies.layoutManager = topRatedMoviesLayoutManager
        topRatedMoviesAdapter = MoviesAdapter(mutableListOf()) { movie ->  showMovieDetails(movie) }
        topRatedMovies.adapter = topRatedMoviesAdapter

        popularMovies = findViewById(R.id.popular_movies)
        popularMoviesLayoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL, false
        )

        popularMovies.layoutManager = popularMoviesLayoutManager
        popularMoviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie)  }
        popularMovies.adapter = popularMoviesAdapter

        getPopularMovies()
        getTopRatedMovies()
        getUpComingMovies()

        MoviesRepository.getPopularMovies(
            onSuccess = ::onPopularMoviesFetched,
            onError = ::onError
        )
    }

    private fun attachPopularMoviesOnScrollListener(){
        popularMovies.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = popularMoviesLayoutManager.itemCount
                val visibleItemCount = popularMoviesLayoutManager.childCount
                val firstVisibleItem = popularMoviesLayoutManager.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2){
                    popularMovies.removeOnScrollListener(this)
                    popularMoviesPage++
                    getPopularMovies()
                }
            }
        })
    }

    private  fun getPopularMovies(){
        MoviesRepository.getPopularMovies(
            popularMoviesPage,
            ::onPopularMoviesFetched,
            ::onError
        )
    }
    private  fun onPopularMoviesFetched(movies: List<Movie>) {
        popularMoviesAdapter.appendMovies(movies)
        attachPopularMoviesOnScrollListener()
    }
    private  fun onError(){
        Toast.makeText(this, getString(R.string.error_fetch_movies), Toast.LENGTH_SHORT).show()
    }


    private fun  attachTopRatedMoviesOnScrollListener() {
        topRatedMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = topRatedMoviesLayoutManager.itemCount
                val visibleItemCount = topRatedMoviesLayoutManager.childCount
                val firstVisibleItem = topRatedMoviesLayoutManager.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2){
                    topRatedMovies.removeOnScrollListener(this)
                    topRatedMoviesPage++
                    getTopRatedMovies()

                }
            }
        })
    }

    private  fun onTopRatedMoviesFetched(movies: List<Movie>){
        topRatedMoviesAdapter.appendMovies(movies)
        attachTopRatedMoviesOnScrollListener()
    }

    private fun getTopRatedMovies() {
        MoviesRepository.getTopRatedMovies(
            topRatedMoviesPage,
            ::onTopRatedMoviesFetched,
            ::onError
        )
    }

    private  fun attachUpComingMoviesOnScrollListener(){
        upComingMovies.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = upComingMoviesLayoutManager.itemCount
                val visibleItemCount = upComingMoviesLayoutManager.childCount
                val firstVisibleItem = upComingMoviesLayoutManager.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    upComingMovies.removeOnScrollListener(this)
                    upComingMoviesPage++
                    getUpComingMovies()
                }
            }
        })
    }

    private  fun onUpComingMoviesFetched(movies: List<Movie>){
        upComingMoviesAdapter.appendMovies(movies)
        attachUpComingMoviesOnScrollListener()
    }

    private  fun getUpComingMovies(){
        MoviesRepository.getUpComingMovies(
            upComingMoviesPage,
            :: onUpComingMoviesFetched,
            :: onError
        )
    }

   private  fun showMovieDetails(movie: Movie) {
       val intent = Intent(this, MovieDetailsActivity::class.java)
       intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
       intent.putExtra(MOVIE_POSTER, movie.posterPath)
       intent.putExtra(MOVIE_TITTLE,movie.tittle)
       intent.putExtra(MOVIE_RATING, movie.rating)
       intent.putExtra(MOVIE_RELEASE_DATE,movie.releaseDate)
       intent.putExtra(MOVIE_OVERVIEW,movie.overview)
       startActivity(intent)
   }
}