
package project.astix.com.balajisosfadirectsales;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

class CustomKeyboard {

      private KeyboardView mKeyboardView,mKeyboardViewNum,mKeyboardViewNumWithoutDecimal;
      private Activity mHostActivity;
      private OnKeyboardActionListener mOnKeyboardActionListener = new OnKeyboardActionListener() {

        public final static int CodeDelete   = -5; // Keyboard.KEYCODE_DELETE
        public final static int CodeCancel   = -3; // Keyboard.KEYCODE_CANCEL
        public final static int CodePrev     = 55000;
        public final static int CodeAllLeft  = 55001;
        public final static int CodeLeft     = 55002;
        public final static int CodeRight    = 55003;
        public final static int CodeAllRight = 55004;
        public final static int CodeNext     = 55005;
        public final static int CodeClear    = 55006;
        public final static int codeCaps    = -1;


        private boolean caps = false;

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            // NOTE We can say '<Key android:codes="49,50" ... >' in the xml file; all codes come in keyCodes, the first in this list in primaryCode
            // Get the EditText and its Editable


            View focusCurrent = mHostActivity.getWindow().getCurrentFocus();
            if( focusCurrent==null || focusCurrent.getClass()!=EditText.class ) return;
            EditText edittext = (EditText) focusCurrent;
            Editable editable = edittext.getText();
            int start = edittext.getSelectionStart();
            // Apply the key to the edittext
            if( primaryCode==CodeCancel ) {
                hideCustomKeyboard();
            } else if( primaryCode==CodeDelete ) {
                if( editable!=null && start>0 ) editable.delete(start - 1, start);
            }

            else if(primaryCode==codeCaps ) {
                caps = !caps;
                mKeyboardView.setShifted(caps);
                mKeyboardView.invalidateAllKeys();


            }

            else if(caps == true && Character.isLetter(primaryCode)){
                char code = (char)primaryCode;
                code = Character.toUpperCase(code);

                editable.insert(start, Character.toString((char) primaryCode).toUpperCase());

                System.out.println("capsCode..."+ code);
            }



            else if( primaryCode==CodeClear ) {
                if( editable!=null ) editable.clear();
            } else if( primaryCode==CodeLeft ) {
                if( start>0 ) edittext.setSelection(start - 1);
            } else if( primaryCode==CodeRight ) {
                if (start < edittext.length()) edittext.setSelection(start + 1);
            } else if( primaryCode==CodeAllLeft ) {
                edittext.setSelection(0);
            } else if( primaryCode==CodeAllRight ) {
                edittext.setSelection(edittext.length());
            } else if( primaryCode==CodePrev ) {
                View focusNew= edittext.focusSearch(View.FOCUS_BACKWARD);
                if( focusNew!=null ) focusNew.requestFocus();
            } else if( primaryCode==CodeNext ) {
                View focusNew= edittext.focusSearch(View.FOCUS_FORWARD);
                if( focusNew!=null ) focusNew.requestFocus();
            }


            else { // insert character

                    editable.insert(start, Character.toString((char) primaryCode));


            }

        }

        @Override
        public void onPress(int arg0) {
        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeUp() {
        }
    };


    public CustomKeyboard(Activity host, int viewid, int layoutid) {
        mHostActivity= host;
        mKeyboardView= (KeyboardView)mHostActivity.findViewById(viewid);
        mKeyboardView.setKeyboard(new Keyboard(mHostActivity, layoutid));
        mKeyboardView.setPreviewEnabled(false);
        mKeyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);




        mKeyboardViewNum= (KeyboardView)mHostActivity.findViewById(viewid);
        mKeyboardViewNum.setKeyboard(new Keyboard(mHostActivity, layoutid));
        mKeyboardViewNum.setPreviewEnabled(false); // NOTE Do not show the preview balloons
        mKeyboardViewNum.setOnKeyboardActionListener(mOnKeyboardActionListener);


        mKeyboardViewNumWithoutDecimal= (KeyboardView)mHostActivity.findViewById(viewid);
        mKeyboardViewNumWithoutDecimal.setKeyboard(new Keyboard(mHostActivity, layoutid));
        mKeyboardViewNumWithoutDecimal.setPreviewEnabled(false); // NOTE Do not show the preview balloons
        mKeyboardViewNumWithoutDecimal.setOnKeyboardActionListener(mOnKeyboardActionListener);

        // Hide the standard keyboard initially
        mHostActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void showCustomKeyboard( View v ) {
        mKeyboardView.setVisibility(View.VISIBLE);
        mKeyboardView.setEnabled(true);
        if( v!=null ) ((InputMethodManager)mHostActivity.getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /** Make the CustomKeyboard invisible. */
    public void hideCustomKeyboard() {

        mKeyboardViewNum.setVisibility(View.GONE);
        mKeyboardViewNum.setEnabled(false);

        mKeyboardView.setVisibility(View.GONE);
        mKeyboardView.setEnabled(false);

    }

  public void hideCustomKeyboardNum(View v) {
        mKeyboardViewNum.setVisibility(View.GONE);
        mKeyboardViewNum.setEnabled(false);
        mKeyboardView.setVisibility(View.GONE);
        mKeyboardView.setEnabled(false);


    }

    public void registerEditText(EditText edt) {


        //edt.setInputType(InputType.TYPE_NULL); // Disable standard keyboard

        if (Build.VERSION.SDK_INT >= 11) {
            edt.setRawInputType(InputType.TYPE_CLASS_NUMBER);
            edt.setTextIsSelectable(true);
        } else {
            edt.setRawInputType(InputType.TYPE_NULL);
            edt.setFocusable(true);
        }
    }




}
