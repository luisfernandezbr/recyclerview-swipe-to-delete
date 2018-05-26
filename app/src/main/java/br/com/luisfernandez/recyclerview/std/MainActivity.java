package br.com.luisfernandez.recyclerview.std;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.List;

import br.com.luisfernandez.recyclerview.std.adapter.StadiumAdapter;
import br.com.luisfernandez.recyclerview.std.pojo.Country;
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

        new MockService(this).loadCountryList(new MockService.DataCallback<List<Country>>()
        {
            @Override
            public void onLoadSuccess(List<Country> data)
            {
                recyclerView.setAdapter(new StadiumAdapter(data));
            }
        });

        AppSimpleCallback appSimpleCallback = new AppSimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, recyclerView);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(appSimpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
