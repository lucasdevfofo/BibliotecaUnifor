package com.bibliotecaunifor.Adm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaAdminEditarLivro(
    navController: NavController,
    tituloLivro: String,
    onVoltarClick: () -> Unit,
    onNotificacoesClick: () -> Unit,
    onMenuClick: () -> Unit,
    onSalvarEdicaoClick: (
        titulo: String,
        autor: String,
        genero: String,
        descricao: String,
        isbn: String,
        quantidade: Int
    ) -> Unit
) {

    val corPrincipal = Color(0xFF044EE7)
    val corFundoInput = Color(0xFFF2F2F2)
    val scrollState = rememberScrollState()


    val tituloOriginal = URLDecoder.decode(tituloLivro, StandardCharsets.UTF_8.toString())


    var titulo by remember { mutableStateOf(TextFieldValue(tituloOriginal)) }
    var autor by remember { mutableStateOf(TextFieldValue("Antoine de Saint-Exupéry")) }
    var genero by remember { mutableStateOf(TextFieldValue("Fábula Filosófica")) }
    var descricao by remember { mutableStateOf(TextFieldValue("Uma fábula sobre um menino que viaja por planetas e aprende lições sobre amor, amizade e a essência da vida.")) }
    var isbn by remember { mutableStateOf(TextFieldValue("978-85-359-2218-8")) }
    var quantidade by remember { mutableStateOf(TextFieldValue("5")) }

    Scaffold(
        topBar = {
            AdminTopBar(
                onVoltarClick = onVoltarClick,
                onNotificacoesClick = onNotificacoesClick,
                onMenuClick = onMenuClick
            )
        },
        bottomBar = {

        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "EDITAR LIVRO",
                color = corPrincipal,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                tituloOriginal,
                color = Color.Gray,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )
            Divider(color = corPrincipal.copy(alpha = 0.5f), thickness = 1.dp)
            Spacer(modifier = Modifier.height(24.dp))


            CampoEdicaoLivro(
                label = "Título do Livro",
                value = titulo,
                onValueChange = { titulo = it },
                corPrincipal = corPrincipal,
                corFundoInput = corFundoInput
            )


            CampoEdicaoLivro(
                label = "Autor",
                value = autor,
                onValueChange = { autor = it },
                corPrincipal = corPrincipal,
                corFundoInput = corFundoInput
            )


            CampoEdicaoLivro(
                label = "Gênero",
                value = genero,
                onValueChange = { genero = it },
                corPrincipal = corPrincipal,
                corFundoInput = corFundoInput
            )


            CampoEdicaoLivro(
                label = "Descrição",
                value = descricao,
                onValueChange = { descricao = it },
                corPrincipal = corPrincipal,
                corFundoInput = corFundoInput,
                singleLine = false,
                minLines = 3
            )


            CampoEdicaoLivro(
                label = "ISBN",
                value = isbn,
                onValueChange = { isbn = it },
                corPrincipal = corPrincipal,
                corFundoInput = corFundoInput
            )


            CampoEdicaoLivro(
                label = "Quantidade em Estoque",
                value = quantidade,
                onValueChange = { quantidade = it },
                corPrincipal = corPrincipal,
                corFundoInput = corFundoInput
            )

            Spacer(modifier = Modifier.height(48.dp))


            Button(
                onClick = {
                    val quantidadeInt = quantidade.text.toIntOrNull() ?: 0
                    onSalvarEdicaoClick(
                        titulo.text,
                        autor.text,
                        genero.text,
                        descricao.text,
                        isbn.text,
                        quantidadeInt
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .pointerHoverIcon(PointerIcon.Hand),
                colors = ButtonDefaults.buttonColors(containerColor = corPrincipal),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "SALVAR ALTERAÇÕES", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampoEdicaoLivro(
    label: String,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    corPrincipal: Color,
    corFundoInput: Color,
    singleLine: Boolean = true,
    minLines: Int = 1
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 4.dp),
            color = Color.Black.copy(alpha = 0.8f)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = singleLine,
            minLines = minLines,
            maxLines = if (singleLine) 1 else Int.MAX_VALUE,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = corPrincipal,
                unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                focusedContainerColor = corFundoInput,
                unfocusedContainerColor = corFundoInput,
                cursorColor = corPrincipal
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TelaAdminEditarLivroPreview() {
    TelaAdminEditarLivro(
        navController = rememberNavController(),
        tituloLivro = "O Pequeno Príncipe",
        onVoltarClick = {},
        onNotificacoesClick = {},
        onMenuClick = {},
        onSalvarEdicaoClick = { _, _, _, _, _, _ -> }
    )
}