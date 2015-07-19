package sample.fragment.com.fragmentstack;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by apple on 01/07/15.
 */
public class FriendsFragment extends Fragment implements  ActionbarTitleHelper{

    public static String TAG = "sample.fragment.com.fragmentstack.FriendsFragment";
    public static String TITLE=  "Friends";


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);
        TextView label_number = (TextView) rootView.findViewById(R.id.label_number);
        label_number.setText("# " + MainActivity.randomFiveDigitGenerator());

        return rootView;
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
        return "FriendsFragment";
    }
}
