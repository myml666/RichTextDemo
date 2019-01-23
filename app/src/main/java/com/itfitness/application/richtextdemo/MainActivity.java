package com.itfitness.application.richtextdemo;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {
    private WebView mWebView;
    private String mRichText="<p style=\"text-align: left;\"><img src=\"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1383902695,1129447956&fm=26&gp=0.jpg\"\n" +
            "\t title=\"1541054054758015328.jpg\" alt=\"1.jpg\" /><img src=\"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2353449440,2528668120&fm=26&gp=0.jpg\"\n" +
            "\t title=\"1541054057414099008.jpg\" alt=\"2.jpg\" /><img src=\"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3446458559,1680525880&fm=26&gp=0.jpg\"\n" +
            "\t title=\"1541054060899024343.jpg\" alt=\"3.jpg\" /></p>";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWebView();
        initRichText();
    }

    /**
     * 加载富文本
     */
    private void initRichText() {
        mWebView.loadDataWithBaseURL(null,getHtmlData(mRichText), "text/html" , "utf-8", null);

    }
    private String getHtmlData(String bodyHTML) {
        String head = "<head>"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> "
                + "<style>img{max-width: 100%; width:100%; height:auto;}*{margin:0px;}</style>"
                + "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }
    private void initWebView() {
        mWebView=findViewById(R.id.webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);//允许使用js
//不支持屏幕缩放
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
//不显示webview缩放按钮
        webSettings.setDisplayZoomControls(false);
        mWebView.setWebChromeClient(new WebChromeClient(){
            //不支持js的alert弹窗，需要自己监听然后通过dialog弹窗
            @Override
            public boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
                AlertDialog.Builder localBuilder = new AlertDialog.Builder(webView.getContext());
                localBuilder.setMessage(message).setPositiveButton("确定",null);
                localBuilder.setCancelable(false);
                localBuilder.create().show();

                //注意:
                //必须要这一句代码:result.confirm()表示:
                //处理结果为确定状态同时唤醒WebCore线程
                //否则不能继续点击按钮
                result.confirm();
                return true;
            }
        });
    }
}
