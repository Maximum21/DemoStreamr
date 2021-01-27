package com.demo.controlr.framework

import com.demo.controlr.data.domain.TwitterResponse
import com.demo.controlr.framework.network.retrofit.ResponseRequest
import retrofit2.http.GET
import retrofit2.http.Path


interface GeneralService {

    @GET("2/users/{user_id}/followers")
    fun show(
        @Path("user_id") userId: String
    ): ResponseRequest<TwitterResponse>
//    @GET("1.0/users/me/friends")
//    fun listFriend(): Observable<List<UserEntity>>
//
//
//    @FormUrlEncoded
//    @POST("1.0/social/{type}")
//    fun unregister(@Path("type") type: String, @Query("access_token") token: String,
//                   @Field("phone_number") phoneNumber: String, @Field("country_code") countryCode: String,
//                   @Field("email") email: String): Observable<VerificationMessage>
//
//    @POST("1.0/users")
//    fun register(@Body loginRequest: LoginRequest): Observable<VerificationMessage>
//
//
//    @POST("1.0/users/validate")
//    fun verificationCode(@Body code: Token): Observable<AuthorizationResponse>
//
//
//    @GET("1.0/contents/{option}")
//    fun getTermsPolicy(@Path("option") option: String): Observable<TermsPolicy>
//
//    @POST("1.0/feedback")
//    fun sendFeedback(@Body feedback: Feedback): Observable<VerificationMessage>
//
//    @POST("1.0/contacts/sync")
//    fun syncContact(@Body syncContact: SyncContact): Observable<VerificationMessage>
//
//
//
//    @GET("1.0/countries")
//    fun allCountries(): ResponseRequest<List<Country>>
//
//
//    @POST("1.0/contacts/sync")
//    fun syncContacts(@Body sysContact: SyncContact): ResponseRequest<VerificationMessage>
}
