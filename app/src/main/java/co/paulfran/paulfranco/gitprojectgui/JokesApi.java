package co.paulfran.paulfranco.gitprojectgui;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JokesApi {

    @GET("jokes/random")
    Call<Joke> getJoke();
}
