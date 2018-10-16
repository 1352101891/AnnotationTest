package com.example.viewfactory;

import android.app.Activity;
import com.example.myapplication.view.AnnotationActivity;
import com.example.myapplication.view.MainActivity;
import com.example.viewbinder.AnnotationActivityViewBinder;
import com.example.viewbinder.MainActivityViewBinder;

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
