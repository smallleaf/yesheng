package com.example.graduate.activity.common;

import java.util.Stack;

import android.app.Activity;

/**
 * �Զ������activityջ
 * @author Administrator
 *
 */
public class ScreenManager {   
    private static Stack<Activity> activityStack;   
    private static ScreenManager instance;   
    private  ScreenManager(){   
    }   
    public static ScreenManager getScreenManager(){   
        if(instance==null){   
            instance=new ScreenManager();   
        }   
        return instance;   
    }   
    /**
     * ����activityfinish
     */
    public void popActivity(){   
        Activity activity=activityStack.lastElement();   
        if(activity!=null){   
            activity.finish();   
            activity=null;   
        }   
    }   
    /**
     * ջ��ָ��activity�Ƴ�
     */
    public void popActivity(Activity activity){   
        if(activity!=null){   
            activity.finish();   
            activityStack.remove(activity);   
            activity=null;   
        }   
    }   
    
    /**
     * ���ص�ǰջ��actiivty
     */
    public Activity currentActivity(){   
        Activity activity=activityStack.lastElement();   
        return activity;   
    }   
    
    /**
     * ����activity
     */
    public void pushActivity(Activity activity){   
        if(activityStack==null){   
            activityStack=new Stack<Activity>();   
        }   
        activityStack.add(activity);   
    }   
       
    /**
     * �Ƴ�����ĳ��������activity actiivty
     */
    public void popAllActivityExceptOne(Class cls){   
        while(true){   
            Activity activity=currentActivity();   
            if(activity==null){   
                break;   
            }   
            if(activity.getClass().equals(cls) ){   
                break;   
            }   
            popActivity(activity);   
        }   
    }   
}  
 