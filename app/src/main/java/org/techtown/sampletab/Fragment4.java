package org.techtown.sampletab;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class Fragment4 extends Fragment {
    public static DiaryViewAdapter adapter;
    public static ArrayList<DiaryViewItem> items = new ArrayList<>();
    private View view;
    DBHelper4 mydb1;
    String strNickname;


    public Fragment4() {
    }

    public void Fragment4(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment4, null) ;
        mydb1 = new DBHelper4(getContext());
        mydb1.updateItems();

        //어댑터생성
        adapter = new DiaryViewAdapter(getActivity(), items);

        //어댑터연결
        ListView listview = (ListView) view.findViewById(R.id.listview) ;
        listview.setAdapter(adapter) ;

        Button addButton = (Button) view.findViewById(R.id.btndisplay);
        addButton.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                onCreateDiary(v);
                Fragment4.adapter.notifyDataSetChanged();
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * ListView의 Item을 Click 할 때 수행할 동작
             * @param adapterView 클릭이 발생한 AdapterView.
             * @param view 클릭 한 AdapterView 내의 View(Adapter에 의해 제공되는 View).
             * @param position 클릭 한 Item의 position
             * @param id 클릭 된 Item의 Id
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Fragment4_viewdetail fragment = new Fragment4_viewdetail(); // Fragment 생성
                Bundle bundle = new Bundle(4); // 파라미터는 전달할 데이터 개수
                bundle.putString("userTiTle", adapter.getItem(position).getTitle()); // key , value
                bundle.putString("userContent", adapter.getItem(position).getContent());
                bundle.putString("userName", adapter.getItem(position).getName());
                bundle.putString("userDate",adapter.getItem(position).getDate());
                bundle.putString("userImgurl", adapter.getItem(position).getImgurl());
                fragment.setArguments(bundle);
                ((MainActivity)getActivity()).replaceFragment(fragment, 1);
            }
        });
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        //Log.d(TAG,"onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    public boolean onCreateDiary(View v) {
        //팝업창을 띄워서 데이터를 입력받도록 하자.
        Intent intent;
        intent = new Intent(getActivity(), PopupActivity_addDiary.class);
        startActivityForResult(intent, 1);
        return true;
    }

    //팝업창에서 받아 온 데이터를 처리함
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1){
            //데이터 처리
            if(resultCode == RESULT_OK) {
                String newTitle = data.getStringExtra("title");
                String newContent = data.getStringExtra("content");
                String newImgurl = data.getStringExtra("imgurl");

                //입력한 데이터를 DB에 저장하자.
                Date from = new Date();
                SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String to = transFormat.format(from);

                GlobalApplication user = (GlobalApplication)getActivity().getApplicationContext();
                strNickname = user.getUserName();

                boolean isInserted = mydb1.insertData(strNickname, newTitle, newContent, to, newImgurl);
                if (isInserted == true) {
                    Toast.makeText(getContext(), "등록성공",
                            Toast.LENGTH_LONG).show();
                    mydb1.updateItems();
                    Fragment4.adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "등록실패",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
