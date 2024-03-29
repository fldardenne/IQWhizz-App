package com.example.iqwhizz;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.iqwhizz.DAO.FriendshipDAO;
import com.example.iqwhizz.Objects.User;

import java.util.ArrayList;

public class SentAdapter extends RecyclerView.Adapter<SentAdapter.MyViewHolder> {

    private ArrayList<String> items;

    public SentAdapter(ArrayList<String> to_display){
        super();
        this.items = to_display;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_sent, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String elem = items.get(position);
        holder.display(elem);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView username;
        private final Button delete_btn;

        private String current;

        public MyViewHolder(final View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.sent_name);
            delete_btn = (Button) itemView.findViewById(R.id.sent_delete_btn);
            delete_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean bool = FriendshipDAO.deleteFriendship(User.currentUser.getUsername(), username.getText().toString());
                    if (!bool) {
                        FriendshipDAO.deleteFriendship(username.getText().toString(), User.currentUser.getUsername());
                    }
                    removeItem(items.indexOf(username.getText().toString()));
                }
            });
        }

        public void display(String elem) {
            current = elem;
            username.setText(current);
        }
    }


    public void updateData(ArrayList<String> newElements) {
        clear();
        items.addAll(newElements);
        notifyDataSetChanged();
    }

    public void addItem(String str) {
        items.add(0, str);
        notifyItemInserted(0);
    }

    private void clear() {
        final int size = items.size();
        items.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void removeItem(int position) {
        items.remove(position);
        //recycler.removeViewAt(position);
        this.notifyItemRemoved(position);
        this.notifyItemRangeChanged(position, items.size());
    }
}
