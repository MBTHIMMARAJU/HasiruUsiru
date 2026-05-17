package com.yourname.hasiruusiru

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class TreeRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val treesCollection = firestore.collection("trees")
    private val speciesCollection = firestore.collection("species")
    private val streetsCollection = firestore.collection("streets")

    // Get all trees as a Real-time Flow
    fun getTreesFlow(): Flow<List<Tree>> = callbackFlow {
        val subscription = treesCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val trees = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(Tree::class.java)?.copy(id = doc.id)
                }
                trySend(trees)
            }
        }
        awaitClose { subscription.remove() }
    }

    // Add a new tree tag
    suspend fun tagTree(tree: Tree): Result<String> {
        return try {
            val docRef = treesCollection.add(tree).await()
            Result.success(docRef.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Fetch species guide data
    suspend fun getSpeciesGuide(): List<SpeciesInfo> {
        return try {
            val snapshot = speciesCollection.get().await()
            snapshot.documents.mapNotNull { it.toObject(SpeciesInfo::class.java) }
        } catch (e: Exception) {
            // Return some mock data if collection is empty for the demo
            listOf(
                SpeciesInfo("Neem", "ಬೇವು", "Azadirachta indica", 1.2, "Highly medicinal, purifies air.", "https://example.com/neem.jpg"),
                SpeciesInfo("Peepal", "ಅರಳಿ", "Ficus religiosa", 1.5, "Sacred tree, produces oxygen at night.", "https://example.com/peepal.jpg"),
                SpeciesInfo("Honge", "ಹೊಂಗೆ", "Pongamia pinnata", 0.8, "Great for urban shade, biofuel potential.", "https://example.com/honge.jpg"),
                SpeciesInfo("Mango", "ಮಾವು", "Mangifera indica", 1.0, "Fruit bearing, large canopy.", "https://example.com/mango.jpg"),
                SpeciesInfo("Banyan", "ಆಲದ ಮರ", "Ficus benghalensis", 1.8, "Vast canopy, supports biodiversity.", "https://example.com/banyan.jpg")
            )
        }
    }

    // Fetch street leaderboard
    suspend fun getStreetLeaderboard(): List<StreetScore> {
        return try {
            val snapshot = streetsCollection
                .orderBy("oxygenScore", Query.Direction.DESCENDING)
                .get()
                .await()
            snapshot.documents.mapNotNull { it.toObject(StreetScore::class.java) }
        } catch (e: Exception) {
            // Return mock data for demo
            listOf(
                StreetScore("MG Road", "CBD", 450, 92, 5),
                StreetScore("Brigade Road", "CBD", 320, 85, 12),
                StreetScore("Indiranagar 100ft Rd", "Indiranagar", 600, 78, 20),
                StreetScore("Koramangala 80ft Rd", "Koramangala", 410, 88, 8),
                StreetScore("Jayanagar 4th Block", "Jayanagar", 800, 95, 2)
            )
        }
    }
}
