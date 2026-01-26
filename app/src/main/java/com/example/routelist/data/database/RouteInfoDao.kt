package com.example.routelist.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RouteInfoDao {

    @Query("SELECT * FROM routes ORDER BY id DESC")
    fun getAllRoutesFlow(): Flow<List<RouteListDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoute(route: RouteListDbModel)

    @Query("DELETE FROM routes WHERE id = :id")
    suspend fun deleteRouteById(id: Int)

    @Update
    suspend fun updateRoute(rout: RouteListDbModel)

    @Query("SELECT * FROM routes WHERE substr(startDate, 7, 4) = :year AND substr(startDate, 4, 2) = :month ORDER BY id DESC")
    fun getRoutesByMonthYearFlow(year: String, month: String): Flow<List<RouteListDbModel>>

}