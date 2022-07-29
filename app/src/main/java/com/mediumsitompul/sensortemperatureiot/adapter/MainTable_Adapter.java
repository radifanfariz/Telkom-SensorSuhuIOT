package com.mediumsitompul.sensortemperatureiot.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mediumsitompul.sensortemperatureiot.MainActivity;
import com.mediumsitompul.sensortemperatureiot.R;

import java.util.HashMap;
import java.util.List;

public class MainTable_Adapter extends RecyclerView.Adapter<MainTable_Adapter.MainTableViewHolder> {

    private Context context;
    private List<MainTable_GetSet> maintable_list;
    private HashMap<String,String> datatables;

    public MainTable_Adapter(Context context, List<MainTable_GetSet> maintable_list) {
        this.context = context;
        this.maintable_list = maintable_list;
    }

    @NonNull
    @Override
    public MainTableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.main_table_design,null);
        return new MainTableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainTableViewHolder holder, int position) {
        MainTable_GetSet mainTable_getSet = maintable_list.get(position);

        datatables = new HashMap<>();
        datatables = mainTable_getSet.getDatatables();

        holder.txtNo.setText(mainTable_getSet.getNo());
        holder.txtDate.setText(mainTable_getSet.getDate());
        holder.txtFlagging.setText(mainTable_getSet.getFlagging());
        holder.txtSuhu.setText(mainTable_getSet.getSuhu());
        holder.card_main_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("FLAGGING_PARAM",mainTable_getSet.getFlagging());
                intent.putExtra("WITEL_PARAM",datatables.get("WITEL_PARAM"));
                intent.putExtra("DATEL_PARAM",datatables.get("DATEL_PARAM"));
                intent.putExtra("STO_PARAM",datatables.get("STO_PARAM"));
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return maintable_list.size();
    }

    public static class MainTableViewHolder extends RecyclerView.ViewHolder {
        TextView txtNo;
        TextView txtDate;
        TextView txtFlagging;
        TextView txtSuhu;
        CardView card_main_table;

        public MainTableViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtNo = itemView.findViewById(R.id.txtNo);
            this.txtDate = itemView.findViewById(R.id.txtDate);
            this.txtFlagging = itemView.findViewById(R.id.txtFlagging);
            this.txtSuhu = itemView.findViewById(R.id.txtSuhu);
            this.card_main_table = itemView.findViewById(R.id.card_main_table);
        }
    }

}
