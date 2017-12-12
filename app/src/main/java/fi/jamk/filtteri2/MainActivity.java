package fi.jamk.filtteri2;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.Layout;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.view.View.OnTouchListener;

import com.xiaopo.flying.sticker.StickerView;
import com.xiaopo.flying.sticker.TextSticker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.R.attr.bitmap;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.graphics.Color.WHITE;
import static android.graphics.Color.argb;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_REQUEST = 1;
    private static final int RESULT_LOAD_IMAGE = 0;

    private final int TAKE_PICTURE = 1; //lisätty

    Button b_save, b_share, b_popup, b_popup2,b_ready,b_popup3, b_reset, b_back, b_restart;
    private ViewGroup rootLayout;


    ImageView imageView, imageFilter, hat1, hat2, hat3, hat4, imageBorder;
    String currentFileName = "";//lisätty
    Matrix matrix = new Matrix();
    Float scale = 1f;
    ScaleGestureDetector SGD;
    private int _xDelta;
    private int _yDelta;

    Drawable drawable;
    Bitmap bitmap;
    Bitmap kuva;
    Integer imageHeight = 0;
    Integer imageWidth = 0;
    Uri bitmapUri;

    Drawable scaled;
    Drawable unscaled;
    Bitmap bm;


    String currentImage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootLayout = (ViewGroup) findViewById(R.id.lay);
        hat1 = (ImageView) rootLayout.findViewById(R.id.hat1);
        hat2 = (ImageView) rootLayout.findViewById(R.id.hat2);
        hat3 = (ImageView) rootLayout.findViewById(R.id.hat3);
        hat4 = (ImageView) rootLayout.findViewById(R.id.hat4);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(1200, 1200);
        hat1.setLayoutParams(layoutParams);
        hat1.setOnTouchListener(new ChoiceTouchListener());
        hat2.setLayoutParams(layoutParams);
        hat2.setOnTouchListener(new ChoiceTouchListener());
        hat3.setLayoutParams(layoutParams);
        hat3.setOnTouchListener(new ChoiceTouchListener());
        hat4.setLayoutParams(layoutParams);
        hat4.setOnTouchListener(new ChoiceTouchListener());

        hat1.setVisibility(View.INVISIBLE);
        hat2.setVisibility(View.INVISIBLE);
        hat3.setVisibility(View.INVISIBLE);
        hat4.setVisibility(View.INVISIBLE);

        final MediaPlayer fedorasound = MediaPlayer.create(this, R.raw.fedora_sound);
        final MediaPlayer copsound = MediaPlayer.create(this, R.raw.cop_sound);
        final MediaPlayer christmassound = MediaPlayer.create(this, R.raw.christmas_sound);
        final MediaPlayer weebsound = MediaPlayer.create(this, R.raw.weeb_sound);
        final MediaPlayer buttonsound = MediaPlayer.create(this, R.raw.button_sound);
        final MediaPlayer buttonsound2 = MediaPlayer.create(this, R.raw.button_sound);
        final MediaPlayer buttonsound3= MediaPlayer.create(this, R.raw.button_sound);
        final MediaPlayer buttonsound4 = MediaPlayer.create(this, R.raw.button_sound);
        final MediaPlayer buttonsound5 = MediaPlayer.create(this, R.raw.button_sound);
        final MediaPlayer buttonsound6 = MediaPlayer.create(this, R.raw.button_sound);
        final MediaPlayer buttonsound7 = MediaPlayer.create(this, R.raw.button_sound);
        final MediaPlayer buttonsound8 = MediaPlayer.create(this, R.raw.button_sound);
        final MediaPlayer buttonsound9 = MediaPlayer.create(this, R.raw.button_sound);
        final MediaPlayer buttonsound10 = MediaPlayer.create(this, R.raw.button_sound);
        final MediaPlayer buttonsound11 = MediaPlayer.create(this, R.raw.button_sound);
        final MediaPlayer buttonsound12 = MediaPlayer.create(this, R.raw.button_sound);
        final MediaPlayer buttonsound13 = MediaPlayer.create(this, R.raw.button_sound);
        final MediaPlayer buttonsound15 = MediaPlayer.create(this, R.raw.button_sound);
        final MediaPlayer buttonsound17 = MediaPlayer.create(this, R.raw.button_sound);
        final MediaPlayer buttonsound18 = MediaPlayer.create(this, R.raw.button_sound);
        final MediaPlayer buttonsound19 = MediaPlayer.create(this, R.raw.button_sound);
        final MediaPlayer buttonsound20 = MediaPlayer.create(this, R.raw.button_sound);
        final MediaPlayer buttonsound21 = MediaPlayer.create(this, R.raw.button_sound);







        //storage oikeudet
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);

            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            }

        } else {
            // ei mitään
        }



        imageView = (ImageView) findViewById(R.id.imageView);
        imageFilter = (ImageView) findViewById(R.id.imageFilter);
        imageBorder = (ImageView) findViewById(R.id.imageBorder);
        hat1 = (ImageView) findViewById(R.id.hat1);
        hat2 = (ImageView) findViewById(R.id.hat2);
        hat3 = (ImageView) findViewById(R.id.hat3);
        hat4 = (ImageView) findViewById(R.id.hat4);

        SGD = new ScaleGestureDetector(this, new ScaleListener());

        b_popup = (Button) findViewById(R.id.b_popup);
        b_popup2 = (Button) findViewById(R.id.b_popup2);
        b_popup3 = (Button) findViewById(R.id.b_popup3);
        b_ready = (Button) findViewById(R.id.b_ready);
        b_reset = (Button) findViewById(R.id.b_reset);
        b_share = (Button) findViewById(R.id.b_share);
        b_save = (Button) findViewById(R.id.b_save);
        b_back = (Button) findViewById(R.id.b_back);
        b_restart = (Button) findViewById(R.id.b_restart);


        b_popup.setEnabled(true);
        b_popup2.setEnabled(true);
        b_popup3.setEnabled(true);
        b_reset.setEnabled(true);
        b_ready.setEnabled(true);
        b_save.setEnabled(false);
        b_share.setEnabled(false);
        b_back.setEnabled(false);
        b_restart.setEnabled(false);

        b_popup.setVisibility(View.VISIBLE);
        b_popup2.setVisibility(View.VISIBLE);
        b_popup3.setVisibility(View.VISIBLE);
        b_reset.setVisibility(View.VISIBLE);
        b_ready.setVisibility(View.VISIBLE);
        b_share.setVisibility(View.INVISIBLE);
        b_save.setVisibility(View.INVISIBLE);
        b_back.setVisibility(View.INVISIBLE);
        b_restart.setVisibility(View.INVISIBLE);




        if(getIntent().getStringExtra("id").equals("TAKE")){
            // Haetaan edellisestä activitystä kuva
            // Jos käyttäjä ottaa kuvan
            try {

                final String imageUri = getIntent().getStringExtra("imagePath");
                Uri fileUri = Uri.parse(imageUri);

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(new File(fileUri.getPath()).getAbsolutePath(), options);
                imageHeight = options.outHeight;
                imageWidth = options.outWidth;

                imageView.setImageBitmap(bitmap);

            } catch(IOException e){

            }
        }
        // jos käyttäjä lataa kuvan
        if(getIntent().getStringExtra("id").equals("LOAD")){

            final String picturePath = getIntent().getStringExtra("picture");
            Bitmap kuva = BitmapFactory.decodeFile(picturePath);
            imageHeight = kuva.getHeight();
            imageWidth = kuva.getWidth();

            imageView.setImageBitmap(kuva);

        }




        b_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final PopupMenu popupMenu = new PopupMenu(MainActivity.this, b_popup);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                buttonsound.start();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override

                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.one:

                                unscaled = getResources().getDrawable(R.drawable.filter);
                                bm = ((BitmapDrawable)unscaled).getBitmap();
                                scaled = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bm, imageWidth, imageHeight, true));
                                imageFilter.setImageDrawable(scaled);
                                buttonsound3.start();

                                break;

                            case R.id.two:

                                unscaled = getResources().getDrawable(R.drawable.filter2);
                                bm = ((BitmapDrawable)unscaled).getBitmap();
                                scaled = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bm, imageWidth, imageHeight, true));
                                imageFilter.setImageDrawable(scaled);
                                buttonsound5.start();

                                break;

                            case R.id.three:

                                unscaled = getResources().getDrawable(R.drawable.filter3);
                                bm = ((BitmapDrawable)unscaled).getBitmap();
                                scaled = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bm, imageWidth, imageHeight, true));
                                imageFilter.setImageDrawable(scaled);
                                buttonsound7.start();
                                break;

                            case R.id.four:
                                unscaled = getResources().getDrawable(R.drawable.nothing);
                                bm = ((BitmapDrawable)unscaled).getBitmap();
                                scaled = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bm, imageWidth, imageHeight, true));
                                imageFilter.setImageDrawable(scaled);
                                buttonsound8.start();
                                break;


                        }

                        return true;
                    }


                });


                popupMenu.show();
            }


        });

        b_popup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final PopupMenu popupMenu2 = new PopupMenu(MainActivity.this, b_popup2);
                popupMenu2.getMenuInflater().inflate(R.menu.popup_menu2, popupMenu2.getMenu());

                buttonsound9.start();

                popupMenu2.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override

                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.five:
                                hat1.setVisibility(View.VISIBLE);
                                hat2.setVisibility(View.INVISIBLE);
                                hat3.setVisibility(View.INVISIBLE);
                                hat4.setVisibility(View.INVISIBLE);
                                fedorasound.start();
                                break;

                            case R.id.six:
                                hat1.setVisibility(View.INVISIBLE);
                                hat2.setVisibility(View.VISIBLE);
                                hat3.setVisibility(View.INVISIBLE);
                                hat4.setVisibility(View.INVISIBLE);
                                copsound.start();
                                break;

                            case R.id.seven:
                                hat1.setVisibility(View.INVISIBLE);
                                hat2.setVisibility(View.INVISIBLE);
                                hat3.setVisibility(View.VISIBLE);
                                hat4.setVisibility(View.INVISIBLE);
                                christmassound.start();
                                break;

                            case R.id.eight:
                                hat1.setVisibility(View.INVISIBLE);
                                hat2.setVisibility(View.INVISIBLE);
                                hat3.setVisibility(View.INVISIBLE);
                                hat4.setVisibility(View.VISIBLE);
                                weebsound.start();
                                break;

                            case R.id.nohat:
                                hat1.setVisibility(View.INVISIBLE);
                                hat2.setVisibility(View.INVISIBLE);
                                hat3.setVisibility(View.INVISIBLE);
                                hat4.setVisibility(View.INVISIBLE);
                                buttonsound10.start();
                                break;

                        }
                        return true;
                    }


                });


                popupMenu2.show();
            }


        });

        b_popup3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final PopupMenu popupMenu3 = new PopupMenu(MainActivity.this, b_popup3);
                popupMenu3.getMenuInflater().inflate(R.menu.popup_menu3, popupMenu3.getMenu());

                buttonsound11.start();

                popupMenu3.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override

                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.nine:
                                unscaled = getResources().getDrawable(R.drawable.christmasborder);
                                bm = ((BitmapDrawable)unscaled).getBitmap();
                                scaled = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bm, imageWidth, imageHeight, true));
                                imageBorder.setImageDrawable(scaled);
                                buttonsound13.start();
                                break;


                            case R.id.ten:
                                unscaled = getResources().getDrawable(R.drawable.presentborder);
                                bm = ((BitmapDrawable)unscaled).getBitmap();
                                scaled = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bm, imageWidth, imageHeight, true));
                                imageBorder.setImageDrawable(scaled);
                                buttonsound15.start();
                                break;



                            case R.id.eleven:
                                unscaled = getResources().getDrawable(R.drawable.romanticborder);
                                bm = ((BitmapDrawable)unscaled).getBitmap();
                                scaled = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bm, imageWidth, imageHeight, true));
                                imageBorder.setImageDrawable(scaled);
                                buttonsound17.start();
                                break;

                            case R.id.twelve:
                                unscaled = getResources().getDrawable(R.drawable.butterflyborder);
                                bm = ((BitmapDrawable)unscaled).getBitmap();
                                scaled = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bm, imageWidth, imageHeight, true));
                                imageBorder.setImageDrawable(scaled);
                                buttonsound19.start();
                                break;


                            case R.id.thirteen:
                                unscaled = getResources().getDrawable(R.drawable.nothing);
                                bm = ((BitmapDrawable)unscaled).getBitmap();
                                scaled = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bm, imageWidth, imageHeight, true));
                                imageBorder.setImageDrawable(scaled);
                                buttonsound20.start();
                                break;


                        }

                        return true;
                    }


                });


                popupMenu3.show();
            }


        });

        b_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonsound21.start();

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(1200, 1200);
                hat1.setLayoutParams(layoutParams);
                hat1.setOnTouchListener(new ChoiceTouchListener());
                hat2.setLayoutParams(layoutParams);
                hat2.setOnTouchListener(new ChoiceTouchListener());
                hat3.setLayoutParams(layoutParams);
                hat3.setOnTouchListener(new ChoiceTouchListener());
                hat4.setLayoutParams(layoutParams);
                hat4.setOnTouchListener(new ChoiceTouchListener());


            }

        });

        b_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View content = findViewById(R.id.lay);
                Bitmap bitmap = getScreenShot(content);
                currentImage = "Flying-" + fileName() + ".jpg";
                store(bitmap, currentImage);

                b_restart.setVisibility(View.VISIBLE);
                b_share.setVisibility(View.VISIBLE);
                b_save.setVisibility(View.INVISIBLE);
                b_back.setVisibility(View.INVISIBLE);
                b_restart.setEnabled(true);
                b_share.setEnabled(true);
                b_back.setEnabled(false);
                b_save.setEnabled(false);
                buttonsound2.start();
            }
        });

        b_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImage(currentImage);
                buttonsound4.start();

            }
        });

        b_ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                b_back.setVisibility(View.VISIBLE);
                b_save.setVisibility(View.VISIBLE);
                b_popup.setVisibility(View.INVISIBLE);
                b_popup2.setVisibility(View.INVISIBLE);
                b_popup3.setVisibility(View.INVISIBLE);
                b_reset.setVisibility(View.INVISIBLE);
                b_ready.setVisibility(View.INVISIBLE);

                b_popup.setEnabled(false);
                b_popup2.setEnabled(false);
                b_popup3.setEnabled(false);
                b_reset.setEnabled(false);
                b_ready.setEnabled(false);
                b_save.setEnabled(true);
                b_back.setEnabled(true);

                buttonsound12.start();

            }
        });

        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                b_back.setVisibility(View.INVISIBLE);
                b_share.setVisibility(View.INVISIBLE);
                b_save.setVisibility(View.INVISIBLE);
                b_popup.setVisibility(View.VISIBLE);
                b_popup2.setVisibility(View.VISIBLE);
                b_popup3.setVisibility(View.VISIBLE);
                b_reset.setVisibility(View.VISIBLE);
                b_ready.setVisibility(View.VISIBLE);

                b_popup.setEnabled(true);
                b_popup2.setEnabled(true);
                b_popup3.setEnabled(true);
                b_reset.setEnabled(true);
                b_ready.setEnabled(true);
                b_save.setEnabled(false);
                b_share.setEnabled(false);
                b_back.setEnabled(false);

                buttonsound6.start();

            }
        });

        b_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buttonsound18.start();

                Intent intent = new Intent(MainActivity.this,start.class);
                startActivity(intent);
            }
        });
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scale = scale * detector.getScaleFactor();
            scale = Math.max(0.1f, Math.min(scale, 20f));
            matrix.setScale(scale, scale);
            hat1.setImageMatrix(matrix);
            hat2.setImageMatrix(matrix);
            hat3.setImageMatrix(matrix);
            hat4.setImageMatrix(matrix);
            return true;

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        SGD.onTouchEvent(event);
        return true;
    }
    private class ChoiceTouchListener implements OnTouchListener{
        public boolean onTouch(View view, MotionEvent event) {
            final int X = (int) event.getRawX();
            final int Y = (int) event.getRawY();
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    _xDelta = X - lParams.leftMargin;
                    _yDelta = Y - lParams.topMargin;
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                            .getLayoutParams();
                    layoutParams.leftMargin = X - _xDelta;
                    layoutParams.topMargin = Y - _yDelta;
                    layoutParams.rightMargin = 0;
                    layoutParams.bottomMargin = 0;
                    view.setLayoutParams(layoutParams);
                    break;
            }
            rootLayout.invalidate();
            return true;
        }
    }




    //get the filtered image
    private static Bitmap getScreenShot(View view){
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }


    //kuvan tallennus
    private void store(Bitmap bm, String fileName){

        // Yritys saada kuvaa tallennettua SD-kortille, toimii emulaattorilla.
        // Lopuksi päätettiin tallentaa kuva laitteen sisäiseen muistiin.

        /*String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath();// + "/FILTTERIKUVAT";
        File dir = new File(dirPath + "/FlyingFilters");
        Toast.makeText(this, dirPath,Toast.LENGTH_SHORT).show();
        /*if(!dir.exists()){
            dir.mkdirs();
        }
        dir.mkdirs();
        File file = new File(dir, fileName);
        try{
            FileOutputStream fos = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.flush();
            fos.close();
           // Toast.makeText(this, "Saved as " + fileName, Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            e.printStackTrace();
        }*/

        MediaStore.Images.Media.insertImage(getContentResolver(), bm,fileName, "Made with Flying Filters");
        Toast.makeText(this, "Saved as " + fileName, Toast.LENGTH_SHORT).show();
    }

    // sharing the image
    private void shareImage(String fileName){
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/FILTTERIKUVAT";
        Uri uri = Uri.fromFile(new File(dirPath, fileName));
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");

        intent.putExtra(Intent.EXTRA_SUBJECT, "");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        try{
            startActivity(Intent.createChooser(intent, "Share via"));
        } catch (Exception e) {
            Toast.makeText(this, "No sharing app found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            kuva = BitmapFactory.decodeFile(picturePath);
            imageView.setImageBitmap(kuva);


        }


        //lisättyä koodia ->
        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK) {
            try {
                File file = new File(Environment.getExternalStorageDirectory(), currentFileName);

                Bitmap immutableBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));

                Bitmap photo = immutableBitmap.copy(Bitmap.Config.ARGB_8888, true);


                imageView.setImageBitmap(photo);




            } catch (FileNotFoundException e) {
                Toast.makeText(this,"Captured image not found! ",Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(this,"Captured image not found! ",Toast.LENGTH_LONG).show();
            }
        }
        //loppuu,
    }

    public String fileName(){ //tämä luo uniikin tiedostonnimen ajan mukaan...
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(new Date());
        String imageFileName = timeStamp;
        currentFileName = imageFileName;
        return imageFileName;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        switch (requestCode){
            case MY_PERMISSION_REQUEST:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                        //do nothing
                    }
                    else {
                        Toast.makeText(this, "No permission granted!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

            }
        }
    }

}
