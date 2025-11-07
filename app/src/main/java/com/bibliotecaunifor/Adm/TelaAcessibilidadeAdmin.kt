package com.bibliotecaunifor.Adm

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable // Import necessário
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape // Import necessário
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bibliotecaunifor.Adm.MenuLateralAdmin
import com.bibliotecaunifor.Route

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaAcessibilidadeAdmin(
    navController: NavController,
    onVoltarClick: () -> Unit,
    onNotificacoesClick: () -> Unit,
    onMenuClick: () -> Unit, // Mantido, mas a lógica de abertura é interna

    onNavSalasClick: () -> Unit,
    onNavReservasClick: () -> Unit,
    onNavUsuariosClick: () -> Unit,
    onNavPerfilClick: () -> Unit,
    currentRoute: String
) {
    var menuLateralAberto by remember { mutableStateOf(false) }
    var tamanhoFonteSelecionado by remember { mutableStateOf("MÉDIO") }
    var modoEscuroAtivo by remember { mutableStateOf(false) }
    val cinzaFundo = Color(0xFFF0F0F0)


    // 1. O Box externo engloba tudo
    Box(modifier = Modifier.fillMaxSize()) {

        Scaffold(
            topBar = {
                AdminTopBarUsuarios(
                    onVoltarClick = onVoltarClick,
                    onNotificacoesClick = onNotificacoesClick,
                    // AÇÃO: Atualiza o estado para abrir o menu
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
            // 2. CONTEÚDO PRINCIPAL DENTRO DO SCAFFOLD
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues) // Garante que o conteúdo respeite as barras
                    .padding(horizontal = 16.dp), // Padding lateral
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    "ACESSIBILIDADE",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 16.dp, top = 8.dp)
                )

                // Seção de Tamanho da Fonte
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

                // Seção de Modo Escuro
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
                        Checkbox(
                            checked = modoEscuroAtivo,
                            onCheckedChange = { modoEscuroAtivo = it },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color(0xFF004AF5),
                                uncheckedColor = Color.Gray
                            )
                        )
                    }
                }
            }
        }

        // 3. DESENHA O MENU LATERAL POR CIMA DO SCAFFOLD
        if (menuLateralAberto) {
            // Fundo escuro (Overlay)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
                    // Clicar no overlay fecha o menu
                    .clickable { menuLateralAberto = false }
            )

            // MenuLateralAdmin
            MenuLateralAdmin(
                modifier = Modifier
                    .align(Alignment.TopEnd) // Alinha o menu à direita/topo
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
                selectedColor = Color(0xFF004AF5),
                unselectedColor = Color.Gray
            ),
            modifier = Modifier.size(24.dp).clip(CircleShape)
        )
    }
}