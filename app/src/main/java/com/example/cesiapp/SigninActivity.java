package com.example.cesiapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

//Login d'un utilisateur, il est déjà inscrit.
public class SigninActivity extends Activity {

    EditText username;
    EditText pwd;
    ProgressBar pg;
    Button btn;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_signin);

        //Récupération du contenu des champs dans la vue
        username = (EditText) findViewById(R.id.signin_username);
        pwd = (EditText) findViewById(R.id.signin_pwd);
        pg = (ProgressBar) findViewById(R.id.signin_pg);
        btn = (Button) findViewById(R.id.signin_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Lors d'un clic pour se connecter
                new SigninAsyncTask(v.getContext()).execute(username.getText().toString(), pwd.getText().toString());
            }
        });
        //
        findViewById(R.id.signin_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), SignupActivity.class);
                startActivity(i);
            }
        });
    }

    //Barre de chargement, on l'affiche dans le cas où il y a un traitement en cours
    private void loading(boolean loading) {
        if(loading){
            pg.setVisibility(View.VISIBLE);
            btn.setVisibility(View.INVISIBLE);
        } else {
            pg.setVisibility(View.INVISIBLE);
            btn.setVisibility(View.VISIBLE);
        }
    }

    //Envoie les données du login à l'api et reste en standby jusqu'au retour de l'API
    protected class SigninAsyncTask extends AsyncTask<String, Void, String>{

        Context context;

        //On fait appel à cette fonction pour
        public SigninAsyncTask(final Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {
            //Vérification de la connexion internet avant l'envoi à l'api
            if(!NetworkHelper.isInternetAvailable(context)){
                return null;
            }

            try {
                //On passe le login et le mdp par un tableau indexé en post à l'API
                Map<String, String> p = new HashMap<>();
                p.put("username", params[0]);
                p.put("pwd", params[1]);

                //On va taper sur l'API
                HttpResult result = NetworkHelper.doPost(context.getString(R.string.url_signin), p, null);

                if(result.code == 200) {
                    // Parse le JSON retourné en string
                    return JsonParser.getToken(result.json);
                }
                return null;
            } catch (Exception e){
                //Le post n'est pas passé, envoi de message d'erreur
                Log.d("CesiApp", "Erreur dans l'envoi des données de connexion : ", e);
                return null;
            }
        }

        //Ce qui va s'executer lorsque l'on a un retour du signin
        @Override
        public void onPostExecute(final String token){
            loading(false);
            if(token != null){
                //Déclaration d'une intention
                Intent i = new Intent(context, TchatActivity.class);
                i.putExtra("CesiApp", token);
                startActivity(i);
            } else {
                //Affiche à l'utilisateur le message d'erreur si la connexion foire
                Toast.makeText(context, context.getString(R.string.error_login), Toast.LENGTH_LONG).show();
            }
        }
    }
}