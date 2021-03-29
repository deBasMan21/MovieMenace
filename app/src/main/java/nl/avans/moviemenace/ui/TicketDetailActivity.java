package nl.avans.moviemenace.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
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

import nl.avans.moviemenace.R;

import java.util.Hashtable;


public class TicketDetailActivity extends AppCompatActivity {
    private TextView mTitleTv;
    private TextView mLocationTv;
    private TextView mRowSeatTv;
    private TextView mDateTimeTv;

    private Button mUseBn;

    private ImageView mBarcodeIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        mUseBn = findViewById(R.id.bn_ticket_detail_use);

        mBarcodeIv = findViewById(R.id.iv_ticket_detail_barcode);

        mBarcodeIv.setImageBitmap(encodeAsBitmap("placeholder"));

        mUseBn.setOnClickListener((View v) -> {
            new AlertDialog.Builder(this)
                    .setTitle("Ticket inleveren")
                    .setMessage("Weet u zeker dat u dit ticket wil gebruiken?")
                    .setPositiveButton("Inleveren", (dialog, which) -> {
                        finish();
                    })
                    .setNegativeButton("Annuleren", null)
                    .setIcon(R.drawable.ic_menu_tickets)
                    .show();
        });
    }

    private Bitmap encodeAsBitmap(String data) {
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        Writer codeWriter = new Code128Writer();
        try {
            int width = Resources.getSystem().getDisplayMetrics().widthPixels;
            int height = 300;
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
}