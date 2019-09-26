package p185296_m203380.ft.unicamp.trip_helper;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;

public class UserRegisterActivity extends AppCompatActivity {

    @BindView(R.id.username)
    EditText name;

    @BindView(R.id.user_age)
    EditText age;

    @BindView(R.id.user_calendar)
    CalendarView birthDate;

    @BindView(R.id.user_radio_male)
    RadioGroup sexMale;

    @BindView(R.id.user_radio_female)
    RadioGroup sexFemale;

    @BindView(R.id.user_radio_others)
    RadioGroup sexOthers;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_register);
    }
}
