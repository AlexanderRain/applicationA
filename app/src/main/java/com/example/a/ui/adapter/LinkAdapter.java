package com.example.a.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a.R;
import com.example.a.entity.Link;
import com.example.a.ui.adapter.OnClickListener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import android.support.annotation.NonNull;

import static com.example.a.utils.Constants.ERROR;
import static com.example.a.utils.Constants.INSERTED;
import static com.example.a.utils.Constants.UNDEFINED;

public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.LinkViewHolder> {

    private List<Link> linksList = new ArrayList<>();
    OnItemClickListener.OnItemClickCallback onItemClickCallback;

    public LinkAdapter(OnItemClickListener.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setData(List<Link> linksList){
            this.linksList = linksList;
            notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_link, parent, false);
        return new LinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LinkViewHolder holder, int position) {
        Link link = linksList.get(position);
        holder.bind(link, position);
    }

    @Override
    public int getItemCount() {
        return linksList.size();
    }

    class LinkViewHolder extends RecyclerView.ViewHolder {

        Context context;
        TextView imageTextView;
        ConstraintLayout imageLinkContainer;

        LinkViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            imageLinkContainer = itemView.findViewById(R.id.image_link_container);
            imageTextView = itemView.findViewById(R.id.image_link);
            imageTextView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        }

        void bind(Link link, int position){
            imageLinkContainer.setOnClickListener(new OnItemClickListener(position, onItemClickCallback));
            imageTextView.setText(link.getImageLink());
            setLinkColor(link.getStatus());
        }

        private void setLinkColor(int status) {
            switch (status) {
                case INSERTED:
                    imageLinkContainer.setBackgroundColor(context.getResources().getColor(R.color.green));
                    break;
                case ERROR:
                    imageLinkContainer.setBackgroundColor(context.getResources().getColor(R.color.red));
                    break;
                case UNDEFINED:
                    imageLinkContainer.setBackgroundColor(context.getResources().getColor(R.color.gray));
                    break;
            }
        }
    }

}
