package es.uniovi.imovil.javier.rutasasturias;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RouteListFragment.Callbacks, SearchView.OnQueryTextListener{

    private String URL = "https://www.turismoasturias.es/open-data/catalogo-de-datos?p_p_id=opendata_WAR_importportlet&p_p_lifecycle=2&p_p_state=normal&p_p_mode=view&p_p_resource_id=exportJson&p_p_cacheability=cacheLevelPage&p_p_col_id=column-1&p_p_col_count=1&_opendata_WAR_importportlet_structure=27540&_opendata_WAR_importportlet_robots=nofollow";

    // Claves para guardar información
    private static final String FILE = "myfile";
    private static final String KEY_LIST_VALUE = "rutasValue";
    private static final String KEY_TAB_POSITION = "index";
    private static final String KEY_SELECTED_ROUTE = "selectedRoute";

    private ArrayList<Ruta> favoritasList = new ArrayList<Ruta>();
    private boolean mTwoPanes;
    private ProgressDialog progress;
    private ListAdapter listAdapter = null;
    private ListView listView = null;
    private Ruta selectedRuta = null;

    public ArrayList<Ruta> rutaArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Preparamos la toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null)
            setSupportActionBar(toolbar);

        // Preparamos las tabs
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        if (tabs != null) {
            tabs.addTab(tabs.newTab().setText(getString(R.string.tab_all)));
            tabs.addTab(tabs.newTab().setText(getString(R.string.tab_favorites)));

            // Repoblamos la lista en funcion de si es favoritas o todas
            tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    if (tab.getPosition()==1){
                        restoreFavorites();
                        populateList(favoritasList);
                    }
                    else
                        populateList(rutaArrayList);
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });
        }

        if (findViewById(R.id.route_details_container) != null) {
            mTwoPanes = true;
        }

        // Ponemos en marcha el ProgressDialog
        progress = new ProgressDialog(this);
        progress.setTitle(this.getString(R.string.loading_title));
        progress.setMessage(this.getString(R.string.loading_message));
        progress.setCancelable(false);
        progress.show();


        if (isOnline()){
            // Si hay datos guardados los cargamos
            if (savedInstanceState != null) {
                ArrayList<Ruta> savedArray = savedInstanceState.getParcelableArrayList(KEY_LIST_VALUE);
                rutaArrayList = savedArray;
                populateList(rutaArrayList);
                int index = savedInstanceState.getInt(KEY_TAB_POSITION);
                TabLayout.Tab tab = tabs.getTabAt(index);
                tab.select();
                if(mTwoPanes){
                    selectedRuta = savedInstanceState.getParcelable(KEY_SELECTED_ROUTE);
                    if(selectedRuta != null){
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        RouteDetailsFragment fragment = (RouteDetailsFragment) fragmentManager.findFragmentById(R.id.route_details_frag);
                        fragment.setDetails(selectedRuta);
                    }
                }
            }
            // Si no, los descargamos
            else{
                new DownloadJsonTask(this).execute(URL);
            }
        }

    }

    // Guardamos datos como la lista o la tab en la que estamos
    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_LIST_VALUE, rutaArrayList);
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        outState.putInt(KEY_TAB_POSITION, tabs.getSelectedTabPosition());
        if (mTwoPanes) {
            outState.putParcelable(KEY_SELECTED_ROUTE, selectedRuta);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflamos el menú, así añadimos los items a la barra
        getMenuInflater().inflate(R.menu.menu, menu);

        // Asociamos la configuración de busqueda con el searchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        MenuItem searchItem  = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));

        searchView.setSubmitButtonEnabled(false);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        listAdapter.getFilter().filter(newText);
        return true;
    }

    // Preparamos las acciones de los items del menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_map:
                Intent intent = new Intent(this, MapsActivity.class);
                Bundle args = new Bundle();
                args.putSerializable(MapsActivity.RUTAS,(Serializable)rutaArrayList);
                intent.putExtra(MapsActivity.BUNDLE,args);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                Context context = getApplicationContext();
                CharSequence text = this.getString(R.string.toast_settings);
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRouteSelected(Ruta ruta) {
        if (mTwoPanes) {
            selectedRuta = ruta;
            FragmentManager fragmentManager = getSupportFragmentManager();
            RouteDetailsFragment fragment = (RouteDetailsFragment) fragmentManager.findFragmentById(R.id.route_details_frag);
            fragment.setDetails(ruta);
        } else {
            Intent intent = new Intent(this, RouteDetailsActivity.class);
            intent.putExtra(RouteDetailsActivity.RUTA, (Parcelable) ruta);
            startActivity(intent);
        }
    }

    boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public void populateList(ArrayList<Ruta> al){
        listView = (ListView) findViewById(R.id.lista_rutas);
        listAdapter = new ListAdapter(this, al);
        listView.setAdapter(listAdapter);
        progress.dismiss();
    }

    // Recuperamos los favoritos del ficehero
    private boolean restoreFavorites () {
        InputStream buffer = null;
        ObjectInput input = null;

        try {
            buffer = new BufferedInputStream(
                    this.openFileInput(FILE));

            input = new ObjectInputStream(buffer);
            favoritasList = (ArrayList<Ruta>) input.readObject();
            return true;
        } catch (Exception ex) {

        } finally {
            try {
                input.close();
            } catch (Exception e) {

            }
        }
        return false;
    }
}
