package team7.sa43.gogulsell;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class OneItemDetail extends AppCompatActivity
{

    String userId;
    String itemId;
    Uri uri;
    Item i;

    TextView textViewItemName;
    TextView textViewCategory;
    TextView textViewPrice;
    TextView textViewContact;
    TextView textViewDescription;
    Button cancelBtn;
    Button callBtn;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_item_detail);
        userId = getIntent().getExtras().getString("userId");
        Log.d("Check User:", userId);
        itemId = getIntent().getExtras().getString("itemId");

        textViewItemName = (TextView) findViewById(R.id.textViewItemName);
        textViewCategory = (TextView) findViewById(R.id.textViewCategory);
        textViewPrice = (TextView) findViewById(R.id.textViewPrice);
        textViewContact = (TextView) findViewById(R.id.textViewContact);
        textViewDescription = (TextView) findViewById(R.id.textViewDescription);
        cancelBtn = (Button) findViewById(R.id.buttonCancel);
        callBtn =(Button) findViewById(R.id.buttonCall);
        image= (ImageView) findViewById(R.id.imageView);


        new AsyncTask<String, Void, Item>() {
            @Override
            protected Item doInBackground(String... params) {
                return Item.getItemById(params[0]);
            }
            @Override
            protected void onPostExecute(Item result) {
                i = result;
                textViewItemName.setText(i.get("itemName"));
                textViewCategory.setText(i.get("category"));
                textViewPrice.setText(i.get("price"));
                textViewContact.setText(i.get("contact"));
                textViewDescription.setText(i.get("description"));
                new CreatePost.DownloadImageTask(image)
                        .execute("http://res.cloudinary.com/dzujeyavy/image/upload/v1482414489/"+itemId+"%0A.png");
                new CreatePost.DownloadImageTask(image)
                        .execute("http://res.cloudinary.com/dzujeyavy/image/upload/v1482414489/"+itemId+".png");
            }
        }.execute(itemId);




        callBtn.setOnClickListener(new View.OnClickListener()
       {
           @Override
           public void onClick(View view)
           {
               uri = Uri.parse("tel:" + i.get("contact"));
               Intent intent = new Intent(Intent.ACTION_DIAL, uri);
               startActivity(intent);
           }
       });

        cancelBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                OneItemDetail.super.onBackPressed();
            }
        });
    }
}
