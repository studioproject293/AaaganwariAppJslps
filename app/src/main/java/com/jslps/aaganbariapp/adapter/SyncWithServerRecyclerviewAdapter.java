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
import com.jslps.aaganbariapp.model.DataSaveModel1;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;

public class SyncWithServerRecyclerviewAdapter extends RecyclerView.Adapter<SyncWithServerRecyclerviewAdapter.ViewHolder> {
    private Activity context;
    OnFragmentListItemSelectListener onFragmentListItemSelectListener;

    ArrayList<DataSaveModel1> dataSaveModel1ArrayList = new ArrayList<>();
    ArrayList<DataSaveModel1> dataSaveModel1ArrayListNew = new ArrayList<>();
    int size;

    public SyncWithServerRecyclerviewAdapter(Activity activity, ArrayList<DataSaveModel1> benifisheryDataModelDbArrayList) {
        this.context = activity;
        this.dataSaveModel1ArrayList = benifisheryDataModelDbArrayList;

    }

    @NonNull
    @Override
    public SyncWithServerRecyclerviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.syncwithserver_row, parent, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull final SyncWithServerRecyclerviewAdapter.ViewHolder holder, final int position) {
        final DataSaveModel1 benifisheryDataModelDbSend = dataSaveModel1ArrayList.get(position);
        holder.benifisheryName.setText(benifisheryDataModelDbSend.getPanchayatname());
        holder.month.setText(benifisheryDataModelDbSend.getMonth());
        holder.year.setText(benifisheryDataModelDbSend.getYear());
        holder.noofmealinmonth.setText(benifisheryDataModelDbSend.getAaganwaricount());

         if (holder.checkBoxUnit.isChecked()){
             dataSaveModel1ArrayListNew.add(position,benifisheryDataModelDbSend);
             onFragmentListItemSelectListener.onListItemLongClickedSnd(1,dataSaveModel1ArrayListNew,position);
         }
        holder.checkBoxUnit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    dataSaveModel1ArrayListNew.add(position,benifisheryDataModelDbSend);
                    onFragmentListItemSelectListener.onListItemLongClickedSnd(1,dataSaveModel1ArrayListNew,position);
                } else {
                    dataSaveModel1ArrayListNew.remove(position);
                    onFragmentListItemSelectListener.onListItemLongClickedSnd(0,dataSaveModel1ArrayListNew,position);

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSaveModel1ArrayList.size();
    }

    public void setListner(OnFragmentListItemSelectListener onFragmentListItemSelectListener) {
        this.onFragmentListItemSelectListener = onFragmentListItemSelectListener;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView benifisheryName, year;
        TextView month, noofmealinmonth;
        CheckBox checkBoxUnit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            benifisheryName = itemView.findViewById(R.id.benifisheryName);
            checkBoxUnit = itemView.findViewById(R.id.unitRate);
            month = itemView.findViewById(R.id.noofbenifishery);
            noofmealinmonth = itemView.findViewById(R.id.noofmealinmonth);
            year = itemView.findViewById(R.id.year);
        }
    }
}
