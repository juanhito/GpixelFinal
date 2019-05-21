package com.gpixel.Login;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.gpixel.R;
import com.gpixel.Retrofit.APIRestService;
import com.gpixel.Retrofit.RetrofitClient;
import com.gpixel.javabeans.AdaptadorJuegos;
import com.gpixel.javabeans.Imagen;
import com.gpixel.javabeans.Juego;
import com.gpixel.javabeans.Prueba;
import com.gpixel.javabeans.plataformas;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InicioActivity extends AppCompatActivity {

    private RecyclerView rvMain;
        private AdaptadorJuegos adaptador;
        private LinearLayoutManager llm;
        String id;


        private ArrayList<Juego> datos;
        private ArrayList<String>datos2;
        private ArrayList<plataformas>datos3;
        Juego juego;
        Imagen imag;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_inicio);

            rvMain = findViewById(R.id.rv);
            datos=new ArrayList<Juego>();
        adaptador=new AdaptadorJuegos(datos);
        llm = new LinearLayoutManager(this);
        datos2=new ArrayList<String>();
        rvMain.setItemAnimator(new DefaultItemAnimator());
        rvMain.setAdapter(adaptador);
        rvMain.setLayoutManager(llm);
        datos3=new ArrayList<plataformas>();
        /*CARGAR DATOS*/
       //cargarDatos();
        consumirWS();
        //prueba();

    }
    public void consumirWS(){
        if(isNetworkAvailable()){
        Retrofit r = RetrofitClient.getClient(APIRestService.BASE_URL);
        APIRestService ars = r.create(APIRestService.class);
        Call<Prueba> call = ars.obtenerPrueba(ars.Key,ars.format,ars.field_list,ars.filter);

        call.enqueue(new Callback<Prueba>() {


            @Override
            public void onResponse(Call<Prueba> call, Response<Prueba> response) {
                if(response.isSuccessful()) {
                    Prueba juego = response.body();
                    datos=juego.getResults();


                    adaptador.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i=new Intent(InicioActivity.this,ActivityRegistrar.class);
                        }
                    });



                    adaptador = new AdaptadorJuegos(datos);
                    rvMain.setAdapter(adaptador);

                    //adaptador.notifyDataSetChanged();

                } else {
                    System.out.print("Eres un inutil");
                }
                adaptador.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Juego juego=datos.get(rvMain.getChildAdapterPosition(v));
                        datos3=juego.getPlataformasAL();
                        imag=juego.getimagen();


                        for(int j=0;j<=datos3.size()-1;j++){
                            plataformas plat= datos3.get(j);
                            String pene=plat.getNombre();
                            datos2.add(pene);



                        }
                        Intent i=new Intent(InicioActivity.this,JuegoActivity.class);
                        i.putExtra("imagen",imag.getImagen());
                        i.putExtra("nombre",juego.getNombre());
                        i.putExtra("descripcion",juego.getDescripcion());
                        i.putExtra("fecha",juego.getFecha());
                        i.putExtra("plataformas",datos2);

                        startActivity(i);
                    }
                });
            }

            @Override
            public void onFailure(Call<Prueba> call, Throwable t) {
                System.out.print("Eres un inutil");

            }
        });
    }}




    private void cargarDatos( ) {


       if (isNetworkAvailable()) {
            Retrofit r = RetrofitClient.getClient(APIRestService.BASE_URL);
            APIRestService ars = r.create(APIRestService.class);
            Call<ArrayList<Juego>> call = ars.obtenerCds("bd2514fa50bc7f31b80992f4dd257af11aa48f96","json","name","");

            call.enqueue(new Callback<ArrayList<Juego>>() {

                @Override
               public void onResponse(Call<ArrayList<Juego>> call, Response<ArrayList<Juego>> response) {
                    if (!response.isSuccessful()) {
                        Log.i("Resultado: ", "Error" + response.code());
                    } else {
                        datos = response.body();
                        if (datos != null) {
                            adaptador = new AdaptadorJuegos(datos);
                            rvMain.setAdapter(adaptador);
                            adaptador.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                       }

                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Juego>> call, Throwable t) {
                    Log.e("error", t.toString());
                }
            });
        } else {
            Toast.makeText(this, "Error de conexión", Toast.LENGTH_LONG).show();
        }
    }


    private boolean isNetworkAvailable() {
        boolean isAvailable=false;
        //Gestor de conectividad
        ConnectivityManager manager = (ConnectivityManager) getSystemService(LoginActivity.CONNECTIVITY_SERVICE);
        //Objeto que recupera la información de la red
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        //Si la información de red no es nula y estamos conectados
        //la red está disponible
        if(networkInfo!=null && networkInfo.isConnected()){
            isAvailable=true;
        }
        return isAvailable;

    }
}
