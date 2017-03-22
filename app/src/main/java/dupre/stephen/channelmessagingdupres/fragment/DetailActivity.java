package dupre.stephen.channelmessagingdupres.fragment;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import dupre.stephen.channelmessagingdupres.R;

public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String texteAAfficher =
                getIntent().getStringExtra("monTextAAfficher");

    }
}
