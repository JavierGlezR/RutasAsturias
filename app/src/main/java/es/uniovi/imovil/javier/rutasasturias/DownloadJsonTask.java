package es.uniovi.imovil.javier.rutasasturias;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class DownloadJsonTask  extends AsyncTask<String, Void, ArrayList<Ruta>> {


    private MainActivity activity;

    public DownloadJsonTask(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<Ruta> doInBackground(String... urls) {

        // urls vienen de la llamada a execute(): urls[0] es la url
        try {
            return downloadUrl(urls[0]);
        } catch (IOException e) {
            return null;
        }
    }

    // Pasa un InputStream a un String
    public String streamToString(InputStream stream) throws IOException,
            UnsupportedEncodingException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int length = 0;
        do {
            length = stream.read(buffer);
            if (length != -1) {
                baos.write(buffer, 0, length);
            }
        } while (length != -1);
        return baos.toString("UTF-8");
    }

    private InputStream openHttpInputStream(String myUrl)
            throws MalformedURLException, IOException, ProtocolException {
        InputStream is;
        java.net.URL url = new URL(myUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // Aquí se hace realmente la petición
        conn.connect();

        is = conn.getInputStream();
        return is;
    }

    private ArrayList<Ruta> downloadUrl(String myUrl) throws IOException{
        InputStream is = null;

        try {
            is = openHttpInputStream(myUrl);
            String jsonRouteInformation = streamToString(is);
            return parseJsonRouteFile(jsonRouteInformation);
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            // Asegurarse de que el InputStream se cierra
            if (is != null) {
                is.close();
            }

        }
        return null;
    }

    // Pasamos las rutas a la actividad
    @Override
    protected void onPostExecute(ArrayList<Ruta> result) {
        activity.rutaArrayList = new ArrayList<Ruta>(result);
        activity.populateList(result);
    }


    private static final String RUTAS_TAG = "articles";
    private static final String RUTA_TAG = "article";
    private static final String DYNAMIC_ELEMENT_TAG = "dynamic-element";
    private static final String DYNAMIC_CONTENT_TAG = "dynamic-content";
    private static final String CONTENT_TAG = "content";

    private ArrayList<Ruta> parseJsonRouteFile(String jsonRouteInformation)
            throws JSONException {
        ArrayList<Ruta> result = new ArrayList<Ruta>();

        JSONObject root = new JSONObject(jsonRouteInformation);
        JSONObject rutas = root.getJSONObject(RUTAS_TAG);
        JSONArray rutasArray = rutas.getJSONArray(RUTA_TAG);

        //Recorremos el array de rutas y obtenemos los datos
        for (int i = 0; i < rutasArray.length(); i++) {

            JSONObject ruta = rutasArray.getJSONObject(i);
            JSONArray datos = ruta.getJSONArray(DYNAMIC_ELEMENT_TAG);

            // Variable donde almacenamos los datos
            Ruta informacionRuta = new Ruta();

            // Obtenemos el nombre de la ruta
            JSONObject nombre = datos.getJSONObject(0);
            try{
                JSONObject datosNombre = nombre.getJSONObject(DYNAMIC_CONTENT_TAG);
                informacionRuta.setNombre(datosNombre.getString(CONTENT_TAG));
            }
            catch (Exception e1){
                try{
                    informacionRuta.setNombre(nombre.getString(DYNAMIC_CONTENT_TAG));
                }
                catch (Exception e2){
                }
            }

            //Obtenemos las características del campo contacto
            JSONObject contacto = datos.getJSONObject(1);
            JSONArray contactoArray = contacto.getJSONArray(DYNAMIC_ELEMENT_TAG);

            //Concejo
            JSONObject concejo = contactoArray.getJSONObject(0);
            try{
                JSONObject datosConcejo = concejo.getJSONObject(DYNAMIC_CONTENT_TAG);
                informacionRuta.setConcejos(datosConcejo.getString(CONTENT_TAG));
            }
            catch (Exception e1){
                try {
                    informacionRuta.setConcejos(concejo.getString(DYNAMIC_CONTENT_TAG));
                }
                catch (Exception e2){
                }
            }

            //Zona
            JSONObject zona = contactoArray.getJSONObject(1);
            try{
                JSONObject datosZona = zona.getJSONObject(DYNAMIC_CONTENT_TAG);
                informacionRuta.setZona(datosZona.getString(CONTENT_TAG));
            }
            catch (Exception e1){
                try{
                    informacionRuta.setZona(zona.getString(DYNAMIC_CONTENT_TAG));
                }
                catch (Exception e2){
                }
            }

            //Distancia
            JSONObject distancia = contactoArray.getJSONObject(2);
            try{
                JSONObject datosDistancia = distancia.getJSONObject(DYNAMIC_CONTENT_TAG);
                informacionRuta.setDistancia(datosDistancia.getString(CONTENT_TAG));
            }
            catch (Exception e1){
                try{
                    informacionRuta.setDistancia(distancia.getString(DYNAMIC_CONTENT_TAG));
                }
                catch (Exception e2){
                }
            }

            //Dificultad
            JSONObject dificultad = contactoArray.getJSONObject(3);
            try{
                JSONObject datosDificultad = dificultad.getJSONObject(DYNAMIC_CONTENT_TAG);
                informacionRuta.setDificultad(datosDificultad.getString(CONTENT_TAG));
            }
            catch (Exception e1){
                try {
                    informacionRuta.setDificultad(dificultad.getString(DYNAMIC_CONTENT_TAG));
                }
                catch (Exception e2){
                }
            }

            //Tiempo a pie
            JSONObject tiempoAPie = contactoArray.getJSONObject(4);
            try{
                JSONObject datosTiempoAPie = tiempoAPie.getJSONObject(DYNAMIC_CONTENT_TAG);
                informacionRuta.setTiempoAPie(datosTiempoAPie.getString(CONTENT_TAG));
            }
            catch (Exception e1){
                try {
                    informacionRuta.setTiempoAPie(tiempoAPie.getString(DYNAMIC_CONTENT_TAG));
                }
                catch (Exception e2){
                }
            }

            //Tiempo BBT
            JSONObject tiempoBTT = contactoArray.getJSONObject(5);
            try{
                JSONObject datosTiempoBTT = tiempoBTT.getJSONObject(DYNAMIC_CONTENT_TAG);
                informacionRuta.setTiempoBTT(datosTiempoBTT.getString(CONTENT_TAG));
            }
            catch (Exception e1){
                try {
                    informacionRuta.setTiempoBTT(tiempoBTT.getString(DYNAMIC_CONTENT_TAG));
                }
                catch (Exception e2){
                }
            }

            //Tiempo en coche
            JSONObject tiempoCoche = contactoArray.getJSONObject(6);
            try{
                JSONObject datosTiempoCoche = tiempoCoche.getJSONObject(DYNAMIC_CONTENT_TAG);
                informacionRuta.setTiempoCoche(datosTiempoCoche.getString(CONTENT_TAG));
            }
            catch (Exception e1){
                try {
                    informacionRuta.setTiempoCoche(tiempoCoche.getString(DYNAMIC_CONTENT_TAG));
                }
                catch (Exception e2){
                }
            }

            //Tiempo en coche
            JSONObject tiempoViasVerdes = contactoArray.getJSONObject(7);
            try{
                JSONObject datosTiempoViasVerdes = tiempoViasVerdes.getJSONObject(DYNAMIC_CONTENT_TAG);
                informacionRuta.setTiempoViasVerdes(datosTiempoViasVerdes.getString(CONTENT_TAG));
            }
            catch (Exception e1){
                try {
                    informacionRuta.setTiempoViasVerdes(tiempoViasVerdes.getString(DYNAMIC_CONTENT_TAG));
                }
                catch (Exception e2){
                }
            }

            //Tiempo en coche
            JSONObject tiempoAscension = contactoArray.getJSONObject(8);
            try{
                JSONObject datosTiempoAscension = tiempoAscension.getJSONObject(DYNAMIC_CONTENT_TAG);
                informacionRuta.setTiempoAscension(datosTiempoAscension.getString(CONTENT_TAG));
            }
            catch (Exception e1){
                try {
                    informacionRuta.setTiempoAscension(tiempoAscension.getString(DYNAMIC_CONTENT_TAG));
                }
                catch (Exception e2){
                }
            }

            //Contacto texto
            JSONObject contactoTexto = contactoArray.getJSONObject(9);
            try{
                JSONObject datosContactoTexto = contactoTexto.getJSONObject(DYNAMIC_CONTENT_TAG);
                informacionRuta.setContactoTexto(datosContactoTexto.getString(CONTENT_TAG));
            }
            catch (Exception e1){
                try {
                    informacionRuta.setContactoTexto(contactoTexto.getString(DYNAMIC_CONTENT_TAG));
                }
                catch (Exception e2){
                }
            }

            //Código de la ruta
            JSONObject codigo = contactoArray.getJSONObject(10);
            try{
                JSONObject datosCodigo = codigo.getJSONObject(DYNAMIC_CONTENT_TAG);
                informacionRuta.setCodigo(datosCodigo.getString(CONTENT_TAG));
            }
            catch (Exception e1){
                try {
                    informacionRuta.setCodigo(codigo.getString(DYNAMIC_CONTENT_TAG));
                }
                catch (Exception e2){
                }
            }

            //Tipo de recorrido
            JSONObject tipoDeRecorrido = contactoArray.getJSONObject(11);
            try{
                JSONObject datosTipoDeRecorrido = tipoDeRecorrido.getJSONObject(DYNAMIC_CONTENT_TAG);
                informacionRuta.setTipoDeRecorrido(datosTipoDeRecorrido.getString(CONTENT_TAG));
            }
            catch (Exception e1){
                try {
                    informacionRuta.setTipoDeRecorrido(tipoDeRecorrido.getString(DYNAMIC_CONTENT_TAG));
                }
                catch (Exception e2){
                }
            }

            //Altitud
            JSONObject altitud = contactoArray.getJSONObject(12);
            try{
                JSONObject datosAltitud = altitud.getJSONObject(DYNAMIC_CONTENT_TAG);
                informacionRuta.setAltitud(datosAltitud.getString(CONTENT_TAG));
            }
            catch (Exception e1){
                try {
                    informacionRuta.setAltitud(altitud.getString(DYNAMIC_CONTENT_TAG));
                }
                catch (Exception e2){
                }
            }

            //Desnivel
            JSONObject desnivel = contactoArray.getJSONObject(13);
            try{
                JSONObject datosDesnivel = desnivel.getJSONObject(DYNAMIC_CONTENT_TAG);
                informacionRuta.setDesnivel(datosDesnivel.getString(CONTENT_TAG));
            }
            catch (Exception e1){
                try {
                    informacionRuta.setDesnivel(desnivel.getString(DYNAMIC_CONTENT_TAG));
                }
                catch (Exception e2){
                }
            }

            //Situación geográfica
            JSONObject situacionGeografica = contactoArray.getJSONObject(14);
            try{
                JSONObject datosSituacionGeografica = situacionGeografica.getJSONObject(DYNAMIC_CONTENT_TAG);
                informacionRuta.setSituacionGeografica(datosSituacionGeografica.getString(CONTENT_TAG));
            }
            catch (Exception e1){
                try {
                    informacionRuta.setSituacionGeografica(situacionGeografica.getString(DYNAMIC_CONTENT_TAG));
                }
                catch (Exception e2){
                }
            }

            //Situación geográfica
            JSONObject puntoDePartida = contactoArray.getJSONObject(15);
            try{
                JSONObject datosPuntoDePartida = puntoDePartida.getJSONObject(DYNAMIC_CONTENT_TAG);
                informacionRuta.setPuntoDePartida(datosPuntoDePartida.getString(CONTENT_TAG));
            }
            catch (Exception e1){
                try {
                    informacionRuta.setPuntoDePartida(puntoDePartida.getString(DYNAMIC_CONTENT_TAG));
                }
                catch (Exception e2){
                }
            }


            //Obtenemos las características del campo información
            JSONObject informacion = datos.getJSONObject(2);
            JSONArray informacionArray = informacion.getJSONArray(DYNAMIC_ELEMENT_TAG);

            //Resumen
            JSONObject resumen = informacionArray.getJSONObject(0);
            try{
                JSONObject datosResumen = resumen.getJSONObject(DYNAMIC_CONTENT_TAG);
                informacionRuta.setResumen(datosResumen.getString(CONTENT_TAG));
            }
            catch (Exception e1){
                try {
                    informacionRuta.setResumen(resumen.getString(DYNAMIC_CONTENT_TAG));
                }
                catch (Exception e2){
                }
            }

            //Informacion texto
            JSONObject informacionTexto = informacionArray.getJSONObject(1);
            try{
                JSONObject datosInformacionTexto = informacionTexto.getJSONObject(DYNAMIC_CONTENT_TAG);
                informacionRuta.setInformacionTexto(datosInformacionTexto.getString(CONTENT_TAG));
            }
            catch (Exception e1){
                try {
                    informacionRuta.setInformacionTexto(informacionTexto.getString(DYNAMIC_CONTENT_TAG));
                }
                catch (Exception e2){
                }
            }

            //Observaciones
            JSONObject observaciones = informacionArray.getJSONObject(2);
            try{
                JSONObject datosObservaciones = observaciones.getJSONObject(DYNAMIC_CONTENT_TAG);
                informacionRuta.setObservaciones(datosObservaciones.getString(CONTENT_TAG));
            }
            catch (Exception e1){
                try {
                    informacionRuta.setObservaciones(observaciones.getString(DYNAMIC_CONTENT_TAG));
                }
                catch (Exception e2){
                }
            }

            //Itinerario
            JSONObject itinerario = informacionArray.getJSONObject(3);
            try{
                JSONObject datosItinerario = itinerario.getJSONObject(DYNAMIC_CONTENT_TAG);
                informacionRuta.setItinerario(datosItinerario.getString(CONTENT_TAG));
            }
            catch (Exception e1){
                try {
                    informacionRuta.setItinerario(itinerario.getString(DYNAMIC_CONTENT_TAG));
                }
                catch (Exception e2){
                }
            }

            //Texto tramos
            JSONObject textoTramos = informacionArray.getJSONObject(4);
            try{
                JSONObject datosTextoTramos = textoTramos.getJSONObject(DYNAMIC_CONTENT_TAG);
                informacionRuta.setTextoTramos(datosTextoTramos.getString(CONTENT_TAG));
            }
            catch (Exception e1){
                try {
                    informacionRuta.setTextoTramos(textoTramos.getString(DYNAMIC_CONTENT_TAG));
                }
                catch (Exception e2){
                }
            }


            //Obtenemos las imágenes
            try{
                JSONObject visualizador = datos.getJSONObject(4);
                JSONArray visualizadorArray = visualizador.getJSONArray(DYNAMIC_ELEMENT_TAG);
                for(int j=0; j<visualizadorArray.length(); j++){
                    JSONObject imagen = visualizadorArray.getJSONObject(j);
                    try{
                        JSONObject datosImagen = imagen.getJSONObject(DYNAMIC_CONTENT_TAG);
                        informacionRuta.setVisualizador(datosImagen.getString(CONTENT_TAG));
                    }
                    catch (Exception e1){
                        try {
                            informacionRuta.setVisualizador(imagen.getString(DYNAMIC_CONTENT_TAG));
                        }
                        catch (Exception e2){
                            System.out.println("e2");
                        }
                    }
                }
            }
            catch (Exception e3){
                System.out.println("e2");
            }


            //Obtenemos el kml
            JSONObject trazadoRuta = datos.getJSONObject(5);

            try{
                JSONObject datosTrazadoRuta = trazadoRuta.getJSONObject(DYNAMIC_CONTENT_TAG);
                informacionRuta.setTrazadoRuta(datosTrazadoRuta.getString(CONTENT_TAG));
            }
            catch (Exception e1){
                try {
                    informacionRuta.setTrazadoRuta(trazadoRuta.getString(DYNAMIC_CONTENT_TAG));
                }
                catch (Exception e2){
                }
            }

            //Obtenemos el tipo de ruta
            JSONObject tipoRuta = datos.getJSONObject(6);

            try{
                JSONObject datosTipoRuta = tipoRuta.getJSONObject(DYNAMIC_CONTENT_TAG);
                informacionRuta.setTipoRuta(datosTipoRuta.getString(CONTENT_TAG));
            }
            catch (Exception e1){
                try {
                    informacionRuta.setTipoRuta(tipoRuta.getString(DYNAMIC_CONTENT_TAG));
                }
                catch (Exception e2){
                }
            }

            //Obtenemos las coordenadas
            try {
                JSONObject coordenadas = datos.getJSONObject(datos.length() - 1);
                JSONObject coordenadasObject = coordenadas.getJSONObject(DYNAMIC_ELEMENT_TAG);

                try {
                    JSONObject datosCoordenadas = coordenadasObject.getJSONObject(DYNAMIC_CONTENT_TAG);
                    informacionRuta.setCoordenadas(datosCoordenadas.getString(CONTENT_TAG));
                } catch (Exception e1) {
                    try {
                        informacionRuta.setCoordenadas(coordenadasObject.getString(DYNAMIC_CONTENT_TAG));
                    } catch (Exception e2) {
                    }
                }

            }
            catch (Exception e3){
            }

            result.add(informacionRuta);
        }
        return result;
    }
}
