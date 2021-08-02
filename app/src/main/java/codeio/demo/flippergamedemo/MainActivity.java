package codeio.demo.flippergamedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int firstSelected = 0;
    private int secondSelected = 0;
    private int scoreMonitor = 0;

    private int[] titleImages = {R.drawable.java, R.drawable.chash, R.drawable.cplusplus, R.drawable.fortan, R.drawable.javascript, R.drawable.php, R.drawable.python, R.drawable.ruby, R.drawable.java, R.drawable.chash, R.drawable.cplusplus, R.drawable.fortan, R.drawable.javascript, R.drawable.php, R.drawable.python, R.drawable.ruby};
    private int[] buttonId = {R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.button10, R.id.button11, R.id.button12, R.id.button13, R.id.button14, R.id.button15, R.id.button16};

    private TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        score = findViewById(R.id.score);
    }

    public void data(View view) {
        for (int i=0; i<16; i++) {
            if (buttonId[i] == view.getId()) {
                manageButtonClicks(i + 1);
                return;
            }
        }
    }

    public void manageButtonClicks(int num) {

        if (firstSelected == num || scoreMonitor == 8) {
            return;
        }

        if (firstSelected == 0) {
            firstSelected = num;
            openOrCloseButton(num, true);
            return;
        }
        secondSelected = num;

        openOrCloseButton(num, true);

        verifySelected();
    }

    public void verifySelected() {
        if (titleImages[firstSelected - 1] == titleImages[secondSelected - 1]) {
            scoreMonitor += 1;

            findViewById(buttonId[firstSelected - 1]).setEnabled(false);
            findViewById(buttonId[secondSelected - 1]).setEnabled(false);

            buttonId[firstSelected - 1] = 0;
            buttonId[secondSelected - 1] = 0;

            displayScore();

            resetValues();
        } else {
            disableOrEnableButtons(false);
            new WaitTask().execute();
        }
    }

    private void resetValues() {
        firstSelected = 0;
        secondSelected = 0;
    }

    private void closeOpened() {
        openOrCloseButton(firstSelected, false);
        openOrCloseButton(secondSelected, false);
        resetValues();
    }

    public void displayScore() {
        score.setText(String.valueOf(scoreMonitor));
    }

    public void disableOrEnableButtons(boolean flag) {
        for (int i=0; i<16; i++) {
            if (buttonId[i] != 0) {
                findViewById(buttonId[i]).setEnabled(flag);
            }
        }
    }

    public void openOrCloseButton(int position, boolean toOpen) {
        ImageButton button = findViewById(buttonId[position - 1]);
        if (toOpen) {
            button.setImageDrawable(getResources().getDrawable(titleImages[position - 1]));
            return;
        }
        button.setImageDrawable(getResources().getDrawable(R.drawable.codeio));
    }

    public class WaitTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            closeOpened();
            disableOrEnableButtons(true);
        }
    }
}