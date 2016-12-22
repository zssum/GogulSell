package team7.sa43.gogulsell;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by edwin on 21/12/16.
 */

public class MyAdapter extends ArrayAdapter<Item>
{

    private List<Item> items;
    int resource;

    public MyAdapter(Context context, int resource, List<Item> items)
    {
        super(context, resource, items);
        this.resource = resource;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(resource, null);
        Item i = items.get(position);
        if (i != null)
        {

            TextView e2 = (TextView) v.findViewById(R.id.textView2);

            e2.setText("\n\t\t\t" + i.get("itemName") + " - " + "$" + i.get("price") + "\n");
            //TODO: Tidy up layout

            //ImageView image = (ImageView) v.findViewById(R.id.imageView2);
            //image.setImageBitmap(Employee.getPhoto(true, eid));
            //TODO: Add images?
        }
        return v;
    }


}
