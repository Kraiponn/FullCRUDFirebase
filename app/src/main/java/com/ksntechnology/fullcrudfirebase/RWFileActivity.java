package com.ksntechnology.fullcrudfirebase;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RWFileActivity extends AppCompatActivity {

    private FileReader reader;
    private ImageView imgProfile;
    private EditText edtData;
    private Button btnCapOnly, btnCapToSaveInternal;
    private Button btnCapToSaveExt;
    private final int CAPTURE_REQUEST_ONLY = 123;
    private final int CAPTURE_SAVE_INTERNAL = 456;
    private final int CAPTURE_SAVE_EXTERNAL = 789;
    private Uri mImgFileUri;
    private String mFileName;
    private String mLastPicturePath;
    private static final int REQUEST_PICTURE_FROM_EXTERNAL = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rwfile);

        initInstance();
        //initView();
    }

    private void initInstance() {
        imgProfile = findViewById(R.id.imageProfile);
        edtData = findViewById(R.id.editData);
        btnCapOnly = findViewById(R.id.buttonCapOnly);
        btnCapToSaveInternal = findViewById(R.id.buttonCapAndSave);
        btnCapToSaveExt = findViewById(R.id.buttonCapToExt);

        requestPermissionForRuntime();
        btnCapOnly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DebugV9", "Button only Click");
                capImageOnly();
            }
        });
        btnCapToSaveInternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capImageAndSaveToInternal();
                Log.d("DebugV9", "Button only Click");
            }
        });

        btnCapToSaveExt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureToExternal();
                //selectPictureFromGallary();
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImageFromExternal();
            }
        });
    }

    private void chooseImageFromExternal() {
        /*Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(
                Intent.createChooser(intent, "Please choose image "),
                REQUEST_PICTURE_FROM_EXTERNAL
        );*/

        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        );
        //intent.setType("image/*");
        startActivityForResult(
                intent,
                REQUEST_PICTURE_FROM_EXTERNAL
        );
    }

    private void requestPermissionForRuntime() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        //
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.cancelPermissionRequest();
                    }
                }).check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == CAPTURE_SAVE_INTERNAL || requestCode == CAPTURE_REQUEST_ONLY) {
            Bundle bundle = data.getExtras();
            Bitmap bitmap = bundle.getParcelable("data");
            if (requestCode == CAPTURE_REQUEST_ONLY) {
                imgProfile.setImageBitmap(bitmap);
            } else if (requestCode == CAPTURE_SAVE_INTERNAL) {
                saveImageToInternal(bitmap);
            }
        }else if (requestCode == CAPTURE_SAVE_EXTERNAL) {
            //String path = mImgFileUri.getPath();

            String path = mLastPicturePath;
            Log.d("MYDEBUG", ">>>>> TO ImageView " + path);

            Bitmap bitmapImg = BitmapFactory.decodeFile(path);
            imgProfile.setImageBitmap(bitmapImg);
        } else if (requestCode == 199) {
            Uri imgUri = data.getData();
            Log.d("DeveloperDebug", imgUri.toString());

            String[] columns = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(
                    imgUri,
                    columns,
                    null,
                    null,
                    null
            );

            String imgPath;
            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                //int columnIndex = cursor.getColumnIndex(columns[0]);
                imgPath = cursor.getString(columnIndex);
            } else {
                imgPath = imgUri.getPath();
            }

            Toast.makeText(this,
                    "Image path " + imgPath,
                    Toast.LENGTH_SHORT).show();
            //Log.d("DeveloperDebug", imgPath);
            Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
            imgProfile.setImageBitmap(bitmap);
            cursor.close();
        } else if (requestCode == REQUEST_PICTURE_FROM_EXTERNAL) {
            Uri imgUri = data.getData();
            String[] columns = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(
                    imgUri,
                    columns,
                    null, null, null
            );

            if (cursor != null) {
                cursor.moveToFirst();
                int colIndex =
                        cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                String imgPath = cursor.getString(colIndex);
                Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
                imgProfile.setImageBitmap(bitmap);
            }
        }
    }

    private void selectPictureFromGallary() {
        requestPermissionForRuntime();
        /*Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        );
        startActivityForResult(intent, 199);*/

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(
                Intent.createChooser(intent, "Choose image"),
                199
        );
    }

    private void captureToExternal() {
        File extDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES
        );
        Date date = new Date();
        SimpleDateFormat dateFormat =
                new SimpleDateFormat("yyyyMMdd_HHmmss");
        mFileName = dateFormat.format(date) + ".jpg";

        File myPicDir = new File(extDir, "XXPicture");
        if (!myPicDir.exists()) {
            myPicDir.mkdirs();
        }

        File imgFile = new File(myPicDir, mFileName);
        mImgFileUri = FileProvider.getUriForFile(
                RWFileActivity.this,
                getApplicationContext().getPackageName() + ".provider",
                imgFile
        );

        mLastPicturePath = imgFile.getAbsolutePath();
        Log.d("DEBUG", ">>>>>From Capture " + myPicDir.getAbsolutePath() + "/" + mFileName);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImgFileUri);
        startActivityForResult(intent, CAPTURE_SAVE_EXTERNAL);

    }

    private void capImageAndSaveToInternal() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURE_SAVE_INTERNAL);
    }

    private void capImageOnly() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURE_REQUEST_ONLY);
    }

    private void saveImageToInternal(Bitmap bitmap) {
        Date date = new Date();
        SimpleDateFormat dateFormat =
                new SimpleDateFormat("yyyyMMdd-HHmmss");
        String imgName = dateFormat.format(date) + ".jpg";

        try {
            FileOutputStream fileOutputStream = openFileOutput(
                    imgName, MODE_PRIVATE
            );
            Bitmap bitmap1 = bitmap;
            bitmap1.compress(
                    Bitmap.CompressFormat.JPEG,
                    100, fileOutputStream);

            fileOutputStream.flush();
            fileOutputStream.close();

            File imgIntDir = getFilesDir();
            String path = imgIntDir.getAbsolutePath() + "/" + imgName;
            bitmap1 = BitmapFactory.decodeFile(path);
            imgProfile.setImageBitmap(bitmap1);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
