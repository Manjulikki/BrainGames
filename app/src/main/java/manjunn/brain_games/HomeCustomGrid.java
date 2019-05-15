package manjunn.brain_games;

/**
 * Created by manjunn on 10/17/2015.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeCustomGrid extends BaseAdapter {
    private Context mContext;
    private final String[] web;
    private final int[] imageid;
    int textId, imageId;
    int gridName;

    public HomeCustomGrid(Context c, String[] web, int[] Image, int textIds, int imageIds, int grid) {
        mContext = c;
        this.imageid = Image;
        this.web = web;
        textId = textIds;
        imageId = imageIds;
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
            TextView textView;
            ImageView imageView;
            if (web.length!=0){
                textView = (TextView) grid.findViewById(textId);
                if (imageid[position]==2131099776) textView.setTextColor(mContext.getResources().getColor(R.color.black));
                else textView.setTextColor(mContext.getResources().getColor(R.color.white));
                textView.setText(web[position]);
            }
            imageView = (ImageView) grid.findViewById(imageId);
            imageView.setImageResource(imageid[position]);


        return grid;

//        View grid;
//        if (convertView == null) {
//            LayoutInflater inflater = (LayoutInflater) mContext
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            grid = inflater.inflate(gridName, null);
//        } else {
//            grid = (View) convertView;
//        }
//        TextView imageButton = (TextView) grid.findViewById(R.id.deleteCity);
//        imageButton.setTag(position);
//        imageButton.setOnClickListener(clickListener);
//
//        TextView textView1, textView2, textView3, textView4;
//        textView1 = (TextView) grid.findViewById(textId1);
//        textView2 = (TextView) grid.findViewById(textId2);
//        textView3 = (TextView) grid.findViewById(textId3);
//        textView4 = (TextView) grid.findViewById(textId4);
//        textView1.setText(text1[position]);
//        textView2.setText(text2[position]);
//        if (text3 != null && !text3.equals("")) {
//            imageButton.setVisibility(View.INVISIBLE);
//            textView3.setText(text3[position]);
//        }
//        if (text4 != null && !text4.equals(""))
//            textView4.setText(text4[position]);
//        return grid;
    }
}

