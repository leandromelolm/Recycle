package com.pdm.recycle.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;


import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.ui.AppBarConfiguration;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pdm.recycle.R;
import com.pdm.recycle.control.ConfiguracaoFirebase;
import com.pdm.recycle.helper.Base64Custom;
import com.pdm.recycle.model.Descarte;

import java.util.ArrayList;
import java.util.List;

public class MainHomeActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int FINE_LOCATION_REQUEST = 1;
    private boolean fine_location;

    private Double latitude;
    private Double longitude;

    private List<Descarte> listdescarte = new ArrayList<Descarte>();
    private ArrayAdapter<Descarte> arrayAdapterDescarte;
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

    private AppBarConfiguration appBarConfiguration;
    // private FirebaseAuth autenticacao;
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

    // private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabaseReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Recycle");
        setSupportActionBar(toolbar);

        inicializarComponentes();

        requestPermission();
    }

    private void requestPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        this.fine_location = (permissionCheck == PackageManager.PERMISSION_GRANTED);
        if (this.fine_location) return;
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                FINE_LOCATION_REQUEST);
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean granted = (grantResults.length > 0) &&
                (grantResults[0] == PackageManager.PERMISSION_GRANTED);
        this.fine_location = (requestCode == FINE_LOCATION_REQUEST) && granted;

        if (mMap != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(this.fine_location);
        }
    }

    private void recuperarLocaisDescarte() {

        DatabaseReference descartes = referencia.child("descartes");

        descartes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Log.i("FIREBASE", snapshot.getValue().toString());
                for (DataSnapshot objSnapshot:snapshot.getChildren()){
                    Descarte d = objSnapshot.getValue(Descarte.class);
                    longitude = d.getLongitude();
                    latitude = d.getLatitude();
                    LatLng localDescarte = new LatLng(latitude,longitude);
                    Log.i("Teste_X", localDescarte.toString());
                    mMap.addMarker(
                            new MarkerOptions()
                                    .position(localDescarte)
                                    .title("Local")
                                    .snippet("Descrição")
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icons8_trash_can_24))
                    );
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng ifRecife = new LatLng(-8.058320, -34.950611);
        // mMap.addMarker(new MarkerOptions().position(ifRecife).title("Local"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ifRecife));

        recuperarLocaisDescarte();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mainhome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch ((item.getItemId())){
            case R.id.menuSair:
                autenticacao.signOut();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void redirectDescarte(View v) {
        Intent intent = new Intent(this, DescarteActivity.class);
        startActivity(intent);
    }

    private void inicializarComponentes(){

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }
}