
package main.java.org.teleal.cling.android.browser;

import java.util.logging.Level;
import java.util.logging.Logger;

import main.java.org.teleal.android.util.FixedAndroidHandler;
import main.java.org.teleal.cling.android.photometer.R;

import org.teleal.common.logging.LoggingUtil;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends TabActivity {

	private static Logger log = Logger.getLogger(MainActivity.class.getName());

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Fix the logging integration between java.util.logging and Android internal logging
		LoggingUtil.resetRootHandler(new FixedAndroidHandler());

		Logger.getLogger("org.teleal.cling").setLevel(Level.INFO);

		setContentView(R.layout.main);

		TabHost tabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;

		intent = new Intent().setClass(this, DemoActivity.class);
		spec = tabHost.newTabSpec("demo")
				.setIndicator("Demo Photometer", getResources().getDrawable(R.drawable.ic_tab_demo))
				.setContent(intent);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);
	}

}
