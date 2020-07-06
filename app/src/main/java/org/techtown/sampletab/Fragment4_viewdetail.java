package org.techtown.sampletab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class Fragment4_viewdetail extends Fragment {
    private View view;
    TextView contentView;
    TextView titleView;
    TextView nameView;
    TextView dateView;
    ImageView imgurlView;
    DBHelper4 mydb2;

    public Fragment4_viewdetail() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment4_viewdetailitem, null);
        mydb2 = new DBHelper4(getContext());

        final String userTitle = getArguments().getString("userTiTle");
        String userContent = getArguments().getString("userContent");
        final String userName = getArguments().getString("userName");
        final String userDate = getArguments().getString("userDate");
        final String userImgurl = getArguments().getString("userImgurl");

        titleView = (TextView)view.findViewById(R.id.detail_title);
        contentView = (TextView)view.findViewById(R.id.detail_content);
        nameView = (TextView)view.findViewById(R.id.detail_name);
        dateView = (TextView)view.findViewById(R.id.detail_date);
        imgurlView = (ImageView)view.findViewById(R.id.detail_imageView);

        titleView.setText(userTitle);
        nameView.setText(userName);
        dateView.setText(userDate);
        Glide.with(getActivity()).load(userImgurl).into(imgurlView);
        contentView.setText(userContent);

        Button deleteButton = (Button) view.findViewById(R.id.detail_delete);
        deleteButton.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                Fragment4 fragment = new Fragment4();
                mydb2.deleteData(userDate);
                mydb2.updateItems();
                ((MainActivity)getActivity()).replaceFragment(fragment, 0);
            }
        });

        return view;
    }

}
