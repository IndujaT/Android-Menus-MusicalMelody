package com.example.induja.musicalmelodies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * Created by Induja on 2/25/2018.
 */

public class WebViewActivity extends AppCompatActivity {
    private WebView myWebView;
    private MyWebChromeClient myWebChromeClient = null;

    private View mCustomView;
    private RelativeLayout mContentView;
    private FrameLayout mCustomViewContainer;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        String url, videoUrl, wikiUrl;
        Bundle extras = getIntent().getExtras();
        videoUrl = extras.getString("video_url");
        wikiUrl = extras.getString("wiki_url");
        if(videoUrl == null)
            url = wikiUrl;
        else url = videoUrl;

       /* myWebView = (WebView) findViewById(R.id.webView);
        myWebView.setWebViewClient(new MyBrowser());
        myWebView.getSettings().setLoadsImagesAutomatically(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        myWebView.loadUrl(videoUrl);*/

        myWebView = (WebView) findViewById(R.id.webView);
        myWebChromeClient = new MyWebChromeClient();
        myWebView.setWebChromeClient(myWebChromeClient);
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {   /*Give the host application a chance to take over the control when a new url is about to be loaded in the current WebView.*/
                return false;
            }
        });
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.loadUrl(url);
    }

    /*private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }*/

    public class MyWebChromeClient extends WebChromeClient {

        FrameLayout.LayoutParams LayoutParameters = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) { /*Notify the host application that the current page has entered full screen mode.*/
            // if a view already exists then immediately terminate the new one
            if (mCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }
            mContentView = (RelativeLayout) findViewById(R.id.activity_main);
            mContentView.setVisibility(View.GONE);

            mCustomViewContainer = new FrameLayout(WebViewActivity.this);
            mCustomViewContainer.setLayoutParams(LayoutParameters);
            mCustomViewContainer.setBackgroundResource(android.R.color.black);
            view.setLayoutParams(LayoutParameters);
            mCustomViewContainer.addView(view);
            mCustomView = view;

            mCustomViewCallback = callback;
            mCustomViewContainer.setVisibility(View.VISIBLE);
            setContentView(mCustomViewContainer);
        }

        @Override
        public void onHideCustomView() {  /*Notify the host application that the current page has exited full screen mode.*/
            if (mCustomView == null) {
                return;
            } else {
                // Hide the custom view.
                mCustomView.setVisibility(View.GONE);
                // Remove the custom view from its container.
                mCustomViewContainer.removeView(mCustomView);
                mCustomView = null;
                mCustomViewContainer.setVisibility(View.GONE);
                mCustomViewCallback.onCustomViewHidden();
                // Show the content view.
                mContentView.setVisibility(View.VISIBLE);
                setContentView(mContentView);
            }
        }


    }
    @Override
    public void onBackPressed() {
        if (mCustomViewContainer != null)
            myWebChromeClient.onHideCustomView();
        else if (myWebView.canGoBack())
            myWebView.goBack();
        else
            super.onBackPressed();
    }
	
	/*public class WebviewActivity extends AppCompatActivity {
		Intent i;
		Bundle b;
		String url;
		WebView mWebview;
		String wikiurl;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_webview);

	             //w =(WebView)findViewById(R.id.web);
				 //i = getIntent();
			     //b = i.getExtras();
			     //data = b.getString("URL");
				//w.getSettings().setMediaPlaybackRequiresUserGesture(true);
				//w.loadUrl("http://"+data);


			i = getIntent();
			b = i.getExtras();
			wikiurl = "http://" + b.getString("wiki");
			url = "http://" + b.getString("URL");
			mWebview = new WebView(this);


			mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript
			final Activity activity = this;

			mWebview.setWebViewClient(new WebViewClient() {
				@SuppressWarnings("deprecation")
				@Override
				public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
					Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
				}

				@TargetApi(android.os.Build.VERSION_CODES.M)
				@Override
				public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
					onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
				}
        });
        if(!wikiurl.equals( "http://null"))
        {
            mWebview.loadUrl(wikiurl);
        }
		else
        {
            if(!url.equals( "http://null"))
            mWebview.loadUrl(url);
        }
        setContentView(mWebview);

    }

    @Override
    public void onBackPressed() {
        finish();
    }

}*/
}
