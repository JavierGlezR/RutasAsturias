package es.uniovi.imovil.javier.rutasasturias;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    private static final String FILE = "myfile";
    private Context context;
    private ArrayList<Ruta> rutaArrayList;
    private ArrayList<Ruta> filteredList;
    private ArrayList<Ruta> favoritasList = new ArrayList<>();
    private RouteFilter routeFilter;
    private LayoutInflater inflater;

    public ListAdapter(Context context, ArrayList<Ruta> rutaArrayList) {
        if (context == null || rutaArrayList == null) {
            throw new IllegalArgumentException();
        }
        this.context = context;
        this.rutaArrayList = rutaArrayList;
        this.filteredList = rutaArrayList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Object getItem(int i) {
        return filteredList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    // Rellenamos el contenido de cada elemento de la lista
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        final ViewHolder holder;
        final Ruta ruta = (Ruta) getItem(position);

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.list_item_route, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) view.findViewById(R.id.nombre);
            holder.information = (TextView) view.findViewById(R.id.info);
            holder.image = (ImageView) view.findViewById(R.id.list_image);
            holder.star = (ImageView) view.findViewById(R.id.star);

            view.setTag(holder);
        }
        else {
            // Recuperamos el viewholder
            holder = (ViewHolder) view.getTag();
        }

        // Enlazamos los contenidos con el viewholder
        holder.name.setText(ruta.getNombre());

        String rutaInfo = "";
        if (ruta.getZona().length() > 1 && !ruta.getZona().contains("language")) {
            rutaInfo += context.getString(R.string.text_place) + " " + ruta.getZona()+"\n" ;
        }
        if (ruta.getDistancia().length() > 0 && !ruta.getDistancia().contains("language")) {
            rutaInfo += context.getString(R.string.text_distance) + " " + ruta.getDistancia();
        }
        holder.information.setText(rutaInfo);

        // Imagen explicativa según el tipo de ruta
        if (ruta.getTipoRuta().length() > 1 && !ruta.getTipoRuta().contains("language")) {
            switch (ruta.getTipoRuta().toLowerCase()){
                case "coche":
                    holder.image.setImageResource(R.drawable.ic_car);
                    break;
                case "btt":
                    holder.image.setImageResource(R.drawable.ic_bike);
                    break;
                case "ascensiones":
                    holder.image.setImageResource(R.drawable.ic_ascension);
                    break;
                case "senderismo":
                    holder.image.setImageResource(R.drawable.ic_walk);
                    break;
            }
        }

        // Imagen para indicar si la ruta es favorita o no
        restoreFavorites ();
        if(contains(favoritasList,ruta)){
            holder.star.setImageResource(R.drawable.ic_star);
        }
        else{
            holder.star.setImageResource(R.drawable.ic_star_empty);
        }

        // Cuando se pulse en la imagen de la estrella se añade o se quita de la lista de favoritas
        holder.star.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                restoreFavorites ();
                if(contains(favoritasList,ruta)){
                    holder.star.setImageResource(R.drawable.ic_star_empty);
                    remove(favoritasList,ruta);
                }
                else{
                    holder.star.setImageResource(R.drawable.ic_star);
                    favoritasList.add(ruta);
                }
                saveFavorites();
            }
        });

        return view;
    }

    // Recuperamos del fichero la lista de favoritas
    private boolean restoreFavorites () {
        InputStream buffer = null;
        ObjectInput input = null;

        try {
            buffer = new BufferedInputStream(
                    context.openFileInput(FILE));

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

    // Guardamos en fichero la lista de favoritas
    private void saveFavorites () {
        FileOutputStream file = null;
        OutputStream buffer = null;
        ObjectOutput output = null;

        try {
            file = context.openFileOutput(FILE, Context.MODE_PRIVATE);
            buffer = new BufferedOutputStream(file);
            output = new ObjectOutputStream(buffer);
            output.writeObject(favoritasList);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
            }
        }
    }

    // Método para comprobar que una ruta está en un array
    public boolean contains(ArrayList<Ruta> al, Ruta r){
        for(int i=0; i<al.size(); i++){
            if(al.get(i).getNombre().equals(r.getNombre())){
                return true;
            }
        }
        return false;
    }

    // Método para eliminar una ruta de un array
    public void remove(ArrayList<Ruta> al, Ruta r){
        for(int i=0; i<al.size(); i++){
            if(al.get(i).getNombre().equals(r.getNombre())){
                al.remove(i);
            }
        }
    }


    public Filter getFilter() {
        if (routeFilter == null) {
            routeFilter = new RouteFilter();
        }

        return routeFilter;
    }

    // El viewholder
    static class ViewHolder {
        TextView name;
        TextView information;
        ImageView image;
        ImageView star;
    }

    private class RouteFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<Ruta> tempList = new ArrayList<Ruta>();

                // Buscamos el contenido de la búsqueda en la lista
                for (Ruta ruta : rutaArrayList) {

                    if (ruta.getNombre().toLowerCase().contains(constraint.toString().toLowerCase()) ||
                            ruta.getConcejos().toLowerCase().contains(constraint.toString().toLowerCase()) ||
                            ruta.getTipoRuta().toLowerCase().contains(constraint.toString().toLowerCase()) ||
                            ruta.getZona().toLowerCase().contains(constraint.toString().toLowerCase())){

                        tempList.add(ruta);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = rutaArrayList.size();
                filterResults.values = rutaArrayList;
            }

            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (ArrayList<Ruta>) results.values;
            notifyDataSetChanged();
        }
    }

}
