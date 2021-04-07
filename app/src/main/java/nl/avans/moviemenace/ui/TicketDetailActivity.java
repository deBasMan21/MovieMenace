package nl.avans.moviemenace.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.squareup.picasso.Picasso;

import nl.avans.moviemenace.R;
import nl.avans.moviemenace.dataLayer.factory.DAOFactory;
import nl.avans.moviemenace.dataLayer.factory.SQLDAOFactory;
import nl.avans.moviemenace.domain.Account;
import nl.avans.moviemenace.domain.Cinema;
import nl.avans.moviemenace.domain.Movie;
import nl.avans.moviemenace.domain.Room;
import nl.avans.moviemenace.domain.Ticket;
import nl.avans.moviemenace.domain.Viewing;
import nl.avans.moviemenace.logic.TicketManager;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Hashtable;

public class TicketDetailActivity extends AppCompatActivity {
    private DAOFactory daoFactory = new SQLDAOFactory();
    private TicketManager ticketManager = new TicketManager(daoFactory);

    private Account account;
    private Ticket ticket;
    private Viewing viewing;
    private Movie movie;

    private TextView mTitleTv;
    private TextView mLocationTv;
    private TextView mRowSeatTv;
    private TextView mDateTimeTv;

    private Button mUseBn;

    private ImageView mBarcodeIv;
    private ImageView mBannerIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        Intent intent = getIntent();
        if (intent.hasExtra(Account.ACCOUNT_KEY)) {
            account = (Account) intent.getSerializableExtra(Account.ACCOUNT_KEY);
        }
        if (intent.hasExtra(Ticket.TICKET_KEY)) {
            ticket = (Ticket) intent.getSerializableExtra(Ticket.TICKET_KEY);
        }
        if (intent.hasExtra(FilmDetailActivity.MOVIE_KEY)) {
            movie = (Movie) intent.getSerializableExtra(FilmDetailActivity.MOVIE_KEY);
        }
        if (intent.hasExtra(Viewing.VIEWING_KEY)) {
            viewing = (Viewing) intent.getSerializableExtra(Viewing.VIEWING_KEY);
        }

        mUseBn = findViewById(R.id.bn_ticket_detail_use);

        mBarcodeIv = findViewById(R.id.iv_ticket_detail_barcode);
        mBarcodeIv.setImageBitmap(encodeAsBitmap(ticket.getViewID() + ":" + ticket.getChairNumber() + ":" + ticket.getStatus()));

        mBannerIv = findViewById(R.id.iv_ticket_detail_banner);
        Picasso.get().load(MainActivity.BASE_URL + movie.getBanner()).into(mBannerIv);

        mUseBn.setOnClickListener((View v) -> {
            new AlertDialog.Builder(this)
                    .setTitle("Ticket inleveren")
                    .setMessage("Weet u zeker dat u dit ticket wil gebruiken?")
                    .setPositiveButton("Inleveren", (dialog, which) -> {
                        new UseTicket().execute(ticket);
                        Intent ticketsIntent = new Intent(this, MainActivity.class);
                        ticketsIntent.putExtra(MainActivity.DESTINATION_KEY, "tickets");
                        ticketsIntent.putExtra(Account.ACCOUNT_KEY, account);
                        startActivity(ticketsIntent);
                    })
                    .setNegativeButton("Annuleren", null)
                    .setIcon(R.drawable.ic_menu_tickets)
                    .show();
        });

        mTitleTv = findViewById(R.id.tv_ticket_detail_title);
        mLocationTv = findViewById(R.id.tv_ticket_detail_location);
        mRowSeatTv = findViewById(R.id.ticket_detail_rowseat);
        mDateTimeTv = findViewById(R.id.tv_ticket_detail_datetime);


        Room room = viewing.getRoom();
        Cinema cinema = room.getCinema();
        mTitleTv.setText(movie.getTitle());
        mLocationTv.setText(cinema.getName() + " - " + getString(R.string.room_num) + " " + room.getRoomNumber());
        mRowSeatTv.setText(getString(R.string.row_num) + " " + ticket.getRowNumber() + " - " + getString(R.string.seat_num) + " " + ticket.getChairNumber());
        mDateTimeTv.setText(viewing.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)) + " - " + viewing.getDate().format(DateTimeFormatter.ofPattern("HH:mm")));
    }

    // encode a string as a barcode bitmap image
    // screen width of 1080p allows a max of 15 characters
    private Bitmap encodeAsBitmap(String data) {
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        Writer codeWriter = new Code128Writer();
        try {
            DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
            int width = displayMetrics.widthPixels;
            int height = 150 * (displayMetrics.densityDpi / 160);
            BitMatrix bitMatrix = codeWriter.encode(data, BarcodeFormat.CODE_128, width, height, hintMap);
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    bmp.setPixel(i, j, bitMatrix.get(i,j) ? Color.BLACK : Color.WHITE);
                }
            }
            return bmp;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public class UseTicket extends AsyncTask<Ticket, Void, Void> {

        @Override
        protected Void doInBackground(Ticket... tickets) {
            ticketManager.useTicket(ticket);
            return null;
        }
    }
}