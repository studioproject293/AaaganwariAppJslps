package com.jslps.aaganbariapp.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.jslps.aaganbariapp.R;
import com.jslps.aaganbariapp.listener.OnFragmentListItemSelectListener;
import com.jslps.aaganbariapp.model.BenifisheryDataModelDbSendNew;
import com.jslps.aaganbariapp.model.ReportFilterModel;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;

public class ReportsVoListAdapter extends RecyclerView.Adapter<ReportsVoListAdapter.ViewHolder> {
    private Activity context;
    OnFragmentListItemSelectListener onFragmentListItemSelectListener;
    ArrayList<ReportFilterModel> reportFilterModelArrayList;
    ArrayList<String>stringArrayList=new ArrayList<>();

    public ReportsVoListAdapter(Activity activity, ArrayList<ReportFilterModel> reportFilterModelArrayList, ArrayList<String> aaganwariName) {
        this.context = activity;
        this.reportFilterModelArrayList = reportFilterModelArrayList;
        this.stringArrayList=aaganwariName;
    }

    @NonNull
    @Override
    public ReportsVoListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.reports_row_vo, parent, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportsVoListAdapter.ViewHolder holder, final int position) {
        holder.title.setText(reportFilterModelArrayList.get(position).getVoname());
        holder.noofTotal.setText(reportFilterModelArrayList.get(position).getAaganwaricount());
        if (TextUtils.isEmpty(reportFilterModelArrayList.get(position).getAaganwaricountApplied()))
            holder.noofsupply.setText("0");
        else {
            holder.noofsupply.setText(reportFilterModelArrayList.get(position).getAaganwaricountApplied());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(context, android.R.style.Theme_DeviceDefault_Dialog_Alert);
                    // Include dialog.xml file
                    dialog.setContentView(R.layout.layout_dialog_report);
                    ImageView closeButton = dialog.findViewById(R.id.closeButton);
                    RecyclerView recyclerView = dialog.findViewById(R.id.recyerviewAaganwariname);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                    recyclerView.setLayoutManager(mLayoutManager);
                    closeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });
                    ArrayList<BenifisheryDataModelDbSendNew>benifisheryDataModelDbSendNews= (ArrayList<BenifisheryDataModelDbSendNew>) Select.from(BenifisheryDataModelDbSendNew.class)
                            .where(Condition.prop("vocode").eq(reportFilterModelArrayList.get(position).getVocode()),
                            Condition.prop("month").eq(reportFilterModelArrayList.get(position).getMonth()),
                            Condition.prop("year").eq(reportFilterModelArrayList.get(position).getYear())).list();
                    System.out.println("dfjkjsd"+new Gson().toJson(benifisheryDataModelDbSendNews));
                    ArrayList<String>arrayList=new ArrayList<>();
                    for (int i=0;i<benifisheryDataModelDbSendNews.size();i++){
                        if (!arrayList.contains(benifisheryDataModelDbSendNews.get(i).getAaganwariname()))
                            arrayList.add(benifisheryDataModelDbSendNews.get(i).getAaganwariname());
                    }

                    ReportsAaganwariRecyclerviewAdapter reportsAaganwariRecyclerviewAdapter = new ReportsAaganwariRecyclerviewAdapter(context, arrayList);
                    recyclerView.setAdapter(reportsAaganwariRecyclerviewAdapter);
                    dialog.setCancelable(false);
                    dialog.show();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return reportFilterModelArrayList.size();
    }

    public void setListner(OnFragmentListItemSelectListener onFragmentListItemSelectListener) {
        this.onFragmentListItemSelectListener = onFragmentListItemSelectListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, noofTotal, noofsupply;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.month);
            noofTotal = itemView.findViewById(R.id.noofTotal);
            noofsupply = itemView.findViewById(R.id.noofsupply);
        }
    }
}
