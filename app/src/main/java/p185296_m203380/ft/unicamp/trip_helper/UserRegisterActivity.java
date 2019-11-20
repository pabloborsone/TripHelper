package p185296_m203380.ft.unicamp.trip_helper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserRegisterActivity extends AppCompatActivity {

    private DatabaseReference db;

    @BindView(R.id.username)
    EditText name;

    @BindView(R.id.user_birth_date)
    EditText birthDate;

    @BindView(R.id.user_email_input)
    EditText userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        ButterKnife.bind(this);

        if (hasRegister()) {
            alreadyRegistered();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        birthDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 2 || editable.length() == 5) {
                    editable.append('/');
                }
            }
        });
    }

    private void init() {
        SharedPreferences.Editor editor = getSharedPreferences(Constants.USER_INFO, MODE_PRIVATE).edit();
        editor.putString(Constants.USERNAME, name.getText().toString());
        editor.putString(Constants.USER_BIRTH_DAY, birthDate.getText().toString());
        editor.putString(Constants.USER_EMAIL, userEmail.getText().toString());
        editor.apply();
    }

    private void initFirebaseDb() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        db = database.getReference(Constants.USERNAME);
        db.setValue(name.getText().toString());
        db = database.getReference(Constants.USER_BIRTH_DAY);
        db.setValue(birthDate.getText().toString());
        db = database.getReference(Constants.USER_EMAIL);
        db.setValue(userEmail.getText().toString());
    }

    @OnClick(R.id.user_continue_button)
    public void onContinueButtonClick() {
        init();
        initFirebaseDb();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finishAfterTransition();
    }

    private void alreadyRegistered() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        db = database.getReference();
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userName = Objects.requireNonNull(dataSnapshot.child(Constants.USERNAME).getValue()).toString();
                String birth = Objects.requireNonNull(dataSnapshot.child(Constants.USER_BIRTH_DAY).getValue()).toString();
                String email = Objects.requireNonNull(dataSnapshot.child(Constants.USER_EMAIL).getValue()).toString();

                name.setText(userName);
                birthDate.setText(birth);
                userEmail.setText(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getBaseContext(), "Value cannot be found", Toast.LENGTH_LONG).show();
            }
        });
        if (!isOnline()) {
            name.setText(getSharedPreferences(Constants.USER_INFO, MODE_PRIVATE).getString(Constants.USERNAME, null));
            birthDate.setText(getSharedPreferences(Constants.USER_INFO, MODE_PRIVATE).getString(Constants.USER_BIRTH_DAY, null));
            userEmail.setText(getSharedPreferences(Constants.USER_INFO, MODE_PRIVATE).getString(Constants.USER_EMAIL, null));
        }
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

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
