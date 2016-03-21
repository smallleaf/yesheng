package com.example.graduate.activity.common;

import java.util.Stack;

import android.app.Activity;

/**
 * 自定义管理activity栈
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
     * 所有activityfinish
     */
    public void popActivity(){   
        Activity activity=activityStack.lastElement();   
        if(activity!=null){   
            activity.finish();   
            activity=null;   
        }   
    }   
    /**
     * 栈顶指定activity移除
     */
    public void popActivity(Activity activity){   
        if(activity!=null){   
            activity.finish();   
            activityStack.remove(activity);   
            activity=null;   
        }   
    }   
    
    /**
     * 返回当前栈顶actiivty
     */
    public Activity currentActivity(){   
        Activity activity=activityStack.lastElement();   
        return activity;   
    }   
    
    /**
     * 加入activity
     */
    public void pushActivity(Activity activity){   
        if(activityStack==null){   
            activityStack=new Stack<Activity>();   
        }   
        activityStack.add(activity);   
    }   
       
    /**
     * 移除除了某个的所有activity actiivty
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
 