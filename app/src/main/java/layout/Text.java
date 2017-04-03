package layout;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nakul.projectone.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.io.IOException;
import java.util.List;

public class Text extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    Button save;
    EditText homeloc, name, homerad;
    TextView tv;
    SharedPreferences sharedpreferences;
    double lat, lon, clat, clon;
    String uname, home, radius;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedpreferences = getContext().getSharedPreferences("Details", Context.MODE_PRIVATE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text, container, false);
        save = (Button) view.findViewById(R.id.save);
        name = (EditText) view.findViewById(R.id.name);
        homeloc = (EditText) view.findViewById(R.id.homeloc);
        homerad = (EditText) view.findViewById(R.id.rad);
        tv = (TextView) view.findViewById(R.id.tv);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor edit = sharedpreferences.edit();
                uname = name.getText().toString();
                home = homeloc.getText().toString();
                radius = homerad.getText().toString();
                edit.putString("Name", uname);
                edit.putString("Home", home);
                edit.putString("Radius", radius);
                edit.commit();
                findloc(home);


            }
        });
        return view;
    }

    public void findloc(String loc) {
        Geocoder geo = new Geocoder(getContext());
        List<Address> add = null;
        if (Geocoder.isPresent()) {
            try {
                add=geo.getFromLocationName(loc,1);
                if (add.size() == 0) {
                    add = geo.getFromLocationName(loc, 1);
                    Toast.makeText(getContext(), "Place not found. Try something more appropriate. ", Toast.LENGTH_SHORT).show();
                    return;
                }
                Address addr = add.get(0);
                lat = addr.getLatitude();
                lon = addr.getLongitude();
                SharedPreferences.Editor edit = sharedpreferences.edit();
                edit.putString("Latitude", String.valueOf(lat));
                edit.putString("Longitude", String.valueOf(lon));
                Toast.makeText(getContext(), "Home location saved.", Toast.LENGTH_SHORT).show();
                edit.commit();
            } catch (IOException e) {
                Toast.makeText(getContext(), "Network Geocoder not workng", Toast.LENGTH_SHORT).show();
            } catch (IllegalArgumentException e) {
                Toast.makeText(getContext(), "No Location Entered", Toast.LENGTH_SHORT).show();
            }

        }
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Text View");
    }

    @Override
    public void onLocationChanged(Location location) {
        double d=distance(lat,lon,location.getLatitude(),location.getLongitude());
        tv.setText(uname+", you are "+d+" kms. from your home location.");

    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta =(lon1-lon2);
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
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
    public void onConnected(Bundle bundle) {



    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
