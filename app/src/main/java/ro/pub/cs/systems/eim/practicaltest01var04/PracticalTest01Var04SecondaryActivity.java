package ro.pub.cs.systems.eim.practicaltest01var04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PracticalTest01Var04SecondaryActivity extends AppCompatActivity {
    Button okButton;
    Button cancelButton;
    EditText studentName;
    EditText studentGroup;

    class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (view == okButton) {
                setResult(RESULT_OK, null);
            } else if (view == cancelButton) {
                setResult(RESULT_CANCELED, null);
            }
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var04_secondary);

        okButton = findViewById(R.id.okButton);
        cancelButton = findViewById(R.id.cancelButton);
        studentName = findViewById(R.id.secondaryActivityStudentName);
        studentGroup = findViewById(R.id.secondaryActivityStudentGroup);

        okButton.setOnClickListener(new ButtonListener());
        cancelButton.setOnClickListener(new ButtonListener());

        Intent intent = getIntent();
        if (intent != null) {
            String studentNameText = intent.getStringExtra(Constants.studentName);
            String studentGroupText = intent.getStringExtra(Constants.studentGroup);
            studentName.setText(studentNameText);
            studentGroup.setText(studentGroupText);
        }
    }
}