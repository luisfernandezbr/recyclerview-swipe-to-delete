package br.com.luisfernandez.recyclerview.std;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import java.util.List;

import br.com.luisfernandez.recyclerview.std.adapter.StadiumAdapter;
import br.com.luisfernandez.recyclerview.std.pojo.Stadium;
import br.com.luisfernandez.recyclerview.std.service.MockService;

public class MainActivity extends AppCompatActivity
{

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final CustomRecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new MockService(this).loadStadiumList(new MockService.DataCallback<List<Stadium>>()
        {
            @Override
            public void onLoadSuccess(List<Stadium> data)
            {
                recyclerView.setAdapter(new StadiumAdapter(MainActivity.this, data));
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(this.getSimpleCallback());

        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @NonNull
    private ItemTouchHelper.SimpleCallback getSimpleCallback()
    {
        return new AppSimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
    }
}
