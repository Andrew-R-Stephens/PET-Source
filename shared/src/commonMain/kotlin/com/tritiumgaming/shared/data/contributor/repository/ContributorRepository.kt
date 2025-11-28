package com.tritiumgaming.shared.data.contributor.repository


interface ContributorRepository {

    fun getSpecialThanks(): Result<List<com.tritiumgaming.shared.data.contributor.model.Contributor>>

}