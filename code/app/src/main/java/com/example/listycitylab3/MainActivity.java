package com.example.listycitylab3;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.listycitylab3.AddCityFragment;
import com.example.listycitylab3.City;
import com.example.listycitylab3.CityArrayAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogListener {

    private ArrayList<City> dataList;
    private CityArrayAdapter cityAdapter;
    private ListView cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sample cities
        String[] cities = { "Edmonton", "Vancouver", "Toronto" };
        String[] provinces = { "AB", "BC", "ON" };

        dataList = new ArrayList<>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }

        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v -> new AddCityFragment().show(getSupportFragmentManager(), "Add City"));

        // Edit city on item click
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            City city = dataList.get(position);
            AddCityFragment fragment = AddCityFragment.newInstance(city);
            fragment.show(getSupportFragmentManager(), "Edit City");
        });
    }

    @Override
    public void onCityAdded(City city) {
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCityEdited(City city) {
        cityAdapter.notifyDataSetChanged(); // Refresh the ListView
    }
}
