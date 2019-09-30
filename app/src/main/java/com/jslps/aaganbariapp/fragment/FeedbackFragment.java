package com.jslps.aaganbariapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.jslps.aaganbariapp.Constant;
import com.jslps.aaganbariapp.R;
import com.jslps.aaganbariapp.activity.MainActivity;
import com.jslps.aaganbariapp.model.FeedbackModelDb;
import com.jslps.aaganbariapp.model.HeaderData;
import com.jslps.aaganbariapp.model.LoginModelDb;
import com.orm.query.Select;

import java.util.ArrayList;

public class FeedbackFragment extends BaseFragment {

    private View rootView;
    private EditText editTextname, editTextphone, editTextemail, editTextMessage;
    Button buttonSubmitFeedback;

    public FeedbackFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mListener.onFragmentUpdate(Constant.setTitle, new HeaderData(false, getString(R.string.feedback)));
        mListener.onFragmentUpdate(Constant.UPDATE_FRAGMENT, Constant.FRAGMENT_FEEDBACK);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_feedback, container, false);
        buttonSubmitFeedback = rootView.findViewById(R.id.buttonSubmitFeedback);
        editTextemail = rootView.findViewById(R.id.emailid);
        editTextMessage = rootView.findViewById(R.id.edittextmessage);
        editTextphone = rootView.findViewById(R.id.phone);
        editTextname = rootView.findViewById(R.id.name);
        buttonSubmitFeedback = rootView.findViewById(R.id.buttonSubmitFeedback);
        buttonSubmitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextname.getText().toString().trim().isEmpty()) {
                    editTextname.setError(getString(R.string.name_validaion));
                    editTextname.requestFocus();
                    showError(editTextname);
                } else if (editTextemail.getText().toString().trim().isEmpty()) {
                    editTextemail.setError(getString(R.string.name_validaion));
                    editTextemail.requestFocus();
                    showError(editTextemail);
                } else if (editTextphone.getText().toString().trim().isEmpty()) {
                    editTextphone.setError(getString(R.string.name_validaion));
                    editTextphone.requestFocus();
                    showError(editTextphone);
                } else if (editTextMessage.getText().toString().trim().isEmpty()) {
                    editTextMessage.setError(getString(R.string.name_validaion));
                    editTextMessage.requestFocus();
                    showError(editTextMessage);
                } else {
                    addFeedback();
                }
            }
        });
        return rootView;
    }

    private void addFeedback() {
        ArrayList<LoginModelDb> loginModelDbArrayList = (ArrayList<LoginModelDb>) Select.from(LoginModelDb.class).list();
        FeedbackModelDb feedbackModelDb = new FeedbackModelDb();
        feedbackModelDb.setCreatedby(loginModelDbArrayList.get(0).getCreatedby());
        feedbackModelDb.setName(editTextname.getText().toString());
        feedbackModelDb.setMessage(editTextMessage.getText().toString());
        feedbackModelDb.setPhone(editTextphone.getText().toString());
        feedbackModelDb.setEmailid(loginModelDbArrayList.get(0).getCreatedby());
        feedbackModelDb.save();
        Toast.makeText(getActivity(), "Feedback added successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void showError(EditText editText) {
        Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        editText.startAnimation(shake);
    }
}

