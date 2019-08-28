package com.exomatik.mpm.mpm.Adapter;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exomatik.mpm.mpm.Activity.BuatPembelajaran;
import com.exomatik.mpm.mpm.Activity.ListPembelajaran;
import com.exomatik.mpm.mpm.Featured.UserPreference;
import com.exomatik.mpm.mpm.Model.ModelBelajar;
import com.exomatik.mpm.mpm.Model.ModelVisi;
import com.exomatik.mpm.mpm.R;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;

import java.util.ArrayList;

/**
 * Created by IrfanRZ on 29/12/2018.
 */

class ViewHolderWithouChild extends RecyclerView.ViewHolder{
    public TextView textView;

    public ViewHolderWithouChild(View itemView){
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.textView);
    }
}

class ViewHolderWithChild extends RecyclerView.ViewHolder{
    public TextView textView, textViewChild, textDesc;
    public RelativeLayout button;
    public ExpandableLinearLayout expandableLayout;
    public ImageView btnEdit;

    public ViewHolderWithChild(View itemView){
        super(itemView);

        textView = (TextView) itemView.findViewById(R.id.textView);
        textViewChild = (TextView) itemView.findViewById(R.id.text_isi);
        textDesc = (TextView) itemView.findViewById(R.id.text_desc);
        button = (RelativeLayout) itemView.findViewById(R.id.button);
        btnEdit = (ImageView) itemView.findViewById(R.id.btn_edit);
        expandableLayout = (ExpandableLinearLayout) itemView.findViewById(R.id.expandableLayout);
    }
}

public class AdapterBelajar extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<ModelBelajar> items;
    Context context;
    Activity activity;
    SparseBooleanArray expandState = new SparseBooleanArray();
    UserPreference userPreference;

    public AdapterBelajar(ArrayList<ModelBelajar> items, Activity activity) {
        this.items = items;
        this.activity = activity;
        for (int i = 0; i < items.size(); i++){
            expandState.append(i, false);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position).isExpandable()){
            return 1;
        }
        else {
            return 0;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        if (viewType == 0){
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.layout_without_child, parent, false);
            return new ViewHolderWithouChild(view);
        }else {
            LayoutInflater inflater = LayoutInflater.from(context);
            userPreference = new UserPreference(context);
            View view = inflater.inflate(R.layout.layout_with_child_belajar, parent, false);
            return new ViewHolderWithChild(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()){
            case 0:{
                ViewHolderWithouChild viewHolder = (ViewHolderWithouChild) holder;
                ModelBelajar item = items.get(position);
                viewHolder.setIsRecyclable(false);
                viewHolder.textView.setText(item.getNama());
            }
            break;
            case 1:{
                final ViewHolderWithChild viewHolder = (ViewHolderWithChild) holder;
                ModelBelajar item = items.get(position);
                viewHolder.setIsRecyclable(false);
                viewHolder.textView.setText(item.getNama());

                viewHolder.expandableLayout.setInRecyclerView(true);
                viewHolder.expandableLayout.setExpanded(expandState.get(position));
                viewHolder.expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
                    @Override
                    public void onPreOpen() {
                        changeRotate(viewHolder.button, 0f, 180f).start();
                        expandState.put(position, true);
                    }

                    @Override
                    public void onPreClose() {
                        changeRotate(viewHolder.button, 180f, 0f).start();
                        expandState.put(position, false);
                    }
                });

                viewHolder.button.setRotation(expandState.get(position)?180f:0f);
                viewHolder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewHolder.expandableLayout.toggle();
                    }
                });

                if (userPreference.getKEY_USER() != null){
                    if (userPreference.getKEY_USER().toString().equals("Admin")){
                        viewHolder.btnEdit.setVisibility(View.VISIBLE);
                        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                BuatPembelajaran.kategoriPembelajaran = ListPembelajaran.kategoriPembelajaran;
                                BuatPembelajaran.position = Integer.toString(position);
                                BuatPembelajaran.dataBelajar = items.get(position);
                                activity.startActivity(new Intent(activity, BuatPembelajaran.class));
                                activity.finish();
                            }
                        });
                    }
                }
                viewHolder.textViewChild.setText(items.get(position).getIsi());
                viewHolder.textDesc.setText(items.get(position).getDesc());
            }
            break;
        }
    }

    private ObjectAnimator changeRotate(RelativeLayout button, float from, float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(button, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
