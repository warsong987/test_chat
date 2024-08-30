@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

package ru.ivan.eremin.testchat.presentation.screen.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastForEach
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import kotlinx.coroutines.launch
import ru.ivan.eremin.testchat.domain.chats.ChatDetails
import ru.ivan.eremin.testchat.domain.chats.ChatDownloadData
import ru.ivan.eremin.testchat.domain.chats.ChatListMessageType
import ru.ivan.eremin.testchat.domain.chats.ChatSelectedFile
import ru.ivan.eremin.testchat.domain.chats.Message
import ru.ivan.eremin.testchat.presentation.components.ErrorImage
import ru.ivan.eremin.testchat.presentation.components.Screen
import ru.ivan.eremin.testchat.presentation.screen.chat.view.ChatInput
import ru.ivan.eremin.testchat.presentation.screen.chat.view.ChatSelectedFileView

@Composable
fun ChatScreen(
    navHostController: NavHostController,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    ChatScreenState(
        state = state,
        onAction = remember {
            {
                handlerAction(it, navHostController, viewModel)
            }
        }
    )
}

@Composable
private fun ChatScreenState(
    state: ChatUiState,
    onAction: (ChatAction) -> Unit = {},
) {
    val scrollState = rememberLazyListState(0)
    Screen(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row {
                        SubcomposeAsyncImage(
                            modifier = Modifier.size(40.dp),
                            model = state.message?.icon,
                            contentDescription = null,
                            error = {
                                ErrorImage()
                            }
                        )
                        Column {
                            Text(
                                text = state.message?.name.orEmpty(),
                            )
                            Text(
                                text = state.message?.status.orEmpty(),
                                fontSize = 14.sp
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onAction(ChatAction.OnBack)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }

                }
            )
        },
        bottomBar = {
            BottomBar(
                state.messagesState,
                state.chatState,
                state.skeleton,
                onAction
            )
        },
        floatingActionButton = {
            FloatingButton(
                scrollState = scrollState,
            )
        }
    ) {
        val currentDownloadFileState by rememberUpdatedState(state.fileDownloadState)

    }
}

@Composable
private fun ChatMessageList(
    sessionState: ChatUiState.SessionState,
    historyState: ChatUiState.HistoryState,
    downloadFileState: (id: String) -> ChatDownloadData?,
    skeleton: Boolean,
    onActions: (ChatAction) -> Unit,
    scrollState: LazyListState,
    modifier: Modifier = Modifier
) {
    val currentOnActions by rememberUpdatedState(onActions)
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        reverseLayout = true,
        verticalArrangement = Arrangement.Top,
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 24.dp),
        state = scrollState
    ) {
        item(key = ChatListMessageType.INPUT, contentType = ChatListMessageType.INPUT) {
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
            )
        }
        if (!skeleton) {
            items(sessionState.messages, key = { it.id }, contentType = { it.type }) {

            }
            }
        }
    }


    @Composable
    private fun FloatingButton(
        scrollState: LazyListState,
    ) {
        val show by remember { derivedStateOf { scrollState.firstVisibleItemIndex > 0 } }
        val scope = rememberCoroutineScope()

        AnimatedVisibility(
            visible = show,
            enter = scaleIn() + fadeIn(),
            exit = scaleOut() + fadeOut(),
        ) {
            CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                IconButton(
                    onClick = { scope.launch { scrollState.scrollToItem(0) } },
                    modifier = Modifier
                        .size(36.dp)
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowDown,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }

    @Composable
    private fun BottomBar(
        messageState: ChatUiState.MessageState,
        chatState: ChatUiState.ChatState,
        skeleton: Boolean,
        onAction: (ChatAction) -> Unit
    ) {
        val currentOnActions by rememberUpdatedState(newValue = onAction)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(
                        topStart = chatState.bottomBarTopRadius(skeleton = skeleton).value,
                        topEnd = chatState.bottomBarTopRadius(skeleton = skeleton).value,
                        bottomEnd = 0.dp,
                        bottomStart = 0.dp
                    )
                )
        ) {
            AnimatedVisibility(chatState.action.isNotEmpty() && !skeleton) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(
                        top = 24.dp,
                        bottom = 16.dp,
                        start = 24.dp,
                        end = 24.dp
                    )
                ) {
                    chatState.action.fastForEach { action ->
                        key(action) {
                            Button(
                                onClick = { /*TODO*/ },
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Text(text = action)
                            }
                        }
                    }
                }
            }

            AnimatedVisibility(visible = messageState.selectedFiles.isNotEmpty() && !skeleton) {
                LazyRow(
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    items(messageState.selectedFiles, key = { it.id }) {
                        ChatSelectedFileView(
                            file = it,
                            onDelete = { currentOnActions(ChatAction.DeleteFileFromSelected(it)) }
                        )
                    }
                }
            }

            ChatInput(
                value = messageState.message,
                onValueChange = { currentOnActions(ChatAction.ChangeMessage(it)) },
                onSend = { currentOnActions(ChatAction.Send) },
                onChooseFile = { currentOnActions(ChatAction.ChooseFile) },
                showSend = messageState.showSend,
                skeleton = skeleton,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.safeDrawing))
    }

    @Composable
    private fun ChatUiState.ChatState.bottomBarTopRadius(skeleton: Boolean): State<Dp> {
        val needRound = blocking || (action.isNotEmpty() && !skeleton)
        return animateDpAsState(if (needRound) 32.dp else 0.dp, label = "RoundBottomBar")
    }

    private fun handlerAction(
        action: ChatAction,
        navHostController: NavHostController,
        viewModel: ChatViewModel
    ) {
        when (action) {
            is ChatAction.OnBack -> navHostController.popBackStack()
            is ChatAction.DeleteFileFromSelected -> viewModel.deleteFileFromSelected(action.chatFile)
            is ChatAction.ChangeMessage -> viewModel.changeMessage(action.message)
            is ChatAction.Send -> viewModel.sendMessage()
        }
    }

    internal sealed interface ChatAction {
        data object OnBack : ChatAction
        data class DeleteFileFromSelected(val chatFile: ChatSelectedFile) : ChatAction
        data class ChangeMessage(val message: String) : ChatAction
        data object Send : ChatAction
        data object ChooseFile : ChatAction
    }

    @Composable
    @Preview
    private fun ChatScreenPreview() {
        MaterialTheme {
            ChatScreenState(
                state = ChatUiState(
                    message = ChatDetails(
                        icon = "",
                        name = "Chat",
                        messages = List(5) {
                            Message(
                                userMessage = "$it UserMessage",
                                otherMessages = "$it OtherMessages"
                            )
                        },
                        status = "Онлайн"
                    )
                ),
                onAction = {}

            )
        }
    }