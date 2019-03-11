package com.abed.leitnerflashcards.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.abed.leitnerflashcards.R;
import com.abed.leitnerflashcards.data.Repository;

public class ManageActivity extends AppCompatActivity {

    private CardsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView rcv = findViewById(R.id.rcv);

        adapter = new CardsListAdapter(this);
        rcv.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcv.setLayoutManager(layoutManager);
        rcv.addItemDecoration(new DividerItemDecoration(rcv.getContext(), layoutManager.getOrientation()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Repository.getInstance(getApplication()).getAllCards().addOnSuccessListener(adapter::setCards);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_manage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {
            startActivity(new Intent(this, AddActivity.class));
        } else if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public boolean onNavigateUp(){
        finish();
        return true;
    }
}
