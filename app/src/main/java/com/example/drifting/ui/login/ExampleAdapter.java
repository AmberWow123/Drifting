package com.example.drifting.ui.login;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drifting.R;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private ArrayList<ExampleItem> exampleList;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView text_name;
        public TextView text_message;
        public TextView text_time;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_friend_image);
            text_name = itemView.findViewById(R.id.textView_friend_name);
            text_message = itemView.findViewById(R.id.textView_recent_message);
            text_time = itemView.findViewById(R.id.textView_time);
        }
    }

    public ExampleAdapter(ArrayList<ExampleItem> cur_exampleList) {
        exampleList = cur_exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        ExampleItem currentItem = exampleList.get(position);

        // setting info into the holder
        holder.imageView.setImageResource(currentItem.getFriendImage());
        holder.text_name.setText(currentItem.getName());
        holder.text_message.setText(currentItem.getText());
        holder.text_time.setText(currentItem.getTime());
    }

    @Override
    public int getItemCount() {
        return exampleList.size();
    }

    public void filterList(ArrayList<ExampleItem> filteredList) {
        exampleList = filteredList;
        notifyDataSetChanged();
    }

}
