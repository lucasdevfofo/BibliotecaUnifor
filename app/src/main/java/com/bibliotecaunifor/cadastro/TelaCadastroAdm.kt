package com.bibliotecaunifor.cadastro

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bibliotecaunifor.R
import com.bibliotecaunifor.viewmodel.CadastroViewModel
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCadastroAdm(
    onNavigateUp: () -> Unit,
    onIrParaUsuarioClick: () -> Unit,
    onNavigateSuccess: () -> Unit
) {
    val azulBotao = Color(0xFF3F4F78)

    // ---------------------- VIEWMODEL ----------------------
    val viewModel = remember { CadastroViewModel() }
    val loading = viewModel.loading
    val sucesso = viewModel.sucesso
    val erro = viewModel.erro

    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(sucesso) {
        if (sucesso) {
            onNavigateSuccess()
            viewModel.sucesso = false
        }
    }
    // -------------------------------------------------------

    var nome by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var matricula by remember { mutableStateOf("") }
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

            // ------------------ TOPO ------------------
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
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo",
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(60.dp))
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(124.dp)
                        .align(Alignment.BottomCenter),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Reserve sua sala",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Biblioteca Unifor",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            lineHeight = 22.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // ----------------- BOTÕES USER / ADM -----------------
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = onIrParaUsuarioClick,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = azulBotao
                    ),
                    border = BorderStroke(2.dp, azulBotao),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                ) {
                    Text("Usuário", fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = azulBotao
                    ),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                ) {
                    Text("Admin", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }

            // ------------------ FORM ------------------
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {

                Text(
                    text = "CADASTRO ADMIN",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                CampoCinzaAdm("Nome Completo", nome) { nome = it }
                CampoCinzaAdm("Senha", senha) { senha = it }
                CampoCinzaAdm("Matrícula", matricula) { matricula = it }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Column(Modifier.weight(1f)) {
                        CampoCinzaAdm("CPF", cpf) { cpf = it }
                    }
                    Column(Modifier.weight(1f)) {
                        CampoCinzaAdm("Telefone", telefone) { telefone = it }
                    }
                }

                CampoCinzaAdm("E-mail", email) { email = it }

                Spacer(modifier = Modifier.height(10.dp))

                // ------------------ BOTÃO CADASTRAR ------------------
                Button(
                    onClick = {
                        if (nome.isBlank() || senha.isBlank() || email.isBlank()) {
                            scope.launch { snackbar.showSnackbar("Preencha todos os campos obrigatórios.") }
                        } else {
                            viewModel.cadastrarAdmin(
                                nome = nome,
                                matricula = matricula,
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
                        .height(48.dp)
                        .pointerHoverIcon(PointerIcon.Hand),
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

                // Mostrar erro de cadastro
                if (erro != null) {
                    LaunchedEffect(erro) {
                        scope.launch { snackbar.showSnackbar(erro!!) }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun CampoCinzaAdm(
    placeholder: String,
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    val cinzaCampo = Color(0xFFF2F2F2)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(cinzaCampo, RoundedCornerShape(6.dp))
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
fun TelaCadastroAdmPreview() {
    BibliotecaUniforTheme {
        TelaCadastroAdm(
            onNavigateUp = {},
            onIrParaUsuarioClick = {},
            onNavigateSuccess = {}
        )
    }
}
