package com.cbmoney.data.remote.datasource

import com.cbmoney.data.remote.model.CategoryFirestore
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CategoryRemoteDataSourceImpl(
    private val firebaseFirestore: FirebaseFirestore
) : CategoryRemoteDataSource {
    override suspend fun fetchAllCategories(): List<CategoryFirestore> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchCategoriesByType(type: String): List<CategoryFirestore> {
        TODO("Not yet implemented")
    }

    override suspend fun saveCategory(category: CategoryFirestore) {
        TODO("Not yet implemented")
    }

    override suspend fun initCategoriesDefault(uid: String) {
        val collection = firebaseFirestore
            .collection("users/$uid/categories")
        val check = collection.get().await()
        if (check.isEmpty) {
//            val batch = firebaseFirestore.batch()
//            DefaultCategoriesMap.categories[CategoryType.EXPENSE]?.forEach { it ->
//                val document = collection.document()
//
//                batch.set(
//                    document, CategoryFirestore(
//                        id = document.id,
//                        name = it["name"] as String,
//                        type = it["type"] as String,
//                        icon = it["icon"] as String,
//                        color = it["color"] as String,
//                        order = it["order"] as Int,
//                        createdAt = Timestamp.now()
//                    )
//                )
//            }
//            DefaultCategoriesMap.categories[CategoryType.INCOME]?.forEach { it ->
//                val document = collection.document()
//                batch.set(document, it)
//            }
//            batch.commit().await()
        }

    }

}