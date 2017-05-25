package es.uniovi.imovil.javier.rutasasturias;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;


public class Ruta implements Parcelable, Serializable{

    //Variables
    private String nombre;
    private String concejos;
    private String zona;
    private String distancia;
    private String dificultad;
    private String tiempoAPie;
    private String tiempoBTT;
    private String tiempoCoche;
    private String tiempoViasVerdes;
    private String tiempoAscension;
    private String contactoTexto;
    private String codigo;
    private String tipoDeRecorrido;
    private String altitud;
    private String desnivel;
    private String situacionGeografica;
    private String puntoDePartida;
    private String resumen;
    private String informacionTexto;
    private String observaciones;
    private String itinerario;
    private String textoTramos;
    private ArrayList<String> visualizador = new ArrayList<>();
    private String trazadoRuta;
    private String tipoRuta;
    private String coordenadas;


    //Métodos de Parceable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(nombre);
        out.writeString(concejos);
        out.writeString(zona);
        out.writeString(distancia);
        out.writeString(dificultad);
        out.writeString(tiempoAPie);
        out.writeString(tiempoBTT);
        out.writeString(tiempoCoche);
        out.writeString(tiempoViasVerdes);
        out.writeString(tiempoAscension);
        out.writeString(contactoTexto);
        out.writeString(codigo);
        out.writeString(tipoDeRecorrido);
        out.writeString(altitud);
        out.writeString(desnivel);
        out.writeString(situacionGeografica);
        out.writeString(puntoDePartida);
        out.writeString(resumen);
        out.writeString(informacionTexto);
        out.writeString(observaciones);
        out.writeString(itinerario);
        out.writeString(textoTramos);
        out.writeList(visualizador);
        out.writeString(trazadoRuta);
        out.writeString(tipoRuta);
        out.writeString(coordenadas);
    }

    public static final Parcelable.Creator<Ruta> CREATOR
            = new Parcelable.Creator<Ruta>() {
        public Ruta createFromParcel(Parcel in) {
            return new Ruta(in);
        }

        public Ruta[] newArray(int size) {
            return new Ruta[size];
        }
    };

    private Ruta(Parcel in) {
        nombre = in.readString();
        concejos = in.readString();
        zona = in.readString();
        distancia = in.readString();
        dificultad = in.readString();
        tiempoAPie = in.readString();
        tiempoBTT = in.readString();
        tiempoCoche = in.readString();
        tiempoViasVerdes = in.readString();
        tiempoAscension = in.readString();
        contactoTexto = in.readString();
        codigo = in.readString();
        tipoDeRecorrido = in.readString();
        altitud = in.readString();
        desnivel = in.readString();
        situacionGeografica = in.readString();
        puntoDePartida = in.readString();
        resumen = in.readString();
        informacionTexto = in.readString();
        observaciones = in.readString();
        itinerario = in.readString();
        textoTramos = in.readString();
        in.readList(visualizador,null);
        trazadoRuta = in.readString();
        tipoRuta = in.readString();
        coordenadas = in.readString();
    }

    public Ruta() {
    }

    //Getters y setters
    public String getItinerario() {
        return itinerario;
    }

    public void setItinerario(String itinerario) {
        this.itinerario = itinerario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getConcejos() {
        return concejos;
    }

    public void setConcejos(String concejos) {
        this.concejos = concejos;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public String getTiempoAPie() {
        return tiempoAPie;
    }

    public void setTiempoAPie(String tiempoAPie) {
        this.tiempoAPie = tiempoAPie;
    }

    public String getTiempoBTT() {
        return tiempoBTT;
    }

    public void setTiempoBTT(String tiempoBTT) {
        this.tiempoBTT = tiempoBTT;
    }

    public String getTiempoCoche() {
        return tiempoCoche;
    }

    public void setTiempoCoche(String tiempoCoche) {
        this.tiempoCoche = tiempoCoche;
    }

    public String getTiempoViasVerdes() {
        return tiempoViasVerdes;
    }

    public void setTiempoViasVerdes(String tiempoViasVerdes) {
        this.tiempoViasVerdes = tiempoViasVerdes;
    }

    public String getTiempoAscension() {
        return tiempoAscension;
    }

    public void setTiempoAscension(String tiempoAscension) {
        this.tiempoAscension = tiempoAscension;
    }

    public String getContactoTexto() {
        return contactoTexto;
    }

    public void setContactoTexto(String contactoTexto) {
        this.contactoTexto = contactoTexto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipoDeRecorrido() {
        return tipoDeRecorrido;
    }

    public void setTipoDeRecorrido(String tipoDeRecorrido) {
        this.tipoDeRecorrido = tipoDeRecorrido;
    }

    public String getAltitud() {
        return altitud;
    }

    public void setAltitud(String altitud) {
        this.altitud = altitud;
    }

    public String getDesnivel() {
        return desnivel;
    }

    public void setDesnivel(String desnivel) {
        this.desnivel = desnivel;
    }

    public String getSituacionGeografica() {
        return situacionGeografica;
    }

    public void setSituacionGeografica(String situacionGeografica) {
        this.situacionGeografica = situacionGeografica;
    }

    public String getPuntoDePartida() {
        return puntoDePartida;
    }

    public void setPuntoDePartida(String puntoDePartida) {
        this.puntoDePartida = puntoDePartida;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getInformacionTexto() {
        return informacionTexto;
    }

    public void setInformacionTexto(String informacionTexto) {
        this.informacionTexto = informacionTexto;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getTextoTramos() {
        return textoTramos;
    }

    public void setTextoTramos(String textoTramos) {
        this.textoTramos = textoTramos;
    }

    public ArrayList<String> getVisualizador() {
        return visualizador;
    }

    public void setVisualizador(String imagen) {
        this.visualizador.add(imagen);
    }

    public String getTrazadoRuta() {
        return trazadoRuta;
    }

    public void setTrazadoRuta(String trazadoRuta) {
        this.trazadoRuta = trazadoRuta;
    }

    public String getTipoRuta() {
        return tipoRuta;
    }

    public void setTipoRuta(String tipoRuta) {
        this.tipoRuta = tipoRuta;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }

}
