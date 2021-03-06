package org.foree.duker.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import org.foree.duker.base.MyApplication;
import org.foree.duker.ui.activity.SettingsActivityTmp;
import org.foree.duker.utils.LogUtils;
import org.foree.duker.R;

/**
 * Created by foree on 4/15/15.
 * about settings
 */
public class AboutFragment extends PreferenceFragment {
    private static final String TAG = "AboutFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preference_about);
        //set VersionName
        findPreference(SettingsActivityTmp.KEY_VERSION_NAME).setTitle(MyApplication.myVersionName);

        //send email to developer
        findPreference(SettingsActivityTmp.KEY_CONTACT_ME).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (LogUtils.isCompilerLog) LogUtils.v(TAG, "send");
                Uri emailUri = Uri.parse("mailto:" + SettingsActivityTmp.MY_EMAIL);
                Intent sendEmail = new Intent(Intent.ACTION_SENDTO, emailUri);
                sendEmail.putExtra(Intent.EXTRA_SUBJECT, "@反馈:");
                //  sendEmail.putExtra(Intent.EXTRA_TEXT, "这是内容");
                startActivity(sendEmail);
                return true;
            }
        });

        //set application Data dir info
        findPreference(SettingsActivityTmp.KEY_APPLICATION_DIR).setSummary(MyApplication.mySdcardDataDir);

        //set application Cache dir info
        findPreference(SettingsActivityTmp.KEY_APPLICATION_CACHE_DIR).setSummary(MyApplication.mySdcardCacheDir);

    }
}
