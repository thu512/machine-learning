package com.lcj.recycler.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lcj.recycler.R;
import com.lcj.recycler.model.ImgData;
import com.squareup.picasso.Picasso;

import java.util.List;


import static android.support.v4.content.ContextCompat.getSystemService;
import static android.support.v4.content.ContextCompat.startActivity;
import static android.support.v4.content.ContextCompat.startForegroundService;

//리사이클러뷰 어댑터
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{
    Context context;
    List<ImgData> items;

    //어댑터 생성자 -> getApplicationContext, 리스트로 보여줄 아이템
    public Adapter(Context context,List<ImgData> items) {
        this.context = context;
        this.items = items;
    }


    //뷰 아이템 레이아웃 객체로 올림 -> 뷰홀더를 거쳐 뷰홀더 객체 리턴
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_item,viewGroup,false);
        return new ViewHolder(view);
    }

    //뷰홀더에 데이터 셋
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ImgData imgData = items.get(i);
        Picasso.get().load(imgData.getThumbnail()).into(viewHolder.imageView);
        viewHolder.link.setText(imgData.getLink());
        viewHolder.title.setText(imgData.getTitle());

        //클릭 이벤트
        viewHolder.link.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(imgData.getLink()));
            startActivity(context,intent,null);
        });
    }

    //리스트 개수
    @Override
    public int getItemCount() {
        return items.size();
    }


    //뷰홀더 클래스 -> 요소들에 대한 객체 생성
    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView title;
        TextView link;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.title);
            link = itemView.findViewById(R.id.link);

        }
    }

}
