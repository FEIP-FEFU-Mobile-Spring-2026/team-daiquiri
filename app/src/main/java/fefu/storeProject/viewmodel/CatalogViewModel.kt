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
        val selectedCategoryId: String,
        val isOffline: Boolean = false
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

    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    val allProducts: StateFlow<List<Product>> = _allProducts.asStateFlow()

    private var allProductsList: List<Product> = emptyList()
    private var allCategories: List<AppCategory> = emptyList()
    private var isCurrentlyOffline = false

    private var selectedCategoryId: String
        get() = savedStateHandle.get<String>(KEY_SELECTED_CATEGORY) ?: ProductRepository.NEW_CATEGORY_ID
        set(value) { savedStateHandle[KEY_SELECTED_CATEGORY] = value }

    init {
        loadCatalog()
    }

    fun loadCatalog() {
        viewModelScope.launch {
            _uiState.value = CatalogUiState.Loading
            isCurrentlyOffline = false

            val cached = withContext(Dispatchers.IO) { repository.loadFromCache() }
            if (cached != null) {
                allProductsList = cached.products
                _allProducts.value = allProductsList
                allCategories = cached.categories
                pushSuccessState()
            }

            val hasNetwork = withContext(Dispatchers.IO) { repository.isNetworkAvailable() }
            if (!hasNetwork) {
                isCurrentlyOffline = true
                if (cached == null) {
                    _uiState.value = CatalogUiState.Error("Нет подключения к сети")
                } else {
                    pushSuccessState()
                }
                return@launch
            }

            try {
                val data = withContext(Dispatchers.IO) { repository.loadFromNetwork() }
                allProductsList = data.products
                _allProducts.value = allProductsList
                allCategories = data.categories
                isCurrentlyOffline = false
                pushSuccessState()
            } catch (e: Exception) {
                isCurrentlyOffline = true
                if (cached == null) {
                    _uiState.value = CatalogUiState.Error(e.message ?: "Ошибка загрузки данных")
                } else {
                    pushSuccessState()
                }
            }
        }
    }

    fun selectCategory(categoryId: String) {
        selectedCategoryId = categoryId
        pushSuccessState()
    }

    private fun pushSuccessState() {
        val filtered = if (selectedCategoryId == ProductRepository.NEW_CATEGORY_ID) {
            allProductsList.filter { it.tags.contains("New") }
        } else {
            allProductsList.filter { it.categoryId == selectedCategoryId }
        }
        _uiState.value = CatalogUiState.Success(
            categories = allCategories,
            products = filtered,
            selectedCategoryId = selectedCategoryId,
            isOffline = isCurrentlyOffline
        )
    }

    companion object {
        private const val KEY_SELECTED_CATEGORY = "selectedCategoryId"
    }
}
