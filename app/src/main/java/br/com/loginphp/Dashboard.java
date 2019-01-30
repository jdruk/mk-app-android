package br.com.loginphp;

import android.app.ActionBar;
import android.app.Dialog;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Dashboard extends AppCompatActivity {

    private WebView webView;
    private Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initComponents();
    }

    public void showSpeed(View view){
        myDialog.show();
        settingSizeDialog();
    }

    public void settingSizeDialog(){
        Display display = getWindowManager(). getDefaultDisplay();
        Point size = new Point();
        display. getSize(size);
        Window window = myDialog.getWindow();
        int height = (int) (size.y * 0.8);
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, height);
    }

    public void settingWebView(){
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setFocusable(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://facebook.com.br");
    }

    public void initComponents(){
        myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.speed_teste);

        webView = myDialog.findViewById(R.id.site);
        settingWebView();
    }
}
