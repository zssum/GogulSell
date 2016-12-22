package team7.sa43.gogulsell;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity
{
    String userId;
    EditText editText;
    Button loginButton;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText);

        loginButton.setOnClickListener(new View.OnClickListener()
        {
           @Override
           public void onClick(View view)
           {
               userId = editText.getText().toString();
               intent = new Intent(getApplicationContext(), ViewCategories.class);
               intent.putExtra("userId",userId);
               startActivity(intent);
           }
        });

    }
}
