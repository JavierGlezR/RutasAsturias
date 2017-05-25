package es.uniovi.imovil.javier.rutasasturias;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class RouteDetailsFragment extends Fragment {

    public static final String RUTA = "es.uniovi.imovil.javier.rutasasturias.Ruta";

    public RouteDetailsFragment() {
    }

    public static RouteDetailsFragment newInstance(Ruta ruta) {

        RouteDetailsFragment fragment = new RouteDetailsFragment();

        Bundle args = new Bundle();
        args.putParcelable(RUTA, ruta);
        fragment.setArguments(args);

        return fragment;
    }

    // Método que se llama normalmente
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView;
        rootView = inflater.inflate(R.layout.route_details_fragment, container, false);


        Bundle args = getArguments();
        if (args != null) {
            final Ruta ruta = args.getParcelable(RUTA);
            return populateDetails(rootView, ruta);
        }
        else
            return rootView;

    }


    // Método que se llama en dispositivos grandes
    public void setDetails(Ruta ruta) {

        populateDetails(getView(), ruta);

        /*
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) getView().findViewById(R.id.collapsing_toolbar);

        final String title= this.getString(R.string.app_name);

        AppBarLayout appBarLayout = (AppBarLayout) getView().findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(title);
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });

        if(ruta.getVisualizador() != null && !ruta.getVisualizador().isEmpty()){
            new DownloadImageTask((ImageView) getView().findViewById(R.id.image_details))
                    .execute("https://www.turismoasturias.es"+ruta.getVisualizador().get(0));
        }

        TextView textView = (TextView) getView().findViewById(R.id.name_details);
        if (ruta.getNombre().length() > 1 && !ruta.getNombre().contains("language")) {
            textView.setText(ruta.getNombre());
        }

        textView = (TextView) getView().findViewById(R.id.info_details);
        String details = "";
        String temp = "";

        if (ruta.getTipoRuta().length() > 1 && !ruta.getTipoRuta().contains("language")) {
            details += this.getString(R.string.text_type) + " " + ruta.getTipoRuta()+"\n" ;
        }
        if (ruta.getPuntoDePartida().length() > 1 && !ruta.getPuntoDePartida().contains("language")) {
            temp = ruta.getPuntoDePartida().replaceAll("\\<.*?>","");
            temp = temp.replaceAll("&.*?;","");
            temp = temp.replace("\n","");
            temp = temp.replace("\t","");
            details += this.getString(R.string.text_start) + " " + temp +"\n" ;
        }
        if (ruta.getContactoTexto().length() > 1 && !ruta.getContactoTexto().contains("language")) {
            temp = ruta.getContactoTexto().replaceAll("\\<.*?>","");
            temp = temp.replaceAll("&.*?;","");
            temp = temp.replace("\n","");
            temp = temp.replace("\t","");
            details += this.getString(R.string.text_contact) + " " + temp +"\n";
        }
        if (ruta.getItinerario().length() > 1 && !ruta.getItinerario().contains("language")) {
            temp = ruta.getItinerario().replaceAll("\\<.*?>","");
            temp = temp.replaceAll("&.*?;","");
            temp = temp.replace("\n","");
            temp = temp.replace("\t","");
            details += this.getString(R.string.text_itinerary) + " " + temp +"\n" ;
        }
        details += "\n";
        if (ruta.getResumen().length() > 1 && !ruta.getResumen().contains("language") && !ruta.getInformacionTexto().contains(ruta.getResumen())) {
            temp = ruta.getResumen().replaceAll("\\<.*?>","");
            temp = temp.replaceAll("&.*?;","");
            temp = temp.replace("\n","");
            temp = temp.replace("\t","");
            details += temp +"\n" ;
        }
        if (ruta.getInformacionTexto().length() > 1 && !ruta.getInformacionTexto().contains("language")) {
            temp = ruta.getInformacionTexto().replaceAll("\\<.*?>","");
            temp = temp.replaceAll("&.*?;","");
            temp = temp.replace("\n","");
            temp = temp.replace("\t","");
            details += temp +"\n" ;
        }
        if (ruta.getObservaciones().length() > 1 && !ruta.getObservaciones().contains("language")) {
            temp = ruta.getObservaciones().replaceAll("\\<.*?>","");
            temp = temp.replaceAll("&.*?;","");
            temp = temp.replace("\n","");
            temp = temp.replace("\t","");
            details += temp +"\n" ;
        }
        textView.setText(details);


        details = "";
        textView = (TextView) getView().findViewById(R.id.location_details);
        if (ruta.getSituacionGeografica().length() > 1 && !ruta.getSituacionGeografica().contains("language")) {
            details += ruta.getSituacionGeografica()+"\n" ;
        }
        if (ruta.getConcejos().length() > 1 && !ruta.getConcejos().contains("language")) {
            details += ruta.getConcejos()+"\n" ;
        }
        if (ruta.getZona().length() > 1 && !ruta.getZona().contains("language")) {
            details += ruta.getZona()+"\n" ;
        }
        textView.setText(details);


        details = "";
        textView = (TextView) getView().findViewById(R.id.technical_info_details);
        if (ruta.getDistancia().length() > 0 && !ruta.getDistancia().contains("language")) {
            details += this.getString(R.string.text_distance) + " " + ruta.getDistancia()+"\n" ;
        }
        if (ruta.getDificultad().length() > 0 && !ruta.getDificultad().contains("language")) {
            details += this.getString(R.string.text_difficulty) + " " + ruta.getDificultad()+"\n" ;
        }
        if (ruta.getTiempoAPie().length() > 0 && !ruta.getTiempoAPie().contains("language")) {
            details += this.getString(R.string.text_time) + " " + ruta.getTiempoAPie()+"\n" ;
        }
        if (ruta.getTiempoBTT().length() > 0 && !ruta.getTiempoBTT().contains("language")) {
            details += this.getString(R.string.text_time) + " " + ruta.getTiempoBTT()+"\n" ;
        }
        if (ruta.getTiempoCoche().length() > 0 && !ruta.getTiempoCoche().contains("language")) {
            details += this.getString(R.string.text_time) + " " + ruta.getTiempoCoche()+"\n" ;
        }
        if (ruta.getTiempoViasVerdes().length() > 0 && !ruta.getTiempoViasVerdes().contains("language")) {
            details += this.getString(R.string.text_time) + " " + ruta.getTiempoViasVerdes()+"\n" ;
        }
        if (ruta.getTiempoAscension().length() > 0 && !ruta.getTiempoAscension().contains("language")) {
            details += this.getString(R.string.text_time) + " " + ruta.getTiempoAscension()+"\n" ;
        }
        if (ruta.getTipoDeRecorrido().length() > 0 && !ruta.getTipoDeRecorrido().contains("language")) {
            details += this.getString(R.string.text_type2) + " " + ruta.getTipoDeRecorrido()+"\n" ;
        }
        if (ruta.getAltitud().length() > 0 && !ruta.getAltitud().contains("language")) {
            details += this.getString(R.string.text_altitude) + " " + ruta.getAltitud()+"\n" ;
        }
        if (ruta.getDesnivel().length() > 0 && !ruta.getDesnivel().contains("language")) {
            details += this.getString(R.string.text_slope) + " " + ruta.getDesnivel()+"\n" ;
        }
        textView.setText(details);


        if(ruta.getCoordenadas() != null && !ruta.getCoordenadas().equals("")){
            FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.fab);
            fab.setVisibility(View.VISIBLE);

            fab.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ArrayList<Ruta> rutaArrayList = new ArrayList<Ruta>();
                    rutaArrayList.add(ruta);

                    Intent intent = new Intent(getActivity(), MapsActivity.class);
                    Bundle arg = new Bundle();
                    arg.putSerializable(MapsActivity.RUTAS,(Serializable)rutaArrayList);
                    intent.putExtra(MapsActivity.BUNDLE,arg);
                    startActivity(intent);
                }
            });

        }*/
    }

    // Rellenamos los detalles
    private View populateDetails (View view, final Ruta ruta){

        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);

        final String title = this.getString(R.string.app_name);

        AppBarLayout appBarLayout = (AppBarLayout) view.findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(title);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });

        if(ruta.getVisualizador() != null && !ruta.getVisualizador().isEmpty()){
            new DownloadImageTask((ImageView) view.findViewById(R.id.image_details))
                    .execute("https://www.turismoasturias.es"+ruta.getVisualizador().get(0));
        }

        TextView textView = (TextView) view.findViewById(R.id.name_details);
        if (ruta.getNombre().length() > 1 && !ruta.getNombre().contains("language")) {
            textView.setText(ruta.getNombre());
        }

        textView = (TextView) view.findViewById(R.id.info_details);
        String details = "";
        String temp = "";

        if (ruta.getTipoRuta().length() > 1 && !ruta.getTipoRuta().contains("language")) {
            details += this.getString(R.string.text_type) + " " + ruta.getTipoRuta()+"\n" ;
        }
        if (ruta.getPuntoDePartida().length() > 1 && !ruta.getPuntoDePartida().contains("language")) {
            temp = ruta.getPuntoDePartida().replaceAll("\\<.*?>","");
            temp = temp.replaceAll("&.*?;","");
            temp = temp.replace("\n","");
            temp = temp.replace("\t","");
            details += this.getString(R.string.text_start) + " " + temp +"\n" ;
        }
        if (ruta.getContactoTexto().length() > 1 && !ruta.getContactoTexto().contains("language")) {
            temp = ruta.getContactoTexto().replaceAll("\\<.*?>","");
            temp = temp.replaceAll("&.*?;","");
            temp = temp.replace("\n","");
            temp = temp.replace("\t","");
            details += this.getString(R.string.text_contact) + " " + temp +"\n";
        }
        if (ruta.getItinerario().length() > 1 && !ruta.getItinerario().contains("language")) {
            temp = ruta.getItinerario().replaceAll("\\<.*?>","");
            temp = temp.replaceAll("&.*?;","");
            temp = temp.replace("\n","");
            temp = temp.replace("\t","");
            details += this.getString(R.string.text_itinerary) + " " + temp +"\n" ;
        }
        details += "\n";
        if (ruta.getResumen().length() > 1 && !ruta.getResumen().contains("language") && !ruta.getInformacionTexto().contains(ruta.getResumen())) {
            temp = ruta.getResumen().replaceAll("\\<.*?>","");
            temp = temp.replaceAll("&.*?;","");
            temp = temp.replace("\n","");
            temp = temp.replace("\t","");
            details += temp +"\n" ;
        }
        if (ruta.getInformacionTexto().length() > 1 && !ruta.getInformacionTexto().contains("language")) {
            temp = ruta.getInformacionTexto().replaceAll("\\<.*?>","");
            temp = temp.replaceAll("&.*?;","");
            temp = temp.replace("\n","");
            temp = temp.replace("\t","");
            details += temp +"\n" ;
        }
        if (ruta.getObservaciones().length() > 1 && !ruta.getObservaciones().contains("language")) {
            temp = ruta.getObservaciones().replaceAll("\\<.*?>","");
            temp = temp.replaceAll("&.*?;","");
            temp = temp.replace("\n","");
            temp = temp.replace("\t","");
            details += temp +"\n" ;
        }
        textView.setText(details);


        details = "";
        textView = (TextView) view.findViewById(R.id.location_details);
        if (ruta.getSituacionGeografica().length() > 1 && !ruta.getSituacionGeografica().contains("language")) {
            details += ruta.getSituacionGeografica()+"\n" ;
        }
        if (ruta.getConcejos().length() > 1 && !ruta.getConcejos().contains("language")) {
            details += ruta.getConcejos()+"\n" ;
        }
        if (ruta.getZona().length() > 1 && !ruta.getZona().contains("language")) {
            details += ruta.getZona()+"\n" ;
        }
        textView.setText(details);


        details = "";
        textView = (TextView) view.findViewById(R.id.technical_info_details);
        if (ruta.getDistancia().length() > 0 && !ruta.getDistancia().contains("language")) {
            details += this.getString(R.string.text_distance) + " " + ruta.getDistancia()+"\n" ;
        }
        if (ruta.getDificultad().length() > 0 && !ruta.getDificultad().contains("language")) {
            details += this.getString(R.string.text_difficulty) + " " + ruta.getDificultad()+"\n" ;
        }
        if (ruta.getTiempoAPie().length() > 0 && !ruta.getTiempoAPie().contains("language")) {
            details += this.getString(R.string.text_time) + " " + ruta.getTiempoAPie()+"\n" ;
        }
        if (ruta.getTiempoBTT().length() > 0 && !ruta.getTiempoBTT().contains("language")) {
            details += this.getString(R.string.text_time) + " " + ruta.getTiempoBTT()+"\n" ;
        }
        if (ruta.getTiempoCoche().length() > 0 && !ruta.getTiempoCoche().contains("language")) {
            details += this.getString(R.string.text_time) + " " + ruta.getTiempoCoche()+"\n" ;
        }
        if (ruta.getTiempoViasVerdes().length() > 0 && !ruta.getTiempoViasVerdes().contains("language")) {
            details += this.getString(R.string.text_time) + " " + ruta.getTiempoViasVerdes()+"\n" ;
        }
        if (ruta.getTiempoAscension().length() > 0 && !ruta.getTiempoAscension().contains("language")) {
            details += this.getString(R.string.text_time) + " " + ruta.getTiempoAscension()+"\n" ;
        }
        if (ruta.getTipoDeRecorrido().length() > 0 && !ruta.getTipoDeRecorrido().contains("language")) {
            details += this.getString(R.string.text_type2) + " " + ruta.getTipoDeRecorrido()+"\n" ;
        }
        if (ruta.getAltitud().length() > 0 && !ruta.getAltitud().contains("language")) {
            details += this.getString(R.string.text_altitude) + " " + ruta.getAltitud()+"\n" ;
        }
        if (ruta.getDesnivel().length() > 0 && !ruta.getDesnivel().contains("language")) {
            details += this.getString(R.string.text_slope) + " " + ruta.getDesnivel()+"\n" ;
        }
        textView.setText(details);



        if(ruta.getCoordenadas() != null && !ruta.getCoordenadas().equals("")){
            FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
            fab.setVisibility(View.VISIBLE);

            fab.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ArrayList<Ruta> rutaArrayList = new ArrayList<Ruta>();
                    rutaArrayList.add(ruta);

                    Intent intent = new Intent(getActivity(), MapsActivity.class);
                    Bundle arg = new Bundle();
                    arg.putSerializable(MapsActivity.RUTAS,(Serializable)rutaArrayList);
                    intent.putExtra(MapsActivity.BUNDLE,arg);
                    startActivity(intent);
                }
            });
        }
        return view;
    }


    // Descargamos imagen
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bm = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bm = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bm;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
