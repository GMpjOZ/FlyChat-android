package flaychat.cn.flychat.contact;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import flaychat.cn.flychat.R;


import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by MPJ on 15/4/27.
 */
public class ContactAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private Context mContext;
    private String [] contacts;
    private LayoutInflater inflater;
//    private List<Contact> mContacts;
public ContactAdapter(Context context) {
    inflater = LayoutInflater.from(context);
    contacts = context.getResources().getStringArray(R.array.contacts);
}

    @Override
    public int getCount() {
        return contacts.length;
    }

    @Override
    public Object getItem(int position) {
        return contacts[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {

            holder = new ViewHolder();
            Log.w("convertView == null",holder.toString());
            convertView = inflater.inflate(R.layout.contacts_list_item_layout, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.contacs_list_item_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            Log.w("convertView != null",holder.toString());
        }
        Log.w("position",position+"");
        Log.w("contacts[position]",contacts[position]);
        Log.w("holder.text",holder.text.toString());

        holder.text.setText(contacts[position]);


        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();

            convertView = inflater.inflate(R.layout.contacts_header, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.contacts_header_text);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        //set header text as first char in name
        String headerText = "" + contacts[position].subSequence(0, 1).charAt(0);
        holder.text.setText(headerText);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return contacts[position].subSequence(0, 1).charAt(0);
    }


    class HeaderViewHolder {
        TextView text;
    }

    class ViewHolder {
        TextView text;
    }
}
