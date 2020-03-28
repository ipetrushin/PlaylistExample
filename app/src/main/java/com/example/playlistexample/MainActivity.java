package com.example.playlistexample;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    DBHelper helper;
    SQLiteDatabase db;
    ListView playlist;
    SimpleCursorAdapter adapter;

    public void refreshDataset() {
        // (1) удобно реализовать отображение данных в отдельной функции
        // её можно вызвать при любом изменении данных
    }

    // (2) https://developer.android.com/guide/topics/ui/controls/togglebutton#java
    // реализовать порядок сортировки записей, заданных позицией переключателя
    // поле для сортировки выбирайте сами
    // мы реализуем обработчик нажатий в классе MainActivity (реализуем интерфейс CompoundButton.OnCheckedChangeListener )
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        // в зависимости от того, какую кнопку нажали, меняем статус переменных
        if (isChecked) {
            // The toggle is enabled
        } else {
            // The toggle is disabled
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playlist = findViewById(R.id.playlist);

        DBHelper helper = new DBHelper(this);
        db = helper.getWritableDatabase();

        Cursor tunes = db.rawQuery("SELECT * FROM playlist", null );
        // содержит выборку всех записей
        Log.d("mytag", "records in cursor: " + tunes.getCount());
        String[] playlist_fields = tunes.getColumnNames();
        // playlist_fields = {"_id", ...} ;

        // int[] - ссылки на id элементов разметки playlist_item
        // полученный Cursor использовать для создания адаптера
        // готовый адаптер назначить для ListView
        int[] views = { R.id.id, R.id.artist, R.id.title, R.id.year, R.id.duration };

         adapter = new SimpleCursorAdapter(this, R.layout.playlist, tunes , playlist_fields, views, 0);
        playlist.setAdapter(adapter);


    }
    public void onClick(View v) {
        Object[] args = {"K4", "Title123",  0}; // значения для подстановки в запрос
        // можно явно указать все значения
        // db.execSQL("INSERT INTO playlist (artist, title, year, duration) values ('K2', 'Track', 1992, 100)");

        // или подставить в шаблон, можно использовать любые функции, доступные в языке SQL
        db.execSQL("INSERT INTO playlist (artist, title, year, duration) values ('?', '?', random(), ?)", args);
        // execSQL позволяет подставить значения в запрос

        // не забывайте выбрать записи заново и обновить источник данных в адаптере
        adapter.changeCursor(db.rawQuery("SELECT * FROM playlist ORDER BY year", null ));
    }
    public void onClearClick(View v) {
        try {
            db.execSQL("DELETE FROM playlist");

        } catch (SQLException e) { Log.d("mytag", e.getLocalizedMessage()); }

        adapter.changeCursor(db.rawQuery("SELECT * FROM playlist ORDER BY year DESC", null ));
    }
}
