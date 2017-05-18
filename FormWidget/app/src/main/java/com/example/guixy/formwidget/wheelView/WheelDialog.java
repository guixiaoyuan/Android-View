package com.example.guixy.formwidget.wheelView;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.guixy.formwidget.FormWidget;
import com.example.guixy.formwidget.R;


/**
 * Function
 * 滚轮选择对话框
 *
 * @author guixy
 * @version 1.0, 12/04/2017
 * @see [相关类/方法]
 * @since [农人帮/V1.1.2]
 */
public class WheelDialog {
    private static final String TAG = WheelDialog.class.getSimpleName();

    private WheelView wheelView;

    private TextView titleTextView;

    private TextView cancelTextView;

    private TextView confirmTextView;

    private AlertDialog dialog;


    public WheelDialog(Context context) {
        dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(R.layout.dialog_dynamic_type);
        wheelView = (WheelView) window.findViewById(R.id.dialog_dynamic_wheel_view_type);
        titleTextView = (TextView) window.findViewById(R.id.pop_dialog_title);
        cancelTextView = (TextView) window.findViewById(R.id.dialog_dynamic_cancel);
        confirmTextView = (TextView) window.findViewById(R.id.dialog_dynamic_confirm);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = 745;
        window.setAttributes(params);
    }

/*    public void setWheelView(final TextView view, final String[] types, String title) {
        wheelView.setVisibleItems(3);
        wheelView.setViewAdapter(new ArrayWheelAdapter<>(getContext(), types));
        wheelView.setCurrentItem(getCurrentPosition(view.getText().toString(), types));
        titleTextView.setText(title);
        //取消按钮的点击事件
        cancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        confirmTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setText(types[wheelView.getCurrentItem()]);
                dialog.dismiss();
            }
        });
    }*/

    public void setWheelView(final FormWidget view, final String[] types, String title) {
        wheelView.setVisibleItems(3);
        wheelView.setViewAdapter(new ArrayWheelAdapter<>(view.getContext(), types));
        wheelView.setCurrentItem(getCurrentPosition(view.getContent(), types));
        titleTextView.setText(title);
        //取消按钮的点击事件
        cancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        confirmTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setContent(types[wheelView.getCurrentItem()]);
                dialog.dismiss();
            }
        });
    }

    private int getCurrentPosition(String typeString, String[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(typeString)) {
                return i;
            }
        }
        return 0;
    }

    public void dissDialog() {
        dialog.dismiss();
    }
}
