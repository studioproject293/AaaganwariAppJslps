package com.jslps.aaganbariapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jslps.aaganbariapp.Constant;
import com.jslps.aaganbariapp.PrefManager;
import com.jslps.aaganbariapp.R;
import com.jslps.aaganbariapp.model.ImageSaveModel;

import java.util.ArrayList;

public class AttachmentImgeAdapter extends RecyclerView.Adapter<AttachmentImgeAdapter.ViewHolder> {
    PrefManager prefManager;
    private static final String TAG = "AttachmentImgeAdapter-->";
    private Context context;
    private ArrayList<ImageSaveModel> imageSaveModels = new ArrayList<ImageSaveModel>();

    public AttachmentImgeAdapter(Context context,ArrayList<ImageSaveModel> arrayList) {
        this.context = context;

        this.imageSaveModels = arrayList;
    }

    @NonNull
    @Override
    public AttachmentImgeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attachment_image, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AttachmentImgeAdapter.ViewHolder holder, final int position) {


        try {
            byte[] byteArray = Base64.decode(imageSaveModels.get(position).getImgebytes(), 0);
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            holder.attachmentImage.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {

            Log.d("Image", TAG + "Gallery On item Click-->" + "IO Exception-->" + e.toString());
        }


        if (Constant.editFlag) {
            if (imageSaveModels.size() == 1)
                holder.removeImage.setVisibility(View.GONE);
            else
                holder.removeImage.setVisibility(View.VISIBLE);
        } else holder.removeImage.setVisibility(View.GONE);
        holder.removeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemRemoved(position);
                imageSaveModels.get(position).delete();
                notifyItemRangeChanged(position, imageSaveModels.size());
            }
        }); }

    @Override
    public int getItemCount() {
        return imageSaveModels.size();
    }
    // Imp should be added
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView attachmentImage;
        public ImageView removeImage;
        public ImageView backimage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            attachmentImage = (ImageView) itemView.findViewById(R.id.attachmentImage);
            removeImage = (ImageView) itemView.findViewById(R.id.removeImage);
        }
    }
}
