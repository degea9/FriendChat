package com.android.friendchat.view.activity;

import com.android.friendchat.R;
import com.android.friendchat.base.BaseActivity;
import com.android.friendchat.data.model.User;
import com.android.friendchat.main.MainActivity;
import com.android.friendchat.presenter.ProfilePresenter;
import com.android.friendchat.utils.CommonUtils;
import com.android.friendchat.view.adapter.GenderAdapter;
import com.android.friendchat.view.interfaces.DatePickerListener;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditProfileActivity extends BaseActivity implements DatePickerListener {
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.profile_gender)
    Spinner mGender;
    @Bind(R.id.profile_birthday)
    TextView mBirthDay;
    @Bind(R.id.first_name)
    EditText mFirstName;
    @Bind(R.id.last_name)
    EditText mLastName;
    private User mUser;

    ProfilePresenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
        mUser = new User();
        mPresenter = new ProfilePresenter();
        setSupportActionBar(mToolbar);
        setUpSpinner();
    }
    private void setUpSpinner() {
        int[] flags = {R.drawable.ic_male,R.drawable.ic_female};
        String[] genders = getResources().getStringArray(R.array.genders);
        GenderAdapter genderAdapter=new GenderAdapter(getApplicationContext(),flags,genders);
        mGender.setAdapter(genderAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.save_profile) {
            boolean isMale = mGender.getSelectedItemPosition()==0 ? true:false;
            String firstName = mFirstName.getText().toString().trim();
            String lastName = mLastName.getText().toString().trim();
            mUser.setIsMale(isMale);
            mUser.setFirstName(firstName);
            mUser.setLastName(lastName);
            mPresenter.saveProfile(mUser);
            navigateTo(MainActivity.class);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.profile_birthday)
    public void changeBirthDay(){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void returnDate(String date) {
        mBirthDay.setText(date);
        mUser.setBirthDay(date);
    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        private DatePickerListener mListener;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            if(getActivity() instanceof DatePickerListener) {
                mListener = (DatePickerListener) getActivity();
                mListener.returnDate(CommonUtils.getDateString(year, month, day));
            }
        }
    }

}

