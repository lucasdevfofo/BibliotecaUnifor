package com.bibliotecaunifor.Adm
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bibliotecaunifor.R
import com.bibliotecaunifor.ui.theme.roxoBotao

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaPerfilAdmin(
    navController: NavController,
    onVoltarClick: () -> Unit,
    onNotificacoesClick: () -> Unit,
    onMenuClick: () -> Unit,
    onEditarClick: () -> Unit,
    onNavSalasClick: () -> Unit,
    onNavReservasClick: () -> Unit,
    onNavUsuariosClick: () -> Unit,
    onNavPerfilClick: () -> Unit,
    currentRoute: String,
    nome: String = "PEDRO AUGUSTO",
    cargo: String = "",
    departamento: String = "ADMINISTRADOR"
) {
    val cinzaBorda = Color(0xFFE0E0E0)
    val cinzaClaro = Color(0xFFF5F7FF)

    var menuLateralAberto by remember { mutableStateOf(false) }
    val scroll = rememberScrollState()


    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                AdminTopBarPerfil(
                    onVoltarClick = onVoltarClick,
                    onNotificacoesClick = onNotificacoesClick,

                    onMenuClick = { menuLateralAberto = true }
                )
            },
            bottomBar = {
                AppBottomNavAdminPadrao(
                    onHomeClick = onNavSalasClick,
                    onHistoricoClick = onNavReservasClick,
                    onListasClick = onNavUsuariosClick,
                    onPerfilClick = onNavPerfilClick,
                    currentRoute = currentRoute
                )
            }
        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(scroll),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                        .background(cinzaClaro)
                        .border(1.dp, cinzaBorda, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_user),
                        contentDescription = "Avatar Administrador",
                        modifier = Modifier.size(72.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = nome, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)

                Text(text = cargo, fontSize = 12.sp, color = Color.Gray)

                Divider(
                    modifier = Modifier.padding(top = 12.dp).width(240.dp),
                    color = cinzaBorda
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(text = departamento, fontSize = 14.sp, color = Color.Black)

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = onEditarClick,
                    colors = ButtonDefaults.buttonColors(containerColor = roxoBotao),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .height(44.dp)
                        .fillMaxWidth(0.6f)
                ) {
                    Text("EDITAR PERFIL", color = Color.White, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))
                Spacer(modifier = Modifier.weight(1f))
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
                onLinkClick = { menuLateralAberto = false }
            )
        }
    }
}