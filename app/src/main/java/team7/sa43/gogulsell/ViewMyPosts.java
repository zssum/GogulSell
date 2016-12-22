package team7.sa43.gogulsell;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class ViewMyPosts extends ListActivity
{
    String userId;
    Intent intent;
    List<Item> items;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        userId = getIntent().getExtras().getString("userId");
        Log.d("Check User:", userId);

        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);


        //TODO: Change method to retrieve posts by USERID
        new AsyncTask<String, Void, List<Item>>()
        {
            @Override
            protected List<Item> doInBackground(String... params)
            {
                return Item.listItemsByUser(params[0]);
            }

            @Override
            protected void onPostExecute(List<Item> result)
            {
                items = result;
                MyAdapter adapter = new MyAdapter(getApplicationContext(), R.layout.row, items);
                setListAdapter(adapter);
            }
        }.execute(userId);



    }

    @Override
    protected void onListItemClick(ListView l, View v,
                                   int position, long id)
    {
        Item item = (Item) getListAdapter().getItem(position);
        //Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), CreatePost.class);
        intent.putExtra("userId", userId);
        intent.putExtra("itemId", item.get("itemId"));
        intent.putExtra("isOwner", true);
        Log.d("User ID", userId);
        Log.d("Item ID", item.get("itemId"));
        startActivityForResult(intent,1);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK) {

                Intent refresh = new Intent(this, ViewMyPosts.class);
                refresh.putExtra("userId", userId);
                startActivity(refresh);
                this.finish();
            }
    }

}
