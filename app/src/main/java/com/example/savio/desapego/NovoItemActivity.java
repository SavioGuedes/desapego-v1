package com.example.savio.desapego;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.savio.desapego.adapters.NovoItemAdapter;
import com.example.savio.desapego.api.model.Item;
import com.example.savio.desapego.helpers.ItemsHelper;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class NovoItemActivity extends AppCompatActivity {

    RecyclerView recyclerview;
    RecyclerView.LayoutManager layoutmanager;
    RecyclerView.Adapter adapter;

    ArrayList<Bitmap> fotos_galeria;

    ImageView novo_item_btn_img;

    public static final int RESULT_GALLERY = 0;
    private TextView compartilhar_item_tv;
    private TextView novo_item_titulo;
    private TextView novo_item_desc;
    private ItemsHelper itemsHelper;
    private ArrayList<Uri> arrayUri;
    private StorageReference storageRef;

//------------------ciclo de vida------------------------------------------------------------------------//


    @Override
    protected void onResume() {
        super.onResume();
        //publicar até 4 imagens
        if (adapter != null && adapter.getItemCount() == 4) {

            novo_item_btn_img.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_item);
        //Cria a referencia para o firebase
        storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://desapego-198301.appspot.com/");
        arrayUri = new ArrayList<>();
        itemsHelper = new ItemsHelper(NovoItemActivity.this);
        fotos_galeria = new ArrayList<>();
        //configuração do recyclerview
        recyclerview = (RecyclerView) findViewById(R.id.novo_item_lista);
        recyclerview.setHasFixedSize(true);
        layoutmanager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerview.setLayoutManager(layoutmanager);
        novo_item_titulo = (TextView) findViewById(R.id.novo_item_titulo);
        novo_item_desc = (TextView) findViewById(R.id.novo_item_descricao);

        compartilhar_item_tv = (TextView) findViewById(R.id.novo_item_compartilhar);
        novo_item_btn_img = (ImageView) findViewById(R.id.novo_item_btn_img);
//------------------Listeres------------------------------------------------------------------------//

        novo_item_btn_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pegarFotos(); //coleta as informações que serão passadas para o adapter
            }
        });

        compartilhar_item_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fotos_galeria.size() > 0 && !TextUtils.isEmpty(novo_item_titulo.getText()) && !TextUtils.isEmpty(novo_item_desc.getText())) {
                    // faz a chamada à API

                    Item item = new Item();
                    item.setName(novo_item_titulo.getText().toString());
                    item.setDescription(novo_item_desc.getText().toString());
                    itemsHelper.newItem(storageRef, arrayUri, item);
                }
            }
        });


    }//fim do onCreate


//------------------Para facilitar minha vida-------------------------------------------------------//

    //abre a tela de galeria do android
    private void pegarFotos() {

        Intent galleryIntent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_GALLERY);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case RESULT_GALLERY:

                if (data != null) {

                    try {

                        //retorna a foto da galeria
                        final Uri imagemUri = data.getData();
                        //Salva o endereço para enviar pro Helper mais tarde
                        arrayUri.add(imagemUri);

                        if (imagemUri != null) {

                            //converte em bitmap
                            Bitmap imagem = BitmapFactory.decodeStream(getContentResolver().openInputStream(imagemUri));

                            //abre as configurações de bitmap, e reduz a qualidade da imagem
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = calculateInSampleSize(imagem.getWidth(), imagem.getHeight());
                            options.inJustDecodeBounds = false;

                            //inserir em um bitmap de novo com qualidade reduzida
                            Bitmap imagem_reduzida = BitmapFactory.decodeStream(
                                    getContentResolver().openInputStream(imagemUri),
                                    null,
                                    options);

                            //manda a imagem para o adapter
                            fotos_galeria.add(imagem_reduzida);
                            adapter = new NovoItemAdapter(fotos_galeria);

                            //atualiza o adapter
                            recyclerview.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }

                break;

            default:
                break;
        }
    }


//------------------Retiradas do Android Docs-------------------------------------------------------//

    //calcula a redução da iamgem de acordo com seu tamanho
    //https://developer.android.com/topic/performance/graphics/load-bitmap.html#load-bitmap
    public static int calculateInSampleSize(int outWidth, int outHeight) {

        // Raw height and width of image
        final int height = outHeight;
        final int width = outWidth;
        int inSampleSize = 1;

        if (height > 640 || width > 480) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= 640
                    && (halfWidth / inSampleSize) >= 480) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
