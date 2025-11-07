package com.bibliotecaunifor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController



private val CorPrincipal = Color(0xFF044EE7)
private val CorCinzaIcone = Color.Gray
private val CorAtivaIcone = Color.Black



@Composable
fun AlunoTopSection(
    onNotificacoesClick: () -> Unit,
    onMenuClick: () -> Unit
) {
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
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
        }


        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 16.dp, top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IconButton(
                onClick = onNotificacoesClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(28.dp)
                )
            }

            IconButton(
                onClick = onMenuClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Menu,
                    contentDescription = null,
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
                Text("ACESSIBILIDADE", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)

            }
        }
    }
}

@Composable
fun AppBottomNavAlunoPadrao(
    onHomeClick: () -> Unit,
    onReservasClick: () -> Unit,
    onCatalogoClick: () -> Unit,
    onPerfilClick: () -> Unit,
    currentRoute: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painterResource(id = R.drawable.ic_home),
            contentDescription = null,
            tint = if (currentRoute == Route.SalasDisponiveis.path) CorAtivaIcone else CorCinzaIcone,
            modifier = Modifier.clickable(onClick = onHomeClick)
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_calendar),
            contentDescription = null,
            tint = if (currentRoute == Route.ReservasRealizadas.path) CorAtivaIcone else CorCinzaIcone,
            modifier = Modifier.clickable(onClick = onReservasClick)
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_list),
            contentDescription = null,
            tint = if (currentRoute == Route.CatalogoLivros.path) CorAtivaIcone else CorCinzaIcone,
            modifier = Modifier.clickable(onClick = onCatalogoClick)
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_user),
            contentDescription = null,
            tint = if (currentRoute == Route.PerfilAluno.path) CorAtivaIcone else CorCinzaIcone,
            modifier = Modifier.clickable(onClick = onPerfilClick)
        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaAcessibilidadeAluno(
    navController: NavController,
    onNotificacoesClick: () -> Unit,
    onVoltarClick: () -> Unit,
    onMenuClick: () -> Unit,

    onNavHomeClick: () -> Unit,
    onNavReservasClick: () -> Unit,
    onNavCatalogoClick: () -> Unit,
    onNavPerfilClick: () -> Unit,
    currentRoute: String
) {
    var menuLateralAberto by remember { mutableStateOf(false) }
    var tamanhoFonteSelecionado by remember { mutableStateOf("MÉDIO") }
    var modoEscuroAtivo by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {

            AlunoTopSection(
                onNotificacoesClick = onNotificacoesClick,
                onMenuClick = { menuLateralAberto = true }
            )


            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "CONFIGURAÇÕES",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 16.dp, top = 8.dp)
                )

                Text(
                    "FONTE",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TamanhoFonteOpcao(
                            label = "aA",
                            size = androidx.compose.ui.unit.TextUnit(18f, androidx.compose.ui.unit.TextUnitType.Sp),
                            isSelected = tamanhoFonteSelecionado == "PEQUENO",
                            onClick = { tamanhoFonteSelecionado = "PEQUENO" }
                        )
                        TamanhoFonteOpcao(
                            label = "aA",
                            size = androidx.compose.ui.unit.TextUnit(24f, androidx.compose.ui.unit.TextUnitType.Sp),
                            isSelected = tamanhoFonteSelecionado == "MÉDIO",
                            onClick = { tamanhoFonteSelecionado = "MÉDIO" }
                        )
                        TamanhoFonteOpcao(
                            label = "aA",
                            size = androidx.compose.ui.unit.TextUnit(32f, androidx.compose.ui.unit.TextUnitType.Sp),
                            isSelected = tamanhoFonteSelecionado == "GRANDE",
                            onClick = { tamanhoFonteSelecionado = "GRANDE" }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    "MODO ESCURO",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Switch(
                            checked = modoEscuroAtivo,
                            onCheckedChange = { modoEscuroAtivo = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = CorPrincipal,
                                uncheckedThumbColor = Color.Gray,
                                uncheckedTrackColor = Color.LightGray
                            )
                        )
                    }
                }
            }


            AppBottomNavAlunoPadrao(
                onHomeClick = onNavHomeClick,
                onReservasClick = onNavReservasClick,
                onCatalogoClick = onNavCatalogoClick,
                onPerfilClick = onNavPerfilClick,
                currentRoute = currentRoute
            )
        }


        if (menuLateralAberto) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
                    .zIndex(0.5f)
                    .clickable(
                        onClick = { menuLateralAberto = false },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    )
            )


            MenuLateral(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .zIndex(1f)
                    .fillMaxHeight()
                    .fillMaxWidth(0.75f)
                    .clip(RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp)),
                navController = navController
            )
        }
    }
}

@Composable
fun TamanhoFonteOpcao(
    label: String,
    size: androidx.compose.ui.unit.TextUnit,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            fontSize = size,
            fontWeight = FontWeight.Bold,
            color = Color.Black.copy(alpha = 0.8f)
        )
        Spacer(modifier = Modifier.height(8.dp))
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = CorPrincipal,
                unselectedColor = Color.Gray
            ),
            modifier = Modifier.size(24.dp).clip(CircleShape)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TelaAcessibilidadeAlunoPreview() {
    TelaAcessibilidadeAluno(
        navController = rememberNavController(),
        onVoltarClick = { },
        onNotificacoesClick = { },
        onMenuClick = { },
        onNavHomeClick = { },
        onNavReservasClick = { },
        onNavCatalogoClick = { },
        onNavPerfilClick = { },
        currentRoute = Route.Acessibilidade.path
    )
}