package com.example.tim.gentleresolve.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;

import com.example.tim.gentleresolve.Constants;
import com.example.tim.gentleresolve.main_ui.AchievementsActivity;
import com.example.tim.gentleresolve.models.Achievement;
import com.example.tim.gentleresolve.models.Vision;
import com.example.tim.gentleresolve.util.OnStartDragListener;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.parceler.Parcels;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.Collections;

public class FirebaseVisionsListAdapter extends FirebaseRecyclerAdapter<Vision, FirebaseVisionsViewHolder> {
    private DatabaseReference mRef;
    private ChildEventListener mChildEventListener;
    private Context mContext;
    private ArrayList<Vision> mVisions = new ArrayList<>();

    public FirebaseVisionsListAdapter(Class<Vision> modelClass, int modelLayout,
                                     Class<FirebaseVisionsViewHolder> viewHolderClass,
                                     Query ref, OnStartDragListener onStartDragListener, Context context) {

        super(modelClass, modelLayout, viewHolderClass, ref);
        mRef = ref.getRef();
        mContext = context;

        mChildEventListener = mRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mVisions.add(dataSnapshot.getValue(Vision.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void populateViewHolder(final FirebaseVisionsViewHolder viewHolder, Vision model, final int position) {
        viewHolder.bindVision(model);

//        viewHolder.mDoneButton.setOnClickListener(new View.OnClickListener() {
//
//        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String achievement = mVisions.toString();
                Intent intent = new Intent(mContext, AchievementsActivity.class);
                saveToFirebase(achievement);
                onItemDelete(position);
                intent.putExtra("position", viewHolder.getAdapterPosition());
                intent.putExtra("achievement", Parcels.wrap(mVisions));
                mContext.startActivity(intent);
            }
        });

    }

    public void onItemDelete(int position) {
        mVisions.remove(position);
        getRef(position).removeValue();
    }

    @Override
    public void cleanup() {
        super.cleanup();
        mRef.removeEventListener(mChildEventListener);
    }

    private void saveToFirebase(String achievement) {
        Achievement mAchievement = new Achievement(achievement);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        DatabaseReference mRef = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_VISIONS)
                .child(uid);

        DatabaseReference pushRef = mRef.push();
        String pushId = pushRef.getKey();
        mAchievement.setPushId(pushId);
        pushRef.setValue(mAchievement);

        mRef.push().setValue(mAchievement);
    }

}
