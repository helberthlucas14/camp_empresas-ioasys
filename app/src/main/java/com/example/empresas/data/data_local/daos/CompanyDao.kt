package com.example.empresas.data.data_local.daos

import androidx.room.*
import com.example.empresas.data.data_local.models.CompanyLocalModel

@Dao
interface CompanyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(company: CompanyLocalModel)

    @Query("SELECT * FROM companies_table")
    suspend fun getAllCompanies(): List<CompanyLocalModel>

}