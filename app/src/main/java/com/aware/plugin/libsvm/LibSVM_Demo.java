package com.aware.plugin.libsvm;

import android.app.Activity;
import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aware.Accelerometer;
import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.providers.Accelerometer_Provider;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.berkeley.compbio.jlibsvm.kernel.KernelFunction;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.BinarySparseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;

/**
 * Created by denzil on 9/6/15.
 */
public class LibSVM_Demo extends Activity {

    private static TextView debug;
    private static weka.classifiers.Classifier classifier;

    public static final String ACTION_UPDATE_UI = "ACTION_UPDATE_UI";

    private TapDetector tapDetector = new TapDetector();
    public class TapDetector extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if( intent.getAction().equals(Accelerometer.ACTION_AWARE_ACCELEROMETER) ) {
                Intent classify = new Intent(context, Classifier.class);
                classify.putParcelableArrayListExtra("data", intent.getParcelableExtra(Accelerometer.EXTRA_DATA));
                context.startService(classify);
            }
            if( intent.getAction().equals(ACTION_UPDATE_UI) ) {
                if( debug != null ) {
                    debug.setText(intent.getStringExtra("class") + ": " + intent.getStringExtra("probability"));
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.libsvm_ui);

        debug = (TextView) findViewById(R.id.debug);

        InputStream io = getResources().openRawResource(R.raw.tap_randomforest);
        try{
            classifier = (weka.classifiers.Classifier) SerializationHelper.read(io);
        }catch( Exception e ) {
            //NOTHING WRONG
        }

        IntentFilter filter = new IntentFilter(Accelerometer.ACTION_AWARE_ACCELEROMETER);
        registerReceiver(tapDetector, filter);

        Aware.setSetting(this, Aware_Preferences.STATUS_ACCELEROMETER, true);
        Aware.setSetting(this, Aware_Preferences.FREQUENCY_ACCELEROMETER, 0);
        sendBroadcast(new Intent(Aware.ACTION_AWARE_REFRESH));
    }

    public static class Classifier extends IntentService {
        public Classifier() {
            super("Tap Classifier");
        }

        @Override
        protected void onHandleIntent(Intent intent) {

            FastVector attributes = new FastVector();
            Attribute x = new Attribute("double_values_0");
            attributes.addElement(x);
            Attribute y = new Attribute("double_values_1");
            attributes.addElement(y);
            Attribute z = new Attribute("double_values_2");
            attributes.addElement(z);
            Attribute vector = new Attribute("vector");
            attributes.addElement(vector);
            Attribute sum = new Attribute("sum");
            attributes.addElement(sum);
            Attribute mean = new Attribute("mean");
            attributes.addElement(mean);
            Attribute median = new Attribute("median");
            attributes.addElement(median);
            Attribute sd = new Attribute("sd");
            attributes.addElement(sd);
            Attribute min = new Attribute("min");
            attributes.addElement(min);
            Attribute max = new Attribute("max");
            attributes.addElement(max);
            Attribute range = new Attribute("range");
            attributes.addElement(range);
            Attribute Q0 = new Attribute("Q0");
            attributes.addElement(Q0);
            Attribute Q25 = new Attribute("Q25");
            attributes.addElement(Q25);
            Attribute Q50 = new Attribute("Q50");
            attributes.addElement(Q50);
            Attribute Q75 = new Attribute("Q75");
            attributes.addElement(Q75);
            Attribute Q100 = new Attribute("Q100");
            attributes.addElement(Q100);
            Attribute avg_x = new Attribute("avg_x");
            attributes.addElement(avg_x);
            Attribute avg_y = new Attribute("avg_y");
            attributes.addElement(avg_y);
            Attribute avg_z = new Attribute("avg_z");
            attributes.addElement(avg_z);
            Attribute std_x = new Attribute("std_x");
            attributes.addElement(std_x);
            Attribute std_y = new Attribute("std_y");
            attributes.addElement(std_y);
            Attribute std_z = new Attribute("std_z");
            attributes.addElement(std_z);
            Attribute min_x = new Attribute("min_x");
            attributes.addElement(min_x);
            Attribute min_y = new Attribute("min_y");
            attributes.addElement(min_y);
            Attribute min_z = new Attribute("min_z");
            attributes.addElement(min_z);
            Attribute max_x = new Attribute("max_x");
            attributes.addElement(max_x);
            Attribute max_y = new Attribute("max_y");
            attributes.addElement(max_y);
            Attribute max_z = new Attribute("max_z");
            attributes.addElement(max_z);
            Attribute range_x = new Attribute("range_x");
            attributes.addElement(range_x);
            Attribute range_y = new Attribute("range_y");
            attributes.addElement(range_y);
            Attribute range_z = new Attribute("range_z");
            attributes.addElement(range_z);
            Attribute Q0_x = new Attribute("Q0_x");
            attributes.addElement(Q0_x);
            Attribute Q25_x = new Attribute("Q25_x");
            attributes.addElement(Q25_x);
            Attribute Q50_x = new Attribute("Q50_x");
            attributes.addElement(Q50_x);
            Attribute Q75_x = new Attribute("Q75_x");
            attributes.addElement(Q75_x);
            Attribute Q100_x = new Attribute("Q100_x");
            attributes.addElement(Q100_x);
            Attribute Q0_y = new Attribute("Q0_y");
            attributes.addElement(Q0_y);
            Attribute Q25_y = new Attribute("Q25_y");
            attributes.addElement(Q25_y);
            Attribute Q50_y = new Attribute("Q50_y");
            attributes.addElement(Q50_y);
            Attribute Q75_y = new Attribute("Q75_y");
            attributes.addElement(Q75_y);
            Attribute Q100_y = new Attribute("Q100_y");
            attributes.addElement(Q100_y);
            Attribute Q0_z = new Attribute("Q0_z");
            attributes.addElement(Q0_z);
            Attribute Q25_z = new Attribute("Q25_z");
            attributes.addElement(Q25_z);
            Attribute Q50_z = new Attribute("Q50_z");
            attributes.addElement(Q50_z);
            Attribute Q75_z = new Attribute("Q75_z");
            attributes.addElement(Q75_z);
            Attribute Q100_z = new Attribute("Q100_z");
            attributes.addElement(Q100_z);

            Attribute median_x = new Attribute("median_x");
            attributes.addElement(median_x);
            Attribute median_y = new Attribute("median_y");
            attributes.addElement(median_y);
            Attribute median_z = new Attribute("median_z");
            attributes.addElement(median_z);

            FastVector tapping_labels = new FastVector(3);
            tapping_labels.addElement("0"); //top
            tapping_labels.addElement("1"); //bottom
            tapping_labels.addElement("2"); //no tapping
            Attribute label = new Attribute("label", tapping_labels);
            attributes.addElement(label);

            Instances instances = new Instances("tappy", attributes, 0);
            instances.setClass(label); //what I want to predict

            ContentValues latest_value = (ContentValues) intent.getParcelableExtra(Accelerometer.EXTRA_DATA); //get the current accelerometer reading

            Instance values = new Instance(50);

            double axis_x = latest_value.getAsDouble(Accelerometer_Provider.Accelerometer_Data.VALUES_0);
            double axis_y = latest_value.getAsDouble(Accelerometer_Provider.Accelerometer_Data.VALUES_1);
            double axis_z = latest_value.getAsDouble(Accelerometer_Provider.Accelerometer_Data.VALUES_2);

            values.setValue( (Attribute) attributes.elementAt(0), axis_x );//x
            values.setValue( (Attribute) attributes.elementAt(1), axis_y );//y
            values.setValue( (Attribute) attributes.elementAt(2), axis_z );//z
            values.setValue( (Attribute) attributes.elementAt(3), Math.sqrt(Math.pow(axis_x,2) + Math.pow(axis_y,2) + Math.pow(axis_z,2)) );//vector
            values.setValue( (Attribute) attributes.elementAt(4), axis_x+axis_y+axis_z );//sum

            //Process historical data
            Cursor accelerometer = getContentResolver().query(
                    Accelerometer_Provider.Accelerometer_Data.CONTENT_URI,
                    new String[]{
                            "double_values_0",
                            "double_values_1",
                            "double_values_2",
                            "(double_values_0+double_values_1+double_values_2) as sum",
                            "AVG(double_values_0) as avgx",
                            "MIN(double_values_0) as minx",
                            "MAX(double_values_0) as maxx",
                            "(MAX(double_values_0)-MIN(double_values_0)) as rangex",
                            "AVG(double_values_1) as avgy",
                            "MIN(double_values_1) as miny",
                            "MAX(double_values_1) as maxy",
                            "(MAX(double_values_1)-MIN(double_values_1)) as rangey",
                            "AVG(double_values_2) as avgz",
                            "MIN(double_values_2) as minz",
                            "MAX(double_values_2) as maxz",
                            "(MAX(double_values_2)-MIN(double_values_2)) as rangez"
                    },
                    Accelerometer_Provider.Accelerometer_Data.TIMESTAMP + " >= " + (System.currentTimeMillis()-1000), //1s window back
                    null,
                    Accelerometer_Provider.Accelerometer_Data.TIMESTAMP + " ASC");

            ArrayList<Double> vectors = new ArrayList<>();
            ArrayList<Double> data_x = new ArrayList<>(); //window x values
            ArrayList<Double> data_y = new ArrayList<>(); //window y values
            ArrayList<Double> data_z = new ArrayList<>(); //window z values

            if( accelerometer != null && accelerometer.moveToFirst() ) {

                values.setValue((Attribute) attributes.elementAt(16), accelerometer.getDouble(accelerometer.getColumnIndex("avgx")));//avgx
                values.setValue((Attribute) attributes.elementAt(17), accelerometer.getDouble(accelerometer.getColumnIndex("avgy")));//avgy
                values.setValue( (Attribute) attributes.elementAt(18), accelerometer.getDouble(accelerometer.getColumnIndex("avgz")) );//avgz
                values.setValue( (Attribute) attributes.elementAt(22), accelerometer.getDouble(accelerometer.getColumnIndex("minx")) );//minx
                values.setValue( (Attribute) attributes.elementAt(23), accelerometer.getDouble(accelerometer.getColumnIndex("miny")) );//miny
                values.setValue( (Attribute) attributes.elementAt(24), accelerometer.getDouble(accelerometer.getColumnIndex("minz")) );//minz
                values.setValue( (Attribute) attributes.elementAt(25), accelerometer.getDouble(accelerometer.getColumnIndex("maxx")) );//maxx
                values.setValue( (Attribute) attributes.elementAt(26), accelerometer.getDouble(accelerometer.getColumnIndex("maxy")) );//maxy
                values.setValue( (Attribute) attributes.elementAt(27), accelerometer.getDouble(accelerometer.getColumnIndex("maxz")) );//maxz
                values.setValue( (Attribute) attributes.elementAt(28), accelerometer.getDouble(accelerometer.getColumnIndex("rangex")) );//rangex
                values.setValue( (Attribute) attributes.elementAt(29), accelerometer.getDouble(accelerometer.getColumnIndex("rangey")) );//rangey
                values.setValue( (Attribute) attributes.elementAt(30), accelerometer.getDouble(accelerometer.getColumnIndex("rangez")) );//rangez

                do{

                    double xx = accelerometer.getDouble(accelerometer.getColumnIndex(Accelerometer_Provider.Accelerometer_Data.VALUES_0));
                    data_x.add(xx);
                    double yy = accelerometer.getDouble(accelerometer.getColumnIndex(Accelerometer_Provider.Accelerometer_Data.VALUES_1));
                    data_y.add(yy);
                    double zz = accelerometer.getDouble(accelerometer.getColumnIndex(Accelerometer_Provider.Accelerometer_Data.VALUES_2));
                    data_z.add(zz);
                    vectors.add(Math.sqrt(xx * xx + yy * yy + zz * zz));

                }while( accelerometer.moveToNext() );
            }
            if( accelerometer != null && ! accelerometer.isClosed() ) accelerometer.close();

            Statistics stats = new Statistics( vectors );

            values.setValue( (Attribute) attributes.elementAt(5), stats.getMean());//mean vector
            values.setValue( (Attribute) attributes.elementAt(6), stats.median());//median vector
            values.setValue( (Attribute) attributes.elementAt(7), stats.getStdDev());//sd vector
            values.setValue( (Attribute) attributes.elementAt(8), stats.getMin());//min vector
            values.setValue( (Attribute) attributes.elementAt(9), stats.getMax());//max vector
            values.setValue( (Attribute) attributes.elementAt(10), stats.getMax()-stats.getMin());//range vector

            values.setValue( (Attribute) attributes.elementAt(11), latest_value.getAsDouble(Accelerometer_Provider.Accelerometer_Data.VALUES_2) );//q0 vector
            values.setValue( (Attribute) attributes.elementAt(12), latest_value.getAsDouble(Accelerometer_Provider.Accelerometer_Data.VALUES_2) );//q25 vector
            values.setValue( (Attribute) attributes.elementAt(13), latest_value.getAsDouble(Accelerometer_Provider.Accelerometer_Data.VALUES_2) );//q50 vector
            values.setValue( (Attribute) attributes.elementAt(14), latest_value.getAsDouble(Accelerometer_Provider.Accelerometer_Data.VALUES_2) );//q75 vector
            values.setValue( (Attribute) attributes.elementAt(15), latest_value.getAsDouble(Accelerometer_Provider.Accelerometer_Data.VALUES_2) );//q100 vector

            values.setValue( (Attribute) attributes.elementAt(19), latest_value.getAsDouble(Accelerometer_Provider.Accelerometer_Data.VALUES_2) );//sdx
            values.setValue( (Attribute) attributes.elementAt(20), latest_value.getAsDouble(Accelerometer_Provider.Accelerometer_Data.VALUES_2) );//sdy
            values.setValue( (Attribute) attributes.elementAt(21), latest_value.getAsDouble(Accelerometer_Provider.Accelerometer_Data.VALUES_2) );//sdz

            values.setValue( (Attribute) attributes.elementAt(46), latest_value.getAsDouble(Accelerometer_Provider.Accelerometer_Data.VALUES_2) );//medianx
            values.setValue( (Attribute) attributes.elementAt(47), latest_value.getAsDouble(Accelerometer_Provider.Accelerometer_Data.VALUES_2) );//mediany
            values.setValue( (Attribute) attributes.elementAt(48), latest_value.getAsDouble(Accelerometer_Provider.Accelerometer_Data.VALUES_2) );//medianz

            values.setValue( (Attribute) attributes.elementAt(31), latest_value.getAsDouble(Accelerometer_Provider.Accelerometer_Data.VALUES_2) );//q0x
            values.setValue( (Attribute) attributes.elementAt(32), latest_value.getAsDouble(Accelerometer_Provider.Accelerometer_Data.VALUES_2) );//q25x
            values.setValue( (Attribute) attributes.elementAt(33), latest_value.getAsDouble(Accelerometer_Provider.Accelerometer_Data.VALUES_2) );//q50x
            values.setValue( (Attribute) attributes.elementAt(34), latest_value.getAsDouble(Accelerometer_Provider.Accelerometer_Data.VALUES_2) );//q75x
            values.setValue( (Attribute) attributes.elementAt(35), latest_value.getAsDouble(Accelerometer_Provider.Accelerometer_Data.VALUES_2) );//q100x
            values.setValue( (Attribute) attributes.elementAt(36), latest_value.getAsDouble(Accelerometer_Provider.Accelerometer_Data.VALUES_2) );//q0y
            values.setValue( (Attribute) attributes.elementAt(37), latest_value.getAsDouble(Accelerometer_Provider.Accelerometer_Data.VALUES_2) );//q25y
            values.setValue( (Attribute) attributes.elementAt(38), latest_value.getAsDouble(Accelerometer_Provider.Accelerometer_Data.VALUES_2) );//q50y
            values.setValue( (Attribute) attributes.elementAt(39), latest_value.getAsDouble(Accelerometer_Provider.Accelerometer_Data.VALUES_2) );//q75y
            values.setValue( (Attribute) attributes.elementAt(40), latest_value.getAsDouble(Accelerometer_Provider.Accelerometer_Data.VALUES_2) );//q100y
            values.setValue( (Attribute) attributes.elementAt(41), latest_value.getAsDouble(Accelerometer_Provider.Accelerometer_Data.VALUES_2) );//q0z
            values.setValue( (Attribute) attributes.elementAt(42), latest_value.getAsDouble(Accelerometer_Provider.Accelerometer_Data.VALUES_2) );//q25z
            values.setValue( (Attribute) attributes.elementAt(43), latest_value.getAsDouble(Accelerometer_Provider.Accelerometer_Data.VALUES_2) );//q50z
            values.setValue( (Attribute) attributes.elementAt(44), latest_value.getAsDouble(Accelerometer_Provider.Accelerometer_Data.VALUES_2) );//q75z
            values.setValue( (Attribute) attributes.elementAt(45), latest_value.getAsDouble(Accelerometer_Provider.Accelerometer_Data.VALUES_2) );//q100z

            values.setDataset(instances);

            try{
                double[] results = classifier.distributionForInstance(values);
                int max_p = 0;
                for( int i = 0; i < results.length; i++ ) {
                    if( results[i] > results[max_p] ) max_p = i;
                }

                String class_type = instances.classAttribute().value(max_p);
                double probability = Math.round(results[max_p] * 100);

                Intent classified = new Intent(ACTION_UPDATE_UI);
                classified.putExtra("class", class_type);
                classified.putExtra("probability", probability);
                sendBroadcast(classified);
            } catch ( Exception e ) {
                //NOTHING WRONG
            }
        }
    }

    public static class Statistics
    {
        ArrayList<Double> data;
        int size;

        public Statistics(ArrayList<Double> data)
        {
            this.data = data;
            size = data.size();
        }

        double getMin() {
            double min = 999999;
            for(double a : data ){
                if( a < min ) min = a;
            }
            return min;
        }

        double getMax() {
            double max = 0;
            for(double a : data ){
                if( a > max ) max = a;
            }
            return max;
        }

        double getMean()
        {
            double sum = 0.0;
            for(double a : data)
                sum += a;
            return sum/size;
        }

        double getVariance()
        {
            double mean = getMean();
            double temp = 0;
            for(double a :data)
                temp += (mean-a)*(mean-a);
            return temp/size;
        }

        double getStdDev()
        {
            return Math.sqrt(getVariance());
        }

        public double median()
        {
            Arrays.sort(data.toArray());

            if (data.size() % 2 == 0)
            {
                return (data.get((data.size() / 2) - 1) + data.get(data.size() / 2)) / 2.0;
            }
            else
            {
                return data.get(data.size() / 2);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(tapDetector);

        Aware.setSetting(this, Aware_Preferences.STATUS_ACCELEROMETER, false);
        sendBroadcast(new Intent(Aware.ACTION_AWARE_REFRESH));
    }
}
