package es.uniovi.imovil.javier.rutasasturias;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class RouteDetailsActivity extends AppCompatActivity {

    public static final String RUTA = "es.uniovi.imovil.javier.rutasasturias.Ruta";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_details_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null)
            setSupportActionBar(toolbar);

        if (findViewById(R.id.fragment_container) != null) {

            Intent intent = getIntent();
            Ruta ruta = intent.getParcelableExtra(RUTA);

            if (savedInstanceState != null) {
                return;
            }

            RouteDetailsFragment fragment =
                    RouteDetailsFragment.newInstance(ruta);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();
        }
    }
}
