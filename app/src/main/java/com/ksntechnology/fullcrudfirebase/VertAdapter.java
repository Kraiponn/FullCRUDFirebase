package com.ksntechnology.fullcrudfirebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class VertAdapter extends RecyclerView.Adapter<VertAdapter.vertViewHolder> {
    private Context mCOntext;
    private List<VertItem> mItem;


    public VertAdapter(Context mCOntext, List<VertItem> mItem) {
        this.mCOntext = mCOntext;
        this.mItem = mItem;
    }


    @NonNull
    @Override
    public vertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCOntext);
        View view = inflater.inflate(
                R.layout.custom_vertical_item, parent,false
        );

        return new vertViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull vertViewHolder holder, int position) {
        VertItem item = mItem.get(position);
        //vertViewHolder vHolder = holder;

        holder.textProductTitle.setText(item.getTextSection());
        HozAdapter hAdapter = new HozAdapter(mCOntext, item.getHorizontalItem());
        holder.rcv.setAdapter(hAdapter);
        holder.rcv.setLayoutManager(new LinearLayoutManager(
                mCOntext,
                RecyclerView.HORIZONTAL,
                false
        ));
    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }



    public class vertViewHolder extends RecyclerView.ViewHolder {
        public TextView textProductTitle;
        public RecyclerView rcv;

        public vertViewHolder(View itemView) {
            super(itemView);
            textProductTitle = itemView.findViewById(R.id.textProductTitle);
            rcv = itemView.findViewById(R.id.recyclerView_Hoz);
        }
    }

}
