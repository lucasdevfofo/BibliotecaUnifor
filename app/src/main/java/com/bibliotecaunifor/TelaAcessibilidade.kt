package com.bibliotecaunifor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bibliotecaunifor.ui.theme.BibliotecaUniforTheme

@Composable
fun AppHeaderAcessibilidade(
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
            contentDescription = "",
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
                    contentDescription = "",
                    tint = Color.Black
                )
            }

            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "",
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.width(56.dp))
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
                    contentDescription = "",
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
                    contentDescription = "",
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
                Text(
                    "Reserve sua sala",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                )
                Text(
                    "Biblioteca Unifor",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun AppBottomNavAcessibilidade(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.White)
            .border(1.dp, Color.LightGray.copy(alpha = 0.5f), RoundedCornerShape(0.dp)),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_home),
            contentDescription = "Home",
            tint = Color.Gray,
            modifier = Modifier.clickable { navController.navigate(Route.SalasDisponiveis.path) }
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_calendar),
            contentDescription = "Histórico",
            tint = Color.Gray,
            modifier = Modifier.clickable { navController.navigate(Route.HistoricoReservas.path) }
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_list),
            contentDescription = "Listas",
            tint = Color.Gray,
            modifier = Modifier.clickable { navController.navigate(Route.ReservasRealizadas.path) }
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_user),
            contentDescription = "Perfil",
            tint = Color.Gray,
            modifier = Modifier.clickable { navController.navigate(Route.PerfilAluno.path) }
        )
    }
}

@Composable
fun TelaAcessibilidade(navController: NavController) {
    var selectedFontSize by remember { mutableStateOf(0) }
    var selectedColorBlindness by remember { mutableStateOf<String?>(null) }
    val menuAberto = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            AppHeaderAcessibilidade(
                navController = navController,
                menuAberto = menuAberto
            )
        },
        bottomBar = { AppBottomNavAcessibilidade(navController = navController) }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("FONTE", fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                    Text("ACESSIBILIDADE", fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FontOption(
                            text = "aA",
                            fontSize = 20.sp,
                            isSelected = selectedFontSize == 0,
                            onClick = { selectedFontSize = 0 }
                        )
                        FontOption(
                            text = "aA",
                            fontSize = 30.sp,
                            isSelected = selectedFontSize == 1,
                            onClick = { selectedFontSize = 1 }
                        )
                        FontOption(
                            text = "aA",
                            fontSize = 40.sp,
                            isSelected = selectedFontSize == 2,
                            onClick = { selectedFontSize = 2 }
                        )
                    }
                }

                Text(
                    "DALTONISMO",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, top = 20.dp, bottom = 8.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    ColorBlindnessOption("ACROMATIA", "Acromatia", selectedColorBlindness, { selectedColorBlindness = it })
                    ColorBlindnessOption("TRITANOPIA", "Tritanopia", selectedColorBlindness, { selectedColorBlindness = it })
                    ColorBlindnessOption("PROTANOPIA", "Protanopia", selectedColorBlindness, { selectedColorBlindness = it })
                    ColorBlindnessOption("DEUTERANOPIA", "Deuteranopia", selectedColorBlindness, { selectedColorBlindness = it })
                }
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

@Composable
fun FontOption(
    text: String,
    fontSize: TextUnit,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = text,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = Color(0xFF004AF5),
                unselectedColor = Color.Gray
            )
        )
    }
}

@Composable
fun ColorBlindnessOption(
    label: String,
    value: String,
    selectedValue: String?,
    onValueChange: (String?) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onValueChange(if (selectedValue == value) null else value) }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )
        RadioButton(
            selected = selectedValue == value,
            onClick = { onValueChange(if (selectedValue == value) null else value) },
            colors = RadioButtonDefaults.colors(
                selectedColor = Color(0xFF004AF5),
                unselectedColor = Color.Gray
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TelaAcessibilidadePreview() {
    val navController = rememberNavController()
    BibliotecaUniforTheme {
        TelaAcessibilidade(navController)
    }
}