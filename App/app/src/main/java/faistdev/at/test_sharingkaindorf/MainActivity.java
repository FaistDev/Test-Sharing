package faistdev.at.test_sharingkaindorf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    /**
     * MainActivity
     */

    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureDatabase();




    }

    private void configureDatabase(){

        /**
         * configureDatabase()
         * If Database Connection is valid -> open registration form
         * Else open Database Configuration
         */

        try{
            db = Database.getInstance();
            if(db.getCon()!=null){
                if(db.getCon().isValid(500)){
                    MainActivity.this.startActivity(new Intent(this,RegisterActivity.class));
                }else{
                    MainActivity.this.startActivity(new Intent(this,ConfDatabaseAct.class));
                }
            }else{
                MainActivity.this.startActivity(new Intent(this,ConfDatabaseAct.class));
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

}
