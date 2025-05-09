package com.module.notelycompose.notes.data

import com.module.notelycompose.notes.data.model.NoteDataModel
import com.module.notelycompose.notes.data.model.TextAlignDataModel
import com.module.notelycompose.notes.data.model.TextFormatDataModel
import com.module.notelycompose.notes.domain.model.Note
import database.NotesEntity
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json

fun NotesEntity.toNote(): Note {
    return Note(
        id = id.toInt(),
        title = title,
        content = content,
        starred = starred,
        createdAt = Instant.fromEpochMilliseconds(created_at)
            .toLocalDateTime(TimeZone.currentSystemDefault())
    )
}

fun NotesEntity.toNoteDataModel(json :Json): NoteDataModel {
    return NoteDataModel(
        id = id,
        title = title,
        content = content,
        starred = starred.starredToBool(),
        formatting = try {
            json.decodeFromString<List<TextFormatDataModel>>(formatting)
        } catch (e: Exception) {
            emptyList()
        },
        textAlign = try {
            TextAlignDataModel.valueOf(text_align)
        } catch (e: Exception) {
            TextAlignDataModel.Start
        },
        recordingPath = recording_path,
        createdAt = Instant.fromEpochMilliseconds(created_at)
            .toLocalDateTime(TimeZone.currentSystemDefault())
    )
}

fun Long.starredToBool(): Boolean {
    return this == 1L
}
