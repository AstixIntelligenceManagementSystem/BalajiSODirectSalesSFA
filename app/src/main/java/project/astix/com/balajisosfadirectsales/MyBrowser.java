package project.astix.com.balajisosfadirectsales;

import android.app.ProgressDialog;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyBrowser extends WebViewClient 
{
	 private ProgressDialog progressBar;
	 
	 public MyBrowser(ProgressDialog progressBar)
	 {
		 this.progressBar=progressBar;
		
		 progressBar.show();
	 }

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url)
		{
			// TODO Auto-generated method stub
			  view.loadUrl(url);
		      return true;
		}
		
		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			
			
			super.onPageFinished(view, url);
			progressBar.dismiss();
			
			
		}
}
