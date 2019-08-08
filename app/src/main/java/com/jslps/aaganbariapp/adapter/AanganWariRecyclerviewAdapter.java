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

public class AanganWariRecyclerviewAdapter extends RecyclerView.Adapter<AanganWariRecyclerviewAdapter.ViewHolder> {
    private Activity context;
    OnFragmentListItemSelectListener onFragmentListItemSelectListener;
    ArrayList<AanganWariModelDb> voListDataModelDbs;
    private PrefManager prefManager;

    public AanganWariRecyclerviewAdapter(Activity activity, ArrayList<AanganWariModelDb> voListDataModelDbs) {
        this.context = activity;
        this.voListDataModelDbs = voListDataModelDbs;

    }

    @NonNull
    @Override
    public AanganWariRecyclerviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.vo_list_row, parent, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull AanganWariRecyclerviewAdapter.ViewHolder holder, final int position) {
        prefManager = PrefManager.getInstance();
        final AanganWariModelDb panchyatDataModelDb = voListDataModelDbs.get(position);
        holder.title.setText(panchyatDataModelDb.getAnganwadiname() + " (" + panchyatDataModelDb.getAwid() + ")");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefManager.setPrefAaganwariCode(panchyatDataModelDb.getAwid());
                prefManager.setPrefAaganwariNAME(panchyatDataModelDb.getAnganwadiname());
                onFragmentListItemSelectListener.onListItemSelected(position, panchyatDataModelDb);
            }
        });
    }

    @Override
    public int getItemCount() {
        return voListDataModelDbs.size();
    }

    public void setListner(OnFragmentListItemSelectListener onFragmentListItemSelectListener) {
        this.onFragmentListItemSelectListener = onFragmentListItemSelectListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
    }
}
