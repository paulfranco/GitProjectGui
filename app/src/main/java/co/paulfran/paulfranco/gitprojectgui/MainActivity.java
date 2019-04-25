package co.paulfran.paulfranco.gitprojectgui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView jokeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        jokeTextView = findViewById(R.id.joke_text_view);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.chucknorris.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JokesApi jokesApi = retrofit.create(JokesApi.class);

        final Call<Joke> jokeCall = jokesApi.getJoke();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayJoke(jokeCall);
            }
        });
    }

    private void displayJoke(Call<Joke> jokeCall) {

        Call<Joke> cloneCall = jokeCall.clone();

        cloneCall.enqueue(new Callback<Joke>() {
            @Override
            public void onResponse(Call<Joke> call, Response<Joke> response) {

                String joke = response.body().getValue();

                jokeTextView.setText(joke);
            }

            @Override
            public void onFailure(Call<Joke> call, Throwable t) {

                Toast.makeText(MainActivity.this, R.string.failed_to_fetch_data,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_reset) {
            jokeTextView.setText(R.string.press_button_to_reset);
        }

        return super.onOptionsItemSelected(item);
    }
}
