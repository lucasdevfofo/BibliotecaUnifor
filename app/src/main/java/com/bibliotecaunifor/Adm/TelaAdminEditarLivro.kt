package com.bibliotecaunifor.Adm

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bibliotecaunifor.model.Livro
import com.bibliotecaunifor.viewmodel.TelaAdminLivrosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaAdminEditarLivro(
    navController: NavController,
    livroId: String
) {
    val viewModel: TelaAdminLivrosViewModel = viewModel()
    var menuLateralAberto by remember { mutableStateOf(false) }
    var livro by remember { mutableStateOf<Livro?>(null) }

    val corPrincipal = Color(0xFF044EE7)
    val corFundoInput = Color(0xFFF2F2F2)
    val scrollState = rememberScrollState()

    var titulo by remember { mutableStateOf("") }
    var autor by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var isbn by remember { mutableStateOf("") }
    var anoPublicacao by remember { mutableStateOf("") }
    var editora by remember { mutableStateOf("") }
    var paginas by remember { mutableStateOf("") }
    var disponivel by remember { mutableStateOf(true) }

    val opcoesDisponibilidade = listOf("Disponível", "Indisponível")
    var expandirDisponibilidade by remember { mutableStateOf(false) }

    val mensagem by viewModel.mensagem.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(livroId) {
        viewModel.buscarLivroPorId(livroId) { livroEncontrado ->
            livro = livroEncontrado
            livroEncontrado?.let {
                titulo = it.titulo
                autor = it.autor
                genero = it.genero
                descricao = it.descricao
                isbn = it.isbn
                anoPublicacao = it.anoPublicacao.toString()
                editora = it.editora
                paginas = it.paginas.toString()
                disponivel = it.disponibilidade == "Disponível"
            }
        }
    }

    LaunchedEffect(mensagem) {
        if (mensagem?.contains("sucesso") == true) {
            navController.popBackStack()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                AdminTopBar(
                    onVoltarClick = { navController.popBackStack() },
                    onNotificacoesClick = { /* TODO */ },
                    onMenuClick = { menuLateralAberto = true }
                )
            },
            bottomBar = {}
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
                    livro?.titulo ?: "Carregando...",
                    color = Color.Gray,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp)
                )

                if (mensagem?.contains("Erro") == true) {
                    Text(
                        text = mensagem ?: "",
                        color = Color.Red,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )
                }

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
                    label = "Ano de Publicação",
                    value = anoPublicacao,
                    onValueChange = { anoPublicacao = it },
                    corPrincipal = corPrincipal,
                    corFundoInput = corFundoInput
                )

                CampoEdicaoLivro(
                    label = "Editora",
                    value = editora,
                    onValueChange = { editora = it },
                    corPrincipal = corPrincipal,
                    corFundoInput = corFundoInput
                )

                CampoEdicaoLivro(
                    label = "Número de Páginas",
                    value = paginas,
                    onValueChange = { paginas = it },
                    corPrincipal = corPrincipal,
                    corFundoInput = corFundoInput
                )

                Text(
                    text = "Disponibilidade:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    color = Color.Black.copy(alpha = 0.8f)
                )
                Box(modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp)) {
                    OutlinedTextField(
                        value = if (disponivel) "Disponível" else "Indisponível",
                        onValueChange = { },
                        readOnly = true,
                        trailingIcon = {
                            Icon(
                                imageVector = if (expandirDisponibilidade) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = "Expandir",
                                Modifier.clickable { expandirDisponibilidade = !expandirDisponibilidade }
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expandirDisponibilidade = !expandirDisponibilidade },
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                            focusedBorderColor = corPrincipal
                        )
                    )

                    DropdownMenu(
                        expanded = expandirDisponibilidade,
                        onDismissRequest = { expandirDisponibilidade = false },
                        modifier = Modifier.fillMaxWidth(0.9f)
                    ) {
                        opcoesDisponibilidade.forEach { selecao ->
                            DropdownMenuItem(
                                text = { Text(selecao) },
                                onClick = {
                                    disponivel = selecao == "Disponível"
                                    expandirDisponibilidade = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(48.dp))

                Button(
                    onClick = {
                        livro?.let { livroAtual ->
                            val livroAtualizado = livroAtual.copy(
                                titulo = titulo,
                                autor = autor,
                                genero = genero,
                                descricao = descricao,
                                isbn = isbn,
                                anoPublicacao = anoPublicacao.toIntOrNull() ?: 0,
                                editora = editora,
                                paginas = paginas.toIntOrNull() ?: 0,
                                disponibilidade = if (disponivel) "Disponível" else "Indisponível"
                            )

                            viewModel.atualizarLivro(livroAtualizado)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .pointerHoverIcon(PointerIcon.Hand),
                    colors = ButtonDefaults.buttonColors(containerColor = corPrincipal),
                    shape = RoundedCornerShape(8.dp),
                    enabled = !isLoading && livro != null && titulo.isNotEmpty() && autor.isNotEmpty()
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(text = "SALVAR ALTERAÇÕES", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }

        if (menuLateralAberto) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(4f)
                    .clickable { menuLateralAberto = false }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.4f))
                )

                MenuLateralAdmin(
                    navController = navController,
                    onLinkClick = { menuLateralAberto = false },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .fillMaxHeight()
                        .fillMaxWidth(0.75f)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampoEdicaoLivro(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
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
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = corPrincipal,
                unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                focusedContainerColor = corFundoInput,
                unfocusedContainerColor = corFundoInput,
                cursorColor = corPrincipal
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TelaAdminEditarLivroPreview() {
    TelaAdminEditarLivro(
        navController = rememberNavController(),
        livroId = "1"
    )
}