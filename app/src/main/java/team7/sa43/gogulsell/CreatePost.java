package team7.sa43.gogulsell;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CreatePost extends AppCompatActivity
{
    String userId;
    String itemId;
    TextView userTextView;//TODO: Remove
    Button createBtn;
    Button cancelBtn;
    EditText itemName;
    Spinner category;
    EditText price;
    EditText contact;
    EditText description;
    Button deleteBtn;

    Intent intent;

    Boolean isOwner = false;

    Item i;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        userId = getIntent().getExtras().getString("userId");
        itemId = getIntent().getExtras().getString("itemId");
        isOwner = getIntent().getExtras().getBoolean("isOwner");

        Log.d("Check User:", userId);


        userTextView = (TextView) findViewById(R.id.textView3); //TODO: Remove
        userTextView.setText(userId);//TODO: Remove

        createBtn = (Button) findViewById(R.id.buttonCreate);
        cancelBtn = (Button) findViewById(R.id.buttonCancel);
        deleteBtn = (Button) findViewById(R.id.buttonDelete);

        itemName = (EditText) findViewById(R.id.editTextItemName);
        category = (Spinner) findViewById(R.id.spinner1);
        price = (EditText) findViewById(R.id.editTextPrice);
        contact = (EditText) findViewById(R.id.editTextContact);
        description = (EditText) findViewById(R.id.editTextDescription);


        if (!isOwner)
        {
            createBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    //Item i = new Item("1", );

                    Item i1 = new Item("1", userId, itemName.getText().toString(), category.getSelectedItem().toString(),
                            price.getText().toString(), "Available", description.getText().toString(), contact.getText().toString());

                    new AsyncTask<Item, Void, Void>() {
                        @Override
                        protected Void doInBackground(Item... params) {
                            Log.d("DEBUG CREATE", params.toString());
                            Item.createItem(params[0]);

                            return null;
                        }
                        @Override
                        protected void onPostExecute(Void result) {
                            Toast.makeText(getApplicationContext(), "Item listing created!", Toast.LENGTH_LONG).show();

                            finish();
                        }
                    }.execute(i1);
                }
            });


        } else if (isOwner)
        {
            itemId = getIntent().getExtras().getString("itemId");

            new AsyncTask<String, Void, Item>() {
                @Override
                protected Item doInBackground(String... params) {
                    Log.d("DEBUG CREATE", params.toString());
                    return Item.getItemById(params[0]);
                }
                @Override
                protected void onPostExecute(Item result) {
                    i = result;

                    itemName.setText(i.get("itemName"));
                    category.setSelection(getIndex(category, i.get("category")));
                    price.setText(i.get("price"));
                    contact.setText(i.get("contact"));
                    description.setText(i.get("description"));
                }
            }.execute(itemId);



            deleteBtn.setVisibility(View.VISIBLE);

            createBtn.setText("Edit Post");

            createBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    i.put("itemName", itemName.getText().toString());
                    i.put("category", category.getSelectedItem().toString());
                    i.put("price", price.getText().toString());
                    i.put("contact", contact.getText().toString());
                    i.put("description", description.getText().toString());
                    Log.d("Test update obj", i.toString());
                    //TODO: Insert UPDATE METHOD here

                    new AsyncTask<Item, Void, Void>() {
                        @Override
                        protected Void doInBackground(Item... params) {
                            Log.d("DEBUG CREATE", params.toString());
                             Item.updateItem(params[0]);
                            return null;
                        }
                        @Override
                        protected void onPostExecute(Void result) {
                            setResult(RESULT_OK, null);
                            finish();
                        }
                    }.execute(i);


                }
            });

            deleteBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {

                    //TODO: Insert DELETE METHOD here
                    new AsyncTask<Item, Void, Void>() {
                        @Override
                        protected Void doInBackground(Item... params) {
                            Item.deleteItem(params[0]);
                            return null;
                        }
                        @Override
                        protected void onPostExecute(Void result) {
                            setResult(RESULT_OK, null);
                            finish();
                        }
                    }.execute(i);


                }
            });
        }

        cancelBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                CreatePost.super.onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.item1:
                intent = new Intent(getApplicationContext(), CreatePost.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
                return true;
            case R.id.item2:
                intent = new Intent(getApplicationContext(), ViewMyPosts.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
                return true;
            case R.id.item3:
                intent = new Intent(getApplicationContext(), Login.class);
                intent.putExtra("userId", "");
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }
}
