package com.example.wordDemoImprove;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


public class AddFragment extends Fragment {

    private Button buttonSubmit;
    private EditText editTextEnglish, editTextChinese;
    private WordViewModel wordViewModel;

    public AddFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        wordViewModel = new ViewModelProvider(requireActivity()).get(WordViewModel.class);
        buttonSubmit = requireActivity().findViewById(R.id.buttonSubmit);
        editTextEnglish = requireActivity().findViewById(R.id.editTextEnglish);
        editTextChinese = requireActivity().findViewById(R.id.editTextChinese);
        buttonSubmit.setEnabled(false);
        editTextEnglish.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editTextEnglish, 0);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String english = editTextEnglish.getText().toString().trim();
                String chinese = editTextChinese.getText().toString().trim();
                buttonSubmit.setEnabled(!english.isEmpty() && !chinese.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        editTextEnglish.addTextChangedListener(textWatcher);
        editTextChinese.addTextChangedListener(textWatcher);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String english = editTextEnglish.getText().toString().trim();
                String chinese = editTextChinese.getText().toString().trim();
                Word word = new Word(english, chinese);
                wordViewModel.InsertWords(word);
                NavController navController = Navigation.findNavController(v);
                navController.navigateUp();
                InputMethodManager inputMethodManager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });


    }
}