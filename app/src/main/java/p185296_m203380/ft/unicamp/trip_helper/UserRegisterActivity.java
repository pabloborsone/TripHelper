package p185296_m203380.ft.unicamp.trip_helper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserRegisterActivity extends AppCompatActivity {

    private String userSex;
    private boolean alreadyTraveledAbroad = false;

    @BindView(R.id.username)
    EditText name;

    @BindView(R.id.user_birth_date)
    EditText birthDate;

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    @BindView(R.id.user_radio_male)
    RadioButton sexMale;

    @BindView(R.id.user_radio_female)
    RadioButton sexFemale;

    @BindView(R.id.user_radio_others)
    RadioButton sexOthers;

    @BindView(R.id.user_already_traveled)
    CheckBox checkBox;

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
        editor.putString(Constants.USER_BIRTH_DAY, birthDate.getText().toString());
        editor.putString(Constants.USER_SEX, userSex);
        editor.putBoolean(Constants.USER_TRAVELED_ABROAD, alreadyTraveledAbroad);
        editor.apply();
    }

    @OnClick(R.id.user_continue_button)
    public void onContinueButtonClick() {
        init();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finishAfterTransition();
    }

    private void addAllListeners() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.user_radio_male) {
                    userSex = sexMale.getText().toString();
                } else if (i == R.id.user_radio_female) {
                    userSex = sexFemale.getText().toString();
                } else if (i == R.id.user_radio_others) {
                    userSex = sexOthers.getText().toString();
                }
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                alreadyTraveledAbroad = b;
            }
        });
    }
}
