package com.example.proyectochat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {


    private CircleImageView fotoPerfil;
    private CircleImageView fotoPerfilmensaje;
    private TextView nombre;
    private RecyclerView rvMensajes;
    private EditText txtMensaje;
    private Button btnEnviar;
    private ImageButton btnEnviarFotos;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private static final int PHOTO_SEND = 1;
    private static final int PHOTO_PERFIL = 2;
    private String fotoPerfilCadena, linkfoto;
    private AdaterMensajes adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fotoPerfil = (CircleImageView) findViewById(R.id.fotoPerfil);
        fotoPerfilmensaje = (CircleImageView) findViewById(R.id.fotoPerfilMensaje);
        nombre = (TextView) findViewById(R.id.nombre);
        rvMensajes = (RecyclerView) findViewById(R.id.rvMensajes);
        txtMensaje = (EditText) findViewById(R.id.txMensajes);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);
        btnEnviarFotos = (ImageButton) findViewById(R.id.btnEnviarFoto);
        fotoPerfilCadena = "";

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("chat");//sala de chat(nombre)
        storage = FirebaseStorage.getInstance();

        adapter = new AdaterMensajes(this);
        LinearLayoutManager l = new LinearLayoutManager(this);
        rvMensajes.setLayoutManager(l);
        rvMensajes.setAdapter(adapter);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.push().setValue(new MensajeEnviar(txtMensaje.getText().toString(), nombre.getText().toString(), fotoPerfilCadena, "1", ServerValue.TIMESTAMP));
                txtMensaje.setText("");

            }
        });
        btnEnviarFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(i, "Seleccionar una Foto"), PHOTO_SEND);
            }
        });


        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(i, "Seleccionar una Foto"), PHOTO_PERFIL);
            }
        });


        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollbar();
            }
        });


        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                MensajeRecivir m = snapshot.getValue(MensajeRecivir.class);
                adapter.Addmensaje(m);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setScrollbar() {
        rvMensajes.scrollToPosition(adapter.getItemCount() - 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_SEND && resultCode == RESULT_OK) {
            Uri u = data.getData();
            storageReference = storage.getReference("imagenes_chat");//imagenes _chat
            final StorageReference fotoRefencia = storageReference.child(u.getLastPathSegment());
            fotoRefencia.putFile(u).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> u = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    u.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            linkfoto = uri.toString();
                            MensajeEnviar m = new MensajeEnviar("Usuario2 te ha enviado una foto ", linkfoto , nombre.getText().toString(), fotoPerfilCadena, "2", ServerValue.TIMESTAMP);
                            databaseReference.push().setValue(m);
                        }
                    });
                }
            });

        } else if (requestCode == PHOTO_PERFIL && resultCode == RESULT_OK) {
            Uri u = data.getData();
            storageReference = storage.getReference("foto_perfil");//imagenes _chat
            final StorageReference fotoRefencia = storageReference.child(u.getLastPathSegment());
            fotoRefencia.putFile(u).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> u = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    u.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            linkfoto = uri.toString();
                            fotoPerfilCadena = uri.toString();
                            MensajeEnviar m = new MensajeEnviar("Usuario2 a actualizado su foto de perfil ", linkfoto , nombre.getText().toString(), fotoPerfilCadena, "2", ServerValue.TIMESTAMP);
                            databaseReference.push().setValue(m);
                            Glide.with(MainActivity.this).load(fotoPerfilCadena).into(fotoPerfil);
                        }
                    });
                }
            });
        }
    }
}