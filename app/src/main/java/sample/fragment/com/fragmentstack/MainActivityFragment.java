package sample.fragment.com.fragmentstack;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements  ActionbarTitleHelper{

    public static String TAG = "sample.fragment.com.fragmentstack.MainActivityFragment";
    public static String TITLE = "Main";

    public MainActivityFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setRetainInstance(true);
        MainActivity.doLog(getActivity(), this.getClass(), "onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.doLog(getActivity(), this.getClass(), "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MainActivity.doLog(getActivity(), this.getClass(), "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        TextView label_number = (TextView) rootView.findViewById(R.id.label_number);
        label_number.setText("# " + MainActivity.randomFiveDigitGenerator());
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MainActivity.doLog(getActivity(), this.getClass(), "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        MainActivity.doLog(getActivity(), this.getClass(), "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.doLog(getActivity(), this.getClass(), "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        MainActivity.doLog(getActivity(), this.getClass(), "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        MainActivity.doLog(getActivity(), this.getClass(), "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainActivity.doLog(getActivity(), this.getClass(), "onDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MainActivity.doLog(getActivity(), this.getClass(), "onDestroyView");
    }

    @Override
    public String getActionbarTitleString() {
        return TITLE;
    }

    @Override
    public void setActionbarTitleString() {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(TITLE);
    }

    @Override
    public String toString() {
        return "Main";
    }
}
