package com.example.notesapp.presentation.notes_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.domain.common.NoteOrder
import com.example.domain.common.OrderType
import com.example.notesapp.R

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChange: (NoteOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(modifier = modifier.fillMaxWidth()) {
            RadioButton(
                text = stringResource(id = R.string.title),
                selected = noteOrder is NoteOrder.Title,
                onSelect = { onOrderChange(NoteOrder.Title(noteOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            RadioButton(
                text = stringResource(id = R.string.date),
                selected = noteOrder is NoteOrder.Date,
                onSelect = { onOrderChange(NoteOrder.Date(noteOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            RadioButton(
                text = stringResource(id = R.string.color),
                selected = noteOrder is NoteOrder.Color,
                onSelect = { onOrderChange(NoteOrder.Color(noteOrder.orderType)) })
        }
        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            RadioButton(
                text = stringResource(id = R.string.ascending),
                selected = noteOrder.orderType is OrderType.Ascending,
                onSelect = { onOrderChange(noteOrder.copy(OrderType.Ascending)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            RadioButton(
                text = stringResource(id = R.string.descending),
                selected = noteOrder.orderType is OrderType.Descending,
                onSelect = { onOrderChange(noteOrder.copy(OrderType.Descending)) }
            )
        }
    }
}