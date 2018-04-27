package com.team5.ubs.viewHolder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.team5.ubs.R;
import com.team5.ubs.chatSeller;
import com.team5.ubs.market_info;
import com.team5.ubs.models.clubfeedmodel;
import com.team5.ubs.models.marketplacemodel;

import java.util.List;

/**
 * Created by tp on 09/04/18.
 */

public class marketFeedAdapter extends RecyclerView.Adapter<marketFeedAdapter.MyViewHolder> {
    public List<marketplacemodel> feedData;

    public marketFeedAdapter(List<marketplacemodel> modelData){

        this.feedData = modelData;

    }

    @Override
    public marketFeedAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.marketfeedcard,parent, false);
        return new marketFeedAdapter.MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final marketplacemodel marketfeed = feedData.get(position);
        holder.title.setText("Name: "+marketfeed.name);
        holder.content.setText("Price "+marketfeed.price);
        if(!marketfeed.image.isEmpty()) {
            Picasso.get().load(marketfeed.image).into(holder.selectImage);
        }
        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), market_info.class);
                i.putExtra("marketTitle", marketfeed.name);
                v.getContext().startActivity(i);
            }
        });
        holder.messageSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),chatSeller.class);
                i.putExtra("sellerName", marketfeed.seller);
                i.putExtra("like", "Nope");
                v.getContext().startActivity(i);
            }
        });
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),chatSeller.class);
                i.putExtra("sellerName", marketfeed.seller);
                i.putExtra("like", "yes");
                v.getContext().startActivity(i);
            }
        });

    }


    @Override
    public int getItemCount() {
        return feedData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title, content;
        public Button info,messageSeller,like;
        public ImageView selectImage;
        public MyViewHolder(View view){
            super(view);
            title = (TextView) view.findViewById(R.id.posttitle);
            content = (TextView) view.findViewById(R.id.postcontent);
            info = (Button) view.findViewById(R.id.info);
            messageSeller = (Button) view.findViewById(R.id.commentbutton);
            like = view.findViewById(R.id.heart);
            selectImage = view.findViewById(R.id.marketfeedImage);

        }
    }
}
