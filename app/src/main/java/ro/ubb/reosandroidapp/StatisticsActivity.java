package ro.ubb.reosandroidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ro.ubb.reosandroidapp.globals.Globals;
import ro.ubb.reosandroidapp.model.Apartment;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        BarChart chart = new BarChart(getApplicationContext());
        chart.setDrawBarShadow(true);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        chart.setLayoutParams(layoutParams);
        // get a layout defined in xml
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.statistics_layout);
        rl.addView(chart);
        List<Apartment> apartments =  Globals.apartmentRepository.getAll();
        float index = 0f;
        List<BarEntry> entries = new ArrayList<BarEntry>();
        for(Apartment apartment: apartments) {
            index++;
            BarEntry barEntry = new BarEntry(index, (float) apartment.getCost(), apartment.getTitle());
            entries.add(barEntry);
        }
        BarDataSet dataSet = new BarDataSet(entries, "Product");
        dataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return entry.getData().toString();
            }
        });
        BarData lineData = new BarData(dataSet);
        chart.setData(lineData);
        chart.invalidate(); // refresh

    }
}
