package dupre.stephen.channelmessagingdupres;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.dynamitechetan.flowinggradient.FlowingGradient;
import com.dynamitechetan.flowinggradient.FlowingGradientClass;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Random;

import android.os.Handler;

/**
 * Created by dupres on 06/02/2017.
 */
public class login_Activity extends AppCompatActivity implements OnDownloadCompleteListener, View.OnClickListener{

    private EditText identifiant;
    private EditText password;
    private Button boutonValider;
    public static final String PREFS_NAME = "PrefsFiles";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        boutonValider = (Button) findViewById(R.id.buttonValider);
        boutonValider.setOnClickListener(this);
        identifiant = (EditText) findViewById(R.id.Identifiant);
        password = (EditText) findViewById(R.id.Password);
        final TextView quote = (TextView) findViewById(R.id.textViewQuote);
        final int[] quoteNb = new int[2];
        final String[] quotes = new String[11];
        quotes[0]="Welcome !";
        quotes[1]="Bienvenue !";
        quotes[2]="Willkommen!";
        quotes[3]="Bienvenido!";
        quotes[4]="Benvenuto!";
        quotes[5]="Yōkoso!";
        quotes[6]="Bem-vindo!";
        quotes[7]="Fáilte!";
        quotes[8]="Dobro pozhalovat'!";
        quotes[9]="Welkom!";
        quotes[10]="Welina !";





        final View logo = findViewById(R.id.imageViewLogoWhite);
        final Handler mHandlerTada = new Handler(); // android.os.handler
        final Integer mShortDelay = 3000; //milliseconds
        mHandlerTada.postDelayed(new Runnable(){
            public void run(){
                YoYo.with(Techniques.Tada).playOn(logo);
                // Your code here
                mHandlerTada.postDelayed(this, mShortDelay);
            }
        }, mShortDelay);
        
        final View tBox = findViewById(R.id.textViewQuote);
        final Handler mHandlerQuote = new Handler();
        final Integer mShortDelayQuote = 4000;

        final Handler mHandlerQuote2 = new Handler();
        final Integer mShortDelayQuote2 = 1000;

        final boolean[] firstTime = {true};

        mHandlerQuote.postDelayed(new Runnable(){
            public void run(){
                YoYo.with(Techniques.SlideOutLeft).playOn(quote);

                mHandlerQuote2.postDelayed(new Runnable(){
                    public void run(){
                        if (firstTime[0]){
                            quoteNb[0]=1;
                            quoteNb[1]=1;
                            firstTime[0] =false;
                        }
                        else{
                            final Random rand = new Random();
                            quoteNb[1]=quoteNb[0];
                            quoteNb[0]=rand.nextInt(quotes.length);
                            if (quoteNb[0]==quoteNb[1]){
                                quoteNb[0]=quoteNb[0]+1;
                                if (quoteNb[0]>quotes.length){
                                    quoteNb[0]=1;
                                }
                            }
                        }

                        quote.setText(quotes[quoteNb[0]]);
                        YoYo.with(Techniques.SlideInRight).playOn(quote);
                    }
                },mShortDelayQuote2);
                mHandlerQuote.postDelayed(this, mShortDelayQuote);
            }
        },mShortDelayQuote);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout_id);
        FlowingGradientClass gradient = new FlowingGradientClass();
        gradient.setBackgroundResource(R.drawable.translate).setTransitionDuration(4000).onLinearLayout(linearLayout).start();



    }

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username",identifiant.getText().toString());
        params.put("password",password.getText().toString());

        Animation animSlideLeft = AnimationUtils.loadAnimation(this,R.anim.slide_left);
        findViewById(R.id.textViewQuote).startAnimation(animSlideLeft);

        YoYo.with(Techniques.FadeOut).playOn(boutonValider);
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setProgress(0);
        Downloader connexion = new Downloader(getApplicationContext(), params, "http://www.raphaelbischof.fr/messaging/?function=connect");
        connexion.setListener(new OnDownloadCompleteListener() {
            @Override
            public void onDownloadComplete(String content) {
                Gson gson = new Gson();
                ConnectionReturn obj = gson.fromJson(content, ConnectionReturn.class);
                if(obj.getResponse().equals("Ok")){
                    Toast.makeText(getApplicationContext(), "Connecté", Toast.LENGTH_SHORT).show();
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("accesstoken", obj.getAccesstoken());
                    editor.commit();
                    progress.setProgress(50);

                    Intent myIntent = new Intent(getApplicationContext(),ChannelListActivity.class);
                    View logo1 = findViewById(R.id.imageViewLogoWhite);
                    startActivity(myIntent, ActivityOptions.makeSceneTransitionAnimation(login_Activity.this,logo1,"logo").toBundle());
                }
                else{
                    Toast.makeText(getApplicationContext(), "Erreur de connexion", Toast.LENGTH_SHORT).show();
                }
            }
        });
        progress.setProgress(100);
        connexion.execute();
        Toast.makeText(getApplicationContext(), "Connexion en cours...", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDownloadComplete(String content) {
        Gson gson = new Gson();
        ConnectionReturn obj = gson.fromJson(content, ConnectionReturn.class);

        if(obj.getResponse().equals("Ok")){
            Toast.makeText(getApplicationContext(), "Connecté", Toast.LENGTH_SHORT).show();

            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("accesstoken", obj.getAccesstoken());

            editor.commit();
        }
        else{
            Toast.makeText(getApplicationContext(), "Erreur de connexion", Toast.LENGTH_SHORT).show();
        }    }
}
