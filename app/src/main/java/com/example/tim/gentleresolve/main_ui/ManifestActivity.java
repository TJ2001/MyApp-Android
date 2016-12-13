package com.example.tim.gentleresolve.main_ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tim.gentleresolve.R;
import com.example.tim.gentleresolve.api_ui.FindSupportActivity;
import com.example.tim.gentleresolve.api_ui.ResultsListActivity;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ManifestActivity extends AppCompatActivity{
    private DatabaseReference mVisionReference;
    private FirebaseListAdapter mFirebaseListAdapter;

    @Bind(R.id.visionListView) ListView mListView;
    @Bind(R.id.supportButton) Button mSupportButton;

//    private List<String> visions = new ArrayList<>();

    public static final String TAG = CreateActivity.class.getSimpleName();
//TODO fix issue with support button

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_manifest);
        ButterKnife.bind(this);

//        Intent intent = getIntent();
//        visions = intent.getStringArrayListExtra("vision");
//        Log.v(TAG, "intent: " + visions);
//        mListView.setAdapter(new MyListAdapter(this, R.layout.list_item, visions));g
//        visions.add("First Item");
//        visions.add("secoond Item");

        mSupportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManifestActivity.this, FindSupportActivity.class));
            }
        });
    }


    private class MyListAdapter extends ArrayAdapter<String>{
        private int layout;
        public MyListAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            layout = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewholder = null;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.itemTextView = (TextView) convertView.findViewById(R.id.itemTextView);
                viewHolder.doneButton = (Button) convertView.findViewById(R.id.doneButton);
                viewHolder.supportButton = (Button) convertView.findViewById(R.id.supportButton);

                convertView.setTag(viewHolder);
            }
            mainViewholder = (ViewHolder) convertView.getTag();

            mainViewholder.doneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String vision = visions.get(position).toString();
                    Intent intent = new Intent(ManifestActivity.this, AchievementsActivity.class);
                    intent.putExtra("vision", vision);
                    Log.v(TAG, "intent from Done Button: " + intent);
                    startActivity(intent);
                }
            });


            mainViewholder.itemTextView.setText(getItem(position));

            return convertView;
        }
    }

    public class ViewHolder {

        ImageView thumbnail;
        TextView itemTextView;
        Button doneButton;
        Button supportButton;
    }
}