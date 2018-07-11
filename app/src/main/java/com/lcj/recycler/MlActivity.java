package com.lcj.recycler;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.label.FirebaseVisionLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionLabelDetector;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;
import com.lcj.recycler.databinding.ActivityMlBinding;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MlActivity extends AppCompatActivity {

    private ActivityMlBinding binding;
    String detectText = "";
    String label = "";
    private Uri mImageCaptureUri;
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ml);
        binding.setActivity(this);
        binding.back.setOnClickListener(view -> {
            finish();
        });
    }

    //글자 인식
    public void recognizeText(View v) {
        BitmapDrawable d = (BitmapDrawable) binding.imageHolder.getDrawable();
        FirebaseVisionImage textImg = FirebaseVisionImage.fromBitmap(d.getBitmap());
        FirebaseVisionTextDetector detector = FirebaseVision.getInstance().getVisionTextDetector();
        detector.detectInImage(textImg).addOnCompleteListener(task -> {
            //감지 완료

            for (FirebaseVisionText.Block text : task.getResult().getBlocks()) {
                detectText += text.getText() + "\n";
            }
            runOnUiThread(() -> {
                new SweetAlertDialog(this)
                        .setTitleText(detectText)
                        .show();
            });

            try {
                detector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    //얼굴 인식
    public void detectFaces(View v) {
        BitmapDrawable d = (BitmapDrawable) binding.imageHolder.getDrawable();
        FirebaseVisionFaceDetector detector = FirebaseVision.getInstance().getVisionFaceDetector();
        detector.detectInImage(FirebaseVisionImage.fromBitmap(d.getBitmap())).addOnCompleteListener(task -> {
            Bitmap markedBitmap = d.getBitmap().copy(Bitmap.Config.ARGB_8888, true);
            Canvas canvas = new Canvas(markedBitmap);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.parseColor("#99003399"));
            for (FirebaseVisionFace face : task.getResult()) {
                canvas.drawRect(face.getBoundingBox(), paint);
            }
            runOnUiThread(() -> {
                binding.imageHolder.setImageBitmap(markedBitmap);
                binding.imageHolder.setScaleType(ImageView.ScaleType.FIT_XY);
            });
        });
    }

    //물건 인식 -> 물건 이름 도출
    public void generateLabels(View v) {
        BitmapDrawable d = (BitmapDrawable) binding.imageHolder.getDrawable();
        FirebaseVisionLabelDetector detector = FirebaseVision.getInstance().getVisionLabelDetector();
        detector.detectInImage(FirebaseVisionImage.fromBitmap(d.getBitmap())).addOnCompleteListener(task -> {

            for (FirebaseVisionLabel lb : task.getResult()) {
                label += lb.getLabel() + "\n";
            }
            runOnUiThread(() -> {
                new SweetAlertDialog(this)
                        .setTitleText(label)
                        .show();
            });
        });
    }

    // 카메라 촬영 후 이미지 가져오기
    public void doTakePhotoAction(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, PICK_FROM_CAMERA);
        }
    }

    //앨범에서 이미지 가져오기
    public void doTakeAlbumAction(View v) {
        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case PICK_FROM_ALBUM: {
                mImageCaptureUri = data.getData();
                Log.d("album", "" + data.getData().toString());
                Log.d("album", "" + mImageCaptureUri.getPath().toString());
                try {
                    Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    binding.imageHolder.setImageBitmap(image_bitmap);
                    binding.imageHolder.setScaleType(ImageView.ScaleType.FIT_XY);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            }
            case PICK_FROM_CAMERA: {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");


                    binding.imageHolder.setImageBitmap(imageBitmap);
                    binding.imageHolder.setScaleType(ImageView.ScaleType.FIT_XY);

                break;
            }

        }
    }
}
