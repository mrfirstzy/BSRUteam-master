package team.bsru.apirat.bsruteam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity { // main calss <
    // ประกาศตัวแปล
    private Button SingInButton, SingUpButton;
    private EditText UserEditText, PassEditText;
    private String userString, passString;
    private String[] loginStrings = new String[8];
    private final String urlphp = "http://swiftcodingthai.com/bsru/get_user_master.php";
    private boolean aBoolean = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind Widget คือการผู้ค่าตัวแปลกัย view ใน XML ไฟล์
        // bind button sing in
        SingInButton = (Button) findViewById(R.id.button);
        // bind button sing up
        SingUpButton = (Button) findViewById(R.id.button3);
        // bind edittext user
        UserEditText = (EditText) findViewById(R.id.editText);
        // bind edittext pass
        PassEditText = (EditText) findViewById(R.id.editText2);

        // Button control
        SingUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //move MainActivity to SingUpActivity
                startActivity(new Intent(MainActivity.this,SingUpActivity.class));
            }
        });

        SingInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check space and get value frome edit text
                userString = UserEditText.getText().toString().trim();
                passString = PassEditText.getText().toString().trim();
                if (userString.equals("") || passString.equals("")) {
                    // have space
                    MyAlert myAlert = new MyAlert(MainActivity.this);
                    myAlert.myDialog("มีช่องว่าง","กรุณากรอกให้ครบ");
                } else {
                    // no space
                    checkUserPass();

                }

            }// on click
        });



    } // main Method
    private void checkUserPass() {
        try {
            GetUser getUser = new GetUser(MainActivity.this);
            getUser.execute(urlphp);
            String strJSON = getUser.get();
            Log.d("16febV1", "strJSON ==>" + strJSON);

            JSONArray jsonArray = new JSONArray(strJSON);
            for (int i=0;i<jsonArray.length();i+=1) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (userString.equals(jsonObject.getString("User"))) {

                    loginStrings[0] = jsonObject.getString("id");
                    loginStrings[1] = jsonObject.getString("Name");
                    loginStrings[2] = jsonObject.getString("User");
                    loginStrings[3] = jsonObject.getString("Password");
                    loginStrings[4] = jsonObject.getString("Image");
                    loginStrings[5] = jsonObject.getString("Avata");
                    loginStrings[6] = jsonObject.getString("Lat");
                    loginStrings[7] = jsonObject.getString("Lng");

                    aBoolean = false;


                } // if

            }   // loop for

            if (aBoolean) {

                MyAlert myAlert = new MyAlert(MainActivity.this);
                myAlert.myDialog("หา User นี่ไม่เจอ ?", "ไม่มี" + userString + "ในฐานข้อมมูลเรา");
            } else if (!passString.equals(loginStrings[3])) {
                //Password False
                MyAlert myAlert = new MyAlert(MainActivity.this);
                myAlert.myDialog("Password False", "พาสเวิร์ดผิด ใส่ใหม่ด้วย ครัช");
            } else {

                Toast.makeText(MainActivity.this, "Welcom" + loginStrings[1],
                Toast.LENGTH_SHORT).show();

            }

        } catch (Exception e) {
            Log.d("16febV1", "e checkUserPass ==>" + e.toString());

        }
    }
} // main class >
