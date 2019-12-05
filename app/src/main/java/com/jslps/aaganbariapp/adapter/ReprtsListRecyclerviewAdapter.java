package com.jslps.aaganbariapp.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chootdev.csnackbar.Align;
import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.jeevandeshmukh.fancybottomsheetdialoglib.FancyBottomSheetDialog;
import com.jslps.aaganbariapp.PrefManager;
import com.jslps.aaganbariapp.R;
import com.jslps.aaganbariapp.listener.OnFragmentListItemSelectListener;
import com.jslps.aaganbariapp.model.AanganWariModelDb;
import com.jslps.aaganbariapp.model.BenifisheryDataModelDbSend;
import com.jslps.aaganbariapp.model.BenifisheryDataModelDbSendNew;
import com.jslps.aaganbariapp.model.ImageSaveModel;
import com.jslps.aaganbariapp.model.ReportDisplayFormModel;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;

public class ReprtsListRecyclerviewAdapter extends RecyclerView.Adapter<ReprtsListRecyclerviewAdapter.ViewHolder> {
    private Activity context;
    OnFragmentListItemSelectListener onFragmentListItemSelectListener;
    ArrayList<ReportDisplayFormModel> benifisheryDataModelDbSends;
    private PrefManager prefManager;

    public ReprtsListRecyclerviewAdapter(Activity activity, ArrayList<ReportDisplayFormModel> benifisheryDataModelDbSends) {
        this.context = activity;
        this.benifisheryDataModelDbSends = benifisheryDataModelDbSends;

    }

    @NonNull
    @Override
    public ReprtsListRecyclerviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.reports_list_row, parent, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull ReprtsListRecyclerviewAdapter.ViewHolder holder, final int position) {
        prefManager = PrefManager.getInstance();
        final ReportDisplayFormModel benifisheryDataModelDbSend = benifisheryDataModelDbSends.get(position);
        holder.aaganwaricode.setText(benifisheryDataModelDbSend.getAaganwariname());
        holder.vocode.setText(benifisheryDataModelDbSend.getVoname());
        holder.panchyatcode.setText(benifisheryDataModelDbSend.getPanchayatname());
        holder.imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFragmentListItemSelectListener.onListItemSelected(R.id.edit, benifisheryDataModelDbSend);
            }
        });
        holder.imageViewView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFragmentListItemSelectListener.onListItemSelected(R.id.show, benifisheryDataModelDbSend);
            }
        });
        holder.imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFragmentListItemSelectListener.onListItemSelected(R.id.edit, benifisheryDataModelDbSend);
            }
        });
        holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FancyBottomSheetDialog.Builder(context)
                        .setTitle("Delete Data")
                        .setMessage("Are you sure you want to delete ?")
                        .setBackgroundColor(Color.parseColor("#F00000")) //don't use R.color.somecolor
                        .setIcon(R.drawable.ic_info_outline_black_24dp,true)
                        .isCancellable(false)
                        .OnNegativeClicked(new FancyBottomSheetDialog.FancyBottomSheetDialogListener() {
                            @Override
                            public void OnClick() {

                            }
                        })
                        .OnPositiveClicked(new FancyBottomSheetDialog.FancyBottomSheetDialogListener() {
                            @Override
                            public void OnClick() {
                                benifisheryDataModelDbSends.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, benifisheryDataModelDbSends.size());
                                onFragmentListItemSelectListener.onListItemSelected(R.id.delete, benifisheryDataModelDbSend);
                            }
                        })
                        .setNegativeBtnText("Cancel")
                        .setPositiveBtnText("Delete")
                        .setPositiveBtnBackground(Color.parseColor("#F00000"))//don't use R.color.somecolor
                        .setNegativeBtnBackground(Color.WHITE)//don't use R.color.somecolor
                        .build();

            }
        });
       if (benifisheryDataModelDbSend.getIsuploadToServer().equalsIgnoreCase("true")){
           holder.imageViewEdit.setVisibility(View.GONE);

       }else {
           holder.imageViewEdit.setVisibility(View.VISIBLE);

       }

      holder.monthYear.setText(getProperFormat(Integer.parseInt(benifisheryDataModelDbSend.getMonth()))+" - "+benifisheryDataModelDbSend.getYear());
    }

    @Override
    public int getItemCount() {
        return benifisheryDataModelDbSends.size();
    }

    public void setListner(OnFragmentListItemSelectListener onFragmentListItemSelectListener) {
        this.onFragmentListItemSelectListener = onFragmentListItemSelectListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView aaganwaricode, vocode, panchyatcode,monthYear;
        ImageView imageViewEdit, imageViewDelete, imageViewView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            aaganwaricode = itemView.findViewById(R.id.aaganwaricode);
            vocode = itemView.findViewById(R.id.vocode);
            panchyatcode = itemView.findViewById(R.id.panchyatcode);
            imageViewEdit = itemView.findViewById(R.id.edit);
            imageViewView = itemView.findViewById(R.id.show);
            imageViewDelete = itemView.findViewById(R.id.delete);
            monthYear = itemView.findViewById(R.id.monthYear);

        }
    }
    private String getProperFormat(int hhORmm) {
        String temp = hhORmm + "";
        if (temp.length() == 1) {
            temp = "0" + temp;
        } else {

        }
        return temp;
    }
}

