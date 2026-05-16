package com.example.lovefasilitas.data.remote

import com.example.lovefasilitas.domain.model.Facility
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    // === FACILITIES ===
    @GET("api/facilities.php")
    suspend fun getAllFacilities(): ApiResponse<List<FacilityApiModel>>

    @GET("api/facilities.php")
    suspend fun getFacilityById(@Query("id") id: Int): ApiResponse<List<FacilityApiModel>>

    @GET("api/facilities.php")
    suspend fun getFacilitiesByCategory(@Query("category") category: String): ApiResponse<List<FacilityApiModel>>

    @POST("api/facilities.php")
    suspend fun addFacility(@Body facility: FacilityRequest): ApiResponse<List<FacilityApiModel>>

    @POST("api/facilities.php")
    suspend fun updateFacility(@Query("_method") method: String = "PUT", @Query("id") id: Int, @Body facility: FacilityRequest): ApiResponse<List<FacilityApiModel>>

    @POST("api/facilities.php")
    suspend fun deleteFacility(@Query("_method") method: String = "DELETE", @Query("id") id: Int): ApiResponse<Map<String, Any>?>

    // === AUTH ===
    @POST("api/auth.php")
    suspend fun login(@Query("action") action: String = "login", @Body request: LoginRequest): ApiResponse<UserApiModel>

    @POST("api/auth.php")
    suspend fun register(@Query("action") action: String = "register", @Body request: RegisterRequest): ApiResponse<UserApiModel>
}
