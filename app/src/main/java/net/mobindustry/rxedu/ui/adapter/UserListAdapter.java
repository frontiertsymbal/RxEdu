package net.mobindustry.rxedu.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.mobindustry.rxedu.R;
import net.mobindustry.rxedu.model.User;

public class UserListAdapter extends ArrayAdapter<User> {

    private LayoutInflater inflater;

    public UserListAdapter(Context context) {
        super(context, R.layout.user_list_item);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.user_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.userName = (TextView) convertView.findViewById(R.id.userName);
            viewHolder.userEmail = (TextView) convertView.findViewById(R.id.userEmail);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        User user = getItem(position);
        viewHolder.userName.setText(user.getName());
        viewHolder.userEmail.setText(user.getEmail());

        return convertView;
    }

    private static class ViewHolder {
        TextView userName;
        TextView userEmail;
    }
}
