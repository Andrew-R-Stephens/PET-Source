package com.tritiumgaming.shared.data.contributor.repository

import com.tritiumgaming.shared.data.contributor.model.Contributor


interface ContributorRepository {

    fun getSpecialThanks(): Result<List<Contributor>>

}