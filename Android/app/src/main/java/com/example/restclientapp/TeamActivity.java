package com.example.restclientapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.restclientapp.api.AuthService;
import com.example.restclientapp.api.RetrofitClient;
import com.example.restclientapp.model.TeamResponse;
import com.example.restclientapp.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamActivity extends AppCompatActivity {

    private TextView tvTeamName;
    private RecyclerView recyclerView;
    private TeamAdapter adapter;
    private String currentUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        tvTeamName = findViewById(R.id.tvTeamName);
        recyclerView = findViewById(R.id.recyclerMembers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TeamAdapter();
        recyclerView.setAdapter(adapter);

        SessionManager session = new SessionManager(this);
        currentUserEmail = session.getEmail();

        cargarDatos();
    }

    private void cargarDatos() {
        AuthService service = RetrofitClient.getApiService();

        service.getUser(currentUserEmail).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // CAMBIO CLAVE: Obtenemos el ID, no el nombre
                    String userId = response.body().getId();

                    // 2. Llamamos a la API usando el ID
                    cargarEquipo(userId);
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(TeamActivity.this, "Error usuario", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarEquipo(String id) {
        AuthService service = RetrofitClient.getApiService();

        Call<TeamResponse> call = service.getTeamInfo(id);

        call.enqueue(new Callback<TeamResponse>() {
            @Override
            public void onResponse(Call<TeamResponse> call, Response<TeamResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    TeamResponse data = response.body();
                    tvTeamName.setText("Equipo: " + data.getTeam());

                    adapter.setMembers(data.getMembers());
                } else {
                    tvTeamName.setText("No se ha encontrado equipo para este ID");
                }
            }

            @Override
            public void onFailure(Call<TeamResponse> call, Throwable t) {
                Toast.makeText(TeamActivity.this, "Fallo de red al cargar equipo", Toast.LENGTH_SHORT).show();
            }
        });
    }
}