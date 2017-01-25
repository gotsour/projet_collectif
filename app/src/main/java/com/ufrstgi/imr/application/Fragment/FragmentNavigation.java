package com.ufrstgi.imr.application.Fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.ufrstgi.imr.application.MainActivity;
import com.ufrstgi.imr.application.R;
import com.ufrstgi.imr.application.activity.SynchronizeFromServerTask;
import com.ufrstgi.imr.application.activity.Updateable;
import com.ufrstgi.imr.application.database.local.ColisManager;
import com.ufrstgi.imr.application.database.local.OperationManager;
import com.ufrstgi.imr.application.database.local.TourneeManager;
import com.ufrstgi.imr.application.database.server.Communication;
import com.ufrstgi.imr.application.object.Colis;
import com.ufrstgi.imr.application.object.Livraison;
import com.ufrstgi.imr.application.object.Operation;
import com.ufrstgi.imr.application.object.Reception;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Duduf on 11/01/2017.
 */

public class FragmentNavigation extends Fragment implements OnMapReadyCallback, LocationListener, Updateable {

    private View rootView;
    GoogleMap myMap;
    MapView mMapView;
    Location location;
    ArrayList<Colis> listeColis;
    Colis colis;

    TextView tvClient;
    TextView tvAdresse;
    TextView tvTelephone;
    TextView tvVille;
    TextView tvNombre;
    Button bValider;
    boolean visible=false;
    int idTournee;
    LatLngBounds bounds;

    private String title;
    private int page;

    public FragmentNavigation() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_navigation, container, false);
            MapsInitializer.initialize(this.getActivity());
            mMapView = (MapView) rootView.findViewById(R.id.map);
            mMapView.onCreate(savedInstanceState);
            mMapView.getMapAsync(this);
            tvClient=(TextView) rootView.findViewById(R.id.tvClient);
            tvAdresse=(TextView) rootView.findViewById(R.id.tvAdresse);
            tvTelephone=(TextView) rootView.findViewById(R.id.tvTelephone);
            tvVille=(TextView) rootView.findViewById(R.id.tvVille);
            tvNombre=(TextView) rootView.findViewById(R.id.tvNombre);
            bValider=(Button) rootView.findViewById(R.id.bValider);


            /*Log.d("loginChauffeur", "chargement sync debut fragment");



            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.d("loginChauffeur", "avant lancement thread");
                    Communication sync=new Communication(getActivity());
                    sync.synchronizeFromServerSynchrone("chauffeur0001",0);
                    Log.d("loginChauffeur", "apres lancement thread");
                }
            });

            t.start(); // spawn thread

            t.join();



            Log.d("loginChauffeur", "fin chargement sync debut fragment");
           /* Log.d("loginChauffeur", "chargement sync debut fragment");
            SynchronizeFromServerTask sync = new SynchronizeFromServerTask(1,"chauffeur0001",getActivity());
            sync.execute();
            Log.d("loginChauffeur", "fin chargement sync debut fragment");*/



            Log.d("loginChauffeur", "chargement bdd dans fragment navigation");
            TourneeManager tourneeManager = new TourneeManager(getActivity());
            tourneeManager.open();
            idTournee=tourneeManager.getTournee().getId_tournee();
            tourneeManager.close();

            Log.d("loginChauffeur","fin chargement bdd dans fragment navigation");

            loadData();

            bValider.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    valideColis();
                    loadData();
                    if(colis!=null){
                        /*GPSTracker gps = new GPSTracker(getActivity());
                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();*/
                        //Log.d("positionLatlong", "latitude : "+latitude+ " longitude : "+longitude);
                        String url =makeURL(location.getLatitude(), location.getLongitude(),
                                colis.getCurrentOperation().getAdresse().getLatlng().getLatitude(), colis.getCurrentOperation().getAdresse().getLatlng().getLongitude());
                        connectAsyncTask test = new connectAsyncTask(url, getActivity(),true);
                        test.execute();
                    }
                    /*connectAsyncTask test = new connectAsyncTask(makeURL(47.282925, 5.993049, 47.282928, 5.993042), getActivity());
                    test.execute();*/

                }
            });





        return rootView;
    }

    public void valideColis(){
        OperationManager operationManager = new OperationManager(getActivity());
        operationManager.open();
        for(int i =0; i<listeColis.size();i++) {
           //valide l'operation en mettant une date reelle
            Operation operation=  listeColis.get(i).getCurrentOperation();
            operation.setDate_reelle(Calendar.getInstance().getTime());

            operationManager.updateOperation(operation);
            Log.d("resultat","valide colis : "+i);

        }
        if(listeColis.size()==0){
            colis=null;
        }



        operationManager.close();
    }

    public void loadData(){
        Log.d("loginChauffeur","load data ");

        ColisManager colisManager = new ColisManager(getActivity());
        colisManager.open();
        listeColis = colisManager.getNextColis(idTournee);
        colisManager.close();
        int nbLivraison=0;
        int nbReception=0;
        for(int i =0; i<listeColis.size();i++){

            if(listeColis.get(i).getCurrentOperation() instanceof Reception){
                nbReception++;
            }
            else{
                nbLivraison++;
            }
        }

        if(listeColis.size()>0){
            colis=listeColis.get(0);
            tvClient.setText(" "+colis.getCurrentOperation().getClient().getNom_client());
            tvAdresse.setText(" "+colis.getCurrentOperation().getAdresse().getRue());
            tvVille.setText(" "+colis.getCurrentOperation().getAdresse().getVille());
            tvTelephone.setText(colis.getCurrentOperation().getClient().getTelephone_client());
        }
        else{
            tvClient.setText(" Fin de la tournée ");
            tvAdresse.setText("");
            tvVille.setText("");
            tvTelephone.setText("");
        }

        tvNombre.setText(nbLivraison+ " colis à livrer \n"+ nbReception + " colis à récuperer");
    }

    public void onMapReady(GoogleMap googleMap) {
        //googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        myMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        myMap.setMyLocationEnabled(true);

        //affiche bouton + - zoom
        myMap.getUiSettings().setZoomControlsEnabled(true);

        //récupération position
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        //affiche bousolle en haut à gauche
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);//locationManager.getBestProvider(criteria, false));
        if (location != null) {
            myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(18)                   // Sets the zoom
                    // .bearing(90)                // Sets the orientation of the camera to east
                    //.tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
           // myMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            Log.d("localisation", "location false : "+location.toString());

        } else {
            Log.d("location", "location false");
        }

        //lancement tracage itinéraire
        if(colis!=null ) {
            String url =makeURL(location.getLatitude(), location.getLongitude(),
                    colis.getCurrentOperation().getAdresse().getLatlng().getLatitude(), colis.getCurrentOperation().getAdresse().getLatlng().getLongitude());
            connectAsyncTask test = new connectAsyncTask(url, getActivity(),false);
            test.execute();

        }

    }

    public String makeURL(double sourcelat, double sourcelog, double destlat, double destlog) {
        StringBuilder urlString = new StringBuilder();
        urlString.append("https://maps.googleapis.com/maps/api/directions/json");
        urlString.append("?origin=");// from
        urlString.append(Double.toString(sourcelat));
        urlString.append(",");
        urlString
                .append(Double.toString(sourcelog));
        urlString.append("&destination=");// to
        urlString
                .append(Double.toString(destlat));
        urlString.append(",");
        urlString.append(Double.toString(destlog));
        urlString.append("&sensor=false&mode=driving&alternatives=true");
        urlString.append("&key=AIzaSyAAj3h7P5IjBNuSv-hLNeYs5yZgVzf11F4");
        return urlString.toString();
    }

    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    public void drawPath(String result) {

        try {
            //Tranform the string into a json object
            final JSONObject json = new JSONObject(result);
            Log.d("location000", "location false "+result.toString());
            JSONArray routeArray = json.getJSONArray("routes");
            JSONObject routes = routeArray.getJSONObject(0);
            Log.d("location000", "location object 0 "+routeArray.getJSONObject(0).toString());
            Log.d("location000", "location object bounds "+routeArray.getJSONObject(0).getJSONObject("bounds").toString());
            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
            String encodedString = overviewPolylines.getString("points");

            Double northLat=routeArray.getJSONObject(0).getJSONObject("bounds").getJSONObject("northeast").getDouble("lat");
            Double northLng=routeArray.getJSONObject(0).getJSONObject("bounds").getJSONObject("northeast").getDouble("lng");
            Double southLat=routeArray.getJSONObject(0).getJSONObject("bounds").getJSONObject("southwest").getDouble("lat");
            Double southLng=routeArray.getJSONObject(0).getJSONObject("bounds").getJSONObject("southwest").getDouble("lng");
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(new LatLng(northLat,northLng));
            builder.include(new LatLng(southLat,southLng));
            bounds = builder.build();
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 100);
            myMap.moveCamera(cu);


            List<LatLng> list = decodePoly(encodedString);
            myMap.clear();
            Polyline line = myMap.addPolyline(new PolylineOptions()
                    .addAll(list)
                    .width(10)
                    .color(Color.parseColor("#05b1fb"))//Google maps blue color
                    .geodesic(true)
            );

        } catch (JSONException e) {

        }
    }


    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }


    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void update() {

    }

    public  class connectAsyncTask extends AsyncTask<Void, Void, String> {
        private ProgressDialog progressDialog;
        String url;
        Context context;
        boolean dialog;

        connectAsyncTask(String urlPass, Context context, boolean dialog){
            url = urlPass;
            this.context=context;
            this.dialog=dialog;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(dialog) {
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Fetching route, Please wait...");
                progressDialog.setIndeterminate(true);
                progressDialog.show();
            }
        }
        @Override
        protected String doInBackground(Void... params) {
            JSONParser jParser = new JSONParser();
            String json = jParser.getJSONFromUrl(url);
            return json;
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(dialog)progressDialog.dismiss();
            if(result!=null){
                drawPath(result);
            }
        }
    }

}
