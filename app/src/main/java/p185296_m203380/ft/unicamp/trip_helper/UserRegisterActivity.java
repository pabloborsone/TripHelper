package p185296_m203380.ft.unicamp.trip_helper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

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

    @BindView(R.id.user_sex)
    TextView userSexTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        ButterKnife.bind(this);

        addAllListeners();
        if (hasRegister()) {
            alreadyRegistered();
        }
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
        radioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == R.id.user_radio_male) {
                userSex = sexMale.getText().toString();
            } else if (i == R.id.user_radio_female) {
                userSex = sexFemale.getText().toString();
            } else if (i == R.id.user_radio_others) {
                userSex = sexOthers.getText().toString();
            }
        });
        checkBox.setOnCheckedChangeListener((compoundButton, b) -> alreadyTraveledAbroad = b);
    }

    private void alreadyRegistered() {
        radioGroup.setVisibility(View.GONE);
        userSexTextView.setVisibility(View.VISIBLE);
        userSexTextView.setText(getSharedPreferences(Constants.USER_INFO, MODE_PRIVATE).getString(Constants.USER_SEX, null));
        checkBox.setChecked(getSharedPreferences(Constants.USER_INFO, MODE_PRIVATE).getBoolean(Constants.USER_TRAVELED_ABROAD, false));
        name.setText(getSharedPreferences(Constants.USER_INFO, MODE_PRIVATE).getString(Constants.USERNAME, null));
        birthDate.setText(getSharedPreferences(Constants.USER_INFO, MODE_PRIVATE).getString(Constants.USER_BIRTH_DAY, null));
    }

    private boolean hasRegister() {
        return (!(getSharedPreferences(Constants.USER_INFO, MODE_PRIVATE).getString(Constants.USERNAME, null) == null));
    }

    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finishAfterTransition();
    }
}
