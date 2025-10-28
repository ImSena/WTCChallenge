package br.com.corecode.wtcchallenge.data.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import br.com.corecode.wtcchallenge.R
import br.com.corecode.wtcchallenge.data.repository.SessionRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "MyFirebaseMessagingSvc"

    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.IO + serviceJob)

    private lateinit var sessionRepository: SessionRepository


    override fun onCreate() {
        super.onCreate()
        sessionRepository = SessionRepository(this)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Novo token FCM gerado: $token")

        serviceScope.launch {
            try {
                val uid = sessionRepository.activeSessionUid.first()

                if (uid != null && uid.isNotBlank()) {
                    Log.d(TAG, "UID da sessão encontrado: $uid. Salvando token...")
                    sendTokenToFirestore(uid, token)
                } else {
                    Log.w(TAG, "UID da sessão está nulo, token não será salvo.")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Erro ao tentar pegar o UID da sessão", e)
            }
        }
    }

    private suspend fun sendTokenToFirestore(uid: String, token: String) {
        val db = Firebase.firestore
        try {
            db.collection("users").document(uid)
                .update("fcmToken", token)
                .await()
            Log.d(TAG, "Token salvo no Firestore para o user $uid")
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao salvar token no Firestore", e)
        }
    }


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d(TAG, "Push recebido! De: ${remoteMessage.from}")

        remoteMessage.data.let { data ->
            val title = data["title"] ?: "Nova Mensagem"
            val body = data["body"] ?: "Você recebeu uma nova mensagem."
            Log.d(TAG, "Dados da mensagem: $title / $body")

            sendLocalNotification(title, body)
        }
    }

    private fun sendLocalNotification(title: String, body: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "wtc_chat_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "WTC Chat",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notificações de mensagens do WTC Business Club"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        val notificationId = (0..1000).random()
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
    }
}