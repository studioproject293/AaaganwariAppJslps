package com.jslps.aaganbariapp.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.jslps.aaganbariapp.R;
import com.jslps.aaganbariapp.listener.OnFragmentListItemSelectListener;
import com.jslps.aaganbariapp.model.AanganWariModelDb;
import com.jslps.aaganbariapp.model.ReportFilterModel;
import com.jslps.aaganbariapp.model.VOListDataModelDb;

import java.util.ArrayList;

public class ReportsVoListAdapter extends RecyclerView.Adapter<ReportsVoListAdapter.ViewHolder> {
    private Activity context;
    OnFragmentListItemSelectListener onFragmentListItemSelectListener;
    ArrayList<ReportFilterModel> reportFilterModelArrayList;


    public ReportsVoListAdapter(Activity activity, ArrayList<ReportFilterModel> reportFilterModelArrayList) {
        this.context = activity;
        this.reportFilterModelArrayList = reportFilterModelArrayList;
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
        else
            holder.noofsupply.setText(reportFilterModelArrayList.get(position).getAaganwaricountApplied());
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
