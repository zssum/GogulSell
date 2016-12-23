package team7.sa43.gogulsell;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CreatePost extends AppCompatActivity
{
    String userId;
    String itemId;
    Button createBtn;
    Button cancelBtn;
    EditText itemName;
    Spinner category;
    EditText price;
    EditText contact;
    EditText description;
    Button deleteBtn;
    ImageView image;
    ByteArrayInputStream bs;

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


        createBtn = (Button) findViewById(R.id.buttonCreate);
        cancelBtn = (Button) findViewById(R.id.buttonCancel);
        deleteBtn = (Button) findViewById(R.id.buttonDelete);

        itemName = (EditText) findViewById(R.id.editTextItemName);
        category = (Spinner) findViewById(R.id.spinner1);
        price = (EditText) findViewById(R.id.editTextPrice);
        contact = (EditText) findViewById(R.id.editTextContact);
        description = (EditText) findViewById(R.id.editTextDescription);

        image=(ImageView) findViewById(R.id.imageView);

        image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 999);
                }
            }
        });


        if (!isOwner)
        {
            createBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    //Item i = new Item("1", );
                    if(validationCheckPass()){
                        Item i1 = new Item("1", userId, itemName.getText().toString(), category.getSelectedItem().toString(),
                                price.getText().toString(), "Available", description.getText().toString(), contact.getText().toString());

                        new AsyncTask<Item, Void, String>() {
                            @Override
                            protected String doInBackground(Item... params) {
                                Log.d("DEBUG CREATE", params.toString());
                                return Item.createItem(params[0]);


                            }
                            @Override
                            protected void onPostExecute(String result) {
                                Log.d("INFO","oncreate result id is"+result);
                                Toast.makeText(getApplicationContext(), "Item listing created!", Toast.LENGTH_LONG).show();
                                if(bs!=null){
                                    new CloudinaryUpload().execute(result);
                                }
                                finish();
                            }
                        }.execute(i1);
                    }

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
                    new DownloadImageTask(image)
                            .execute("http://res.cloudinary.com/dzujeyavy/image/upload/v1482414489/"+itemId+"%0A.png");
                    new DownloadImageTask(image)
                            .execute("http://res.cloudinary.com/dzujeyavy/image/upload/v1482414489/"+itemId+".png");

                }
            }.execute(itemId);



            deleteBtn.setVisibility(View.VISIBLE);

            createBtn.setText("Edit Post");

            createBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(validationCheckPass()){
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
                                if(bs!=null) new CloudinaryUpload().execute(itemId);
                                setResult(RESULT_OK, null);
                                finish();
                            }
                        }.execute(i);
                    }



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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 999 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            image.setImageBitmap(imageBitmap);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();
            bs = new ByteArrayInputStream(bitmapdata);
        }
    }

    public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            if(result!=null){
                bmImage.setImageBitmap(result);
            }
        }
    }


    private class CloudinaryUpload extends AsyncTask<String,Void,Void>{
        Cloudinary cloudinary = new Cloudinary("cloudinary://249349252536743:BQXR6x7hmYNSO0u2M2kZu9_3gDM@dzujeyavy");
        @Override
        protected Void doInBackground(String... strings) {
            try {
                Log.d("INFO","cloudinary uploading");
                cloudinary.uploader().upload(bs, ObjectUtils.asMap("public_id", strings[0]));
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("INFO","cloudinary uploading failed"+StackTrace.trace(e));
            }
            return null;
        }
    }

    private boolean validationCheckPass(){
        if(itemName.getText().toString().length()<1||price.getText().toString().length()<1||
                contact.getText().toString().length()<1||description.getText().toString().length()<1){
            Toast.makeText(CreatePost.this,"Please ensure there are no empty fields",Toast.LENGTH_SHORT).show();
            return false;
        } else {
            try {
                Integer.parseInt( contact.getText().toString());
                Double.parseDouble(price.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(CreatePost.this,"Please Price and Phone Numbers are correct",Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
}
