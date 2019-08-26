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
import com.jslps.aaganbariapp.model.PanchyatDataModelDb;
import com.jslps.aaganbariapp.model.VOListDataModelDb;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;

public class PanchyatRecyclerviewAdapter extends RecyclerView.Adapter<PanchyatRecyclerviewAdapter.ViewHolder> {
    private Activity context;
    OnFragmentListItemSelectListener onFragmentListItemSelectListener;
    ArrayList<PanchyatDataModelDb> panchyatDataModelDbs;
    private PrefManager prefManager;

    public PanchyatRecyclerviewAdapter(Activity activity, ArrayList<PanchyatDataModelDb> panchyatDataModelDbs) {
        this.context = activity;
        this.panchyatDataModelDbs = panchyatDataModelDbs;

    }

    @NonNull
    @Override
    public PanchyatRecyclerviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row;
        if (panchyatDataModelDbs != null && panchyatDataModelDbs.size() > 3) {
            row = LayoutInflater.from(context).inflate(R.layout.pay_options_row, parent, false);
        } else {
            row = LayoutInflater.from(context).inflate(R.layout.pay_options_row1, parent, false);

        }
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull PanchyatRecyclerviewAdapter.ViewHolder holder, final int position) {
        final PanchyatDataModelDb panchyatDataModelDb = panchyatDataModelDbs.get(position);
        ArrayList<VOListDataModelDb> panchyatDataModelDbs = (ArrayList<VOListDataModelDb>) Select.from(VOListDataModelDb.class)
                .where(Condition.prop("clustercode").eq(panchyatDataModelDb.getClustercode())).list();
        prefManager = PrefManager.getInstance();
        if (prefManager.getPrefLangaugeSelection().equalsIgnoreCase("english"))
            holder.title.setText(panchyatDataModelDb.getClustername() + " (" + panchyatDataModelDbs.size() + ")");
        else
            holder.title.setText(panchyatDataModelDb.getClusternameh() + " (" + panchyatDataModelDbs.size() + ")");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefManager.setPrefPanchyatCode(panchyatDataModelDb.getClustercode());
                prefManager.setPrefPanchyatNAME(panchyatDataModelDb.getClustername());
                onFragmentListItemSelectListener.onListItemSelected(position, panchyatDataModelDb);
            }
        });
    }

    @Override
    public int getItemCount() {
        return panchyatDataModelDbs.size();
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
