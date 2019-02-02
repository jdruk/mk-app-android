package br.com.loginphp;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

public class Dashboard extends AppCompatActivity {

    private WebView webView;
    private Dialog myDialog;

    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initComponents();
        Intent intent = getIntent();
        name = intent.getExtras().getString("name");
    }

    public void showSpeedTest(View view){
        myDialog.show();
        settingSizeDialog(true);
        settingWebView("http://192.168.1.23/speed-teste/");
    }

    public void showSac(View view){
        openWhatsApp("55 89 9997-5638");
    }

    public void getInvoice(View view){

        Intent i = new Intent(view.getContext(), InvoiceActivity.class);
        startActivity(i);

//        RequestQueue queue = Volley.newRequestQueue(view.getContext());
//        String url = "http://192.168.1.23/speed-teste/login/boleto.php";
//        StringRequest postRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    Log.d("resposta", response);
//
//                    JSONObject jsonObject = new JSONObject(response);
//                    int error = jsonObject.getInt("error");
//                    if (error == 0){
//                        myDialog.show();
//                        settingSizeDialog(false);
//                        settingWebView("http://central.renet-ce.com.br/boleto/18boleto.php?titulo=" + jsonObject.getString("titulo"));
//                    }
//                } catch (JSONException e) {
//                    Toast.makeText(Dashboard.this, e.getMessage(), Toast.LENGTH_LONG);
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(Dashboard.this, error.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        }){
//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("user_name",name);
//                return params;
//            }
//        };
//        queue.add(postRequest);
    }

    public void unlockNetwork(View view){

        Toast.makeText(this, "desbloquear", Toast.LENGTH_SHORT).show();
    }

    public void settingSizeDialog(boolean fullScreen){
        Display display = getWindowManager(). getDefaultDisplay();
        Point size = new Point();
        display. getSize(size);
        Window window = myDialog.getWindow();
        int height = ActionBar.LayoutParams.MATCH_PARENT;
        if(fullScreen){
             height = (int) (size.y * 0.8);
        }

        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, height);
    }

    public void settingWebView(String url){
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setFocusable(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }

    public void initComponents(){
        myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.speed_teste);

        webView = myDialog.findViewById(R.id.site);
  
    }

    private void openWhatsApp(String number) {
        PackageManager pm=getPackageManager();
        try {

            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            String text = "Olá estou precisando.";

            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            sendIntent.setPackage("com.whatsapp");
            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(number)+"@s.whatsapp.net");
            sendIntent.putExtra(Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(sendIntent, "Suporte"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp não estar instalado", Toast.LENGTH_SHORT)
                    .show();
            Uri uri = Uri.parse("market://details?id=com.whatsapp");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(goToMarket);
        }
    }

    public void download(){
        new DownloadFile().execute("http://maven.apache.org/maven-1.x/maven.pdf", "maven.pdf");
    }

    public void showFile(){
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/testthreepdf/" + "maven.pdf");
        Uri path = Uri.fromFile(pdfFile);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try{
            startActivity(pdfIntent);
        }catch(ActivityNotFoundException e){
            Toast.makeText(Dashboard.this, "Nenhum leitor de PDF no celular", Toast.LENGTH_SHORT).show();
        }
    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            if(Build.VERSION.SDK_INT>=24){
                try{
                    Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                    m.invoke(null);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            String fileUrl = strings[0];
            String fileName = strings[1];
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "testthreepdf");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try{
                pdfFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            showFile();
        }
    }

}
