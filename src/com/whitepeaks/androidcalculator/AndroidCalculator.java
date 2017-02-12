package com.whitepeaks.androidcalculator;

import com.whitepeak.androidcalculator.R;

import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import com.whitepeaks.calculator.Calculator;

/**
*
* @author Stefano Galli
*/

public class AndroidCalculator extends Activity {

	EditText in, out;
	Typeface type;

	String output = "";
	String input = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Remove title
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Fullscreen mode
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator);

		// Load a custom font
		type = Typeface.createFromAsset(getAssets(), "fonts/Calculator.ttf");

		// Initialize all buttons
		initAllButtons();

		// Initialize EditText views and set custom font
		in = (EditText) findViewById(R.id.input);
		out = (EditText) findViewById(R.id.output);
		in.setTypeface(type);
		out.setTypeface(type);

	}

	// Class to handle touch events. Grant Haptic and visual Feedback and
	// buttons actions
	private class HapticTouchListener implements OnTouchListener,
			OnClickListener {
		Drawable[] layers;

		private final int feedbackType;

		public HapticTouchListener(int type) {
			feedbackType = type;
		}

		public int feedbackType() {
			return feedbackType;
		}

		@Override
		public boolean onTouch(final View v, MotionEvent event) {
			layers = new Drawable[2];

			// perform visual and Haptic feedback on button pressed
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				visualFeedback(v);
				v.performHapticFeedback(feedbackType());

			}

			// perform a reverse visual feedback and a click event on button
			// released
			if (event.getAction() == MotionEvent.ACTION_UP) {
				reverseVisualFeedback(v);
				v.performClick();
			}

			return true;
		}

		@Override
		public void onClick(View v) {
			boolean digitOrOperator = true;
			switch (v.getId()) {

			// Delete last input char
			case R.id.button_delete:
				String str = in.getText().toString();
				if (str.length() > 0) {
					input = str.substring(0, str.length() - 1);
					output = Calculator.calculateString(input);
					in.setText("");
					out.setText("");
					in.append(input);
					if (input.equals(""))
						out.append("");
					else
						out.append(output);
				}
				digitOrOperator = false;
				break;

			// Clear all
			case R.id.button_clear:
				output = "Real time calculation";
				input = "Type an expression to begin...";
				digitOrOperator = false;
				in.setText("");
				in.setHint(input);
				out.setText("");
				out.setHint(output);
				input = "";
				output = "";
				break;
			}

			// Otherwise add a digit or operator as the last input char and
			// calculate
			if (digitOrOperator) {
				Button b = (Button) v;
				String buttonText = b.getText().toString();
				input = input + buttonText;
				output = Calculator.calculateString(input);
				in.setText("");
				out.setText("");
				in.append(input);
				out.append(output);
			}
		}

		// Set up a TransitionDrawable object to handle and start buttons visual
		// transition
		private void visualFeedback(View v) {

			TransitionDrawable transition;
			switch (v.getId()) {
			case R.id.button_1:
			case R.id.button_2:
			case R.id.button_3:
			case R.id.button_4:
			case R.id.button_5:
			case R.id.button_6:
			case R.id.button_7:
			case R.id.button_8:
			case R.id.button_9:
				layers[0] = getResources().getDrawable(
						R.drawable.button_custom_center);
				layers[1] = getResources().getDrawable(
						R.drawable.button_custom_center_pressed);
				transition = new TransitionDrawable(layers);
				v.setBackground(transition);
				transition.startTransition(100);
				break;

			case R.id.button_clear:
			case R.id.button_divide:
			case R.id.button_multiply:
				layers[0] = getResources().getDrawable(
						R.drawable.button_custom_top);
				layers[1] = getResources().getDrawable(
						R.drawable.button_custom_top_pressed);
				transition = new TransitionDrawable(layers);
				v.setBackground(transition);
				transition.startTransition(100);
				break;

			case R.id.button_minus:
			case R.id.button_plus:
			case R.id.button_openPar:
				layers[0] = getResources().getDrawable(
						R.drawable.button_custom_right);
				layers[1] = getResources().getDrawable(
						R.drawable.button_custom_right_pressed);
				transition = new TransitionDrawable(layers);
				v.setBackground(transition);
				transition.startTransition(100);
				break;

			case R.id.button_0:
			case R.id.button_dot:
			case R.id.button_pow:
				layers[0] = getResources().getDrawable(
						R.drawable.button_custom_bot);
				layers[1] = getResources().getDrawable(
						R.drawable.button_custom_bot_pressed);
				transition = new TransitionDrawable(layers);
				v.setBackground(transition);
				transition.startTransition(100);
				break;

			case R.id.button_delete:
				layers[0] = getResources().getDrawable(
						R.drawable.button_custom_top_right);
				layers[1] = getResources().getDrawable(
						R.drawable.button_custom_top_right_pressed);
				transition = new TransitionDrawable(layers);
				v.setBackground(transition);
				transition.startTransition(100);
				break;

			case R.id.button_closedPar:
				layers[0] = getResources().getDrawable(
						R.drawable.button_custom_bot_right);
				layers[1] = getResources().getDrawable(
						R.drawable.button_custom_bot_right_pressed);
				transition = new TransitionDrawable(layers);
				v.setBackground(transition);
				transition.startTransition(100);
				break;

			}

		}

		// Set up a TransitionDrawable object to handle and start buttons
		// reverse visual transition
		private void reverseVisualFeedback(View v) {
			TransitionDrawable transition;
			switch (v.getId()) {
			case R.id.button_1:
			case R.id.button_2:
			case R.id.button_3:
			case R.id.button_4:
			case R.id.button_5:
			case R.id.button_6:
			case R.id.button_7:
			case R.id.button_8:
			case R.id.button_9:
				layers[1] = getResources().getDrawable(
						R.drawable.button_custom_center);
				layers[0] = getResources().getDrawable(
						R.drawable.button_custom_center_pressed);
				transition = new TransitionDrawable(layers);
				v.setBackground(transition);
				transition.startTransition(100);
				break;

			case R.id.button_clear:
			case R.id.button_divide:
			case R.id.button_multiply:
				layers[1] = getResources().getDrawable(
						R.drawable.button_custom_top);
				layers[0] = getResources().getDrawable(
						R.drawable.button_custom_top_pressed);
				transition = new TransitionDrawable(layers);
				v.setBackground(transition);
				transition.startTransition(100);
				break;

			case R.id.button_minus:
			case R.id.button_plus:
			case R.id.button_openPar:
				layers[1] = getResources().getDrawable(
						R.drawable.button_custom_right);
				layers[0] = getResources().getDrawable(
						R.drawable.button_custom_right_pressed);
				transition = new TransitionDrawable(layers);
				v.setBackground(transition);
				transition.startTransition(100);
				break;

			case R.id.button_0:
			case R.id.button_dot:
			case R.id.button_pow:
				layers[1] = getResources().getDrawable(
						R.drawable.button_custom_bot);
				layers[0] = getResources().getDrawable(
						R.drawable.button_custom_bot_pressed);
				transition = new TransitionDrawable(layers);
				v.setBackground(transition);
				transition.startTransition(100);
				break;

			case R.id.button_delete:
				layers[1] = getResources().getDrawable(
						R.drawable.button_custom_top_right);
				layers[0] = getResources().getDrawable(
						R.drawable.button_custom_top_right_pressed);
				transition = new TransitionDrawable(layers);
				v.setBackground(transition);
				transition.startTransition(100);
				break;

			case R.id.button_closedPar:
				layers[1] = getResources().getDrawable(
						R.drawable.button_custom_bot_right);
				layers[0] = getResources().getDrawable(
						R.drawable.button_custom_bot_right_pressed);
				transition = new TransitionDrawable(layers);
				v.setBackground(transition);
				transition.startTransition(100);
				break;
			}
		}
	}

	// Initialize a single button enabling feedback, custom font and setting up
	// listeners
	private void initializeButton(int btnId, int hapticId) {
		Button btn = (Button) findViewById(btnId);
		btn.setHapticFeedbackEnabled(true);
		btn.setTypeface(type);
		HapticTouchListener HapListener = new HapticTouchListener(hapticId);
		btn.setOnTouchListener(HapListener);
		btn.setOnClickListener(HapListener);
	}

	// Initialize all buttons
	private void initAllButtons() {
		initializeButton(R.id.button_0, HapticFeedbackConstants.KEYBOARD_TAP);
		initializeButton(R.id.button_1, HapticFeedbackConstants.KEYBOARD_TAP);
		initializeButton(R.id.button_2, HapticFeedbackConstants.KEYBOARD_TAP);
		initializeButton(R.id.button_3, HapticFeedbackConstants.KEYBOARD_TAP);
		initializeButton(R.id.button_4, HapticFeedbackConstants.KEYBOARD_TAP);
		initializeButton(R.id.button_5, HapticFeedbackConstants.KEYBOARD_TAP);
		initializeButton(R.id.button_6, HapticFeedbackConstants.KEYBOARD_TAP);
		initializeButton(R.id.button_7, HapticFeedbackConstants.KEYBOARD_TAP);
		initializeButton(R.id.button_8, HapticFeedbackConstants.KEYBOARD_TAP);
		initializeButton(R.id.button_9, HapticFeedbackConstants.KEYBOARD_TAP);
		initializeButton(R.id.button_clear,
				HapticFeedbackConstants.KEYBOARD_TAP);
		initializeButton(R.id.button_closedPar,
				HapticFeedbackConstants.KEYBOARD_TAP);
		initializeButton(R.id.button_delete,
				HapticFeedbackConstants.KEYBOARD_TAP);
		initializeButton(R.id.button_divide,
				HapticFeedbackConstants.KEYBOARD_TAP);
		initializeButton(R.id.button_dot, HapticFeedbackConstants.KEYBOARD_TAP);
		initializeButton(R.id.button_minus,
				HapticFeedbackConstants.KEYBOARD_TAP); 
		initializeButton(R.id.button_multiply,
				HapticFeedbackConstants.KEYBOARD_TAP);
		initializeButton(R.id.button_openPar,
				HapticFeedbackConstants.KEYBOARD_TAP);
		initializeButton(R.id.button_plus, HapticFeedbackConstants.KEYBOARD_TAP);
		initializeButton(R.id.button_pow, HapticFeedbackConstants.KEYBOARD_TAP);
		initializeButton(R.id.button_0, HapticFeedbackConstants.KEYBOARD_TAP);
	}

}
