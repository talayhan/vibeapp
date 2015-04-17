package net.talayhan.android.vibeproject.Controller;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.twotoasters.jazzylistview.JazzyHelper;
import com.twotoasters.jazzylistview.recyclerview.JazzyRecyclerViewScrollListener;

import net.talayhan.android.vibeproject.R;
import net.talayhan.android.vibeproject.Util.ListModel;
import net.talayhan.android.vibeproject.Util.MyAdapter;

/**
 * Created by talayhan on 06/04/15.
 */
public class ChartRecyclerView extends Activity {

    private JazzyRecyclerViewScrollListener jazzyScrollListener;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private static final String KEY_TRANSITION_EFFECT = "transition_effect";
    private int mCurrentTransitionEffect = JazzyHelper.FLIP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter ()
        mAdapter = new MyAdapter(ListModel.getModel());
        mRecyclerView.setAdapter(mAdapter);


        //mRecyclerView.setLayoutManager(createLayoutManager(itemLayoutRes, isStaggered));
        //mRecyclerView.setAdapter(new SampleRecyclerViewAdapter(ListModel.getModel(), itemLayoutRes, isStaggered));

        jazzyScrollListener = new JazzyRecyclerViewScrollListener();
        mRecyclerView.setOnScrollListener(jazzyScrollListener);

        setupJazziness(mCurrentTransitionEffect);
    }

    /*
    * Setup list view animation */
    private void setupJazziness(int effect) {
        mCurrentTransitionEffect = effect;
        jazzyScrollListener.setTransitionEffect(mCurrentTransitionEffect);
    }
}
