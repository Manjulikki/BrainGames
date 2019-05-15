package manjunn.brain_games;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by manjunn on 8/27/2016.
 */
public class TouchGamesCustomGrid extends BaseAdapter {

    private Context mContext;
    private final int[] imageid;
    int imageId;
    int gridName;

    public TouchGamesCustomGrid(Context c, int[] Image, int id, int grid) {
        mContext = c;
        this.imageid = Image;
        imageId=id;
        gridName = grid;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return imageid.length;
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
        ImageView imageView;
        imageView = (ImageView) grid.findViewById(imageId);
        if (imageid[position]!=-1)
        imageView.setImageResource(imageid[position]);
        return grid;
    }
}

