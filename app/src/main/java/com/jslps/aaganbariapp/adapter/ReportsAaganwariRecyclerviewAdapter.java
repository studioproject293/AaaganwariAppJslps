package com.jslps.aaganbariapp.adapter;

import android.app.Activity;
import android.text.TextUtils;
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
import com.jslps.aaganbariapp.model.ReportFilterModel;
import com.jslps.aaganbariapp.model.VOListDataModelDb;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;

public class ReportsAaganwariRecyclerviewAdapter extends RecyclerView.Adapter<ReportsAaganwariRecyclerviewAdapter.ViewHolder> {
    private Activity context;
    OnFragmentListItemSelectListener onFragmentListItemSelectListener;
    ArrayList<String> voListDataModelDbs;
    private PrefManager prefManager;

    public ReportsAaganwariRecyclerviewAdapter(Activity activity, ArrayList<String> voListDataModelDbs) {
        this.context = activity;
        this.voListDataModelDbs = voListDataModelDbs;

    }

    @NonNull
    @Override
    public ReportsAaganwariRecyclerviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.reports_aaganwari_list_row, parent, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportsAaganwariRecyclerviewAdapter.ViewHolder holder, final int position) {

        holder.title.setText(voListDataModelDbs.get(position));
    }

    @Override
    public int getItemCount() {
        return voListDataModelDbs.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
    }
}
