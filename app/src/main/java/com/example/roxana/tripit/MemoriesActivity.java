package com.example.roxana.tripit;

/**
 * Created by Roxana on 5/13/2017.
 */

        import android.app.Activity;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.net.Uri;
        import android.os.Bundle;
        import android.provider.MediaStore;
        import android.support.design.widget.FloatingActionButton;
        import android.support.design.widget.Snackbar;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.View;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.widget.GridView;
        import android.widget.ImageView;

public class MemoriesActivity extends AppCompatActivity {

    ImageView ivImage;
    Integer REQUEST_CAMERA = 1;

    Integer SELECT_FILE = 0;
    public final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memories);


        ivImage = (ImageView) findViewById(R.id.ivImage);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                SelectImage();

            }
        });
    }
    private void SelectImage() {

        final CharSequence[] items={"Camera","Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(MemoriesActivity.this);
        builder.setTitle("Add Image");

        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Camera")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);

                } else if (items[i].equals("Gallery")) {

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    //startActivityForResult(intent.createChooser(intent, "Select File"), SELECT_FILE);
                    startActivityForResult(intent, SELECT_FILE);

                } else if (items[i].equals("Cancel")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();

    }

    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode== Activity.RESULT_OK) {

            if(requestCode==REQUEST_CAMERA){

                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                ivImage.setImageBitmap(bmp);

            }else if(requestCode==SELECT_FILE){

                Uri selectedImageUri = data.getData();
                ivImage.setImageURI(selectedImageUri);
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings)
            return true;
        if (id == R.id.memories) {
            Intent intent = new Intent(getApplicationContext(), MemoriesActivity.class);
            //intent.putExtra("wish",inputString);
            startActivity(intent);
            return true;
        }
        if (id == R.id.maps) {
            Intent intent = new Intent(getApplicationContext(), MapsMainActivity.class);
            //intent.putExtra("wish",inputString);
            startActivity(intent);
            return true;
        }
        if (id == R.id.docs) {
            Intent intent = new Intent(getApplicationContext(), DocsActivity.class);
            //intent.putExtra("wish",inputString);
            startActivity(intent);
            return true;
        }
        if (id == R.id.budget) {
            Intent intent = new Intent(getApplicationContext(), BudgetActivity.class);
            //intent.putExtra("wish",inputString);
            startActivity(intent);
            return true;
        }
        if (id == R.id.meteo) {
            Intent intent = new Intent(getApplicationContext(), MeteoActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.exit) {
            finish();
            moveTaskToBack(true);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


}
