package in.app.dequeue.Utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


/**
 * Created by A on 17-03-2018.
 */

public class ViewAdapter extends FragmentStatePagerAdapter {
    int mnumoftabs;
    public ViewAdapter(FragmentManager fm, int numoftabs) {
        super(fm);
        this.mnumoftabs = numoftabs;
    }


    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
