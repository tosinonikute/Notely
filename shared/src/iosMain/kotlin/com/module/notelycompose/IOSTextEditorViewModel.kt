package com.module.notelycompose

import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import com.module.notelycompose.notes.domain.DeleteNoteById
import com.module.notelycompose.notes.domain.GetLastNote
import com.module.notelycompose.notes.domain.GetNoteById
import com.module.notelycompose.notes.domain.InsertNoteUseCase
import com.module.notelycompose.notes.domain.UpdateNoteUseCase
import com.module.notelycompose.notes.presentation.helpers.TextEditorHelper
import com.module.notelycompose.notes.presentation.detail.TextEditorViewModel
import com.module.notelycompose.notes.presentation.detail.model.EditorPresentationState
import com.module.notelycompose.notes.ui.detail.EditorUiState
import com.module.notelycompose.notes.presentation.mapper.EditorPresentationToUiStateMapper
import com.module.notelycompose.notes.presentation.mapper.TextAlignPresentationMapper
import com.module.notelycompose.notes.presentation.mapper.TextFormatPresentationMapper

class IOSTextEditorViewModel(
    private val getNoteByIdUseCase: GetNoteById,
    private val insertNoteUseCase: InsertNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteById,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val getLastNoteUseCase: GetLastNote,
    private val editorPresentationToUiStateMapper: EditorPresentationToUiStateMapper,
    private val textFormatPresentationMapper: TextFormatPresentationMapper,
    private val textAlignPresentationMapper: TextAlignPresentationMapper,
    private val textEditorHelper: TextEditorHelper
) {

    private val viewModel by lazy {
        TextEditorViewModel(
            getNoteByIdUseCase = getNoteByIdUseCase,
            insertNoteUseCase = insertNoteUseCase,
            deleteNoteUseCase = deleteNoteUseCase,
            updateNoteUseCase = updateNoteUseCase,
            getLastNoteUseCase = getLastNoteUseCase,
            editorPresentationToUiStateMapper = editorPresentationToUiStateMapper,
            textFormatPresentationMapper = textFormatPresentationMapper,
            textAlignPresentationMapper =  textAlignPresentationMapper,
            textEditorHelper = textEditorHelper
        )
    }
    val state = viewModel.editorPresentationState

    fun onGetNoteById(id: String) {
        viewModel.onGetNoteById(id)
    }

    fun onGetUiState(presentationState: EditorPresentationState): EditorUiState {
        return viewModel.onGetUiState(presentationState)
    }

    fun onUpdateContent(
        newContent: TextFieldValue
    ) {
        return viewModel.onUpdateContent(newContent)
    }

    fun onToggleBold() {
        return viewModel.onToggleBold()
    }

    fun onToggleItalic() {
        return viewModel.onToggleItalic()
    }

    fun setTextSize(size: Float) {
        return viewModel.setTextSize(size)
    }

    fun onToggleUnderline() {
        return viewModel.onToggleUnderline()
    }

    fun onSetAlignment(alignment: TextAlign) {
        return viewModel.onSetAlignment(alignment)
    }

    fun onToggleBulletList() {
        return viewModel.onToggleBulletList()
    }

    fun onUpdateRecordingPath(recordingPath: String) {
        return viewModel.onUpdateRecordingPath(recordingPath)
    }

    fun onDeleteRecord() {
        return viewModel.onDeleteRecord()
    }

    fun onDeleteNote() {
        return viewModel.onDeleteNote()
    }

    fun onToggleStar() {
        viewModel.onToggleStar()
    }
}
