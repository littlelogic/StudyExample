package leavesc.hello.messenger_server;
//leavesc.hello.messenger_server.PermissionActivity
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TextView;

import net.grandcentrix.tray.AppPreferences;

/**
 * 作者：leavesC
 * 时间：2019/4/4 10:46
 * 描述：
 */
public class PermissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView hTextView = new TextView(this);
        hTextView.setGravity(Gravity.CENTER);
        hTextView.setBackgroundColor(Color.WHITE);
        AppPreferences mAppPreferences = new AppPreferences(this);
        String sp = mAppPreferences.getString("AppPreferences_messenger_client_key","defaul");

        String content_str = "我是自定义权限，leavesc.hello.messenger_server.permission.PermissionActivity"
                + "\n-来自块app的AppPreferences无效->"+sp;
        hTextView.setText(content_str);
        setContentView(hTextView);
    }
}
