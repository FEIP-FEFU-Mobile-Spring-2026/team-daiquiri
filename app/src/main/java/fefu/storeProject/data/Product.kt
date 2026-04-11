package fefu.storeProject.data

import fefu.storeProject.R
import androidx.compose.ui.graphics.Color
import fefu.storeProject.ui.theme.BrownPrimary

data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val imageRes: Int,
    val categories: Set<Category>
)

enum class Category {
    NEW,
    JEANS,
    TSHIRTS,
    DRESSES,
    OUTERWEAR,
    HOODIES
}

enum class Size {
    XXS, XS, S, M, L, XL
}

data class ProductColor(
    val name: String,
    val color: Color
)

val availableColors = listOf(
    ProductColor("Черный", Color.Black),
    ProductColor("Белый", Color.White),
    ProductColor("Серый", Color.Gray),
    ProductColor("Коричневый", BrownPrimary),
    ProductColor("Бежевый", Color(0xFFD7CCC8)),
    ProductColor("Темно-синий", Color(0xFF1A237E))
)

val products = listOf(
    Product(
        id = 1,
        title = "Блейзер прямого кроя",
        description = "Элегантный двубортный блейзер с четкой линией плеч. Идеально дополняет как офисный, так и вечерний образ. Выполнен из премиальной костюмной ткани.",
        price = 8990,
        imageRes = R.drawable.blazer,
        categories = setOf(Category.OUTERWEAR, Category.NEW)
    ),
    Product(
        id = 2,
        title = "Брюки из лиоцелла",
        description = "Легкие брюки свободного прямого кроя. Лиоцелл — дышащий материал, обеспечивающий максимальный комфорт в теплую погоду. Эластичный пояс для удобной посадки.",
        price = 3990,
        imageRes = R.drawable.pants,
        categories = setOf(Category.JEANS, Category.NEW)
    ),
    Product(
        id = 3,
        title = "Фактурный кардиган",
        description = "Уютный вязаный кардиган из 100% органического хлопка. Укороченные рукава и свободный силуэт создают расслабленный повседневный стиль.",
        price = 4590,
        imageRes = R.drawable.cardigan,
        categories = setOf(Category.TSHIRTS)
    ),
    Product(
        id = 4,
        title = "Базовая футболка oversize",
        description = "Универсальная футболка свободного кроя из плотного хлопка. Долго держит форму и не выцветает после стирки. База для любого гардероба.",
        price = 1990,
        imageRes = R.drawable.tshirt,
        categories = setOf(Category.TSHIRTS)
    ),
    Product(
        id = 5,
        title = "Джинсы Mom Fit",
        description = "Классические джинсы с высокой посадкой в винтажном стиле. Плотный деним, который идеально подчеркивает фигуру и становится мягче со временем.",
        price = 5490,
        imageRes = R.drawable.jeans,
        categories = setOf(Category.JEANS)
    ),

    Product(
        id = 6,
        title = "Платье-комбинация",
        description = "Шелковистое платье-миди на тонких бретелях. Струящаяся ткань создает женственный силуэт. Отлично сочетается с жакетом или свитером.",
        price = 6990,
        imageRes = R.drawable.dress,
        categories = setOf(Category.DRESSES, Category.NEW)
    ),
    Product(
        id = 7,
        title = "Худи с начесом",
        description = "Объемное худи с теплым внутренним слоем. Глубокий капюшон и карман-кенгуру. Незаменимая вещь для прохладных вечеров.",
        price = 3200,
        imageRes = R.drawable.hoodie,
        categories = setOf(Category.HOODIES)
    ),
    Product(
        id = 8,
        title = "Тренч бежевый",
        description = "Классический двубортный тренч с поясом. Водоотталкивающая пропитка защитит от дождя, а стильный крой подойдет к любому образу.",
        price = 12990,
        imageRes = R.drawable.trench,
        categories = setOf(Category.OUTERWEAR, Category.NEW)
    ),
    Product(
        id = 9,
        title = "Льняная рубашка",
        description = "Легкая рубашка из натурального льна. Свободный крой и рукава, которые можно подвернуть. Идеальный выбор для жаркого лета.",
        price = 4100,
        imageRes = R.drawable.shirt,
        categories = setOf(Category.TSHIRTS, Category.NEW)
    ),
    Product(
        id = 10,
        title = "Джинсовая куртка",
        description = "Укороченная джинсовка в стиле 90-х. Прочный деним с легкими потертостями. Металлические пуговицы с гравировкой бренда.",
        price = 5990,
        imageRes = R.drawable.denim_jacket,
        categories = setOf(Category.OUTERWEAR, Category.JEANS)
    )
)