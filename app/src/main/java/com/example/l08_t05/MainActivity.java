package com.example.l08_t05;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView teksti;
    TextView text;
    ProgressBar progressBar;
    SeekBar seekBar;
    Context context = null;

    ArrayList<Bottle> bottleList = new ArrayList();

    public static int bottles = 6;
    public static float money = 0;
    public static int kierros = 0;
    public static String viimeisin_nimi = "Tyhjä";
    public static float viimeisin_hinta = 0;
    public static float viimeisin_koko = 0;
    public static String viimeisin_valmistaja = "Tyhjä";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;
        System.out.println(context.getFilesDir());

        teksti = (TextView) findViewById(R.id.textView);
        teksti.setText("Toiminnot näkyvät tässä.");
        progressBar = (ProgressBar) findViewById((R.id.progressBar));

        seekBar = (SeekBar) findViewById((R.id.seekBar));
        text = (TextView) findViewById((R.id.rahasyotto));

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.pullot, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        seekBar.setOnSeekBarChangeListener((new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressBar.setProgress(progress);
                text.setText(progress + "€");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        }));

        bottleList.add(new Bottle(0, "Pepsi Max", "Pepsi", 0.3f, 1.8f, 0.5f));
        bottleList.add(new Bottle(1, "Pepsi Max", "Pepsi", 0.3f, 2.2f, 1.5f));
        bottleList.add(new Bottle(2, "Coca-Cola Zero", "Coca-Cola", 0.3f, 2.0f, 0.5f));
        bottleList.add(new Bottle(3, "Coca-Cola Zero", "Coca-Cola", 0.3f, 2.5f, 1.5f));
        bottleList.add(new Bottle(4, "Fanta Zero", "Coca-Cola", 0.3f, 1.95f, 0.5f));
        bottleList.add(new Bottle(5, "Fanta Zero", "Coca-Cola", 0.3f, 1.95f, 1.5f));
    }
    public void addMoney(View view) {
        float muutos = seekBar.getProgress();
        money = money + muutos;
        seekBar.setProgress(0);
        teksti.setText("Klink! Lisää rahaa laitteeseen!");
    }
    public void buyBottle(View view) {
        int a = 0;
        int k = 0;
        for (Bottle bt : bottleList) {
            int valittu_pullo = bottleList.get(a).getIde();

            if (valittu_pullo == kierros) {
                float valittu_raha = bottleList.get(a).getPrice();
                if (valittu_raha <= money) {
                    k++;
                    String pullo = bottleList.get(a).getName() + " " + bottleList.get(a).getSize() + " litraa tipahti masiinasta.";
                    teksti.setText(pullo);
                    float vahennys = bottleList.get(a).getPrice();
                    money = money - vahennys;

                    bottles -= 1;

                    /*Tallennetaan ostosten tiedot kuittiin*/

                    viimeisin_nimi = bottleList.get(a).getName();
                    viimeisin_hinta = bottleList.get(a).getPrice();
                    viimeisin_koko = bottleList.get(a).getSize();
                    viimeisin_valmistaja = bottleList.get(a).getManufacturer();
                    bottleList.remove(a);
                    break;
                } else {
                    teksti.setText("Syötä lisää rahaa!");
                    k++;

                }
            } else {

            } a++;
        }
        if (k == 0) {
            teksti.setText("Pulloa ei ole laitteessa. ");
        }
    }
    public void returnMoney(View view) {
        String t = ("Klink klink. Sinne menivät rahat! Rahaa tuli ulos ") + money + " €.";
        teksti.setText(t);
        money = 0;
    }
    public void writeKuitti(View view) {
        try {
            OutputStreamWriter ows = new OutputStreamWriter(context.openFileOutput("kuitti.txt", Context.MODE_PRIVATE));

            String merkkijono = "Kuitti: " + viimeisin_nimi +" "+ viimeisin_koko + " litraa"+"\n"+"Valmistaja: " + viimeisin_valmistaja + "\n"+"Hinta: " + viimeisin_hinta;

            ows.write(merkkijono);
            ows.close();
            teksti.setText("Kuitti kirjoitettu.");

        } catch (IOException e) {
            Log.e("IOException", "Syötettä ei voi lukea.");
        } finally {
            System.out.println("Kirjoitettu.");
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
        kierros = parent.getSelectedItemPosition();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) { }
}
