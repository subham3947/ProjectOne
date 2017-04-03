package layout;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.nakul.projectone.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {


        GoogleMap gmap;
    LatLng location;
    int radius;
   double lat,lon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferences sharedpreferences = getContext().getSharedPreferences("Details", Context.MODE_PRIVATE);
        lat=Double.parseDouble(sharedpreferences.getString("Latitude","65.90"));
        lon=Double.parseDouble(sharedpreferences.getString("Longitude","65.90"));
        radius=Integer.parseInt(sharedpreferences.getString("Radius","0"));
        location=new LatLng(lat,lon);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Map View");
        /*Bundle args=getArguments();
        *//*try{
             geoloc = args.getString("HOME");
            radius=args.getInt("Radius");
            System.out.print(geoloc);
        }catch (Exception e){
            Toast.makeText(getContext(), "Fill in the details first!!", Toast.LENGTH_SHORT).show();
        }*/

        return inflater.inflate(R.layout.fragment_map, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment fragment=(SupportMapFragment)this.getChildFragmentManager().findFragmentById(R.id.mapView);
        fragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        gmap=googleMap;
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            gmap.setMyLocationEnabled(true);

        }
        gmap.addMarker(new MarkerOptions().position(location).title("Home"));
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 14.0f));
        Circle circle= gmap.addCircle(new CircleOptions()
                .center(new LatLng(lat,lon))
                .radius(radius)
                .strokeColor(Color.rgb(92,152,255))
                .fillColor(Color.TRANSPARENT));

    }

}
