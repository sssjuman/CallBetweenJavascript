package sam.com.androidjavascript;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {
    WebView webView;
    EditText et_word;
    Button btn_call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webView);
        et_word = (EditText) findViewById(R.id.et_word);
        btn_call = (Button) findViewById(R.id.btn_call);

        btn_call.setOnClickListener(this);

        webView.getSettings().setJavaScriptEnabled(true);

        //顯示Javascript的alert
        webView.setWebChromeClient(new WebChromeClient());

        //新增javascriptInterface
        //第一個引數：需要一個與Javascript對映的java物件
        //第二個引數：該java物件在Javascript裡面的物件名
        webView.addJavascriptInterface(new JavaObject(), "android_app");

        //載入本地html檔案
        webView.loadUrl("file:///android_asset/testJS.html");

    }


    private class JavaObject {
        //target是4.2以上都需要新增@JavascriptInterface註解，否則無法呼叫
        @JavascriptInterface
        //由JavaScript取得的值帶入此方法中呼叫
        public void callAndroid(String inputbox) {
            Toast.makeText(MainActivity.this, inputbox, Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onClick(View view) {
        if (et_word.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please input something!", Toast.LENGTH_LONG).show();

            return;
        }
        //取得的值帶入JavaScript的方法中呼叫
        webView.loadUrl("javascript:mobileCallJs('" + et_word.getText().toString() + "')");
    }
}
