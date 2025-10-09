package br.com.corecode.wtcchallenge.domain.repository

import br.com.corecode.wtcchallenge.data.model.User
import br.com.corecode.wtcchallenge.domain.repository.interfaces.IUserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class UserRepository : IUserRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()

    override suspend fun login(email: String, password: String): Result<User> {
        return try{
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()

            val firebaseUser = authResult.user

            if(firebaseUser != null){
                Result.success(User(uid = firebaseUser.uid, email = firebaseUser.email))
            }else{
                Result.failure(Exception("Usuário não encontrado."))
            }
        }catch(e: Exception){
            Result.failure(e)
        }
    }
}