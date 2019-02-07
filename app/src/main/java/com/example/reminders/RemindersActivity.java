package com.example.reminders;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.widget.Toast;


public class RemindersActivity extends AppCompatActivity {
    private ListView mListview;
    private RemindersDbAdapter mDbAdapter;
    private RemindersSimpleCursorAdapter mCursorAdapter;


    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);


        mListview = (ListView) findViewById(R.id.reminders_list_view);
        mListview.setDivider(null);
        mDbAdapter = new RemindersDbAdapter(this);
        mDbAdapter.open();
        if (savedInstanceState == null){
            //clear all data
            mDbAdapter.deleteAllReminders();
            //add some data
            insertSomeReminders();
        }

        Cursor cursor = mDbAdapter.fetchAllReminders();

        //from columns definded in the db
        String[] from = new String[]{
                RemindersDbAdapter.COL_CONTENT
        };

        //to the ids of views in the layout
        int[] to = new int[]{
                R.id.row_text
        };

        mCursorAdapter = new RemindersSimpleCursorAdapter(
                //context
                RemindersActivity.this,
                //the layout of the row
                R.layout.reminders_row,
                //cursor
                cursor,
                //from columns defined in the db
                from,
                //to the ids of views in the layout
                to,
                //flag - not used
                0
        );

        //the cursorAdapter (controller) is now updating the listview (view)
        //with data from the db (model)
        mListview.setAdapter(mCursorAdapter);

        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int masterListPosition, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RemindersActivity.this);
                ListView modeListView = new ListView(RemindersActivity.this);
                String[] modes = new String[] {"Edit Reminder", "Delete Reminder"};
                ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(RemindersActivity.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, modes);
                modeListView.setAdapter(modeAdapter);
                builder.setView(modeListView);
                final Dialog dialog = builder.create();
                dialog.show();
                modeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //edit reminder
                        if(position == 0) {
                            Toast.makeText(RemindersActivity.this, "edit"
                            + masterListPosition, Toast.LENGTH_SHORT).show();
                            //delete reminder
                        } else {
                            Toast.makeText(RemindersActivity.this, "delete"
                            + masterListPosition, Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
            }
        });

        // this will only work on version higher than or equal to the Honeycomb sdk version 11
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            //below is the code logic to handle multiSelection on a listview
            //whenever you long press on an item the onCreatActionMode is invoked and a menu is inflated
            mListview.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                @Override
                public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

                }

                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    MenuInflater Inflater = mode.getMenuInflater();
                    Inflater.inflate(R.menu.cam_menu, menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }
                //once other items are clicked the onActionItemClicked method is invoked and then the item is then
                //deleted from the list
                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.menu_item_delete_reminder:
                            for (int nC = mCursorAdapter.getCount()-1; nC >= 0; nC--){
                                if (mListview.isItemChecked(nC)){
                                    mDbAdapter.deleteReminderById(getIdFromPosition(nC));
                                }
                            }
                            mode.finish();
                            mCursorAdapter.changeCursor(mDbAdapter.fetchAllReminders());
                            return true;
                    }
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                }
            });
        }

    }

    private int getIdFromPosition(int nC) {
        return (int)mCursorAdapter.getItemId(nC);
    }

    private void insertSomeReminders() {
        mDbAdapter.createReminder("Buy some food stuffs for princess", true);
        mDbAdapter.createReminder("send some birthday gift to mummy", false);
        mDbAdapter.createReminder("Dinner at the dinner with my girlfriend", false);
        mDbAdapter.createReminder("Buy some food stuffs for princess", true);
        mDbAdapter.createReminder("send some birthday gift to mummy", false);
        mDbAdapter.createReminder("Dinner at the dinner with my girlfriend", false);
        mDbAdapter.createReminder("Buy some food stuffs for princess", true);
        mDbAdapter.createReminder("send some birthday gift to mummy", false);
        mDbAdapter.createReminder("Dinner at the dinner with my girlfriend", false);
        mDbAdapter.createReminder("Buy some food stuffs for princess", true);
        mDbAdapter.createReminder("send some birthday gift to mummy", false);
        mDbAdapter.createReminder("Dinner at the dinner with my girlfriend", false);
        mDbAdapter.createReminder("Buy some food stuffs for princess", true);
        mDbAdapter.createReminder("send some birthday gift to mummy", false);
        mDbAdapter.createReminder("Dinner at the dinner with my girlfriend", false);
        mDbAdapter.createReminder("Buy some food stuffs for princess", true);
        mDbAdapter.createReminder("send some birthday gift to mummy", false);
        mDbAdapter.createReminder("Dinner at the dinner with my girlfriend", false);
        mDbAdapter.createReminder("Buy some food stuffs for princess", true);
        mDbAdapter.createReminder("send some birthday gift to mummy", false);
        mDbAdapter.createReminder("Dinner at the dinner with my girlfriend", false);
    }
    private void fireCustomDialog(final Reminder reminder){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reminders, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.action_new:
                //create new reminder
                Log.d(getLocalClassName(),"create new reminder");
                return true;
            case R.id.action_exit:
                finish();
                return true;
                default:
                    return false;
        }
    }


}
