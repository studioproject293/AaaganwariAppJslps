package com.jslps.aaganbariapp.adapter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jslps.aaganbariapp.Constant;
import com.jslps.aaganbariapp.R;
import com.jslps.aaganbariapp.fragment.EntryFormFragmentEdit;
import com.jslps.aaganbariapp.listener.OnFragmentListItemSelectListener;
import com.jslps.aaganbariapp.model.BenifisheryDataModelDbSend;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BenifisheryRowEditRecyclerviewAdapter extends RecyclerView.Adapter<BenifisheryRowEditRecyclerviewAdapter.ViewHolder> {
    private Activity context;
    OnFragmentListItemSelectListener onFragmentListItemSelectListener;
    ArrayList<BenifisheryDataModelDbSend> benifisheryDataModelDbArrayList;

    public BenifisheryRowEditRecyclerviewAdapter(Activity activity, ArrayList<BenifisheryDataModelDbSend> benifisheryDataModelDbArrayList) {
        this.context = activity;
        this.benifisheryDataModelDbArrayList = benifisheryDataModelDbArrayList;

    }

    @NonNull
    @Override
    public BenifisheryRowEditRecyclerviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.benifishery_row, parent, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull final BenifisheryRowEditRecyclerviewAdapter.ViewHolder holder, final int position) {
        final BenifisheryDataModelDbSend benifisheryDataModelDb = benifisheryDataModelDbArrayList.get(position);
        holder.benifisheryName.setText(benifisheryDataModelDb.getBenfname());
        holder.noofmealinmonth.setText(benifisheryDataModelDb.getNoofmeal());
        holder.noOfBenifishery.setText(benifisheryDataModelDb.getNoofbenf());
        holder.unitRate.setText(new DecimalFormat("##.##").format(Double.parseDouble(benifisheryDataModelDb.getUnitrateofmeal())));
        Double total = 0.0;
        if (Constant.editFlag) {
            holder.noOfBenifishery.setEnabled(true);
            holder.noofmealinmonth.setEnabled(true);

        } else {
            holder.noOfBenifishery.setEnabled(false);
            holder.noofmealinmonth.setEnabled(false);
        }
        total = Double.parseDouble(benifisheryDataModelDb.getUnitrateofmeal()) * Double.parseDouble(benifisheryDataModelDb.getNoofmeal()) *
                Double.parseDouble(benifisheryDataModelDb.getNoofbenf());

        holder.textViewTotal.setText(new DecimalFormat("##.##").format(Double.parseDouble(total.toString())));
        holder.noOfBenifishery.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    //do your work here
                    Double total = 0.0;

                    total = Double.parseDouble(benifisheryDataModelDb.getUnitrateofmeal()) * Double.parseDouble(benifisheryDataModelDb.getNoofmeal()) *
                            Double.parseDouble(s.toString());

                    holder.textViewTotal.setText(total.toString());
                    benifisheryDataModelDb.setNoofbenf(holder.noOfBenifishery.getText().toString());
                    benifisheryDataModelDb.setNoofmeal(holder.noofmealinmonth.getText().toString());
                    benifisheryDataModelDb.setUnitrateofmeal(new DecimalFormat("##.##").format(holder.unitRate.getText().toString()));
                    benifisheryDataModelDb.setAmount(holder.textViewTotal.getText().toString());
                    //benifisheryDataModelDb.save();
                    Double totalAll = 0.0;
                    for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
                        totalAll += Double.parseDouble(benifisheryDataModelDbArrayList.get(i).getUnitrateofmeal()) * Double.parseDouble(benifisheryDataModelDbArrayList.get(i).getNoofmeal()) *
                                Double.parseDouble(benifisheryDataModelDbArrayList.get(i).getNoofbenf());
                    }
                    EntryFormFragmentEdit.textViewtotalAll.setText(totalAll.toString());
                } else {
                    Double total = 0.0;

                    total = Double.parseDouble(benifisheryDataModelDb.getUnitrateofmeal()) * Double.parseDouble(benifisheryDataModelDb.getNoofmeal());
                    holder.textViewTotal.setText(new DecimalFormat("##.##").format(Double.parseDouble(total.toString())));
                    benifisheryDataModelDb.setNoofbenf(holder.noOfBenifishery.getText().toString());
                    benifisheryDataModelDb.setNoofmeal(holder.noofmealinmonth.getText().toString());
                    benifisheryDataModelDb.setUnitrateofmeal(new DecimalFormat("##.##").format(Double.parseDouble(holder.unitRate.getText().toString())));
                    benifisheryDataModelDb.setAmount(new DecimalFormat("##.##").format(Double.parseDouble( holder.textViewTotal.getText().toString())));
                    Double totalAll = 0.0;
                    for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
                        if (!TextUtils.isEmpty(benifisheryDataModelDbArrayList.get(i).getNoofbenf())) {
                            totalAll += Double.parseDouble(benifisheryDataModelDbArrayList.get(i).getUnitrateofmeal()) * Double.parseDouble(benifisheryDataModelDbArrayList.get(i).getNoofmeal()) *
                                    Double.parseDouble(benifisheryDataModelDbArrayList.get(i).getNoofbenf());
                        } else {
                            totalAll += Double.parseDouble(benifisheryDataModelDbArrayList.get(i).getUnitrateofmeal()) * Double.parseDouble(benifisheryDataModelDbArrayList.get(i).getNoofmeal());
                        }
                    }
                    EntryFormFragmentEdit.textViewtotalAll.setText(totalAll.toString());
                }
            }


            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        });
        holder.noofmealinmonth.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    //do your work here
                    Double total = 0.0;

                    total = Double.parseDouble(benifisheryDataModelDb.getUnitrateofmeal()) * Double.parseDouble(s.toString()) *
                            Double.parseDouble(benifisheryDataModelDb.getNoofbenf());

                    holder.textViewTotal.setText(new DecimalFormat("##.##").format(Double.parseDouble(total.toString())));
                    benifisheryDataModelDb.setNoofbenf(holder.noOfBenifishery.getText().toString());
                    benifisheryDataModelDb.setNoofmeal(holder.noofmealinmonth.getText().toString());
                    benifisheryDataModelDb.setUnitrateofmeal(new DecimalFormat("##.##").format(Double.parseDouble(holder.unitRate.getText().toString())));
                    benifisheryDataModelDb.setAmount(new DecimalFormat("##.##").format(Double.parseDouble( holder.textViewTotal.getText().toString())));
                    //benifisheryDataModelDb.save();
                    Double totalAll = 0.0;
                    for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
                        totalAll += Double.parseDouble(benifisheryDataModelDbArrayList.get(i).getUnitrateofmeal()) * Double.parseDouble(benifisheryDataModelDbArrayList.get(i).getNoofmeal()) *
                                Double.parseDouble(benifisheryDataModelDbArrayList.get(i).getNoofbenf());
                    }
                    EntryFormFragmentEdit.textViewtotalAll.setText(totalAll.toString());

                } else {
                    Double total = 0.0;
                    if (!TextUtils.isEmpty(s.toString()))
                        total = Double.parseDouble(benifisheryDataModelDb.getUnitrateofmeal()) * Double.parseDouble(s.toString()) *
                                Double.parseDouble(benifisheryDataModelDb.getNoofbenf());
                    else
                        total = Double.parseDouble(benifisheryDataModelDb.getUnitrateofmeal())  *
                                Double.parseDouble(benifisheryDataModelDb.getNoofbenf());
                    holder.textViewTotal.setText(total.toString());
                    Double totalAll = 0.0;
                    for (int i = 0; i < benifisheryDataModelDbArrayList.size(); i++) {
                        if (!TextUtils.isEmpty(benifisheryDataModelDbArrayList.get(i).getNoofmeal())) {
                            totalAll += Double.parseDouble(benifisheryDataModelDbArrayList.get(i).getUnitrateofmeal()) * Double.parseDouble(benifisheryDataModelDbArrayList.get(i).getNoofmeal()) *
                                    Double.parseDouble(benifisheryDataModelDbArrayList.get(i).getNoofbenf());
                        } else {
                            totalAll += Double.parseDouble(benifisheryDataModelDbArrayList.get(i).getUnitrateofmeal()) *
                                    Double.parseDouble(benifisheryDataModelDbArrayList.get(i).getNoofbenf());
                        }
                    }
                    EntryFormFragmentEdit.textViewtotalAll.setText(totalAll.toString());
                }
            }


            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

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

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView benifisheryName, unitRate, textViewTotal;
        EditText noOfBenifishery, noofmealinmonth;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            benifisheryName = itemView.findViewById(R.id.benifisheryName);
            unitRate = itemView.findViewById(R.id.unitRate);
            textViewTotal = itemView.findViewById(R.id.total);
            noOfBenifishery = itemView.findViewById(R.id.noofbenifishery);
            noofmealinmonth = itemView.findViewById(R.id.noofmealinmonth);
        }
    }
}
