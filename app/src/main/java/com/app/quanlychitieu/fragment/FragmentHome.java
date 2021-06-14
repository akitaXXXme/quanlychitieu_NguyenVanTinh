package com.app.quanlychitieu.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.app.quanlychitieu.MainActivity;
import com.app.quanlychitieu.R;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentHome extends Fragment {

    TextView chitiet, sotien, txthanmuc, tienhanmuc;

    NumberFormat numberFormat = NumberFormat.getInstance();
    SharedPreferences sharedPreferences;
    String hanmuc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        sharedPreferences = getContext().getSharedPreferences("hanmuc", Context.MODE_PRIVATE);
        hanmuc = sharedPreferences.getString("hanmuc", "null");
        anhXa(view);
        return view;
    }

    public void anhXa(View view) {
        chitiet = view.findViewById(R.id.xemghichep);
        sotien = view.findViewById(R.id.sotien);
        txthanmuc = view.findViewById(R.id.hanmuctc);
        tienhanmuc = view.findViewById(R.id.hanmuc);
        tienhanmuc.setText(hanmuc);
        Calendar calendar = Calendar.getInstance();
        Integer thang = calendar.get(Calendar.MONTH) + 1;
        Float tien = MainActivity.khoanThuChiDao.getTongtien(thang, 1);
        sotien.setText(numberFormat.format(tien) + "đ");

        txthanmuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHanMuc();
            }
        });

        chitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getContext();
                mainActivity.addFragmentLsthuchi();
            }
        });


    }


    public void showHanMuc() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.popup_hanmuc);
        Button thoat = dialog.findViewById(R.id.btnthoat);
        thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        final EditText editText = dialog.findViewById(R.id.edtsodu);
        Button luu = dialog.findViewById(R.id.btnluu);
        luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Float f = Float.valueOf(editText.getText().toString());
                hanmuc = numberFormat.format(f) + "đ";
                tienhanmuc.setText(hanmuc);
                editor.putString("hanmuc", hanmuc);
                editor.commit();
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}
