package network.social.com.androidfinal;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import network.social.com.androidfinal.model.Gorev;

public class TaskAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Gorev> mTaskList;

    public TaskAdapter(Activity activity, List<Gorev> taskList) {
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mTaskList = taskList;
    }

    @Override
    public int getCount() {
        return mTaskList.size();
    }

    @Override
    public Gorev getItem(int position) {
        return mTaskList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView;

        itemView = mInflater.inflate(R.layout.flag_list, null);
        TextView textName=(TextView)itemView.findViewById(R.id.customer_first_last_name);
        TextView textTcNo=(TextView)itemView.findViewById(R.id.customer_tc_no);
        TextView textCargo=(TextView)itemView.findViewById(R.id.cargo_pay);

        Gorev customerInform=mTaskList.get(position);

        textName.setText(customerInform.getCustomerName()+customerInform.getCustomerLastName());
        textTcNo.setText(customerInform.getCustomerTcNo());
        textCargo.setText(String.valueOf(customerInform.getCargoPay()));


        return itemView;
    }
}


