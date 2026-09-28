package com.tritiumgaming.data.contributor.repository

import com.tritiumgaming.data.contributor.model.Contributor


interface ContributorRepository {

    fun getSpecialThanks(): Result<List<Contributor>>

}