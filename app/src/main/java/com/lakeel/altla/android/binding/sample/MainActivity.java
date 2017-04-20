package com.lakeel.altla.android.binding.sample;

import com.lakeel.altla.android.binding.BindingMode;
import com.lakeel.altla.android.binding.ViewBindingFactory;
import com.lakeel.altla.android.binding.command.RelayCommand;
import com.lakeel.altla.android.binding.converter.RelayConverter;
import com.lakeel.altla.android.property.BooleanProperty;
import com.lakeel.altla.android.property.IntProperty;
import com.lakeel.altla.android.property.StringProperty;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public final class MainActivity extends AppCompatActivity {

    private final ViewModel viewModel = new ViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel.onCreate(savedInstanceState);

        ViewBindingFactory bindingFactory = new ViewBindingFactory(this);

        bindingFactory.create(R.id.radio_group_button, "checkedButton", viewModel.radioGroupChecked).bind();
        bindingFactory.create(R.id.text_view_set_text, "text", viewModel.textViewText).bind();
        bindingFactory.create(R.id.edit_text, "text", viewModel.editTextText).bind();
        bindingFactory.create(R.id.text_view_edit_text_result, "text", viewModel.editTextText)
                      .mode(BindingMode.ONE_WAY)
                      .bind();
        bindingFactory.create(R.id.toggle_button, "checked", viewModel.toggleButtonChecked).bind();
        bindingFactory.create(R.id.text_view_toggle_button_result, "text", viewModel.toggleButtonChecked)
                      .mode(BindingMode.ONE_WAY)
                      .converter(new RelayConverter(value -> value == null ? null : value.toString()))
                      .bind();
        bindingFactory.create(R.id.edit_text_toggle_enabled, "enabled", viewModel.toggleButtonChecked)
                      .mode(BindingMode.ONE_WAY)
                      .bind();

        bindingFactory.create(R.id.button_on_click, "onClick", viewModel.commandClick).bind();
        bindingFactory.create(R.id.button_set_text, "onClick", new RelayCommand(viewModel::setTextViewText)).bind();
        bindingFactory.create(R.id.button_clear_text, "onClick", new RelayCommand(viewModel::clearTextViewText)).bind();
        bindingFactory
                .create(R.id.button_edit_text_clear_text, "onClick", new RelayCommand(viewModel::clearEditTextText))
                .bind();
        bindingFactory.create(R.id.text_view_on_long_click, "onLongClick", new RelayCommand(viewModel::onLongClick))
                      .bind();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        viewModel.onSaveInstanceState(outState);
    }

    public final class ViewModel {

        private static final String STATE_RADIO_GROUP_CHECKED = "radioGroupChecked";

        private static final String STATE_TEXT_VIEW_TEXT = "setTextViewText";

        private static final String STATE_EDIT_TEXT_TEXT = "editTextText";

        private static final String STATE_TOGGLE_BUTTON_CHECKED = "toggleButtonChecked";

        public IntProperty radioGroupChecked;

        public final RelayCommand commandClick = new RelayCommand(
                () -> Toast.makeText(MainActivity.this, "onClick", Toast.LENGTH_SHORT).show(),
                () -> radioGroupChecked.get() == R.id.radio_button_button_enabled);

        public StringProperty textViewText;

        public StringProperty editTextText;

        public BooleanProperty toggleButtonChecked;

        private ViewModel() {
        }

        void onCreate(Bundle savedInstanceState) {
            if (savedInstanceState == null) {
                radioGroupChecked = new IntProperty(R.id.radio_button_button_disabled);
                textViewText = new StringProperty();
                editTextText = new StringProperty();
                toggleButtonChecked = new BooleanProperty();
            } else {
                radioGroupChecked = savedInstanceState.getParcelable(STATE_RADIO_GROUP_CHECKED);
                textViewText = savedInstanceState.getParcelable(STATE_TEXT_VIEW_TEXT);
                editTextText = savedInstanceState.getParcelable(STATE_EDIT_TEXT_TEXT);
                toggleButtonChecked = savedInstanceState.getParcelable(STATE_TOGGLE_BUTTON_CHECKED);
            }

            radioGroupChecked.addOnValueChangedListener(sender -> {
                Log.v("DEBUG", "raiseOnCanExecuteChanged(): canExecute = " + commandClick.canExecute());
                commandClick.raiseOnCanExecuteChanged();
            });
        }

        void onSaveInstanceState(Bundle outState) {
            outState.putParcelable(STATE_RADIO_GROUP_CHECKED, radioGroupChecked);
            outState.putParcelable(STATE_TEXT_VIEW_TEXT, textViewText);
            outState.putParcelable(STATE_EDIT_TEXT_TEXT, editTextText);
            outState.putParcelable(STATE_TOGGLE_BUTTON_CHECKED, toggleButtonChecked);
        }

        private void setTextViewText() {
            textViewText.set("Text was set.");
        }

        private void clearTextViewText() {
            textViewText.set(null);
        }

        private void clearEditTextText() {
            editTextText.set(null);
        }

        private void onLongClick() {
            Toast.makeText(MainActivity.this, "onLongClick", Toast.LENGTH_SHORT).show();
        }
    }
}
