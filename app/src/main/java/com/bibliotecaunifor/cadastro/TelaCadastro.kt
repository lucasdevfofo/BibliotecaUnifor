package com.bibliotecaunifor.cadastro

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.bibliotecaunifor.R
import com.bibliotecaunifor.viewmodel.CadastroViewModel
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCadastro(
    onNavigateUp: () -> Unit,
    onIrParaAdminClick: () -> Unit,
    onNavigateSuccess: () -> Unit
) {
    val azulBotao = Color(0xFF2E4C93)

    // ---------- VIEWMODEL ----------
    val viewModel = remember { CadastroViewModel() }
    val loading = viewModel.loading
    val sucesso = viewModel.sucesso
    val erro = viewModel.erro

    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Navega quando cadastro der certo
    LaunchedEffect(sucesso) {
        if (sucesso) {
            viewModel.sucesso = false
            onNavigateSuccess()
        }
    }
    // ---------------------------------

    var nome by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var matricula by remember { mutableStateOf("") }
    var curso by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbar) }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(padding)
        ) {

            // ======= CABEÇALHO =======
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.livros),
                    contentDescription = null,
                    modifier = Modifier.matchParentSize(),
                    contentScale = ContentScale.Crop
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(Color.White)
                        .align(Alignment.TopCenter),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onNavigateUp,
                        modifier = Modifier.size(60.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.weight(0.67f))
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo",
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(124.dp)
                        .align(Alignment.BottomCenter),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Reserve sua sala\nBiblioteca Unifor",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 22.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // ===== BOTÕES USUÁRIO / ADMIN =====
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = azulBotao
                    ),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                ) {
                    Text("Usuário", color = Color.White, fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = onIrParaAdminClick,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = azulBotao
                    ),
                    border = BorderStroke(2.dp, azulBotao),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                ) {
                    Text("Admin", fontWeight = FontWeight.Bold)
                }
            }

            // ===== FORM =====
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {

                Text("CADASTRO", fontSize = 14.sp, fontWeight = FontWeight.Bold)

                CampoCinza("Nome Completo", nome) { nome = it }
                CampoCinza("Senha", senha) { senha = it }
                CampoCinza("Matrícula", matricula) { matricula = it }

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Column(Modifier.weight(1f)) {
                        CampoCinza("Curso", curso) { curso = it }
                    }
                    Column(Modifier.weight(1f)) {
                        CampoCinza("CPF", cpf) { cpf = it }
                    }
                }

                CampoCinza("Telefone", telefone) { telefone = it }
                CampoCinza("E-mail", email) { email = it }

                // ===== BOTÃO CADASTRAR =====
                Button(
                    onClick = {
                        if (nome.isBlank() || senha.isBlank() || email.isBlank()) {
                            scope.launch { snackbar.showSnackbar("Preencha todos os campos obrigatórios.") }
                        } else {
                            viewModel.cadastrarUsuario(
                                nome = nome,
                                matricula = matricula,
                                curso = curso,
                                cpf = cpf,
                                telefone = telefone,
                                email = email,
                                senha = senha
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = azulBotao),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    if (loading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Text("Cadastrar", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }

                // Mostrar erro do Firebase
                if (erro != null) {
                    LaunchedEffect(erro) {
                        scope.launch {
                            snackbar.showSnackbar(erro ?: "Erro desconhecido")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun CampoCinza(
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    val cinza = Color(0xFFF2F2F2)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(cinza, RoundedCornerShape(6.dp))
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black, fontSize = 14.sp),
            decorationBox = { inner ->
                if (value.isEmpty()) {
                    Text(placeholder, color = Color.Gray, fontSize = 14.sp)
                }
                inner()
            }
        )
    }

    Spacer(modifier = Modifier.height(10.dp))
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun TelaCadastroPreview() {
    BibliotecaUniforTheme {
        TelaCadastro(
            onNavigateUp = {},
            onIrParaAdminClick = {},
            onNavigateSuccess = {}
        )
    }
}
