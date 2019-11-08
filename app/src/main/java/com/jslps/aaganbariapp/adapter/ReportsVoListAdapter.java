package com.jslps.aaganbariapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jslps.aaganbariapp.R;
import com.jslps.aaganbariapp.listener.OnFragmentListItemSelectListener;
import com.jslps.aaganbariapp.model.AanganWariModelDb;
import com.jslps.aaganbariapp.model.VOListDataModelDb;

import java.util.ArrayList;

public class ReportsVoListAdapter extends RecyclerView.Adapter<ReportsVoListAdapter.ViewHolder> {
    private Activity context;
    OnFragmentListItemSelectListener onFragmentListItemSelectListener;
    ArrayList<VOListDataModelDb> arrayListString;
    ArrayList<AanganWariModelDb>aanganWariModelDbArrayList;
    ArrayList<String>stringArrayList;
    ArrayList<String>arrayList;
    public ReportsVoListAdapter(Activity activity, ArrayList<VOListDataModelDb> arrayListString, ArrayList<String> stringArrayList,ArrayList<String> arrayList) {
        this.context = activity;
        this.arrayListString = arrayListString;
        this.stringArrayList=stringArrayList;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public ReportsVoListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.reports_row_vo, parent, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportsVoListAdapter.ViewHolder holder, final int position) {
        holder.title.setText(arrayListString.get(position).getVoshg());
        holder.noofTotal.setText(stringArrayList.get(position));
        holder.noofTotal.setText(stringArrayList.get(position));
        holder.noofsupply.setText(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayListString.size();
    }

    public void setListner(OnFragmentListItemSelectListener onFragmentListItemSelectListener) {
        this.onFragmentListItemSelectListener = onFragmentListItemSelectListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, noofTotal,noofsupply;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.month);
            noofTotal = itemView.findViewById(R.id.noofTotal);
            noofsupply=itemView.findViewById(R.id.noofsupply);
        }
    }
}
