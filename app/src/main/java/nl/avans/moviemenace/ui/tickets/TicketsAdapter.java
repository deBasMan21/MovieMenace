package nl.avans.moviemenace.ui.tickets;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import nl.avans.moviemenace.R;
import nl.avans.moviemenace.ui.TicketDetailActivity;

public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.TicketsViewHolder> {
    public static class TicketsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context context;

        public TicketsViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            context.startActivity(new Intent(context, TicketDetailActivity.class));
        }
    }

    @NonNull
    @Override
    public TicketsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_ticket, parent, false);

        return new TicketsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
