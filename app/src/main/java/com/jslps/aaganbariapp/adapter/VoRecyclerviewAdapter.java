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
import com.jslps.aaganbariapp.model.VOListDataModelDb;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;

public class VoRecyclerviewAdapter extends RecyclerView.Adapter<VoRecyclerviewAdapter.ViewHolder> {
    private Activity context;
    OnFragmentListItemSelectListener onFragmentListItemSelectListener;
    ArrayList<VOListDataModelDb> voListDataModelDbs;
    private PrefManager prefManager;

    public VoRecyclerviewAdapter(Activity activity, ArrayList<VOListDataModelDb> voListDataModelDbs) {
        this.context = activity;
        this.voListDataModelDbs = voListDataModelDbs;

    }

    @NonNull
    @Override
    public VoRecyclerviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row;
//        View row = LayoutInflater.from(context).inflate(R.layout.vo_list_row, parent, false);
        if (voListDataModelDbs != null && voListDataModelDbs.size() > 3) {
            row = LayoutInflater.from(context).inflate(R.layout.vo_list_row, parent, false);
        } else {
            row = LayoutInflater.from(context).inflate(R.layout.vo_list_row, parent, false);

        }
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull VoRecyclerviewAdapter.ViewHolder holder, final int position) {
        prefManager = PrefManager.getInstance();
        final VOListDataModelDb panchyatDataModelDb = voListDataModelDbs.get(position);
       ArrayList<AanganWariModelDb> panchyatDataModelDbs = (ArrayList<AanganWariModelDb>) Select.from(AanganWariModelDb.class)
                .where(Condition.prop("vocode").eq(panchyatDataModelDb.getVocode())).list();
        if (TextUtils.isEmpty(prefManager.getPrefLangaugeSelection())) {
            if (prefManager.getPrefLangaugeSelection().equalsIgnoreCase("english"))
                holder.title.setText(panchyatDataModelDb.getVoshg() + " (" + panchyatDataModelDbs.size() + ")");
            else
                holder.title.setText(panchyatDataModelDb.getVoshghindi() + " (" + panchyatDataModelDbs.size() + ")");
        }else holder.title.setText(panchyatDataModelDb.getVoshg() + " (" + panchyatDataModelDbs.size() + ")");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefManager.setPREF_VOCode(panchyatDataModelDb.getVocode());
                prefManager.setPREF_VONAME(panchyatDataModelDb.getVoshg());
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

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
    }
}
