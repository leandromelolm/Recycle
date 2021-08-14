package com.pdm.recycle.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.ui.AppBarConfiguration;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pdm.recycle.R;
import com.pdm.recycle.control.ConfiguracaoFirebase;
import com.pdm.recycle.helper.Base64Custom;
import com.pdm.recycle.model.Coleta;
import com.pdm.recycle.model.Descarte;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainHomeActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        OnInfoWindowClickListener,
        GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private static final int FINE_LOCATION_REQUEST = 1;
    private boolean fine_location;
    private Double latitude;
    private Double longitude;
    private String tipoResiduo,status,dataDescarte;
    private ArrayList<Descarte> listDescarte;
    private DataSnapshot locaisDescarte;
    private String idDescarte;
    private String userEmail;
    private String emailUserAutenticado;
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private AppBarConfiguration appBarConfiguration;
    //private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private FirebaseAuth autenticacao;
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

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        FirebaseUser usuarioAtual =  autenticacao.getCurrentUser();
        emailUserAutenticado = usuarioAtual.getEmail();

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

                /* Salvando os dados do firebase na varíável locaisDescarte */
                locaisDescarte = snapshot;
                //Log.i("FIREBASE", snapshot.getValue().toString());

                /* Limpar os marcadores no Mapa*/
                mMap.clear();

                for (DataSnapshot objSnapshot:snapshot.getChildren()) {
                    Descarte descarte = objSnapshot.getValue(Descarte.class);
                    String descarteID = objSnapshot.getKey();
                    tipoResiduo = descarte.getTipoResiduo();
                    latitude = descarte.getLatitude();
                    longitude = descarte.getLongitude();
                    status = descarte.getStatus();
                    dataDescarte = descarte.getDataDescarte();
                    idDescarte = descarteID;
                    userEmail =  descarte.getUserEmail();
                    LatLng localDescarte = new LatLng(latitude, longitude);

                    Log.i("local_descarte", localDescarte.toString());

                    if (status.startsWith("Não Coletado")) {
                        Marker marker = mMap.addMarker(
                                new MarkerOptions()
                                        .position(localDescarte)
                                        .title("Tipo de resíduo: " + tipoResiduo)
                                        .snippet("Data Descarte: " + dataDescarte +
                                                "\n Quem Descartou: " + userEmail +
                                                "\n Coordenada Descarte: " + localDescarte)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icons8_recycle_24))
                        );
                        marker.hideInfoWindow();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d("Erro ", error.getMessage());
            }
        });
    }

    /* Método atualiza o status do descarte no firebase para "Coletado */
    private void informarColetaResiduo(LatLng position){

        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabaseReference();
        DatabaseReference descartes =  firebaseRef.child("descartes");

        Log.i("FIREBASE", locaisDescarte.getValue().toString());

        String identificadorDescarte = Base64Custom.codificarBase64(String.valueOf(position));

        Log.i("coletarResiduoDescartado: ",identificadorDescarte );

        for (DataSnapshot objSnapshot:locaisDescarte.getChildren()){
            Descarte descarte = objSnapshot.getValue(Descarte.class);
            String descarteID = objSnapshot.getKey();
            status="Coletado";
            Log.i(" status coletado: ", descarteID);

            /* atualizando apenas o status no firebase */
            descartes.child(identificadorDescarte).child("status").setValue(status);
            Toast.makeText(this, "Coleta Informada!",Toast.LENGTH_LONG).show();

            if(identificadorDescarte.matches(descarteID)){
                Coleta coleta = new Coleta();
                coleta.setTipoResiduo(descarte.getTipoResiduo());
                coleta.setDataDescarte(descarte.getDataDescarte());
                coleta.setLatitude(descarte.getLatitude());
                coleta.setLongitude(descarte.getLongitude());
                coleta.setUserEmail(emailUserAutenticado);

                Date data = new Date(System.currentTimeMillis());
                SimpleDateFormat formatarDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                formatarDate.format(data);

                coleta.setDataColeta(formatarDate.format(data));

                coleta.salvarColeta();
            }
        }
    }

    /* Método atualiza o status do descarte no firebase para "Não Encontrado */
    private void informarNaoEncontrado(LatLng position){

        DatabaseReference descartes =  firebaseRef.child("descartes");
        String identificadorDescarte = Base64Custom.codificarBase64(String.valueOf(position));

        for (DataSnapshot objSnapshot:locaisDescarte.getChildren()){
            String descarteID = objSnapshot.getKey();
            status="Não Encontrado";
            Log.i(" ID status no found: ", descarteID);

            /* atualiza apenas o status no firebase */
            descartes.child(identificadorDescarte).child("status").setValue(status);
            Toast.makeText(this, "Reportado!",Toast.LENGTH_LONG).show();
        }
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
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ifRecife, 13));

        recuperarLocaisDescarte();
        mMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        //Toast.makeText(this, "Teste Classe onInfoWindowClick ",
        // Toast.LENGTH_SHORT).show();

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("O que deseja fazer com o resíduo?");
        builder.setMessage(marker.getTitle() +
                " \n " + marker.getSnippet());

        // add the buttons
        builder.setPositiveButton("Coletar", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i){
                informarColetaResiduo(marker.getPosition());
            }
        });
        builder.setNeutralButton("Não encontrado", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i){
                informarNaoEncontrado(marker.getPosition());
            }
        });
        builder.setNegativeButton("Cancelar", null);

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
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

    @Override
    public void onMapClick( LatLng latLng) {

    }

}