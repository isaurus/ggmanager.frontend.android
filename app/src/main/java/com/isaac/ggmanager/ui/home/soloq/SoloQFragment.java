package com.isaac.ggmanager.ui.home.soloq;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.isaac.ggmanager.core.utils.InsetsUtils;
import com.isaac.ggmanager.databinding.FragmentSoloQBinding;

/**
 * Fragmento que muestra una página web dentro de la aplicación, específicamente la web
 * de OP.GG, que es una plataforma popular para estadísticas de partidas en juegos.
 * Utiliza un WebView con soporte para JavaScript y manejo interno de las cargas de URLs.
 */
public class SoloQFragment extends Fragment {

    private FragmentSoloQBinding binding;

    private final String URL = "https://op.gg/";

    /**
     * Infla la vista del fragmento usando ViewBinding y aplica padding para los insets del sistema.
     *
     * @param inflater           Objeto para inflar la vista
     * @param container          Contenedor padre al que se añadirá esta vista (puede ser null)
     * @param savedInstanceState Estado previo guardado del fragmento (puede ser null)
     * @return Vista raíz inflada para este fragmento
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSoloQBinding.inflate(inflater, container, false);
        InsetsUtils.applySystemWindowInsetsPadding(binding.getRoot());
        return binding.getRoot();
    }

    /**
     * Configura el WebView después de que la vista ha sido creada, habilitando JavaScript y
     * definiendo un WebViewClient para gestionar la navegación interna de URLs.
     *
     * @param view               Vista creada para el fragmento
     * @param savedInstanceState Estado previo guardado del fragmento (puede ser null)
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpWebView();
    }

    /**
     * Configura el WebView con soporte para JavaScript y un WebViewClient que intercepta
     * la carga de URLs para que se realice dentro del mismo WebView.
     */
    private void setUpWebView() {
        WebView webView = binding.wvSoloq;

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                // Para API 21+ (Lollipop y superiores)
                view.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // Compatibilidad con versiones anteriores a API 21
                view.loadUrl(url);
                return true;
            }
        });

        webView.loadUrl(URL);
    }

    /**
     * Se limpia la referencia a binding para evitar fugas de memoria cuando se destruye la vista.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
