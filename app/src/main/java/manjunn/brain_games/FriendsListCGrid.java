package manjunn.brain_games;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by manjunn on 9/11/2016.
 */
public class FriendsListCGrid extends BaseAdapter {

    private Context mContext;
    private final String[] web,cities;
    private final Bitmap[] imageid;
    int textId, imageId,citiesId;
    int gridName;

    public FriendsListCGrid(Context c, String[] text, String[] cities, Bitmap[] images, int textIds, int cityId, int imageIds, int grid) {
        mContext = c;
        this.imageid = images;
        this.web = text;
        this.cities=cities;
        textId = textIds;
        imageId = imageIds;
        citiesId=cityId;
        gridName = grid;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return web.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(gridName, null);
        } else {
            grid = (View) convertView;
        }
        TextView textView, city;
        ImageView imageView;
        if (web.length!=0){
            textView = (TextView) grid.findViewById(textId);
            city = (TextView) grid.findViewById(citiesId);
            textView.setText(web[position]);
            city.setText(cities[position]);
        }
        imageView = (ImageView) grid.findViewById(imageId);
        imageView.setImageBitmap(imageid[position]);
        return grid;
    }
}
