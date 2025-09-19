package com.example.listycitylab3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.listycitylab3.City;

public class AddCityFragment extends DialogFragment {

    public interface AddCityDialogListener {
        void onCityAdded(City city);
        void onCityEdited(City city);
    }

    private AddCityDialogListener listener;
    private City cityToEdit; // null if adding a new city

    public AddCityFragment() { } // Required empty constructor

    public static AddCityFragment newInstance(City city) {
        AddCityFragment fragment = new AddCityFragment();
        Bundle args = new Bundle();
        args.putSerializable("city", city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.fragment_add_city, null);

        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        // Check if editing an existing city
        if (getArguments() != null) {
            cityToEdit = (City) getArguments().getSerializable("city");
            if (cityToEdit != null) {
                editCityName.setText(cityToEdit.getName());
                editProvinceName.setText(cityToEdit.getProvince());
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        return builder
                .setView(view)
                .setTitle(cityToEdit == null ? "Add a city" : "Edit city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton(cityToEdit == null ? "Add" : "Save", (dialog, which) -> {
                    String name = editCityName.getText().toString();
                    String province = editProvinceName.getText().toString();

                    if (cityToEdit == null) {
                        // Adding new city
                        City newCity = new City(name, province);
                        listener.onCityAdded(newCity);
                    } else {
                        // Editing existing city
                        cityToEdit.setName(name);
                        cityToEdit.setProvince(province);
                        listener.onCityEdited(cityToEdit);
                    }
                })
                .create();
    }
}
