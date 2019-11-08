package com.jslps.aaganbariapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jslps.aaganbariapp.PrefManager;
import com.jslps.aaganbariapp.R;
import com.jslps.aaganbariapp.listener.OnFragmentListItemSelectListener;
import com.jslps.aaganbariapp.model.AanganWariModelDb;
import com.jslps.aaganbariapp.model.VOListDataModelDb;

import java.util.ArrayList;

public class ReportsMonthRecyclerviewAdapter extends RecyclerView.Adapter<ReportsMonthRecyclerviewAdapter.ViewHolder> {
    private Activity context;
    OnFragmentListItemSelectListener onFragmentListItemSelectListener;
    ArrayList<String> arrayListString;
    ArrayList<String> benifisheryDataModelDbSendNews;
    ArrayList<AanganWariModelDb> aanganWariModelDbArrayList;
    VOListDataModelDb voListDataModelDb;
    private PrefManager prefManager;

    public ReportsMonthRecyclerviewAdapter(Activity activity, ArrayList<String> arrayListString, ArrayList<AanganWariModelDb> aanganWariModelDbs, ArrayList<String> s) {
        this.context = activity;
        this.arrayListString = arrayListString;
        this.aanganWariModelDbArrayList=aanganWariModelDbs;
        this.benifisheryDataModelDbSendNews=s;
    }

    @NonNull
    @Override
    public ReportsMonthRecyclerviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.reports_row, parent, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportsMonthRecyclerviewAdapter.ViewHolder holder, final int position) {
        holder.title.setText(arrayListString.get(position));
        holder.noofTotal.setText(aanganWariModelDbArrayList.size()+"");
        holder.noofSupply.setText(benifisheryDataModelDbSendNews.get(position));

    }

    @Override
    public int getItemCount() {
        return arrayListString.size();
    }

    public void setListner(OnFragmentListItemSelectListener onFragmentListItemSelectListener) {
        this.onFragmentListItemSelectListener = onFragmentListItemSelectListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, noofTotal,noofSupply;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.month);
            noofTotal = itemView.findViewById(R.id.noofTotal);
            noofSupply = itemView.findViewById(R.id.noofsupply);
        }
    }
}
