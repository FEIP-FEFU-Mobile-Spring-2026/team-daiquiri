package fefu.storeProject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import fefu.storeProject.data.AppCategory
import fefu.storeProject.data.Product
import fefu.storeProject.data.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class CatalogUiState {
    data object Loading : CatalogUiState()
    data class Success(
        val categories: List<AppCategory>,
        val products: List<Product>,
        val selectedCategoryId: String
    ) : CatalogUiState()
    data class Error(val message: String) : CatalogUiState()
}

class CatalogViewModel(
    application: Application,
    private val savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val repository = ProductRepository(application)

    private val _uiState = MutableStateFlow<CatalogUiState>(CatalogUiState.Loading)
    val uiState: StateFlow<CatalogUiState> = _uiState.asStateFlow()

    private var allProducts: List<Product> = emptyList()
    private var allCategories: List<AppCategory> = emptyList()

    private var selectedCategoryId: String
        get() = savedStateHandle.get<String>(KEY_SELECTED_CATEGORY) ?: ProductRepository.NEW_CATEGORY_ID
        set(value) { savedStateHandle[KEY_SELECTED_CATEGORY] = value }

    init {
        loadCatalog()
    }

    fun loadCatalog() {
        viewModelScope.launch {
            _uiState.value = CatalogUiState.Loading
            try {
                val data = withContext(Dispatchers.IO) { repository.loadCatalog() }
                allProducts = data.products
                allCategories = data.categories
                pushSuccessState()
            } catch (e: Exception) {
                _uiState.value = CatalogUiState.Error(e.message ?: "Ошибка загрузки данных")
            }
        }
    }

    fun selectCategory(categoryId: String) {
        selectedCategoryId = categoryId
        pushSuccessState()
    }

    private fun pushSuccessState() {
        val filtered = if (selectedCategoryId == ProductRepository.NEW_CATEGORY_ID) {
            allProducts.filter { it.tags.contains("New") }
        } else {
            allProducts.filter { it.categoryId == selectedCategoryId }
        }
        _uiState.value = CatalogUiState.Success(
            categories = allCategories,
            products = filtered,
            selectedCategoryId = selectedCategoryId
        )
    }

    companion object {
        private const val KEY_SELECTED_CATEGORY = "selectedCategoryId"
    }
}
