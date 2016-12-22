package team7.sa43.gogulsell;

import android.app.*;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewCategories extends AppCompatActivity
{
    ImageButton imgBtnClothing;
    ImageButton imgBtnBooks;
    ImageButton imgBtnElectronics;
    ImageButton imgBtnFurniture;
    Intent intent;
    FloatingActionButton fab;
    String userId;
    TextView userTextView;
    //TODO: Remove

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_categories);
        userId = getIntent().getExtras().getString("userId");
        Log.d("Check User:", userId);

        imgBtnClothing = (ImageButton) findViewById(R.id.imageButtonClothing);
        imgBtnBooks = (ImageButton) findViewById(R.id.imageButtonBooks);
        imgBtnElectronics = (ImageButton) findViewById(R.id.imageButtonElectronics);
        imgBtnFurniture = (ImageButton) findViewById(R.id.imageButtonFurniture);
        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        userTextView = (TextView) findViewById(R.id.textView2) ; //TODO: Remove
        userTextView.setText(userId);//TODO: Remove

        imgBtnClothing.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                intent = new Intent(getApplicationContext(), ListPostsActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("category","clothing");
                startActivity(intent);
            }
        });

        imgBtnBooks.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                intent = new Intent(getApplicationContext(), ListPostsActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("category","books");
                startActivity(intent);
            }
        });
        imgBtnElectronics.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                intent = new Intent(getApplicationContext(), ListPostsActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("category","electronics");
                startActivity(intent);
            }
        });
        imgBtnFurniture.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                intent = new Intent(getApplicationContext(), ListPostsActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("category","furniture");
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                intent = new Intent(getApplicationContext(), CreatePost.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                intent = new Intent(getApplicationContext(), CreatePost.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
                return true;
            case R.id.item2:
                intent = new Intent(getApplicationContext(), ViewMyPosts.class);
                intent.putExtra("userId",userId);
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
}
