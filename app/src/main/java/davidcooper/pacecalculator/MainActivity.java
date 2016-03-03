package davidcooper.pacecalculator;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class MainActivity extends ActionBarActivity {

    int minutes;
    int hour;
    int seconds;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //Edit text field
        final EditText minField = (EditText) findViewById(R.id.minute);
        minField.setFilters(new InputFilter[]{new InputFilterMinMax("0", "59")});
       final EditText hourField = (EditText) findViewById(R.id.hour);
        hourField.setFilters(new InputFilter[]{new InputFilterMinMax("0", "12")});
        final EditText secondField = (EditText) findViewById(R.id.seconds);
        secondField.setFilters(new InputFilter[]{new InputFilterMinMax("0", "59")});
        final EditText distance = (EditText) findViewById(R.id.userDistance);
        RadioButton mile = (RadioButton) findViewById(R.id.milesRadio);
        RadioButton meters = (RadioButton) findViewById(R.id.metersRadio);


        //Textview - Lables

        TextView lable1 = (TextView) findViewById(R.id.textView5);




        //Spinner
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.distance, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);






        Button enter = (Button)findViewById(R.id.button);
        enter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                //Check to make sure everything is filled in and selected.

                if(hourField.getText().toString().matches(""))
                {
                    String nothing = "0";
                    hourField.setText(nothing);
                    hour = Integer.parseInt(hourField.getText().toString());
                }else {
                    hour = Integer.parseInt(hourField.getText().toString());
                }

               if(minField.getText().toString().matches(""))
                {
                    String nothing = "0";
                    minField.setText(nothing);
                    minutes= Integer.parseInt(minField.getText().toString());
                }else {
                    minutes = Integer.parseInt(minField.getText().toString());
                }

                if(secondField.getText().toString().matches(""))
                {
                    String nothing = "0";
                    secondField.setText(nothing);
                    seconds = Integer.parseInt(secondField.getText().toString());
                }else {
                    seconds = Integer.parseInt(secondField.getText().toString());
                }









                String name = (String) spinner.getSelectedItem().toString();
                double distanceInMi = 0;




                if (name.equalsIgnoreCase("3k")) {
                    distanceInMi = 1.86411358;
                }else if (name.equalsIgnoreCase("5k")) {
                    distanceInMi = 3.10685596 ;
                }else if (name.equalsIgnoreCase("2 Mile")) {
                    distanceInMi = 2 ;
                } else if (name.equalsIgnoreCase("10k")) {
                    distanceInMi = 6.21371;
                } else if (name.equalsIgnoreCase("8k")) {
                    distanceInMi = 4.97096954;
                } else if (name.equalsIgnoreCase("Half-Marathon")) {
                    distanceInMi = 13.109375;
                } else if (name.equalsIgnoreCase("Marathon")) {
                    distanceInMi = 26.21875;
                } else if (name.equalsIgnoreCase("None Selected"))
                    distanceInMi = Double.parseDouble(distance.getText().toString());



                int minToSec = minutes*60;
                int hourToSec = (hour*60*60);
                double totalSeconds = minToSec +hourToSec + seconds;

                double pacePerMile = (totalSeconds/distanceInMi)/(60);
                double decimal = ((pacePerMile - Math.floor(pacePerMile))*60)/100;



                double Minute = Math.floor(pacePerMile);


                DecimalFormat df = new DecimalFormat("0.00");
                double pace = (Minute+decimal);



                String finalPace =  df.format(pace);





             //   showtime.setText(finalPace+"pace per mile");

                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                Bundle passBundle = new Bundle();
                passBundle.putString("pace", finalPace);
                passBundle.putDouble("totalSeconds", totalSeconds);
                passBundle.putInt("minutes", minutes);
                passBundle.putInt("seconds", seconds);
                passBundle.putInt("hour", hour);
                passBundle.putDouble("distance",distanceInMi);
                intent.putExtras(passBundle);
                MainActivity.this.startActivity(intent);
            }
        });


    }







    public class InputFilterMinMax implements InputFilter {

        private int min, max;

        public InputFilterMinMax(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public InputFilterMinMax(String min, String max) {
            this.min = Integer.parseInt(min);
            this.max = Integer.parseInt(max);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (isInRange(min, max, input))
                    return null;
            } catch (NumberFormatException nfe) { }
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }









}