package br.com.corecode.wtcchallenge.data.repository

import br.com.corecode.wtcchallenge.data.model.User
import br.com.corecode.wtcchallenge.domain.repository.IUserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class UserRepository(
    private val sessionRepository: SessionRepository
) : IUserRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()

    override suspend fun login(email: String, password: String): Result<User> {
        return try{
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()

            val firebaseUser = authResult.user

            if(firebaseUser != null){
                sessionRepository.saveSession(firebaseUser.uid)
                Result.success(User(uid = firebaseUser.uid, email = firebaseUser.email))
            }else{
                Result.failure(Exception("Usuário não encontrado."))
            }
        }catch(e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun logout(): Result<Unit> {
        return try{
            firebaseAuth.signOut()
            sessionRepository.clearSession()
            Result.success(Unit)
        }catch(e: Exception){
            Result.failure(e)
        }
    }
}