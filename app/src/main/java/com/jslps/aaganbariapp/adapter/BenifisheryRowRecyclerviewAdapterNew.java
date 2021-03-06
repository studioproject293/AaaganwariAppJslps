package com.jslps.aaganbariapp.adapter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jslps.aaganbariapp.R;
import com.jslps.aaganbariapp.listener.OnFragmentListItemSelectListener;
import com.jslps.aaganbariapp.model.BenifisheryDataModelDbNew;

import java.util.ArrayList;

public class BenifisheryRowRecyclerviewAdapterNew extends RecyclerView.Adapter<BenifisheryRowRecyclerviewAdapterNew.ViewHolder> {
    private Activity context;
    OnFragmentListItemSelectListener  onFragmentListItemSelectListener;
    ArrayList<BenifisheryDataModelDbNew> benifisheryDataModelDbArrayList;

    public BenifisheryRowRecyclerviewAdapterNew(Activity activity, ArrayList<BenifisheryDataModelDbNew> benifisheryDataModelDbArrayList) {
        this.context = activity;
        this.benifisheryDataModelDbArrayList = benifisheryDataModelDbArrayList;
    }

    @NonNull
    @Override
    public BenifisheryRowRecyclerviewAdapterNew.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.benifishery_row_new, parent, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull final BenifisheryRowRecyclerviewAdapterNew.ViewHolder holder, final int position) {
        holder.benifisheryName.setKeyListener(DigitsKeyListener.getInstance(true,true));
        holder.rice.setKeyListener(DigitsKeyListener.getInstance(true,true));
        holder.potato.setKeyListener(DigitsKeyListener.getInstance(true,true));
        holder.jaggery.setKeyListener(DigitsKeyListener.getInstance(true,true));
        holder.arharDal.setKeyListener(DigitsKeyListener.getInstance(true,true));
        holder.penauts.setKeyListener(DigitsKeyListener.getInstance(true,true));
        holder.chana.setKeyListener(DigitsKeyListener.getInstance(true,true));
        final BenifisheryDataModelDbNew benifisheryDataModelDb = benifisheryDataModelDbArrayList.get(position);
        holder.benifisheryName.setText(benifisheryDataModelDb.getBenfname());
        holder.noOfBenifishery.setText(benifisheryDataModelDb.getNoofbenf());
        benifisheryDataModelDbArrayList.get(position).setRice(holder.rice.getText().toString());
        benifisheryDataModelDbArrayList.get(position).setJaggery(holder.jaggery.getText().toString());
        benifisheryDataModelDbArrayList.get(position).setPenauts(holder.penauts.getText().toString());
        benifisheryDataModelDbArrayList.get(position).setPotato(holder.potato.getText().toString());
        benifisheryDataModelDbArrayList.get(position).setArhardal(holder.arharDal.getText().toString());
        benifisheryDataModelDbArrayList.get(position).setChana(holder.chana.getText().toString());
        onFragmentListItemSelectListener.onListItemSelected(position, benifisheryDataModelDbArrayList);
        holder.noOfBenifishery.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    if (Integer.parseInt(s.toString()) < 200) {
                        benifisheryDataModelDbArrayList.get(position).setNoofbenf(s.toString());
                        onFragmentListItemSelectListener.onListItemSelected(position, benifisheryDataModelDbArrayList);
                        onFragmentListItemSelectListener.onListItemLongClickedSnd(0, benifisheryDataModelDbArrayList, 0);
                    } else {
                        benifisheryDataModelDbArrayList.get(position).setNoofbenf(s.toString());
                        onFragmentListItemSelectListener.onListItemSelected(position, benifisheryDataModelDbArrayList);
                        onFragmentListItemSelectListener.onListItemLongClickedSnd(1, benifisheryDataModelDbArrayList, 0);
                    }
                } else {
                    benifisheryDataModelDbArrayList.get(position).setNoofbenf("0.0");
                    onFragmentListItemSelectListener.onListItemSelected(position, benifisheryDataModelDbArrayList);
                }
            }


            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        });
        holder.rice.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    if (s.toString().startsWith(".")) {
                        benifisheryDataModelDbArrayList.get(position).setRice("0" + s.toString());
                        onFragmentListItemSelectListener.onListItemSelected(position, benifisheryDataModelDbArrayList);
                    } else {
                        benifisheryDataModelDbArrayList.get(position).setRice(s.toString());
                        onFragmentListItemSelectListener.onListItemSelected(position, benifisheryDataModelDbArrayList);
                    }
                }
            }


            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        });
        holder.jaggery.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    if (s.toString().startsWith(".")) {
                        benifisheryDataModelDbArrayList.get(position).setJaggery("0" + s.toString());
                        onFragmentListItemSelectListener.onListItemSelected(position, benifisheryDataModelDbArrayList);
                    } else {
                        benifisheryDataModelDbArrayList.get(position).setJaggery(s.toString());
                        onFragmentListItemSelectListener.onListItemSelected(position, benifisheryDataModelDbArrayList);
                    }
                }
            }


            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        });
        holder.potato.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    if (s.toString().startsWith(".")) {
                        benifisheryDataModelDbArrayList.get(position).setPotato("0" + s.toString());
                        onFragmentListItemSelectListener.onListItemSelected(position, benifisheryDataModelDbArrayList);
                    } else {
                        benifisheryDataModelDbArrayList.get(position).setPotato(s.toString());
                        onFragmentListItemSelectListener.onListItemSelected(position, benifisheryDataModelDbArrayList);
                    }
                }
            }


            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        });
        holder.arharDal.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    if (s.toString().startsWith(".")) {
                        benifisheryDataModelDbArrayList.get(position).setArhardal("0" + s.toString());
                        onFragmentListItemSelectListener.onListItemSelected(position, benifisheryDataModelDbArrayList);
                    } else {
                        benifisheryDataModelDbArrayList.get(position).setArhardal(s.toString());
                        onFragmentListItemSelectListener.onListItemSelected(position, benifisheryDataModelDbArrayList);
                    }
                }
            }


            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        });
        holder.chana.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    if (s.toString().startsWith(".")) {
                        benifisheryDataModelDbArrayList.get(position).setChana("0" + s.toString());
                        onFragmentListItemSelectListener.onListItemSelected(position, benifisheryDataModelDbArrayList);
                    } else {
                        benifisheryDataModelDbArrayList.get(position).setChana(s.toString());
                        onFragmentListItemSelectListener.onListItemSelected(position, benifisheryDataModelDbArrayList);
                    }
                }
            }


            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        });
        holder.penauts.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (!TextUtils.isEmpty(s.toString())) {
                    if (s.toString().startsWith(".")) {
                        benifisheryDataModelDbArrayList.get(position).setPenauts("0"+s.toString());
                        onFragmentListItemSelectListener.onListItemSelected(position, benifisheryDataModelDbArrayList);
                    }else {
                        benifisheryDataModelDbArrayList.get(position).setPenauts(s.toString());
                        onFragmentListItemSelectListener.onListItemSelected(position, benifisheryDataModelDbArrayList);
                    }
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
        TextView benifisheryName;
        EditText noOfBenifishery, rice, arharDal, penauts, chana, jaggery, potato;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            benifisheryName = itemView.findViewById(R.id.benifisheryName);
            rice = itemView.findViewById(R.id.rice);
            potato = itemView.findViewById(R.id.potatao);
            noOfBenifishery = itemView.findViewById(R.id.noofbenifishery);
            jaggery = itemView.findViewById(R.id.jaggery);
            arharDal = itemView.findViewById(R.id.dalArhar);
            penauts = itemView.findViewById(R.id.penauts);
            chana = itemView.findViewById(R.id.chana);
        }
    }

    public void updateList(ArrayList<BenifisheryDataModelDbNew> benifisheryDataModelDbArrayList) {
        this.benifisheryDataModelDbArrayList = benifisheryDataModelDbArrayList;
        notifyDataSetChanged();
    }
}
