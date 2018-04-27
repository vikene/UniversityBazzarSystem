package com.team5.ubs.viewHolder;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.team5.ubs.R;
import com.team5.ubs.commentActivity;
import com.team5.ubs.models.clubfeedmodel;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by tp on 28/02/18.
 */

public class clubFeedAdapter extends RecyclerView.Adapter<clubFeedAdapter.MyViewHolder> {
    public List<clubfeedmodel> feedData;

    public clubFeedAdapter(List<clubfeedmodel> modelData){

        this.feedData = modelData;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.frontcard,parent, false);
        return new MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final clubfeedmodel clubfeed = feedData.get(position);
        holder.content.setText(clubfeed.getContent());
        holder.clubname.setText(clubfeed.getClubname());
        holder.title.setText(clubfeed.getTitle());
        holder.commentbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(),commentActivity.class);
                i.putExtra("clubName", clubfeed.getClubname());
                i.putExtra("clubTitle", clubfeed.getTitle());
                view.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return feedData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView clubname,title, content;
        public Button commentbutton;
        public MyViewHolder(View view){
            super(view);
            clubname = (TextView) view.findViewById(R.id.Clubname);
            title = (TextView) view.findViewById(R.id.posttitle);
            content = (TextView) view.findViewById(R.id.postcontent);
            commentbutton = view.findViewById(R.id.commentbutton);

        }
    }
}
