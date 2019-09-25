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
import com.jslps.aaganbariapp.R;
import com.jslps.aaganbariapp.listener.OnFragmentListItemSelectListener;
import com.jslps.aaganbariapp.model.ImageSaveModel;

import java.util.ArrayList;

public class AttachmentImgeAdapter extends RecyclerView.Adapter<AttachmentImgeAdapter.ViewHolder> {

    private static final String TAG = "AttachmentImgeAdapter-->";
    private Context context;
    private ArrayList<ImageSaveModel> imageSaveModels = new ArrayList<ImageSaveModel>();
    OnFragmentListItemSelectListener onFragmentListItemSelectListener;

    public AttachmentImgeAdapter(Context context, ArrayList<ImageSaveModel> arrayList) {
        this.context = context;
        this.imageSaveModels = arrayList;
    }

    @NonNull
    @Override
    public AttachmentImgeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attachment_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AttachmentImgeAdapter.ViewHolder holder, final int position) {
        try {
            Log.d("Image", TAG + "Gallery On item Click-->" + "" + position);

            byte[] byteArray = Base64.decode(imageSaveModels.get(position).getImagebyte(), 0);
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

                imageSaveModels.remove(position);
                notifyItemRangeChanged(position, imageSaveModels.size());
                //onFragmentListItemSelectListener.onListItemSelected(position, imageSaveModels.get(position));
                if (Constant.editFlag) {
                    if (imageSaveModels.size() == 1)
                        holder.removeImage.setVisibility(View.GONE);
                    else
                        holder.removeImage.setVisibility(View.VISIBLE);
                } else holder.removeImage.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageSaveModels.size();
    }

    // Imp should be added
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setListner(OnFragmentListItemSelectListener entryFormFragmentEdit) {
        onFragmentListItemSelectListener = entryFormFragmentEdit;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView attachmentImage;
        ImageView removeImage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            attachmentImage = (ImageView) itemView.findViewById(R.id.attachmentImage);
            removeImage = (ImageView) itemView.findViewById(R.id.removeImage);
        }
    }
}
