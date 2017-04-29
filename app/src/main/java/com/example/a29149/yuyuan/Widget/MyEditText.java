package com.example.a29149.yuyuan.Widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;

public class MyEditText extends AppCompatEditText implements TextWatcher,OnFocusChangeListener {

	private Drawable left, right;//左右图标

	private boolean isFocus = false; //标记是否获得焦点，若获得焦点再做判断，默认没获得焦点
	  
	private int xUp=0;
	public MyEditText(Context context) {
		this(context, null); 
	} 
		  
	public MyEditText(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.editTextStyle); 
	} 
		  
    public MyEditText(Context context, AttributeSet attrs, int defStyle) {
    	super(context, attrs, defStyle); 
		initWedgits(); //初始化
	}
    
    public void initWedgits() { 
    	try { 
    	      left = getCompoundDrawables()[0];     	     
    	      right = getCompoundDrawables()[2]; 
    	      initDatas(); 
    	    } catch (Exception e) {
    	      e.printStackTrace(); 
    	    } 
	}
    
    public void initDatas() { 
    	try { 
    	      setCompoundDrawablesWithIntrinsicBounds(left, null, null, null); 
    	      addListeners();
    	    } catch (Exception e) {
    	      e.printStackTrace(); 
    	    } 
	}
    
    public void addListeners() { 
    	try { 
    		  setOnFocusChangeListener(this); 
    	      addTextChangedListener(this); 
    	    } catch (Exception e) {
    	      e.printStackTrace(); 
    	    } 
	}
    
	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                  int arg3) {
		// TODO Auto-generated method stub		
	}
	
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int after) {
		 if (isFocus) { 
			 if (TextUtils.isEmpty(s)) {
				 setCompoundDrawablesWithIntrinsicBounds(left, null, null, null); 
		      } else { 		        
		    	  if (null == right) { 
		    		  right = getCompoundDrawables()[2]; 
		        	 } 
		    	  setCompoundDrawablesWithIntrinsicBounds(left, null, right, null); 
		      } 
		 }	
    }
	
	@Override
	  public boolean onTouchEvent(MotionEvent event) {
	    try { 
	      switch (event.getAction()) { 
	      case MotionEvent.ACTION_UP:
	        xUp = (int) event.getX(); 	      
	        if ((getWidth() - xUp) <= getCompoundPaddingRight()) { 
	          if (!TextUtils.isEmpty(getText().toString())) {
				  setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
				  setText("");
	          } 
	        } 
	        break; 
	      default: 
	        break; 
	      } 
	    } catch (Exception e) {
	      e.printStackTrace(); 
	    } 
	    return super.onTouchEvent(event); 
	  } 
	
	@Override
	public void onFocusChange(View arg0, boolean arg1) {
		 try { 
		      this.isFocus = arg1; 
		    } catch (Exception e) {
		      e.printStackTrace(); 
		    } 
		
	}

}
