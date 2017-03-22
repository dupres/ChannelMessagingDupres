package dupre.stephen.channelmessagingdupres.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.HashMap;

import dupre.stephen.channelmessagingdupres.Channel;
import dupre.stephen.channelmessagingdupres.ChannelActivity;
import dupre.stephen.channelmessagingdupres.ChannelArrayAdapter;
import dupre.stephen.channelmessagingdupres.ChannelsContainer;
import dupre.stephen.channelmessagingdupres.Downloader;
import dupre.stephen.channelmessagingdupres.FriendsActivity;
import dupre.stephen.channelmessagingdupres.OnDownloadCompleteListener;
import dupre.stephen.channelmessagingdupres.R;
import dupre.stephen.channelmessagingdupres.login_Activity;

public class ListFragment extends AppCompatActivity {
    private ListView channels;
    private Button btnFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list);

        channels = (ListView) findViewById(R.id.listViewChannels);
        btnFriends = (Button) findViewById(R.id.buttonFriends);

        SharedPreferences settings = getSharedPreferences(login_Activity.PREFS_NAME, 0);
        String accesstoken = settings.getString("accesstoken","");

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("accesstoken",accesstoken);

        Downloader connexion = new Downloader(getApplicationContext(), params, "http://www.raphaelbischof.fr/messaging/?function=getchannels");

        connexion.setListener(new  OnDownloadCompleteListener() {
            @Override
            public void onDownloadComplete(String content) {
                Gson gson = new Gson();
                ChannelsContainer obj = gson.fromJson(content, ChannelsContainer.class);

                channels.setAdapter((new ChannelArrayAdapter(getApplicationContext(), obj.getChannels())));
            }
        });


        channels.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Channel channel = (Channel) channels.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), FragmentActivity.class);
                intent.putExtra("channelid", Integer.toString(channel.getChannelID()));
                startActivity(intent);
            }
        });

        btnFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FriendsActivity.class);
                startActivity(intent);
            }
        });

        connexion.execute();
    }
}
