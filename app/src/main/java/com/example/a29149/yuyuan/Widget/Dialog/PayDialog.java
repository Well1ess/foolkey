package com.example.a29149.yuyuan.Widget.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a29149.yuyuan.R;


public class PayDialog extends Dialog {

    public PayDialog(Context context) {

        super(context);
    }

    public PayDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Dialog dialog;
        private Context context;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;

        private TextView mAmount;
        private TextView mVirtualMoney;
        private EditText mPassword;
        private int position;

        public Builder(Context context) {
            this.context = context;
        }

        public Dialog getDialog() {
            return dialog;
        }

        public void setDialog(Dialog dialog) {
            this.dialog = dialog;
        }


        public String getmAmount() {
            return mAmount.getText().toString();
        }

        public void setmAmount(String mAmount) {
            this.mAmount.setText(mAmount);
        }

        public String getmVirtualMoney() {
            return mVirtualMoney.getText().toString();
        }

        public void setmVirtualMoney(String mVirtualMoney) {
            this.mVirtualMoney.setText(mVirtualMoney);
        }

        public String getmPassword() {
            return mPassword.getText().toString();
        }

        public void setmPassword(String mPassword) {
            this.mPassword.setText(mPassword);
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public Builder setPositiveButton(int positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public PayDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final PayDialog dialog = new PayDialog(context, R.style.Base_Theme_AppCompat_Dialog);
            View layout = inflater.inflate(R.layout.dialog_my_del_team_layout, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));


            if (positiveButtonText != null) {
                ((Button) layout.findViewById(R.id.info_fragment2_new_msg_bt_commit))
                        .setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.info_fragment2_new_msg_bt_commit))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    positiveButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_POSITIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.info_fragment2_new_msg_bt_commit).setVisibility(
                        View.GONE);
            }
            // set the cancel button
            if (negativeButtonText != null) {
                ((Button) layout.findViewById(R.id.info_fragment2_new_msg_bt_giveup))
                        .setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    ((Button) layout.findViewById(R.id.info_fragment2_new_msg_bt_giveup))
                            .setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    negativeButtonClickListener.onClick(dialog,
                                            DialogInterface.BUTTON_NEGATIVE);
                                }
                            });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.info_fragment2_new_msg_bt_giveup).setVisibility(
                        View.GONE);
            }
            mAmount = (TextView) dialog.findViewById(R.id.amount);
            mVirtualMoney = (TextView) dialog.findViewById(R.id.virtual_money);
            mPassword = (EditText) dialog.findViewById(R.id.password);

            dialog.setContentView(layout);
            //dialog.setCanceledOnTouchOutside(false);

            this.dialog = dialog;
            return dialog;
        }

    }
}
