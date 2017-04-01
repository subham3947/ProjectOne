package layout;

import android.Manifest;
import android.content.Context;
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
    String geoloc;
    int radius;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Map View");
        Bundle args=getArguments();
        try{
             geoloc = args.getString("HOME");
            radius=args.getInt("Radius");
            System.out.print(geoloc);
        }catch (Exception e){
            Toast.makeText(getContext(), "Fill in the details first!!", Toast.LENGTH_SHORT).show();
        }

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
        Geocoder geo=new Geocoder(getContext());
        List<Address> add=null;
        if(Geocoder.isPresent()){
            try{
                 add=geo.getFromLocationName(geoloc,1);
                if (add.size()==0){
                    Toast.makeText(getContext(), "Place not found. Try something more appropriate. ", Toast.LENGTH_SHORT).show();
                    return;
                }
                Address addr=add.get(0);
                LatLng location=new LatLng(addr.getLatitude(),addr.getLongitude());
                gmap.addMarker(new MarkerOptions().position(location).title("Home"));
                gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 14.0f));
                Circle circle = gmap.addCircle(new CircleOptions()
                        .center(new LatLng(addr.getLatitude(),addr.getLongitude()))
                        .radius(radius)
                        .strokeColor(Color.rgb(92,152,255))
                        .fillColor(Color.TRANSPARENT));
            } catch (IOException e) {
                Toast.makeText(getContext(),"Network Geocoder not workng", Toast.LENGTH_SHORT).show();
            }
            catch (IllegalArgumentException e) {
                Toast.makeText(getContext(), "No Location Entered", Toast.LENGTH_SHORT).show();
            }


        }
    }

   /* public void gotoPlace(String geoloc) {


    }*/


}
