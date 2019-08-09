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
import java.util.List;

public class AttachmentImgeAdapter extends RecyclerView.Adapter<AttachmentImgeAdapter.ViewHolder> {
    PrefManager prefManager;
    private static final String TAG = "AttachmentImgeAdapter-->";
    private Context context;
    private List<String> finalbytes = new ArrayList<String>(2);
    private List<String> finalnames = new ArrayList<String>(2);
    private List<Long> finalsizes = new ArrayList<Long>(2);
    private List<String> finaltypes = new ArrayList<String>(2);
    private ArrayList<ImageSaveModel> imageSaveModels = new ArrayList<ImageSaveModel>();

    public AttachmentImgeAdapter(Context context, List<String> finalbytes, List<String> finalnames, List<Long> finalsizes, List<String> finaltypes,ArrayList<ImageSaveModel>arrayList) {
        this.context = context;
        this.finalbytes = finalbytes;
        this.finalnames = finalnames;
        this.finalsizes = finalsizes;
        this.finaltypes = finaltypes;
        this.imageSaveModels=arrayList;
    }

    @NonNull
    @Override
    public AttachmentImgeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attachment_image, parent, false);
        ViewHolder  viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AttachmentImgeAdapter.ViewHolder holder, final int position) {
        ImageSaveModel imageSaveModel = null;
        /*if (imageSaveModels != null && imageSaveModels.size() > 0) {
            imageSaveModel = imageSaveModels.get(position);
        }*/
         finalbytes.add(imageSaveModels.get(position).getImgebytes());
         finalsizes.add(imageSaveModels.get(position).getFinalsizes());
         finalnames.add(imageSaveModels.get(position).getFinalnames());
         finaltypes.add(imageSaveModels.get(position).getFinaltypes());
        try {
            byte[] byteArray = Base64.decode(imageSaveModels.get(position).getImgebytes(), 0);
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            holder.attachmentImage.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {

            Log.d("Image", TAG + "Gallery On item Click-->" + "IO Exception-->" + e.toString());
        }

        holder.attachmentImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.removeImage.setVisibility(View.VISIBLE);
                holder.backimage.setVisibility(View.VISIBLE);
                return true;
            }
        });
        if (Constant.editFlag){
            holder.removeImage.setVisibility(View.VISIBLE);
        }else  holder.removeImage.setVisibility(View.GONE);
        holder.removeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*imageSaveModels.remove(finalImageSaveModel);
                finalImageSaveModel.setImgebytes(null);
                finalImageSaveModel.setFinalnames(null);
                finalImageSaveModel.setPanchyatcode(null);
                finalImageSaveModel.setVocode(null);
                finalImageSaveModel.setAaganwaricode(null);
                finalImageSaveModel.setFinaltypes(null);
                finalImageSaveModel.setFinalsizes(null);
                finalImageSaveModel.save();*/
                imageSaveModels.get(position).delete();
                finalbytes.remove(position);
                finalnames.remove(position);
                finalsizes.remove(position);
                finaltypes.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, finalbytes.size());
                notifyItemRangeChanged(position, finalnames.size());
                notifyItemRangeChanged(position, finalsizes.size());
                notifyItemRangeChanged(position, finaltypes.size());


            }
        });
       /* holder.backimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.removeImage.setVisibility(View.GONE);
                holder.backimage.setVisibility(View.GONE);
            }
        });

        holder.removeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalbytes.remove(position);
                finalnames.remove(position);
                finalsizes.remove(position);
                finaltypes.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, finalbytes.size());
                notifyItemRangeChanged(position, finalnames.size());
                notifyItemRangeChanged(position, finalsizes.size());
                notifyItemRangeChanged(position, finaltypes.size());
            }
        });*/
        /*if(bitmap!=null)
        {
            bitmap.recycle();
            bitmap=null;
        }*/
    }

    @Override
    public int getItemCount() {
       /* if (finalbytes.size()>1){
            return 2;
        }else {
            return finalbytes.size();

        }*/
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
