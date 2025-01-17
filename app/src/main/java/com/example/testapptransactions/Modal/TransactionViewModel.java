package com.example.testapptransactions.Modal;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.testapptransactions.Data.MyApplication;
import com.example.testapptransactions.Data.TransactionDao;
import com.example.testapptransactions.Data.AppDatabase;
import com.example.testapptransactions.Data.TransactionEntity;

import java.util.List;
public class TransactionViewModel extends AndroidViewModel {
    private final TransactionDao transactionDao;
    private final LiveData<List<TransactionEntity>> allTransactions;

    public TransactionViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getDatabase(application);
        transactionDao = database.transactionDao();


        allTransactions = transactionDao.getAllTransactions();
    }

    public LiveData<List<TransactionEntity>> getAllTransactions() {
        return allTransactions;
    }

    public void insertTransactions(List<TransactionEntity> transactions) {
        new Thread(() -> transactionDao.insertTransactions(transactions)).start();
    }
}

