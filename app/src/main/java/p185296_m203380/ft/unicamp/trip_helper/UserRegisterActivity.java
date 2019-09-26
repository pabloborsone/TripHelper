package p185296_m203380.ft.unicamp.trip_helper;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserRegisterActivity extends AppCompatActivity {

    private String userBirthDate;
    private String userSex;

    @BindView(R.id.username)
    EditText name;

    @BindView(R.id.user_age)
    EditText age;

    @BindView(R.id.user_calendar)
    CalendarView birthDate;

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    @BindView(R.id.user_radio_male)
    RadioButton sexMale;

    @BindView(R.id.user_radio_female)
    RadioButton sexFemale;

    @BindView(R.id.user_radio_others)
    RadioButton sexOthers;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        ButterKnife.bind(this);

        addAllListeners();
    }

    private void init() {
        SharedPreferences.Editor editor = getSharedPreferences(Constants.USER_INFO, MODE_PRIVATE).edit();
        editor.putString(Constants.USERNAME, name.getText().toString());
        editor.putString(Constants.USER_AGE, age.getText().toString());
        editor.putString(Constants.USER_BIRTH_DAY, userBirthDate);
        editor.putString(Constants.USER_SEX, userSex);
        editor.apply();
    }

    @OnClick(R.id.user_continue_button)
    public void onContinueButtonClick() {
        init();
    }

    private void addAllListeners() {
        birthDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                userBirthDate = new GregorianCalendar(day, month, year).toString();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.user_radio_male) {
                    userSex = sexMale.getText().toString();
                } else if (i == R.id.user_radio_female) {
                    userSex = sexFemale.getText().toString();
                } else if (i == R.id.user_radio_others){
                    userSex = sexOthers.getText().toString();
                }
            }
        });
    }
}
