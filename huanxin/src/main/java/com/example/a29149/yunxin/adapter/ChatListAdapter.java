package com.example.a29149.yunxin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a29149.yunxin.R;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.ArrayList;

/**
 * Created by 张丽华 on 2017/5/21.
 * Description:
 */

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.Holder> {

    private ArrayList<EMMessage> emMessages;
    private String me;
    private Context mContext;

    private final int TO_ME = 0;
    private final int FROM_ME = 1;

    public ChatListAdapter(Context context, String me, String headUrl) {
        mContext = context;
        this.me = me;
    }

    @Override
    public int getItemViewType(int position) {
        if (getEmMessages().get(position).getTo().equals(me))
            return TO_ME;
        else
            return FROM_ME;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TO_ME)
            return new Holder(LayoutInflater.from(mContext).inflate(R.layout.nim_message_item_left, parent, false));
        else
            return new Holder(LayoutInflater.from(mContext).inflate(R.layout.nim_message_item_right, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.content.setText(((EMTextMessageBody)getEmMessages().get(position).getBody()).getMessage());
        holder.time.setText(getEmMessages().get(position).getMsgTime()+"");

        if (getItemViewType(position) == TO_ME)
            holder.nickName.setText(getEmMessages().get(position).getFrom());
        else
            holder.nickName.setText("我");
    }

    @Override
    public int getItemCount() {
        return emMessages.size();
    }

    public ArrayList<EMMessage> getEmMessages() {
        if (emMessages == null)
            emMessages = new ArrayList<>();
        return emMessages;
    }

    public void setEmMessages(ArrayList<EMMessage> emMessages) {
        this.emMessages = emMessages;
    }

    public void addEmMessage(EMMessage emMessage) {
        if (emMessages == null)
            emMessages = new ArrayList<>();
        this.emMessages.add(emMessage);
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView nickName;
        public TextView content;
        public TextView time;
        public Holder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.head);
            nickName = (TextView)itemView.findViewById(R.id.message_item_nickname);
            content = (TextView)itemView.findViewById(R.id.message_item_content);
            time = (TextView)itemView.findViewById(R.id.message_item_time);
        }
    }
}
