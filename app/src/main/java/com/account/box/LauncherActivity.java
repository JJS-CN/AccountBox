package com.account.box;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArraySet;

import com.account.box.activity.LoginActivity;
import com.account.box.activity.MainActivity;
import com.blankj.utilcode.util.SPUtils;
import com.jjs.base.JJsActivity;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;

/**
 * 说明：初次启动。启动页
 * Created by aa on 2017/8/8.
 */

public class LauncherActivity extends JJsActivity {

    @Override
    protected void onActivityResult(int i, Intent intent) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SPUtils.getInstance("app").getBoolean(Store.Login.isLogin, false)) {
            MainActivity.open(this);
        } else {
            LoginActivity.open(this);
        }
        JPushInterface.setAlias(this, 1, "1");
        Set<String> tags = new ArraySet<String>();
        tags.add("aaa");
        tags.add("bbb");
        tags.add("ccc");
        JPushInterface.setTags(this, 2, tags);
        finish();
        //
        //        String b = "MIIT2AYJKoZIhvcNAQcCoIITyTCCE8UCAQExCzAJBgUrDgMCGgUAMIIDeQYJKoZIhvcNAQcBoIIDagSCA2YxggNiMAoCAQgCAQEEAhYAMAoCARQCAQEEAgwAMAsCAQECAQEEAwIBADALAgEDAgEBBAMMATMwCwIBCwIBAQQDAgEAMAsCAQ8CAQEEAwIBADALAgEQAgEBBAMCAQAwCwIBGQIBAQQDAgEDMAwCAQoCAQEEBBYCNCswDAIBDgIBAQQEAgIAiTANAgENAgEBBAUCAwGHzzANAgETAgEBBAUMAzEuMDAOAgEJAgEBBAYCBFAyNDcwGAIBBAIBAgQQYdDOmuU3WmFlqPXCli+BozAbAgEAAgEBBBMMEVByb2R1Y3Rpb25TYW5kYm94MBwCAQUCAQEEFN1alLNreW+e4J/SQALc+I3VYbiVMB4CAQICAQEEFgwUZWFzeW5ldC5tZHQuaW5mb3RlY2gwHgIBDAIBAQQWFhQyMDE3LTEwLTE5VDA4OjA1OjMyWjAeAgESAgEBBBYWFDIwMTMtMDgtMDFUMDc6MDA6MDBaMEICAQcCAQEEOoiTVFI4bR9TTavh+kAqOaSS5yxP/5pFYYoljq5pepwZ7r6Y3wollWVKYfylrb08+wM1IpYQVhsPRIkwWAIBBgIBAQRQAINpZOzVsHbuUcCkUALlyJX+uUsjntUS6GiORlswCkO0CeyNNzkU7ffx6BY/7PV6LShnsTSvyomGVkfPAgM+JQGVaFzYncklHEzYOBYNHw8wggFbAgERAgEBBIIBUTGCAU0wCwICBqwCAQEEAhYAMAsCAgatAgEBBAIMADALAgIGsAIBAQQCFgAwCwICBrICAQEEAgwAMAsCAgazAgEBBAIMADALAgIGtAIBAQQCDAAwCwICBrUCAQEEAgwAMAsCAga2AgEBBAIMADAMAgIGpQIBAQQDAgEBMAwCAgarAgEBBAMCAQEwDAICBq4CAQEEAwIBADAMAgIGrwIBAQQDAgE";
        //        byte[] str = Base64.decode(b, Base64.DEFAULT);
        //        String str2 = null;
        //        String str3 = null;
        //        try {
        //            str2 = new String(str, "GB2312");
        //            str3 = new String(str, "utf-8");
        //        } catch (UnsupportedEncodingException e) {
        //            e.printStackTrace();
        //        }
        //        LogUtils.e("b:" + str3);
        //        String object = new Gson().toJson(b);
        //        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),"{\"receipt-data\":\"ewoJInNpZ25hdHVyZSIgPSAiQXg5SDVRRGphdVp4Y3RsSnJab1Zlb1I1WUc4OUNpdmZRYXI4NVh4Z2VDMGluMklKSXpKTGdlNGRUVVdUbmN3dlA3QnRWUUg5TTZuU1BUY1EyZFh5WllTQldhb0lGcU9EeGMwTC9PcjZ4aHQvVjZVajVhd3NHdjJSMFlIem9kOXJnYmtXZ2syTm1iWFFvVERkQiszRjNaOW54a1FGSG9PTExYMnM1QXhFcnZ1L3I1YzdjRWZaem91cjBrZkZEWWJsdSsxU1Vkbk52U015ZVdGb1VHc2VRRWVzRmlwWk1wMVRyelVsdDlacmNSM0VIcng1NHE0V0hpNkhoaDVYSTRUS0JCM0FTVjFRUy9TT3VZNDBXYW9WT1ZSV3V0Y2FkU2c5ejRIRkp3U0NTMmw0MmdXajVBOFc5Ui9CZkRZTjBNZnVYNWJ1RnpkdStYeXpGWkRBTzVRd2E3Z0FBQVdBTUlJRmZEQ0NCR1NnQXdJQkFnSUlEdXRYaCtlZUNZMHdEUVlKS29aSWh2Y05BUUVGQlFBd2daWXhDekFKQmdOVkJBWVRBbFZUTVJNd0VRWURWUVFLREFwQmNIQnNaU0JKYm1NdU1Td3dLZ1lEVlFRTERDTkJjSEJzWlNCWGIzSnNaSGRwWkdVZ1JHVjJaV3h2Y0dWeUlGSmxiR0YwYVc5dWN6RkVNRUlHQTFVRUF3dzdRWEJ3YkdVZ1YyOXliR1IzYVdSbElFUmxkbVZzYjNCbGNpQlNaV3hoZEdsdmJuTWdRMlZ5ZEdsbWFXTmhkR2x2YmlCQmRYUm9iM0pwZEhrd0hoY05NVFV4TVRFek1ESXhOVEE1V2hjTk1qTXdNakEzTWpFME9EUTNXakNCaVRFM01EVUdBMVVFQXd3dVRXRmpJRUZ3Y0NCVGRHOXlaU0JoYm1RZ2FWUjFibVZ6SUZOMGIzSmxJRkpsWTJWcGNIUWdVMmxuYm1sdVp6RXNNQ29HQTFVRUN3d2pRWEJ3YkdVZ1YyOXliR1IzYVdSbElFUmxkbVZzYjNCbGNpQlNaV3hoZEdsdmJuTXhFekFSQmdOVkJBb01Da0Z3Y0d4bElFbHVZeTR4Q3pBSkJnTlZCQVlUQWxWVE1JSUJJakFOQmdrcWhraUc5dzBCQVFFRkFBT0NBUThBTUlJQkNnS0NBUUVBcGMrQi9TV2lnVnZXaCswajJqTWNqdUlqd0tYRUpzczl4cC9zU2cxVmh2K2tBdGVYeWpsVWJYMS9zbFFZbmNRc1VuR09aSHVDem9tNlNkWUk1YlNJY2M4L1cwWXV4c1FkdUFPcFdLSUVQaUY0MWR1MzBJNFNqWU5NV3lwb041UEM4cjBleE5LaERFcFlVcXNTNCszZEg1Z1ZrRFV0d3N3U3lvMUlnZmRZZUZScjZJd3hOaDlLQmd4SFZQTTNrTGl5a29sOVg2U0ZTdUhBbk9DNnBMdUNsMlAwSzVQQi9UNXZ5c0gxUEttUFVockFKUXAyRHQ3K21mNy93bXYxVzE2c2MxRkpDRmFKekVPUXpJNkJBdENnbDdaY3NhRnBhWWVRRUdnbUpqbTRIUkJ6c0FwZHhYUFEzM1k3MkMzWmlCN2o3QWZQNG83UTAvb21WWUh2NGdOSkl3SURBUUFCbzRJQjF6Q0NBZE13UHdZSUt3WUJCUVVIQVFFRU16QXhNQzhHQ0NzR0FRVUZCekFCaGlOb2RIUndPaTh2YjJOemNDNWhjSEJzWlM1amIyMHZiMk56Y0RBekxYZDNaSEl3TkRBZEJnTlZIUTRFRmdRVWthU2MvTVIydDUrZ2l2Uk45WTgyWGUwckJJVXdEQVlEVlIwVEFRSC9CQUl3QURBZkJnTlZIU01FR0RBV2dCU0lKeGNKcWJZWVlJdnM2N3IyUjFuRlVsU2p0ekNDQVI0R0ExVWRJQVNDQVJVd2dnRVJNSUlCRFFZS0tvWklodmRqWkFVR0FUQ0IvakNCd3dZSUt3WUJCUVVIQWdJd2diWU1nYk5TWld4cFlXNWpaU0J2YmlCMGFHbHpJR05sY25ScFptbGpZWFJsSUdKNUlHRnVlU0J3WVhKMGVTQmhjM04xYldWeklHRmpZMlZ3ZEdGdVkyVWdiMllnZEdobElIUm9aVzRnWVhCd2JHbGpZV0pzWlNCemRHRnVaR0Z5WkNCMFpYSnRjeUJoYm1RZ1kyOXVaR2wwYVc5dWN5QnZaaUIxYzJVc0lHTmxjblJwWm1sallYUmxJSEJ2YkdsamVTQmhibVFnWTJWeWRHbG1hV05oZEdsdmJpQndjbUZqZEdsalpTQnpkR0YwWlcxbGJuUnpMakEyQmdnckJnRUZCUWNDQVJZcWFIUjBjRG92TDNkM2R5NWhjSEJzWlM1amIyMHZZMlZ5ZEdsbWFXTmhkR1ZoZFhSb2IzSnBkSGt2TUE0R0ExVWREd0VCL3dRRUF3SUhnREFRQmdvcWhraUc5Mk5rQmdzQkJBSUZBREFOQmdrcWhraUc5dzBCQVFVRkFBT0NBUUVBRGFZYjB5NDk0MXNyQjI1Q2xtelQ2SXhETUlKZjRGelJqYjY5RDcwYS9DV1MyNHlGdzRCWjMrUGkxeTRGRkt3TjI3YTQvdncxTG56THJSZHJqbjhmNUhlNXNXZVZ0Qk5lcGhtR2R2aGFJSlhuWTR3UGMvem83Y1lmcnBuNFpVaGNvT0FvT3NBUU55MjVvQVE1SDNPNXlBWDk4dDUvR2lvcWJpc0IvS0FnWE5ucmZTZW1NL2oxbU9DK1JOdXhUR2Y4YmdwUHllSUdxTktYODZlT2ExR2lXb1IxWmRFV0JHTGp3Vi8xQ0tuUGFObVNBTW5CakxQNGpRQmt1bGhnd0h5dmozWEthYmxiS3RZZGFHNllRdlZNcHpjWm04dzdISG9aUS9PamJiOUlZQVlNTnBJcjdONFl0UkhhTFNQUWp2eWdhWndYRzU2QWV6bEhSVEJoTDhjVHFBPT0iOwoJInB1cmNoYXNlLWluZm8iID0gImV3b0pJbTl5YVdkcGJtRnNMWEIxY21Ob1lYTmxMV1JoZEdVdGNITjBJaUE5SUNJeU1ERTJMVEEwTFRBMklEQXdPakF5T2pJNElFRnRaWEpwWTJFdlRHOXpYMEZ1WjJWc1pYTWlPd29KSW5WdWFYRjFaUzFwWkdWdWRHbG1hV1Z5SWlBOUlDSXpNVEJrWWpKbE4yWXhOelJpTTJFNVptUmhPVE0wWkdaa01HRTJaREZpT0Raa1pqYzVNekEwSWpzS0NTSnZjbWxuYVc1aGJDMTBjbUZ1YzJGamRHbHZiaTFwWkNJZ1BTQWlNVEF3TURBd01ESXdNelkzTURNNE55STdDZ2tpWW5aeWN5SWdQU0FpTmlJN0Nna2lkSEpoYm5OaFkzUnBiMjR0YVdRaUlEMGdJakV3TURBd01EQXlNRE0yTnpBek9EY2lPd29KSW5GMVlXNTBhWFI1SWlBOUlDSXhJanNLQ1NKdmNtbG5hVzVoYkMxd2RYSmphR0Z6WlMxa1lYUmxMVzF6SWlBOUlDSXhORFU1T1RJMk1UUTROell6SWpzS0NTSjFibWx4ZFdVdGRtVnVaRzl5TFdsa1pXNTBhV1pwWlhJaUlEMGdJa1l3TWpKRk5rRXhMVFUyTURZdE5FRTRRUzA0UkRrMExUSTFOVU5GTWpRNU1FRkdNaUk3Q2draWNISnZaSFZqZEMxcFpDSWdQU0FpYm1WMExuQmxZV3RuWVcxbGN5NTBiM2xpYkdGemRDNWthV0Z0YjI1a05qUWlPd29KSW1sMFpXMHRhV1FpSUQwZ0lqRXdOakExTVRRNE1URWlPd29KSW1KcFpDSWdQU0FpYm1WMExuQmxZV3RuWVcxbGN5NWhiWGxqYmlJN0Nna2ljSFZ5WTJoaGMyVXRaR0YwWlMxdGN5SWdQU0FpTVRRMU9Ua3lOakUwT0RjMk15STdDZ2tpY0hWeVkyaGhjMlV0WkdGMFpTSWdQU0FpTWpBeE5pMHdOQzB3TmlBd056b3dNam95T0NCRmRHTXZSMDFVSWpzS0NTSndkWEpqYUdGelpTMWtZWFJsTFhCemRDSWdQU0FpTWpBeE5pMHdOQzB3TmlBd01Eb3dNam95T0NCQmJXVnlhV05oTDB4dmMxOUJibWRsYkdWeklqc0tDU0p2Y21sbmFXNWhiQzF3ZFhKamFHRnpaUzFrWVhSbElpQTlJQ0l5TURFMkxUQTBMVEEySURBM09qQXlPakk0SUVWMFl5OUhUVlFpT3dwOSI7CgkiZW52aXJvbm1lbnQiID0gIlNhbmRib3giOwoJInBvZCIgPSAiMTAwIjsKCSJzaWduaW5nLXN0YXR1cyIgPSAiMCI7Cn0=\"}");
        //        RetrofitUtils.getInstance()
        //                .create(ApiService.Test.class)
        //                .appleTest(body)
        //                .compose(RxSchedulers.getInstance(this.bindToLifecycle()).<RxResult<UserBean>>io_main())
        //                .subscribe(new RxObserver<UserBean>() {
        //                    @Override
        //                    protected void _onSuccess(UserBean userBean) {
        //
        //                    }
        //                });

    }
}
