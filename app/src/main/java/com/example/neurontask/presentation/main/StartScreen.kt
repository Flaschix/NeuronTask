package com.example.neurontask.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.neurontask.R
import com.example.neurontask.domain.entity.User
import com.example.neurontask.presentation.BackBtn

@Composable
fun StartScreen(
    viewModel: StartScreenViewModel,
    onNavigateToRegistration: () -> Unit,
    onNavigateToPurchases: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        val state by viewModel.state.collectAsState()

        when (val currentState = state) {
            is StartScreenState.Loading -> {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is StartScreenState.Authorized -> {
                val user = currentState.user
                Column{
                    UserInfo(user = user)
                    Body(
                        onNavigateToRegistration,
                        onNavigateToPurchases
                    )
                }
            }
            is StartScreenState.NotAuthorized -> {
                Column {
                    Body(
                        onNavigateToRegistration,
                        onNavigateToPurchases
                    )
                }
            }
        }
    }

}

@Composable
fun UserInfo(user: User) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = user.firstName,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 4.dp)
        ) {
            Text(
                text = user.lastName,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = {  },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(id = R.string.edit_profile),
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = user.phone,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}


@Composable
fun Body(
    onNavigateToRegistration: () -> Unit,
    onNavigateToPurchases: () -> Unit
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)) {

        CategoryTitle(stringResource(id = R.string.my_purchases))

        Spacer(modifier = Modifier.height(8.dp))

        CustomListItem(
            leftContent = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Icon",
                    tint = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.sizeIn(50.dp, 50.dp)
                )
            },
            rightContent = {
                Arrow()
            },
            onClick = onNavigateToPurchases
        )

        Spacer(modifier = Modifier.height(24.dp))

        CategoryTitle(stringResource(id = R.string.settings))

        Spacer(modifier = Modifier.height(8.dp))

        CustomListItem(
            leftContent = {
                ItemText(stringResource(id = R.string.email))
            },
            rightContent = {
                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        text = "smm@gmail.com",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(id = R.string.need_to_confirm),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                Arrow()
            },
            onClick = {}
        )

        Spacer(modifier = Modifier.height(8.dp))

        CustomListItem(
            leftContent = {
                ItemText(text = stringResource(id = R.string.enter_by_bio))
            },
            rightContent = {
                Switch(
                    checked = true,
                    onCheckedChange = {},
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = MaterialTheme.colorScheme.error,
                    )
                )
            },
            onClick = {}
        )

        Spacer(modifier = Modifier.height(8.dp))

        CustomListItem(
            leftContent = {
                ItemText(text = stringResource(id = R.string.change_code))
            },
            rightContent = {
                Arrow()
            },
            onClick = {}
        )

        Spacer(modifier = Modifier.height(8.dp))
        
        CustomListItem(
            leftContent = {
                ItemText(text = stringResource(id = R.string.registration_btn))
            },
            rightContent = {
                Arrow()
            },
            onClick = onNavigateToRegistration
        )

        Spacer(modifier = Modifier.height(8.dp))
        
        CustomListItem(
            leftContent = {
                ItemText(text = stringResource(id = R.string.lang))
            },
            rightContent = {
                Text(
                    text = stringResource(id = R.string.ru), style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.width(8.dp))
                Arrow()
            },
            onClick = {}
        )
    }
}


@Composable
fun CustomListItem(
    modifier: Modifier = Modifier,
    leftContent: @Composable RowScope.() -> Unit,
    rightContent: @Composable RowScope.() -> Unit,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 68.dp)
            .background(MaterialTheme.colorScheme.onBackground, shape = RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            content = leftContent
        )

        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            content = rightContent
        )
    }
}


@Composable
fun CategoryTitle(text: String){
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun ItemText(text: String){
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun Arrow(){
    Icon(
        imageVector = Icons.Default.KeyboardArrowRight,
        contentDescription = "Arrow",
        tint = MaterialTheme.colorScheme.primary
    )
}


