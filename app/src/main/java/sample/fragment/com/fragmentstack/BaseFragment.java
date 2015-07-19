package sample.fragment.com.fragmentstack;

import android.support.v4.app.Fragment;

/**
 * Created by nitinraj.arvind on 7/18/2015.
 */
public abstract class BaseFragment extends Fragment {

    public abstract String getActionbarTitleString();

    public abstract void setActionbarTitleString();
}
