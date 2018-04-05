package plant.kz.aygolek.plantyoutube.gpsaplant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import plant.kz.aygolek.plantyoutube.R;

public class activity_color_capture extends PlantPlaecesActivity implements View.OnClickListener {

    private Button button;

    public static final int IMAGE_GALLERY_REQUEST = 20;
    private ImageView imgPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_capture);
        button = (Button)findViewById(R.id.btnImageGallery);
                button.setOnClickListener(this);

        imgPicture = (ImageView) findViewById(R.id.imgPicture);
    }


    @Override
    public int getCurrentMenuId() {
        return R.id.capturecolor;
    }

    public void  onImageGalleryClicked(){
        Toast.makeText(this, "button clicked", Toast.LENGTH_SHORT).show();
        //invoke image gallery with implicit intent, parameter, I want to pick something

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnImageGallery:
                Toast.makeText(this, "button clicked", Toast.LENGTH_SHORT).show();
                Intent photoPickerIntent=new Intent(Intent.ACTION_PICK);
                //where to find that
                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String pictureDirectoryPath = pictureDirectory.getPath();
                //finally get URI
                Uri data= Uri.parse(pictureDirectoryPath);
                //set the data and type, wo und welche daten bei starten des intents sollen angezeigt werden.
                //setdata wird meistens dazu verwernde um auf etwas zu zeigen
                photoPickerIntent.setDataAndType(data,"image/*");

                startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK){
            if(requestCode == IMAGE_GALLERY_REQUEST){
                Uri imageUri = data.getData();

                InputStream inputStream;

                try {
                    inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);

                    imgPicture.setImageBitmap(imageBitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "unable to open image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
