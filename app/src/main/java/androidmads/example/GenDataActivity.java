package androidmads.example;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

// Importy

public class GenDataActivity extends AppCompatActivity {

    Button btnScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gen_data);

        btnScan = findViewById(R.id.btnScan);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Inicjalizacja skanera QR
                IntentIntegrator integrator = new IntentIntegrator(GenDataActivity.this);
                integrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() != null) {
                String qrCodeData = result.getContents();
                // Tutaj możesz przetwarzać odczytane dane z kodu QR

                // Otwórz przeglądarkę z adresem URL z kodu QR
                openBrowser(qrCodeData);
            }
        }
    }

    private void openBrowser(String query) {
        // Utwórz nowy intent do wyszukiwania w przeglądarce
        Intent searchIntent = new Intent(Intent.ACTION_WEB_SEARCH);
        searchIntent.putExtra(SearchManager.QUERY, query);

        // Sprawdź, czy istnieje przynajmniej jedna aplikacja obsługująca akcję wyszukiwania w przeglądarce
        if (searchIntent.resolveActivity(getPackageManager()) != null) {
            // Uruchom wyszukiwanie w przeglądarce
            startActivity(searchIntent);
        } else {
            // Jeśli nie znaleziono przeglądarki, można obsłużyć to zdarzenie odpowiednim komunikatem
            Toast.makeText(this, "Brak aplikacji przeglądarki", Toast.LENGTH_SHORT).show();
        }
    }
}
