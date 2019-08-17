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
import com.jslps.aaganbariapp.model.BenifisheryDataModelDbSend;
import com.jslps.aaganbariapp.model.VOListDataModelDb;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.Calendar;

public class AanganWariRecyclerviewAdapter extends RecyclerView.Adapter<AanganWariRecyclerviewAdapter.ViewHolder> {
    private Activity context;
    OnFragmentListItemSelectListener onFragmentListItemSelectListener;
    ArrayList<AanganWariModelDb> voListDataModelDbs;
    VOListDataModelDb voListDataModelDb;
    private PrefManager prefManager;

    public AanganWariRecyclerviewAdapter(Activity activity, ArrayList<AanganWariModelDb> voListDataModelDbs, VOListDataModelDb voListDataModelDbRec) {
        this.context = activity;
        this.voListDataModelDbs = voListDataModelDbs;
        this.voListDataModelDb = voListDataModelDbRec;
    }

    @NonNull
    @Override
    public AanganWariRecyclerviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.aaganwarilist_row, parent, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull AanganWariRecyclerviewAdapter.ViewHolder holder, final int position) {
        prefManager = PrefManager.getInstance();
        final AanganWariModelDb panchyatDataModelDb = voListDataModelDbs.get(position);
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        holder.title.setText(panchyatDataModelDb.getAnganwadiname() + " (" + panchyatDataModelDb.getAwid() + ")");

        ArrayList<BenifisheryDataModelDbSend> panchyatDataModelDbs = (ArrayList<BenifisheryDataModelDbSend>) Select.from(BenifisheryDataModelDbSend.class)
                .where(Condition.prop("month").eq(month),
                        Condition.prop("vocode").eq(voListDataModelDb.getVocode()),
                        Condition.prop("year").eq(year),
                        Condition.prop("aaganwaricode").eq(panchyatDataModelDb.getAwid())).list();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefManager.setPrefAaganwariCode(panchyatDataModelDb.getAwid());
                prefManager.setPrefAaganwariNAME(panchyatDataModelDb.getAnganwadiname());
                onFragmentListItemSelectListener.onListItemSelected(position, panchyatDataModelDb);
            }
        });
        ArrayList<BenifisheryDataModelDbSend> arrayList = new ArrayList<>();
        ArrayList<String> arrayListString = new ArrayList<>();
        for (int i = 0; i < panchyatDataModelDbs.size(); i++) {
            BenifisheryDataModelDbSend benifisheryDataModelDbSend = panchyatDataModelDbs.get(i);
            String aaganbarcode = benifisheryDataModelDbSend.getAaganwaricode();

            if (!arrayListString.contains(aaganbarcode)) {
                arrayListString.add(aaganbarcode);
                arrayList.add(benifisheryDataModelDbSend);

            }
        }
        ArrayList<BenifisheryDataModelDbSend> arrayList1 = new ArrayList<>();
        ArrayList<String> arrayListString1 = new ArrayList<>();
        for (int i = 0; i < Select.from(BenifisheryDataModelDbSend.class).list().size(); i++) {
            BenifisheryDataModelDbSend benifisheryDataModelDbSend1 =Select.from(BenifisheryDataModelDbSend.class).list().get(i);
            String aaganbarcode1 = benifisheryDataModelDbSend1.getAaganwaricode();

            if (!arrayListString1.contains(aaganbarcode1)) {
                arrayListString1.add(aaganbarcode1);
                arrayList1.add(benifisheryDataModelDbSend1);

            }
        }
       // holder.total.setText("Total Record: "+arrayList.size() + "/" +arrayList1.size());
    }

    @Override
    public int getItemCount() {
        return voListDataModelDbs.size();
    }

    public void setListner(OnFragmentListItemSelectListener onFragmentListItemSelectListener) {
        this.onFragmentListItemSelectListener = onFragmentListItemSelectListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, total;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            total = itemView.findViewById(R.id.total);
        }
    }
}
