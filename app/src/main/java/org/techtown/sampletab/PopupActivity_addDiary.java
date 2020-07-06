package org.techtown.sampletab;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import java.io.File;

public class PopupActivity_addDiary extends AppCompatActivity {
    String imgurl;
    private static final int PICK_FROM_ALBUM = 1;
    private File tempFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //팝업액티비티의 제목을 제거한다.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.popup_adddiary);

        final EditText et_name = (EditText) findViewById(R.id.et_title);
        final EditText et_content = (EditText) findViewById(R.id.et_content);

        Button button_gallery = (Button) findViewById(R.id.bt_gallery);
        button_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_FROM_ALBUM);
            }
        });

        //확인버튼 이벤트
        Button button_ok = (Button) findViewById(R.id.bt_ok);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //데이터 전달하고 액티비티 닫기
                String title = et_name.getText().toString();
                String content = et_content.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                intent.putExtra("imgurl", imgurl);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }



    //바깥영역 클릭 방지와 백 버튼 차단
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()== MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };

        CursorLoader cursorLoader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            //경로 구하기

            imgurl=getRealPathFromURI(uri);
        }
        super.onActivityResult(requestCode, resultCode, data);
    };





//    @Override
//    public void onBackPressed() {
//        return;
//    }
}