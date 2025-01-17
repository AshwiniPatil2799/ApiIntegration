package com.example.testapptransactions.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.testapptransactions.Data.TransactionEntity;
import com.example.testapptransactions.Modal.TransactionViewModel;
import com.example.testapptransactions.R;
import com.example.testapptransactions.TransactionAdapter;
import com.example.testapptransactions.Utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TransactionsActivity extends AppCompatActivity {

    private RecyclerView transactionsRecyclerView;
    private SearchView searchEditText;
    private Toolbar toolbar;

    private TransactionAdapter adapter;
    private List<TransactionEntity> transactionList = new ArrayList<>();

    private TransactionViewModel viewModel;

    private RequestQueue requestQueue;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trancation);

        initializeUI();

        setupToolbar();

        setupRecyclerView();

        setupViewModel();

        setupSearchView();
    }

    /**
     * Initializes UI components.
     */
    private void initializeUI() {
        transactionsRecyclerView = findViewById(R.id.transactionsRecyclerView);
        searchEditText = findViewById(R.id.searchView);
        toolbar = findViewById(R.id.toolbar);
        requestQueue = Volley.newRequestQueue(this);
    }

    /**
     * Sets up the toolbar with back navigation.
     */
    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Sets up RecyclerView and its adapter.
     */
    private void setupRecyclerView() {
        transactionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TransactionAdapter(transactionList);
        transactionsRecyclerView.setAdapter(adapter);
    }

    /**
     * Sets up the ViewModel and observes database changes.
     */
    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        // Observe transactions from the database
        viewModel.getAllTransactions().observe(this, transactions -> {
            transactionList.clear();
            transactionList.addAll(transactions);
            adapter.notifyDataSetChanged();
        });
    }

    /**
     * Sets up search functionality for filtering transactions.
     */
    private void setupSearchView() {
        searchEditText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterTransactions(newText);
                return true;
            }
        });
    }

    /**
     * Filters the transaction list based on the search text.
     *
     * @param text Search text entered by the user.
     */
    private void filterTransactions(String text) {
        ArrayList<TransactionEntity> filteredList = new ArrayList<>();

        for (TransactionEntity item : transactionList) {
            if (item.getDate().toLowerCase().contains(text.toLowerCase()) ||
                    item.getDescription().toLowerCase().contains(text.toLowerCase())  ||
            item.getCategory().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.filterList(filteredList);

        // Optionally, show a message if no data is found
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No matching transactions found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                showLogoutConfirmationDialog();
                return true;
            case R.id.action_dark_mode:
                toggleDarkMode();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Shows a confirmation dialog for logout.
     */
    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> performLogout())
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    /**
     * Performs the logout operation.
     */
    private void performLogout() {
        // Clear saved token
        SharedPreferencesUtil.clearToken(this);

        // Navigate to Login Screen
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    /**
     * Toggles between dark and light mode.
     */
    private void toggleDarkMode() {
        int currentMode = AppCompatDelegate.getDefaultNightMode();
        SharedPreferences preferences = getSharedPreferences(SharedPreferencesUtil.PREFS_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            editor.putBoolean("DarkMode", false);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            editor.putBoolean("DarkMode", true);
        }

        editor.apply();
    }

}
