package faistdev.at.test_sharingkaindorf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private Button btRegister;
    private EditText etMail;
    private EditText etUsername;
    private EditText etPassword;
    private Database db;
    private boolean saveUserState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegisterClick();
            }
        });
    }

    private void init() {
        btRegister = findViewById(R.id.btRegister);
        etMail=findViewById(R.id.etMail);
        etUsername=findViewById(R.id.etUsername);
        etPassword=findViewById(R.id.etPWD);
        try{
            db=Database.getInstance();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Check if fields are filled out
     * Set userState to false
     * Call db.saveUser(); if it is successful -> set userState to true
     * Wait while userState is false
     */
    private void onRegisterClick(){



        //Check if required fields are filled out
        if(!fieldsFilledOut(etUsername.getText().toString(),etPassword.getText().toString(),etMail.getText().toString())){
            Toast.makeText(getApplicationContext(),"You must fill out all fields!",Toast.LENGTH_LONG).show();
        }else{
            saveUserState=false;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        saveUserState=db.saveUser(etUsername.getText().toString(),etMail.getText().toString(),etPassword.getText().toString());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

            thread.start();

            while (!saveUserState){
                //Waiting for successful insert
            }

            Toast.makeText(getApplicationContext(),"Registration successful!",Toast.LENGTH_LONG).show();

        }
    }

    public boolean fieldsFilledOut(String username, String password, String email){
        if(username.equals("") || password.equals("") || email.equals("")){
            return false;
        }else{
            return true;
        }
    }

}
