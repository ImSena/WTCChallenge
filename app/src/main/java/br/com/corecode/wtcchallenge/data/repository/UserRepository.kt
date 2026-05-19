package br.com.corecode.wtcchallenge.data.repository

import br.com.corecode.wtcchallenge.data.model.User
import br.com.corecode.wtcchallenge.data.network.LoginPayload
import br.com.corecode.wtcchallenge.data.network.RetrofitClient
import br.com.corecode.wtcchallenge.domain.repository.IUserRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await

class UserRepository(
    private val sessionRepository: SessionRepository
) : IUserRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore
    private val usersCollection = db.collection("users")

    private val authService = RetrofitClient.getAuthService()

    override suspend fun login(email: String, password: String): Result<User> {
        return try{

            val fcmToken = try {
                FirebaseMessaging.getInstance().token.await()
            }catch(e: Exception){
                null
            }

            val payload = LoginPayload(email, password, fcmToken)
            val response = authService.login(payload)

            if (response.isSuccessful && response.body() != null) {
                val loginResponse = response.body()!!

                sessionRepository.saveSession(
                    uid = loginResponse.uid,
                    role = loginResponse.role,
                    token = loginResponse.token
                )

                val authenticatedUser = User(
                    id = loginResponse.uid,
                    name = loginResponse.name,
                    email = email,
                    role = loginResponse.role
                )

                Result.success(authenticatedUser)
            } else {
                Result.failure(Exception("E-mail ou senha inválidos no servidor local."))
            }
        }catch(e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun logout(): Result<Unit> {
        return try {
            sessionRepository.clearSession()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}