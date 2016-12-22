package team7.sa43.gogulsell;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class ListPostsActivity extends ListActivity
{
    String userId;
    Intent intent;
    String category;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        userId = getIntent().getExtras().getString("userId");
        category = getIntent().getExtras().getString("category");
        Log.d("Check User:", userId);

        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);

        new AsyncTask<String, Void, List<Item>>()
        {
            @Override
            protected List<Item> doInBackground(String... params)
            {
                Log.d("DO in BACK", "success");
                List<Item> listItem = Item.listItemsByCategory(params[0]);
                Log.d("DO in BACK", listItem.toString());
                return listItem;
            }

            @Override
            protected void onPostExecute(List<Item> result)
            {
                //tv.setText(result.toString());
                Log.d("TESTTEST", result.toString());
                MyAdapter adapter = new MyAdapter(ListPostsActivity.this, R.layout.row, result);
                setListAdapter(adapter);
            }
        }.execute(category);
    }

    @Override
    protected void onListItemClick(ListView l, View v,
                                   int position, long id)
    {
        Item item = (Item) getListAdapter().getItem(position);
        //Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), OneItemDetail.class);
        intent.putExtra("userId", item.get("userId"));
        intent.putExtra("itemId", item.get("itemId"));
//        Log.d("User ID", item.get("userId"));
//        Log.d("Item ID", item.toString());
        startActivity(intent);
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

}
