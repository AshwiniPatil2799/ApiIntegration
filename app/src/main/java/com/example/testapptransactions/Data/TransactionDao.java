package com.example.testapptransactions.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TransactionDao {
    @Query("SELECT * FROM transactions")
    LiveData<List<TransactionEntity>> getAllTransactions();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTransactions(List<TransactionEntity> transactions);


}
