package com.bibliotecaunifor.Adm


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bibliotecaunifor.R
import com.bibliotecaunifor.ui.theme.roxoBotao

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaAdminEditarPerfil(
    navController: NavController,
    onVoltarClick: () -> Unit,
    onNotificacoesClick: () -> Unit,
    onMenuClick: () -> Unit,
    onConfirmarEdicao: (
        novoNome: String,
        novaMatricula: String,
        novoEmail: String,
        novoTelefone: String,
        novoCpf: String
    ) -> Unit
) {

    var menuLateralAberto by remember { mutableStateOf(false) }


    var nome by remember { mutableStateOf("Pedro Augusto") }
    var matricula by remember { mutableStateOf("2345989") }
    var email by remember { mutableStateOf("PedroAugusto@gmail.com") }
    var telefone by remember { mutableStateOf("82 9 8273-9280") }
    var cpf by remember { mutableStateOf("888.222.444-99") }

    val scrollState = rememberScrollState()


    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                AdminTopBarUsuarios(
                    onVoltarClick = onVoltarClick,
                    onNotificacoesClick = onNotificacoesClick,

                    onMenuClick = { menuLateralAberto = true }
                )
            },

        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.White)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Editar Perfil",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = "Avatar do usuário",
                    modifier = Modifier
                        .size(96.dp)
                        .padding(bottom = 16.dp),
                    tint = Color.Gray
                )


                TextFieldCustom(value = nome, label = "Nome completo", onValueChange = { nome = it })
                TextFieldCustom(value = matricula, label = "Matrícula", onValueChange = { matricula = it }, keyboardType = KeyboardType.Number)
                TextFieldCustom(value = email, label = "Email", onValueChange = { email = it }, keyboardType = KeyboardType.Email)
                TextFieldCustom(value = telefone, label = "Telefone", onValueChange = { telefone = it }, keyboardType = KeyboardType.Phone)
                TextFieldCustom(value = cpf, label = "CPF", onValueChange = { cpf = it }, keyboardType = KeyboardType.Number)

                Spacer(modifier = Modifier.height(32.dp))


                Button(
                    onClick = {
                        onConfirmarEdicao(nome, matricula, email, telefone, cpf)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = roxoBotao),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(55.dp)
                ) {
                    Text("CONFIRMAR", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }


        if (menuLateralAberto) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))

                    .clickable { menuLateralAberto = false }
            )


            MenuLateralAdmin(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .fillMaxHeight()
                    .fillMaxWidth(0.75f)
                    .clip(RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp)),
                navController = navController,
                onLinkClick = { menuLateralAberto = false } // <-- A CORREÇÃO
            )
        }
    }
}


@Composable
fun TextFieldCustom(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(vertical = 8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.LightGray,
            cursorColor = Color.Black
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}
@Preview(showBackground = true)
@Composable
fun TelaAdminEditarPerfilPreview() {
    Surface(color = Color.White) {
        val navController = rememberNavController()

        TelaAdminEditarPerfil(
            navController = navController,
            onVoltarClick = { /* */ },
            onNotificacoesClick = { /* */ },
            onMenuClick = { /* */ },
            onConfirmarEdicao = { _, _, _, _, _ -> /* */ }
        )
    }
}