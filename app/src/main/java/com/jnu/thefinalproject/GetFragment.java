package com.jnu.thefinalproject;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jnu.aboutDate.Record;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class GetFragment extends Fragment {

    private static final String ARG_PARAM1="param1";
    private static final String ARG_PARAM2="param2";

    private ExpandableListView expandableListview;

    private String mParam1;
    private String mParam2;
    public GetExpandableListviewAdapter adapterget;
    private ImageButton imageButtonOut;

    private ArrayList<Record> recordsGet = new ArrayList<Record>();
    private ArrayList<String> children  = new ArrayList<String >();
    private ArrayList<ArrayList<Record>> recordsChild  = new ArrayList<ArrayList<Record>>();


    public GetFragment() {
        // Required empty public constructor
    }

    //根据record的年份进行分类
    private ArrayList<String> Classify_Record_By_year(ArrayList<Record> records){
        ArrayList<String> str = new ArrayList<>();
        HashMap<String,Integer> map = new HashMap<>();
        for(int i=0;i<records.size();i++){
            if(!map.containsKey(records.get(i).getReson())){
                str.add(records.get(i).getReson());
                map.put((records.get(i).getReson()),1);
            }
        }
        Collections.sort(str);
        return str;
    }

    //初始化子层数据，根据年份进行分类
    private void addRecord(){
        MainActivity.dataBank.Load();
        recordsGet = MainActivity.dataBank.getGetRecords();
        if(0==recordsGet.size()) children.add("暂无收礼记录");
        else children=Classify_Record_By_year(recordsGet);

        for(int i=0;i<children.size();i++){
            ArrayList<Record> string1 = new ArrayList<>();
            for(int j=0;j<recordsGet.size();j++){
                if(children.get(i).equals(recordsGet.get(j).getReson())){
                    string1.add(recordsGet.get(j));
                }
            }
            recordsChild.add(string1);
        }
    }

    public static GetFragment newInstance(String param1, String param2) {
        GetFragment fragment=new GetFragment();
        Bundle args=new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1=getArguments().getString(ARG_PARAM1);
            mParam2=getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_get, container, false);
        initView(view);
        imageButtonOut = view.findViewById(R.id.imageButton_give_out);
        imageButtonOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        return view;
    }
    //适配器
    public class GetExpandableListviewAdapter extends BaseExpandableListAdapter {
        private ArrayList<String> children;
        private ArrayList<ArrayList<Record>> recordsChild;
        private GetFragment context;

        public GetExpandableListviewAdapter(GetFragment context, ArrayList<String> children, ArrayList<ArrayList<Record>> recordName){
            this.context=context;
            this.children=children;
            this.recordsChild=recordName;

        }

        @Override
        public int getGroupCount() {
            return children.size();
        }

        @Override
        public int getChildrenCount(int i) {
            return recordsChild.get(i).size();
        }

        @Override
        public Object getGroup(int i) {
            return children.get(i);
        }

        @Override
        public Object getChild(int i, int i1) {
            return recordsChild.get(i).get(i1);
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override

        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GetGroupViewHolder groupViewHolder;
            if (convertView == null){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_child_get,parent,false);
                groupViewHolder = new GetGroupViewHolder();
                groupViewHolder.parent_textview_id = convertView.findViewById(R.id.txt_get);
                groupViewHolder.parent_image = convertView.findViewById(R.id.txt2_get);
                convertView.setTag(groupViewHolder);
            }else {
                groupViewHolder = (GetGroupViewHolder)convertView.getTag();
            }
            groupViewHolder.parent_textview_id.setText(children.get(groupPosition));
            groupViewHolder.parent_image.setText(String.valueOf(recordsChild.get(groupPosition).size()));
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            getChildViewHolder childViewHolder;
            if (convertView==null){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_grandson_get,parent,false);
                childViewHolder = new getChildViewHolder();
                childViewHolder.name = (TextView)convertView.findViewById(R.id.textView_get_name);
                childViewHolder.type = (TextView)convertView.findViewById(R.id.textView_get_type);
                childViewHolder.date = (TextView)convertView.findViewById(R.id.textView_get_date);
                childViewHolder.account = (TextView)convertView.findViewById(R.id.textView_get_account);
                convertView.setTag(childViewHolder);

            }else {
                childViewHolder = (getChildViewHolder) convertView.getTag();
            }
            childViewHolder.name.setText(recordsChild.get(groupPosition).get(childPosition).getName());
            childViewHolder.type.setText(recordsChild.get(groupPosition).get(childPosition).getReson());
            childViewHolder.date.setText(recordsChild.get(groupPosition).get(childPosition).getDate().substring(0,4)+"年"+recordsChild.get(groupPosition).get(childPosition).getDate().substring(4,6)+"月"+recordsChild.get(groupPosition).get(childPosition).getDate().substring(6,8)+"日");
            childViewHolder.account.setText("￥"+recordsChild.get(groupPosition).get(childPosition).getAccount()+"元");
            return convertView;
        }

        //指定位置上的子元素是否可选中
        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }
        public class GetGroupViewHolder {
            TextView parent_textview_id;
            TextView parent_image;
        }

        public class getChildViewHolder {
            TextView name;
            TextView date;
            TextView type;
            TextView account;
        }
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        ExpandableListView.ExpandableListContextMenuInfo info =(ExpandableListView.ExpandableListContextMenuInfo) menuInfo;
        int type = ExpandableListView.getPackedPositionType(info.packedPosition);
        super.onCreateContextMenu(menu, v, menuInfo);
        if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD ){
            menu.add(1,1,1,"修改");
            menu.add(1,2,1,"删除");
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        ExpandableListView.ExpandableListContextMenuInfo info =(ExpandableListView.ExpandableListContextMenuInfo) item.getMenuInfo();
        final int type = ExpandableListView.getPackedPositionType(info.packedPosition);
        final int group = ExpandableListView
                .getPackedPositionGroup(info.packedPosition);
        final int child = ExpandableListView
                .getPackedPositionChild(info.packedPosition);
        switch (item.getItemId()){
            case 1:
                final AlertDialog.Builder customizeDialog =new AlertDialog.Builder(getContext());
                final View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.context_menu_correct_get,null);
                final EditText edit_text_date =(EditText) dialogView.findViewById(R.id.corrct_date_get);
                final TextView edit_text_reson =(TextView) dialogView.findViewById(R.id.correct_reson_get);
                final EditText edit_text_name =(EditText) dialogView.findViewById(R.id.correct_name_get);
                final EditText edit_text_account=(EditText) dialogView.findViewById(R.id.correct_account_get);
                edit_text_date.setText(recordsChild.get(group).get(child).getDate());
                edit_text_reson.setText(recordsChild.get(group).get(child).getReson());
                edit_text_name.setText(recordsChild.get(group).get(child).getName());
                edit_text_account.setText(recordsChild.get(group).get(child).getAccount());
                customizeDialog.setIcon(R.drawable.correct);
                customizeDialog.setTitle("请输入修改内容：");
                customizeDialog.setView(dialogView);
                customizeDialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                        builder.setIcon(R.drawable.attention);
                        builder.setTitle("输入数据有误");
                        if(edit_text_date.getText().toString().length()!=8||(Integer.parseInt(edit_text_date.getText().toString().substring(4,6))>12)||(Integer.parseInt(edit_text_date.getText().toString().substring(6,8))>31)){
                            builder.setMessage("日期格式有误");
                            AlertDialog Dialog=builder.create();
                            Dialog.show();
                        } else if (edit_text_name.getText().toString().length() == 0) {
                            builder.setMessage("请输入好友姓名");
                            AlertDialog Dialog=builder.create();
                            Dialog.show();
                        } else if (edit_text_account.getText().toString().length() == 0) {

                            builder.setMessage("请输入礼金数量");
                            AlertDialog Dialog=builder.create();
                            Dialog.show();
                        }else {
                            String date = edit_text_date.getText().toString();
                            String reson = edit_text_reson.getText().toString();
                            String name = edit_text_name.getText().toString();
                            String account = edit_text_account.getText().toString();
                            correctRecords(recordsChild.get(group).get(child),MainActivity.dataBank.listRecord,name,date,account,reson);
                            MainActivity.dataBank.Save();
                            recordsChild.get(group).get(child).setReson(reson);
                            recordsChild.get(group).get(child).setDate(date);
                            recordsChild.get(group).get(child).setName(name);
                            recordsChild.get(group).get(child).setAccount(account);

                            adapterget.notifyDataSetChanged();
                            HomeFragment.delet();
                            Toast.makeText(getContext(),"成功修改为：\n"+"日期："+recordsChild.get(group).get(child).getDate()+"\n类型："+reson+"\n姓名："+name+"\n金额："+account,Toast.LENGTH_SHORT).show();
                    }
                    }
                });
                customizeDialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                customizeDialog.show();break;

            case 2:final AlertDialog.Builder normalDialog =new AlertDialog.Builder(this.getContext());
                normalDialog.setIcon(R.drawable.attention);
                normalDialog.setTitle("   ");
                normalDialog.setMessage("是否删除该条记录?");
                normalDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                deletRecord(recordsChild.get(group).get(child),MainActivity.dataBank.listRecord);
                                MainActivity.dataBank.Save();
                                recordsGet.remove(recordsChild.get(group).get(child));
                                recordsChild.get(group).remove(child);
                                if(recordsChild.get(group).size()==0){
                                    children.remove(group);
                                    if(children.size()==0)
                                        children.add("暂无随礼记录");
                                }
                                adapterget.notifyDataSetInvalidated();
                                HomeFragment.delet();
                                Toast.makeText(getContext(),"删除成功！",Toast.LENGTH_SHORT).show();
                            }
                        });
                normalDialog.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                normalDialog.show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void initView(View view) {
        addRecord();
        expandableListview=view.findViewById(R.id.listView_parent_get);
        adapterget=new GetExpandableListviewAdapter(this, children,recordsChild);
        expandableListview.setAdapter(adapterget);
        expandableListview.expandGroup(0);
        this.registerForContextMenu(expandableListview);
    }
    private void deletRecord(Record record, ArrayList<Record> arrayList){
        for(int i=0;i<arrayList.size();i++){
            if(arrayList.get(i).equals(record)){
                arrayList.remove(i);
                break;
            }
        }
    }
    public void correctRecords(Record record, ArrayList<Record> arrayList, String name, String date, String account, String reson){
        for(int i=0;i<arrayList.size();i++){
            if(arrayList.get(i).equals(record)){
                arrayList.get(i).setName(name);
                arrayList.get(i).setReson(reson);
                arrayList.get(i).setAccount(account);
                arrayList.get(i).setDate(date);
                break;
            }
        }
    }
}