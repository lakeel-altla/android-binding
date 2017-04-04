package com.lakeel.altla.android.binding.sample;

import com.lakeel.altla.android.binding.BindCommand;
import com.lakeel.altla.android.binding.BindProperties;
import com.lakeel.altla.android.binding.BindProperty;
import com.lakeel.altla.android.binding.BinderFactory;
import com.lakeel.altla.android.binding.BooleanProperty;
import com.lakeel.altla.android.binding.CommandName;
import com.lakeel.altla.android.binding.Converter;
import com.lakeel.altla.android.binding.ConverterName;
import com.lakeel.altla.android.binding.IntProperty;
import com.lakeel.altla.android.binding.ObjectProperty;
import com.lakeel.altla.android.binding.PropertyName;
import com.lakeel.altla.android.binding.RelayCommand;
import com.lakeel.altla.android.binding.RelayConverter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.Objects;

public final class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private final ViewModel viewModel = new ViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BinderFactory binderFactory = new BinderFactory(this);
        binderFactory.create(viewModel).bind();
    }

    private final class ViewModel {

        @BindProperty(id = R.id.radio_group_button, name = PropertyName.CHECKED_BUTTON)
        IntProperty radioGroupChecked = new IntProperty() {

            int value = R.id.radio_button_button_disabled;

            @Override
            public int get() {
                return value;
            }

            @Override
            public void set(int value) {
                if (this.value != value) {
                    this.value = value;
                    raiseOnValueChanged();
                    commandClick.raiseOnCanExecuteChanged();
                }
            }
        };

        @BindCommand(id = R.id.button_on_click, name = CommandName.ON_CLICK)
        RelayCommand commandClick = new RelayCommand(
                () -> Toast.makeText(MainActivity.this, "onClick", Toast.LENGTH_SHORT).show(),
                () -> radioGroupChecked.get() == R.id.radio_button_button_enabled);

        @BindProperty(id = R.id.text_view_set_text, name = PropertyName.TEXT)
        ObjectProperty<String> textViewText = new ObjectProperty<String>() {

            String value;

            @Nullable
            @Override
            public String get() {
                return value;
            }

            @Override
            public void set(@Nullable String value) {
                if (!Objects.equals(this.value, value)) {
                    this.value = value;
                    raiseOnValueChanged();
                }
            }
        };

        @BindCommand(id = R.id.button_set_text, name = CommandName.ON_CLICK)
        RelayCommand commandTextViewText = new RelayCommand(() -> textViewText.set("Text was set."));

        @BindCommand(id = R.id.button_clear_text, name = CommandName.ON_CLICK)
        RelayCommand commandClearTextViewText = new RelayCommand(() -> textViewText.set(null));

        @BindProperties({ @BindProperty(id = R.id.edit_text, name = PropertyName.TEXT),
                          @BindProperty(id = R.id.text_view_edit_text_result, name = PropertyName.TEXT) })
        ObjectProperty<String> editTextText = new ObjectProperty<String>() {

            String value;

            @Nullable
            @Override
            public String get() {
                return value;
            }

            @Override
            public void set(@Nullable String value) {
                if (!Objects.equals(this.value, value)) {
                    Log.v(TAG, "set: " + value);
                    this.value = value;
                    raiseOnValueChanged();
                }
            }
        };

        @BindCommand(id = R.id.button_edit_text_clear_text, name = CommandName.ON_CLICK)
        RelayCommand commandClearEditTextText = new RelayCommand(() -> editTextText.set(null));

        @BindCommand(id = R.id.text_view_on_long_click, name = CommandName.ON_LONG_CLICK)
        RelayCommand commandLongClick = new RelayCommand(
                () -> Toast.makeText(MainActivity.this, "onLongClick", Toast.LENGTH_SHORT).show());

        @BindProperties({ @BindProperty(id = R.id.toggle_button, name = PropertyName.CHECKED),
                          @BindProperty(id = R.id.text_view_toggle_button_result, name = PropertyName.TEXT,
                                        converter = "objectStringConverter"),
                          @BindProperty(id = R.id.edit_text_toggle_enabled, name = PropertyName.ENABLED) })
        BooleanProperty toggleButtonChecked = new BooleanProperty() {

            boolean value;

            @Override
            public boolean get() {
                return value;
            }

            @Override
            public void set(boolean value) {
                if (this.value != value) {
                    this.value = value;
                    raiseOnValueChanged();
                }
            }
        };

        @ConverterName("objectStringConverter")
        Converter objectStringConverter = new RelayConverter(value -> value == null ? null : value.toString(),
                                                             null);
    }
}
