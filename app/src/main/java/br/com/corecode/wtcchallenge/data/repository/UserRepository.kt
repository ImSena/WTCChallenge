package br.com.corecode.wtcchallenge.data.repository

import br.com.corecode.wtcchallenge.data.model.User
import br.com.corecode.wtcchallenge.domain.repository.IUserRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class UserRepository(
    private val sessionRepository: SessionRepository
) : IUserRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore
    private val usersCollection = db.collection("users")

    override suspend fun login(email: String, password: String): Result<User> {
        return try{
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user

            if(firebaseUser != null){
                val userDoc = usersCollection.document(firebaseUser.uid).get().await()
                val user = userDoc.toObject<User>()

                if(user != null){
                    sessionRepository.saveSession(firebaseUser.uid, user.role)
                    val completeUser = user.copy(uid = firebaseUser.uid)
                    Result.success(completeUser)
                }else{
                    Result.failure(Exception("Não foi possível encontrar usuário"))
                }

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