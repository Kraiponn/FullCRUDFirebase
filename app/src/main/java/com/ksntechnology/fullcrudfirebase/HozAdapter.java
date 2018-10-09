package com.ksntechnology.fullcrudfirebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class HozAdapter extends RecyclerView.Adapter<HozAdapter.hozViewHolder> {
    private Context mCOntext;
    private List<HozItem> mItem;


    public HozAdapter(Context mCOntext, List<HozItem> mItem) {
        this.mCOntext = mCOntext;
        this.mItem = mItem;
    }

    @NonNull
    @Override
    public hozViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                      int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCOntext);
        View view = inflater.inflate(
                R.layout.custom_horizontal_item, parent, false
        );

        return new hozViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull hozViewHolder holder, int position) {
        HozItem item = mItem.get(position);
        //hozViewHolder vHolder = (HozAdapter.hozViewHolder) holder;

        holder.textProductName.setText(item.getProductName());
    }

    /*@Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,
                                 int position) {
        HozItem item = mItem.get(position);
        hozViewHolder vHolder = (HozAdapter.hozViewHolder) holder;

        vHolder.textProductName.setText(item.getProductName());
    }*/

    @Override
    public int getItemCount() {
        return mItem.size();
    }



    public class hozViewHolder extends RecyclerView.ViewHolder {
        public TextView textProductName;

        public hozViewHolder(View itemView) {
            super(itemView);
            textProductName = itemView.findViewById(R.id.textProductName);
        }
    }

}
