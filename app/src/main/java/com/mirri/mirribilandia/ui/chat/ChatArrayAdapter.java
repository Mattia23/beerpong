package com.mirri.mirribilandia.ui.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mirri.mirribilandia.R;

import java.util.ArrayList;
import java.util.List;

public class ChatArrayAdapter extends ArrayAdapter<ChatMessage> {

    private TextView chatText;
    private TextView user;
    private TextView orario;
    private List<ChatMessage> chatMessageList = new ArrayList<>();
    private Context context;

    @Override
    public void add(ChatMessage object) {
        chatMessageList.add(object);
        super.add(object);
    }

    public ChatArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
    }

    public int getCount() {
        return this.chatMessageList.size();
    }

    public ChatMessage getItem(int index) {
        return this.chatMessageList.get(index);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessage chatMessageObj = getItem(position);
        View row;
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (chatMessageObj.left) {
            row = inflater.inflate(R.layout.chat_right, parent, false);
            chatText = row.findViewById(R.id.msgr);
            if(chatMessageObj.message.length() <= 5){
                String newChatMessage = chatMessageObj.message;
                for(int num = chatMessageObj.message.length(); num <= 5; num++){
                    newChatMessage = " " + newChatMessage;
                }
                chatText.setText(newChatMessage);
            }else{
                chatText.setText(chatMessageObj.message);
            }
        }else{
            row = inflater.inflate(R.layout.chat_left, parent, false);
            chatText = row.findViewById(R.id.msgr);
            if(chatMessageObj.message.length() <= 15){
                String newChatMessage = chatMessageObj.message;
                for(int num = chatMessageObj.message.length(); num <= 15; num++){
                    newChatMessage = newChatMessage.concat(" ");
                }
                chatText.setText(newChatMessage);
            }else{
                chatText.setText(chatMessageObj.message);
            }

            user = row.findViewById(R.id.username);
            user.setText(chatMessageObj.id);
        }


        orario = row.findViewById(R.id.orario);
        orario.setText(chatMessageObj.orario);
        return row;
    }
}