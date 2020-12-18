package com.jnu.thefinalproject;

import android.content.DialogInterface;
import android.content.Intent;
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

import static android.widget.Toast.LENGTH_SHORT;

public class GiveFragment extends Fragment {

    private ExpandableListView expand_list_id;
    private static final String ARG_PARAM1="param1";
    private static final String ARG_PARAM2="param2";
    private ImageButton imageButtonOut;
    public static ExpandableListviewAdapter adaptergive;
    private ArrayList<Record> recordsGive = new ArrayList<Record>();
    private ArrayList<String> children  = new ArrayList<String >();
    private ArrayList<ArrayList<Record>> recordsChild  = new ArrayList<ArrayList<Record>>();
    int iamegNumb = R.drawable.home;
    private String mParam1;
    private String mParam2;


    public ArrayList<String> Classify_Record_By_year(ArrayList<Record> recordsYear){
        ArrayList<String> str = new ArrayList<>();
        HashMap<String,Integer> map = new HashMap<>();
        for(int i=0;i<recordsYear.size();i++){
            if(!map.containsKey(recordsYear.get(i).getYear())){
                str.add(recordsYear.get(i).getYear());
                map.put((recordsYear.get(i).getYear()),1);
            }
        }
        Collections.sort(str);
        return str;
    }

    private ArrayList<ArrayList<Record>> addRecord(){
        MainActivity.dataBank.Load();
        recordsGive = MainActivity.dataBank.getGiveRecords();
        if(0==recordsGive.size()||recordsGive==null) children.add("暂无收礼记录");
        else children = Classify_Record_By_year(recordsGive);
        for(int i=0;i<children.size();i++){
            ArrayList<Record> child = new ArrayList<>();
            for(int j=0;j<recordsGive.size();j++){
                if(children.get(i).equals(recordsGive.get(j).getYear())){
                    child.add(recordsGive.get(j));
                }
            }
            recordsChild.add(child);
        }
        return recordsChild;
    }
    public GiveFragment() {}
    public static GiveFragment newInstance(String param1, String param2) {
        GiveFragment fragment=new GiveFragment();
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

    @Override//oncreateView
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_give, container, false);

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
    //适配器adapter
    public class ExpandableListviewAdapter extends BaseExpandableListAdapter {
        private ArrayList<String> children;
        private ArrayList<ArrayList<Record>> records;
        private GiveFragment context;

        public ExpandableListviewAdapter(GiveFragment context, ArrayList<String> children, ArrayList<ArrayList<Record>> records){
            this.context=context;
            this.children=children;
            this.records=records;
        }

        @Override
        public int getGroupCount() {
            return children.size();
        }

        @Override
        public int getChildrenCount(int i) {
            return records.get(i).size();
        }

        @Override
        public Object getGroup(int i) {
            return children.get(i);
        }

        @Override
        public Object getChild(int i, int i1) {
            return records.get(i).get(i1);
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
            return false;
        }

        @Override

        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupViewHolder groupViewHolder;
            if (convertView == null){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_child_give,parent,false);
                groupViewHolder = new GroupViewHolder();
                groupViewHolder.parent_textview_id = convertView.findViewById(R.id.txt);
                groupViewHolder.parent_image = convertView.findViewById(R.id.txt2);
                convertView.setTag(groupViewHolder);
            }else {
                groupViewHolder = (GroupViewHolder)convertView.getTag();
            }
            groupViewHolder.parent_textview_id.setText(children.get(groupPosition));
            groupViewHolder.parent_image.setText(String.valueOf(records.get(groupPosition).size()));
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildViewHolder childViewHolder;
            if (convertView==null){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_grandson_give,parent,false);
                childViewHolder = new ChildViewHolder();
                childViewHolder.name = (TextView)convertView.findViewById(R.id.textView_give_name);
                childViewHolder.type = (TextView)convertView.findViewById(R.id.textView_give_type);
                childViewHolder.date = (TextView)convertView.findViewById(R.id.textView_give_date);
                childViewHolder.account = (TextView)convertView.findViewById(R.id.textView_give_account);
                convertView.setTag(childViewHolder);

            }else {
                childViewHolder = (ChildViewHolder) convertView.getTag();
            }
            childViewHolder.name.setText((CharSequence) records.get(groupPosition).get(childPosition).getName());
            childViewHolder.type.setText(records.get(groupPosition).get(childPosition).getReson());
            childViewHolder.date.setText(records.get(groupPosition).get(childPosition).getDate().substring(4,6)+"月"+records.get(groupPosition).get(childPosition).getDate().substring(6,8)+"日");
            childViewHolder.account.setText("￥"+records.get(groupPosition).get(childPosition).getAccount()+"元");
            return convertView;
        }

        //指定位置上的子元素是否可选中
        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }
        public class GroupViewHolder {
            TextView parent_textview_id;
            TextView parent_image;
        }

        public class ChildViewHolder {
            TextView name;
            TextView date;
            TextView type;
            TextView account;
        }
    }

    @Override//上下文菜单实现修改删除
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
                final View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.context_menu_correct,null);
                final TextView edit_text_year = (TextView) dialogView.findViewById(R.id.textView_correct_year);
                final EditText edit_text_date =(EditText) dialogView.findViewById(R.id.corrct_date);
                final EditText edit_text_reson =(EditText) dialogView.findViewById(R.id.correct_reson);
                final EditText edit_text_name =(EditText) dialogView.findViewById(R.id.correct_name);
                final EditText edit_text_account=(EditText) dialogView.findViewById(R.id.correct_account);
                edit_text_year.setText(recordsChild.get(group).get(child).getDate().substring(0,4));
                edit_text_date.setText(recordsChild.get(group).get(child).getDate().substring(4,8));
                edit_text_reson.setText(recordsChild.get(group).get(child).getReson());
                edit_text_name.setText(recordsChild.get(group).get(child).getName());
                edit_text_account.setText(recordsChild.get(group).get(child).getAccount());
                customizeDialog.setIcon(R.drawable.correct);
                customizeDialog.setTitle("请输入修改内容：");
                customizeDialog.setView(dialogView);
                customizeDialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 获取EditView中的输入内容
                        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                        builder.setIcon(R.drawable.attention);
                        builder.setTitle("输入数据有误");
                        if (edit_text_reson.getText().toString().length() == 0) {
                            builder.setMessage("请输入送礼原因");
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
                            String date=edit_text_year.getText().toString()+edit_text_date.getText().toString();
                            String reson=edit_text_reson.getText().toString();
                            String name=edit_text_name.getText().toString();
                            String account=edit_text_account.getText().toString();
                            correctRecords(recordsChild.get(group).get(child), MainActivity.dataBank.listRecord, name, date, account, reson);
                            MainActivity.dataBank.Save();
                            recordsChild.get(group).get(child).setReson(reson);
                            recordsChild.get(group).get(child).setDate(date);
                            recordsChild.get(group).get(child).setName(name);
                            recordsChild.get(group).get(child).setAccount(account);
                            adaptergive.notifyDataSetChanged();
                            HomeFragment.delet();
                            Toast.makeText(getContext(), "成功修改为：\n" + "日期：" + recordsChild.get(group).get(child).getDate() + "\n类型：" + reson + "\n姓名：" + name + "\n金额：" + account, Toast.LENGTH_SHORT).show();
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
                                recordsGive.remove(recordsChild.get(group).get(child));
                                recordsChild.get(group).remove(child);
                                if(recordsChild.get(group).size()==0){
                                    children.remove(group);
                                    if(children.size()==0)
                                        children.add("暂无随礼记录");
                                }

                                adaptergive.notifyDataSetInvalidated();
                                HomeFragment.delet();
                                Toast.makeText(getContext(),"删除成功！", LENGTH_SHORT).show();
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

    @Override//申请上下文菜单
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        ExpandableListView.ExpandableListContextMenuInfo info =(ExpandableListView.ExpandableListContextMenuInfo) menuInfo;
        int type = ExpandableListView.getPackedPositionType(info.packedPosition);
        super.onCreateContextMenu(menu, v, menuInfo);
        if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD ){
            menu.add(1,1,1,"修改");
            menu.add(1,2,1,"删除");
        }
    }

    //数据加载
    private void initView(View view) {
        addRecord();
        expand_list_id=view.findViewById(R.id.listView_parent);
        adaptergive=new ExpandableListviewAdapter(this,children, recordsChild);
        expand_list_id.setAdapter(adaptergive);
        expand_list_id.expandGroup(0);
        this.registerForContextMenu(expand_list_id);
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
//    public void add(Record record){
//        int i=0;
//        ArrayList<Record> temp = new ArrayList<>();
//        for(i=0;i<children.size();i++){
//            if(record.getYear().equals(children.get(i))){
//                recordsChild.get(i).add(record);
//                break;
//            }
//        }
//        if(i==children.size()){
//            children.add(record.getYear());
//            temp.add(record);
//            recordsChild.add(temp);
//        }
//    }
}

