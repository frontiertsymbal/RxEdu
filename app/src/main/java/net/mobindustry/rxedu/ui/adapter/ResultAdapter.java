package net.mobindustry.rxedu.ui.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import net.mobindustry.rxedu.R;

public class ResultAdapter extends ArrayAdapter<String> {

    public ResultAdapter(Context context) {
        super(context, R.layout.result_list_item, R.id.resultText);
    }
}
