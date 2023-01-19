package com.example.hungerbase.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hungerbase.Home;
import com.example.hungerbase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignUpFragment extends Fragment {

    TextInputEditText rEmail, rPswrd, rcPswrd;
    TextInputLayout tiEmail, tiPswrd, ticPswrd;
    Button btnSignUp;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;

    public SignUpFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);


        rEmail = view.findViewById(R.id.etEmail);
        rPswrd = view.findViewById(R.id.etPswrd);
        rcPswrd = view.findViewById(R.id.etCPswrd);
        tiEmail = view.findViewById(R.id.ti_Email);
        tiPswrd = view.findViewById(R.id.ti_Pswrd);
        ticPswrd = view.findViewById(R.id.ti_cPswrd);
        btnSignUp = view.findViewById(R.id.btnSignUp);
        progressBar = view.findViewById(R.id.progress_bar);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               validation();

            }
        });




        return view;
    }

    private void firebaseSignUp() {
        progressBar.setVisibility(View.VISIBLE);
        String email = rEmail.getText().toString();
        String password = rPswrd.getText().toString();

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getContext(), "Sign Up Successfully", Toast.LENGTH_SHORT).show();
                            Fragment detFragment = new Detail_Fragment();
                            FragmentTransaction fm = getActivity().getSupportFragmentManager().beginTransaction();
                            fm.replace(R.id.fragment_detail,detFragment).commit();
                            rEmail.setText("");
                            rPswrd.setText("");


                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            rEmail.setText("");
                            rPswrd.setText("");
                            Toast.makeText(getContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public boolean validation(){

      String vEmail = rEmail.getText().toString();
      String vPswrd = rPswrd.getText().toString();
      String vcPswrd = rcPswrd.getText().toString();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


                if (!vEmail.matches(emailPattern)) {
                    tiEmail.setHelperText("Please enter a valid email address");
                } if (vEmail.isEmpty()){
                    tiEmail.setHelperText("Field can't be empty");
                }

                if (vPswrd.length() < 6) {
                    tiPswrd.setHelperText("Password is too weak");
                } if (vPswrd.isEmpty()){
                     tiPswrd.setHelperText("Field can't be empty");
                 }if (!vPswrd.equals(vcPswrd)){
                    ticPswrd.setHelperText("Password Not Matching");
                 }else if (vcPswrd.isEmpty()){
                     ticPswrd.setHelperText("Field can't be empty");
                 }else {
                     firebaseSignUp();
                 }


        return true;
    }

}