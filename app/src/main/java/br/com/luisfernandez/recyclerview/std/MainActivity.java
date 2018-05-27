package br.com.luisfernandez.recyclerview.std;

import android.os.Bundle;
import android.support.annotation.NonNull;
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

    private CustomRecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        this.loadData();

        new ItemTouchHelper(
                new AppSimpleCallback(
                        0,
                        ItemTouchHelper.LEFT,
                        recyclerView
                )
        ).attachToRecyclerView(recyclerView);
    }

    private void loadData()
    {
        new MockService(this).loadCountryList(this.getDataCallback());
    }

    @NonNull
    private MockService.DataCallback<List<Country>> getDataCallback()
    {
        return new MockService.DataCallback<List<Country>>()
        {
            @Override
            public void onLoadSuccess(List<Country> data)
            {
                recyclerView.setAdapter(new StadiumAdapter(data));
            }
        };
    }
}
