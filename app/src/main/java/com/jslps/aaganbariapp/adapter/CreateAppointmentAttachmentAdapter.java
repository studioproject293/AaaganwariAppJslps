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

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.jslps.aaganbariapp.Constant;
import com.jslps.aaganbariapp.PrefManager;
import com.jslps.aaganbariapp.R;
import com.jslps.aaganbariapp.model.BenifisheryDataModelDb;
import com.jslps.aaganbariapp.model.BenifisheryDataModelDbSend;
import com.jslps.aaganbariapp.model.ImageSaveModel;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;


public class CreateAppointmentAttachmentAdapter extends RecyclerView.Adapter<AttachmentViewHolder> {
    private static final String TAG = "AllergyAttachmnertAdapter-->";
    AttachmentViewHolder attachmentViewHolder;
    private Context context;
    private List<String> finalbytes = new ArrayList<String>();
    private List<String> finalnames = new ArrayList<String>();
    private List<Long> finalsizes = new ArrayList<Long>();
    private List<String> finaltypes = new ArrayList<String>();
    private ArrayList<ImageSaveModel> imageSaveModels = new ArrayList<ImageSaveModel>();

    public CreateAppointmentAttachmentAdapter(Context context, List<String> finalbytes, List<String> finalnames, List<Long> finalsizes, List<String> finaltypes) {
        this.context = context;
        this.finalbytes = finalbytes;
        this.finalnames = finalnames;
        this.finalsizes = finalsizes;
        this.finaltypes = finaltypes;
    }

    @Override
    public AttachmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attachment_image, parent, false);
        attachmentViewHolder = new AttachmentViewHolder(view);
        return attachmentViewHolder;
    }

    @Override
    public void onBindViewHolder(final AttachmentViewHolder holder, final int position) {
        ImageSaveModel imageSaveModel = null;
        /*if (imageSaveModels != null && imageSaveModels.size() > 0) {
            imageSaveModel = imageSaveModels.get(position);
        }*/

        try {
            byte[] byteArray = Base64.decode(finalbytes.get(position), 0);
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

    }

    @Override
    public int getItemCount() {
        return finalbytes.size();
    }

    // Imp should be added
    @Override
    public int getItemViewType(int position) {
        return position;
    }
}


class AttachmentViewHolder extends RecyclerView.ViewHolder {

    public ImageView attachmentImage;
    public ImageView removeImage;
    public ImageView backimage;

    public AttachmentViewHolder(View itemView) {
        super(itemView);
        attachmentImage = (ImageView) itemView.findViewById(R.id.attachmentImage);
        removeImage = (ImageView) itemView.findViewById(R.id.removeImage);
    }
}
