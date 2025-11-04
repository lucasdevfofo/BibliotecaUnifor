package com.bibliotecaunifor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import android.net.Uri

// Modelo dos livros
data class Livro(
    val titulo: String,
    val descricao: String,
    val genero: String,
    val autor: String,
    val disponibilidade: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCatalogoLivros(navController: NavController) {
    var menuAberto by remember { mutableStateOf(false) }
    var pesquisa by remember { mutableStateOf("") }

    // --- Lista fixa com 10 livros de exemplo ---
    val livros = listOf(
        Livro(
            "O Pequeno Príncipe",
            "Uma fábula poética sobre amor, amizade e a essência da vida, acompanhando um jovem príncipe em sua jornada por diferentes planetas.",
            "Fábula filosófica / Literatura infantojuvenil",
            "Antoine de Saint-Exupéry",
            "Disponível"
        ),
        Livro(
            "Noites Brancas",
            "Um jovem sonhador conhece uma mulher em São Petersburgo e vive quatro noites intensas que marcam sua vida para sempre.",
            "Romance psicológico",
            "Fiódor Dostoiévski",
            "Disponível"
        ),
        Livro(
            "Código Limpo",
            "Um guia essencial para desenvolvedores que buscam escrever código legível, eficiente e fácil de manter.",
            "Tecnologia / Programação",
            "Robert C. Martin",
            "Disponível"
        ),
        Livro(
            "Entendendo Algoritmos",
            "Uma introdução visual e prática ao mundo dos algoritmos e estruturas de dados, perfeita para iniciantes em programação.",
            "Tecnologia / Computação",
            "Aditya Bhargava",
            "Disponível"
        ),
        Livro(
            "JavaScript: O Guia Definitivo",
            "A obra mais completa sobre JavaScript, cobrindo desde os fundamentos até conceitos avançados da linguagem.",
            "Tecnologia / Desenvolvimento Web",
            "David Flanagan",
            "Disponível"
        ),
        Livro(
            "Dom Casmurro",
            "A clássica história de amor e ciúmes entre Bentinho e Capitu, escrita com maestria por Machado de Assis.",
            "Romance realista / Literatura brasileira",
            "Machado de Assis",
            "Indisponível"
        ),
        Livro(
            "O Hobbit",
            "Bilbo Bolseiro embarca em uma inesperada aventura ao lado de anões e do mago Gandalf em busca de um tesouro guardado por um dragão.",
            "Fantasia / Aventura",
            "J. R. R. Tolkien",
            "Disponível"
        ),
        Livro(
            "1984",
            "Um retrato distópico de um futuro controlado por um regime totalitário, onde o Grande Irmão tudo vê.",
            "Distopia / Política / Ficção científica",
            "George Orwell",
            "Disponível"
        ),
        Livro(
            "O Cortiço",
            "Um retrato vívido da vida em um cortiço carioca, explorando desigualdade social, miséria e ambição.",
            "Naturalismo / Literatura brasileira",
            "Aluísio Azevedo",
            "Disponível"
        ),
        Livro(
            "Orgulho e Preconceito",
            "A história envolvente entre Elizabeth Bennet e o sr. Darcy, marcada por ironia, amor e críticas sociais sutis.",
            "Romance clássico / Literatura inglesa",
            "Jane Austen",
            "Disponível"
        )
    )

    // Filtro de pesquisa
    val livrosFiltrados = remember(pesquisa) {
        if (pesquisa.isBlank()) livros
        else livros.filter { it.titulo.contains(pesquisa, ignoreCase = true) }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ==== Cabeçalho ====
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
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.size(60.dp).pointerHoverIcon(PointerIcon.Hand)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.weight(0.63f))

                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo UNIFOR",
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
                        onClick = { navController.navigate(Route.Notificacoes.path) },
                        modifier = Modifier.size(40.dp).pointerHoverIcon(PointerIcon.Hand)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "Notificações",
                            tint = Color.Black,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    IconButton(
                        onClick = { menuAberto = !menuAberto },
                        modifier = Modifier.size(40.dp).pointerHoverIcon(PointerIcon.Hand)
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
                        Text("Catálogo de Livros", color = Color.White, fontSize = 18.sp)
                        Text(
                            "Biblioteca Unifor",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ==== Título + Barra de pesquisa ====
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "RESERVE UM LIVRO",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = pesquisa,
                    onValueChange = { pesquisa = it },
                    placeholder = { Text("Pesquise um livro...") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color(0xFFF5F5F5),
                        focusedIndicatorColor = Color(0xFF044EE7),
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ==== Lista de Livros ====
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                items(livrosFiltrados) { livro ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFE7EEFF), RoundedCornerShape(4.dp))
                            .padding(vertical = 12.dp)
                            .clickable {
                                val tituloEncoded = Uri.encode(livro.titulo)
                                val descricaoEncoded = Uri.encode(livro.descricao)
                                val generoEncoded = Uri.encode(livro.genero)
                                val autorEncoded = Uri.encode(livro.autor)
                                val disponivelEncoded = Uri.encode(livro.disponibilidade)
                                navController.navigate(
                                    "descricaoLivro/$tituloEncoded/$descricaoEncoded/$generoEncoded/$autorEncoded/$disponivelEncoded"
                                )
                            }

                    ) {
                        Text(
                            text = livro.titulo,
                            color = Color(0xFF044EE7),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }

        // ==== Menu lateral ====
        if (menuAberto) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
                    .clickable { menuAberto = false }
            )
            MenuLateral(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clip(RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp)),
                navController = navController
            )
        }
    }
}
