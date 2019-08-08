package com.jslps.aaganbariapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.jslps.aaganbariapp.listener.OnFragmentInteractionListener;


public abstract class BaseFragment extends Fragment {

    OnFragmentInteractionListener mListener;
    private AppCompatActivity mActivity;

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onViewCreated(view, savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
  //      // QLog.m11v("Class:" + getClass().getSimpleName());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void onAttach(Activity activity) {
    //    // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onAttach(activity);
        mActivity = (AppCompatActivity)activity;
    }

    public void onCreate(Bundle savedInstanceState) {
      //  // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onCreate(savedInstanceState);
    }

    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        //// // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onInflate(activity, attrs, savedInstanceState);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onActivityCreated(savedInstanceState);
    }

    public void onStart() {
        // // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onStart();
    }

    public void onResume() {
        // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onResume();
    }

    public void onPause() {
        // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onPause();
    }

    public void onStop() {
        // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onStop();
    }

    public void onDestroyView() {
        // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onDestroyView();
    }

    public void onDestroy() {
        // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onDestroy();
    }


    public void onLowMemory() {
        // QLog.m11v("Class:" + getClass().getSimpleName());
        super.onLowMemory();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            //if(com.olive.upi.transport.TransportConstants.appRelease) Log.d("", "onAttach: mListener is not OnFragmentInteractionListener "+mListener);
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mActivity = null;
    }

    public static boolean isValidEmail(CharSequence email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }



//    public void setUpToolBar(String title) {
//        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
//        TextView screenTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
//        screenTitle.setText(title);
//    }


//    public void setmScreenTitle(String title){
//        this.title = title;
//    }

    public AppCompatActivity getmActivity(){
        return mActivity;
    }


}
