package com.jslps.aaganbariapp.adapter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.jslps.aaganbariapp.R;
import com.jslps.aaganbariapp.fragment.EntryFormFragment;
import com.jslps.aaganbariapp.listener.OnFragmentListItemSelectListener;
import com.jslps.aaganbariapp.model.BenifisheryDataModelDb;
import com.jslps.aaganbariapp.model.BenifisheryDataModelDbSend;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;

public class SyncWithServerRecyclerviewAdapter extends RecyclerView.Adapter<SyncWithServerRecyclerviewAdapter.ViewHolder> {
    private Activity context;
    OnFragmentListItemSelectListener onFragmentListItemSelectListener;
    ArrayList<BenifisheryDataModelDbSend> benifisheryDataModelDbArrayList;
    ArrayList<BenifisheryDataModelDbSend> benifisheryDataModelDbSendss = new ArrayList<>();

    public SyncWithServerRecyclerviewAdapter(Activity activity, ArrayList<BenifisheryDataModelDbSend> benifisheryDataModelDbArrayList) {
        this.context = activity;
        this.benifisheryDataModelDbArrayList = benifisheryDataModelDbArrayList;

    }

    @NonNull
    @Override
    public SyncWithServerRecyclerviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.syncwithserver_row, parent, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull final SyncWithServerRecyclerviewAdapter.ViewHolder holder, final int position) {
        final BenifisheryDataModelDbSend benifisheryDataModelDbSend = benifisheryDataModelDbArrayList.get(position);
        holder.benifisheryName.setText(benifisheryDataModelDbSend.getPanchyatname());
        holder.month.setText(benifisheryDataModelDbSend.getMonth());
        ArrayList<BenifisheryDataModelDbSend> panchyatDataModelDbs = (ArrayList<BenifisheryDataModelDbSend>) Select.from(BenifisheryDataModelDbSend.class)
                .where(Condition.prop("month").eq(benifisheryDataModelDbSend.getMonth())).list();
        holder.noofmealinmonth.setText("1");
        benifisheryDataModelDbSendss.add(position,benifisheryDataModelDbSend);
        panchyatDataModelDbs.get(position).setVoname(null);
        panchyatDataModelDbs.get(position).setAaganwariname(null);
        panchyatDataModelDbs.get(position).setPanchyatname(null);
        panchyatDataModelDbs.get(position).setId(null);
        panchyatDataModelDbs.get(position).setCreatedon(null);

        holder.checkBoxUnit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    benifisheryDataModelDbSendss.add(position, benifisheryDataModelDbSend);
                    //System.out.println("jdfjhjds"+new Gson().toJson(benifisheryDataModelDbSend));
                } else {
                    benifisheryDataModelDbSendss.remove(position);
                    //System.out.println("jdfjhjds"+new Gson().toJson(benifisheryDataModelDbSend));

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return benifisheryDataModelDbArrayList.size();
    }

    public void setListner(OnFragmentListItemSelectListener onFragmentListItemSelectListener) {
        this.onFragmentListItemSelectListener = onFragmentListItemSelectListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView benifisheryName;
        TextView month, noofmealinmonth;
        CheckBox checkBoxUnit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            benifisheryName = itemView.findViewById(R.id.benifisheryName);
            checkBoxUnit = itemView.findViewById(R.id.unitRate);
            month = itemView.findViewById(R.id.noofbenifishery);
            noofmealinmonth = itemView.findViewById(R.id.noofmealinmonth);
        }
    }
}
