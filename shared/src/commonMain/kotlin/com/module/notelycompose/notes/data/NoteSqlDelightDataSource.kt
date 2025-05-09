package com.module.notelycompose.notes.data

import com.module.notelycompose.core.CommonFlow
import com.module.notelycompose.core.DateTimeUtil
import com.module.notelycompose.core.toCommonFlow
import com.module.notelycompose.database.NoteDatabase
import com.module.notelycompose.notes.data.model.NoteDataModel
import com.module.notelycompose.notes.data.model.TextAlignDataModel
import com.module.notelycompose.notes.data.model.TextFormatDataModel
import com.module.notelycompose.notes.domain.NoteDataSource
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val STARRED = 1L
private const val NOT_STARRED = 0L

class NoteSqlDelightDataSource(
    private val database: NoteDatabase
) : NoteDataSource {
    private val queries = database.noteQueries
    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }
    override suspend fun insertNote(
        title: String,
        content: String,
        starred: Boolean,
        formatting: List<TextFormatDataModel>,
        textAlign: TextAlignDataModel,
        recordingPath: String
    ): Long? {
        queries.insertNote(
            title = title,
            content = content,
            starred = starred.starredToDigit(),
            formatting = json.encodeToString(formatting),
            text_align = textAlign.toString(),
            recording_path = recordingPath,
            created_at = DateTimeUtil.toEpochMilli(DateTimeUtil.now())
        )
        return getLastNoteId()
    }

    override suspend fun updateNote(
        id: Long,
        title: String,
        content: String,
        starred: Boolean,
        formatting: List<TextFormatDataModel>,
        textAlign: TextAlignDataModel,
        recordingPath: String
    ) {
        queries.updateNote(
            id = id,
            title = title,
            content = content,
            starred = starred.starredToDigit(),
            formatting = json.encodeToString(formatting),
            text_align = textAlign.toString(),
            recording_path = recordingPath,
            created_at = DateTimeUtil.toEpochMilli(DateTimeUtil.now())
        )
    }

    override fun getNotes(): CommonFlow<List<NoteDataModel>> {
        return queries
            .getAllNotes()
            .asFlow()
            .mapToList()
            .map { notes ->
                notes.map { note ->
                    note.toNoteDataModel(json)
                }
            }.toCommonFlow()
    }

    override fun getStarredNotes(): CommonFlow<List<NoteDataModel>> {
        return queries
            .getNotesByStarred(STARRED)
            .asFlow()
            .mapToList()
            .map { notes ->
                notes.map { note ->
                    note.toNoteDataModel(json)
                }
            }.toCommonFlow()
    }

    override fun getVoiceNotes(): CommonFlow<List<NoteDataModel>> {
        return queries
            .getNotesByRecordingPath()
            .asFlow()
            .mapToList()
            .map { notes ->
                notes.map { note ->
                    note.toNoteDataModel(json)
                }
            }.toCommonFlow()
    }

    override fun getNotesByKeyword(keyword: String): CommonFlow<List<NoteDataModel>> {
        return queries
            .getNotesByContent(keyword)
            .asFlow()
            .mapToList()
            .map { notes ->
                notes.map { note ->
                    note.toNoteDataModel(json)
                }
            }.toCommonFlow()
    }

    override fun getNoteById(id: Long): NoteDataModel? {
        return queries
            .getNoteById(id)
            .executeAsOneOrNull()?.toNoteDataModel(json)
    }

    override fun getLastNote(): NoteDataModel? {
        return queries
            .getLastNote()
            .executeAsOneOrNull()?.toNoteDataModel(json)
    }

    override fun getLastNoteId(): Long? {
        return queries
            .getLastNoteId()
            .executeAsOneOrNull()
    }

    override suspend fun deleteNoteById(id: Long) {
        queries
            .deleteNoteById(id)
    }
}

fun Boolean.starredToDigit(): Long {
    return if(this) {
        STARRED
    } else {
        NOT_STARRED
    }
}
