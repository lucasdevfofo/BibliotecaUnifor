package com.bibliotecaunifor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme


@Composable
fun AppHeaderRenovarLivro(
    navController: NavController,
    menuAberto: MutableState<Boolean>
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.livros),
            contentDescription = "Imagem de fundo biblioteca",
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
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar",
                    tint = Color.Black
                )
            }

            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo UNIFOR",
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.width(40.dp))
        }

        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 16.dp, top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconButton(
                onClick = { navController.navigate(Route.Notificacoes.path) },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Notificações",
                    tint = Color.Black,
                    modifier = Modifier.size(28.dp)
                )
            }

            IconButton(
                onClick = { menuAberto.value = !menuAberto.value },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Menu,
                    contentDescription = "Menu",
                    tint = Color.Black,
                    modifier = Modifier.size(30.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(124.dp)
                .align(Alignment.BottomCenter),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Reserve sua sala", color = Color.White, fontSize = 18.sp)
                Text(
                    "Biblioteca Unifor",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun AppBottomNavRenovarLivro(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Filled.Home, "Home", tint = Color.Gray,
            modifier = Modifier.clickable { navController.navigate(Route.SalasDisponiveis.path) }
        )
        Icon(Icons.Filled.DateRange, "Reservas", tint = Color.Gray,
            modifier = Modifier.clickable { navController.navigate(Route.ReservasRealizadas.path) }
        )
        Icon(Icons.Filled.List, "Catálogo", tint = Color.Gray,
            modifier = Modifier.clickable { navController.navigate(Route.CatalogoLivros.path) }
        )
        Icon(Icons.Filled.Person, "Perfil", tint = Color.Gray,
            modifier = Modifier.clickable { navController.navigate(Route.PerfilAluno.path) }
        )
    }
}


@Composable
fun TelaRenovarLivro(
    navController: NavController
) {
    val menuAberto = remember { mutableStateOf(false) }
    var tempoPermanencia by remember { mutableStateOf("12/09 - 20/09") }

    Scaffold(
        topBar = {
            AppHeaderRenovarLivro(
                navController = navController,
                menuAberto = menuAberto
            )
        },
        bottomBar = { AppBottomNavRenovarLivro(navController = navController) }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.White)
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "PEQUENO PRÍNCIPE",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF044EE7),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "TEMPO DE PERMANÊNCIA",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = tempoPermanencia,
                    onValueChange = { tempoPermanencia = it },
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            Icons.Filled.KeyboardArrowDown,
                            contentDescription = "Selecionar Data",
                            modifier = Modifier.clickable { }
                        )
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.LightGray,
                        unfocusedBorderColor = Color.LightGray
                    )
                )

                Spacer(modifier = Modifier.height(140.dp))

                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F4F78)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        "RENOVAR",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
            }

            if (menuAberto.value) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.4f))
                        .zIndex(0.5f)
                        .clickable { menuAberto.value = false }
                )
                MenuLateral(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .zIndex(1f)
                        .clip(RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp)),
                    navController = navController
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaRenovarLivroPreview() {
    val navController = rememberNavController()
    BibliotecaUniforTheme {
        TelaRenovarLivro(navController)
    }
}