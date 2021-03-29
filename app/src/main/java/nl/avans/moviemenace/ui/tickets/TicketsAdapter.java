package nl.avans.moviemenace.ui.tickets;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import nl.avans.moviemenace.R;

public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.TicketsViewholder> {
    public static class TicketsViewholder extends RecyclerView.ViewHolder {

        public TicketsViewholder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public TicketsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_ticket, parent, false);

        return new TicketsViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketsViewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
