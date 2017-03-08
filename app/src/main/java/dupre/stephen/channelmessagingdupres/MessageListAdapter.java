package dupre.stephen.channelmessagingdupres;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dupres on 06/02/2017.
 */
public class MessageListAdapter extends ArrayAdapter<Message> {
    private final Context context;
    private final List<Message> values;

    public MessageListAdapter(Context context, List<Message> messages ){
        super(context, 0, messages);
        this.context = context;
        this.values = messages;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_row_message, parent, false);
        }

        TextView text = (TextView) convertView.findViewById(R.id.textViewMessage);
        TextView user = (TextView) convertView.findViewById(R.id.textViewUser);
        TextView date = (TextView) convertView.findViewById(R.id.textViewDate);
        CircleImageView avatar = (CircleImageView) convertView.findViewById(R.id.imageViewAvatar);

        text.setText(message.getMessage());
        user.setText(message.getUsername().toString() + " : ");
        date.setText(message.getDate());

        if(ChannelActivity.images.containsKey(message.getImageUrl())){
            avatar.setImageBitmap(ChannelActivity.images.get(message.getImageUrl()));
        }else{
            new DownloadImageTask(avatar).execute(message.getImageUrl());
        }

        return convertView;
    }
}
