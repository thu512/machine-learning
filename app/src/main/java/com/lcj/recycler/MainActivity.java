package com.lcj.recycler;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.lcj.recycler.adapter.Adapter;
import com.lcj.recycler.databinding.ActivityMainBinding;
import com.lcj.recycler.model.ImgData;
import com.lcj.recycler.model.NetData;
import com.lcj.recycler.net.Net;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;            //데이터 바인딩
    private String content;                         //검색 키워드
    private String id = "VrlWaXO5ekWFGN2uNv4Z";     //네이버 검색 API ID
    private String secret = "7sHvpho2LE";           //네이버 검색 API secret
    private Adapter adapter;                        //recycler view adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);      //데이터바인딩을 이용한 레이아웃에 data set

        binding.back.setOnClickListener(view -> {
            finish();
        });
    }


    //검색 버튼 클릭
    public void onButtonClick(View v) {
        content = binding.content.getText().toString(); //사용자가 입력한 키워드 받아오기
        Log.d("CONTENT", "값:  "+content);

        //이미지 검색!!
        setData();
        downKeyboard(this,binding.content);
    }

    //리사이클러뷰 셋팅
    void setRecyclerView(List<ImgData> items){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());       //리사이클러뷰 내부 레이아웃 형식
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);                                 //방향 설정
        binding.recyclerview.setLayoutManager(layoutManager);                                       //set
        adapter = new Adapter(getApplicationContext(),items);                                       //어댑터에 데이터 셋팅
        binding.recyclerview.setAdapter(adapter);                                                   //리사이클러뷰에 어댑터 붙이기
    }

    //네이버 이미지 검색 API
    void setData(){
        if (content != null) {

            //Retrofit ㄱㄱ!!
            Call<NetData> res = Net.getInstance().getNetInterface().getImg(id, secret, content, "all",100);
            res.enqueue(new Callback<NetData>() {
                @Override
                public void onResponse(Call<NetData> call, Response<NetData> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            //값 받아오기 성공 -> 데이터 셋팅 -> 리사이클러 뷰 셋팅
                            List<ImgData> items = response.body().getItems();
                            setRecyclerView(items);

                        } else {
                            Log.d("REST","통신실패1");
                        }
                    } else {
                        Log.d("REST","통신실패2");
                        Log.d("REST","통신실패2 "+response.toString());
                    }
                }

                @Override
                public void onFailure(Call<NetData> call, Throwable t) {
                    Log.d("REST","통신실패3"+t.getLocalizedMessage());
                }
            });
        }
    }

    //검색후 키보드 내리기
    public static void downKeyboard(Context context, EditText editText) {

        InputMethodManager mInputMethodManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);

        mInputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);

    }



    public void onButtonClick2(View v) {
        Intent intent = new Intent(getApplicationContext(), SubActivity.class);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("code","requestCode: "+requestCode);
        Log.d("code","resultCode: "+resultCode);

        Toast.makeText(getApplicationContext(),"응답 코드: "+resultCode,Toast.LENGTH_LONG).show();
    }
}
