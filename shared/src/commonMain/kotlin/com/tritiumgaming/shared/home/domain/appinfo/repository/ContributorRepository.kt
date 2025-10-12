package com.tritiumgaming.shared.home.domain.appinfo.repository

import com.tritiumgaming.shared.home.domain.appinfo.model.Contributor


interface ContributorRepository {

    fun getSpecialThanks(): Result<List<Contributor>>

}