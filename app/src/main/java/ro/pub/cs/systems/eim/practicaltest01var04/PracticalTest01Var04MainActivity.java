package ro.pub.cs.systems.eim.practicaltest01var04;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;

public class PracticalTest01Var04MainActivity extends AppCompatActivity {
    Button navigateToSecondaryActivityButton;
    Button displayInformationButton;
    CheckBox studentCheckbox;
    CheckBox groupCheckbox;
    EditText studentText;
    EditText groupText;
    EditText informationText;

    class MessageBroadcastReceiver extends BroadcastReceiver {

            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals(Constants.studentNameAction)) {
                    String studentName = intent.getStringExtra(Constants.studentName);
                    Log.d("PracticalTest01Var04", "studentName: " + studentName);
                } else if (action.equals(Constants.studentGroupAction)) {
                    String studentGroup = intent.getStringExtra(Constants.studentGroup);
                    Log.d("PracticalTest01Var04", "studentGroup: " + studentGroup);
                }
            }
    }

    MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    IntentFilter intentFilter = new IntentFilter();

    class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (view == displayInformationButton) {
                String information = "";
                if (studentCheckbox.isChecked()) {
                    String studentName = studentText.getText().toString();
                    if (studentName.isEmpty()) {
                        Toast.makeText(PracticalTest01Var04MainActivity.this, "Student name is checked but is empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    information += studentName + " ";
                }
                if (groupCheckbox.isChecked()) {
                    String groupName = groupText.getText().toString();
                    if (groupName.isEmpty()) {
                        Toast.makeText(PracticalTest01Var04MainActivity.this, "Group name is checkd but it is empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    information += groupName;
                }
                informationText.setText(information);
                Intent serviceIntent = new Intent(getApplicationContext(), PracticalTest01Var04Service.class);
                serviceIntent.putExtra(Constants.studentName, studentText.getText().toString());
                serviceIntent.putExtra(Constants.studentGroup, groupText.getText().toString());
                getApplicationContext().startService(serviceIntent);
            } else if (view == navigateToSecondaryActivityButton) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var04SecondaryActivity.class);
                intent.putExtra(Constants.studentName, studentText.getText().toString());
                intent.putExtra(Constants.studentGroup, groupText.getText().toString());
                startActivityForResult(intent, Constants.navigateToSecondary);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.navigateToSecondary) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Activity returned with result OK", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Activity returned with result CANCEL", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var04_main);

        navigateToSecondaryActivityButton = findViewById(R.id.navigateToSecondaryActivityButton);
        displayInformationButton = findViewById(R.id.displayInformationButton);
        studentCheckbox = findViewById(R.id.studentCheckbox);
        groupCheckbox = findViewById(R.id.groupCheckbox);
        studentText = findViewById(R.id.studentText);
        groupText = findViewById(R.id.groupText);
        informationText = findViewById(R.id.informationText);

        Arrays.asList(navigateToSecondaryActivityButton, displayInformationButton).forEach(button -> button.setOnClickListener(new ButtonListener()));
        intentFilter.addAction(Constants.studentNameAction);
        intentFilter.addAction(Constants.studentGroupAction);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(Constants.studentGroup)) {
            groupText.setText(savedInstanceState.getString(Constants.studentGroup));
        }
        if (savedInstanceState.containsKey(Constants.studentName)) {
            studentText.setText(savedInstanceState.getString(Constants.studentName));
        }
        if (savedInstanceState.containsKey(Constants.allInformation)) {
            informationText.setText(savedInstanceState.getString(Constants.allInformation));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(messageBroadcastReceiver);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.studentGroup, groupText.getText().toString());
        outState.putString(Constants.studentName, studentText.getText().toString());
        outState.putString(Constants.allInformation, informationText.getText().toString());
    }
}