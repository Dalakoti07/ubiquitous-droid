package com.example.ubiquitousdroid.activitiesandfragments;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.example.ubiquitousdroid.R;
import com.example.ubiquitousdroid.models.ImageObject;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class FullBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private BottomSheetBehavior mBehavior;
    private ImageView iv_photo;
    private TextView tv_photo_title;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("commonTag","oncreate called of bottomsheet");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        view = View.inflate(getContext(), R.layout.layout_bottomsheet, null);
        Log.d("commonTag","oncreateDialog called of bottomsheet and title is "+getArguments().get("title"));
        iv_photo=view.findViewById(R.id.iv_picture);
        tv_photo_title=view.findViewById(R.id.tv_photo_title);
        setData(new ImageObject("1",getArguments().get("title").toString(),getArguments().get("imageUrl").toString()));

        ConstraintLayout constraintLayout = view.findViewById(R.id.parent_view);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) constraintLayout.getLayoutParams();
        params.height = getScreenHeight();
        constraintLayout.setLayoutParams(params);

        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    private void setData(ImageObject objecty){
        if(view == null){
            Log.d("commonTag"," its null");
            return;
        }
        Log.d("commonTag"," its not null");
        tv_photo_title.setText(objecty.getName());
        Glide
                .with(iv_photo)
                .load(objecty.getUrl())
                .fitCenter()
                .placeholder(R.drawable.ic_photo)
                .into(iv_photo);
    }
}