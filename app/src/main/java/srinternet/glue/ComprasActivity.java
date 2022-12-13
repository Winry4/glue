package srinternet.glue;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.vending.billing.IInAppBillingService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ComprasActivity extends AppCompatActivity implements IabBroadcastReceiver.IabBroadcastListener{
    private IabHelper mHelper;
    private IabBroadcastReceiver mBroadcastReceiver;
    private String COINS1="and.glue.coinsa";
    private String COINS2="and.glue.coinsb";
    private String COINS3="and.glue.coinsc";
    private String COINS4="and.glue.coinsd";
    private String COINS5="and.glue.coinse";
    public static final String RESPONSE_INAPP_PURCHASE_DATA = "INAPP_PURCHASE_DATA";
    public static final String RESPONSE_INAPP_SIGNATURE = "INAPP_DATA_SIGNATURE";
    String purchase;
    static final int RC_REQUEST = 10001;
    private IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener;
    String response="";

    @Override
    public void receivedBroadcast() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_compras);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ImageView atras = (ImageView)findViewById(R.id.img_btn_atras_misCompras);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApL/JzpNiRcpDqKC8tp2KvldOuccIASJKqAfpTi4O1fzi9q+eKApqYVWMdgDZG6iKr6UAhZqMNfJpcIBd2tHPuDG7e+k9IDgDusZkTUxTDB4Nx2yEJ6eLKd3rMmg0H4vhtbuYW7wX3clWPZN1/46ZIznPXdvz3ncFPwbn2VRKsHpHXCHznmuWQz6zal3MGaxTlUimMbiRandR4SPOdCmf1byqGOTIU3JUvGiiB4xOPtJBfLC5jkTyheO2kAL1uQ8LtDtVGz7YknAZdNjevlTWBT+HmPZNCAfcrKbXupxjHnszOF4mukruCYUE/157E/iMv3ttMXQzcWabLGJsR386HQIDAQAB";
        mHelper = new IabHelper(getApplicationContext(), base64EncodedPublicKey);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    String params="funcion=f29&idFacebo="+Memoria.UserFBId+"&idSesion="+Memoria.idSension;

                    HttpURLConnection conn =(HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    PrintWriter wr = new PrintWriter (
                            conn.getOutputStream ());
                    wr.print(params);
                    wr.close();
                    conn.connect();
                    Scanner inStream = new Scanner(conn.getInputStream());
                    while(inStream.hasNextLine())
                        response+=(inStream.nextLine());

                    conn.disconnect();


                    ComprasActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                JSONObject u = new JSONObject(response);
                                TextView coins = (TextView) findViewById(R.id.textView27);
                                String txt = u.getInt("creditos") + "";
                                coins.setText(txt);
                                TextView com = (TextView) findViewById(R.id.textView26);
                                if(u.getBoolean("premium")){
                                    String h="Premium - "+ u.getString("fechaPremium");
                                    com.setText(h);
                                    coins.setText("∞");
                                }


                            }catch(Exception e){
                                e.printStackTrace();
                                TextView coins = (TextView) findViewById(R.id.textView27);
                                String txt = "0";
                                coins.setText(txt);
                            }

                        }
                    });


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });



        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            @Override
            public void onIabSetupFinished(IabResult result) {

                if (!result.isSuccess()) {
                    System.out.println("In-app Billing setup failed: " + result);
                } else {
                    System.out.println("In-app Billing is set up OK");
                }

                if (mHelper == null) return;

                mBroadcastReceiver = new IabBroadcastReceiver(ComprasActivity.this);
                IntentFilter broadcastFilter = new IntentFilter(IabBroadcastReceiver.ACTION);
                registerReceiver(mBroadcastReceiver, broadcastFilter);

                // IAB is fully set up. Now, let's get an inventory of stuff we own.
                System.out.println("Setup successful. Querying inventory.");

                try {
                    final IabHelper.QueryInventoryFinishedListener
                            mQueryFinishedListener = new IabHelper.QueryInventoryFinishedListener() {
                        public void onQueryInventoryFinished(IabResult result, Inventory inventory)
                        {
                            if (result.isFailure()) {
                                // handle error
                                return;
                            }
                            String p1 =
                                    inventory.getSkuDetails(COINS1).getPrice();
                            String p2 =
                                    inventory.getSkuDetails(COINS2).getPrice();

                            String p3 =
                                    inventory.getSkuDetails(COINS3).getPrice();
                            String p4 =
                                    inventory.getSkuDetails(COINS4).getPrice();
                            //String p5 =
                            inventory.getSkuDetails(COINS5).getPrice();
                            // update the UI

                            TextView tex1 = (TextView) findViewById(R.id.textView28);
                            TextView tex2 = (TextView) findViewById(R.id.textView31);
                            //TextView tex3 = (TextView) findViewById(R.id.textView32);
                            TextView tex4 = (TextView) findViewById(R.id.textView33);
                            TextView tex5 = (TextView) findViewById(R.id.textView34);

                            tex1.setText(p3);
                            tex2.setText(p4);
                            //tex3.setText(p3);
                            tex4.setText(p1);
                            tex5.setText(p2);

                        }
                    };
                    List additionalSkuList = new ArrayList();
                    additionalSkuList.add(COINS1);
                    additionalSkuList.add(COINS2);
                    additionalSkuList.add(COINS3);
                    additionalSkuList.add(COINS4);
                    additionalSkuList.add(COINS5);

                    mHelper.queryInventoryAsync(true, additionalSkuList,additionalSkuList,
                            mQueryFinishedListener);

                } catch (IabHelper.IabAsyncInProgressException e) {
                    System.out.println("Error querying inventory. Another async operation in progress.");
                }

            }
        });

        //AQUI SE COMPRA

        RelativeLayout cred10 = (RelativeLayout) findViewById(R.id.Creditos10);
        cred10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                purchase="and.glue.coinsA";
                mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
                    public void onIabPurchaseFinished(IabResult result, final Purchase purchase) {
                        // if we were disposed of in the meantime, quit.
                        if (mHelper == null) return;

                        if (result.isFailure()) {
                            System.out.println(result.getMessage());
                        }else if (result.isSuccess()){
                            consume(purchase);
                        }
                    }
                };
                try{
                    mHelper.launchPurchaseFlow(ComprasActivity.this,COINS1,10001,mPurchaseFinishedListener, "");

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        RelativeLayout cred20 = (RelativeLayout) findViewById(R.id.Creditos20);
        cred20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                purchase="and.glue.coinsB";
                mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
                    public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
                        // if we were disposed of in the meantime, quit.
                        if (mHelper == null) return;

                        if (result.isFailure()) {
                            System.out.println(result.getMessage());
                        }else if (result.isSuccess()){
                            consume(purchase);
                        }
                    }
                };
                try{
                    mHelper.launchPurchaseFlow(ComprasActivity.this,COINS2,10001,mPurchaseFinishedListener, "");

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        RelativeLayout cred50 = (RelativeLayout) findViewById(R.id.Creditos50);
        cred50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                purchase="and.glue.coinsC";
                mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
                    public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
                        // if we were disposed of in the meantime, quit.
                        if (mHelper == null) return;

                        if (result.isFailure()) {
                            System.out.println(result.getMessage());
                        }else if (result.isSuccess()){
                            consume(purchase);
                        }
                    }
                };
                try{
                    mHelper.launchPurchaseFlow(ComprasActivity.this,COINS3,10001,mPurchaseFinishedListener, "");

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        RelativeLayout cred100 = (RelativeLayout) findViewById(R.id.Creditos100);
        cred100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                purchase="and.glue.coinsD";
                mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
                    public void onIabPurchaseFinished(IabResult result, final Purchase purchase) {
                        // if we were disposed of in the meantime, quit.
                        if (mHelper == null) return;

                        if (result.isFailure()) {
                            System.out.println(result.getMessage());
                        }else if (result.isSuccess()){

                            consume(purchase);
                        }
                    }
                };
                try{
                    mHelper.launchPurchaseFlow(ComprasActivity.this,COINS4,10001,mPurchaseFinishedListener, "");

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }


    public void consume(Purchase purchase){
        IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
                new IabHelper.OnConsumeFinishedListener() {
                    public void onConsumeFinished(final Purchase purchase, IabResult result) {
                        if (result.isSuccess()) {
                            System.out.println("COMPRA FINALIZADA");
                            // provision the in-app purchase to the user
                            // (for example, credit 50 gold coins to player's character)
                        }
                        else {
                            System.out.println(result.getMessage());
                        }
                    }
                };
        try {
            mHelper.consumeAsync(purchase, mConsumeFinishedListener);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (mHelper != null) mHelper.dispose();
            mHelper = null;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if(requestCode==10001){
            System.out.println("hola");
            if(resultCode==RESULT_OK){
                System.out.println("MANDANDO AL SERVER COSAS");
                new AsyncTask<Object,Object,Object>(){
                    @Override
                    protected Object doInBackground(Object[] params) {

                        String dataSignature = data.getStringExtra(RESPONSE_INAPP_SIGNATURE);
                        String purchaseData = data.getStringExtra(RESPONSE_INAPP_PURCHASE_DATA);
                        try {
                            String param="funcion=f25&idFacebo="+Memoria.UserFBId+"&idSesion="+Memoria.idSension+"&bundleId="+purchase+"&reciboTr="+purchaseData+"&firmaRec="+dataSignature;
                            String response="";
                            HttpURLConnection conn =(HttpURLConnection) new URL("http://www.glueffect.com/app/glue.php").openConnection();
                            conn.setRequestMethod("POST");
                            conn.setDoOutput(true);
                            conn.setDoInput(true);
                            PrintWriter wr = new PrintWriter (
                                    conn.getOutputStream ());
                            wr.print(param);
                            wr.close();
                            conn.connect();
                            Scanner inStream = new Scanner(conn.getInputStream());
                            while(inStream.hasNextLine())
                                response+=(inStream.nextLine());

                            conn.disconnect();
                            System.out.println(response);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        TextView coins = (TextView) findViewById(R.id.textView27);
                        String txt = "muchismos";
                        coins.setText(txt);
                        super.onPostExecute(o);
                    }
                }.execute("");
            }
        }



        if (!mHelper.handleActivityResult(requestCode,
                resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}

