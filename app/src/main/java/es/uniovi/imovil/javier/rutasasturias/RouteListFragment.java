package es.uniovi.imovil.javier.rutasasturias;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class RouteListFragment extends Fragment implements AdapterView.OnItemClickListener {

    Callbacks mCallback;

    public static RouteListFragment newInstance() {
        RouteListFragment fragment = new RouteListFragment();
        return fragment;
    }

    public RouteListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.route_list_fragment, container, false);

        ListView rutas = (ListView) rootView.findViewById(R.id.lista_rutas);
        rutas.setOnItemClickListener(this);

        return rootView;
    }

    public interface Callbacks {
        public void onRouteSelected(Ruta ruta);
    }

    @Override
    public void onAttach(Context activity) {

        super.onAttach(activity);
        try {
            mCallback = (Callbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    " must implement Callbacks");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Ruta ruta = (Ruta) parent.getItemAtPosition(position);
        mCallback.onRouteSelected(ruta);
    }

}