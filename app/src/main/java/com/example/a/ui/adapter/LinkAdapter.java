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

import java.util.ArrayList;
import java.util.List;

import android.support.annotation.NonNull;

// Если вдруг надо, читал тут
// devcolibri Урок 11. Работа с RecyclerView на примере TweetsRecyclerView
public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.LinkViewHolder> {

    // TO DO: onClickListener
    private List<Link> linksList = new ArrayList<>();

//Этим методом инициализирую ^ вон в тот лист, листом из ДБ
    public void setData(List<Link> linksList){
            this.linksList = linksList;
            notifyDataSetChanged();
        }

    // Требует переопределения, создает собственно вьюху
    // для ссылки, то есть одну строку
    @NonNull
    @Override
    public LinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_link, parent, false);
        return new LinkViewHolder(view);
    }

    // Требует переопределения, связь вьюхи с джава классами
    // так сказать дает логику картике
    @Override
    public void onBindViewHolder(@NonNull LinkViewHolder holder, int position) {
        Link link = linksList.get(position);
        holder.bind(link, position);
    }

    // Требует переопределения (не понял где он вызывается)
    @Override
    public int getItemCount() {
        return linksList.size();
    }

    // Предоставляет прямую ссылку на каждый View-компонент
    // Используется для кэширования View-компонентов и последующего быстрого доступа к ним
    class LinkViewHolder extends RecyclerView.ViewHolder {
        // Ваш ViewHolder должен содержать переменные для всех
        // View-компонентов, которым вы хотите задавать какие-либо свойства
        // в процессе работы пользователя со списком

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

        // Заполняет контейнер ссылкой и цветом
        void bind(Link link, int position){
            imageTextView.setText(link.getImageLink());
            setLinkColor(link.getStatus());
        }

        // Метод со свечкой, тут все очевидно
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
