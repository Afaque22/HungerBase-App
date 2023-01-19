package com.example.hungerbase.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.hungerbase.Home;
import com.example.hungerbase.Models.DetialsModel;
import com.example.hungerbase.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Detail_Fragment extends Fragment {


    Button btnSave;
    AutoCompleteTextView etProvince, etCity;
    TextInputEditText etName, etPhoneNo, etAddress;
    DatabaseReference dropdownRef;
    DatabaseReference detailsRef;
    FirebaseDatabase firebaseDatabase;
    ArrayList<String> province;
    String userClick;

    public Detail_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_, container, false);

        etProvince = view.findViewById(R.id.etProvince);
        etCity = view.findViewById(R.id.etCity);
        etName = view.findViewById(R.id.etName);
        etPhoneNo = view.findViewById(R.id.etPhone);
        etAddress = view.findViewById(R.id.etAddress);
        btnSave = view.findViewById(R.id.btnSave);

        firebaseDatabase = FirebaseDatabase.getInstance();
        dropdownRef = firebaseDatabase.getReference("DropDowns").child("Province");


        dropdownRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                province = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String state = snapshot.getKey();
                    province.add(state);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.drop_down, province);

                adapter.setDropDownViewResource(R.layout.drop_down);
                etProvince.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                processInsert();

            }
        });


        dropDownItemClick();

        //  FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        //   dbRef.keepSynced(true);


        return view;
    }

    // ----- Method dropDownItemClick Start ---->
    public void dropDownItemClick() {


        etProvince.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                userClick = province.get(position);

                dropdownRef.child(userClick).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<String> cities = new ArrayList<>();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()){
                            String city = snapshot1.getKey();
                            cities.add(city);
                        }
                        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getContext(),R.layout.drop_down,cities);

                        etCity.setAdapter(adapter2);
                        etCity.setText("");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), "Failed to fetch", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
    // ----- method dropDownItemClick end -----


    // ------ method processInsert start ------


    public void processInsert() {

        String name = etName.getText().toString();
        String phoneNo = etPhoneNo.getText().toString();
        String province = etProvince.getText().toString();
        String city = etCity.getText().toString();
        String address = etAddress.getText().toString();

        DetialsModel model = new DetialsModel(name, phoneNo, province, city, address);

        detailsRef = firebaseDatabase.getReference();
        // .child("Province");

        String userID = FirebaseAuth.getInstance().getUid();


        detailsRef.//child(province).child(city).
                child("Users").child(userID).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {


                Intent intent = new Intent(getActivity().getBaseContext(),Home.class);
                intent.putExtra("city",city);
                etName.setText("");
                etPhoneNo.setText("");
                etProvince.setText("");
                etCity.setText("");
                etAddress.setText("");
                Toast.makeText(getContext(), "Details Inserted", Toast.LENGTH_SHORT).show();
               // startActivity(new Intent(getContext(), Home.class));
                startActivity(intent);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getContext(), "Could Not Inserted", Toast.LENGTH_SHORT).show();


            }
        });
    }
}