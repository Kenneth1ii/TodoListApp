package com.example.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


// Responsible for displaying data from the model into a row in the recycle view , Extend viewholder here , important that we define it first.

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    public interface onClickListener {
        void onItemClicked(int position );
    }

    public interface OnLongClickListener {
        void onItemLongClicked(int position);
    }

    List<String> items;
    OnLongClickListener longClickListener;
    onClickListener clickListener;


    // Constructor to pass in Data to ItemAdapter.Viewholder
    public ItemAdapter(List<String> items, OnLongClickListener longClickListener, onClickListener clickListener) {
        this.items = items;
        this.longClickListener = longClickListener;
        this.clickListener = clickListener;
    }





    //methods for Recyclerview.Adapter , required onCreate, OnBindViewHolder, getItemCount
    @NonNull
    @Override
    // responsible for creating EACH view.
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Use layout inflator into inflate a (VIEW)                // Builtin XML file of view creating , Route, attach.
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        // wrap it inside a View Holder and return it
        return new ViewHolder(todoView);    // All passed into Viewholder.
    }
    @Override
    // Responsible for binding data to (particular view holder)!
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Grab @ particular position.
        String item = items.get(position);

        // Bind the item in specific view holder
        holder.bind(item); // write inside View holder Class.
    }
    @Override
    public int getItemCount() {
        return items.size();
    }








    // container provide easy access to view of each row of the list
    class ViewHolder extends RecyclerView.ViewHolder {  // viewholder

        TextView tvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1); // simple list XML builtin
        }

        // Update the view inside of the holder with this data
        public void bind(String item) {     // method for bind.
            tvItem.setText(item);
            tvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClicked(getAdapterPosition());
                }
            });
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    // Notify the listener that this position was long pressed.
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
        }
    }

}
