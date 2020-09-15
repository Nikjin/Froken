package com.example.confesso;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class AnonymousPicSelecter extends AppCompatActivity {

    ImageView back;


    Integer [] imageIds= {
            R.drawable.froken1,
            R.drawable.froken2,
            R.drawable.froken3,
            R.drawable.froken4,
            R.drawable.froken5,
            R.drawable.froken6,
            R.drawable.froken7,
            R.drawable.froken8,
            R.drawable.froken9,

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anonymous_pic_selecter);

        GridView gridView = findViewById(R.id.gridview);
        gridView.setAdapter(new ImageAdapter(this));

        back=findViewById(R.id.back);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent,
                                    View v, int position, long id) {
                int pos= position;
                Intent i=new Intent(getApplicationContext(),AnonymousPostActivity.class);
                i.putExtra("pos",pos);
                startActivity(i);
                finish();

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public class ImageAdapter extends BaseAdapter {
        private Context context;

        public ImageAdapter(Context c) {
            context = c;
        }

        @Override
        public int getCount() {
            return imageIds.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if(convertView==null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(210, 210));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(5, 5, 5, 5);
            }
            else {
                imageView= (ImageView) convertView;

            }
            imageView.setImageResource(imageIds[position]);
            return imageView;
        }
    }

}

