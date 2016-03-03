package davidcooper.pacecalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by COOPE1_DAVI on 12/24/2015.
 */

public class SecondActivity extends Activity {
    double unformatedtime = 0;
    double decimal = 0;
    double convertedTime;
    String timeString;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondlayout);


        //initiate all the textviews
        TextView display = (TextView) findViewById(R.id.VO2Max);
        TextView label = (TextView) findViewById(R.id.textView3);
        TextView label1 = (TextView) findViewById(R.id.textView4);
       final TextView displayPace = (TextView) findViewById(R.id.pacePerMile);
       final TextView newTime = (TextView) findViewById(R.id.raceTime);
        TextView userTime = (TextView) findViewById(R.id.userEnteredTime);


        //get the extras bundle that was passed from 1st activity
        final String pacePerMile = getIntent().getExtras().getString("pace");
        Double totalSeconds = getIntent().getExtras().getDouble("totalSeconds");
        int minutes = getIntent().getExtras().getInt("minutes");
        int hours = getIntent().getExtras().getInt("hour");
        int seconds = getIntent().getExtras().getInt("seconds");
        final Double Userdistance = getIntent().getExtras().getDouble("distance");

        //Display the users entered time at top of page
       final double totalInMin = totalSeconds/60 ;


        if(hours<1) {
            if (seconds < 10) {
                userTime.setText("Time: " + minutes + ":" + "0" + seconds);
            } else {
                userTime.setText("Time: " + minutes + ":" + seconds);
            }
        }else if(hours>1){
            if (minutes < 10) {
               userTime.setText("Time: " + hours + "0" + minutes + ":" + seconds);
            } else if (seconds < 10) {
               userTime.setText("Time: " + hours + minutes + ":" + "0" + seconds);
            } else if(minutes < 10 && seconds < 10) {
               userTime.setText("Time: " + hours + "0" + minutes + ":" + "0" + seconds);
            }
        }




        //Spinnner to find equivalent race times

        String[] events = {"None Selected","Mile","3k","2 Mile","5k","8k","10k","Half-Marathon","Marathon"};

       final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> list = new  ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, events );
        // Specify the layout to use when the list of choices appears
        list.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(list);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

         //On selection of a distance find the eqivalent time associated with it
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

         //Get item currently selected spinner
              String distanceName = (String) spinner.getSelectedItem().toString();


                if (distanceName.equalsIgnoreCase("5k")) {
                    unformatedtime = totalInMin * Math.pow((3.10685596/ Userdistance), 1.06);
                    decimal = (unformatedtime - Math.floor(unformatedtime)) ;
                } else if (distanceName.equalsIgnoreCase("3k")) {
                    unformatedtime = totalInMin * Math.pow((1.86411358/ Userdistance), 1.07);
                    decimal = (unformatedtime - Math.floor(unformatedtime)) ;
                } else if (distanceName.equalsIgnoreCase("Mile")) {
                    unformatedtime = totalInMin * Math.pow((1.0 /  Userdistance), 1.08);
                    decimal = (unformatedtime - Math.floor(unformatedtime)) ;
                } else if (distanceName.equalsIgnoreCase("10k")) {
                    unformatedtime = totalInMin*(Math.pow((6.21371/Userdistance), 1.06));
                    decimal = (unformatedtime - Math.floor(unformatedtime));
                } else if (distanceName.equalsIgnoreCase("8k")) {
                    unformatedtime = totalInMin * Math.pow((4.97096954 / Userdistance), 1.06);
                    decimal = (unformatedtime - Math.floor(unformatedtime)) ;
                } else if (distanceName.equalsIgnoreCase("Half-Marathon")) {
                    unformatedtime = totalInMin * Math.pow((13.109375 / Userdistance), 1.06);
                    decimal = (unformatedtime - Math.floor(unformatedtime)) ;
                } else if (distanceName.equalsIgnoreCase("Marathon")) {
                    unformatedtime = totalInMin * Math.pow((26.21875 / Userdistance), 1.06);
                    decimal = (unformatedtime - Math.floor(unformatedtime)) ;
                }else if (distanceName.equalsIgnoreCase("2 Mile")) {
                    unformatedtime = totalInMin * Math.pow((2.0 / Userdistance), 1.06);
                    decimal = (unformatedtime - Math.floor(unformatedtime)) ;
                }

                if (unformatedtime > 60) {
                    double convert1 = unformatedtime / 60;   // 2.39
                    double toFormatHour = Math.floor(convert1);  // 2
                    double convert2 = (convert1 - Math.floor(convert1)) * 60;   //23.86644
                    double toFormatMin = Math.floor(convert2); //23
                    double toFormatSec = (convert2 - Math.floor(convert2)) * 60;  //0.519
                    convertedTime = toFormatHour + toFormatMin + toFormatSec;

                    int hour = (int) toFormatHour;
                    int min =  (int) toFormatMin;
                    int seconds = (int) toFormatSec;

                    if(min<10){
                        timeString = hour + ":" + "0"+ min + ":"+ seconds;
                    }else if(seconds<10){
                        timeString = hour + ":" +min + ":"+ "0"+ seconds;
                    }else{
                        timeString = hour + ":" +min + ":"+ seconds;
                    }
                } else if(unformatedtime<60) {
                    int min = (int) (Math.floor(unformatedtime));
                    double sec = decimal * 60;
                    int finalSecond = (int) sec;


                    if (finalSecond < 10) {
                        timeString = min + ":" + "0" + finalSecond;
                    } else {
                        timeString = min + ":" + finalSecond;
                    }

                }

                newTime.setText(timeString);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });















        double velocity = (Userdistance * 1609.34) / totalInMin;

        //Use formula for Vo2max -  top= vo   bottom = % of max
        double top = 0.000104 * Math.pow(velocity, 2) + (0.182258 * (velocity)) - 4.6;
        double bottom = 0.298955 * (Math.exp(-0.1932605 * totalInMin)) + 0.1894393 * (Math.exp(-0.012778 * totalInMin)) + 0.8;
        //Combine to form vo2max
        double VOMax = (top) / (bottom);
        DecimalFormat df = new DecimalFormat("#.##");
        String vo2max= df.format(VOMax);

        //Display both pace per mile and the voMax of that pace/distance
        display.setText("V02 Max: " + vo2max);
        displayPace.setText("Pace: " + pacePerMile );


        /**
        String [] options = {"per mile" , "per kilometer"};
        final Spinner pace = (Spinner) findViewById(R.id.paceOption);
        ArrayAdapter<String> list2 = new  ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, options );
        // Specify the layout to use when the list of choices appears
        list2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        pace.setAdapter(list2);
        pace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {


                String paceOption = (String) pace.getSelectedItem().toString();
                if (paceOption.equalsIgnoreCase("per mile"))
                    displayPace.setText("Pace: " + pacePerMile + " pace per mile");
                else if(paceOption.equalsIgnoreCase("per kilometer"){
                double perkilo = pacePerMile *  0.6213711;
                displayPace.setText("Pace: " + pacePerMile + " pace per mile");
            }

                public void onNothingSelected(AdapterView<?> parentView) {
                    // your code here
                }
        }
         */


    }
}
