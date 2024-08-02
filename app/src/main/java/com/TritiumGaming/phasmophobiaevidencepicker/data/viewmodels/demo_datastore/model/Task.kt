package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.demo_datastore.model

import java.util.Date

enum class TaskPriority {
    HIGH, MEDIUM, LOW
}
data class Task(
    val name: String,
    val deadline: Date,
    val priority: TaskPriority,
    val completed: Boolean = false
)