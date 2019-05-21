package com.gpixel.Login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gpixel.R;
import com.gpixel.Usuario.Aplicacion;
import com.gpixel.Usuario.UsuarioPojo;
import com.gpixel.javabeans.Usuario;


public class ActivityRegistrar extends AppCompatActivity {

    private EditText inputEmail, inputPassword,inputUser;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private DatabaseReference dbR;

    private String email;
    private String password;
    private String user;
    Aplicacion app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        app= (Aplicacion) getApplication();

        auth = FirebaseAuth.getInstance();

        btnSignUp =findViewById(R.id.btnRegistrarse);
        inputEmail =  findViewById(R.id.etEmail);
        inputPassword =findViewById(R.id.etCont);
        inputUser=findViewById(R.id.etNombre);
        progressBar = findViewById(R.id.progresbarreg);
        dbR= FirebaseDatabase.getInstance().getReference().child("Perfil");


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = inputEmail.getText().toString().trim();
                password = inputPassword.getText().toString().trim();
                user=inputUser.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Introduce tu dirección de Email!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Introduce la contraseña!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Contraseña demasiado corta, un minimo de 6 caracteres!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(ActivityRegistrar.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(ActivityRegistrar.this, "Creación con email completada" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                                if (!task.isSuccessful()) {
                                    Toast.makeText(ActivityRegistrar.this, "Registro Fallado." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    String id= dbR.push().getKey();
                                    String mail=auth.getCurrentUser().getEmail();

                                    UsuarioPojo usuarioPojo = new UsuarioPojo(mail,user,id);

                                    dbR.child(id).setValue(usuarioPojo);

                                    app.setIdusuario(id);
                                    app.setUsuario(mail);

                                    Intent i=new Intent(ActivityRegistrar.this, InicioActivity.class);

                                    startActivity(i);

                                    finish();
                                }
                            }
                        });

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
        }
    }


