package com.example.cesiapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import com.example.cesiapp.R;


/**
 * Created by sca on 02/06/15.
 */
public class MessagesAdapter extends BaseAdapter {

    private final Context context;

    public MessagesAdapter(Context ctx){
        this.context = ctx;
    }

    List<Message> messages = new LinkedList<>();

    public void addMessage(List<Message> messages){
        this.messages = messages;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(messages == null){
            return 0;
        }
        return messages.size();
    }

    @Override
    public Message getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView==null){
            //On "inflate" le layout
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_message, parent, false);
            //Création du viewholder
            vh = new ViewHolder();
            vh.username = (TextView) convertView.findViewById(R.id.msg_user);
            vh.message = (TextView) convertView.findViewById(R.id.msg_message);
            vh.date = (TextView) convertView.findViewById(R.id.msg_date);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        //Affection des valeurs récupérées
        vh.username.setText(messages.get(position).getUsername());
        vh.message.setText(messages.get(position).getMsg());
        vh.date.setText(DateHelper.getFormattedDate(messages.get(position).getDate()));

        return convertView;
    }

    //Dans l'idéal à mettre dans un fichier à part entière mais suffisant ici
    private class ViewHolder{
        TextView username;
        TextView message;
        TextView date;
    }
}
