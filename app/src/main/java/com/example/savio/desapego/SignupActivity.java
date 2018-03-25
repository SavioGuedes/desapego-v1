package com.example.savio.desapego;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.savio.desapego.api.model.ProfileAttributes;
import com.example.savio.desapego.api.model.Register;
import com.example.savio.desapego.helpers.AuthHelper;
import com.example.savio.desapego.retrofit.ApiService;

public class SignupActivity extends AppCompatActivity {

    TextView possui_conta;
    private Button btn_cadastrar;
    private EditText tv_senha;
    private EditText tv_email;
    private EditText tv_nome;
    private ApiService apiService;
    private AuthHelper loginHelper;

//------------------clico de vida-------------------------------------------------------------------//


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
//        apiService = ServiceGenerator.createService(ApiService.class);
        possui_conta = (TextView) findViewById(R.id.signup_possui_conta);
        btn_cadastrar = (Button) findViewById(R.id.signup_btn_cadastrar);
        tv_nome = (EditText) findViewById(R.id.signup_nome);
        tv_email = (EditText) findViewById(R.id.signup_email);
        tv_senha = (EditText) findViewById(R.id.signup_senha);

//------------------Listeners-----------------------------------------------------------------------//

        btn_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tv_nome.getText().toString().equals("") &&
                        !tv_email.getText().toString().equals("") &&
                        !tv_senha.getText().toString().equals("")) {

                    Register new_user = new Register();
                    ProfileAttributes new_user_profile = new ProfileAttributes();
                    new_user_profile.setName(tv_nome.getText().toString());
                    new_user.setEmail(tv_email.getText().toString());
                    new_user.setPassword(tv_senha.getText().toString());
                    new_user.setPasswordConfirmation(tv_senha.getText().toString());
                    new_user.setProfileAttributes(new_user_profile);
                    createAccount();

                } else {
                    Log.i("LISTA", "Erro: " + "Campos obrigatorios");
                }
            }
        });

        possui_conta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                finish();
            }
        });


    }//fim do onCreate


    public void createAccount() {
        //validação de login api nativa
        final String senha = ((EditText) findViewById(R.id.login_senha)).getText().toString();
        final String email = ((EditText) findViewById(R.id.login_email)).getText().toString();
        loginHelper = new AuthHelper();
        Intent intent = new Intent(this, MainActivity.class);
        loginHelper.createAccount(this, intent, email, senha);

    }
    //------------------fim de codigo-------------------------------------------------------------------//
}
