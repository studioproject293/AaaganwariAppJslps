package com.jslps.aaganbariapp.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.jslps.aaganbariapp.Constant;
import com.jslps.aaganbariapp.PrefManager;
import com.jslps.aaganbariapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GalleryActivity extends AppCompatActivity {
    PrefManager prefManager;
    private static final String TAG = "GalleryActivity-->";
    private ArrayList<String> absolutesPath;
    List<String> finalbytes = new ArrayList<String>();
    List<String> finalnames = new ArrayList<String>();
    List<Long> finalsizes = new ArrayList<Long>();
    List<String> finaltypes = new ArrayList<String>();
    List<Integer> positions = new ArrayList<Integer>();
    int intialSize = 0;
    int alertIndentifierCounter = 0;
    Uri uri;
    Cursor cursor;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);
        prefManager = PrefManager.getInstance();
        GridView gallery = (GridView) findViewById(R.id.galleryGridView);
        LinearLayout cancel = (LinearLayout) findViewById(R.id.cancel);
        LinearLayout done = (LinearLayout) findViewById(R.id.done);

        List<String> tempfinalbytes1 = new ArrayList<String>(Constant.finalbytes);
        List<String> tempfinalnames1 = new ArrayList<String>(Constant.finalnames);
        List<Long> tempfinalsizes1 = new ArrayList<Long>(Constant.finalsizes);
        List<String> tempfinaltypes1 = new ArrayList<String>(Constant.finaltypes);

        intialSize = tempfinalbytes1.size();
        finalbytes = tempfinalbytes1;
        finalnames = tempfinalnames1;
        finalsizes = tempfinalsizes1;
        finaltypes = tempfinaltypes1;
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (alertIndentifierCounter != 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(GalleryActivity.this);
                    builder.setTitle("Please Confirm?");
                    builder.setCancelable(false);

                    builder.setMessage("You will lose data. \nAre you sure you want to continue?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            GalleryActivity.super.onBackPressed();
                            intialSize = 0;
                            absolutesPath.clear();
                            //if user pressed "yes", then he is allowed to exit from application
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //if user select "No", just cancel this dialog and continue with app
                            dialog.cancel();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    GalleryActivity.super.onBackPressed();
                }
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intialSize = 0;
                List<String> tempfinalbytes = new ArrayList<String>(finalbytes);
                List<String> tempfinalnames = new ArrayList<String>(finalnames);
                List<Long> tempfinalsizes = new ArrayList<Long>(finalsizes);
                List<String> tempfinaltypes = new ArrayList<String>(finaltypes);

                /*Log.d(Constant.TAG,TAG+"Before Assigining simple-------->");
                Log.d(Constant.TAG,TAG+"bytes"+finalbytes.size());
                Log.d(Constant.TAG,TAG+"names"+finalnames.size());
                Log.d(Constant.TAG,TAG+"sizes"+finalsizes.size());
                Log.d(Constant.TAG,TAG+"types"+finaltypes.size());*/

                Constant.finalbytes = tempfinalbytes;
                Constant.finalnames = tempfinalnames;
                Constant.finalsizes = tempfinalsizes;
                Constant.finaltypes = tempfinaltypes;

                /*Log.d(Constant.TAG,TAG+"after Assigining Common-------->");
                Log.d(Constant.TAG,TAG+"bytes"+Constant.finalbytes.size());
                Log.d(Constant.TAG,TAG+"names"+Constant.finalnames.size());
                Log.d(Constant.TAG,TAG+"sizes"+Constant.finalsizes.size());
                Log.d(Constant.TAG,TAG+"types"+Constant.finaltypes.size());*/

                finalbytes.clear();
                finalnames.clear();
                finalsizes.clear();
                finaltypes.clear();

                /*Log.d(Constant.TAG,TAG+"after clearing Common-------->");
                Log.d(Constant.TAG,TAG+"bytes"+Constant.finalbytes.size());
                Log.d(Constant.TAG,TAG+"names"+Constant.finalnames.size());
                Log.d(Constant.TAG,TAG+"sizes"+Constant.finalsizes.size());
                Log.d(Constant.TAG,TAG+"types"+Constant.finaltypes.size());*/

                GalleryActivity.super.onBackPressed();
                absolutesPath.clear();
            }
        });

        gallery.setAdapter(new ImageAdapter(this));

        //Setting Click
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                if (null != absolutesPath && !absolutesPath.isEmpty()) {
                    String path = absolutesPath.get(position);
                    File imgFile = new File(path);
                    String encodedBase64 = "";
                    long fileSizeInBytes = 0;
                    Log.d("", TAG + "PAthhhhhh" + path);
                    String fileExtension = path.substring(path.lastIndexOf("/") + 1).substring(path.substring(path.lastIndexOf("/") + 1).lastIndexOf(".") + 1);
                    String fileNameWithExtention = path.substring(path.lastIndexOf("/") + 1);
                    String fileName = fileNameWithExtention.substring(0, fileNameWithExtention.lastIndexOf("."));
                    //Log.d(Constant.TAG,TAG+"background"+arg1.getBackground());
                    if (imgFile.exists()) {
                        if (arg1.getBackground() == (null)) {
                            FileInputStream fileInputStreamReader = null;
                            try {
                                fileInputStreamReader = new FileInputStream(imgFile);
                                fileSizeInBytes = fileInputStreamReader.available();
                                byte[] bytes = new byte[(int) imgFile.length()];
                                fileInputStreamReader.read(bytes);
                                encodedBase64 = Base64.encodeToString(bytes, Base64.DEFAULT);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                                //Log.d(Constant.TAG, TAG + "Gallery On item Click-->" + "File Not Found Exception-->" + e.toString());
                            } catch (IOException e) {
                                e.printStackTrace();
                                //Log.d(Constant.TAG, TAG + "Gallery On item Click-->" + "IO Exception-->" + e.toString());
                            } catch (Error e) {
                                Toast.makeText(GalleryActivity.this, "RAM is running out of memory, Please clear your RAM first.", Toast.LENGTH_SHORT).show();
                                //Log.d(Constant.TAG, TAG + "Gallery On item Click-->" + "IO Exception-->" + e.toString());
                            }

                            //setting Image size boundary Image should be less than 5MB
                            if (fileSizeInBytes < 5000000) {
                                if (finalbytes.size() < Constant.maxAttachment) {
                                    finalbytes.add(encodedBase64);
                                    finalnames.add(fileName+ ".jpg");
                                    finalsizes.add(fileSizeInBytes);
                                    finaltypes.add(fileExtension);
                                    positions.add(position);
                                    alertIndentifierCounter++;

                                    arg1.setBackgroundResource(R.color.colorPrimaryDark);
                                } else {
                                    Toast.makeText(GalleryActivity.this, "Can not upload more than " + Constant.maxAttachment + " images.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(GalleryActivity.this, "Image size should be less than 5MB", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            int removedItemPosition = 0;
                            try {
                                alertIndentifierCounter--;
                                arg1.setBackground(null);
                                removedItemPosition = positions.indexOf(position);
                                finalbytes.remove(removedItemPosition + intialSize);
                                finalnames.remove(removedItemPosition + intialSize);
                                finalsizes.remove(removedItemPosition + intialSize);
                                finaltypes.remove(removedItemPosition + intialSize);
                                positions.remove(removedItemPosition);
                            } catch (Exception e) {
                                e.printStackTrace();
                                //Log.d(Constant.TAG,TAG+"Exception While removing");
                            }

                            }
                    } else {
                        Toast.makeText(GalleryActivity.this, "Image not exist", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }


    /**
     * The Class ImageAdapter.
     */
    private class ImageAdapter extends BaseAdapter {

        /**
         * The context.
         */
        private Activity context;


        /**
         * Instantiates a new image adapter.
         *
         * @param localContext the local context
         */
        public ImageAdapter(Activity localContext) {
            context = localContext;
            absolutesPath = getAllShownImagesPath(context);
            System.out.println("dfhiewgsdhwhds" + absolutesPath);
        }

        public int getCount() {
            return absolutesPath.size();
        }

        public Object getItem(int position) {
            return null;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }


        @Override
        public int getViewTypeCount() {
            return getCount();
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            final ImageView picturesView;
            if (convertView == null) {
                picturesView = new ImageView(context);
                picturesView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                picturesView.setLayoutParams(new GridView.LayoutParams(270, 270));
                picturesView.setPadding(10, 10, 10, 10);
            } else {
                picturesView = (ImageView) convertView;
            }

            Glide.with(context).load(absolutesPath.get(position))
                    .placeholder(R.mipmap.ic_launcher_round).centerCrop()
                    .into(picturesView);


            return picturesView;
        }

        /**
         * Getting All Images Path.
         *
         * @param activity the activity
         * @return ArrayList with images Path
         */
        /*private ArrayList<String> getAllShownImagesPath(Activity activity) {
            ArrayList<String> listOfAllImages = new ArrayList<String>();
            String absolutePathOfImage = null;
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            String[] projection = {MediaStore.MediaColumns.DATA,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
            cursor = activity.getContentResolver().query(uri, projection, null,
                    null, null);
            column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            column_index_folder_name = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            while (cursor.moveToNext()) {
                absolutePathOfImage = cursor.getString(column_index_data);
                listOfAllImages.add(absolutePathOfImage);
            }
            //Log.d(Constant.TAG,TAG+"listOfAllImages.size()"+listOfAllImages.size());
            return listOfAllImages;
        }*/
        private ArrayList<String> getAllShownImagesPath(Activity activity) {
            Uri uri;
            Cursor cursor;
            int column_index_data, column_index_folder_name;
            ArrayList<String> listOfAllImages = new ArrayList<String>();
            String absolutePathOfImage = null;
            uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

            String[] projection = {MediaStore.MediaColumns.DATA,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

            cursor = activity.getContentResolver().query(uri, projection, null,
                    null, null);

            column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            column_index_folder_name = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            while (cursor.moveToNext()) {
                absolutePathOfImage = cursor.getString(column_index_data);

                listOfAllImages.add(absolutePathOfImage);
            }
            return listOfAllImages;
        }


    }

    //Handle Back press
    @Override
    public void onBackPressed() {

        if (alertIndentifierCounter != 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(GalleryActivity.this);
            builder.setTitle("Please Confirm?");
            builder.setCancelable(false);

            builder.setMessage("You will lose data. \nAre you sure you want to continue?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    GalleryActivity.super.onBackPressed();
                    absolutesPath.clear();
                    //if user pressed "yes", then he is allowed to exit from application
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user select "No", just cancel this dialog and continue with app
                    dialog.cancel();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        } else {
            super.onBackPressed();
        }

    }

}