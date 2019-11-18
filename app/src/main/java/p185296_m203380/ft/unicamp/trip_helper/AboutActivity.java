package p185296_m203380.ft.unicamp.trip_helper;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.about_text)
    public void onTextClick() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(getText(R.string.github_text).toString()));
        startActivity(browserIntent);
    }

    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finishAfterTransition();
    }
}
