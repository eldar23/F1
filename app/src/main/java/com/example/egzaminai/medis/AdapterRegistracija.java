package com.example.egzaminai.medis;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class AdapterRegistracija extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<Registracija> data= Collections.emptyList();
    Registracija current;
    int currentPos=0;

    public static final String ENTRY = "com.example.egzaminai.medis.ENTRY";

    // create constructor to initialize context and data sent from MainActivity
    public AdapterRegistracija(Context context, List<Registracija> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    // Inflate the layout when ViewHolder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.container_registracija, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in RecyclerView to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        Registracija current=data.get(position);
        myHolder.textId.setText("ID: " + current.getId());
        myHolder.textLenktynininkas.setText(current.getLenktynininkas());
        myHolder.textVartotojas.setText("User: " + current.getVartotojas());
        myHolder.textTrasa.setText("Circuit: " + current.getTrasa());
        myHolder.textLaikas.setText("Time(h): " + current.getLaikas());
        myHolder.textData.setText("Date: " + current.getData());
        myHolder.textKomandos.setText("Team: " + current.getKomandos());
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textLenktynininkas;
        TextView textVartotojas;
        TextView textTrasa;
        TextView textLaikas;
        TextView textData;
        TextView textKomandos;
        TextView textId;


        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            textLenktynininkas = (TextView) itemView.findViewById(R.id.textDriver);
            textVartotojas = (TextView) itemView.findViewById(R.id.textVartotojas);
            textTrasa = (TextView) itemView.findViewById(R.id.textTrasa);
            textLaikas = (TextView) itemView.findViewById(R.id.textLaikas);
            textData = (TextView) itemView.findViewById(R.id.textData);
            textKomandos = (TextView) itemView.findViewById(R.id.textKomandos);
            textId = (TextView) itemView.findViewById(R.id.textId);

            itemView.setOnClickListener(this);

        }

        // Click event for all items
        @Override
        public void onClick(View v) {
            int itemPosition = getAdapterPosition();
            String entryID = data.get(itemPosition).getId();

            Registracija driver = data.get(itemPosition);

            Intent intent = new Intent(context, NewEntryActivity.class);
            //intent.putExtra(ENTRY, entryID);
            intent.putExtra(ENTRY, driver);
            context.startActivity(intent);
        }

    }

}
