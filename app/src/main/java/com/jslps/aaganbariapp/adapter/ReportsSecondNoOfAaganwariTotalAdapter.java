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

import java.util.ArrayList;

public class ReportsSecondNoOfAaganwariTotalAdapter extends RecyclerView.Adapter<ReportsSecondNoOfAaganwariTotalAdapter.ViewHolder> {
    private Activity context;
    OnFragmentListItemSelectListener onFragmentListItemSelectListener;
    ArrayList<AanganWariModelDb> arrayListString;

    public ReportsSecondNoOfAaganwariTotalAdapter(Activity activity, ArrayList<AanganWariModelDb> arrayListString) {
        this.context = activity;
        this.arrayListString = arrayListString;
    }

    @NonNull
    @Override
    public ReportsSecondNoOfAaganwariTotalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.aaganwarilist_row, parent, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportsSecondNoOfAaganwariTotalAdapter.ViewHolder holder, final int position) {
        holder.title.setText(arrayListString.size()+"");


    }

    @Override
    public int getItemCount() {
        return arrayListString.size();
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
