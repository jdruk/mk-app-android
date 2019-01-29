package br.com.loginphp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText password, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);


    }

    public void onLogin(final View view){
        final String email = this.email.getText().toString();
        String password = this.password.getText().toString();


//        BackgroundWorker backgroundWorker = new BackgroundWorker(this, new BackgroundWorker.AsynResponse() {
//            @Override
//            public void processFinish(String output) {
//               // if(output.equals("sucesso")){
//                    Intent intent = new Intent(view.getContext(), Dashboard.class);
//               //     intent.putExtra("email", email);
//                    startActivity(intent);
//                //} else {
//                 //   Toast.makeText(view.getContext(), "Erro no login", Toast.LENGTH_SHORT).show();
//                //}
//
//            }
//        });
//        backgroundWorker.execute("login", email, password);
        Intent intent = new Intent(view.getContext(), Dashboard.class);
        startActivity(intent);
    }
}
