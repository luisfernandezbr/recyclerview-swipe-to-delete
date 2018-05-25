package br.com.luisfernandez.recyclerview.std;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import br.com.luisfernandez.recyclerview.std.adapter.StadiumAdapter;
import br.com.luisfernandez.recyclerview.std.pojo.Stadium;
import br.com.luisfernandez.recyclerview.std.service.MockService;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new MockService(this).loadStadiumList(new MockService.DataCallback<List<Stadium>>()
        {
            @Override
            public void onLoadSuccess(List<Stadium> data)
            {
                recyclerView.setAdapter(new StadiumAdapter(MainActivity.this, data));
            }
        });
    }
}
