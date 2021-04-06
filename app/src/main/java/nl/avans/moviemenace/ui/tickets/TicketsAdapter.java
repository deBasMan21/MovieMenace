package nl.avans.moviemenace.ui.tickets;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nl.avans.moviemenace.R;
import nl.avans.moviemenace.dataLayer.DatabaseConnection;
import nl.avans.moviemenace.dataLayer.factory.DAOFactory;
import nl.avans.moviemenace.dataLayer.factory.SQLDAOFactory;
import nl.avans.moviemenace.domain.Account;
import nl.avans.moviemenace.domain.Cinema;
import nl.avans.moviemenace.domain.Movie;
import nl.avans.moviemenace.domain.Room;
import nl.avans.moviemenace.domain.Ticket;
import nl.avans.moviemenace.domain.Viewing;
import nl.avans.moviemenace.logic.MovieEntityManager;
import nl.avans.moviemenace.logic.MovieManager;
import nl.avans.moviemenace.logic.TicketManager;
import nl.avans.moviemenace.logic.ViewingManager;
import nl.avans.moviemenace.ui.TicketDetailActivity;

public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.TicketsViewHolder> {
    private DAOFactory daoFactory = new SQLDAOFactory();
    private TicketManager ticketManager = new TicketManager(daoFactory);
    private ViewingManager viewingManager = new ViewingManager(daoFactory);
    private MovieManager movieManager = new MovieManager(daoFactory);

    private List<Ticket> tickets;
    private List<Viewing> viewings;
    private List<Movie> movies;

    private Account account;

    public TicketsAdapter(Account account) {
        tickets = new ArrayList<>();
        this.account = account;
        new LoadTickets().execute(this.account);
    }

    public static class TicketsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context context;
        private Ticket ticket;

        private TextView mTitleTv;
        private TextView mLocationTv;
        private TextView mRowSeatTv;
        private TextView mDateTimeTv;

        public TicketsViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            itemView.setOnClickListener(this);

            mTitleTv = itemView.findViewById(R.id.tv_ticket_viewholder_title);
            mLocationTv = itemView.findViewById(R.id.tv_ticket_viewholder_location);
            mRowSeatTv = itemView.findViewById(R.id.tv_ticket_viewholder_rowseat);
            mDateTimeTv = itemView.findViewById(R.id.tv_ticket_viewholder_datetime);
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
        Ticket ticket = tickets.get(position);
        holder.ticket = ticket;
        Room room = viewings.get(position).getRoom();
        Cinema cinema = room.getCinema();
        holder.mTitleTv.setText(movies.get(position).getTitle());
        holder.mLocationTv.setText(cinema.getName() + " - " + Resources.getSystem().getString(R.string.room_number) + room.getRoomNumber());
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public class LoadTickets extends AsyncTask<Account, Void, Void> {
        @Override
        protected Void doInBackground(Account... accounts) {

            Account account = accounts[0];
            List<Ticket> ticketList = new ArrayList<>();
            List<Viewing> viewingList = new ArrayList<>();
            List<Movie> movieList = new ArrayList<>();

            if (account == null) {
                ticketList = ticketManager.getAllTicketsForAccount("");
            } else {
                ticketList = ticketManager.getAllTicketsForAccount(account.getEmail());
                for (Ticket t : ticketList) {
                    Log.e("TicketAdapter", "doInBackground: " + t.getViewID());
                    Viewing viewing = viewingManager.getViewing(t.getViewID());
                    viewingList.add(viewing);
                    Movie movie = movieManager.getMovie(null, viewing.getMovie());
                    movieList.add(movie);
                }
            }

            tickets = ticketList;
            viewings = viewingList;
            movies = movieList;

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            notifyDataSetChanged();
        }
    }
}
