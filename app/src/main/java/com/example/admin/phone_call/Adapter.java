package com.example.admin.phone_call;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Admin on 2/28/2018.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {


    private final Context context;
    public AlarmManager alarmManager;
    private final List<Contact> contactlist;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name, number, status;
        public ImageView thumbnail;
        public Button button,button1;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.Contact_name);
            number = itemView.findViewById(R.id.Contact_number);
            status = itemView.findViewById(R.id.status);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            button = itemView.findViewById(R.id.cancel);
            button1 = itemView.findViewById(R.id.callback);

            button.setTag(R.integer.button_view,itemView);
            button1.setTag(R.integer.button1_view,itemView);
            button.setOnClickListener(this);
            button1.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            //checking for on button click listener
            if (v.getId() == button.getId()){
                int x = getAdapterPosition();
                String number = contactlist.get(x).getNumber();
                String[] string = new String[]{contactlist.get(x).getName(),number,contactlist.get(x).getThumbnail()};
                button.setVisibility(View.INVISIBLE);
                DatabaseHelper db = new DatabaseHelper(context);
                db.updateContact(new Contact("","","Cancelled",""));
                NotificationManager notificationManager = new NotificationManager(context.getApplicationContext(),string);
                notificationManager.cancel(number);
            }
            else if (v.getId() == button1.getId()){
                int x = getAdapterPosition();
                String number = contactlist.get(x).getNumber();
                String dial = "tel:"+number;
                context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
            }
        }
    }


    public Adapter(Context context,List<Contact> contactList){
        this.context = context;
        this.contactlist = contactList;
    }

    @Override
    public Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home,parent,false);
        return new MyViewHolder(itemview);
    }
    //setting values in widgets
    @Override
    public void onBindViewHolder(Adapter.MyViewHolder holder, int position) {
        holder.name.setText(contactlist.get(position).getName());
        holder.number.setText(contactlist.get(position).getNumber());
        if ((contactlist.get(position).getStatus()).equals("Pending")){

        }else{
            holder.button.setVerticalScrollbarPosition(View.INVISIBLE);

        }
        try {
            holder.thumbnail.setImageURI(Uri.parse(contactlist.get(position).getThumbnail()));
        }catch (NullPointerException x){

        }
    }

    @Override
    public int getItemCount() {
        return contactlist.size();
    }

}
