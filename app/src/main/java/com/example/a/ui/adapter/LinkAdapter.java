package com.example.a.ui.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a.R;
import com.example.a.entity.Link;
import com.example.a.ui.fragment.OnClickListener;

import java.util.ArrayList;
import java.util.List;

import android.support.annotation.NonNull;

public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.LinkViewHolder> {

    private List<Link> linksList = new ArrayList<>();
    OnClickListener.OnItemClickCallback onItemClickCallback;

    public LinkAdapter(OnClickListener.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public LinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_link, parent, false);
        return new LinkViewHolder(view);
    }

    public void setData(List<Link> linksList){
        this.linksList = linksList;
        notifyDataSetChanged();
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

    // Предоставляет прямую ссылку на каждый View-компонент
    // Используется для кэширования View-компонентов и последующего быстрого доступа к ним
    class LinkViewHolder extends RecyclerView.ViewHolder {

        Context context;
        TextView imageTextView;
        ConstraintLayout imageLinkContainer;

        // Конструктор, который принимает на вход View-компонент строки
        // и ищет все дочерние компоненты
        LinkViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            imageLinkContainer = itemView.findViewById(R.id.image_link_container);
            imageTextView = itemView.findViewById(R.id.image_link);
        }

        void bind(Link link, int position){
            imageLinkContainer.setOnClickListener(new OnClickListener(position, onItemClickCallback));
            imageTextView.setText(link.getImageLink());
            setLinkColor(link.getStatus());
        }

        private void setLinkColor(int status) {
            switch (status) {
                case 1:
                    imageLinkContainer.setBackgroundColor(context.getResources().getColor(R.color.green));
                    break;
                case 2:
                    imageLinkContainer.setBackgroundColor(context.getResources().getColor(R.color.red));
                    break;
                case 3:
                    imageLinkContainer.setBackgroundColor(context.getResources().getColor(R.color.gray));
                    break;
            }
        }
    }

}

/*public class LinkAdapter extends ArrayAdapter<Link> {
    private Context mContext;
    private int mResource;

    public LinkAdapter(Context context, int resource, ArrayList<Link> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        String imageLink = getItem(position).getImageLink();
        int status = getItem(position).getStatus();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvLink = convertView.findViewById(R.id.textView);

        tvLink.setText(imageLink);

        switch (status) {
            case 1:
                convertView.setBackgroundColor(convertView.getResources().getColor(R.color.green));
//                convertView.setBackgroundColor(Color.GREEN);
                break;
            case 2:
                convertView.setBackgroundColor(convertView.getResources().getColor(R.color.red));
//                convertView.setBackgroundColor(Color.RED);
                break;
            case 3:
                convertView.setBackgroundColor(convertView.getResources().getColor(R.color.gray));
//                convertView.setBackgroundColor(Color.GRAY);
                break;
        }

        return convertView;
    }
}*/