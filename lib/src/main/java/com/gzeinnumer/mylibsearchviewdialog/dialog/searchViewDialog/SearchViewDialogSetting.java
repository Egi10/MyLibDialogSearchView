package com.gzeinnumer.mylibsearchviewdialog.dialog.searchViewDialog;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.gzeinnumer.mylibsearchviewdialog.R;
import com.gzeinnumer.mylibsearchviewdialog.constant.ButtonStyle;
import com.gzeinnumer.mylibsearchviewdialog.constant.SelectType;
import com.gzeinnumer.mylibsearchviewdialog.dialog.BaseDialog;
import com.gzeinnumer.mylibsearchviewdialog.dialog.searchViewDialog.adapter.MyHolderSingle;
import com.gzeinnumer.mylibsearchviewdialog.dialog.searchViewDialog.adapter.RvItemAdapter;
import com.gzeinnumer.mylibsearchviewdialog.model.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchViewDialogSetting extends BaseDialog implements MyHolderSingle.OnItemSelectedListener {

    protected SearchViewDialog.OnCancelPressed onCancelPressed;

    protected SearchViewDialog.OnOkPressedSingle onOkPressedSingle;
    protected SearchViewDialog.OnOkPressedMulti onOkPressedMulti;

    private View _view;
    private LinearLayout _dialogCanvas;
    private LinearLayout _parentButton;
    private TextView _tvTitle;
    private TextView _tvContent;
    private Button _dBtnCancelMBT;
    private Button _dBtnCancelMBO;
    private Button _dBtnCancelMBC;
    private Button _dBtnOkMBT;
    private Button _dBtnOkMBO;
    private Button _dBtnOkMBC;
    private RecyclerView _rv;
    private TextInputEditText _edSearch;
    protected ButtonStyle btnStyle;
    protected int type = SelectType.TYPE_SINGLE;

    protected String tvTitleValue;
    protected String tvContentValue;
    protected String dBtnCancelValue;
    protected String dBtnOkValue;

    protected Drawable shapeCanvas = null;
    protected double tvTitleSize = 0;
    protected double tvContentSize = 0;
    protected double dBtnTextSize = 0;

    protected int tvTitleColor = 0;
    protected int tvContentColor = 0;
    protected int btnTextColorOk = 0;
    protected int btnTextColorCancel = 0;

    protected int okIconL = 0;
    protected int okIconT = 0;
    protected int okIconR = 0;
    protected int okIconB = 0;
    protected int cancelIconL = 0;
    protected int cancelIconT = 0;
    protected int cancelIconR = 0;
    protected int cancelIconB = 0;

    protected int buttonGravity = -100;

    protected int tvTitleAlignment = View.TEXT_ALIGNMENT_CENTER; //default from view
    protected int tvContentAlignment = View.TEXT_ALIGNMENT_TEXT_START; //default from view

    protected int listHeight = 0;

    protected ArrayList<String> listFromUser = new ArrayList<>();
    protected ArrayList<SearchViewModel> list = new ArrayList<>();
    private RvItemAdapter _adapter;

    protected int textListColor = -100;
    protected int textSearchColor = -100;
    protected int textListSize = -100;
    protected int textSearchSize = -100;

    private void initView() {
        _dialogCanvas = _view.findViewById(R.id.dialog_canvas);
        _parentButton = _view.findViewById(R.id.parent_button);
        _tvTitle = _view.findViewById(R.id.tv_title);
        _tvContent = _view.findViewById(R.id.tv_content);
        _dBtnCancelMBT = _view.findViewById(R.id.d_btn_cancel_MBT);
        _dBtnCancelMBO = _view.findViewById(R.id.d_btn_cancel_MBO);
        _dBtnCancelMBC = _view.findViewById(R.id.d_btn_cancel_MBC);
        _dBtnOkMBT = _view.findViewById(R.id.d_btn_ok_MBT);
        _dBtnOkMBO = _view.findViewById(R.id.d_btn_ok_MBO);
        _dBtnOkMBC = _view.findViewById(R.id.d_btn_ok_MBC);
        _rv = _view.findViewById(R.id.rv_item);
        _edSearch = _view.findViewById(R.id.ed_search);
    }

    @Override
    protected int contentView() {
        if (isFullScreen){
            return R.layout.select_dialog_fullscreen;
        } else {
            return R.layout.select_dialog;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this._view = view;
        initView();
        initDesign();
        initOnClick();
        initTextView();
    }

    private void initDesign() {
        if (shapeCanvas!=null)
            _dialogCanvas.setBackground(shapeCanvas);

        if (tvTitleValue != null)
            _tvTitle.setText(tvTitleValue);
        else
            _tvTitle.setVisibility(View.GONE);

        if (tvContentValue != null)
            _tvContent.setText(tvContentValue);
        else
            _tvContent.setVisibility(View.GONE);

        if (dBtnCancelValue != null) {
            _dBtnCancelMBT.setText(dBtnCancelValue);
            _dBtnCancelMBO.setText(dBtnCancelValue);
            _dBtnCancelMBC.setText(dBtnCancelValue);
        }

        if (dBtnOkValue != null){
            _dBtnOkMBT.setText(dBtnOkValue);
            _dBtnOkMBO.setText(dBtnOkValue);
            _dBtnOkMBC.setText(dBtnOkValue);
        }

        if (btnStyle != null) {
            if (btnStyle == ButtonStyle.ButtonText) {
                btnVisibleCancel(_dBtnCancelMBT, View.VISIBLE);
                btnVisibleOk(_dBtnOkMBT, View.VISIBLE);
            }
            if (btnStyle == ButtonStyle.ButtonOutlined) {
                btnVisibleCancel(_dBtnCancelMBO, View.VISIBLE);
                btnVisibleOk(_dBtnOkMBO, View.VISIBLE);
            }
            if (btnStyle == ButtonStyle.ButtonContained) {
                btnVisibleCancel(_dBtnCancelMBC, View.VISIBLE);
                btnVisibleOk(_dBtnOkMBC, View.VISIBLE);
            }
        } else {
            btnVisibleOk(_dBtnCancelMBT, View.VISIBLE);
            btnVisibleOk(_dBtnOkMBT, View.VISIBLE);
        }

        if (tvTitleSize!=0)
            _tvTitle.setTextSize((float) tvTitleSize);

        if (tvContentSize!=0)
            _tvContent.setTextSize((float) tvContentSize);

        if (dBtnTextSize!=0){
            _dBtnCancelMBT.setTextSize((float) dBtnTextSize);
            _dBtnCancelMBO.setTextSize((float) dBtnTextSize);
            _dBtnCancelMBC.setTextSize((float) dBtnTextSize);
            _dBtnOkMBT.setTextSize((float) dBtnTextSize);
            _dBtnOkMBO.setTextSize((float) dBtnTextSize);
            _dBtnOkMBC.setTextSize((float) dBtnTextSize);
        }

        if (tvTitleColor!=0)
            _tvTitle.setTextColor(tvTitleColor);

        if (tvContentColor!=0)
            _tvContent.setTextColor(tvContentColor);

        if (btnTextColorCancel!=0){
            _dBtnCancelMBT.setTextColor(btnTextColorCancel);
            _dBtnCancelMBO.setTextColor(btnTextColorCancel);
            _dBtnCancelMBC.setTextColor(btnTextColorCancel);
        }

        if (btnTextColorOk!=0){
            _dBtnOkMBT.setTextColor(btnTextColorOk);
            _dBtnOkMBO.setTextColor(btnTextColorOk);
            _dBtnOkMBC.setTextColor(btnTextColorOk);
        }

        if(buttonGravity!=-100){
            _parentButton.setGravity(buttonGravity);
        }

        if(tvTitleAlignment != View.TEXT_ALIGNMENT_CENTER){
            _tvTitle.setTextAlignment(tvTitleAlignment);
        }

        if(tvContentAlignment != View.TEXT_ALIGNMENT_TEXT_START){
            _tvContent.setTextAlignment(tvContentAlignment);
        }

        if (listFromUser.size()>0){
            for (int i = 0; i<listFromUser.size(); i++){
                list.add(new SearchViewModel(i, listFromUser.get(i)));
            }
            _adapter = new RvItemAdapter(type, this, list);

            if (textListColor != -100)
                _adapter.setTextItemColor(textListColor);

            if (textListSize != -100)
                _adapter.setTextItemSize(textListSize);

            if (textSearchColor != -100)
                _edSearch.setTextColor(textSearchColor);

            if (textSearchSize != -100)
                _edSearch.setTextSize((float) textSearchSize);

            _rv.setAdapter(_adapter);
            _rv.setLayoutManager(new LinearLayoutManager(requireContext()));
            _rv.setHasFixedSize(true);
        }

        if (listHeight>0 && !isFullScreen){
            ViewGroup.LayoutParams params =_rv.getLayoutParams();
            params.height=listHeight;
            _rv.setLayoutParams(params);
        }
    }

    private void initOnClick() {
        _dBtnCancelMBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCancelPressed != null)
                    onCancelPressed.onCancelPressed();
                getDialog().dismiss();
            }
        });

        _dBtnOkMBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOkPressedSingle != null || onOkPressedMulti!=null)
                    actionOk();
            }
        });

        _dBtnCancelMBO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCancelPressed != null)
                    onCancelPressed.onCancelPressed();
                getDialog().dismiss();
            }
        });

        _dBtnOkMBO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOkPressedSingle != null || onOkPressedMulti!=null)
                    actionOk();
            }
        });

        _dBtnCancelMBC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCancelPressed != null)
                    onCancelPressed.onCancelPressed();
                getDialog().dismiss();
            }
        });

        _dBtnOkMBC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onOkPressedSingle != null || onOkPressedMulti!=null)
                    actionOk();
            }
        });
    }

    private void initTextView() {
        _edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                _adapter.getFilter().filter(s.toString());
            }
        });
    }

    private static final String TAG = "SearchViewDialogSetting";

    private void actionOk() {
        List<SearchViewModel> selectedItems = _adapter.getSelectedItems();
        Log.d(TAG, "actionOk: "+selectedItems);
        if (selectedItems.size()>0) {
            if (type == SelectType.TYPE_SINGLE) {
                onOkPressedSingle.onOkSingle(selectedItems.get(0).getPosition(), selectedItems.get(0).getName());
            } else {
                onOkPressedMulti.onOkMulti(selectedItems);
            }
        }
        getDialog().dismiss();
    }

    private void btnVisibleOk(Button button, int visible) {
        button.setVisibility(visible);
        button.setVisibility(visible);
        if (okIconL != 0 || okIconT != 0 || okIconR != 0 || okIconB != 0) {
            if (okIconT !=0 || okIconB !=0){
                ViewGroup.LayoutParams lp = button.getLayoutParams();
                lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                button.setLayoutParams(lp);
            }
            button.setCompoundDrawablesWithIntrinsicBounds(okIconL, okIconT, okIconR, okIconB);
        }
    }

    private void btnVisibleCancel(Button button, int visible) {
        button.setVisibility(visible);
        button.setVisibility(visible);
        if (cancelIconL != 0 || cancelIconT != 0 || cancelIconR != 0 || cancelIconB != 0) {
            if (cancelIconT !=0 || cancelIconB !=0){
                ViewGroup.LayoutParams lp = button.getLayoutParams();
                lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                button.setLayoutParams(lp);
            }
            button.setCompoundDrawablesWithIntrinsicBounds(cancelIconL, cancelIconT, cancelIconR, cancelIconB);
        }
    }
    List<SearchViewModel> selectedItems;
    @Override
    public void onItemSelected(SearchViewModel item) {

        List<SearchViewModel> selectedItems = _adapter.getSelectedItems();
        Log.d(TAG, "onItemSelected: "+selectedItems.size());
    }
}
