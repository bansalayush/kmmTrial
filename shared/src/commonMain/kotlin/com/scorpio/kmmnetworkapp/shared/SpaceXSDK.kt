package com.scorpio.kmmnetworkapp.shared

import com.scorpio.kmmnetworkapp.shared.cache.Database
import com.scorpio.kmmnetworkapp.shared.cache.DatabaseDriverFactory
import com.scorpio.kmmnetworkapp.shared.entity.RocketLaunch
import com.scorpio.kmmnetworkapp.shared.network.SpaceXApi

class SpaceXSDK(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)
    private val api = SpaceXApi()

    @Throws(Exception::class) suspend fun getLaunches(forceReload: Boolean): List<RocketLaunch> {
        val cachedLaunches = database.getAllLaunches()
        return if (cachedLaunches.isNotEmpty() && !forceReload) {
            cachedLaunches
        } else {
            api.getAllLaunches().also {
                database.clearDatabase()
                database.createLaunches(it)
            }
        }
    }
}