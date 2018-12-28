package thiagoaires.tagpol.View;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import thiagoaires.tagpol.controle.Controlador;
import thiagoaires.tagpol.controle.Fragments.loadingFragment;
import thiaires.tagpol.R;

public class Visualization_just_webview extends AppCompatActivity {
    private Controlador c;
    private String iddep1;
    private String iddep2;
    private String nomedep1;
    private String nomedep2;
    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualization_just_webview);
        try {
            iddep1 = (String) getIntent().getSerializableExtra("iddep1");
            nomedep1 = (String) getIntent().getSerializableExtra("nomedep1");
            iddep2 = (String) getIntent().getSerializableExtra("iddep2");
            nomedep2 = (String) getIntent().getSerializableExtra("nomedep2");
            c = (Controlador) getIntent().getSerializableExtra("controlador");

            Log.d("CTRL", "DEPUTADO: " + iddep1 + " " + nomedep1 + " " + iddep2 + " " + nomedep2);
        }catch (Exception e){
            e.printStackTrace();
        }

        webView = (WebView) findViewById(R.id.webView);
        carregaWebView();
    }

    public void carregaWebView(){
        final WebSettings wsdonut = webView.getSettings();
        //openLoading();
        wsdonut.setJavaScriptEnabled(true);
        wsdonut.setAllowFileAccess(true);
        wsdonut.setDomStorageEnabled(true);
        wsdonut.setAllowContentAccess(true);
        wsdonut.setAllowFileAccessFromFileURLs(true);
        wsdonut.setAllowUniversalAccessFromFileURLs(true);
        wsdonut.setSupportZoom(false);
        wsdonut.setBuiltInZoomControls(false);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(new WebAppInterface(this), "Android");
        webView.loadUrl("file:///android_asset/deputado_comparacao/index.html");
    }

    public class WebAppInterface {
        private Context context;

        public WebAppInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void finalizar() {
            closeLoading();
        }

        @JavascriptInterface
        public void inicia() {
            openLoading();
        }

        @JavascriptInterface
        public String getDeputadoComparacao(){
            return iddep1 + ";" + nomedep1 + ";" + iddep2 + ";" + nomedep2;
        }
    }

    public void openLoading(){
        loadingFragment f = new loadingFragment();
        f.show(getSupportFragmentManager().beginTransaction(), "loading");
    }

    public void closeLoading(){
        loadingFragment f = (loadingFragment) getSupportFragmentManager().findFragmentByTag("loading");
        if(f != null) {f.dismiss(); getSupportFragmentManager().beginTransaction().remove(f);}
    }
}
