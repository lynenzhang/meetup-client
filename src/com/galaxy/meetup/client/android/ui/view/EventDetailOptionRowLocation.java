/**
 * galaxy inc.
 * meetup client for android
 */
package com.galaxy.meetup.client.android.ui.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.galaxy.meetup.client.android.R;
import com.galaxy.meetup.server.client.v2.domain.Event;
import com.galaxy.meetup.server.client.v2.domain.Location;

/**
 * 
 * @author sihai
 *
 */
public class EventDetailOptionRowLocation extends EventDetailsOptionRowLayout
		implements OnClickListener {

	private static Drawable sDirectionIcon;
    private static Drawable sHangoutIcon;
    private static Drawable sHangoutJoinIcon;
    private static String sHangoutJoinText;
    private static String sHangoutTitle;
    private static String sHangoutbeforeText;
    private static Drawable sLocationIcon;
    private ImageView mLaunchIcon;
    private EventActionListener mListener;
    private boolean mLocation;
    private ImageView mTypeIcon;
    private boolean sInitialized;
    
	public EventDetailOptionRowLocation(Context context)
    {
        super(context);
    }

    public EventDetailOptionRowLocation(Context context, AttributeSet attributeset)
    {
        super(context, attributeset, 0);
    }

    public EventDetailOptionRowLocation(Context context, AttributeSet attributeset, int i)
    {
        super(context, attributeset, i);
    }

    public final void bind(Event event, EventActionListener eventactionlistener)
    {
        boolean flag;
        Location location = event.getLocation();
        if(null != location)
        	mLocation = true;
        else
        	mLocation = false;
        mListener = eventactionlistener;
        if(mLocation) {
            mTypeIcon.setImageDrawable(sLocationIcon);
            mLaunchIcon.setImageDrawable(sDirectionIcon);
            super.bind(location.buildAddress(), (String)null, mTypeIcon, mLaunchIcon);
        } else {
            mTypeIcon.setImageDrawable(sHangoutIcon);
            String s = sHangoutbeforeText;
            long i = System.currentTimeMillis() - event.getStartTime().getTimeMs().longValue();
            ImageView imageview = null;
            if(i > 0)
            {
                mLaunchIcon.setImageDrawable(sHangoutJoinIcon);
                imageview = mLaunchIcon;
                s = sHangoutJoinText;
            }
            super.bind(sHangoutTitle, s, mTypeIcon, imageview);
        }
    }

    protected final void init(Context context, AttributeSet attributeset, int i)
    {
        super.init(context, attributeset, i);
        if(!sInitialized)
        {
            Resources resources = context.getResources();
            sLocationIcon = resources.getDrawable(R.drawable.icn_events_details_location);
            sDirectionIcon = resources.getDrawable(R.drawable.icn_events_directions);
            sHangoutIcon = resources.getDrawable(R.drawable.icn_events_hangout_1up);
            sHangoutTitle = resources.getString(R.string.event_detail_hangout_title);
            sHangoutbeforeText = resources.getString(R.string.event_detail_hangout_before);
            sHangoutJoinText = resources.getString(R.string.event_detail_hangout_during);
            sHangoutJoinIcon = resources.getDrawable(R.drawable.icn_events_arrow_right);
            sInitialized = true;
        }
        mTypeIcon = new ImageView(context, attributeset, i);
        mLaunchIcon = new ImageView(context, attributeset, i);
        setClickable(true);
        setOnClickListener(this);
    }

    public void onClick(View view)
    {
        if(mLocation)
            mListener.onLocationClicked();
        else
            mListener.onHangoutClicked();
    }
}
