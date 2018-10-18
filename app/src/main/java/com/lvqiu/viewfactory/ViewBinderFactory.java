package com.lvqiu.viewfactory;

import android.app.Activity;
import com.lvqiu.myapplication.view.AnnotationActivity;
import com.lvqiu.myapplication.view.MainActivity;
import com.lvqiu.viewbinder.AnnotationActivityViewBinder;
import com.lvqiu.viewbinder.MainActivityViewBinder;

public final class ViewBinderFactory {
  public static void binderViews(Activity activity) {
    if(activity  instanceof AnnotationActivity) {
    	AnnotationActivityViewBinder.InitViews((AnnotationActivity) activity);
    	return;  
    }
    if(activity  instanceof MainActivity) {
    	MainActivityViewBinder.InitViews((MainActivity) activity);
    	return;  
    }
  }
}
