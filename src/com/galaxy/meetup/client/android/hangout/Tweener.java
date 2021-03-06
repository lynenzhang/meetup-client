/**
 * galaxy inc.
 * meetup client for android
 */
package com.galaxy.meetup.client.android.hangout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;

/**
 * 
 * @author sihai
 *
 */
public class Tweener {

	private static android.animation.Animator.AnimatorListener mCleanupListener = new AnimatorListenerAdapter() {
		public final void onAnimationCancel(Animator paramAnimator) {
			Tweener.access$000(paramAnimator);
		}

		public final void onAnimationEnd(Animator paramAnimator) {
			Tweener.access$000(paramAnimator);
		}
	};
    private static HashMap sTweens = new HashMap();
    ObjectAnimator animator;
    
	private Tweener(ObjectAnimator objectanimator)
    {
        animator = objectanimator;
    }

    private static void replace(ArrayList arraylist, Object aobj[])
    {
        int i = aobj.length;
        int j = 0;
        while(j < i) 
        {
            Object obj = aobj[j];
            Tweener tweener = (Tweener)sTweens.get(obj);
            if(tweener != null)
            {
                tweener.animator.cancel();
                if(arraylist != null)
                    tweener.animator.setValues((PropertyValuesHolder[])arraylist.toArray(new PropertyValuesHolder[arraylist.size()]));
                else
                    sTweens.remove(tweener);
            }
            j++;
        }
    }

    public static void reset()
    {
        sTweens.clear();
    }

    public static Tweener to(Object obj, long l, Object aobj[])
    {
        long l1 = 0L;
        android.animation.ValueAnimator.AnimatorUpdateListener animatorupdatelistener = null;
        android.animation.Animator.AnimatorListener animatorlistener = null;
        TimeInterpolator timeinterpolator = null;
        ArrayList arraylist = new ArrayList(aobj.length / 2);
        int i = 0;
        while(i < aobj.length) 
        {
            if(!(aobj[i] instanceof String))
                throw new IllegalArgumentException((new StringBuilder("Key must be a string: ")).append(aobj[i]).toString());
            String s = (String)aobj[i];
            Object obj1 = aobj[i + 1];
            if(!"simultaneousTween".equals(s))
                if("ease".equals(s))
                    timeinterpolator = (TimeInterpolator)obj1;
                else
                if("onUpdate".equals(s) || "onUpdateListener".equals(s))
                    animatorupdatelistener = (android.animation.ValueAnimator.AnimatorUpdateListener)obj1;
                else
                if("onComplete".equals(s) || "onCompleteListener".equals(s))
                    animatorlistener = (android.animation.Animator.AnimatorListener)obj1;
                else
                if("delay".equals(s))
                    l1 = ((Number)obj1).longValue();
                else
                if(!"syncWith".equals(s))
                    if(obj1 instanceof float[])
                    {
                        float af[] = new float[2];
                        af[0] = ((float[])obj1)[0];
                        af[1] = ((float[])obj1)[1];
                        arraylist.add(PropertyValuesHolder.ofFloat(s, af));
                    } else
                    if(obj1 instanceof Number)
                        arraylist.add(PropertyValuesHolder.ofFloat(s, new float[] {
                            ((Number)obj1).floatValue()
                        }));
                    else
                        throw new IllegalArgumentException((new StringBuilder("Bad argument for key \"")).append(s).append("\" with value ").append(obj1.getClass()).toString());
            i += 2;
        }
        Tweener tweener = (Tweener)sTweens.get(obj);
        ObjectAnimator objectanimator;
        if(tweener == null)
        {
            objectanimator = ObjectAnimator.ofPropertyValuesHolder(obj, (PropertyValuesHolder[])arraylist.toArray(new PropertyValuesHolder[arraylist.size()]));
            tweener = new Tweener(objectanimator);
            sTweens.put(obj, tweener);
        } else
        {
            objectanimator = ((Tweener)sTweens.get(obj)).animator;
            replace(arraylist, new Object[] {
                obj
            });
        }
        if(timeinterpolator != null)
            objectanimator.setInterpolator(timeinterpolator);
        objectanimator.setStartDelay(l1);
        objectanimator.setDuration(l);
        if(animatorupdatelistener != null)
        {
            objectanimator.removeAllUpdateListeners();
            objectanimator.addUpdateListener(animatorupdatelistener);
        }
        if(animatorlistener != null)
        {
            objectanimator.removeAllListeners();
            objectanimator.addListener(animatorlistener);
        }
        objectanimator.addListener(mCleanupListener);
        objectanimator.start();
        return tweener;
    }

    static void access$000(Animator animator1)
    {
        Iterator iterator = sTweens.entrySet().iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            if(((Tweener)((java.util.Map.Entry)iterator.next()).getValue()).animator != animator1)
                continue;
            iterator.remove();
            break;
        } while(true);
        return;
    }
}
