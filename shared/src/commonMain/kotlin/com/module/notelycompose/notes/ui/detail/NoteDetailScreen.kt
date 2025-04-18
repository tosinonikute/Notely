package com.module.notelycompose.notes.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.module.notelycompose.audio.ui.player.PlatformAudioPlayerUi
import com.module.notelycompose.audio.ui.player.model.AudioPlayerUiState
import com.module.notelycompose.audio.ui.recorder.RecordUiComponent
import com.module.notelycompose.notes.ui.theme.LocalCustomColors
import com.module.notelycompose.resources.vectors.IcRecorder
import com.module.notelycompose.resources.vectors.Images
import notelycompose.shared.generated.resources.Res
import notelycompose.shared.generated.resources.note_detail_recorder
import org.jetbrains.compose.resources.stringResource

@Composable
fun NoteDetailScreen(
    newNoteDateString: String,
    editorState: EditorUiState,
    audioPlayerUiState: AudioPlayerUiState,
    recordCounterString: String,
    onNavigateBack: () -> Unit,
    onUpdateContent: (newContent: TextFieldValue) -> Unit,
    onFormatActions: NoteFormatActions,
    onAudioActions: NoteAudioActions,
    onNoteActions: NoteActions
) {
    var showFormatBar by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    var showRecordDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { DetailNoteTopBar(onNavigateBack = onNavigateBack) },
        floatingActionButton = {
            RecordButton(onClick = { showRecordDialog = true })
        },
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = {
            BottomNavigationBar(
                selectionSize = editorState.selectionSize,
                isStarred = editorState.isStarred,
                showFormatBar = showFormatBar,
                textFieldFocusRequester = focusRequester,
                onFormatActions = onFormatActions,
                onShowTextFormatBar = { showFormatBar = it },
                onStarNote = onNoteActions.onStarNote,
                onDeleteNote = onNoteActions.onDeleteNote
            )
        }
    ) { paddingValues ->
        NoteContent(
            paddingValues = paddingValues,
            newNoteDateString = newNoteDateString,
            editorState = editorState,
            showFormatBar = showFormatBar,
            showRecordDialog = showRecordDialog,
            focusRequester = focusRequester,
            onUpdateContent = onUpdateContent,
            audioPlayerUiState = audioPlayerUiState,
            onAudioActions = onAudioActions
        )
    }

    if (showRecordDialog) {
        onAudioActions.onRequestAudioPermission()
        RecordUiComponent(
            onDismiss = { showRecordDialog = false },
            onAfterRecord = {
                showRecordDialog = false
                onAudioActions.onAfterRecord()
            },
            recordCounterString = recordCounterString,
            onStartRecord = onAudioActions.onStartRecord,
            onStopRecord = onAudioActions.onStopRecord
        )
    }
}

@Composable
private fun RecordButton(onClick: () -> Unit) {
    FloatingActionButton(
        modifier = Modifier.border(
            width = 1.dp,
            color = LocalCustomColors.current.floatActionButtonBorderColor,
            shape = CircleShape
        ),
        backgroundColor = LocalCustomColors.current.bodyBackgroundColor,
        onClick = onClick
    ) {
        Icon(
            imageVector = Images.Icons.IcRecorder,
            contentDescription = stringResource(Res.string.note_detail_recorder),
            tint = LocalCustomColors.current.bodyContentColor
        )
    }
}

@Composable
private fun NoteContent(
    paddingValues: PaddingValues,
    newNoteDateString: String,
    editorState: EditorUiState,
    showFormatBar: Boolean,
    showRecordDialog: Boolean,
    focusRequester: FocusRequester,
    onUpdateContent: (TextFieldValue) -> Unit,
    audioPlayerUiState: AudioPlayerUiState,
    onAudioActions: NoteAudioActions
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
            .background(LocalCustomColors.current.bodyBackgroundColor)
            .imePadding()
    ) {
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            DateHeader(newNoteDateString)

            if (editorState.recording.isRecordingExist) {
                PlatformAudioPlayerUi(
                    filePath = editorState.recording.recordingPath,
                    uiState = audioPlayerUiState,
                    onLoadAudio = onAudioActions.onLoadAudio,
                    onClear = onAudioActions.onClear,
                    onSeekTo = onAudioActions.onSeekTo,
                    onTogglePlayPause = onAudioActions.onTogglePlayPause
                )
            }

            NoteEditor(
                editorState = editorState,
                showFormatBar = showFormatBar,
                showRecordDialog = showRecordDialog,
                focusRequester = focusRequester,
                onUpdateContent = onUpdateContent
            )
        }
    }
}

@Composable
private fun DateHeader(dateString: String) {
    Text(
        text = dateString,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
        fontSize = 12.sp,
        color = LocalCustomColors.current.bodyContentColor
    )
}

@Composable
private fun NoteEditor(
    editorState: EditorUiState,
    showFormatBar: Boolean,
    showRecordDialog: Boolean,
    focusRequester: FocusRequester,
    onUpdateContent: (TextFieldValue) -> Unit
) {
    val transformation = VisualTransformation { text ->
        TransformedText(
            buildAnnotatedString {
                append(text)
                editorState.formats.forEach { format ->
                    addStyle(
                        SpanStyle(
                            fontWeight = if (format.isBold) FontWeight.Bold else null,
                            fontStyle = if (format.isItalic) FontStyle.Italic else null,
                            textDecoration = if (format.isUnderline)
                                TextDecoration.Underline else null,
                            fontSize = format.textSize?.sp ?: TextUnit.Unspecified
                        ),
                        format.range.first.coerceIn(0, text.length),
                        format.range.last.coerceIn(0, text.length)
                    )
                }
            },
            OffsetMapping.Identity
        )
    }

    BasicTextField(
        value = editorState.content,
        onValueChange = onUpdateContent,
        modifier = Modifier
            .fillMaxSize()
            .height(600.dp)
            .focusRequester(focusRequester)
            .padding(horizontal = 16.dp),
        textStyle = TextStyle(
            color = LocalCustomColors.current.bodyContentColor,
            textAlign = editorState.textAlign
        ),
        cursorBrush = SolidColor(LocalCustomColors.current.bodyContentColor),
        readOnly = showFormatBar,
        enabled = !showRecordDialog,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences
        ),
        visualTransformation = transformation
    )
}

// Helper data classes to group related callbacks
data class NoteFormatActions(
    val onToggleBold: () -> Unit,
    val onToggleItalic: () -> Unit,
    val onToggleUnderline: () -> Unit,
    val onSetAlignment: (TextAlign) -> Unit,
    val onToggleBulletList: () -> Unit,
    val onSelectTextSizeFormat: (Float) -> Unit
)

data class NoteAudioActions(
    val onStartRecord: () -> Unit,
    val onStopRecord: () -> Unit,
    val onRequestAudioPermission: () -> Unit,
    val onAfterRecord: () -> Unit,
    val onLoadAudio: (String) -> Unit,
    val onClear: () -> Unit,
    val onSeekTo: (Int) -> Unit,
    val onTogglePlayPause: () -> Unit
)

data class NoteActions(
    val onDeleteNote: () -> Unit,
    val onStarNote: () -> Unit
)
