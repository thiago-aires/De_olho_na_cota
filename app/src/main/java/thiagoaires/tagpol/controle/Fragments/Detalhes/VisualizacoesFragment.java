package thiagoaires.tagpol.controle.Fragments.Detalhes;


import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.airbnb.lottie.LottieAnimationView;

import thiagoaires.tagpol.Modelo.Deputado;
import thiagoaires.tagpol.controle.Controlador;
import thiagoaires.tagpol.controle.Fragments.homescreen.DeputadoFragment;
import thiagoaires.tagpol.controle.Fragments.loadingFragment;
import thiaires.tagpol.R;

public class VisualizacoesFragment extends Fragment {
    private Deputado dep1;
    private Deputado dep2;
    private WebView grafico;
    private CardView mensal;
    private CardView anual;
    private CardView pin;
    private int width, height;
    private Controlador c;
    private boolean flag_pin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_visualisation, container, false);
        this.flag_pin = false; //por padrao nao é comparison
        Bundle b = getArguments();
        if (b != null) {
            try{
                c = (Controlador) b.getSerializable("controlador");
                Log.d("CTRL", "visu fragment: controlador" + c.getDeputados().getDados().size());
                dep1 = (Deputado) b.getSerializable("dep");
                Log.d("CTRL", "onCreateView: " + dep1.toString());
            }catch (Exception e){
                e.printStackTrace();
                c = new Controlador();
                c.carregaDeputados();
            }
        }

        grafico = (WebView) v.findViewById(R.id.grafico);

        mensal = (CardView) v.findViewById(R.id.mensal);

        mensal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carregaWebView("file:///android_asset/donut_deputado/index.html");
            }
        });

        anual = (CardView) v.findViewById(R.id.anual);

        anual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carregaWebView("file:///android_asset/bar/index.html");
            }
        });

        pin = (CardView) v.findViewById(R.id.pin);

        pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // como implementar para comparar?
                Bundle b = new Bundle();
                b.putSerializable("controlador", c);
                b.putSerializable("flag_pin", true);
                b.putSerializable("dep1", dep1);
                Fragment fragment = new DeputadoFragment();
                fragment.setArguments(b);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentLayout, fragment, "current")
                        .commit();
            }
        });

        carregaWebView("file:///android_asset/donut_deputado/index.html");
        return v;
    }

    public void openLoading(){
        loadingFragment f = new loadingFragment();
        f.show(getActivity().getSupportFragmentManager().beginTransaction(), "loading");
    }

    public void closeLoading(){
        loadingFragment f = (loadingFragment) getActivity().getSupportFragmentManager().findFragmentByTag("loading");
        if(f != null) {f.dismiss(); getActivity().getSupportFragmentManager().beginTransaction().remove(f);}
    }

    public class WebAppInterface {
        private Context context;

        public WebAppInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public String getDadosDeputado() {
            return dep1.getDados().getUltimoStatus().getNomeEleitoral() + ";" + dep1.getDados().getId();
        }

        @JavascriptInterface
        public void finalizar(){
            closeLoading();
        }

        @JavascriptInterface
        public void inicia(){
            openLoading();
        }


        @JavascriptInterface
        public int getWidth(){
            return width;
        }

        @JavascriptInterface
        public int getHeight(){
            return height;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void carregaWebView(String path_index){
        final WebSettings wsdonut = grafico.getSettings();
        openLoading();
        wsdonut.setJavaScriptEnabled(true);
        wsdonut.setAllowFileAccess(true);
        wsdonut.setDomStorageEnabled(true);
        wsdonut.setAllowContentAccess(true);
        wsdonut.setAllowFileAccessFromFileURLs(true);
        wsdonut.setAllowUniversalAccessFromFileURLs(true);
        wsdonut.setSupportZoom(false);
        wsdonut.setBuiltInZoomControls(false);
        grafico.setWebViewClient(new WebViewClient());
        grafico.setWebChromeClient(new WebChromeClient());
        grafico.addJavascriptInterface(new WebAppInterface(getContext()), "Android");
        grafico.loadUrl(path_index);
    }
}
