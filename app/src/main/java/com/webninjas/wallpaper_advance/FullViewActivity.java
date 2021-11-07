package com.webninjas.wallpaper_advance;


import static com.webninjas.wallpaper_advance.adapters.ShowImagesAdapter.shareImage;
import static com.webninjas.wallpaper_advance.adapters.ShowImagesAdapter.shareVideo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.webninjas.wallpaper_advance.adapters.ShowImagesAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;


public class FullViewActivity extends AppCompatActivity {

    ShowImagesAdapter showImagesAdapter;
    ViewPager vpView;
    FloatingActionButton imDelete, imWhatsappShare, imShare;
    ImageView im_close;
    private FullViewActivity activity;
    private ArrayList<File> fileArrayList;
    private int Position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_view);

        imDelete = findViewById(R.id.imDelete);
        imWhatsappShare = findViewById(R.id.imWhatsappShare);
        imShare = findViewById(R.id.imShare);
        im_close = findViewById(R.id.im_close);
        vpView = findViewById(R.id.vp_view);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            fileArrayList = (ArrayList<File>) getIntent().getSerializableExtra("ImageDataFile");
            Position = getIntent().getIntExtra("Position", 0);
        }
        initViews();

    }

    public void initViews() {
        showImagesAdapter = new ShowImagesAdapter(this, fileArrayList, FullViewActivity.this);
        vpView.setAdapter(showImagesAdapter);
        vpView.setCurrentItem(Position);

        vpView.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                Position = arg0;
                System.out.println("Current position==" + Position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int num) {
            }
        });

        imDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder ab = new AlertDialog.Builder(activity);
                ab.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        boolean b = fileArrayList.get(Position).delete();
                        if (b) {
                            deleteFileAA(Position);
                        }
                    }
                });
                ab.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = ab.create();
                alert.setTitle("Do you want to delete");
                alert.show();
            }
        });

        imShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fileArrayList.get(Position).getName().contains(".mp4")) {
                    Log.d("SSSSS", "onClick: " + fileArrayList.get(Position) + "");
                    shareVideo(activity, fileArrayList.get(Position).getPath());
                } else {
                    shareImage(activity, fileArrayList.get(Position).getPath());
                }
            }
        });

        imWhatsappShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fileArrayList.get(Position).getName().contains(".mp4")) {
                    shareImageVideoOnWhatsapp(activity, fileArrayList.get(Position).getPath(), true);
                } else {
                    shareImageVideoOnWhatsapp(activity, fileArrayList.get(Position).getPath(), false);
                }
            }
        });

        im_close.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        activity = this;
    }

    public void deleteFileAA(int position) {
        fileArrayList.remove(position);
        showImagesAdapter.notifyDataSetChanged();
        Toast.makeText(this, "file deleted", Toast.LENGTH_SHORT).show();
//        MainFrag.common.INSTANCE.setToast(activity, "file_deleted");
        if (fileArrayList.size() == 0) {
            onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void shareImageVideoOnWhatsapp(Context context, String filePath, boolean isVideo) {
        Uri imageUri = Uri.parse(filePath);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setPackage("com.whatsapp");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "");
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        if (isVideo) {
            shareIntent.setType("video/*");
        } else {
            shareIntent.setType("image/*");
        }
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            context.startActivity(shareIntent);
        } catch (Exception e) {
            Toast.makeText(this, "whatsapp_not_installed", Toast.LENGTH_SHORT).show();
        }
    }


    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

}
