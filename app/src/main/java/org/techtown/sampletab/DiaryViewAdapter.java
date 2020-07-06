package org.techtown.sampletab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DiaryViewAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<DiaryViewItem> listViewItemList = null;

    public DiaryViewAdapter(Context mcontext, ArrayList<DiaryViewItem> list) {
        super();
        this.mContext = mcontext;
        listViewItemList = list;
    }

    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    @Override
    public DiaryViewItem getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "fragment4_view" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment4_viewitem, parent, false);
        }

        /* 'fragment4_view'에 정의된 위젯에 대한 참조 획득 */
        TextView titleTextView = (TextView) convertView.findViewById(R.id.title);
        TextView nameTextView = (TextView) convertView.findViewById(R.id.name);
        TextView dateTextView = (TextView) convertView.findViewById(R.id.date);


        //TextView dateTextView = (TextView) convertView.findViewById(R.id.date);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        DiaryViewItem listViewItem = listViewItemList.get(position);

        /* 각 위젯에 세팅된 아이템을 뿌려준다 */
        titleTextView.setText(listViewItem.getTitle());
        nameTextView.setText(listViewItem.getName());
        dateTextView.setText(listViewItem.getDate());


        /* (위젯에 대한 이벤트리스너를 지정하고 싶다면 여기에 작성하면된다..)*/
        /*
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "선택되었다", Toast.LENGTH_SHORT).show();
            }
        });*/
        return convertView;
    }

    public void addItem(DiaryViewItem mItem) {
        /* mItems에 MyItem을 추가한다. */
        listViewItemList.add(mItem);
    }
}