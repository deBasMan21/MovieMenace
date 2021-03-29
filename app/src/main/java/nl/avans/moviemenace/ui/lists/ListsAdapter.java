package nl.avans.moviemenace.ui.lists;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import nl.avans.moviemenace.ListDetailActivity;
import nl.avans.moviemenace.R;

public class ListsAdapter extends RecyclerView.Adapter<ListsAdapter.ListsViewHolder> {
    public static class ListsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context context;

        private TextView mTitleTv;

        public ListsViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            itemView.setOnClickListener(this);

            mTitleTv = itemView.findViewById(R.id.tv_list_viewholder_title);
        }

        @Override
        public void onClick(View v) {
            context.startActivity(new Intent(context, ListDetailActivity.class));
        }
    }

    @NonNull
    @Override
    public ListsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_list, parent, false);
        return new ListsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
