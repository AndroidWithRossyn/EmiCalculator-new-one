package com.example.emicalculator_2.Calculations;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.emicalculator_2.R;
import java.util.ArrayList;
import java.util.List;

/* compiled from: MyHistoryList */
class MyListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    /* access modifiers changed from: private */
    public String contextname;
    /* access modifiers changed from: private */
    public List<String> historyDate = new ArrayList();
    /* access modifiers changed from: private */
    public List<Double> historyPrincipal = new ArrayList();
    /* access modifiers changed from: private */
    public List<Integer> id = new ArrayList();
    /* access modifiers changed from: private */
    public List<String> maintitle = new ArrayList();
    SQLiteDatabase myDatabase;
    /* access modifiers changed from: private */
    public List<Double> time = new ArrayList();
    /* access modifiers changed from: private */
    public List<Double> withdrawal = new ArrayList();

    public MyListAdapter(Activity context2, List<String> maintitle2, List<Double> historyPrincipal2, List<String> historyDate2, List<Integer> id2, List<Double> time2, List<Double> withdrawal2) {
        super(context2, R.layout.history_element, maintitle2);
        this.context = context2;
        String localClassName = context2.getLocalClassName();
        this.contextname = localClassName;
        Toast.makeText(context2, localClassName, 1).show();
        this.maintitle = maintitle2;
        this.historyPrincipal = historyPrincipal2;
        this.historyDate = historyDate2;
        this.id = id2;
        this.time = time2;
        if (this.contextname.equals("Calculations.Swp")) {
            this.withdrawal = withdrawal2;
        }
    }

    public View getView(final int position, View view, ViewGroup parent) {
        View rowView = this.context.getLayoutInflater().inflate(R.layout.history_element, (ViewGroup) null, true);
        TextView titleText = (TextView) rowView.findViewById(R.id.nameid);
        TextView principal = (TextView) rowView.findViewById(R.id.historyPrincipal);
        TextView date = (TextView) rowView.findViewById(R.id.historyDate);
        this.myDatabase = getContext().openOrCreateDatabase("EMI", 0, (SQLiteDatabase.CursorFactory) null);
        if (this.contextname.equals("Calculations.emi")) {
            this.myDatabase.execSQL("CREATE TABLE IF NOT EXISTS emiTable(name TEXT,principalAmount DOUBLE,interest DOUBLE,tenure DOUBLE,date TEXT,id INTEGER PRIMARY KEY)");
        } else if (this.contextname.equals("Calculations.Fd")) {
            this.myDatabase.execSQL("CREATE TABLE IF NOT EXISTS fdTable(name TEXT,principalAmount DOUBLE,interest DOUBLE,tenure DOUBLE,date TEXT,id INTEGER PRIMARY KEY)");
        } else if (this.contextname.equals("Calculations.Rd")) {
            this.myDatabase.execSQL("CREATE TABLE IF NOT EXISTS rdTable(name TEXT,principalAmount DOUBLE,interest DOUBLE,tenure DOUBLE,date TEXT,id INTEGER PRIMARY KEY)");
        } else if (this.contextname.equals("Calculations.ppf")) {
            this.myDatabase.execSQL("CREATE TABLE IF NOT EXISTS ppfTable(name TEXT,principalAmount DOUBLE,interest DOUBLE,tenure DOUBLE,date TEXT,id INTEGER PRIMARY KEY)");
        } else if (this.contextname.equals("Calculations.lumpsum")) {
            this.myDatabase.execSQL("CREATE TABLE IF NOT EXISTS lumpsumTable(name TEXT,principalAmount DOUBLE,interest DOUBLE,tenure DOUBLE,date TEXT,id INTEGER PRIMARY KEY)");
        } else if (this.contextname.equals("Calculations.saving")) {
            this.myDatabase.execSQL("CREATE TABLE IF NOT EXISTS savingTable(name TEXT,principalAmount DOUBLE,interest DOUBLE,tenure DOUBLE,date TEXT,id INTEGER PRIMARY KEY)");
        } else if (this.contextname.equals("Calculations.Sip")) {
            this.myDatabase.execSQL("CREATE TABLE IF NOT EXISTS sipTable(name TEXT,principalAmount DOUBLE,interest DOUBLE,tenure DOUBLE,date TEXT,id INTEGER PRIMARY KEY)");
        } else {
            this.myDatabase.execSQL("CREATE TABLE IF NOT EXISTS swpTable(name TEXT,principalAmount DOUBLE,interest DOUBLE,tenure DOUBLE,date TEXT,id INTEGER PRIMARY KEY)");
        }
        ((ImageButton) rowView.findViewById(R.id.delete)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (MyListAdapter.this.contextname.equals("Calculations.emi")) {
                    SQLiteDatabase sQLiteDatabase = MyListAdapter.this.myDatabase;
                    sQLiteDatabase.execSQL("DELETE FROM emiTable WHERE id=" + MyListAdapter.this.id.get(position));
                } else if (MyListAdapter.this.contextname.equals("Calculations.Fd")) {
                    SQLiteDatabase sQLiteDatabase2 = MyListAdapter.this.myDatabase;
                    sQLiteDatabase2.execSQL("DELETE FROM fdTable WHERE id=" + MyListAdapter.this.id.get(position));
                } else if (MyListAdapter.this.contextname.equals("Calculations.Rd")) {
                    SQLiteDatabase sQLiteDatabase3 = MyListAdapter.this.myDatabase;
                    sQLiteDatabase3.execSQL("DELETE FROM rdTable WHERE id=" + MyListAdapter.this.id.get(position));
                } else if (MyListAdapter.this.contextname.equals("Calculations.ppf")) {
                    SQLiteDatabase sQLiteDatabase4 = MyListAdapter.this.myDatabase;
                    sQLiteDatabase4.execSQL("DELETE FROM ppfTable WHERE id=" + MyListAdapter.this.id.get(position));
                } else if (MyListAdapter.this.contextname.equals("Calculations.lumpsum")) {
                    SQLiteDatabase sQLiteDatabase5 = MyListAdapter.this.myDatabase;
                    sQLiteDatabase5.execSQL("DELETE FROM lumpsumTable WHERE id=" + MyListAdapter.this.id.get(position));
                } else if (MyListAdapter.this.contextname.equals("Calculations.saving")) {
                    SQLiteDatabase sQLiteDatabase6 = MyListAdapter.this.myDatabase;
                    sQLiteDatabase6.execSQL("DELETE FROM savingTable WHERE id=" + MyListAdapter.this.id.get(position));
                } else if (MyListAdapter.this.contextname.equals("Calculations.Sip")) {
                    SQLiteDatabase sQLiteDatabase7 = MyListAdapter.this.myDatabase;
                    sQLiteDatabase7.execSQL("DELETE FROM sipTable WHERE id=" + MyListAdapter.this.id.get(position));
                } else {
                    SQLiteDatabase sQLiteDatabase8 = MyListAdapter.this.myDatabase;
                    sQLiteDatabase8.execSQL("DELETE FROM swpTable WHERE id=" + MyListAdapter.this.id.get(position));
                    MyListAdapter.this.withdrawal.remove(position);
                }
                MyListAdapter.this.id.remove(position);
                MyListAdapter.this.time.remove(position);
                MyListAdapter.this.historyPrincipal.remove(position);
                MyListAdapter.this.maintitle.remove(position);
                MyListAdapter.this.historyDate.remove(position);
                MyListAdapter.this.notifyDataSetChanged();
            }
        });
        ((Button) rowView.findViewById(R.id.apply)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i;
                if (MyListAdapter.this.contextname.equals("Calculations.emi")) {
                    i = new Intent(MyListAdapter.this.getContext(), emi.class);
                } else if (MyListAdapter.this.contextname.equals("Calculations.Fd")) {
                    i = new Intent(MyListAdapter.this.getContext(), Fd.class);
                } else if (MyListAdapter.this.contextname.equals("Calculations.Rd")) {
                    i = new Intent(MyListAdapter.this.getContext(), Rd.class);
                } else if (MyListAdapter.this.contextname.equals("Calculations.ppf")) {
                    i = new Intent(MyListAdapter.this.getContext(), ppf.class);
                } else if (MyListAdapter.this.contextname.equals("Calculations.saving")) {
                    i = new Intent(MyListAdapter.this.getContext(), saving.class);
                } else if (MyListAdapter.this.contextname.equals("Calculations.Sip")) {
                    i = new Intent(MyListAdapter.this.getContext(), Sip.class);
                } else if (MyListAdapter.this.contextname.equals("Calculations.lumpsum")) {
                    i = new Intent(MyListAdapter.this.getContext(), lumpsum.class);
                } else {
                    i = new Intent(MyListAdapter.this.getContext(), Swp.class);
                }
                i.putExtra("Open", String.valueOf(position));
                MyListAdapter.this.getContext().startActivity(i);
            }
        });
        titleText.setText(this.maintitle.get(position));
        principal.setText("Principal : ₹" + this.historyPrincipal.get(position));
        date.setText("First EMI: " + this.historyDate.get(position));
        return rowView;
    }
}
