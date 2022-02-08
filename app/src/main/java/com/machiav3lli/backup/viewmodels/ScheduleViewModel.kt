/*
 * OAndBackupX: open-source apps backup and restore app.
 * Copyright (C) 2020  Antonios Hazim
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.machiav3lli.backup.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.machiav3lli.backup.dbs.Schedule
import com.machiav3lli.backup.dbs.ScheduleDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO Add factory as companion
class ScheduleViewModel(val id: Long, private val scheduleDB: ScheduleDao, appContext: Application)
    : AndroidViewModel(appContext) {

    var schedule = MediatorLiveData<Schedule>()

    init {
        schedule.addSource(scheduleDB.getLiveSchedule(id), schedule::setValue)
    }

    fun deleteSchedule() {
        viewModelScope.launch {
            deleteS()
        }
    }

    private suspend fun deleteS() {
        withContext(Dispatchers.IO) {
            scheduleDB.delete(schedule.value!!)
        }
    }
}