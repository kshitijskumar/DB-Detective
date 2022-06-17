package com.example.dbdetectivedemo.testdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTestEntity(test: TestEntity)

    @Query("SELECT * FROM test_table")
    fun getAllTestEntries(): Flow<List<TestEntity>>

}